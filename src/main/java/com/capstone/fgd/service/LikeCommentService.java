package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.*;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.GetTotalLikeCommentByCommentDTO;
import com.capstone.fgd.domain.dto.LikeCommentRequest;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.repository.CommentRepository;
import com.capstone.fgd.repository.LikeCommentRepository;
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
public class LikeCommentService {

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ThreadsService threadsService;

    @Autowired
    private ModelMapper mapper;


    public ResponseEntity<Object> LikeComment(Principal principal, LikeCommentRequest request){
        try{
            log.info("Executing post like comment");
            Users userLogin = (Users) userService.loadUserByUsername(principal.getName());

            log.info("{}",userLogin.getId());

            Optional<Comment> commentOptional = commentRepository.findById(request.getCommentLike().getId());

            if (commentOptional.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Optional<LikeComment> likeCommentOptional = likeCommentRepository.userLikeComment(userLogin.getId(),
                    request.getCommentLike().getId());

            if (likeCommentOptional.isEmpty()){
                if (request.getIsLike().equals(true)){
                    log.info("User Insert like true and dislike false");

                    LikeComment likeComment = LikeComment.builder()
                            .userLike(userLogin)
                            .commentLike(commentOptional.get())
                            .isLike(true)
                            .build();

                    likeCommentRepository.save(likeComment);

                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment,LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }

                if (request.getIsLike().equals(false)){
                    log.info("User insert false and dislike true");

                    LikeComment likeComment = LikeComment.builder()
                            .userLike(userLogin)
                            .commentLike(commentOptional.get())
                            .isLike(false)
                            .build();

                    likeCommentRepository.save(likeComment);

                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);

                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }
            }

            if (likeCommentOptional.isPresent()){
                log.info("User Like Comment Found");

                if (request.getIsLike().equals(true)){
                    log.info("Like True and Dislike is false");

                    LikeComment likeComment = likeCommentOptional.get();
                    likeComment.setId(likeComment.getId());
                    likeComment.setIsLike(true);

                    likeCommentRepository.save(likeComment);
                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }

                if (request.getIsLike().equals(true)){
                    log.info("Like false and dislike is true");

                    LikeComment likeComment = likeCommentOptional.get();
                    likeComment.setId(likeComment.getId());
                    likeComment.setIsLike(false);

                    likeCommentRepository.save(likeComment);
                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }

                if (request.getIsLike().equals(false) ){
                    log.info("User Unlike or undislike comment");
                    LikeComment likeComment = likeCommentOptional.get();
                    likeComment.setId(likeComment.getId());
                    likeComment.setIsLike(false);

                    likeCommentRepository.save(likeComment);
                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND, likeCommentRequest, HttpStatus.OK);
                }

            }
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.error("Get an error by executing add like comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> getTotalLikeCommentByComment(){
        try {
            log.info("Executing total like comment by comment");

            List<GetTotalLikeCommentByComment> getTotalLikeCommentByCommentList = likeCommentRepository.
                    getTotalCommentLikeByComment();

            List<GetTotalLikeCommentByCommentDTO> getTotalLikeCommentByCommentDTOList = new ArrayList<>();

            for(GetTotalLikeCommentByComment lc : getTotalLikeCommentByCommentList){
                getTotalLikeCommentByCommentDTOList.add(GetTotalLikeCommentByCommentDTO.builder()
                                .like(lc.getLike())
                                .Comment_id(lc.getComment_Id())
                        .build());
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,getTotalLikeCommentByCommentDTOList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count like comment, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> countLikeThread(CommentRequest request){
        try {
            log.info("Executing count like Comment ");
            log.info("{}",request.getId());
            Long count = (likeCommentRepository.userLikeComment(request.getId()));
            log.info("{}",count);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,count,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count like comment, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
