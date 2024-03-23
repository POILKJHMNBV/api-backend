package com.zhenwu.api.model.enums;

import lombok.Getter;

/**
 * @author zhenwu
 */
@Getter
public enum RequestMethodEnum {
    GET(0, "GET"),
    POST(1, "POST"),
    DELETE(2, "DELETE"),
    PUT(3, "PUT"),
    PATCH(4, "PATCH"),
    ;

    private final int code;
    private final String description;

    RequestMethodEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}