package com.zhenwu.api.service;

import com.zhenwu.api.model.entity.ApiUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zhenwu
* @description 针对表【api_user(用户表)】的数据库操作Service
*/
public interface ApiUserService extends IService<ApiUser> {

    /**
     * 用户注册
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param userLoginIp 用户登录ip
     * @return token
     */
    String userLogin(String userAccount, String userPassword, String userLoginIp);

    /**
     * 退出登录
     * @param token 用户登录token
     */
    void logout(String token);
}
