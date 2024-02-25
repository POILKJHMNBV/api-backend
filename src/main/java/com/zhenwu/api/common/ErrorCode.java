package com.zhenwu.api.common;

import lombok.Getter;

/**
 * @author zhenwu
 */
@Getter
public enum ErrorCode {
    SUCCESS(200, "ok"),
    PARAMS_ERROR(400, "请求参数错误"),
    UNAUTHORIZED(401, "未登录或token失效"),
    NOT_FOUND_ERROR(404, "请求数据不存在"),
    FORBIDDEN_ERROR(403, "禁止访问"),
    SYSTEM_ERROR(500, "系统内部异常");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
