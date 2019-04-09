package com.ccnu.bbs.enums;

import lombok.Getter;

@Getter
public enum  ResultEnum {

    SUCCESS(0, "成功"),
    CODE_ERROR(1, "未获取到code"),
    SESSION_ERROR(2, "获取sessionKey和openid失败"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
