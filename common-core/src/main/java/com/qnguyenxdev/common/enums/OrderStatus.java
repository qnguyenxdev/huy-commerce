package com.qnguyenxdev.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status of a customer order")
public enum OrderStatus {

    @Schema(description = "Order has been placed but not yet processed")
    PENDING,

    @Schema(description = "Order is currently being prepared or packed")
    PROCESSING,

    @Schema(description = "Order has been shipped and is in transit")
    SHIPPED,

    @Schema(description = "Order has been delivered to the customer")
    DELIVERED,

    @Schema(description = "Order was cancelled before shipment")
    CANCELLED,

    @Schema(description = "Customer returned the order")
    RETURNED
}
