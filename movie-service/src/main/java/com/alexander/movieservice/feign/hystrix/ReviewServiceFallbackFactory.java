package com.alexander.movieservice.feign.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import com.alexander.movieservice.feign.ReviewsFeignClient;

import java.util.Collections;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 * This allows access to the underlying exception that broke the circuit
 */
@Component
public class ReviewServiceFallbackFactory implements FallbackFactory<ReviewsFeignClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceFallbackFactory.class);

    @Override
    public ReviewsFeignClient create(Throwable throwable) {
        return movieId -> {
            LOGGER.error("Error occurred trying to fetch reviews from review service", throwable);
            return CollectionModel.empty(Collections.emptyList());
        };
    }

}
