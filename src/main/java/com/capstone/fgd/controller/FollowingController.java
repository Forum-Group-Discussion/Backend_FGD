package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.FollowingRequest;
import com.capstone.fgd.service.FollowingService;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
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

