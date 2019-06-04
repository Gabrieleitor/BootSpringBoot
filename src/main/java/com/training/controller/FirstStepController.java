package com.training.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstStepController {

    @GetMapping("/inicio")
    public String step1() {
        return "Step 1- BootSpringBoot";
    }
}
