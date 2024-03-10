package com.zhenwu.api.model.dto.user;

import com.zhenwu.api.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhenwu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "查询用户信息表单类")
public class QueryUserInfoForm extends PageRequest implements Serializable  {

    private static final long serialVersionUID = -7670276698097095968L;

    /**
     * 账号
     */
    @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$", message = "用户账号内容不正确")
    @Schema(description = "用户账号")
    private String userAccount;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称")
    private String userName;

    /**
     * 用户邮箱
     */
    @Schema(description = "用户邮箱")
    private String userEmail;

    /**
     * 用户电话
     */
    @Schema(description = "用户电话")
    private String userPhone;

    /**
     * 用户状态 0-正常 1-已删除 2-已禁用
     */
    @Schema(description = "用户状态")
    private Integer userStatus;

    /**
     * 用户登录ip
     */
    @Schema(description = "用户登录ip")
    private String userLoginIp;

    /**
     * 用户登录时间
     */
    @Schema(description = "用户登录时间")
    private Date userLoginTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private Date updateTime;
}
