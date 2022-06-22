package com.capstone.fgd.controller;


import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.service.CommentService;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<?> createNewComment(@RequestBody CommentRequest request, Principal principal) {
        return commentService.createNewComment(request, principal);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteComment(Principal principal, @PathVariable Long id) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)) {
            return commentService.deleteComment(id);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED, null, HttpStatus.BAD_REQUEST);
    }



}
