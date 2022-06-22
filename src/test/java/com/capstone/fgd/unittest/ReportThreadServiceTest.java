package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dto.ReportThreadRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.service.ReportThreadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ReportThreadService.class)
public class ReportThreadServiceTest {

    @MockBean
    private UserRepository userRepository;

//    @MockBean
//    private ThreadRepository threadRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ReportThreadService reportThreadService;

//    @Test
//    void addReportThread_Success_Test(){
//        Users users = Users.builder()
//                .id(1L)
//                .build();
////        Threads thread = Threads.builder()
////                .id(1L)
////                .build();
//
//        ReportThread reportThread = ReportThread.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .build();
//
//        ReportThreadRequest reportThreadRequest = ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(users));
//        when(mapper.map(any(), eq(ReportThread.class))).thenReturn(reportThread);
//        when(mapper.map(any(), eq(ReportThreadRequest.class))).thenReturn(reportThreadRequest);
//
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .id(1L)
//                .reportType(ReportType.Fraud)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        ReportThread data = (ReportThread) Objects.requireNonNull(apiResponse).getData();
//        assertEquals(1L, data.getId());
//        assertEquals(ReportType.Fraud,data.getReportType());
//        assertEquals(1L,data.getUser().getId());
//    }

//    @Test
//    void addUser_NotFound_Test(){
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = reportThreadService.createNewReportThread(ReportThreadRequest.builder()
//                .id(1L)
//                .users(UsersRequest.builder().id(1L).build())
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
//                .users(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }

    @Test
    void getAllReportThread() {
    }

    @Test
    void getReportThreadById() {
    }

    @Test
    void updateReportThread() {
    }

    @Test
    void deleteReportThread() {
    }
}