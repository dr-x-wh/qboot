package com.qboot.common.enums;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND, "未找到该数据"),

    ALREADY_EXISTS("ALREADY_EXISTS", HttpStatus.CONFLICT, "该数据已存在"),

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