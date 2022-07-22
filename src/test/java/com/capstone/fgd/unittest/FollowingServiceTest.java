package com.capstone.fgd.unittest;


import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.Enum.ReportType;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.FollowingRequest;
import com.capstone.fgd.domain.dto.ReportCommentRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.FollowingRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.service.FollowingService;
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

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FollowingService.class)
public class FollowingServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private FollowingRepository followingRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private FollowingService followingService;

    @Autowired
    private Principal principal;

//    @Test
//    void getAllReportComment_Success_Test() {
//        Following following = Following.builder()
//                .id(1L)
//                .type("XXX")
//                .isFollow(true)
//                .build();
//        when(followingRepository.findAll()).thenReturn(List.of(following));
//        when(mapper.map(any(),eq(FollowingRequest.class))).thenReturn(FollowingRequest.builder()
//                .id(1L)
//                .type("XXX")
//                .user(UsersRequest.builder().id(1L).build())
//                .userFollow(UsersRequest.builder().id(1L).build())
//                .build());
//        ResponseEntity<Object> responseEntity = followingService.getAllFollowing();
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        List<FollowingRequest> followingRequestList = (List<FollowingRequest>) apiResponse.getData();
//
//        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1L, followingRequestList.size());
//    }


//    @Test
//    void createFollowing_Success_Test(){
//        Users thisusers = Users.builder()
//                .id(1L)
//                .name("Jokowi")
//                .email("jokowi3periode@gmail.com")
//                .password("Jokowi3periode")
//                .isSuspended(false)
//                .isAdmin(false)
//                .build();
//        Users usertofollow = Users.builder()
//                .id(2L)
//                .name("Megawati")
//                .email("megawatitdkpensiun@gmail.com")
//                .password("Megawat12")
//                .isSuspended(false)
//                .isAdmin(false)
//                .build();
//
//        FollowingRequest followingRequest = FollowingRequest.builder()
//                .id(1L)
//                .user(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .userFollowing(UsersRequest.builder()
//                        .id(2L)
//                        .build())
//                .build();
//
//        Following following = Following.builder()
//                .id(1L)
//                .user(Users.builder()
//                        .id(1L)
//                        .build())
//                .user_following(Users.builder()
//                        .id(2L)
//                        .build())
//                .build();
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(thisusers));
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(usertofollow));
////        when(mock().thenReturn(true);
//        when(mapper.map(any(),eq(FollowingRequest.class))).thenReturn(followingRequest);
//        when(followingRepository.save(any())).thenReturn(following);
//
//        ResponseEntity<Object> responseEntity = followingService.createFollow(FollowingRequest
//                .builder()
//                .id(1L)
//                .userFollowing(UsersRequest.builder().id(2L).build())
//                .build(), 1L);
//
//        ApiResponse apiResponse = (ApiResponse)  responseEntity.getBody();
//        FollowingRequest data = (FollowingRequest) Objects.requireNonNull(apiResponse).getData();
//        assertEquals(1L,data.getId());
//        assertEquals(1L,data.getUser().getId());
//        assertEquals(2L,data.getUserFollowing().getId());
//    }


}
