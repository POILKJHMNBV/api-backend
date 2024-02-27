package com.zhenwu.api.config;

import com.zhenwu.api.common.ErrorCode;
import com.zhenwu.api.common.Result;
import com.zhenwu.api.exception.BasicException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhenwu
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public <T> Result<T> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMsg = fieldError == null ? ErrorCode.PARAMS_ERROR.getMessage() : fieldError.getDefaultMessage();
        return Result.failure(ErrorCode.PARAMS_ERROR.getCode(), errorMsg);
    }

    @ExceptionHandler(BasicException.class)
    public <T> Result<T> basicExceptionHandler(BasicException e) {
        log.error("basicException: " + e.getMessage(), e);
        return Result.failure(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public <T> Result<T> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return Result.failure(ErrorCode.SYSTEM_ERROR.getCode(), ErrorCode.SYSTEM_ERROR.getMessage());
    }
}