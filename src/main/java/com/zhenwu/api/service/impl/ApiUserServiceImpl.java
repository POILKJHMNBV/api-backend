package com.zhenwu.api.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.exception.BasicException;
import com.zhenwu.api.model.entity.ApiUser;
import com.zhenwu.api.model.vo.LoginUserVO;
import com.zhenwu.api.service.ApiUserService;
import com.zhenwu.api.mapper.ApiUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import static com.zhenwu.api.constant.RedisConstants.LOGIN_USER_KEY;
import static com.zhenwu.api.constant.RedisConstants.LOGIN_USER_TTL;

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
            ApiUser apiUser = new ApiUser();
            apiUser.setUserAccount(userAccount);
            apiUser.setUserPassword(encryptPassword);
            int rows = this.apiUserMapper.insert(apiUser);
            if (rows != 1) {
                throw new BasicException(ErrorCode.SYSTEM_ERROR);
            }
            return apiUser.getId();
        }
    }

    @Override
    public String userLogin(String userAccount, String userPassword) {
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userAccount + userPassword).getBytes());
        QueryWrapper<ApiUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);
        ApiUser apiUser = this.apiUserMapper.selectOne(queryWrapper);
        if (apiUser == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BasicException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }
        String token = UUID.randomUUID().toString(true);
        LoginUserVO loginUser = new LoginUserVO();
        BeanUtils.copyProperties(apiUser, loginUser);
        this.stringRedisTemplate.opsForValue().set(LOGIN_USER_KEY + token, JSONUtil.toJsonStr(loginUser), LOGIN_USER_TTL);
        return token;
    }
}