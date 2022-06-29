package com.capstone.fgd.controller;


import com.capstone.fgd.domain.dto.FollowingRequest;
import com.capstone.fgd.service.FollowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/following")
public class FollowingController {

    @Autowired
    private FollowingService followingService;


    @PostMapping(value = "")
    public ResponseEntity<Object> follow(@RequestBody FollowingRequest request,Principal principal){
        return followingService.followUser(request,principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllFollowing (Principal principal){
        return followingService.getAllFollowing(principal);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getFollowingById(Principal principal, @PathVariable Long id){
        return followingService.getFollowingByid(id);
    }


}

