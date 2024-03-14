package com.example.courses.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class demoController {

    @GetMapping
    public String hello(){
        return "hello courses app";
    }
}
