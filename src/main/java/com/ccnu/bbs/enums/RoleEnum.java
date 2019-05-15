package com.ccnu.bbs.enums;


import lombok.Getter;

@Getter
public enum RoleEnum {

    USER(1,"用户"),
    ADMIN(2,"管理员")
    ;

    private Integer code;

    private String message;

    RoleEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
