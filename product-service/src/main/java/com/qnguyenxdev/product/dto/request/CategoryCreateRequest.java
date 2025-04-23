package com.qnguyenxdev.product.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CategoryCreateRequest implements Serializable {

    @NotEmpty(message = "{validation.category.name.not-empty}")
    String name;
}
