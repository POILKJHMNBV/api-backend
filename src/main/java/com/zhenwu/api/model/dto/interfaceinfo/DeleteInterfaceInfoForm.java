package com.zhenwu.api.model.dto.interfaceinfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author zhenwu
 */
@Data
@Schema(description = "删除接口信息表单类")
public class DeleteInterfaceInfoForm {

    @Schema(description = "接口id数组")
    private Long[] ids;
}
