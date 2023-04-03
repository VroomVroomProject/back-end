package com.backend.vroomvroom.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public String hello() {
        return "user ok!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "admin ok!";
    }

    @GetMapping("/all")
    public String all() {
        return "all ok!";
    }

    @GetMapping("/user2")
    public String user2() {
        return "user2 ok!";
    }

    @GetMapping("/admin2")
    public String admin2() {
        return "admin2 ok!";
    }

    @GetMapping("/all2")
    public String all2() {
        return "all2 ok!";
    }
}
