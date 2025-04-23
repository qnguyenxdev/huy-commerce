package com.qnguyenxdev.product.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductCreateRequest implements Serializable {

    @NotBlank(message = "{validation.product.name.not-empty}")
    @Size(min = 3, max = 200, message = "{validation.product.name.size}")
    String name;

    @Min(value = 0, message = "{validation.product.price.min}")
    @Max(value = 100000000, message = "{validation.product.price.max}")
    Float price;
    String description;

    @JsonProperty("category_id")
    String categoryId;
}
