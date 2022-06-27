package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Slf4j
@Service
public class FileStorageService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Value("${upload.path}")
    private String uploadPath;

    public ResponseEntity<?> saveFile(MultipartFile multipartFile, Principal principal)  {


        try {
            log.info("Executing upload file");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());
            Path uploadDir = Paths.get(uploadPath);

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            usersSignIn.setUrlImage(fileName);
            userRepository.save(usersSignIn);
            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,fileName, HttpStatus.OK);
        }catch (Exception e){
                log.error("Upload File Error :{}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
