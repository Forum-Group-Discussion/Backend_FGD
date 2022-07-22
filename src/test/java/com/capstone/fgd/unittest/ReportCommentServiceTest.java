package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.Enum.ReportType;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dao.ReportThread;
import com.capstone.fgd.domain.dto.*;
import com.capstone.fgd.repository.*;
import com.capstone.fgd.service.ReportCommentService;
import com.capstone.fgd.service.ReportThreadService;
import com.capstone.fgd.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReportCommentService.class)
public class ReportCommentServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private ReportCommentRepository reportCommentRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ReportCommentService reportCommentService;

    @Test
    void getAllReportComment_Success_Test() {
        ReportComment reportComment = ReportComment.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportCommentRepository.findAll()).thenReturn(List.of(reportComment));
        when(mapper.map(any(),eq(ReportCommentRequest.class))).thenReturn(ReportCommentRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .comment(CommentRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportCommentService.getAllReportComment();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ReportCommentRequest> reportCommentRequestList = (List<ReportCommentRequest>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L, reportCommentRequestList.size());
    }

    @Test
    void getAllReportComment_NotFound_Test(){
        ReportComment reportComment = ReportComment.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportCommentRepository.findAll()).thenReturn(Collections.emptyList());
        when(mapper.map(any(),eq(ReportCommentRequest.class))).thenReturn(ReportCommentRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .comment(CommentRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportCommentService.getAllReportComment();
        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
        List<ReportCommentRequest> reportCommentRequestList = (List<ReportCommentRequest>) apiResponse.getData();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getAllReportComment_Exception_Test(){
        ReportComment reportComment = ReportComment.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportCommentRepository.findAll()).thenThrow(NullPointerException.class);
        when(mapper.map(any(),eq(ReportCommentRequest.class))).thenReturn(ReportCommentRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .comment(CommentRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportCommentService.getAllReportComment();
        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
        List<ReportCommentRequest> reportCommentRequestList = (List<ReportCommentRequest>) apiResponse.getData();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

//    @Test
//    void deleteReportCommentById_Success_Test() {
//        when(reportCommentRepository.findById(anyLong())).thenReturn(Optional.of(ReportComment.builder()
//                .id(1L)
//                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
//                .build()));
//        doNothing().when(reportCommentRepository).delete(any());
//        ApiResponse apiResponse =(ApiResponse) reportCommentService.deleteReportComment(1L).getBody();
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse.getMessage()));
//        verify(reportCommentRepository, times(1)).delete(any());
//    }

    @Test
    void deleteReport_NotFoundId_Test(){
        when(reportCommentRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(reportCommentRepository).delete(any());
        ResponseEntity<Object> responseEntity = reportCommentService.deleteReportComment(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void deleteReportComment_Exception_Test(){
        when(reportCommentRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(reportCommentRepository).delete(any());
        ResponseEntity<Object> responseEntity = reportCommentService.deleteReportComment(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }
}
