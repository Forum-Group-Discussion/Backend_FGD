package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ThreadRequest;
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
    public ResponseEntity<Object> updateThread(@PathVariable Long id, @RequestBody TopicRequest request) {
            return topicService.updateTopic(id, request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTopic(@PathVariable Long id) {
            return topicService.deleteTopic(id);
    }


}
