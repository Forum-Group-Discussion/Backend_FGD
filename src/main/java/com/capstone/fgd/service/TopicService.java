package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.repository.TopicRepository;
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
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> createNewTopic(TopicRequest request){
        try {
            log.info("Executing create new Topic");
            Topic topic = Topic.builder()
                    .id(request.getId())
                    .topicName(request.getTopicName())
                    .build();
            topicRepository.save(topic);
            TopicRequest topicRequest = mapper.map(topic, TopicRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,request, HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing create new topic, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateTopic(Long id, TopicRequest request){
        try {
            log.info("Executing update topic with id, Id : {}",id);
            Optional<Topic> topicOptional = topicRepository.findById(id);
            if (topicOptional.isEmpty()){
                log.error("topic not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            Topic topic = topicOptional.get();
            topic.setTopicName(request.getTopicName());
            topicRepository.save(topic);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,mapper.map(topic,TopicRequest.class),HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by update topic, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllTopic(){
        try {
            log.info("Executing get all topic");
            List<Topic> topicList = topicRepository.findAll();
            List<TopicRequest> topicRequestList = new ArrayList<>();

            for (Topic topic: topicList){
                topicRequestList.add(mapper.map(topic, TopicRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,topicRequestList,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error for executing get all topic, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Object> getTopicById(Long id){
        try {
            log.info("Executing getTopicById with id : {}", id);
            Optional<Topic> topicOptional = topicRepository.findById(id);

            if (topicOptional.isEmpty()){
                log.info("topic not found");
                return ResponseUtil.build(ResponseMessage.KEY_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            Topic topic = topicOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(topic, TopicRequest.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Get an error by executing get topic by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteTopic(Long id){
        try {
            Optional<Topic> topicOptional = topicRepository.findById(id);

            if (topicOptional.isEmpty()){
                log.info("topic not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            topicRepository.delete(topicOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing delete topic");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
