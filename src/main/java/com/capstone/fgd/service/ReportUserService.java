package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.ReportThread;
import com.capstone.fgd.domain.dao.ReportUser;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.domain.dto.ReportUserRequest;
import com.capstone.fgd.repository.ReportThreadRepository;
import com.capstone.fgd.repository.ReportUserRepository;
import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ReportUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReportUserRepository reportUserRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> createNewReportUser(ReportUserRequest request, Principal principal) {
        try {
            log.info("Executing create new Report User");
            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Users> userOptional = userRepository.findById(request.getUserReport().getId());
            if (userOptional.isEmpty()) {
                log.info("User not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ReportUser reportUser = ReportUser.builder()
                    .user(userSignIn)
                    .userReport(userOptional.get())
                    .reportType(request.getReportType())
                    .build();
            reportUserRepository.save(reportUser);
            ReportUserRequest reportUserRequest = mapper.map(reportUser, ReportUserRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportUserRequest, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Get an error executing new report user, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllReportUser(){
        try {
            log.info("Executing get all report User");
            List<ReportUser> reportUserList = reportUserRepository.findAll();
            List<ReportUserRequest> reportUserRequestList = new ArrayList<>();

            if (reportUserList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (ReportUser reportUser : reportUserList){
                reportUserRequestList.add(mapper.map(reportUser, ReportUserRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportUserRequestList, HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error get all report user, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<Object> getReportuserById(Long id) {
        try {
            log.info("Executing get report user by id with id {}", id);
            Optional<ReportUser> reportUserOptional = reportUserRepository.findById(id);

            if (reportUserOptional.isEmpty()) {
                log.info("report user not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ReportUser reportUser = reportUserOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(reportUser, ReportUserRequest.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get report user by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
