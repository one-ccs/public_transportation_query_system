package com.example.publictransportationquerysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ad")
public class AdController {

    @GetMapping("/getone")
    public String getone() {
        return new String("getone api");
    }
}
