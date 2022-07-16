package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.*;
import com.capstone.fgd.domain.dto.GetCountThread;
import com.capstone.fgd.domain.dto.GetCountThreadByUser;
import com.capstone.fgd.domain.dto.ThreadByLikeDTO;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.TopicRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.*;

@Slf4j
@Service
@Transactional
public class ThreadsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ThreadsRepository threadsRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("${upload.path}")
    private String uploadPath;

    public ResponseEntity<Object> createNewThreadUsingImage(Principal principal,ThreadsRequest threadRequest,
                                                            MultipartFile file){
        try {
            log.info("Executing create new Thread Using Image");

            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Topic> topicOptional = topicRepository.findById(threadRequest.getTopic().getId());
            if (topicOptional.isEmpty()){
                log.info("Topic not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
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

            InputStream inputStream = file.getInputStream();
            Path filePath = uploadDir.resolve(imageUrl);
            String imageUrlSave = uploadPath + imageUrl;
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);

            Threads thread = Threads.builder()
                    .users(userSignIn)
                    .topic(topicOptional.get())
                    .title(threadRequest.getTitle())
                    .content(threadRequest.getContent())
                    .image(imageUrlSave)
                    .build();
            threadsRepository.save(thread);
            ThreadsRequest threadRequestDto = mapper.map(thread, ThreadsRequest.class);

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, threadRequestDto,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing new thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> createNewThread(Principal principal,ThreadsRequest threadRequest){
        try {
            log.info("Executing create new Thread");

            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Topic> topicOptional = topicRepository.findById(threadRequest.getTopic().getId());
            if (topicOptional.isEmpty()){
                log.info("Topic not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Threads thread = Threads.builder()
                    .users(userSignIn)
                    .topic(topicOptional.get())
                    .title(threadRequest.getTitle())
                    .content(threadRequest.getContent())
                    .image(null)
                    .build();
            threadsRepository.save(thread);
            ThreadsRequest threadRequestDto = mapper.map(thread, ThreadsRequest.class);

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, threadRequestDto,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing new thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> getAllThread(){
        try {
            log.info("Executing get all Thread");
            List<Threads> threadList = threadsRepository.findAll();
            List<ThreadsRequest> threadRequestList = new ArrayList<>();

            if (threadList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Threads thread: threadList){
                threadRequestList.add(mapper.map(thread, ThreadsRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, threadRequestList, HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error get all thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getThreadById(Long id){
        try {
            log.info("Executing getThreadById with id : {}", id);
            Optional<Threads> threadOptional = threadsRepository.findById(id);

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            Threads thread = threadOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(thread, ThreadsRequest.class), HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing get thread by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getThreadByTopic(Long request){
        try {
            log.info("Executing get All Thread By Topic");
            List<Threads> threadsList = threadsRepository.getAllThreadByTopic(request);
            List<ThreadsRequest> threadsRequestList = new ArrayList<>();

            if (threadsList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Threads threads: threadsList){
                threadsRequestList.add(mapper.map(threads, ThreadsRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, threadsRequestList, HttpStatus.OK);
        }catch (Exception e){
            log.error("Get An Error get thread by topic : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> getAllThreadByNew(){
        try {
            log.info("Executing get All Thread Order By DSC");
            List<Threads> threadsList = threadsRepository.getThreadDESC();
            List<ThreadsRequest> threadsRequestList = new ArrayList<>();

            if (threadsList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);

            }

            for (Threads threads: threadsList){
                threadsRequestList.add(mapper.map(threads, ThreadsRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,threadsRequestList, HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error get thread order by dsc : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getListThreadByLike(){
        try {
            log.info("Executing get All Thread By Like ");
            List<ThreadByLikeDao> joinThreadLKS = threadsRepository.getListThreadByLike();
            List<ThreadByLikeDTO> threadsRequestList = new ArrayList<>();
            log.info("JOIN THREAD LKS: {}",joinThreadLKS);


            for (ThreadByLikeDao thread: joinThreadLKS){
                log.info("Like : {}",thread.getLike());
                threadsRequestList.add(ThreadByLikeDTO.builder()
                                .id(thread.getId())
                                .like(thread.getLike())
                                .title(thread.getTitle())
                                .content(thread.getContent())
                                .created_at(thread.getCreated_At())
                                .name_user(thread.getName_User())
                        .build());
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,threadsRequestList, HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error get thread order by dsc : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateThread(Long id, ThreadsRequest request){
        try {
            log.info("Executing update thread");
            Optional<Threads> threadOptional = threadsRepository.findById(id);

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            Threads thread = threadOptional.get();
            thread.setTitle(request.getTitle());
            thread.setContent(request.getContent());
            threadsRepository.save(thread);
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,mapper.map(thread, ThreadsRequest.class), HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing update thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteThread(Integer id){
        try {
            log.info("DELETE THREAD BY ID");
            Optional<Threads> threadOptional = threadsRepository.findById(Long.valueOf(id));

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            threadsRepository.deleteThreadFromSaveThread(id);
            List<GetCommentByThreadId> comments = threadsRepository.getidCommentByThreadId(id);

            for (GetCommentByThreadId c: comments){
                log.info("Id comment : {}",c.getId());
                threadsRepository.deleteThreadFromReportComment(c.getId());
                threadsRepository.deleteThreadFromLikeComment(c.getId());
            }

            threadsRepository.deleteThreadFromComment(id);
            threadsRepository.deleteThreadFromReportThread(id);
            threadsRepository.deleteThreadFromLikeThread(id);

            threadsRepository.delete(threadOptional.get());
            log.info("DELETE THREAD SUCCESS");
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);


        } catch (Exception e){
            log.error("Get an error by executing delete thread : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    public ResponseEntity<?> getAllThreadWithPagination(Long offset,Long limit){
        try {
            log.info("Get All Thread With Pagination");
            List<Threads> threadsList = threadsRepository.getAllThreadDESCUsingPagination(offset,limit);
            List<ThreadsRequest> threadsRequestList = new ArrayList<>();

            if (threadsList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.OK);
            }

            for (Threads threads : threadsList){
                threadsRequestList.add(mapper.map(threads,ThreadsRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,threadsRequestList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get all thread with pagination, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchByThread(String req){
        try {
            log.info("Executing searchByThread");
            List<Threads> threadsList = threadsRepository.searchByThread(req);
            List<ThreadsRequest> threadsRequestList = new ArrayList<>();

            if (threadsList.isEmpty()){
                log.info("not found any thread");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Threads threads:threadsList){
                threadsRequestList.add(mapper.map(threads,ThreadsRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,threadsRequestList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by searching thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getTotalThread(){
        try {
            log.info("Executing total thread");
            Integer getTotalThread = threadsRepository.getCountThread();

            GetCountThread getCountThread =GetCountThread.builder()
                    .totalThread(getTotalThread)
                    .build();



            return ResponseUtil.build(ResponseMessage.KEY_FOUND,getCountThread,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by searching thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getCountThreadByUser(Principal principal){
        try {
            log.info("Executing total thread by user");
            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());


            Long getTotalThreadByUser = threadsRepository.countThreadByUserId(userSignIn.getId());

            GetCountThreadByUser getCountThread = GetCountThreadByUser.builder()

                    .totalThreadByUser(getTotalThreadByUser)
                    .build();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,getCountThread,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by searching thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
