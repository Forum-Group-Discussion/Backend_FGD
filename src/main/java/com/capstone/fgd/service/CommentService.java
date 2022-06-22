package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Comment;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.ThreadsRequest;
import com.capstone.fgd.repository.CommentRepository;
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

@Slf4j
@Service
public class CommentService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThreadsRepository threadRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CommentRepository commentRepository;


    public ResponseEntity<Object> createNewComment(CommentRequest commentRequest, Principal principal){
        try {
            log.info("Executing create new Comment");
            Users userSignIn = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Threads> threadOptional = threadRepository.findById(commentRequest.getThread().getId());
            if (threadOptional.isEmpty()){
                log.info("Thread not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.BAD_REQUEST);
            }

            Comment comment = Comment.builder()
                    .users(userSignIn)
                    .thread(threadOptional.get())
                    .content(commentRequest.getComment())
                    .build();

//            Comment comment = mapper.map(commentRequest, Comment.class);
//            comment.setId(commentRequest.getId());
//            comment.setUsers(commentRequest.getUsers());
//            comment.setThread(commentRequest.getThread());
//            comment.setContent(commentRequest.getContent());
            commentRepository.save(comment);

            CommentRequest commentRequestDto = mapper.map(comment, CommentRequest.class);

            return ResponseUtil.build(ResponseMessage.KEY_FOUND, commentRequestDto,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing new Comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getCommentById(Long id){
        try {
            log.info("Executing getCommentById with id : {}", id);
            Optional<Comment> commentOptional = commentRepository.findById(id);

            if (commentOptional.isEmpty()){
                log.info("comment not found");
                return ResponseUtil.build(ResponseMessage.KEY_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            Comment comment = commentOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(comment, ThreadsRequest.class), HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing get comment by id, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteComment(Long id){
        try {
            Optional<Comment> commentOptional= commentRepository.findById(id);

            if (commentOptional.isEmpty()){
                log.info("comment not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            commentRepository.delete(commentOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing delete Comment");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
