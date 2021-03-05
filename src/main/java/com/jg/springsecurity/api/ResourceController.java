package com.jg.springsecurity.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @GetMapping("/resources/{resourceNumber}")
    public String getResource(@PathVariable final Integer resourceNumber) {
        return "Here's your resource: " + resourceNumber;
    }

    /**
     * Checks whether current Principal has authority 'OBSERVER'. (If hasRole is used instead, prefix ROLE_ is expected.
     */
    @GetMapping("/observer")
    @PreAuthorize("hasAuthority('OBSERVER')")
    public String observerOnly() {
        return "Hello, Observer!";
    }

    /**
     * Checks whether current Principal has authority 'USER'. (If hasRole is used instead, prefix ROLE_ is expected.
     */
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    public String userOnly() {
        return "Hello, User!";
    }

    /**
     * Checks whether current Principal has authority 'ADMIN'. (If hasRole is used instead, prefix ROLE_ is expected.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminOnly() {
        return "Hello, Admin!";
    }

}
