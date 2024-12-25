package com.alexander.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final AuthenticationFilter authenticationFilter;

    @Autowired
    public AuthenticationFilterFactory(AuthenticationFilter authenticationFilter) {
        super(Object.class);
        this.authenticationFilter = authenticationFilter;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return authenticationFilter;
    }

}
