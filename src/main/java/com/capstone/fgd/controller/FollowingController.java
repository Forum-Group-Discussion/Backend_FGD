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
@RequestMapping(value = "/v1/following")
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @CrossOrigin
    @PostMapping(value = "")
    public ResponseEntity<Object> follow(@RequestBody FollowingRequest request,@PathVariable Long id){
        return followingService.createFollow(request,id);
    }

    @CrossOrigin
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllFollowing (Principal principal){
        return followingService.getAllFollowing();
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getFollowingById(Principal principal, @PathVariable Long id){
        return followingService.getFollowingByid(id);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteFollowing(@PathVariable Long id){
        return followingService.deleteFollowingById(id);
    }
}
