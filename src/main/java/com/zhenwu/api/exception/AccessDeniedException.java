package com.zhenwu.api.exception;

import com.zhenwu.api.common.ErrorCode;

/**
 * @author zhenwu
 */
public class AccessDeniedException extends BasicException {

    private static final long serialVersionUID = -8630741624734558927L;

    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccessDeniedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
