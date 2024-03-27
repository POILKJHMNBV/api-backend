package com.zhenwu.api.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.dto.user.QueryUserInfoForm;
import com.zhenwu.api.model.entity.ApiUser;
import com.zhenwu.api.model.vo.LoginUserVO;
import com.zhenwu.api.service.ApiUserService;
import com.zhenwu.api.mapper.ApiUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zhenwu.api.constant.RedisConstants.*;

/**
 * @author zhenwu
 * @description 针对表【api_user(用户表)】的数据库操作Service实现
 */
@Service
@Slf4j
public class ApiUserServiceImpl extends ServiceImpl<ApiUserMapper, ApiUser>
        implements ApiUserService {

    private static final String SALT = "zhenwu";

    @Resource
    private ApiUserMapper apiUserMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final DefaultRedisScript<Long> LOGIN_SCRIPT;
    private static final DefaultRedisScript<Long> LOGOUT_SCRIPT;
    private static final DefaultRedisScript<Long> USER_OFFLINE_SCRIPT;
    static {
        LOGIN_SCRIPT = new DefaultRedisScript<>();
        LOGIN_SCRIPT.setLocation(new ClassPathResource("login.lua"));
        LOGIN_SCRIPT.setResultType(Long.class);

        LOGOUT_SCRIPT = new DefaultRedisScript<>();
        LOGOUT_SCRIPT.setLocation(new ClassPathResource("logout.lua"));
        LOGOUT_SCRIPT.setResultType(Long.class);

        USER_OFFLINE_SCRIPT = new DefaultRedisScript<>();
        USER_OFFLINE_SCRIPT.setLocation(new ClassPathResource("user_offline.lua"));
        USER_OFFLINE_SCRIPT.setResultType(Long.class);
    }

    @Override
    public String generateVerificationCode(String userAccount, int operate) {
        String key = operate == 0 ? REGISTER_VERIFICATION_CODE_KEY + userAccount :
                LOGIN_VERIFICATION_CODE_KEY + userAccount;

        // 1.判断验证码是否已经存在
        if (Boolean.TRUE.equals(this.stringRedisTemplate.hasKey(key))) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "验证码已发送, 请勿重复操作");
        }

        // 2.生成验证码
        String verificationCode = RandomUtil.randomString(4);
        this.stringRedisTemplate.opsForValue().set(key, verificationCode, VERIFICATION_CODE_TTL, TimeUnit.SECONDS);
        return verificationCode;
    }

    @Override
    public long userRegister(String verificationCode, String userAccount, String userPassword, String checkPassword) {

        // 1.密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        // 2.校验验证码
        this.verifyVerificationCode(verificationCode, REGISTER_VERIFICATION_CODE_KEY + userAccount);

        // 3.用户注册
        synchronized (userAccount.intern()) {
            // 判断账户是否已经存在
            QueryWrapper<ApiUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_account", userAccount);
            Long count = this.apiUserMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BasicException(ErrorCode.PARAMS_ERROR, "账户已经存在");
            }

            // 进行用户注册
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAccount + userPassword).getBytes());
            KeyPair pair = SecureUtil.generateKeyPair("RSA");
            PublicKey publicKey = pair.getPublic();
            PrivateKey privateKey = pair.getPrivate();
            String publicKeyStr = Base64.encode(publicKey.getEncoded());
            String privateKeyStr = Base64.encode(privateKey.getEncoded());
            String accessKey = UUID.randomUUID().toString(true);
            String secretKey = Base64.encode(DigestUtil.sha256(accessKey + userAccount + userPassword));

            ApiUser apiUser = new ApiUser();
            apiUser.setUserAccount(userAccount);
            apiUser.setUserPassword(encryptPassword);
            apiUser.setUserPublickey(publicKeyStr);
            apiUser.setUserPrivatekey(privateKeyStr);
            apiUser.setUserAccesskey(accessKey);
            apiUser.setUserSecretkey(secretKey);
            int rows = this.apiUserMapper.insert(apiUser);
            if (rows != 1) {
                throw new BasicException(ErrorCode.SYSTEM_ERROR);
            }
            return apiUser.getId();
        }
    }

    /**
     * 校验验证码
     * @param verificationCode 验证码
     * @param key 验证码 key
     */
    private void verifyVerificationCode(String verificationCode, String key) {
        String currentVerificationCode = this.stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isEmpty(currentVerificationCode)) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "验证码已经失效, 请重新发送");
        }
        if (!currentVerificationCode.equals(verificationCode)) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        this.stringRedisTemplate.delete(key);
    }

    @Override
    public String userLogin(String verificationCode, String userAccount, String userPassword, String userLoginIp) {
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAccount + userPassword).getBytes());
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("userAccount", userAccount);
        paramMap.put("userPassword", encryptPassword);
        LoginUserVO loginUser = this.apiUserMapper.userLogin(paramMap);
        if (loginUser == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BasicException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        if (loginUser.getUserStatus() != 0) {
            log.info("user status error");
            throw new BasicException(ErrorCode.PARAMS_ERROR, "用户状态异常，请联系管理员");
        }

        // 校验验证码
        this.verifyVerificationCode(verificationCode, LOGIN_VERIFICATION_CODE_KEY + userAccount);

        boolean updateResult = this.update()
                .eq("id", loginUser.getId())
                .set("user_login_ip", userLoginIp)
                .setSql("user_login_time = NOW()")
                .update();
        if (!updateResult) {
            throw new BasicException(ErrorCode.SYSTEM_ERROR, "登录失败");
        }

        String key = LOGIN_USER_ACCOUNT + userAccount;
        if (Boolean.TRUE.equals(this.stringRedisTemplate.hasKey(key))) {
            // 用户在别处登录，将当前用户下线
            String oldToken = this.stringRedisTemplate.opsForValue().get(key);
            this.stringRedisTemplate.delete(LOGIN_USER_KEY + oldToken);
        }
        String token = UUID.randomUUID().toString(true);
        Long result = this.stringRedisTemplate.execute(LOGIN_SCRIPT,
                Collections.emptyList(),
                token, LOGIN_USER_TTL.toString(), userAccount, JSONUtil.toJsonStr(loginUser));
        if (result == null || result != 0) {
            throw new BasicException(ErrorCode.SYSTEM_ERROR, "登录失败");
        }
        return token;
    }

    @Override
    public void logout(String token) {
        this.stringRedisTemplate.execute(LOGOUT_SCRIPT,
                Collections.emptyList(),
                token, UserHolder.getUser().getUserAccount());
    }

    @Override
    public boolean offline(Long[] ids) {
        List<ApiUser> apiUserList = this.query().select("user_account").in("id", ids).list();
        List<String> userAccountList = apiUserList.stream().map(ApiUser::getUserAccount).collect(Collectors.toList());
        int size = userAccountList.size();
        String[] userAccountArray = new String[size];
        for (int i = 0; i < size; i++) {
            userAccountArray[i] = userAccountList.get(i);
        }
        Long res = this.stringRedisTemplate.execute(USER_OFFLINE_SCRIPT, Collections.emptyList(), userAccountArray);
        return res != null && res == 0;
    }

    @Override
    public Page<ApiUser> listUserInfoByPage(QueryUserInfoForm queryUserInfoForm) {
        queryUserInfoForm.setStart();
        long count = this.apiUserMapper.listUserInfoCount(queryUserInfoForm);
        List<ApiUser> apiUserList = this.apiUserMapper.listUserInfoByPage(queryUserInfoForm);
        apiUserList.forEach(apiUser -> {
            Date createTime = apiUser.getCreateTime();
            apiUser.setCreateTime(DateUtil.offset(createTime, DateField.HOUR_OF_DAY, -8));
            Date userLoginTime = apiUser.getUserLoginTime();
            apiUser.setUserLoginTime(DateUtil.offset(userLoginTime, DateField.HOUR_OF_DAY, -8));
        });
        long current = queryUserInfoForm.getCurrent();
        long pageSize = queryUserInfoForm.getPageSize();
        Page<ApiUser> page = new Page<>(current, pageSize);
        page.setTotal(count);
        page.setRecords(apiUserList);
        return page;
    }

    @Override
    public long deleteUserInfo(Long[] ids) {
        boolean res = this.update().set("user_status", 1).in("id", ids).update();
        return res && this.offline(ids) ? ids.length : 0;
    }

    @Override
    public boolean forbidUser(long id) {
        boolean res = this.update().set("user_status", 2).eq("id", id).update();
        Long[] ids = new Long[1];
        ids[0] = id;
        return res && this.offline(ids);
    }

    @Override
    public boolean permitUser(long id) {
        return this.update().set("user_status", 0).eq("id", id).update();
    }
}