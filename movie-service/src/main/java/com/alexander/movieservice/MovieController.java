package com.alexander.movieservice;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alexander.movieservice.feign.ReviewsFeignClient;
import com.alexander.movieservice.model.MovieDto;
import com.alexander.movieservice.model.MovieReview;
import com.alexander.movieservice.repository.Movie;
import com.alexander.movieservice.repository.MovieRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("movies")
public class MovieController {
    private final MovieRepository movieRepository;
    private final ReviewsFeignClient reviewsFeignClient;

    public MovieController(MovieRepository movieRepository, ReviewsFeignClient reviewsFeignClient) {
        this.movieRepository = movieRepository;
        this.reviewsFeignClient = reviewsFeignClient;
    }

    @GetMapping
    public ResponseEntity<List<MovieDto>> getMovies() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieDto> movieDtos = movies.stream().map(movie -> {
            CollectionModel<MovieReview> movieReviews = reviewsFeignClient.getMovieReviews(movie.getId());
            return new MovieDto(movie, movieReviews.getContent());
        }).collect(Collectors.toList());
        return ResponseEntity.ok(movieDtos);
    }

    @GetMapping("{movieId}")
    public ResponseEntity<MovieDto> getMovie(@PathVariable("movieId") Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new EntityNotFoundException("Movie not found"));
        CollectionModel<MovieReview> movieReviews = reviewsFeignClient.getMovieReviews(movieId);
        return ResponseEntity.ok(new MovieDto(movie, movieReviews.getContent()));
    }

}
