package com.capstone.fgd.controller;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Report;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ReportRequest;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.service.ReportService;
import com.capstone.fgd.service.TopicService;
import com.capstone.fgd.service.UserService;
import com.capstone.fgd.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewReport(Principal principal, @RequestBody ReportRequest request){
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return reportService.createNewReport(request);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllTopic(){
        return reportService.getAllReport();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getReportById(@PathVariable Long id){
        return reportService.getReportById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateReport(Principal principal,@PathVariable Long id, @RequestBody ReportRequest request) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return reportService.updateReport(id,request);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReport(Principal principal, @PathVariable Long id) {
        Users user = (Users) userService.loadUserByUsername(principal.getName());
        if (user.getIsAdmin().equals(true)){
            return reportService.deleteReport(id);
        }
        return ResponseUtil.build(ResponseMessage.NON_AUTHORIZED,null, HttpStatus.BAD_REQUEST);
    }

}
