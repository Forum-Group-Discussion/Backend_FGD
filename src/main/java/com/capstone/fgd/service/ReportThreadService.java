package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.GetListTotalReportThread;
import com.capstone.fgd.domain.dao.ReportThread;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.GetListTotalReportThreadDTO;
import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.repository.ReportThreadRepository;
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
public class ReportThreadService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadsRepository threadRepository;

    @Autowired
    private ReportThreadRepository reportThreadRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> createNewReportThread(ReportThreadRequest reportThreadRequest, Principal principal) {
        try {
            log.info("Executing create new Report Thread");
            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Threads> threadOptional = threadRepository.findById(reportThreadRequest.getThread().getId());

            Optional<ReportThread> optionalReportThread = reportThreadRepository.hasBeenReportThread(userSignIn.getId(),
                    reportThreadRequest.getThread().getId());

            if (threadOptional.isEmpty()) {
                log.info("Thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            if (optionalReportThread.isPresent()){
                log.info("has been report thread");
                return ResponseUtil.build(ResponseMessage.SUCCESS_REPORT_THREAD,null,HttpStatus.BAD_REQUEST);
            }

            ReportThread reportThread = ReportThread.builder()
                    .user(userSignIn)
                    .thread(threadOptional.get())
                    .reportType(reportThreadRequest.getReportType())
                    .build();
            reportThreadRepository.save(reportThread);
            ReportThreadRequest reportThreadRequestDto = mapper.map(reportThread, ReportThreadRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportThreadRequestDto, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Get an error executing new report thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllReportThread(){
        try {
            log.info("Executing get all report thread");
            List<ReportThread> reportThreadList = reportThreadRepository.findAll();
            List<ReportThreadRequest> reportThreadRequestList = new ArrayList<>();

            if (reportThreadList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (ReportThread reportThread : reportThreadList){
                reportThreadRequestList.add(mapper.map(reportThread, ReportThreadRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportThreadRequestList, HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error get all report thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    public ResponseEntity<Object> getReportThreadById(Long id) {
        try {
            log.info("Executing get report thread by id with id {}", id);
            Optional<ReportThread> reportThreadOptional = reportThreadRepository.findById(id);

            if (reportThreadOptional.isEmpty()) {
                log.info("report thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ReportThread reportThread = reportThreadOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(reportThread, ReportThreadRequest.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get report thread by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> getListReportThreadByThread() {
        try {

            log.info("Executing List report  thread by thread");
            List<GetListTotalReportThread> getListTotalReportThreadList = reportThreadRepository.getListTotalReport();
            List<GetListTotalReportThreadDTO> getListTotalReportThreadDTOS = new ArrayList<>();

            if (getListTotalReportThreadList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (GetListTotalReportThread list : getListTotalReportThreadList){
                getListTotalReportThreadDTOS.add(GetListTotalReportThreadDTO.builder()
                                .thread_id(list.getThread_Id())
                                .total_report_thraed(list.getTotal_Report_Thread())
                        .build());
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, getListTotalReportThreadDTOS, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing list total report thread");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteReportThread(Long id) {
        try {
            Optional<ReportThread> reportThreadOptional = reportThreadRepository.findById(id);
            if (reportThreadOptional.isEmpty()) {
                log.info("report thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            reportThreadRepository.delete(reportThreadOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing delete report thread");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
