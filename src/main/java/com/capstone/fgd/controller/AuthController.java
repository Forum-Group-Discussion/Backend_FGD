package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value  ="/register" )
    ResponseEntity<?> register(@RequestBody UsersRequest req){
        return authService.register(req);
    }

    @PostMapping(value  ="/login" )
    ResponseEntity<?> login(@RequestBody UsersRequest req){
        return authService.authenticateAndGenerateToken(req);
    }

}
