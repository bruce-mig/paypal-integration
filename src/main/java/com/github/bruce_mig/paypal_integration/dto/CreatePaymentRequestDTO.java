package com.github.bruce_mig.paypal_integration.dto;

import lombok.Builder;

@Builder
public record CreatePaymentRequestDTO(
        Double total,
        String currency,
        String method,
        String intent,
        String description
) {
}
