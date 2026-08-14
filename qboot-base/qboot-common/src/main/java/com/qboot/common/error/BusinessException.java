package com.qboot.common.error;

import com.qboot.common.enums.ErrorCode;

import java.util.Objects;

public final class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, null);
    }

    public BusinessException(ErrorCode errorCode, String detail) {
        super(resolveDetail(errorCode, detail));
        this.errorCode = errorCode;
    }

    private static String resolveDetail(ErrorCode errorCode, String detail) {
        Objects.requireNonNull(errorCode, "errorCode must not be null");
        return detail == null || detail.isBlank() ? errorCode.defaultMessage() : detail;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
