package com.alexander.supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderClient orderClient;

    public void placeOrder(Order order) {
        log.info("Requesting order ms to place an order");
        orderClient.performOrder(order);
    }

}
