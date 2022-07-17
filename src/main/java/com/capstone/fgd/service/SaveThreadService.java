package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.SaveThread;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.SaveThreadRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.SaveThreadRepository;
import com.capstone.fgd.repository.ThreadsRepository;
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

@Service
@Slf4j
public class SaveThreadService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaveThreadRepository saveThreadRepository;

    @Autowired
    private ThreadsRepository threadsRepository;

    @Autowired
    private ModelMapper mapper;



    public ResponseEntity<Object> saveThread (SaveThreadRequest request,Principal principal) {
        try {
            log.info("Executing service create save thread");
            Users userLogin = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Threads> threadOptional = threadsRepository.findById(request.getThreads().getId());

            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Optional<SaveThread> checkSaveThread = saveThreadRepository.checkUserSaveThread(threadOptional.get().getId()
                    ,userLogin.getId());

            if (checkSaveThread.isPresent()){
                log.info("User : {} have save thread id : {} ",userLogin.getName(),request.getThreads().getId());
                return ResponseUtil.build("YOU_HAVE_BEEN_SAVE_THIS_THREAD",null, HttpStatus.BAD_REQUEST);
            }

            SaveThread saveThread = SaveThread.builder()
                    .user(userLogin)
                    .threads(threadOptional.get())
                    .build();
            saveThreadRepository.save(saveThread);
            log.info("berhasil");

            SaveThreadRequest saveThreadRequestDTO = mapper.map(saveThread, SaveThreadRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, saveThreadRequestDTO, HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error executing new save thread, error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public  ResponseEntity<Object> getByUser(Principal principal){
        try {
            Users user = (Users)userService.loadUserByUsername(principal.getName());
            log.info("Executing get all save thread by user with user id : {} ",user.getId());
            List<SaveThread> saveThreadList = saveThreadRepository.getByUser(user.getId());
            List<SaveThreadRequest> saveThreadRequestList = new ArrayList<>();

            if (saveThreadList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (SaveThread saveThread : saveThreadList){
                saveThreadRequestList.add(mapper.map(saveThread,SaveThreadRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,saveThreadRequestList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get An error get all save thread by user : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public  ResponseEntity<Object> deleteSaveThread(Principal principal, Long id){
        try {
            log.info("Executing delete save thread user by id : {}", id);
            Users user = (Users)userService.loadUserByUsername(principal.getName());
            Optional<SaveThread> saveThreadList = saveThreadRepository.getDeleteSaveThread(user.getId(),id);

            if (saveThreadList.isPresent()){
                saveThreadRepository.delete(saveThreadList.get());
                log.info("Success delete save thread with id thread : {} and user id : {}", id,user.getId());
                return ResponseUtil.build(ResponseMessage.KEY_FOUND, null,HttpStatus.OK);
            }
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null,HttpStatus.BAD_REQUEST);


        }catch (Exception e){
            log.error("Get An error get delete save thread by user : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
