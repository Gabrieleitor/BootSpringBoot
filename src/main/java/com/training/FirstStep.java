package com.training;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstStep {

    @GetMapping("/inicio")
    public String step1() {
        return "Step 1- BootSpringBoot";
    }
}
