package com.qnguyenxdev.product.dto.response;

import com.qnguyenxdev.common.enums.ImageType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UploadImageResponse implements Serializable {

    String publicId;
    String url;
    ImageType imageType;
}
