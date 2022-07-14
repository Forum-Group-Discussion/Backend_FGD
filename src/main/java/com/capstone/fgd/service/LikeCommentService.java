package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Comment;
import com.capstone.fgd.domain.dao.LikeComment;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.LikeCommentRequest;
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
                if (request.getIsLike().equals(true) && request.getIsDislike().equals(false)){
                    log.info("User Insert like true and dislike false");

                    LikeComment likeComment = LikeComment.builder()
                            .userLike(userLogin)
                            .commentLike(commentOptional.get())
                            .isLike(true)
                            .isDislike(false)
                            .build();

                    likeCommentRepository.save(likeComment);

                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment,LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }

                if (request.getIsLike().equals(false) && request.getIsDislike().equals(true)){
                    log.info("User insert false and dislike true");

                    LikeComment likeComment = LikeComment.builder()
                            .userLike(userLogin)
                            .commentLike(commentOptional.get())
                            .isLike(false)
                            .isDislike(true)
                            .build();

                    likeCommentRepository.save(likeComment);

                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);

                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }
            }

            if (likeCommentOptional.isPresent()){
                log.info("User Like Comment Found");

                if (request.getIsLike().equals(true) && request.getIsDislike().equals(false)){
                    log.info("Like True and Dislike is false");

                    LikeComment likeComment = likeCommentOptional.get();
                    likeComment.setId(likeComment.getId());
                    likeComment.setIsLike(true);
                    likeComment.setIsDislike(false);

                    likeCommentRepository.save(likeComment);
                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }

                if (request.getIsLike().equals(false) && request.getIsDislike().equals(true)){
                    log.info("Like false and dislike is true");

                    LikeComment likeComment = likeCommentOptional.get();
                    likeComment.setId(likeComment.getId());
                    likeComment.setIsLike(false);
                    likeComment.setIsDislike(true);

                    likeCommentRepository.save(likeComment);
                    LikeCommentRequest likeCommentRequest = mapper.map(likeComment, LikeCommentRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,likeCommentRequest,HttpStatus.OK);
                }

                if (request.getIsLike().equals(false) && request.getIsDislike().equals(false)){
                    log.info("User Unlike or undislike comment");
                    LikeComment likeComment = likeCommentOptional.get();
                    likeComment.setId(likeComment.getId());
                    likeComment.setIsLike(null);
                    likeComment.setIsDislike(null);

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

    public ResponseEntity<Object> countDislikecomment(CommentRequest request){
        try {
            log.info("Executing count dislike comment ");
            log.info("{}",request.getId());
            Long count = (likeCommentRepository.userDislikeComment(request.getId()));
            log.info("{}",count);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,count,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing count dislike comment, Error : {}",e.getMessage());
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
