package com.example.public_transportation_query_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.public_transportation_query_system.entity.vo.Result;

@Controller
@RequestMapping("/api/ad")
public class AdController {

    @GetMapping
    public Result<Object> apiAdGet() {
        return Result.success();
    }

    @PutMapping
    public Result<Object> apiAdPut() {
        return Result.success();
    }

    @PostMapping
    public Result<Object> apiAdPost() {
        return Result.success();
    }

    @DeleteMapping
    public Result<Object> apiAdDelete() {
        return Result.success();
    }
}
