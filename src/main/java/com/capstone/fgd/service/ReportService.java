package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Report;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dto.ReportRequest;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.repository.ReportRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadsService threadsService;

    @Autowired
    private ModelMapper mapper;


    public ResponseEntity<Object> createNewReport(ReportRequest request){
        try {
            log.info("Executing create new report");
            Report report = Report.builder()
                    .content(request.getContent())
                    .build();
            reportRepository.save(report);
            ReportRequest reportRequest = mapper.map(report,ReportRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,reportRequest, HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing create new report, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllReport(){
        try {
            log.info("Executing get all report");
            List<Report> reportList = reportRepository.findAll();
            List<ReportRequest> reportRequestList = new ArrayList<>();

            if (reportList.isEmpty()){
                log.info("data report is empty");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Report report: reportList){
                reportRequestList.add(mapper.map(report,ReportRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,reportRequestList,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error for executing get all report, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> getReportById(Long id){
        try {
            log.info("Executing get report with id : {}", id);
            Optional<Report> reportOptional = reportRepository.findById(id);

            if (reportOptional.isEmpty()){
                log.info("report not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            ReportRequest reportRequest = mapper.map(reportOptional.get(),ReportRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportRequest, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get report by id : {} , Error : {}",id, e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateReport(Long id, ReportRequest request){
        try {
            log.info("Executing update report with id : {}",id);
            Optional<Report> reportOptional = reportRepository.findById(id);

            if (reportOptional.isEmpty()){
                log.info("report not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            Report report = reportOptional.get();
            report.setContent(request.getContent());
            reportRepository.save(report);
            ReportRequest reportRequest = mapper.map(report,ReportRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,reportRequest,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by update report, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteReport(Long id){
        try {
            Optional<Report> reportOptional = reportRepository.findById(id);

            if (reportOptional.isEmpty()){
                log.info("report not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            reportRepository.delete(reportOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing delete report");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
