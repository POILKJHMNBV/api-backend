package com.zhenwu.api.model.enums;

import lombok.Getter;

/**
 * @author zhenwu
 */
@Getter
public enum RoleEnum {
    ADMIN("admin"),
    CUSTOMER("user"),
    ;

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }
}
