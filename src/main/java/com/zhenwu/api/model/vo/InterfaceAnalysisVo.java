package com.zhenwu.api.model.vo;

import lombok.Data;

/**
 * @author zhenwu
 */
@Data
public class InterfaceAnalysisVo {

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口调用次数
     */
    private Integer invokeNum;
}