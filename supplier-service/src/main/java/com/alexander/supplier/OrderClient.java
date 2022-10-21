package com.alexander.supplier;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("order-service")
public interface OrderClient {

    @PostMapping("orders")
    void performOrder(@RequestBody Order order);

}
