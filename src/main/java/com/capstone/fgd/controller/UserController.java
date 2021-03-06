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

    @PostMapping("/editimage")
    public ResponseEntity<?> editImage(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        return fileService.editImage(multipartFile,principal);
    }

    @PostMapping("/deleteimage")
    public ResponseEntity<?> deleteImage(Principal principal) {
        return fileService.userDeleteImage(principal);
    }

    @PostMapping("/editimagebackground")
    public ResponseEntity<?> editImageBackground(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        return fileService.editImageBackground(multipartFile,principal);
    }

    @PostMapping("/deleteimagebackground")
    public ResponseEntity<?> deleteImageBackground(Principal principal) {
        return fileService.userDeleteImageBackground(principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllUser(Principal principal) {
            return userService.getAllUser();
    }

    @GetMapping(value = "/image/{id}",produces= "application/json")
    public ResponseEntity<?> loadUserImage(@PathVariable Long id){
        return fileService.userLoadImage(id);
    }

    @GetMapping(value = "/imagebackground",produces= "application/json")
    public ResponseEntity<?> loadUserImageBackground(Principal principal){
        return fileService.userLoadImageBackground(principal);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUserById(Principal principal, @PathVariable Long id){
        return userService.getUserByid(id);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchUser(Principal principal,@RequestParam(value = "user",required = false) String user){
        return userService.searchUser(user);
    }

    @PutMapping(value = "/suspend/{id}")
    public ResponseEntity<Object> updateSuspend(Principal principal,@PathVariable Long id,@RequestBody UsersRequest
                                                request){

        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return userService.updateSuspended(id,request);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Object> updateUser(Principal principal,@PathVariable Long id,
                                             @RequestBody UsersRequest user){
        return userService.updateUser(id,user);
    }


}
