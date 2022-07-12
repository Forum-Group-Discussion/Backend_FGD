package com.capstone.fgd.controller;


import com.capstone.fgd.domain.dto.SaveThreadRequest;
import com.capstone.fgd.service.SaveThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "v1/savethread")
public class SaveThreadController {
    @Autowired
    private SaveThreadService saveThreadService;

    @PostMapping(value = "")
    ResponseEntity<Object> savethread (@RequestBody SaveThreadRequest request, Principal principal){
        return saveThreadService.saveThread(request, principal);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<Object> getSaveThreadById(@PathVariable Long id){
        return saveThreadService.getByUser(id);
    }
}
