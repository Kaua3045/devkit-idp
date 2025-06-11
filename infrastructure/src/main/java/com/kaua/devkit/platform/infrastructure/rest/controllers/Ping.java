package com.kaua.devkit.platform.infrastructure.rest.controllers;

import com.kaua.devkit.platform.domain.utils.InstantUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ping")
public class Ping {

    @GetMapping
    public ResponseEntity<?> pong() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Map.of("Pong", InstantUtils.now().toString()));
    }
}
