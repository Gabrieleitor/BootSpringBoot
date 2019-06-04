package com.training.repository;

import com.training.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The movie repository.
 */
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
