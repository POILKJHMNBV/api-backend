package com.zhenwu.api.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.http.HttpStatus;

/**
 * @author zhenwu
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public final class Result<T> {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 响应消息
     */
    private String message;

    private final Integer pageSize = 20;
    private final Integer current = 1;

    private Result() {
        this.code = HttpStatus.SC_OK;
        this.message = "success";
    }

    private Result(T data) {
        this.code = HttpStatus.SC_OK;
        this.message = "success";
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> failure() {
        return new Result<>(HttpStatus.SC_INTERNAL_SERVER_ERROR, "failure");
    }

    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message);
    }
}
