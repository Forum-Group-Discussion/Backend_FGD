package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.service.ThreadsService;
import com.capstone.fgd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/thread")
public class ThreadsController {
    @Autowired
    private UserService userService;

    @Autowired
    private ThreadsService threadsService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewThread(@RequestBody ThreadsRequest request, Principal principal) {
        return threadsService.createNewThread(request,principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllThread() {
        return threadsService.getAllThread();
    }

    @GetMapping(value = "/bytopic")
    public ResponseEntity<Object> getThreadByTopic(@RequestParam(value = "topic", required = false)Integer thread){
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

//    @DeleteMapping(value = "/{id}")
//<<<<<<< HEAD
//    public ResponseEntity<Object> deleteThread(Principal principal, @PathVariable Long id) {
//=======
//    public ResponseEntity<Object> deleteThread(@PathVariable Long id) {
//>>>>>>> fd2925da89ceed722cd9748aec38befb9024fe2e
//        return threadsService.deleteThread(id);
//    }
//
//    @GetMapping(value = "/search")
//    public ResponseEntity<Object> searchThread(@RequestParam(value = "thread",required = false) String thread){
//        return userService.searchUser(thread);
//    }
}