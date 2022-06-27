package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping(value = "/v1/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "")
    public ResponseEntity<Object> getAllUser (Principal principal) {

        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return userService.getAllUser();
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(Principal principal, @PathVariable Long id){

        return userService.getUserByid(id);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchUser(Principal principal,@RequestParam(value = "email",required = false) String email){
        return userService.searchUser(email);
    }

    @PutMapping(value = "/suspend/{id}")
    public ResponseEntity<Object> updateSuspend(Principal principal,@PathVariable Long id){

        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return userService.updateSuspended(id);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Object> updateUser(Principal principal,@PathVariable Long id,
                                             @RequestBody UsersRequest user){
        return userService.updateUser(id,user);
    }


}
