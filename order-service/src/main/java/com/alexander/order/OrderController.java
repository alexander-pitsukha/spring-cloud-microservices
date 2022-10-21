package com.alexander.order;

import com.alexander.util.RoleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("orders")
@Slf4j
public class OrderController {

    @PostMapping
    ResponseEntity<HttpStatus> placeOrder(@RequestBody Order order, HttpServletRequest request) {
        String userRole = request.getHeader("userRole");
        if (RoleUtil.isAdminRole(userRole)) {
            log.info("userId - {}, userRole - {}", request.getHeader("userId"), request.getHeader("userRole"));
        }
        log.info("Placing an order for product: {} with quantity: {}", order.getProductName(), order.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("message")
    public String test() {
        return "Hello JavaInUse Called in Order Service";
    }

}