package com.qnguyenxdev.product.controller;

import com.qnguyenxdev.common.dto.ApiResponse;
import com.qnguyenxdev.product.dto.request.ProductCreateRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    @GetMapping("")
    public ApiResponse<String> getAllProducts() {
        return ApiResponse.<String>builder()
                          .status(200)
                          .message("Success")
                          .data("List of all products")
                          .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<String> getProductById(@PathVariable("id") String productId) {
        return ApiResponse.<String>builder()
                          .status(200)
                          .message("Success")
                          .data("List of all products")
                          .build();
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<String> insertProduct(
            @Valid @RequestBody ProductCreateRequest request
    ) {
        return ApiResponse.<String>builder()
                          .status(200)
                          .message("Success")
                          .data("List of all products")
                          .build();
    }
}
