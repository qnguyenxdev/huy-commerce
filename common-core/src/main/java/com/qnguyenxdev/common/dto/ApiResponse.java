package com.qnguyenxdev.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ApiResponse<T> implements Serializable {

    int status;
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    T data;
}
