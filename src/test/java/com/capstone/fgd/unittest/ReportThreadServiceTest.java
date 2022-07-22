package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.Enum.ReportType;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.ReportThread;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.ReportThreadRepository;
import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.UserRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReportThreadService.class)
public class ReportThreadServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ThreadsRepository threadRepository;

    @MockBean
    private ReportThreadRepository reportThreadRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ReportThreadService reportThreadService;

//    @Test
//    void addReportThread_Success_Test(){
//        Users user = Users.builder()
//                .id(1L)
//                .build();
//        Threads thread = Threads.builder()
//                .id(1L)
//                .build();
//
//        ReportThread reportThread = ReportThread.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .build();
//
//        ReportThreadRequest reportThreadRequest = ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//
////        when(userService.loadUserByUsername(anyLong())).thenReturn
////        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread));
//        when(mapper.map(any(), eq(ReportThread.class))).thenReturn(reportThread);
//        when(mapper.map(any(), eq(ReportThreadRequest.class))).thenReturn(reportThreadRequest);
//
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        ReportThread data = (ReportThread) Objects.requireNonNull(apiResponse).getData();
//        assertEquals(1L, data.getId());
//        assertEquals(ReportType.Fraud,data.getReportType());
//        assertEquals(1L,data.getUser().getId());
//        assertEquals(1L,data.getThread().getId());
//    }
//
//    @Test
//    void addUser_NotFound_Test(){
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .id(1L)
//                .user(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void addUser_Exception_Test(){
//        when(userRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .user(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }

//    @Test
//    void AddThread_NotFound_Test(){
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        when(threadRepository.findById(1L)).thenReturn(Optional.of(Threads.builder().build()));
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .id(1L)
//                .thread(ThreadsRequest.builder().id(1L).build())
//                .user(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void addThread_Exception_Test(){
//        Users user = Users.builder()
//                .id(1L)
//                .build();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(threadRepository.save(anyLong())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .user(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void addReportThread_Exception_Test(){
//        Users user = Users.builder()
//                .id(1L)
//                .build();
//        Threads thread = Threads.builder()
//                .id(1L)
//                .build();
//
//        ReportThread reportThread = ReportThread.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .build();
//
//        ReportThreadRequest reportThreadRequest = ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread));
//        when(mapper.map(any(), eq(ReportThread.class))).thenReturn(reportThread);
//        when(mapper.map(any(), eq(ReportThreadRequest.class))).thenReturn(reportThreadRequest);
//        when(reportThreadRepository.save(any())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
    @Test
    void getAllReportThread_Success_Test() {
        ReportThread reportThread = ReportThread.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportThreadRepository.findAll()).thenReturn(List.of(reportThread));
        when(mapper.map(any(),eq(ReportThreadRequest.class))).thenReturn(ReportThreadRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .thread(ThreadsRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportThreadService.getAllReportThread();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<ReportThreadRequest> reportThreadRequestList = (List<ReportThreadRequest>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L, reportThreadRequestList.size());
    }

    @Test
    void getAllReportThread_NotFound_Test(){
        ReportThread reportThread = ReportThread.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Bullying_elements)
                .build();
        when(reportThreadRepository.findAll()).thenReturn(Collections.emptyList());
        when(mapper.map(any(),eq(ReportThreadRequest.class))).thenReturn(ReportThreadRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Bullying_elements)
                .user(UsersRequest.builder().id(1L).build())
                .thread(ThreadsRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportThreadService.getAllReportThread();
        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
        List<ReportThreadRequest> reportThreadRequestList = (List<ReportThreadRequest>) apiResponse.getData();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void getAllReportThread_Exception_Test(){
        ReportThread reportThread = ReportThread.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build();
        when(reportThreadRepository.findAll()).thenThrow(NullPointerException.class);
        when(mapper.map(any(),eq(ReportThreadRequest.class))).thenReturn(ReportThreadRequest.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .user(UsersRequest.builder().id(1L).build())
                .thread(ThreadsRequest.builder().id(1L).build())
                .build());
        ResponseEntity<Object> responseEntity = reportThreadService.getAllReportThread();
        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
        List<ReportThreadRequest> reportThreadRequestList = (List<ReportThreadRequest>) apiResponse.getData();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

//    @Test
//    void getReportThreadById_Success_Test() {
//        ReportThread reportThread = ReportThread.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .build();
//        ReportThreadRequest reportThreadRequest = ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(reportThreadRepository.findById(anyLong())).thenReturn(Optional.of(reportThread));
//        when(mapper.map(any(), eq(ReportThreadRequest.class))).thenReturn(reportThreadRequest);
//        ResponseEntity<Object> responseEntity = reportThreadService.getReportThreadById(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        ReportThreadRequest data = (ReportThreadRequest) apiResponse.getData();
//        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1L, data.getId());
//        assertEquals(ReportType.Fraud,data.getReportType());
//        assertEquals(1L,data.getUser().getId());
//        assertEquals(1L,data.getThread().getId());
//    }
//
//    @Test
//    void getReportThreadById_NotFound_Test(){
//        when(reportThreadRepository.findById(anyLong())).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = reportThreadService.getReportThreadById(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void getReportThreadById_Exception_Test(){
//        ReportThread reportThread = ReportThread.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .build();
//        ReportThreadRequest reportThreadRequest = ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(reportThreadRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        when(mapper.map(any(), eq(ReportThreadRequest.class))).thenReturn(reportThreadRequest);
//        ResponseEntity<Object> responseEntity = reportThreadService.getReportThreadById(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void updateReportThread_Success_Test(){
//        ReportThread reportThread = ReportThread.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .build();
//        ReportThreadRequest reportThreadRequest = ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(reportThreadRepository.findById(anyLong())).thenReturn(Optional.of(reportThread));
//        when(mapper.map(any(), eq(ReportThread.class))).thenReturn(reportThread);
//        when(mapper.map(any(), eq(ReportThreadRequest.class))).thenReturn(reportThreadRequest);
//        when(reportThreadRepository.save(any())).thenReturn(reportThread);
//        ResponseEntity<Object> responseEntity = reportThreadService.updateReportThread(1L, ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        ReportThread data = (ReportThread) Objects.requireNonNull(apiResponse).getData();
//        assertEquals(1L, data.getId());
//        assertEquals(ReportType.Fraud,data.getReportType());
//        assertEquals(1L,data.getUser().getId());
//        assertEquals(1L,data.getThread().getId());
//    }
//
//    @Test
//    void updateReportThread_NotFound_Test(){
//        when(reportThreadRepository.findById(anyLong())).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = reportThreadService.updateReportThread(1L, ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void updateReportThread_Exception_Test(){
//        when(reportThreadRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = reportThreadService.updateReportThread(1L, ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .thread(ThreadsRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
    @Test
    void deleteReportThreadById_Success_Test() {
        when(reportThreadRepository.findById(anyLong())).thenReturn(Optional.of(ReportThread.builder()
                .id(1L)
                .reportType(ReportType.This_thread_contains_inappropriate_and_Fraud_elements)
                .build()));
        doNothing().when(reportThreadRepository).delete(any());
        ApiResponse apiResponse =(ApiResponse) reportThreadService.deleteReportThread(1L).getBody();
        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse.getMessage()));
        verify(reportThreadRepository, times(1)).delete(any());
    }

    @Test
    void deleteReport_NotFoundId_Test(){
        when(reportThreadRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(reportThreadRepository).delete(any());
        ResponseEntity<Object> responseEntity = reportThreadService.deleteReportThread(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void deleteReportThread_Exception_Test(){
        when(reportThreadRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(reportThreadRepository).delete(any());
        ResponseEntity<Object> responseEntity = reportThreadService.deleteReportThread(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

}