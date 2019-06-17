package com.training.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The person name.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonName {

    private String name;
    private String lastName;
}
