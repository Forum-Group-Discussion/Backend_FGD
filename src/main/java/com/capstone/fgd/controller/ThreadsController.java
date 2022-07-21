package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.service.FileService;
import com.capstone.fgd.service.ThreadsService;
import com.capstone.fgd.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/thread")
public class ThreadsController {
    @Autowired
    private UserService userService;

    @Autowired
    private ThreadsService threadsService;

    @Autowired
    private FileService fileService;


    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createNewThread(Principal principal,@RequestParam("json") String json,
                                                  @RequestParam(value = "file",required = false) MultipartFile file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        ThreadsRequest request = mapper.readValue(json,ThreadsRequest.class);

        if(file == null){
            return threadsService.createNewThread(principal,request);
        }
        return threadsService.createNewThreadUsingImage(principal,request,file);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllThread() {
        return threadsService.getAllThread();
    }


    @GetMapping(value = "/bytopic")
    public ResponseEntity<Object> getThreadByTopic(@RequestParam(value = "topic", required = false)Long thread){
        return threadsService.getThreadByTopic(thread);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getThreadById(@PathVariable Long id) {
        return threadsService.getThreadById(id);
    }

    @GetMapping(value = "/desc")
    public ResponseEntity<Object> getThreadDesc(){
        return threadsService.getAllThreadByNew();
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateThread(@PathVariable Long id, @RequestBody ThreadsRequest request) {
        return threadsService.updateThread(id, request);
    }

    @GetMapping(value = "/photo/{id}",produces= "application/json")
    public ResponseEntity<?> getImageThreadById(@PathVariable Long id){
        return fileService.getImageThreadById(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteThread(@PathVariable Integer id) {

        return threadsService.deleteThread(id);
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Object> searchByThread(@RequestParam(value = "thread",required = false) String thread){
        return threadsService.searchByThread(thread);
    }


    @GetMapping(value = "/bylike")
    public ResponseEntity<?> getListThreadByLike(){
        return threadsService.getListThreadByLike();
    }

    @GetMapping(value = "/totalthread")
    public ResponseEntity<?> getTotalThread(){
        return threadsService.getTotalThread();
    }

    @GetMapping(value = "/totalthreaduserid")
    public ResponseEntity<?> getCountThreadByUser(Principal principal){
        return threadsService.getCountThreadByUser(principal);
    }



}