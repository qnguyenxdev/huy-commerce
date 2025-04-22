package com.qnguyenxdev.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "error.global.uncategorized.error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "error.global.user.existed", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(1002, "error.global.invalid.resource.not.found", HttpStatus.NOT_FOUND),
    FILE_TOO_LARGE(1003, "error.global.file.too.large", HttpStatus.PAYLOAD_TOO_LARGE),
    INVALID_FILE_TYPE(1004, "error.global.invalid.file.type", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    INVALID_EMPTY_FILE(1005, "error.global.invalid.empty.file", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED(1006, "error.global.file.upload.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_IMAGE_ID(1006, "error.global.invalid.image.id", HttpStatus.BAD_REQUEST),
    FILE_DELETE_FAILED(1006, "error.global.file.delete.failed", HttpStatus.INTERNAL_SERVER_ERROR),
    MISMATCH_IMAGE_TYPE_COUNT(1007, "error.global.mismatch.image.type.count", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private final int code;
    private final String message;
    private final HttpStatus status;
}
