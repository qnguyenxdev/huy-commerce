package com.qnguyenxdev.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PageResponse<T> implements Serializable {

    int currentPage;
    int totalPages;
    int pageSize;
    long totalElements;

    @Builder.Default
    private List<T> data = Collections.emptyList();
}
