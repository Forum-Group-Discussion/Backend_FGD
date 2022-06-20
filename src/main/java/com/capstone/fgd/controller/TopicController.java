package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.service.TopicService;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping(value = "/v1/topic")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewTopic(Principal principal, @RequestBody TopicRequest request){
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return topicService.createNewTopic(request);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllTopic(){
        return topicService.getAllTopic();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getTopicById(@PathVariable Long id){
        return topicService.getTopicById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateThread(Principal principal,@PathVariable Long id, @RequestBody TopicRequest request) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return topicService.updateTopic(id, request);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTopic(Principal principal, @PathVariable Long id) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return topicService.deleteTopic(id);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }


}
