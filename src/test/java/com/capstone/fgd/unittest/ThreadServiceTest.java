package com.capstone.fgd.unittest;

import com.capstone.fgd.domain.common.ApiResponse;
import com.capstone.fgd.domain.dao.Thread;
import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.ThreadRequest;
import com.capstone.fgd.domain.dto.TopicRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.ThreadRepository;
import com.capstone.fgd.repository.TopicRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.service.ThreadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ThreadService.class)
public class ThreadServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TopicRepository topicRepository;

    @MockBean
    private ThreadRepository threadRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ThreadService threadService;


    @Test
    void addThread_Success_Test(){
        Users users = Users.builder()
                .id(1L)
                .build();
        Topic topic = Topic.builder()
                .id(1L)
                .build();

        Thread thread = Thread.builder()
                .id(1L)
                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
                .image("urlxxxxxxxxxxxxxxxx")
                .content("Xxxxxxxxxxxxxxxxxxx")
                .save(false)
                .build();

        ThreadRequest threadRequest = ThreadRequest.builder()
                .id(1L)
                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
                .image("urlxxxxxxxxxxxxxxxx")
                .content("Xxxxxxxxxxxxxxxxxxx")
                .save(false)
                .users(UsersRequest.builder()
                        .id(1L)
                        .build())
                .topic(TopicRequest.builder()
                        .id(1L)
                        .build())
                .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(users));
        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
        when(mapper.map(any(),eq(Thread.class))).thenReturn(thread);
        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(threadRequest);

        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
                .id(1L)
                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
                .image("urlxxxxxxxxxxxxxxxx")
                .content("Xxxxxxxxxxxxxxxxxxx")
                .save(false)
                .users(UsersRequest.builder()
                        .id(1L)
                        .build())
                .topic(TopicRequest.builder()
                        .id(1L)
                        .build())
                .build());
        ApiResponse apiResponse  = (ApiResponse) responseEntity.getBody();
        ThreadRequest data = (ThreadRequest) Objects.requireNonNull(apiResponse).getData();
        assertEquals(1L,data.getId());
        assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXXX",data.getTitle());
        assertEquals("Xxxxxxxxxxxxxxxxxxx",data.getContent());
        assertEquals("urlxxxxxxxxxxxxxxxx",data.getImage());
        assertEquals(1L,data.getTopic().getId());
        assertEquals(1L,data.getUsers().getId());



    }
}
