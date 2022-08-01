package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.Enum.ReportType;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dao.ReportUser;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.ReportCommentRequest;
import com.capstone.fgd.domain.dto.ReportUserRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.CommentRepository;
import com.capstone.fgd.repository.ReportCommentRepository;
import com.capstone.fgd.repository.ReportUserRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.service.ReportCommentService;
import com.capstone.fgd.service.ReportUserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReportUserService.class)
public class ReportUserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ReportUserRepository reportUserRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ReportUserService reportUserService;

    @Test
    void getAllReportUser_Success_Test() {
        ReportUser reportUser = ReportUser.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportUserRepository.findAll()).thenReturn(List.of(reportUser));
        when(mapper.map(any(),eq(ReportUserRequest.class))).thenReturn(ReportUserRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .userReport(UsersRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportUserService.getAllReportUser();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ReportUserRequest> reportUserRequestList = (List<ReportUserRequest>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L, reportUserRequestList.size());
    }

    @Test
    void getAllReportUser_NotFound_Test(){
        ReportUser reportUser = ReportUser.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportUserRepository.findAll()).thenReturn(Collections.emptyList());
        when(mapper.map(any(),eq(ReportUserRequest.class))).thenReturn(ReportUserRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .userReport(UsersRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportUserService.getAllReportUser();
        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
        List<ReportUserRequest> reportUserRequestList = (List<ReportUserRequest>) apiResponse.getData();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getAllReportUser_Exception_Test(){
        ReportUser reportUser = ReportUser.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportUserRepository.findAll()).thenThrow(NullPointerException.class);
        when(mapper.map(any(),eq(ReportUserRequest.class))).thenReturn(ReportUserRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .userReport(UsersRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportUserService.getAllReportUser();
        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
        List<ReportUserRequest> reportUserRequestList = (List<ReportUserRequest>) apiResponse.getData();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }
}
