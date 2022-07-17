package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.Enum.ReportType;
import com.capstone.fgd.domain.dao.*;
import com.capstone.fgd.domain.dto.GetListTotalReportCommentDTO;
import com.capstone.fgd.domain.dto.GetListTotalReportThreadDTO;
import com.capstone.fgd.domain.dto.ReportCommentRequest;
import com.capstone.fgd.domain.dto.ReportTypeDTO;
import com.capstone.fgd.repository.CommentRepository;
import com.capstone.fgd.repository.ReportCommentRepository;
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
public class ReportCommentService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReportCommentRepository reportCommentRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    public ResponseEntity<Object> createNewReportComment(ReportCommentRequest reportCommentRequest, Principal principal) {
        try {
            log.info("Executing create new Report Comment");
            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Comment> commentOptional = commentRepository.findById(reportCommentRequest.getComment().getId());
            if (commentOptional.isEmpty()) {
                log.info("Comment not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Optional<ReportComment> optionalReportComment = reportCommentRepository.hasBeenReportComment(userSignIn.getId());

            if (optionalReportComment.isPresent()){
                return ResponseUtil.build(ResponseMessage.SUCCESS_REPORT_COMMENT,null,HttpStatus.BAD_REQUEST);
            }

            if (optionalReportComment.isEmpty()){
                ReportComment reportComment = ReportComment.builder()
                        .user(userSignIn)
                        .comment(commentOptional.get())
                        .reportType(reportCommentRequest.getReportType())
                        .build();
                reportCommentRepository.save(reportComment);
                ReportCommentRequest reportCommentRequestDto = mapper.map(reportComment, ReportCommentRequest.class);
                return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportCommentRequestDto, HttpStatus.OK);
            }
            return null;


        } catch (Exception e) {
            log.error("Get an error executing new report comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllReportComment() {
        try {
            log.info("Executing get all report comment");
            List<ReportComment> reportCommentList = reportCommentRepository.findAll();
            List<ReportCommentRequest> reportCommentRequestList = new ArrayList<>();

            if (reportCommentList.isEmpty()) {
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            for (ReportComment reportComment : reportCommentList) {
                reportCommentRequestList.add(mapper.map(reportComment, ReportCommentRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportCommentRequestList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error get all report comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getReportCommentById(Long id) {
        try {
            log.info("Executing get report comment by id with id {}", id);
            Optional<ReportComment> reportCommentOptional = reportCommentRepository.findById(id);

            if (reportCommentOptional.isEmpty()) {
                log.info("report comment not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ReportComment reportComment = reportCommentOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(reportComment, ReportCommentRequest.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get report comment by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateReportComment(Long id, ReportCommentRequest request) {
        try {
            log.info("Executing update Report Comment");
            Optional<ReportComment> reportCommentOptional = reportCommentRepository.findById(id);

            if (reportCommentOptional.isEmpty()) {
                log.info("report comment not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            ReportComment reportComment = reportCommentOptional.get();
            reportComment.setReportType(request.getReportType());
            reportCommentRepository.save(reportComment);
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, mapper.map(reportComment, ReportCommentRequest.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error executing update report comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllReportType() {
        try {
            log.info("Executing get all report ");

            List<ReportTypeDTO> reportTypeList = new ArrayList<>();

            ReportType[] rt = ReportType.values();
            for (int i = 1; i <= rt.length; i++) {
                ReportType r = rt[i-1];
                reportTypeList.add(ReportTypeDTO.builder()
                                .id(Long.valueOf(i))
                                .reportType(String.valueOf(r))
                        .build());
            }


            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportTypeList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error get all report, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteReportComment(Long id) {
        try {
            Optional<ReportComment> reportCommentOptional = reportCommentRepository.findById(id);
            if (reportCommentOptional.isEmpty()) {
                log.info("report comment not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            reportCommentRepository.delete(reportCommentOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error executing delete report comment");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getListReportCommentByComment() {
        try {

            log.info("Executing List report  thread by thread");
            List<GetListTotalReportComment> getListTotalReportComments = reportCommentRepository.getListTotalReportComment();
            List<GetListTotalReportCommentDTO> getListTotalReportCommentDTOList = new ArrayList<>();

            if (getListTotalReportComments.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (GetListTotalReportComment list : getListTotalReportComments){
                getListTotalReportCommentDTOList.add(GetListTotalReportCommentDTO.builder()
                        .comment_id(list.getComment_Id())
                        .total_report_comment(list.getTotal_Report_Comment())
                        .build());
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, getListTotalReportCommentDTOList, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing list total report comment");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
