package com.alexander.movieservice.model;

import com.alexander.movieservice.repository.Movie;
import lombok.Data;

import java.util.Collection;

@Data
public class MovieDto {
    private Long id;
    private String title;
    private String poster;
    private Collection<MovieReview> reviews;

    public MovieDto(Movie movie, Collection<MovieReview> reviews) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.poster = movie.getPoster();
        this.reviews = reviews;
    }

}
