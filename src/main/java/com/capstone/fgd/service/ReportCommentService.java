package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Comment;
import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ReportCommentRequest;
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

            ReportComment reportComment = ReportComment.builder()
                    .user(userSignIn)
                    .comment(commentOptional.get())
                    .reportType(reportCommentRequest.getReportType())
                    .build();
            reportCommentRepository.save(reportComment);
            ReportCommentRequest reportCommentRequestDto = mapper.map(reportComment, ReportCommentRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, reportCommentRequestDto, HttpStatus.OK);
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
}
