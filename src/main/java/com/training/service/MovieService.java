package com.training.service;

import com.training.model.Movie;
import com.training.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * The movie service.
 */
@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    /**
     * Retrieves a {@link Movie} given an id.
     *
     * @param id the movie id. It cannot be null
     * @return the movie
     * @throws IllegalArgumentException if <code>id</code> is null
     */
    public Movie findById(Long id) {
        Assert.notNull(id, "The movie id cannot be null");
        return movieRepository.findById(id).orElse(null);
    }

    /**
     * Returns all the movies.
     * @return a list with movies
     */
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    /**
     * Saves a new movie or update if it exists.
     *
     * @param movie the movie to be saved or updated.
     * @return the movie
     * @throws IllegalArgumentException if <code>movie<code/> is null
     */
    public Movie save(Movie movie) {
        Assert.notNull(movie, "The movie cannot be null");
        return movieRepository.save(movie);
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
