package com.training.controller;

import com.training.controller.dto.MovieDTO;
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
 */
@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * Returns a movie given an id.
     *
     * @param id the movie id
     * @return the movie
     */
    @GetMapping(value = "/{id}")
    public MovieDTO getMovie(@PathVariable Long id) {
        return movieService.findById(id);
    }

    /**
     * Returns all movies.
     *
     * @return a list of movies
     */
    @GetMapping
    public List<MovieDTO> getMovies() {
        return movieService.findAll();
    }

    /**
     * Saves a new movie
     *
     * @param movieDTO the movie
     * @return the saved movie
     */
    @PostMapping
    public MovieDTO saveMovie(@RequestBody MovieDTO movieDTO) {
        return movieService.save(movieDTO);
    }

    /**
     * Updates a movie given an id.
     *
     * @param id    the movie id
     * @param movieDTO the movie
     * @return the updated movie
     */
    @PutMapping("/{id}")
    public MovieDTO updateMovie(@PathVariable Long id, @RequestBody MovieDTO movieDTO) {
        movieDTO.setId(id);
        return movieService.save(movieDTO);
    }

    /**
     * Deletes a movie given an id
     *
     * @param id the movie id
     */
    @DeleteMapping(value = "/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteById(id);
    }

}
