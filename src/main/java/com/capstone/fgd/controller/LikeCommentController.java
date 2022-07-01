package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.LikeCommentRequest;
import com.capstone.fgd.service.LikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "v1/likecomment")
public class LikeCommentController {

    @Autowired
    private LikeCommentService likeCommentService;

    @PostMapping(value = "")
    ResponseEntity<Object> likeComment (Principal principal, @RequestBody LikeCommentRequest request){
        return likeCommentService.LikeComment(principal, request);
    }

    @GetMapping(value = "/like")
    ResponseEntity<Object> countLikeComment(@RequestBody CommentRequest request){
        return likeCommentService.countLikeThread(request);
    }

    @GetMapping(value = "/dislike")
    ResponseEntity<Object> countDislikeComment(@RequestBody CommentRequest request){
        return likeCommentService.countDislikecomment(request);
    }

}
