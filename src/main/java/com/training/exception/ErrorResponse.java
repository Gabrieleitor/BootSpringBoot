package com.training.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private Instant timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

}
