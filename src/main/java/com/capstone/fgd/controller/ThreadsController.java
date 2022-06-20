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

    @CrossOrigin
    @PostMapping(value = "")
    public ResponseEntity<Object> createNewThread(@RequestBody ThreadsRequest request, Principal principal) {
        return threadsService.createNewThread(request,principal);
    }

    @CrossOrigin
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllThread() {
        return threadsService.getAllThread();

    }

    @CrossOrigin
    @GetMapping(value = "/follow")
    public ResponseEntity<Object> getAllThreadFollow(Principal principal) {
        return threadsService.getAllThreadFollow(principal);

    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getThreadById(@PathVariable Long id) {
        return threadsService.getThreadById(id);
    }

    @CrossOrigin
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateThread(@PathVariable Long id, @RequestBody ThreadsRequest request) {
        return threadsService.updateThread(id, request);
    }

    @CrossOrigin
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteThread(@PathVariable Long id) {
        return threadsService.deleteThread(id);
    }
}