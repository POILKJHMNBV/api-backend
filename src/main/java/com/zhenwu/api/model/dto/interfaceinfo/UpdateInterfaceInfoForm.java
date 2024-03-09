package com.zhenwu.api.model.dto.interfaceinfo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author zhenwu
 */
@Data
@Schema(description = "更新接口信息表单类")
public class UpdateInterfaceInfoForm implements Serializable {

    private static final long serialVersionUID = -3853108416014289044L;

    @Schema(description = "接口id")
    private Long id;

    /**
     * 接口名称
     */
    @NotBlank(message = "接口名称不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$", message = "接口名称内容不正确")
    @Schema(description = "接口名称")
    private String interfaceName;

    /**
     * 接口描述
     */
    @NotBlank(message = "接口描述不能为空")
    @Length(min = 1, max = 256, message = "接口描述长度不可超过256个字符")
    @Schema(description = "接口描述")
    private String interfaceDescription;

    /**
     * 接口提供系统
     */
    @NotBlank(message = "接口提供系统不能为空")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,100}$", message = "接口提供系统内容不正确")
    @Schema(description = "接口提供系统")
    private String interfaceVendor;

    /**
     * 接口提供系统名
     */
    @NotBlank(message = "接口提供系统名不能为空")
    @Length(min = 1, max = 256, message = "接口提供系统名长度不可超过256个字符")
    @Schema(description = "接口提供系统名")
    private String interfaceVendorName;

    /**
     * 访问主机
     */
    @NotBlank(message = "访问主机不能为空")
    @Pattern(regexp = "^(http|https)://[a-zA-Z0-9./?:]*$", message = "访问主机内容不正确")
    @Length(min = 1, max = 256, message = "访问主机长度不可超过256个字符")
    @Schema(description = "访问主机")
    private String interfaceHost;

    /**
     * 访问路径
     */
    @NotBlank(message = "访问路径不能为空")
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
    @Schema(description = "接口请求参数编码格式")
    private Integer interfaceRequestParamsCharset;

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
     * 接口请求方法类型
     */
    @Schema(description = "接口请求方法类型")
    private Integer interfaceRequestMethod;
}
