package com.zhenwu.api.model.dto.interfaceinfo;

import com.zhenwu.api.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author zhenwu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "查询接口信息表单类")
public class QueryInterfaceInfoForm extends PageRequest implements Serializable {

    private static final long serialVersionUID = 981649889913818629L;

    @Schema(description = "接口id")
    private Long id;

    /**
     * 接口token
     */
    @Schema(description = "接口token")
    private String interfaceToken;

    /**
     * 接口名称
     */
    @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$", message = "接口名称内容不正确")
    @Schema(description = "接口名称")
    private String interfaceName;

    /**
     * 接口描述
     */
    @Length(min = 1, max = 256, message = "接口描述长度不可超过256个字符")
    @Schema(description = "接口描述")
    private String interfaceDescription;

    /**
     * 接口提供系统
     */
    @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$", message = "接口提供系统内容不正确")
    @Schema(description = "接口提供系统")
    private String interfaceVendor;

    /**
     * 接口提供系统名
     */
    @Length(min = 1, max = 256, message = "接口提供系统名长度不可超过256个字符")
    @Schema(description = "接口提供系统名")
    private String interfaceVendorName;

    /**
     * 访问主机
     */
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9./?]*$", message = "访问主机内容不正确")
    @Length(min = 1, max = 256, message = "访问主机长度不可超过256个字符")
    @Schema(description = "访问主机")
    private String interfaceHost;

    /**
     * 访问路径
     */
    @Pattern(regexp = "^[a-zA-Z0-9./?]*$", message = "访问路径内容不正确")
    @Length(min = 1, max = 256, message = "访问路径长度不可超过256个字符")
    @Schema(description = "访问路径")
    private String interfacePath;

    /**
     * 接口请求参数MIME类型
     */
    @Length(max = 128, message = "接口请求参数MIME类型长度不可超过128个字符")
    @Schema(description = "接口请求参数MIME类型")
    private String interfaceRequestParamsMime;

    /**
     * 接口请求参数编码格式
     */
    @Length(max = 10, message = "接口请求参数编码格式长度不可超过10个字符")
    @Schema(description = "接口请求参数编码格式")
    private String interfaceRequestParamsCharset;

    /**
     * 接口请求参数
     */
    @Length(max = 50000, message = "接口请求参数长度不可超过50000个字符")
    @Schema(description = "接口请求参数")
    private String interfaceRequestParams;

    /**
     * 接口请求头
     */
    @Length(max = 50000, message = "接口响应头长度不可超过50000个字符")
    @Schema(description = "接口响应头")
    private String interfaceRequestHeader;

    /**
     * 接口响应头
     */
    @Length(max = 50000, message = "接口响应头长度不可超过50000个字符")
    @Schema(description = "接口响应头")
    private String interfaceResponseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @Schema(description = "接口状态")
    private Integer interfaceStatus;

    /**
     * 接口请求方法类型
     */
    @Pattern(regexp = "^[A-Z]{3,10}$", message = "接口请求方法类型内容不正确")
    @Schema(description = "接口请求方法类型")
    private String interfaceRequestMethod;

    /**
     * 接口发布人
     */
    @Schema(description = "接口发布人")
    private Long interfacePublishUserid;

    /**
     * 接口是否删除(0-已删, 1-未删)
     */
    @Schema(description = "接口是否删除")
    private Integer interfaceDelete;
}
