package com.zhenwu.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhenwu.api.model.dto.user.QueryUserInfoForm;
import com.zhenwu.api.model.entity.ApiUser;

/**
* @author zhenwu
* @description 针对表【api_user(用户表)】的数据库操作Service
*/
public interface ApiUserService extends IService<ApiUser> {

    /**
     * 生成验证码
     * @param userAccount 用户账户
     * @param operate   操作 0-用户注册     1-用户登录
     * @return 验证码
     */
    String generateVerificationCode(String userAccount, int operate);

    /**
     * 用户注册
     * @param verificationCode 验证码
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String verificationCode, String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     * @param verificationCode 验证码
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param userLoginIp 用户登录ip
     * @return token
     */
    String userLogin(String verificationCode, String userAccount, String userPassword, String userLoginIp);

    /**
     * 退出登录
     * @param token 用户登录token
     */
    void logout(String token);

    /**
     * 用户下线
     * @param ids 用户账户数组
     * @return 用户是否下线成功
     */
    boolean offline(Long[] ids);

    /**
     * 分页查询接口信息
     * @param queryUserInfoForm 查询条件
     * @return 该页的用户信息
     */
    Page<ApiUser> listUserInfoByPage(QueryUserInfoForm queryUserInfoForm);

    /**
     * 删除用户信息
     * @param ids 用户id数组
     * @return 用户是否删除成功
     */
    long deleteUserInfo(Long[] ids);

    /**
     * 禁用用户
     * @param id 用户id
     * @return 用户是否禁用成功
     */
    boolean forbidUser(long id);

    /**
     * 启用用户
     * @param id 用户id
     * @return 用户是否启用成功
     */
    boolean permitUser(long id);
}
