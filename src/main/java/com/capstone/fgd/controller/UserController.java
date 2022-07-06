package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.FileService;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @PostMapping("/uploadfile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        return fileService.saveFile(multipartFile,principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllUser (Principal principal) {

        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return userService.getAllUser();
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);

    }
    @GetMapping(value = "/image",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> loadUserImage(Principal principal){
        return fileService.userLoadImage(principal);
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
