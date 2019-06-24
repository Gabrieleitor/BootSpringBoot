package com.training.config;

import com.training.service.converter.ActorDTOToActorConverter;
import com.training.service.converter.ActorToActorDTOConverter;
import com.training.service.converter.MovieDTOToMovieConverter;
import com.training.service.converter.MovieToMovieDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConverterConfig implements WebMvcConfigurer {

    @Autowired
    private ActorDTOToActorConverter actorDTOToActorConverter;

    @Autowired
    private ActorToActorDTOConverter actorToActorDTOConverter;

    @Autowired
    private MovieDTOToMovieConverter movieDTOToMovieConverter;

    @Autowired
    private MovieToMovieDTOConverter movieToMovieDTOConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(actorDTOToActorConverter);
        registry.addConverter(actorToActorDTOConverter);
        registry.addConverter(movieDTOToMovieConverter);
        registry.addConverter(movieToMovieDTOConverter);
    }

}
