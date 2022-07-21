package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.*;
import com.capstone.fgd.domain.dto.GetCountDislikeThreadbyThreadDTO;
import com.capstone.fgd.domain.dto.GetCountLikeThreadByThreadDTO;
import com.capstone.fgd.domain.dto.LikeThreadRequest;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.repository.LikeThreadRepository;
import com.capstone.fgd.repository.ThreadsRepository;
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
public class LikeThreadService {

    @Autowired
    private LikeThreadRepository likeThreadRepository;

    @Autowired
    private ThreadsRepository threadsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> likeTheThread(Principal principal, LikeThreadRequest req){
        try {
            log.info("Executing post like thread");
            Users userLogin = (Users) userService.loadUserByUsername(principal.getName());
            log.info("{}",userLogin.getId());

            Optional<Threads> threadsOptional = threadsRepository.findById(req.getThreadLike().getId());

            if (threadsOptional.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Optional<LikeThread> likeThreadOptional = likeThreadRepository.userLikeThreads(userLogin.getId(),
                    req.getThreadLike().getId());

            if (likeThreadOptional.isEmpty()){

                if (req.getIsLike().equals(true) && req.getIsDislike().equals(false)){
                    log.info("User insert like true and dislike false");

                    LikeThread likeThread = LikeThread.builder()
                            .userLike(userLogin)
                            .threadLike(threadsOptional.get())
                            .isLike(true)
                            .isDislike(false)
                            .build();

                    likeThreadRepository.save(likeThread);

                    LikeThreadRequest likeThreadRequest = mapper.map(likeThread,LikeThreadRequest.class);

                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeThreadRequest,HttpStatus.OK);
                }

                if (req.getIsLike().equals(false) && req.getIsDislike().equals(true)){
                    log.info("User insert false true and dislike true");

                    LikeThread likeThread = LikeThread.builder()
                            .userLike(userLogin)
                            .threadLike(threadsOptional.get())
                            .isLike(false)
                            .isDislike(true)
                            .build();

                    likeThreadRepository.save(likeThread);

                    LikeThreadRequest likeThreadRequest = mapper.map(likeThread,LikeThreadRequest.class);

                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeThreadRequest,HttpStatus.OK);
                }

            }

            //user have liked
            if (likeThreadOptional.isPresent()){
                    log.info("User Like Thread Found");

                  if (req.getIsLike().equals(true) && req.getIsDislike().equals(false))     {
                    log.info("Like True and Dislike is False");

                    LikeThread likeThread = likeThreadOptional.get();
                    likeThread.setId(likeThread.getId());
                    likeThread.setIsLike(true);
                    likeThread.setIsDislike(false);

                    likeThreadRepository.save(likeThread);
                    LikeThreadRequest likeThreadRequest = mapper.map(likeThread,LikeThreadRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeThreadRequest,HttpStatus.OK);

                }

                if (req.getIsLike().equals(false) && req.getIsDislike().equals(true)) {
                    log.info("Like False and Dislike is True");

                    LikeThread likeThread = likeThreadOptional.get();
                    likeThread.setId(likeThread.getId());
                    likeThread.setIsLike(false);
                    likeThread.setIsDislike(true);

                    likeThreadRepository.save(likeThread);
                    LikeThreadRequest likeThreadRequest = mapper.map(likeThread,LikeThreadRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeThreadRequest,HttpStatus.OK);

                }

                if (req.getIsLike().equals(false) && req.getIsDislike().equals(false)){
                    log.info("User Unlike or undislike thread");
                    LikeThread likeThread = likeThreadOptional.get();
                    likeThread.setId(likeThread.getId());
                    likeThread.setIsLike(null);
                    likeThread.setIsDislike(null);

                    likeThreadRepository.save(likeThread);
                    LikeThreadRequest likeThreadRequest = mapper.map(likeThread,LikeThreadRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeThreadRequest,HttpStatus.OK);
                }

            }
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);

        } catch (Exception e){
            log.error("Get an error by executing add like thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> countLikeThread(ThreadsRequest request){
        try {
            log.info("Executing count like thread ");
            log.info("{}",request.getId());
           Long count = (likeThreadRepository.userLikeThreads(request.getId()));
           log.info("{}",count);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,count,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count like thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> countDislikeThread(ThreadsRequest request){
        try {
            log.info("Executing count dislike thread ");
            log.info("{}",request.getId());
            Long count = (likeThreadRepository.userDislikeThreads(request.getId()));
            log.info("{}",count);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,count,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count dislike thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getListLikeThread(){
        try {
            log.info("Executing count like thread by thread ");
            List<GetCountLikeThreadByThread> getCountLikeThreadByThreads = likeThreadRepository.getCountLikeThreadByThread();
            List<GetCountLikeThreadByThreadDTO> getCountLikeThreadByThreadDTOList = new ArrayList<>();

            for (GetCountLikeThreadByThread count : getCountLikeThreadByThreads){
                getCountLikeThreadByThreadDTOList.add(GetCountLikeThreadByThreadDTO.builder()
                                .countLikeThread(count.getLike())
                                .threadId(count.getThread_Id())
                        .build());
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,getCountLikeThreadByThreadDTOList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count like thread by threadz, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getListDislikeThread(){
        try {
            log.info("Executing count dislike thread by thread ");
            List<GetDislikeThreadByThread> getDislikeThreadByThreads = likeThreadRepository.getCountDislikeThreadByThread();
            List<GetCountDislikeThreadbyThreadDTO> getCountDislikeThreadbyThreadDTOS = new ArrayList<>();

            for (GetDislikeThreadByThread count : getDislikeThreadByThreads){
                getCountDislikeThreadbyThreadDTOS.add(GetCountDislikeThreadbyThreadDTO.builder()
                                .countDislikeThread(count.getDislike())
                                .threadId(count.getThread_id())
                        .build());
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,getCountDislikeThreadbyThreadDTOS,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count like thread by threadz, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
