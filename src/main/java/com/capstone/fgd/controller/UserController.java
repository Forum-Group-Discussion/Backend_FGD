package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllUser (Principal principal){


        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return userService.getAllUser();
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(Principal principal, @PathVariable Long id){
        return userService.getUserByid(id);
    }

//    @PutMapping(value = "/{id}")
//    public ResponseEntity<Object> updateUser(Principal principal,@PathVariable Long id,@RequestBody UserDto userDto){
//        return userService.updateService(id,userDto);
//    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Object> deleteUser(Principal principal,@PathVariable Long id){
//        UserDao user = (UserDao) userService.loadUserByUsername(principal.getName());
//        if (user.getAuthor().equals("ADMIN")){
//            return userService.deleteUser(id);
//        }
//        return ResponseUtil.build(ResponseMassage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
//
//    }
}
