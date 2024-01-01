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
     * 用户昵称
     */
    private String userName;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
