package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.AuthService;
import com.capstone.fgd.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.security.Principal;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping(value = "/v1/auth")
public class AuthController {
    @Autowired
    private FileStorageService fileStorageService;

    private final AuthService authService;

    @PostMapping("/uploadfile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        return fileStorageService.saveFile(multipartFile,principal);
    }

    @PostMapping(value ="/registerwithuploadfile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> register(@RequestParam("json") String json, @RequestParam(value = "file",required = false) MultipartFile file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        UsersRequest request = mapper.readValue(json, UsersRequest.class);


        if (file == null){
            return authService.register(request);
        }
        return authService.registerWithUploadImages(request, file);
    }



    @PostMapping(value  ="/login" )
    ResponseEntity<?> login(@RequestBody UsersRequest req){
        return authService.authenticateAndGenerateToken(req);
    }

}
