package com.qnguyenxdev.product.controller;

import com.qnguyenxdev.common.dto.ApiResponse;
import com.qnguyenxdev.product.dto.request.CategoryCreateRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@Validated
public class CategoryController {

    @GetMapping("")
    public ApiResponse<String> getAllCategories() {
        return ApiResponse.<String>builder()
                .status(200)
                .message("Success")
                .data("List of all categories")
                .build();
    }

    @PostMapping("")
    public ApiResponse<String> insertCategory(@Valid @RequestBody CategoryCreateRequest request) {
        return ApiResponse.<String>builder()
                          .status(200)
                          .message("Success")
                          .data("List of all categories")
                          .build();
    }
}
