package com.zhenwu.api.model.dto.interfaceinfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhenwu
 */
@Data
@Schema(description = "测试调用目标接口表单类")
public class InvokeInterfaceForm implements Serializable {

    private static final long serialVersionUID = 287588515633397283L;

    private Long id;

    /**
     * 用户上送的参数
     */
    private String userRequestParam;
}
