package com.qnguyenxdev.product.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.qnguyenxdev.common.enums.ImageType;
import com.qnguyenxdev.common.exception.AppException;
import com.qnguyenxdev.common.enums.ErrorCode;
import com.qnguyenxdev.product.dto.response.UploadImageResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j(topic = "IMAGE-SERVICE")
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

    Cloudinary cloudinary;

    // Maximum file size for upload (10MB)
    private static final long MAX_FILE_UPLOAD_SIZE = 10 * 1024 * 1024; // 10MB

    public UploadImageResponse uploadFile(MultipartFile file, ImageType type, String productId) {

        validateImageFile(file);

        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String id = result.get("public_id").toString();
            String url = result.get("secure_url").toString();
            // TODO save them into product-image table

            return UploadImageResponse
                    .builder()
                    .publicId(id)
                    .url(url)
                    .imageType(type)
                    .build();
        } catch (Exception e) {
            log.error("Upload single image failed: {}", e.getMessage(), e.getCause());
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    public List<UploadImageResponse> uploadAll(List<MultipartFile> files, List<ImageType> types, String productId) {

        if (CollectionUtils.isEmpty(files) || CollectionUtils.isEmpty(types)) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }

        if (files.size() != types.size()) {
            throw new AppException(ErrorCode.MISMATCH_IMAGE_TYPE_COUNT);
        }

        for (MultipartFile file : files) {
            validateImageFile(file);
        }

        List<CompletableFuture<UploadImageResponse>> uploadImageTasks = IntStream.range(0, files.size())
                                                                        .mapToObj(i -> {
                                                                            MultipartFile file = files.get(i);
                                                                            ImageType type = types.get(i);
                                                                            return CompletableFuture.supplyAsync(() -> uploadFile(file, type, productId))
                                                                                                    .exceptionally(ex -> {
                                                                                                        throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                                                                                                    });
                                                                        }).toList();

        return uploadImageTasks
                .stream()
                .map(task -> {
                    try {
                        return task.join();
                    } catch (Exception e) {
                        log.error("Upload multiple images failed: {}", e.getMessage(), e.getCause());
                        throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
                    }
                })
                .toList();
    }

    public void delete(String imageId) {

        if (!StringUtils.hasLength(imageId)) {
            throw new AppException(ErrorCode.INVALID_IMAGE_ID);
        }

        try {
            Map<?, ?> result = cloudinary.uploader().destroy(imageId, ObjectUtils.emptyMap());

            if (!"ok".equals(result.get("result"))) {
                throw new AppException(ErrorCode.FILE_DELETE_FAILED);
            }
        } catch (Exception e) {
            log.error("Delete image failed: {}", e.getMessage(), e.getCause());
            throw new AppException(ErrorCode.FILE_DELETE_FAILED);
        }
    }

    private void validateImageFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_EMPTY_FILE);
        }

        if(file.getSize() > MAX_FILE_UPLOAD_SIZE) {
            throw new AppException(ErrorCode.FILE_TOO_LARGE);
        }

        String contentType = file.getContentType();
        if (!StringUtils.hasLength(contentType) || !contentType.startsWith("image/")) {
            throw new AppException(ErrorCode.INVALID_FILE_TYPE);
        }
    }
}
