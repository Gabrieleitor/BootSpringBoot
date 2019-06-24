package com.training.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {

    private Long id;

    private String name;

    private String lastName;

    private List<MovieDTO> movies;
}
