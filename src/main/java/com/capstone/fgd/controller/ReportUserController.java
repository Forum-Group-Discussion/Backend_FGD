package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.domain.dto.ReportUserRequest;
import com.capstone.fgd.service.ReportThreadService;
import com.capstone.fgd.service.ReportUserService;
import com.capstone.fgd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/reportuser")
public class ReportUserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReportUserService reportUserService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewReportUser(@RequestBody ReportUserRequest request, Principal principal){
        return reportUserService.createNewReportUser(request,principal);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllReportU(Principal principal){
        return reportUserService.getAllReportUser();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getReportUserById(Principal principal, @PathVariable Long id) {
        return reportUserService.getReportuserById(id);
    }

    @GetMapping(value = "/totalreport")
    public ResponseEntity<Object> getTotalReport(){
        return reportUserService.getTotalReport();
    }


    @GetMapping(value = "/listotalreportuser")
    public ResponseEntity<Object> getListReportuser(){
        return reportUserService.getListReportUser();
    }


}

