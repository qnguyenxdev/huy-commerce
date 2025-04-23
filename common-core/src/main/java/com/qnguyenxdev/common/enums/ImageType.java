package com.qnguyenxdev.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Supported types of product images")
public enum ImageType {
    @Schema(description = "Thumbnail image for product listings")
    THUMBNAIL,

    @Schema(description = "Main product image (e.g. front view)")
    MAIN,

    @Schema(description = "Additional gallery image")
    GALLERY,

    @Schema(description = "Zoomed-in detail shot")
    ZOOM,

    @Schema(description = "Image of the product packaging")
    PACKAGING
}
