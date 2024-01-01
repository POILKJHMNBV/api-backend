package com.zhenwu.api.exception;

import com.zhenwu.api.common.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhenwu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasicException extends RuntimeException {

    private static final long serialVersionUID = -7489111036689610161L;

    private final int code;

    public BasicException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BasicException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }
}
