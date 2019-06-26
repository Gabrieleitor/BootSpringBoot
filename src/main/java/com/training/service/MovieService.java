package com.training.service;

import com.training.controller.dto.MovieDTO;
import com.training.model.Movie;
import com.training.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

/**
 * The movie service.
 */
@Service
public class MovieService {

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Retrieves a {@link Movie} given an id.
     *
     * @param id the movie id. It cannot be null
     * @return the movie
     * @throws IllegalArgumentException if <code>id</code> is null
     */
    public MovieDTO findById(Long id) {
        Assert.notNull(id, "The movie id cannot be null");
        Movie movie = movieRepository.findById(id)
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Movie not found"));

        return conversionService.convert(movie, MovieDTO.class);
    }

    /**
     * Returns all the movies.
     * @return a list with movies
     */
    public Page<MovieDTO> findAll(final Pageable pageable) {
        return movieRepository.findAll(pageable)
            .map( movie -> conversionService.convert(movie, MovieDTO.class));
    }

    /**
     * Saves a new movie or update if it exists.
     *
     * @param movieDTO the movie to be saved or updated.
     * @return the movie
     * @throws IllegalArgumentException if <code>movie<code/> is null
     */
    public MovieDTO save(MovieDTO movieDTO) {
        Assert.notNull(movieDTO, "The movieDTO cannot be null");

        Movie movie = conversionService.convert(movieDTO, Movie.class);
        movie = movieRepository.save(movie);

        return Optional.ofNullable(conversionService.convert(movie, MovieDTO.class))
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Movie not saved") );
    }

    /**
     * Removes the given movie.
     *
     * @param id the movie id
     * @throws IllegalArgumentException if <code>movie</code> is null
     */
    public void deleteById(Long id) {
        Assert.notNull(id, "The movie id cannot be null");
        movieRepository.deleteById(id);
    }
}
