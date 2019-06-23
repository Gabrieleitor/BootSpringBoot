package com.training.service;

import com.training.controller.dto.MovieDTO;
import com.training.model.Genre;
import com.training.model.Movie;
import com.training.repository.MovieRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public MovieDTO findById(Long id) {
        Assert.notNull(id, "The movie id cannot be null");
        Movie movie = movieRepository.findById(id)
            .orElseThrow( () -> new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Movie nof found"));

        MovieDTO movieDTO = new MovieDTO();
        BeanUtils.copyProperties(movie, movieDTO);
        return movieDTO;
    }

    /**
     * Returns all the movies.
     * @return a list with movies
     */
    public List<MovieDTO> findAll() {
        return movieRepository.findAll().stream()
            .map( movie -> {
                MovieDTO movieDTO = new MovieDTO();
                BeanUtils.copyProperties(movie, movieDTO);

                if (Objects.nonNull(movie.getGenre())) {
                    movieDTO.setGenre(movie.getGenre().name());
                }

                return movieDTO;
            })
            .collect(Collectors.toList());
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

        Movie movie = new Movie();
        BeanUtils.copyProperties(movieDTO, movie);

        if (Objects.nonNull(movieDTO.getGenre())) {
            movie.setGenre(Genre.valueOf(movieDTO.getGenre()));
        }
        Assert.notNull(movie, "The movie cannot be null");

        Movie savedMovie = movieRepository.save(movie);
        MovieDTO rtaDTO = new MovieDTO();

        if (Objects.nonNull(savedMovie)) {
            BeanUtils.copyProperties(savedMovie, rtaDTO);

            if (Objects.nonNull(movieDTO.getGenre())) {
                rtaDTO.setGenre(savedMovie.getGenre().name());
            }
        }

        return rtaDTO;
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
