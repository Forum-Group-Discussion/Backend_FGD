package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Comment;
import com.capstone.fgd.domain.dao.GetTotalCommentByThread;
import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.CommentRequest;
import com.capstone.fgd.domain.dto.GetTotalCommentByThreadDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
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
                    .comment(commentRequest.getComment())
                    .build();
            commentRepository.save(comment);
            CommentRequest commentRequestDto = mapper.map(comment, CommentRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, commentRequestDto,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error executing new Comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getCommentByThread(Integer req){
        try {
            log.info("Executing get all comment by thread");
            List<Comment> commentList = commentRepository.searchByIdThread(req);
            List<CommentRequest> commentRequestsList = new ArrayList<>();

            if (commentList.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Comment comment: commentList){
                commentRequestsList.add(mapper.map(comment,CommentRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,commentRequestsList,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get An error get all comment by thread : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getTotalCommentByThread(){
        try {
            log.info("Executing get total comment by thread");
            List<GetTotalCommentByThread> getTotalCommentByThreadList = commentRepository.getTotalCommentByThread();
            List<GetTotalCommentByThreadDTO> getTotalCommentByThreadDTOList = new ArrayList<>();

            for (GetTotalCommentByThread totalcomment : getTotalCommentByThreadList){
                getTotalCommentByThreadDTOList.add(GetTotalCommentByThreadDTO.builder()
                                .totalComment(totalcomment.getTotal_Comment())
                                .threadId(totalcomment.getThread_Id())
                                .userId(totalcomment.getUser_Id())
                        .build());
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,getTotalCommentByThreadDTOList,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get An error get all comment by thread : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
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
            return ResponseUtil.build(ResponseMessage.KEY_FOUND, mapper.map(comment, CommentRequest.class), HttpStatus.OK);
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
            commentRepository.deleteLikeCommentUseCommentId(id);
            commentRepository.deleteReportCommentUseCommentId(id);
            commentRepository.deleteComment(id);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        } catch (Exception e){
            log.error("Get an error by executing delete Comment");
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND, null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> updateComment(Long id, CommentRequest request){
        try {
            log.info("Executing Update Comment");
            Optional<Comment> commentOptional = commentRepository.findById(id);

            if (commentOptional.isEmpty()){
                log.info("Comment Not Found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            Comment comment = commentOptional.get();
            comment.setComment(request.getComment());
            commentRepository.save(comment);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,mapper.map(comment, CommentRequest.class), HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error executing update comment, Error : {}", e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> searchComment(String req){
        try {
            log.info("Executing search comment");
             List<Comment> commentList = commentRepository.searchComment(req);
             List<CommentRequest> commentRequestList = new ArrayList<>();

            if (commentList.isEmpty()){
                log.info("not found any comment");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }

            for (Comment comment:commentList){
                commentRequestList.add(mapper.map(comment,CommentRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,commentRequestList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by searching thread, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
