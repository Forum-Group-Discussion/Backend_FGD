package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.service.AuthService;
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

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = UserService.class)
public class UserServiceTest {
    @MockBean
    private ModelMapper mapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void loadUserByUsername_Success_Test() {
        Users users = Users.builder()
                .id(1L)
                .name("Alzeta")
                .ausername("Al")
                .email("alzeta@gmail.com")
                .password("Alzeta12")
                .image("gambar.jpg")
                .bio("sukses")
                .location("Bandung")
                .website("www.gfd.com")
                .backgroundImage("dfg.jpg")
                .build();
        when(userRepository.findByEmail(any())).thenReturn(users);
        UserDetails userDetails = userService.loadUserByUsername("alzeta@gmail.com");
        assertEquals("alzeta@gmail.com", userDetails.getUsername());
    }

//    @Test
//    void getAllUser_NotFound_Test(){
//        Users users = Users.builder()
//                .id(1L)
//                .name("Alzeta")
//                .ausername("Al")
//                .email("alzeta@gmail.com")
//                .password("Alzeta12")
//                .image("gambar.jpg")
//                .bio("sukses")
//                .location("Bandung")
//                .website("www.gfd.com")
//                .backgroundImage("dfg.jpg")
//                .build();
//        when(userRepository.findAll()).thenReturn(Collections.emptyList());
//        ResponseEntity<Object> responseEntity = userService.getAllUser();
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        List<UsersRequest> userRequestList = (List<UsersRequest>) apiResponse.getData();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse));
//    }

    @Test
    public void getUserById_Success_Test() {
        Users users = Users.builder()
                .id(1L)
                .name("Alzeta")
                .ausername("Al")
                .email("alzeta@gmail.com")
                .password("Alzeta12")
                .image("gambar.jpg")
                .bio("sukses")
                .location("Bandung")
                .website("www.gfd.com")
                .backgroundImage("dfg.jpg")
                .build();
        UsersRequest usersRequest = UsersRequest.builder()
                .id(1L)
                .name("Alzeta")
                .ausername("Al")
                .email("alzeta@gmail.com")
                .password("Alzeta12")
                .image("gambar.jpg")
                .bio("sukses")
                .location("Bandung")
                .website("www.gfd.com")
                .backgroundImage("dfg.jpg")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(users));
        when(mapper.map(any(), eq(UsersRequest.class))).thenReturn(usersRequest);
        ResponseEntity<Object> responseEntity = userService.getUserByid(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UsersRequest data = (UsersRequest) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L, data.getId());
        assertEquals("Alzeta", data.getName());
        assertEquals("alzeta@gmail.com", data.getEmail());
    }

    @Test
    public void getUserById_NotFound_Test() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = userService.getUserByid(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void getUserById_Exception_Test() {
        when(userRepository.findById(1L)).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = userService.getUserByid(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void updateUser_Success_Test(){
        Users users = Users.builder()
                .id(1L)
                .name("Alzeta")
                .ausername("Al")
                .email("alzeta@gmail.com")
                .password("Alzeta12")
                .image("gambar.jpg")
                .bio("sukses")
                .location("Bandung")
                .website("www.gfd.com")
                .backgroundImage("dfg.jpg")
                .build();
        UsersRequest usersRequest = UsersRequest.builder()
                .id(1L)
                .name("Alzeta")
                .ausername("Al")
                .email("alzeta@gmail.com")
                .password("Alzeta12")
                .image("gambar.jpg")
                .bio("sukses")
                .location("Bandung")
                .website("www.gfd.com")
                .backgroundImage("dfg.jpg")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(users));
        when(mapper.map(any(),eq(Users.class))).thenReturn(users);
        when(mapper.map(any(),eq(UsersRequest.class))).thenReturn(usersRequest);
        when(userRepository.save(any())).thenReturn(users);
        ResponseEntity<Object> responseEntity = userService.updateUser(1L,UsersRequest.builder()
                .id(1L)
                .name("Alzeta")
                .email("alzeta@gmail.com")
                .image("gambar.jpg")
                .bio("sukses")
                .location("Bandung")
                .website("www.gfd.com")
                .backgroundImage("dfg.jpg")
                .build());

        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UsersRequest data = (UsersRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L, data.getId());
        assertEquals("Alzeta", data.getName());
        assertEquals("alzeta@gmail.com", data.getEmail());

    }
}
