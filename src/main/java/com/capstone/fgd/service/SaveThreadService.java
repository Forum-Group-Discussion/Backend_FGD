package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.SaveThread;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.SaveThreadRequest;
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

    public ResponseEntity<Object> saveThread (Principal principal, SaveThreadRequest request){
        try {
            log.info("Executing service create save thread");
            Users userLogin = (Users) userService.loadUserByUsername(principal.getName());
            log.info("{}",userLogin.getId());

            Optional<Threads> threadOptional = threadsRepository.findById(request.getIdThread().getId());
            if (threadOptional.isEmpty()){
                log.info("thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            SaveThread save = SaveThread.builder()
                    .user(userLogin)
                    .threads(threadOptional.get())
                    .build();
            saveThreadRepository.save(save);

            SaveThreadRequest saveThreadRequestDTO = mapper.map(save, SaveThreadRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, saveThreadRequestDTO, HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error executing new save thread, error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
