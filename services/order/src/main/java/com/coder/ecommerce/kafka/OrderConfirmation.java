package com.coder.ecommerce.kafka;

import com.coder.ecommerce.customer.CustomerResponse;
import com.coder.ecommerce.order.PaymentMethod;
import com.coder.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
