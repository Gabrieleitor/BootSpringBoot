package com.training.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;

    private String name;

    private String genre;

    @JsonIgnoreProperties("movies")
    private List<ActorDTO> actors;

}
