package com.training.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.training.controller.dto.MovieDTO;
import com.training.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MovieController.class)
public class MovieControllerTest {

    private static final Long MOVIE_ID = 1L;
    private static final String MOVIE_NAME = "The Movie name";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    public void testGetMovie() throws Exception {
        //when
        this.mockMvc.perform(get("/movies/" + MOVIE_ID))
            .andExpect(status().isOk());

        //then
        verify(movieService).findById(MOVIE_ID);
    }

    @Test
    public void testGetMovies() throws Exception {
        //when
        this.mockMvc.perform(get("/movies"))
            .andExpect(status().isOk());

        //then
        verify(movieService).findAll(any(Pageable.class));
    }

    @Test
    public void testSaveMovie() throws Exception {
        //given
        MovieDTO movie = MovieDTO.builder()
            .name(MOVIE_NAME)
            .build();

        //when
        this.mockMvc.perform(post("/movies")
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(new ObjectMapper().writeValueAsString(movie)))
            .andExpect(status().isOk());

        //then
        verify(movieService).save(movie);
    }

    @Test
    public void testUpdateMovie() throws Exception {
        MovieDTO movie = MovieDTO.builder()
            .name("")
            .build();

        this.mockMvc.perform(put("/movies/" + MOVIE_ID)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(new ObjectMapper().writeValueAsString(movie)))
            .andExpect(status().isOk());

        movie.setId(MOVIE_ID);
        verify(movieService).save(movie);
    }

    @Test
    public void testDeleteMovie() throws Exception {
        //when
        this.mockMvc.perform(delete("/movies/" + MOVIE_ID))
            .andExpect(status().isOk());

        //then
        verify(movieService).deleteById(MOVIE_ID);
    }

}
