package com.zhenwu.api.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhenwu
 */
@Data
@Schema(description = "删除用户信息表单类")
public class DeleteUserInfoForm {

    @Schema(description = "用户id数组")
    Long[] ids;
}
