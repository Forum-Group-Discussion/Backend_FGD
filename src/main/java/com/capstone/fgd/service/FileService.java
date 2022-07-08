package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
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
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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


    public ResponseEntity<?> userLoadImage(Principal principal){
        try {
            log.info("Executing photo profile ");
            Users usersSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Path uploadDir = Paths.get(uploadPath);
            log.info("Name file : {}",usersSignIn.getImage());
            Path filePath = uploadDir.resolve(usersSignIn.getImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());


            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

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

            Path uploadDir = Paths.get(uploadPath);
            log.info("Name file : {}",usersSignIn.getImage());
            Path filePath = uploadDir.resolve(usersSignIn.getBackgroundImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

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

            Path uploadDir = Paths.get(uploadPath);
            log.info("Name file : {}",threadsOptional.get().getImage());
            Path filePath = uploadDir.resolve(threadsOptional.get().getImage()).normalize();
            Resource resource = new UrlResource(filePath.toUri());


            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

        }catch (Exception e){
            log.info("Get an error by executing image thread by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<?> getAllThreadUsingImage(){
        try {
            log.info("Get All Thread Using Image");

            List<ThreadsRequest> threadsRequestList = new ArrayList<>();
            List<Threads> threadsList = threadsRepository.getAllImage();


            for (Threads threads1: threadsList){
                threadsRequestList.add(mapper.map(threads1, ThreadsRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,threadsRequestList,HttpStatus.OK);


        }catch (Exception e){
            log.info("Get an error by executing all iamge thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
