package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.*;
import com.capstone.fgd.domain.dto.GetCountReportDTO;
import com.capstone.fgd.domain.dto.GetListTotalReportThreadDTO;
import com.capstone.fgd.domain.dto.GetListTotalReportUserDTO;
import com.capstone.fgd.domain.dto.ReportUserRequest;
import com.capstone.fgd.repository.ReportUserRepository;
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

            Optional<ReportUser> optionalReportUser = reportUserRepository.hasBeenReportUser(userSignIn.getId(),request.getUserReport().getId());

            Optional<ReportUser> reporrUrSelf = reportUserRepository.cantReportYourSelf(userSignIn.getId(),request.getUserReport().getId());

            if (optionalReportUser.isPresent()){
                log.info("has been report user");
                return ResponseUtil.build(ResponseMessage.SUCCESS_REPORT_USER,null,HttpStatus.BAD_REQUEST);
            }

            if (reporrUrSelf.isPresent()){
                return ResponseUtil.build("CAN'T_REPORT_YOURSELF",null,HttpStatus.BAD_REQUEST);
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

    public ResponseEntity<Object> getTotalReport() {
        try {
            log.info("Executing get total report ");
           Integer getCountReports = reportUserRepository.getCountReport();

            GetCountReportDTO getCountReportDTO = GetCountReportDTO.builder()
                    .totalReport(getCountReports)
                    .build();


            return ResponseUtil.build(ResponseMessage.KEY_FOUND, getCountReportDTO, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get total report , Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getListReportUser() {
        try {

            log.info("Executing List report user by user_report_id");

            List<GetListTotalReportUser> getListTotalReportUsers = reportUserRepository.getListTotalReportUser();
            List<GetListTotalReportUserDTO> getListTotalReportUserDTOS = new ArrayList<>();

            if (getListTotalReportUsers.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (GetListTotalReportUser list : getListTotalReportUsers){
                getListTotalReportUserDTOS.add(GetListTotalReportUserDTO.builder()
                                .user_report_id(list.getUser_Report_Id())
                                .total_report_user(list.getTotal_Report_User())
                        .build());
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, getListTotalReportUserDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing list total report thread");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
