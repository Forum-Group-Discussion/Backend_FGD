package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.TopicRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
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

    public ResponseEntity<Object> createNewThread(ThreadsRequest threadRequest, Principal principal){
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
                    .image(threadRequest.getImage())
                    .save(false)
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

    public ResponseEntity<Object> updateThread(Long id, ThreadsRequest request){
        try {
            log.info("Executing update team");
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

    public ResponseEntity<Object> deleteThread(Long id){
        try {
            Optional<Threads> threadOptional = threadsRepository.findById(id);

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            threadsRepository.delete(threadOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing delete thread");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
