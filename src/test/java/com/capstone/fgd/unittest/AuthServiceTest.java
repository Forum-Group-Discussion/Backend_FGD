package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.TokenResponse;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.security.JwtTokenProvider;
import com.capstone.fgd.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AuthService.class)
public class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthService authService;

    @Test
    void registerUsersSuccess_Test() {

        Users users = Users.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(false)
                .isSuspended(false)
                .build();
        when(userRepository.existsByEmail(users.getEmail())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(users);

        ResponseEntity<Object> responseEntity = authService.register(UsersRequest.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UsersRequest usersheck = (UsersRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void registerUsers_Admin_Success_Test() {

        Users users = Users.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(true)
                .isSuspended(false)
                .build();
        when(userRepository.existsByEmail(users.getEmail())).thenReturn(false);
        when(userRepository.save(users)).thenReturn(users);

        ResponseEntity<Object> responseEntity = authService.register(UsersRequest.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(true)
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        UsersRequest usersheck = (UsersRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
    }

    @Test
    void registerUsersFail_NullField_Test() {

        Users users = Users.builder()
                .id(1L)
                .name("")
                .email("")
                .password("")
                .isAdmin(false)
                .isSuspended(false)
                .build();

        when(userRepository.existsByEmail(users.getEmail())).thenReturn(false);
        when(userRepository.save(users)).thenReturn(users);

        ResponseEntity<Object> responseEntity = authService.register(UsersRequest.builder()
                .id(1L)
                .name("")
                .email("")
                .password("")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.COLUMN_NULL,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void registerUsersFail_Namelessthen8_Test() {

        Users users = Users.builder()
                .id(1L)
                .name("hafidz")
                .email("hafidzfebrian@gmail.com")
                .password("Jokowilover12")
                .isAdmin(false)
                .isSuspended(false)
                .build();

        when(userRepository.existsByEmail(users.getEmail())).thenReturn(false);
        when(userRepository.save(users)).thenReturn(users);

        ResponseEntity<Object> responseEntity = authService.register(UsersRequest.builder()
                .id(1L)
                .name("hafidz")
                .email("hafidzfebrian@gmail.com")
                .password("Jokowilover12")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.CHAR_LESS_8,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void registerUsersFail_Emailnothave_at_Test() {

        Users users = Users.builder()
                .id(1L)
                .name("hafidzfebrian")
                .email("hafidzfebriangmail.com")
                .password("Jokowilover12")
                .isAdmin(false)
                .isSuspended(false)
                .build();

        when(userRepository.existsByEmail(users.getEmail())).thenReturn(false);
        when(userRepository.save(users)).thenReturn(users);

        ResponseEntity<Object> responseEntity = authService.register(UsersRequest.builder()
                .id(1L)
                .name("hafidzfebrian")
                .email("hafidzfebriangmail.com")
                .password("Jokowilover12")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals("EMAIL_NOT_VALID",Objects.requireNonNull(apiResponse).getMessage());
    }


    @Test
    void registerUsersFail_Password_at_Test() {

        Users users = Users.builder()
                .id(1L)
                .name("hafidzfebrian")
                .email("hafidzfebrian@gmail.com")
                .password("Jokowilove")
                .isAdmin(false)
                .isSuspended(false)
                .build();

        when(userRepository.existsByEmail(users.getEmail())).thenReturn(false);
        when(userRepository.save(users)).thenReturn(users);

        ResponseEntity<Object> responseEntity = authService.register(UsersRequest.builder()
                .id(1L)
                .name("hafidzfebrian")
                .email("hafidzfebrian@gmail.com")
                .password("Jokowilove")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals("MUST_CONTAINS_NUMBER_AND_CHAR",Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void authenticatedAndGenerateTokenSuccess_Test() {
        Users users = Users.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(false)
                .isSuspended(false)
                .build();

        when(jwtTokenProvider.generateToken(any())).thenReturn("TOKEN");
        when(userRepository.findChildByName(any())).thenReturn(users);
        ResponseEntity<?> responseEntity = authService.authenticateAndGenerateToken(UsersRequest.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(false)
                .isSuspended(false)
                .build());
        ApiResponse response = (ApiResponse) responseEntity.getBody();
        TokenResponse tokenResponse = (TokenResponse) Objects.requireNonNull(response).getData();
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        assertEquals("TOKEN", tokenResponse.getToken());
        assertEquals("Hafidz Febrian",tokenResponse.getName());
        assertEquals(false,tokenResponse.getIsAdmin());
        assertEquals(false,tokenResponse.getIsSupended());

    }

    @Test
    void authenticatedAndGenerateTokenFail_Test() {
        Users users = Users.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(false)
                .isSuspended(false)
                .build();

        when(jwtTokenProvider.generateToken(any())).thenThrow(NullPointerException.class);
        when(userRepository.findChildByName(any())).thenReturn(users);
        ResponseEntity<?> responseEntity = authService.authenticateAndGenerateToken(UsersRequest.builder()
                .id(1L)
                .name("Hafidz Febrian")
                .email("hafidzencis@gmail.com")
                .password("jokowiloveR12")
                .isAdmin(false)
                .isSuspended(false)
                .build());
        ApiResponse apiresponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiresponse).getMessage());

    }
}