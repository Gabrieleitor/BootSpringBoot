package com.training.service.converter;

import com.training.controller.dto.MovieDTO;
import com.training.model.Genre;
import com.training.model.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovieDTOToMovieConverter implements Converter<MovieDTO, Movie> {

    @Override
    public Movie convert(MovieDTO movieDTO) {
        Movie movie = new Movie();
        BeanUtils.copyProperties(movieDTO, movie);

        if (Objects.nonNull(movieDTO.getGenre())) {
            movie.setGenre(Genre.valueOf(movieDTO.getGenre()));
        }

        return movie;
    }

}
