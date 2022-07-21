package com.capstone.fgd.controller;

import com.capstone.fgd.domain.dto.ReportCommentRequest;
import com.capstone.fgd.service.ReportCommentService;
import com.capstone.fgd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(value = "/v1/reportcomment")
public class ReportCommentController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReportCommentService reportCommentService;

    @PostMapping(value = "")
    public ResponseEntity<Object> createNewReportComment(@RequestBody ReportCommentRequest request, Principal principal){
        return reportCommentService.createNewReportComment(request, principal);
    }

    @GetMapping(value = "/allreporttype")
    public ResponseEntity<Object> getAllReportType(){
        return reportCommentService.getAllReportType();
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> getAllReportComment(Principal principal){
        return reportCommentService.getAllReportComment();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getReportCommentById(Principal principal, @PathVariable Long id){
        return reportCommentService.getReportCommentById(id);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteReportComment(Principal principal, @PathVariable Long id){
        return reportCommentService.deleteReportComment(id);
    }

    @GetMapping(value = "/listotalreportcomment")
    public ResponseEntity<Object> getListReportComment(){
        return reportCommentService.getListReportCommentByComment();
    }
}
