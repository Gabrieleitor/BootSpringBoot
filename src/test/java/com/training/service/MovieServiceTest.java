package com.training.service;

import com.training.controller.dto.MovieDTO;
import com.training.model.Movie;
import com.training.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

/**
 * {@link MovieService} unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ConversionService conversionService;

    @InjectMocks
    private MovieService movieService;

    private static final Long MOVIE_ID = 1L;
    private static final String MOVIE_NAME = "Movie name";

    @Test
    public void givenAValidIdWhenFindByIdThenReturnMovie() {
        //given
        Movie expectedMovie = Movie.builder()
            .id(MOVIE_ID)
            .name(MOVIE_NAME)
            .build();

        MovieDTO returnMovieDTO = MovieDTO.builder()
            .id(MOVIE_ID)
            .name(MOVIE_NAME)
            .build();


        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(expectedMovie));

        doReturn(returnMovieDTO).when(conversionService).convert(expectedMovie, MovieDTO.class);

        //when
        MovieDTO movie = movieService.findById(MOVIE_ID);

        //then
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(movie.getId(), expectedMovie.getId());
    }

    @Test
    public void givenANullIdWhenFindByIdThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            movieService.findById(null));
    }

    @Test
    public void testFindAllShouldRunOk() {
        //given
        List<Movie> expectedMovies = Arrays.asList(Movie.builder()
            .id(MOVIE_ID)
            .name(MOVIE_NAME)
            .build());

        when(movieRepository.findAll()).thenReturn(expectedMovies);

        //when
        List<MovieDTO> movies = movieService.findAll();

        //then
        Assertions.assertNotNull(movies);
        Assertions.assertEquals(1, movies.size());
    }

    @Test
    public void givenAMovieWhenSaveThenReturnMovie() {
        //given
        Movie expectedMovie = Movie.builder()
            .id(MOVIE_ID)
            .name(MOVIE_NAME)
            .build();

        MovieDTO movieToBeSaved = MovieDTO.builder()
            .name(MOVIE_NAME)
            .build();

        Movie movieConverter = Movie.builder()
            .name(MOVIE_NAME)
            .build();

        MovieDTO movieToBeReturned = MovieDTO.builder()
            .id(MOVIE_ID)
            .name(MOVIE_NAME)
            .build();


        doReturn(movieConverter).when(conversionService).convert(movieToBeSaved, Movie.class);

        doReturn(movieToBeReturned).when(conversionService).convert(expectedMovie, MovieDTO.class);

        when(movieRepository.save(any(Movie.class))).thenReturn(expectedMovie);

        //when
        MovieDTO movie = movieService.save(movieToBeSaved);

        //then
        Assertions.assertNotNull(movie);
        Assertions.assertNotNull(movie.getId());
        Assertions.assertEquals(movie.getName(), expectedMovie.getName());
    }

    @Test
    public void givenANullMovieWhenSaveThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            movieService.save(null));
    }

    @Test
    public void givenAMovieIdWhenDeleteByIdThenRunOk() {
        //when
        movieService.deleteById(MOVIE_ID);

        //then
        verify(movieRepository).deleteById(MOVIE_ID);
    }

    @Test
    public void givenANullIdWhenDeleteByIdThenThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
            movieService.deleteById(null));
    }
}
