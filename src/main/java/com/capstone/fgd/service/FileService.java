package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ConvertImageRequest;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
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
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;

@Slf4j
@Service
public class FileService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadsService threadsService;

    @Autowired
    private ThreadsRepository threadsRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${upload.path}")
    private String uploadPath;

    public ResponseEntity<?> editImage(MultipartFile multipartFile, Principal principal)  {
        try {
            log.info("Executing upload image");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());
            Path uploadDir = Paths.get(uploadPath);

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String getExt = FilenameUtils.getExtension(fileName);
            log.info(getExt);
            String imageUrl = String.format("%s.%s", UUID.randomUUID(),getExt);
            log.info(imageUrl);

            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadDir.resolve(uploadPath+imageUrl);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            usersSignIn.setImage(String.valueOf(filePath));
            userRepository.save(usersSignIn);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,filePath, HttpStatus.OK);

        }catch (Exception e){
                log.error("Upload File Error :{}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> editImageBackground(MultipartFile multipartFile, Principal principal)  {
        try {
            log.info("Executing upload image");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());
            Path uploadDir = Paths.get(uploadPath);

            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String getExt = FilenameUtils.getExtension(fileName);
            log.info(getExt);
            String imageUrl = String.format("%s.%s", UUID.randomUUID(),getExt);
            log.info(imageUrl);

            InputStream inputStream = multipartFile.getInputStream();
            Path filePath = uploadDir.resolve(uploadPath+imageUrl);
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
            usersSignIn.setBackgroundImage(String.valueOf(filePath));
            userRepository.save(usersSignIn);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,filePath, HttpStatus.OK);

        }catch (Exception e){
            log.error("Upload File Error :{}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> userDeleteImage(Principal principal){
        try {
            log.info("Executing delete photo profile ");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());
            usersSignIn.setImage(null);
            userRepository.save(usersSignIn);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null, HttpStatus.OK);
        } catch (Exception e){
            log.info("Get an error by executing photo profile, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> userDeleteImageBackground(Principal principal){
        try {
            log.info("Executing delete photo background ");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());
            usersSignIn.setBackgroundImage(null);
            userRepository.save(usersSignIn);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null, HttpStatus.OK);
        } catch (Exception e){
            log.info("Get an error by executing photo profile, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<?> userLoadImage(Long id){
        try {
            log.info("Executing photo profile ");

            Optional<Users> usersOptional = userRepository.findById(id);
            if (usersOptional.isEmpty()){
                log.info("user not found/ null");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            if (usersOptional.get().getImage() == null){
                log.info("Image null");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            log.info("gagal");
            Path uploadDir = Paths.get(uploadPath);

            log.info("Name file : {}");
            Path filePath = uploadDir.resolve(usersOptional.get().getImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            File convert = resource.getFile();
            byte[] fileContent = FileUtils.readFileToByteArray(convert);
            String encodedString = Base64
                    .getEncoder()
                    .encodeToString(fileContent);

            ConvertImageRequest convertImageRequest = ConvertImageRequest.builder()
                    .id(usersOptional.get().getId())
                    .imageBase64(encodedString)
                    .build();

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, convertImageRequest,HttpStatus.OK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (Exception e){
            log.info("Get an error by executing photo profile, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }


    public ResponseEntity<?> userLoadImageBackground(Principal principal){
        try {
            log.info("Executing photo profile ");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());

            if (usersSignIn.getBackgroundImage() == null){
                log.info("Image background null");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            Path uploadDir = Paths.get(uploadPath);


            log.info("Name file : {}",usersSignIn.getImage());
            Path filePath = uploadDir.resolve(usersSignIn.getBackgroundImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            File convert = resource.getFile();
            byte[] fileContent = FileUtils.readFileToByteArray(convert);
            String encodedString = Base64
                    .getEncoder()
                    .encodeToString(fileContent);

            ConvertImageRequest convertImageRequest = ConvertImageRequest.builder()
                    .id(usersSignIn.getId())
                    .imageBase64(encodedString)
                    .build();

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, convertImageRequest,HttpStatus.OK);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (Exception e){
            log.info("Get an error by executing photo profile, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    public ResponseEntity<?> getImageThreadById(Long id){
        try {
            Optional<Threads> threadsOptional = threadsRepository.findById(id);

            if (threadsOptional.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            if (threadsOptional.get().getImage() == null){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Path uploadDir = Paths.get(uploadPath);
            log.info("Name file : {}",threadsOptional.get().getImage());
            Path filePath = uploadDir.resolve(threadsOptional.get().getImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            File convert = resource.getFile();
            byte[] fileContent = FileUtils.readFileToByteArray(convert);
            String encodedString = Base64
                    .getEncoder()
                    .encodeToString(fileContent);

            ConvertImageRequest convertImageRequest = ConvertImageRequest.builder()
                    .id(id)
                    .imageBase64(encodedString)
                    .build();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, convertImageRequest,HttpStatus.OK);
        }catch (Exception e){
            log.info("Get an error by executing image thread by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
