package com.training.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.training.converter.PersonNameConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * The actor entity.
 */
@Builder
@Data
@Entity(name = "actors")
@AllArgsConstructor
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = PersonNameConverter.class)
    private PersonName name;

    @ManyToMany
    @JsonIgnoreProperties("actors")
    private List<Movie> movies;

}
