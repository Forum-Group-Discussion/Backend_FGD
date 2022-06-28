package com.capstone.fgd.controller;


import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.service.ReportThreadService;
import com.capstone.fgd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/reportthread")
public class ReportThreadController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReportThreadService reportThreadService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewReportThread(@RequestBody ReportThreadRequest request, Principal principal){
        return reportThreadService.createNewReportThread(request,principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllReportThread(Principal principal){
        return reportThreadService.getAllReportThread();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getReportThreadById(Principal principal, @PathVariable Long id) {
        return reportThreadService.getReportThreadById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReportThread(Principal principal, @PathVariable Long id, @RequestBody ReportThreadRequest request) {
        return reportThreadService.updateReportThread(id, request);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReportThread(Principal principal, @PathVariable Long id) {
        return reportThreadService.deleteReportThread(id);
    }

}
