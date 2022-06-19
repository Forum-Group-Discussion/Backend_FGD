package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
public class AuthController {
    private final AuthService authService;

    @CrossOrigin
    @PostMapping(value  ="/register" )
    ResponseEntity<?> register( @RequestBody UsersRequest req)  {
        return authService.register(req);
    }

    @CrossOrigin
    @PostMapping(value  ="/login" )
    ResponseEntity<?> login(@RequestBody @Valid UsersRequest req){
        return authService.authenticateAndGenerateToken(req);
    }

}
