package com.bolsafacil.corretorinteligente.controllers;

import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;


@RestController
@EnableAutoConfiguration
public class Monitor {
    
    @GetMapping(value="/")
    String home() {
        return "";
    }
}