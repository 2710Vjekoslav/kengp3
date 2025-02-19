package org.example.common.exception;

public enum ErrorCode {

    SYSTEM_ERROR(999, "系統繁忙，請稍後再試");

    private Integer code;

    private String message;

    ErrorCode(int code, String message) {
    }
}
