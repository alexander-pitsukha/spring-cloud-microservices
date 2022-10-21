package com.alexander.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilterFactory extends AbstractGatewayFilterFactory<Object> {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    public AuthenticationFilterFactory() {
        super(Object.class);
    }

    @Override
    public GatewayFilter apply(Object config) {
        return authenticationFilter;
    }

}
