package com.jg.springsecurity.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resources/{resourceNumber}")
    public String getResource(@PathVariable final Integer resourceNumber) {
        return "Here's your resource: " + resourceNumber;
    }

}
