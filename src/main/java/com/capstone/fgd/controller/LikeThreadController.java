package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.LikeThreadRequest;
import com.capstone.fgd.service.LikeThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/likethread")
public class LikeThreadController {
    @Autowired
    private LikeThreadService likeThreadService;

    @PostMapping(value = "")
    ResponseEntity<Object> likeThread(Principal principal,@RequestBody LikeThreadRequest request){
        return likeThreadService.likeTheThread(principal,request);
    }

    @GetMapping(value = "")
    ResponseEntity<Object> countLikeThread(@RequestBody  LikeThreadRequest request){
        return likeThreadService.countLikeThread(request);
    }


}
