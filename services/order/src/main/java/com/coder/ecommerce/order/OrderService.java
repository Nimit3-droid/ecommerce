package com.coder.ecommerce.order;

import com.coder.ecommerce.customer.CustomerClient;
import com.coder.ecommerce.exception.BusinessException;
import com.coder.ecommerce.orderline.OrderLineRequest;
import com.coder.ecommerce.orderline.OrderLineService;
import com.coder.ecommerce.product.ProductClient;
import com.coder.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createOrder(OrderRequest orderRequest) {
        // check if the customer exists using OpenFeign client
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(()->new BusinessException("Customer not found"));

        // purchase the items using product microservice and using rest template
        this.productClient.purchaseProducts(orderRequest.products());

        // persist the order in the database
        var order= this.orderRepository.save(mapper.toOrder(orderRequest));

        // persist the order lines in the database
        for(PurchaseRequest purchaseRequest: orderRequest.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    ) 
            );
        }

        // start the payment process
        // send notification to the customer using kafka notification service
        return 1;
    }
}
