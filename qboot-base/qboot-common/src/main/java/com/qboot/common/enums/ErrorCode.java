package com.qboot.common.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    USER_NOT_FOUND("USER_NOT_FOUND", HttpStatus.NOT_FOUND, "用户不存在"),

    USER_ALREADY_EXISTS("USER_ALREADY_EXISTS", HttpStatus.CONFLICT, "用户已存在"),

    INVALID_OPERATION("INVALID_OPERATION", HttpStatus.CONFLICT, "当前状态不允许此操作");

    private final String code;
    private final HttpStatus status;
    private final String defaultMessage;

    public String code() {
        return code;
    }

    public HttpStatus status() {
        return status;
    }

    public String defaultMessage() {
        return defaultMessage;
    }
}