package com.zhenwu.api.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhenwu
 */
@Data
public class LoginUserVO implements Serializable {

    private static final long serialVersionUID = 1967482833909493062L;

    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户状态 0-正常 1-已删除 2-已禁用
     */
    private Integer userStatus;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户电话
     */
    private String userPhone;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user-普通用户  admin-管理员
     */
    private String userRole;

    /**
     * 访问密钥
     */
    private String userAccesskey;

    /**
     * 加密密钥
     */
    private String userSecretkey;

    /**
     * 用户公钥
     */
    private String userPublickey;
}
