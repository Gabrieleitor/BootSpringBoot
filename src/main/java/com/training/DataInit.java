package com.training;

import com.training.model.Actor;
import com.training.model.Genre;
import com.training.model.Movie;
import com.training.model.PersonName;
import com.training.repository.ActorRepository;
import com.training.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class DataInit {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

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

        List<Movie> pelicula = Collections.singletonList(movie);

        actorRepository.save(
            Actor.builder()
                .name(new PersonName("Antonio", "Gasalla"))
                .movies(pelicula)
                .build()
        );

        actorRepository.save(
            Actor.builder()
                .name(new PersonName("Luis", "Brandoni"))
                .movies(pelicula)
                .build()
        );

        actorRepository.save(
            Actor.builder()
                .name(new PersonName("China", "Zorrilla"))
                .movies(pelicula)
                .build()
        );

        List<Movie> peliculaDarin = Arrays.asList(movie1, movie2);
        List<Movie> pelicula1 = Collections.singletonList(movie1);

        actorRepository.save(
            Actor.builder()
                .name(new PersonName("Ricardo ", "Darín"))
                .movies(peliculaDarin)
                .build()
        );

        actorRepository.save(
            Actor.builder()
                .name(new PersonName("Gastón", "Pauls"))
                .movies(pelicula1)
                .build()
        );

    }

}
