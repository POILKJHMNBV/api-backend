package com.zhenwu.api.model.dto.interfaceinfo;

import com.zhenwu.api.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zhenwu
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "查询接口信息表单类")
public class QueryInterfaceInfoForm extends PageRequest implements Serializable {

    private static final long serialVersionUID = 981649889913818629L;

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
    @Schema(description = "接口请求参数编码格式")
    private Integer interfaceRequestParamsCharset;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @Schema(description = "接口状态")
    private Integer interfaceStatus;

    /**
     * 接口请求方法类型
     */
    @Schema(description = "接口请求方法类型")
    private Integer interfaceRequestMethod;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private String createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private String updateTime;
}
