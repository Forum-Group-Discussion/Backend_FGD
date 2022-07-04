package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Slf4j
@Service
public class FileService {
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

            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadDir.resolve(uploadPath+fileName);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            usersSignIn.setUrlImage(String.valueOf(filePath));
            userRepository.save(usersSignIn);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,filePath, HttpStatus.OK);
        }catch (Exception e){
                log.error("Upload File Error :{}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> userLoadImage(Principal principal){
        try {
            log.info("Executing photo profile ");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Path uploadDir = Paths.get(uploadPath);
            log.info("Name file : {}",usersSignIn.getUrlImage());
            Path filePath = uploadDir.resolve(usersSignIn.getUrlImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

//            var imgFile = new ClassPathResource("D:/ANIME/FILES/0266fefa-6d1e-424f-ac22-5686d206face.jpg");

//            return ResponseEntity
//                    .ok()
//                    .contentType(MediaType.IMAGE_JPEG)
//                    .body(new InputStreamResource(imgFile.getInputStream()));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
//
//                    .contentType(MediaType.parseMediaType("image/jpeg, image/jpg, image/png, image/gif"))


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (Exception e){
            log.info("Get an error by executing photo profile, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
