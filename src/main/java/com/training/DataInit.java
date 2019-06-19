package com.training;

import com.training.model.Genre;
import com.training.model.Movie;
import com.training.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataInit {

    @Autowired
    private MovieRepository movieRepository;

    @PostConstruct
    public void init() {

        // Movies
        Movie movie = Movie.builder()
            .name("Esperando la carroza")
            .genre(Genre.DRAMA)
            .build();

        Movie movie1 = Movie.builder()
            .name("9 Reinas")
            .genre(Genre.ACTION)
            .build();

        Movie movie2 = Movie.builder()
            .name("El secreto de sus ojos")
            .genre(Genre.MYSTERY)
            .build();


        movieRepository.save(movie);
        movieRepository.save(movie1);
        movieRepository.save(movie2);
    }
}
