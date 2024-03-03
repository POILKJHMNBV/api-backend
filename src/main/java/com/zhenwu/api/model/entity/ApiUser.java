package com.zhenwu.api.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * @TableName api_user
 */
@TableName(value ="api_user")
@Data
public class ApiUser implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = -8061305742869978541L;
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户昵称
     */
    private String userName;

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
     * 用户角色：0-普通用户  1-管理员
     */
    private Integer userRole;

    /**
     * 用户状态 0-正常 1-已删除 2-已禁用
     */
    private Integer userStatus;

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

    /**
     * 用户私钥
     */
    private String userPrivatekey;

    /**
     * 用户登录ip
     */
    private String userLoginIp;

    /**
     * 用户登录时间
     */
    private Date userLoginTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApiUser apiUser = (ApiUser) o;

        if (!id.equals(apiUser.id)) return false;
        if (!userAccount.equals(apiUser.userAccount)) return false;
        if (!userPassword.equals(apiUser.userPassword)) return false;
        if (!userName.equals(apiUser.userName)) return false;
        if (!userEmail.equals(apiUser.userEmail)) return false;
        if (!userPhone.equals(apiUser.userPhone)) return false;
        if (!userAvatar.equals(apiUser.userAvatar)) return false;
        if (!userProfile.equals(apiUser.userProfile)) return false;
        if (!userRole.equals(apiUser.userRole)) return false;
        if (!userStatus.equals(apiUser.userStatus)) return false;
        if (!userAccesskey.equals(apiUser.userAccesskey)) return false;
        if (!userSecretkey.equals(apiUser.userSecretkey)) return false;
        if (!userPublickey.equals(apiUser.userPublickey)) return false;
        if (!userPrivatekey.equals(apiUser.userPrivatekey)) return false;
        if (!userLoginIp.equals(apiUser.userLoginIp)) return false;
        if (!userLoginTime.equals(apiUser.userLoginTime)) return false;
        if (!createTime.equals(apiUser.createTime)) return false;
        return updateTime.equals(apiUser.updateTime);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userAccount.hashCode();
        result = 31 * result + userPassword.hashCode();
        result = 31 * result + userName.hashCode();
        result = 31 * result + userEmail.hashCode();
        result = 31 * result + userPhone.hashCode();
        result = 31 * result + userAvatar.hashCode();
        result = 31 * result + userProfile.hashCode();
        result = 31 * result + userRole.hashCode();
        result = 31 * result + userStatus.hashCode();
        result = 31 * result + userAccesskey.hashCode();
        result = 31 * result + userSecretkey.hashCode();
        result = 31 * result + userPublickey.hashCode();
        result = 31 * result + userPrivatekey.hashCode();
        result = 31 * result + userLoginIp.hashCode();
        result = 31 * result + userLoginTime.hashCode();
        result = 31 * result + createTime.hashCode();
        result = 31 * result + updateTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userAccount=").append(userAccount);
        sb.append(", userPassword=").append(userPassword);
        sb.append(", userName=").append(userName);
        sb.append(", userEmail=").append(userEmail);
        sb.append(", userPhone=").append(userPhone);
        sb.append(", userAvatar=").append(userAvatar);
        sb.append(", userProfile=").append(userProfile);
        sb.append(", userRole=").append(userRole);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", userAccesskey=").append(userAccesskey);
        sb.append(", userSecretkey=").append(userSecretkey);
        sb.append(", userPublickey=").append(userPublickey);
        sb.append(", userPrivatekey=").append(userPrivatekey);
        sb.append(", userLoginIp=").append(userLoginIp);
        sb.append(", userLoginTime=").append(userLoginTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}