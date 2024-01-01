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
    private static final long serialVersionUID = -5902907296904580183L;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
            && (this.getInterfaceName() == null ? other.getInterfaceName() == null : this.getInterfaceName().equals(other.getInterfaceName()))
            && (this.getInterfaceDescription() == null ? other.getInterfaceDescription() == null : this.getInterfaceDescription().equals(other.getInterfaceDescription()))
            && (this.getInterfaceUrl() == null ? other.getInterfaceUrl() == null : this.getInterfaceUrl().equals(other.getInterfaceUrl()))
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
        result = prime * result + ((getInterfaceName() == null) ? 0 : getInterfaceName().hashCode());
        result = prime * result + ((getInterfaceDescription() == null) ? 0 : getInterfaceDescription().hashCode());
        result = prime * result + ((getInterfaceUrl() == null) ? 0 : getInterfaceUrl().hashCode());
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
        sb.append(", interfaceName=").append(interfaceName);
        sb.append(", interfaceDescription=").append(interfaceDescription);
        sb.append(", interfaceUrl=").append(interfaceUrl);
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