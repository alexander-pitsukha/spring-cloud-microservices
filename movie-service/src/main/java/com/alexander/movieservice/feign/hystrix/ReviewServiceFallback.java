package com.alexander.movieservice.feign.hystrix;

import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;
import com.alexander.movieservice.feign.ReviewsFeignClient;
import com.alexander.movieservice.model.MovieReview;

import java.util.Collections;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 */
@Component
public class ReviewServiceFallback implements ReviewsFeignClient {

    @Override
    public CollectionModel<MovieReview> getMovieReviews(Long movieId) {
        return CollectionModel.empty(Collections.emptyList());
    }

}
