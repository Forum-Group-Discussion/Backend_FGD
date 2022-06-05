package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.TokenResponse;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.security.JwtTokenProvider;
import com.capstone.fgd.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Object> register(UsersRequest req) {

        log.info("Register -> name : {},email : {}, password : {}, isAdmin", req.getName());
        if ( req.getName().equals("") || req.getEmail().equals("")|| req.getPassword().equals("")){
                log.error(" Name , Email, Password is null");
                return ResponseUtil.build(ResponseMessage.COLUMN_NULL,null, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(req.getEmail())) {
            log.error("User already exist");
            return ResponseUtil.build(ResponseMessage.USER_EXISTS,null, HttpStatus.BAD_REQUEST);
        }

        if (req.getIsAdmin() == null || req.getIsAdmin().equals(false)) {
               log.info(" User ");
               Users userDao = Users.builder()
                       .name(req.getName())
                       .username(req.getEmail())
                       .password(passwordEncoder.encode(req.getPassword()))
                       .isAdmin(false)
                       .build();

               userRepository.save(userDao);
               log.info("User is Saved");

               return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);
        }

        if (req.getIsAdmin().equals(true) ) {
            log.info(" Admin ");
            Users userDao = Users.builder()
                    .name(req.getName())
                    .username(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .isAdmin(req.getIsAdmin())
                    .build();
            userRepository.save(userDao);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);

        }
        return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);

    }


    public ResponseEntity<?> authenticateAndGenerateToken(UsersRequest req) {
        try {
            if (req.getEmail().equals(null)) {
                return ResponseUtil.build(ResponseMessage.EMAIL_NULL,null,HttpStatus.BAD_REQUEST);
            }

            if (req.getPassword().equals(null)){
                return ResponseUtil.build(ResponseMessage.PASSWORD_NULL,null,HttpStatus.BAD_REQUEST);
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generationToken(authentication);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,
                    TokenResponse.
                            builder().token(jwt).build(),
                    HttpStatus.OK);
        } catch (BadCredentialsException e){
            log.error("Bad Credential", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
