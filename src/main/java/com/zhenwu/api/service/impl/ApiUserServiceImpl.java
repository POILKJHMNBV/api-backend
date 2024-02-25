package com.zhenwu.api.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.UserHolder;
import com.zhenwu.api.exception.BasicException;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    static {
        LOGIN_SCRIPT = new DefaultRedisScript<>();
        LOGIN_SCRIPT.setLocation(new ClassPathResource("login.lua"));
        LOGIN_SCRIPT.setResultType(Long.class);

        LOGOUT_SCRIPT = new DefaultRedisScript<>();
        LOGOUT_SCRIPT.setLocation(new ClassPathResource("logout.lua"));
        LOGOUT_SCRIPT.setResultType(Long.class);
    }

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BasicException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        // 2.用户注册
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

    @Override
    public String userLogin(String userAccount, String userPassword, String userLoginIp) {
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAccount + userPassword).getBytes());
        Map<String, String> paramMap = new HashMap<>(2);
        paramMap.put("userAccount", userAccount);
        paramMap.put("userPassword", encryptPassword);
        LoginUserVO loginUser = this.apiUserMapper.userLogin(paramMap);
        if (loginUser == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BasicException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }

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
}