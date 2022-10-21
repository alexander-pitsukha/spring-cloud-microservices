package com.alexander.movieservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MovieReview extends RepresentationModel<MovieReview> {

    private Long id;

    private Long movieId;

    private String review;

    private String authorName;

}
