package com.qnguyenxdev.product.controller;

import com.qnguyenxdev.common.dto.ApiResponse;
import com.qnguyenxdev.common.enums.ImageType;
import com.qnguyenxdev.product.dto.request.ProductCreateRequest;
import com.qnguyenxdev.product.dto.response.UploadImageResponse;
import com.qnguyenxdev.product.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
@RequiredArgsConstructor
@Tag(name = "Product Controller")
@Slf4j(topic = "PRODUCT-CONTROLLER")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

    private final ImageService imageService;

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
                          .data("Found product with ID: " + productId)
                          .build();
    }

    @PostMapping(value = "")
    public ApiResponse<String> insertProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ApiResponse.<String>builder()
                          .status(200)
                          .message("Success")
                          .data("Insert product successfully")
                          .build();
    }

    @Operation(summary = "Upload Product Image", description = "Upload a product image with its type")
    @PostMapping(value = "/media/upload")
    public ApiResponse<UploadImageResponse> uploadProductImage(
            @RequestParam("file") MultipartFile image,
            @Parameter(description = "Type of image being uploaded")
            @RequestParam("type") ImageType type,
            @RequestParam("productId") String productId
    ) {
        log.info("Upload image");

        UploadImageResponse res = imageService.uploadFile(image, type, productId);
        return ApiResponse.<UploadImageResponse>builder()
                          .status(200)
                          .message("Success")
                          .data(res)
                          .build();
    }

    @Operation(summary = "Upload Multiple Product Images", description = "Upload multiple product images with their types")
    @PostMapping(value = "/media/upload-multiple")
    public ApiResponse<List<UploadImageResponse>> uploadMultipleProductImages(
            @RequestParam("files") List<MultipartFile> images,
            @Parameter(description = "Types of images being uploaded")
            @RequestParam("types") List<ImageType> types,
            @RequestParam("productId") String productId
    ) {
        log.info("Upload multiple image");

        List<UploadImageResponse> res = imageService.uploadAll(images, types, productId);
        return ApiResponse.<List<UploadImageResponse>>builder()
                          .status(200)
                          .message("Success")
                          .data(res)
                          .build();
    }

    @Operation(summary = "Delete Product Image", description = "Delete a product image by its ID")
    @DeleteMapping(value = "/media/delete/{id}")
    public ApiResponse<Void> deleteProductImage(@PathVariable("id") String imageId) {
        log.info("delete image");

        imageService.delete(imageId);
        return ApiResponse.<Void>builder()
                          .status(200)
                          .message("Success")
                          .build();
    }
}
