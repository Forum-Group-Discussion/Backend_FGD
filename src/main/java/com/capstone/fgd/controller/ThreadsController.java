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
    public ResponseEntity<?> createNewThread(@RequestBody ThreadsRequest request, Principal principal) {
        return threadsService.createNewThread(request,principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllThread(Principal principal) {
        return threadsService.getAllThread();

    }

    @GetMapping(value = "/follow")
    public ResponseEntity<Object> getAllThreadFollow(Principal principal) {
        return threadsService.getAllThreadFollow(principal);

    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getThreadById(Principal principal, @PathVariable Long id) {
        return threadsService.getThreadById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateThread(Principal principal, @PathVariable Long id, @RequestBody ThreadsRequest request) {
        return threadsService.updateThread(id, request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTeam(Principal principal, @PathVariable Long id) {
        return threadsService.deleteThread(id);
    }
}