package com.training.controller;

import com.training.model.Movie;
import com.training.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The Movie Controller.
 *
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Returns a movie given an id.
     * @param id the movie id
     * @return the movie
     */
    @GetMapping(value = "/{id}")
    public Movie getMovie(@PathVariable Long id) {
        return movieService.findById(id);
    }

    /**
     * Returns all movies.
     * @return a list of movies
     */
    @GetMapping
    public List<Movie> getMovies() {
        return movieService.findAll();
    }

    /**
     * Saves a new movie
     * @param movie the movie
     * @return the saved movie
     */
    @PostMapping
    public Movie saveMovie(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie) {
        movie.setId(id);
        return movieService.save(movie);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
    }

}
