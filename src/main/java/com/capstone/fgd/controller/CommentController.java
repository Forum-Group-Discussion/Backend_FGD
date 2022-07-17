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

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllCommentByThread(@RequestParam(value = "thread", required = false)Integer comment){
        return commentService.getCommentByThread(comment);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable Long id){
        return commentService.getCommentById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> UpdateComment(@PathVariable Long id, @RequestBody CommentRequest request){
        return commentService.updateComment(id, request);
    }

    @GetMapping(value = "/totalcommentbythread")
    public ResponseEntity<Object> getTotalCommentByThread(){
        return commentService.getTotalCommentByThread();
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchComment(@RequestParam(value = "comment", required = false)String comment){
        return commentService.searchComment(comment);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteComment(Principal principal, @PathVariable Long id) {
            return commentService.deleteComment(id);

    }



}
