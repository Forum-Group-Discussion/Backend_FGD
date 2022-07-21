package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.service.ReportCommentService;
import com.capstone.fgd.service.ReportThreadService;
import com.capstone.fgd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/allreporttype")
public class ReportTypeController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReportCommentService reportCommentService;


    @GetMapping(value = "")
    public ResponseEntity<Object> getAllReportType(){
        return reportCommentService.getAllReportType();
    }
}
