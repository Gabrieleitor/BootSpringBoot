package com.training.service.converter;

import com.training.controller.dto.MovieDTO;
import com.training.model.Movie;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MovieToMovieDTOConverter implements Converter<Movie, MovieDTO> {

    @Override
    public MovieDTO convert(Movie movie) {
        MovieDTO movieDTO = new MovieDTO();
        BeanUtils.copyProperties(movie, movieDTO);

        if (Objects.nonNull(movie.getGenre())) {
            movieDTO.setGenre(movie.getGenre().name());
        }

        return movieDTO;
    }

}
