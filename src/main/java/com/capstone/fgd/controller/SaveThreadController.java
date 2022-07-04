package com.capstone.fgd.controller;


import com.capstone.fgd.domain.dto.SaveThreadRequest;
import com.capstone.fgd.service.SaveThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "v1/savethread")
public class SaveThreadController {
    @Autowired
    private SaveThreadService saveThreadService;

    @PostMapping(value = "")
    ResponseEntity<Object> savethread (Principal principal, @RequestBody SaveThreadRequest request){
        return saveThreadService.saveThread(principal, request);
    }

}
