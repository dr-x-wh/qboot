package com.qboot.auth.controller;

import com.qboot.auth.pojo.dto.LoginDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody(required = false) LoginDTO value) {
        return "hello";
    }

}
