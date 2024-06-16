package com.coder.ecommerce.payment;

import com.coder.ecommerce.customer.CustomerResponse;
import com.coder.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
