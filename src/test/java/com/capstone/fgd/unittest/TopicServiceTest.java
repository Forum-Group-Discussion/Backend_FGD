package com.capstone.fgd.unittest;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.repository.TopicRepository;
import com.capstone.fgd.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TopicService.class)
public class TopicServiceTest {
    @MockBean
    private TopicRepository topicRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private TopicService topicService;

    @Test
    void addTopic_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        TopicRequest topicRequest = TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build();

        when(modelMapper.map(any(),eq(Topic.class))).thenReturn(topic);
        when(modelMapper.map(any(),eq(TopicRequest.class))).thenReturn(topicRequest);
        when(topicRepository.save(ArgumentMatchers.any())).thenReturn(topic);
        ResponseEntity<Object> responseEntity = topicService.createNewTopic(TopicRequest.builder()
                .topicName("Sport")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TopicRequest data = (TopicRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Sport",data.getTopicName());

    }

    @Test
    void addTopicException_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        TopicRequest topicRequest = TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build();

        when(modelMapper.map(any(),eq(Topic.class))).thenReturn(topic);
        when(modelMapper.map(any(),eq(TopicRequest.class))).thenReturn(topicRequest);
        when(topicRepository.save(ArgumentMatchers.any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = topicService.createNewTopic(TopicRequest.builder()
                .topicName("Sport")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void getAllTopic_Success_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        when(topicRepository.findAll()).thenReturn(List.of(topic));
        when(modelMapper.map(any(),eq(TopicRequest.class)))
                .thenReturn(TopicRequest.builder()
                        .id(1L)
                        .topicName("Sport")
                        .build());
        ResponseEntity<Object> responseEntity = topicService.getAllTopic();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TopicRequest> topicRequestList = (List<TopicRequest>) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1,topicRequestList.size());
    }

    @Test
    void getAllTopic_NotfoundTopic_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        when(topicRepository.findAll()).thenReturn(Collections.emptyList());
        when(modelMapper.map(any(),eq(TopicRequest.class)))
                .thenReturn(TopicRequest.builder()
                        .id(1L)
                        .topicName("Sport")
                        .build());
        ResponseEntity<Object> responseEntity = topicService.getAllTopic();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        List<TopicRequest> topicRequestList = (List<TopicRequest>) apiResponse.getData();

        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void getAllTopic_Exception_Test(){
        when(topicRepository.findAll()).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = topicService.getAllTopic();
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void getTopicById_Success_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        TopicRequest topicRequest = TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build();

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(modelMapper.map(any(),eq(TopicRequest.class))).thenReturn(topicRequest);
        ResponseEntity<Object> responseEntity = topicService.getTopicById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TopicRequest data = (TopicRequest) apiResponse.getData();

        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_FOUND,Objects.requireNonNull(apiResponse).getMessage());
        assertEquals(1L,data.getId());
        assertEquals("Sport",data.getTopicName());


    }

    @Test
    public void getTopicById_NotFoundId_Test(){
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = topicService.getTopicById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    public void getTopicById_Exception_Test(){
        when(topicRepository.findById(1L)).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = topicService.getTopicById(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();


        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());

    }

    @Test
    void updateTopicSuccess_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        TopicRequest topicRequest = TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        when(modelMapper.map(any(),eq(Topic.class))).thenReturn(topic);
        when(modelMapper.map(any(),eq(TopicRequest.class))).thenReturn(topicRequest);
        when(topicRepository.save(any())).thenReturn(topic);
        ResponseEntity<Object> responseEntity = topicService.updateTopic(1L,TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TopicRequest data = (TopicRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("Sport",data.getTopicName());
    }


    @Test
    void updateTopic_IdNotFound_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        TopicRequest topicRequest = TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());
        ResponseEntity<Object> responseEntity = topicService.updateTopic(1L,TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TopicRequest data = (TopicRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void updateTopic_Exception_Test(){
        Topic topic = Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        TopicRequest topicRequest = TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build();
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        when(modelMapper.map(any(),eq(Topic.class))).thenReturn(topic);
        when(modelMapper.map(any(),eq(TopicRequest.class))).thenReturn(topicRequest);
        when(topicRepository.save(any())).thenThrow(NullPointerException.class);
        ResponseEntity<Object> responseEntity = topicService.updateTopic(1L,TopicRequest.builder()
                .id(1L)
                .topicName("Sport")
                .build());
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        TopicRequest data = (TopicRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    public void deleteTopicSuccess_Test(){
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(Topic.builder()
                .id(1L)
                .topicName("Sport")
                .build()));
        doNothing().when(topicRepository).delete(any());

        ApiResponse apiResponse = (ApiResponse) topicService.deleteTopic(1L).getBody();
        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
        verify(topicRepository,times(1)).delete(any());
    }

    @Test
    public void deleteCoachNotFound_Test(){
        when(topicRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(topicRepository).delete(any());

        ResponseEntity<Object> responseEntity = topicService.deleteTopic(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }

    @Test
    void deleteCoachException_Test(){
        when(topicRepository.findById(anyLong())).thenThrow(NullPointerException.class);
        doNothing().when(topicRepository).delete(any());

        ResponseEntity<Object> responseEntity = topicService.deleteTopic(1L);
        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),responseEntity.getStatusCodeValue());
        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
    }
}
