package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
public class AuthController {

    private final AuthService authService;

//    @CrossOrigin
    @PostMapping(value  ="/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> register(@RequestParam("json") String json, @RequestParam("file")MultipartFile file) throws JsonProcessingException {
        ObjectMapper  mapper = new ObjectMapper();

        UsersRequest request = mapper.readValue(json,UsersRequest.class);

        return authService.register(request,file);

    }

//    @CrossOrigin
    @PostMapping(value  ="/login" )
    ResponseEntity<?> login(@RequestBody UsersRequest req){
        return authService.authenticateAndGenerateToken(req);
    }

}
