package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Thread;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ThreadRequest;
import com.capstone.fgd.repository.ThreadRepository;
import com.capstone.fgd.repository.TopicRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ThreadService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> createNewThread(ThreadRequest threadRequest){
        try {
            log.info("Executing create new Thread");
            Optional<Users> usersOptional = userRepository.findById(threadRequest.getUsers().getId());
            if (usersOptional.isEmpty()){
                log.info("User not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }
            Optional<Topic> topicOptional = topicRepository.findById(threadRequest.getTopic().getId());
            if (topicOptional.isEmpty()){
                log.info("Topic not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Thread thread = Thread.builder()
                    .title(threadRequest.getTitle())
                    .content(threadRequest.getContent())
                    .image(threadRequest.getImage())
                    .save(false)
                    .build();

            thread.setUsers(usersOptional.get());
            thread.setTopic(topicOptional.get());
            threadRepository.save(thread);
            ThreadRequest threadRequestDto = mapper.map(thread, ThreadRequest.class);

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, threadRequestDto,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing new thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateThread(Long id, ThreadRequest request){
        try {
            log.info("Executing update team");
            Optional<Thread> threadOptional = threadRepository.findById(id);

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            Thread thread = threadOptional.get();
            thread.setTitle(request.getTitle());
            thread.setContent(request.getContent());
            threadRepository.save(thread);

        return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,mapper.map(thread, ThreadRequest.class), HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing update thread, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllThread(){
        try {
            log.info("Executing get all Thread");
            List<Thread> threadList = threadRepository.findAll();
            List<ThreadRequest> threadRequestList = new ArrayList<>();

            if (threadList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Thread thread: threadList){
                threadRequestList.add(mapper.map(thread, ThreadRequest.class));
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
            Optional<Thread> threadOptional = threadRepository.findById(id);

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            Thread thread = threadOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(thread,ThreadRequest.class), HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing get thread by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteThread(Long id){
        try {
            Optional<Thread> threadOptional = threadRepository.findById(id);

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            threadRepository.delete(threadOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing delete thread");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
