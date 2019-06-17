package com.training.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import java.util.Set;

/**
 * The movie entity.
 */
@Builder
@Data
@Entity(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @ManyToMany(mappedBy = "movies")
    private Set<Actor> actors;

}
