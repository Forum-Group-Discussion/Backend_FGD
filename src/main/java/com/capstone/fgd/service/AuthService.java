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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.io.FilenameUtils;
//import java.io.IOException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.regex.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseEntity<?> authenticateAndGenerateToken(UsersRequest req) {
        try {
            log.info(" Login ");
            if (req.getEmail().equals("")) {
                return ResponseUtil.build(ResponseMessage.EMAIL_NULL,null,HttpStatus.BAD_REQUEST);
            }

            if (req.getPassword().equals("")){
                return ResponseUtil.build(ResponseMessage.PASSWORD_NULL,null,HttpStatus.BAD_REQUEST);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);

            Optional<Users> usersOptional = Optional.ofNullable(userRepository.findByEmail(req.getEmail()));
            Users users = usersOptional.get();
            TokenResponse tokenResponse = TokenResponse.builder()
                    .token(jwt)
                    .id(users.getId())
                    .name(users.getName())
                    .isAdmin(users.getIsAdmin())
                    .isSupended(users.getIsSuspended())
                    .build();

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,
                    tokenResponse,
                    HttpStatus.OK);
        } catch (BadCredentialsException e){
            log.error("Bad Credential", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value("${upload.path}")
    private String uploadPath;

    public ResponseEntity<Object> registerWithUploadImages(UsersRequest req, MultipartFile file) {

        log.info(" Register Using Image");
        //check filled name,email,password
        if ( req.getName().equals("") || req.getEmail().equals("")|| req.getPassword().equals("")){
            log.error(" Name , Email, Password is null");
            return ResponseUtil.build(ResponseMessage.COLUMN_NULL,null, HttpStatus.BAD_REQUEST);
        }

//        check character name
        int name = req.getName().length();
        int j = 0;
        for ( int i = 0; i < name ; i ++ ){
            j++;
        }

        if (j < 4){
            log.info(" Your character less than 4");
            return ResponseUtil.build(ResponseMessage.CHAR_LESS_4,null,HttpStatus.BAD_REQUEST);
        }

        //check email
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(req.getEmail());
        if (!matcher.matches()){
            return ResponseUtil.build(ResponseMessage.EMAIL_INVALID,null,HttpStatus.BAD_REQUEST);
        }

        //check password
        int pass = req.getPassword().length();
        int x = 0;
        for ( int i = 0; i < pass ; i ++ ){
            x++;
        }

        if (x < 8){
            log.info(" Your character less than 8");
            return ResponseUtil.build(ResponseMessage.CHAR_LESS_8,null,HttpStatus.BAD_REQUEST);
        }

        String regexpassword = req.getPassword();
        String numRegex   = ".*[0-9].*";
        String alphaRegex = ".*[A-Z].*";



        if ((regexpassword.matches(numRegex) && regexpassword.matches(alphaRegex)) == false) {
            log.info("must contain number and char");
            return ResponseUtil.build("MUST_CONTAINS_NUMBER_AND_CAPITALCHAR",null,HttpStatus.BAD_REQUEST);
        }


        if (userRepository.existsByEmail(req.getEmail())) {
            log.error("User already exist");
            return ResponseUtil.build(ResponseMessage.USER_EXISTS,null, HttpStatus.BAD_REQUEST);
        }

        if (file.isEmpty()){
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
        }
        // sebagai tempat upload
        log.info("Store file");
        Path uploadDir = Paths.get(uploadPath);


        // sebagai ttempat upload
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String getExt = FilenameUtils.getExtension(fileName);
        log.info(getExt);
        String imageUrl = String.format("%s.%s", UUID.randomUUID(),getExt);
        log.info(imageUrl);

        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path filePath = uploadDir.resolve(imageUrl);
        String imageUrlSave = uploadPath + imageUrl;
        try {
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (req.getIsAdmin() == null || req.getIsAdmin().equals(false)) {
            log.info(" User ");
            Users userDao = Users.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .image(imageUrlSave)
                    .isAdmin(false)
                    .isSuspended(false)
                    .build();

            userRepository.save(userDao);
            log.info("User is Saved");

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);
        }

        if (req.getIsAdmin().equals(true) ) {
            log.info(" Admin ");
            Users userDao = Users.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .image(imageUrlSave)
                    .isAdmin(req.getIsAdmin())
                    .isSuspended(false)
                    .build();
            userRepository.save(userDao);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);

        }
        return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    public ResponseEntity<Object> register(UsersRequest req) {

        log.info(" Register ");
        //check filled name,email,password
        if ( req.getName().equals("") || req.getEmail().equals("")|| req.getPassword().equals("")){
            log.error(" Name , Email, Password is null");
            return ResponseUtil.build(ResponseMessage.COLUMN_NULL,null, HttpStatus.BAD_REQUEST);
        }

//        check character name
        int name = req.getName().length();
        int j = 0;
        for ( int i = 0; i < name ; i ++ ){
            j++;
        }

        if (j < 4){
            log.info(" Your character less than 4");
            return ResponseUtil.build(ResponseMessage.CHAR_LESS_4,null,HttpStatus.BAD_REQUEST);
        }

        //check email
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(req.getEmail());
        if (!matcher.matches()){
            return ResponseUtil.build(ResponseMessage.EMAIL_INVALID,null,HttpStatus.BAD_REQUEST);
        }

        //check password
        int pass = req.getPassword().length();
        int x = 0;
        for ( int i = 0; i < pass ; i ++ ){
            x++;
        }

        if (x < 8){
            log.info(" Your character less than 8");
            return ResponseUtil.build(ResponseMessage.CHAR_LESS_8,null,HttpStatus.BAD_REQUEST);
        }

        String regexpassword = req.getPassword();
        String numRegex   = ".*[0-9].*";
        String alphaRegex = ".*[A-Z].*";



        if ((regexpassword.matches(numRegex) && regexpassword.matches(alphaRegex)) == false) {
            log.info("must contain number and char");
            return ResponseUtil.build("MUST_CONTAINS_NUMBER_AND_CAPITALCHAR",null,HttpStatus.BAD_REQUEST);
        }


        if (userRepository.existsByEmail(req.getEmail())) {
            log.error("User already exist");
            return ResponseUtil.build(ResponseMessage.USER_EXISTS,null, HttpStatus.BAD_REQUEST);
        }

        if (req.getIsAdmin() == null || req.getIsAdmin().equals(false)) {
            log.info("Admin :{}",req.getIsAdmin());
            log.info(" User ");
            Users userDao = Users.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .image(null)
                    .isAdmin(false)
                    .isSuspended(false)
                    .build();

            userRepository.save(userDao);
            log.info("User is Saved");

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);
        }

        if (req.getIsAdmin().equals(true) ) {
            log.info(" Admin ");
            Users userDao = Users.builder()
                    .name(req.getName())
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .image(null)
                    .isAdmin(req.getIsAdmin())
                    .isSuspended(false)
                    .build();
            userRepository.save(userDao);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, null, HttpStatus.OK);

        }
        return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}