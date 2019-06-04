package com.training.service;

import com.training.model.Movie;
import com.training.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * {@link MovieService} unit tests.
 */
@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

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

        when(movieRepository.findById(anyLong())).thenReturn(Optional.of(expectedMovie));

        //when
        Movie movie = movieService.findById(MOVIE_ID);

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
        List<Movie> movies = movieService.findAll();

        //then
        Assertions.assertNotNull(movies);
        Assertions.assertEquals(1, movies.size());
        Assertions.assertSame(expectedMovies, movies);
    }

    @Test
    public void givenAMovieWhenSaveThenReturnMovie() {
        //given
        Movie expectedMovie = Movie.builder()
            .id(MOVIE_ID)
            .name(MOVIE_NAME)
            .build();

        Movie movieToBeSaved = Movie.builder()
            .name(MOVIE_NAME)
            .build();

        when(movieRepository.save(any(Movie.class))).thenReturn(expectedMovie);

        //when
        Movie movie = movieService.save(movieToBeSaved);

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
