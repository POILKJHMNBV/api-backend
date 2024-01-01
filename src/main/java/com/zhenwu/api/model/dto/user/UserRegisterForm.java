package com.zhenwu.api.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author zhenwu
 */
@Data
@Schema(description = "用户注册表单类")
public class UserRegisterForm implements Serializable {
    private static final long serialVersionUID = 1362430110214209612L;

    /**
     * 账号
     */
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{2,20}$", message = "用户名内容不正确")
    @Schema(description = "用户名")
    private String userAccount;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "密码内容不正确")
    @Schema(description = "密码")
    private String userPassword;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "确认密码内容不正确")
    @Schema(description = "确认密码")
    private String checkPassword;
}
