package com.zhenwu.api.model.dto.interfaceinfo;

import com.zhenwu.api.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author zhenwu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "查询接口信息表单类")
public class QueryInterfaceInfoForm extends PageRequest implements Serializable {

    private static final long serialVersionUID = 981649889913818629L;

    private Long id;

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
     * 接口状态（0-关闭，1-开启）
     */
    private Integer interfaceStatus;

    /**
     * 接口请求方法类型
     */
    private String interfaceRequestMethod;

    /**
     * 接口发布人
     */
    private Long interfacePublishUserid;

    /**
     * 接口是否删除(0-未删, 1-已删)
     */
    private Integer interfaceDelete;
}
