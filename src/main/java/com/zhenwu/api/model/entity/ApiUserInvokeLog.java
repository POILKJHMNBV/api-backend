package com.zhenwu.api.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户调用接口日志表
 * @TableName api_user_invoke_log
 */
@TableName(value ="api_user_invoke_log")
@Data
public class ApiUserInvokeLog implements Serializable {

    @TableField(exist = false)
    private static final long serialVersionUID = 8067688508420454141L;
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 操作用户id
     */
    private Long userId;

    /**
     * 调用接口id
     */
    private Long interfaceInfoId;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * 请求参数
     */
    private String requestParameter;

    /**
     * 响应状态
     */
    private Integer responseStatus;

    /**
     * 耗时 单位：ms
     */
    private Integer costTime;

    /**
     * 错误原因
     */
    private String errorReason;

    /**
     * 创建时间
     */
    private Date createTime;

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
        ApiUserInvokeLog other = (ApiUserInvokeLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getInterfaceInfoId() == null ? other.getInterfaceInfoId() == null : this.getInterfaceInfoId().equals(other.getInterfaceInfoId()))
            && (this.getRequestIp() == null ? other.getRequestIp() == null : this.getRequestIp().equals(other.getRequestIp()))
            && (this.getRequestParameter() == null ? other.getRequestParameter() == null : this.getRequestParameter().equals(other.getRequestParameter()))
            && (this.getResponseStatus() == null ? other.getResponseStatus() == null : this.getResponseStatus().equals(other.getResponseStatus()))
            && (this.getCostTime() == null ? other.getCostTime() == null : this.getCostTime().equals(other.getCostTime()))
            && (this.getErrorReason() == null ? other.getErrorReason() == null : this.getErrorReason().equals(other.getErrorReason()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getInterfaceInfoId() == null) ? 0 : getInterfaceInfoId().hashCode());
        result = prime * result + ((getRequestIp() == null) ? 0 : getRequestIp().hashCode());
        result = prime * result + ((getRequestParameter() == null) ? 0 : getRequestParameter().hashCode());
        result = prime * result + ((getResponseStatus() == null) ? 0 : getResponseStatus().hashCode());
        result = prime * result + ((getCostTime() == null) ? 0 : getCostTime().hashCode());
        result = prime * result + ((getErrorReason() == null) ? 0 : getErrorReason().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", interfaceInfoId=").append(interfaceInfoId);
        sb.append(", requestIp=").append(requestIp);
        sb.append(", requestParameter=").append(requestParameter);
        sb.append(", responseStatus=").append(responseStatus);
        sb.append(", costTime=").append(costTime);
        sb.append(", errorReason=").append(errorReason);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}