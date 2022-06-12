package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ThreadRequest;
import com.capstone.fgd.service.ThreadService;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/thread")
public class ThreadController {
    @Autowired
    private UserService userService;

    @Autowired
    private ThreadService threadService;

    @PostMapping(value = "")
    public ResponseEntity<?> createNewThread(@RequestBody ThreadRequest request) {
        return threadService.createNewThread(request);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllThread(Principal principal) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)) {
            return threadService.getAllThread();
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED, null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getThreadById(Principal principal, @PathVariable Long id) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)) {
            return threadService.getThreadById(id);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED, null, HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateThread(Principal principal, @PathVariable Long id, @RequestBody ThreadRequest request) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)) {
            return threadService.updateThread(id, request);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED, null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteTeam(Principal principal, @PathVariable Long id) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)) {
            return threadService.deleteThread(id);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED, null, HttpStatus.BAD_REQUEST);
    }
}