package com.zhenwu.api.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 接口信息表
 * @TableName api_interface_info
 */
@TableName(value ="api_interface_info")
@Data
public class ApiInterfaceInfo implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 1191942173239014400L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接口token
     */
    private String interfaceToken;

    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 接口描述
     */
    private String interfaceDescription;

    /**
     * 接口提供系统
     */
    private String interfaceVendor;

    /**
     * 接口提供系统名
     */
    private String interfaceVendorName;

    /**
     * 访问主机
     */
    private String interfaceHost;

    /**
     * 访问路径
     */
    private String interfacePath;

    /**
     * 接口请求参数MIME类型
     */
    private String interfaceRequestParamsMime;

    /**
     * 接口请求参数编码格式
     */
    private String interfaceRequestParamsCharset;

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
     * 接口是否删除(0-已删, 1-未删)
     */
    private Integer interfaceDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApiInterfaceInfo other = (ApiInterfaceInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInterfaceToken() == null ? other.getInterfaceToken() == null : this.getInterfaceToken().equals(other.getInterfaceToken()))
            && (this.getInterfaceName() == null ? other.getInterfaceName() == null : this.getInterfaceName().equals(other.getInterfaceName()))
            && (this.getInterfaceDescription() == null ? other.getInterfaceDescription() == null : this.getInterfaceDescription().equals(other.getInterfaceDescription()))
            && (this.getInterfaceVendor() == null ? other.getInterfaceVendor() == null : this.getInterfaceVendor().equals(other.getInterfaceVendor()))
            && (this.getInterfaceVendorName() == null ? other.getInterfaceVendorName() == null : this.getInterfaceVendorName().equals(other.getInterfaceVendorName()))
            && (this.getInterfaceHost() == null ? other.getInterfaceHost() == null : this.getInterfaceHost().equals(other.getInterfaceHost()))
            && (this.getInterfacePath() == null ? other.getInterfacePath() == null : this.getInterfacePath().equals(other.getInterfacePath()))
            && (this.getInterfaceRequestParamsMime() == null ? other.getInterfaceRequestParamsMime() == null : this.getInterfaceRequestParamsMime().equals(other.getInterfaceRequestParamsMime()))
            && (this.getInterfaceRequestParamsCharset() == null ? other.getInterfaceRequestParamsCharset() == null : this.getInterfaceRequestParamsCharset().equals(other.getInterfaceRequestParamsCharset()))
            && (this.getInterfaceRequestParams() == null ? other.getInterfaceRequestParams() == null : this.getInterfaceRequestParams().equals(other.getInterfaceRequestParams()))
            && (this.getInterfaceRequestHeader() == null ? other.getInterfaceRequestHeader() == null : this.getInterfaceRequestHeader().equals(other.getInterfaceRequestHeader()))
            && (this.getInterfaceResponseHeader() == null ? other.getInterfaceResponseHeader() == null : this.getInterfaceResponseHeader().equals(other.getInterfaceResponseHeader()))
            && (this.getInterfaceStatus() == null ? other.getInterfaceStatus() == null : this.getInterfaceStatus().equals(other.getInterfaceStatus()))
            && (this.getInterfaceRequestMethod() == null ? other.getInterfaceRequestMethod() == null : this.getInterfaceRequestMethod().equals(other.getInterfaceRequestMethod()))
            && (this.getInterfacePublishUserid() == null ? other.getInterfacePublishUserid() == null : this.getInterfacePublishUserid().equals(other.getInterfacePublishUserid()))
            && (this.getInterfaceDelete() == null ? other.getInterfaceDelete() == null : this.getInterfaceDelete().equals(other.getInterfaceDelete()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInterfaceToken() == null) ? 0 : getInterfaceToken().hashCode());
        result = prime * result + ((getInterfaceName() == null) ? 0 : getInterfaceName().hashCode());
        result = prime * result + ((getInterfaceDescription() == null) ? 0 : getInterfaceDescription().hashCode());
        result = prime * result + ((getInterfaceVendor() == null) ? 0 : getInterfaceVendor().hashCode());
        result = prime * result + ((getInterfaceVendorName() == null) ? 0 : getInterfaceVendorName().hashCode());
        result = prime * result + ((getInterfaceHost() == null) ? 0 : getInterfaceHost().hashCode());
        result = prime * result + ((getInterfacePath() == null) ? 0 : getInterfacePath().hashCode());
        result = prime * result + ((getInterfaceRequestParamsMime() == null) ? 0 : getInterfaceRequestParamsMime().hashCode());
        result = prime * result + ((getInterfaceRequestParamsCharset() == null) ? 0 : getInterfaceRequestParamsCharset().hashCode());
        result = prime * result + ((getInterfaceRequestParams() == null) ? 0 : getInterfaceRequestParams().hashCode());
        result = prime * result + ((getInterfaceRequestHeader() == null) ? 0 : getInterfaceRequestHeader().hashCode());
        result = prime * result + ((getInterfaceResponseHeader() == null) ? 0 : getInterfaceResponseHeader().hashCode());
        result = prime * result + ((getInterfaceStatus() == null) ? 0 : getInterfaceStatus().hashCode());
        result = prime * result + ((getInterfaceRequestMethod() == null) ? 0 : getInterfaceRequestMethod().hashCode());
        result = prime * result + ((getInterfacePublishUserid() == null) ? 0 : getInterfacePublishUserid().hashCode());
        result = prime * result + ((getInterfaceDelete() == null) ? 0 : getInterfaceDelete().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", interfaceToken=").append(interfaceToken);
        sb.append(", interfaceName=").append(interfaceName);
        sb.append(", interfaceDescription=").append(interfaceDescription);
        sb.append(", interfaceVendor=").append(interfaceVendor);
        sb.append(", interfaceVendorName=").append(interfaceVendorName);
        sb.append(", interfaceHost=").append(interfaceHost);
        sb.append(", interfacePath=").append(interfacePath);
        sb.append(", interfaceRequestParamsMime=").append(interfaceRequestParamsMime);
        sb.append(", interfaceRequestParamsCharset=").append(interfaceRequestParamsCharset);
        sb.append(", interfaceRequestParams=").append(interfaceRequestParams);
        sb.append(", interfaceRequestHeader=").append(interfaceRequestHeader);
        sb.append(", interfaceResponseHeader=").append(interfaceResponseHeader);
        sb.append(", interfaceStatus=").append(interfaceStatus);
        sb.append(", interfaceRequestMethod=").append(interfaceRequestMethod);
        sb.append(", interfacePublishUserid=").append(interfacePublishUserid);
        sb.append(", interfaceDelete=").append(interfaceDelete);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}