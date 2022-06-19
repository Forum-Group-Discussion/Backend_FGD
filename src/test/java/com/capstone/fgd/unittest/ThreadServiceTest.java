package com.capstone.fgd.unittest;

import com.capstone.fgd.repository.ThreadsRepository;
import com.capstone.fgd.repository.TopicRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.service.ThreadsService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ThreadsService.class)
public class ThreadServiceTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TopicRepository topicRepository;

    @MockBean
    private ThreadsRepository threadsRepository;

    @MockBean
    private ModelMapper mapper;

    @Autowired
    private ThreadsService threadsService;


//    @Test
//    void addThread_Success_Test(){
//        Users users = Users.builder()
//                .id(1L)
//                .build();
//        Topic topic = Topic.builder()
//                .id(1L)
//                .build();
//
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//
//        ThreadRequest threadRequest = ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(users));
//        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
//        when(mapper.map(any(),eq(Threads.class))).thenReturn(thread);
//        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(threadRequest);
//
//        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse  = (ApiResponse) responseEntity.getBody();
//        ThreadRequest data = (ThreadRequest) Objects.requireNonNull(apiResponse).getData();
//        assertEquals(1L,data.getId());
//        assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXXX",data.getTitle());
//        assertEquals("Xxxxxxxxxxxxxxxxxxx",data.getContent());
//        assertEquals("urlxxxxxxxxxxxxxxxx",data.getImage());
//        assertEquals(1L,data.getTopic().getId());
//        assertEquals(1L,data.getUsers().getId());
//    }
//
//    @Test
//    void addUser_NotFound_Test(){
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
//                .id(1L)
//                .users(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void AddTopic_NotFound_Test(){
//        when(userRepository.findById(1L)).thenReturn(Optional.empty());
//        when(topicRepository.findById(1L)).thenReturn(Optional.of(Topic.builder().id(1L).build()));
//        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
//                .id(1L)
//                .topic(TopicRequest.builder().id(1L).build())
//                .users(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void addUser_Exception_Test(){
//        when(userRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
//                .users(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void addTopic_Exception_Test(){
//        Users user = Users.builder()
//                .id(1L)
//                .build();
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(topicRepository.save(any())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
//                .users(UsersRequest.builder().id(1L).build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void addThread_Exception_Test(){
//        Users users = Users.builder()
//                .id(1L)
//                .build();
//        Topic topic = Topic.builder()
//                .id(1L)
//                .build();
//
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//
//        ThreadRequest threadRequest = ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//
//        when(userRepository.findById(anyLong())).thenReturn(Optional.of(users));
//        when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
//        when(mapper.map(any(),eq(Threads.class))).thenReturn(thread);
//        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(threadRequest);
//        when(threadRepository.save(any())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = threadService.createNewThread(ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse  = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void updateThread_Success_Test(){
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//
//        ThreadRequest threadRequest = ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread));
//        when(mapper.map(any(), eq(Threads.class))).thenReturn(thread);
//        when(mapper.map(any(), eq(ThreadRequest.class))).thenReturn(threadRequest);
//        when(threadRepository.save(any())).thenReturn(thread);
//        ResponseEntity<Object> responseEntity = threadService.updateThread(1L,ThreadRequest.builder()
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        ThreadRequest data = (ThreadRequest) Objects.requireNonNull(apiResponse).getData();
//        assertEquals(1L,data.getId());
//        assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXXX",data.getTitle());
//        assertEquals("Xxxxxxxxxxxxxxxxxxx",data.getContent());
//        assertEquals("urlxxxxxxxxxxxxxxxx",data.getImage());
//        assertEquals(1L,data.getTopic().getId());
//        assertEquals(1L,data.getUsers().getId());
//    }
//
//    @Test
//    void updateThread_NotFound_Test(){
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = threadService.updateThread(1L,ThreadRequest.builder()
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    public void updateThread_Exception_Test(){
//        when(threadRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        ResponseEntity<Object> responseEntity = threadService.updateThread(1L,ThreadRequest.builder()
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void getAllThread_Success_Test(){
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//        when(threadRepository.findAll()).thenReturn(List.of(thread));
//        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ResponseEntity<Object> responseEntity = threadService.getAllThread();
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        List<ThreadRequest> threadRequestList = (List<ThreadRequest>) apiResponse.getData();
//
//        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1L,threadRequestList.size());
//    }
//
//    @Test
//    void getAllThread_Notfound_Test(){
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//        when(threadRepository.findAll()).thenReturn(Collections.emptyList());
//        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ResponseEntity<Object> responseEntity = threadService.getAllThread();
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        List<ThreadRequest> threadRequestList = (List<ThreadRequest>) apiResponse.getData();
//        assertEquals(HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND,Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    public void getAllThread_Exception_Test(){
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//        when(threadRepository.findAll()).thenThrow(NullPointerException.class);
//        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build());
//        ResponseEntity<Object> responseEntity = threadService.getAllThread();
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        List<ThreadRequest> threadRequestList = (List<ThreadRequest>) apiResponse.getData();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void getThreadById_Success_Test(){
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//
//        ThreadRequest threadRequest = ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(thread));
//        when(mapper.map(any(), eq(ThreadRequest.class))).thenReturn(threadRequest);
//        ResponseEntity<Object> responseEntity = threadService.getThreadById(1L);
//        ApiResponse apiResponse =(ApiResponse) responseEntity.getBody();
//        ThreadRequest data = (ThreadRequest) apiResponse.getData();
//        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//        assertEquals(1L,data.getId());
//        assertEquals("XXXXXXXXXXXXXXXXXXXXXXXXXX",data.getTitle());
//        assertEquals("Xxxxxxxxxxxxxxxxxxx",data.getContent());
//        assertEquals("urlxxxxxxxxxxxxxxxx",data.getImage());
//        assertEquals(1L,data.getTopic().getId());
//        assertEquals(1L,data.getUsers().getId());
//    }
//
//    @Test
//    void getThreadById_NotFound_Test(){
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.empty());
//        ResponseEntity<Object> responseEntity = threadService.getThreadById(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void getThreadById_Exception_Test(){
//        Threads thread = Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build();
//
//        ThreadRequest threadRequest = ThreadRequest.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .users(UsersRequest.builder()
//                        .id(1L)
//                        .build())
//                .topic(TopicRequest.builder()
//                        .id(1L)
//                        .build())
//                .build();
//        when(threadRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        when(mapper.map(any(),eq(ThreadRequest.class))).thenReturn(threadRequest);
//        ResponseEntity<Object> responseEntity = threadService.getThreadById(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void deleteThreadById_Success_Test(){
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.of(Threads.builder()
//                .id(1L)
//                .title("XXXXXXXXXXXXXXXXXXXXXXXXXX")
//                .image("urlxxxxxxxxxxxxxxxx")
//                .content("Xxxxxxxxxxxxxxxxxxx")
//                .save(false)
//                .build()));
//        doNothing().when(threadRepository).delete(any());
//        ApiResponse apiResponse = (ApiResponse) threadService.deleteThread(1L).getBody();
//        assertEquals(ResponseMessage.KEY_FOUND, Objects.requireNonNull(apiResponse.getMessage()));
//        verify(threadRepository,times(1)).delete(any());
//    }
//
//    @Test
//    void deleteThread_NotFoundId_Test(){
//        when(threadRepository.findById(anyLong())).thenReturn(Optional.empty());
//        doNothing().when(threadRepository).delete(any());
//        ResponseEntity<Object> responseEntity = threadService.deleteThread(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
//
//    @Test
//    void deleteThread_Exception_Test(){
//        when(threadRepository.findById(anyLong())).thenThrow(NullPointerException.class);
//        doNothing().when(threadRepository).delete(any());
//        ResponseEntity<Object> responseEntity = threadService.deleteThread(1L);
//        ApiResponse apiResponse = (ApiResponse) responseEntity.getBody();
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseEntity.getStatusCodeValue());
//        assertEquals(ResponseMessage.KEY_NOT_FOUND, Objects.requireNonNull(apiResponse).getMessage());
//    }
}
