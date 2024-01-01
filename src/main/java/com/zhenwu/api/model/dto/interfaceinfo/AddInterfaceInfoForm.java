package com.zhenwu.api.model.dto.interfaceinfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhenwu
 */
@Data
@Schema(description = "查询接口信息表单类")
public class AddInterfaceInfoForm implements Serializable {

    private static final long serialVersionUID = -6465430956402963660L;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口描述
     */
    private String interfaceDescription;

    /**
     * 接口地址
     */
    private String interfaceUrl;

    /**
     * 接口请求参数
     */
    private String interfaceRequestParams;

    /**
     * 接口请求头
     */
    private String interfaceRequestHeader;

    /**
     * 接口响应头
     */
    private String interfaceResponseHeader;


    /**
     * 接口请求方法类型
     */
    private String interfaceRequestMethod;
}
