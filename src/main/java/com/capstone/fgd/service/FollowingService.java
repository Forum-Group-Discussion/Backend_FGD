package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.FollowingRequest;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.FollowingRepository;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FollowingService {

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public ResponseEntity<Object> createFollow(FollowingRequest request,Long ids){
        try{
            log.info("Executing service create follow");
            Optional<Users>usersOptional = userRepository.findById(request.getUser().getId());
            if (usersOptional.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            Optional<Users> usersToFollow = userRepository.findById(ids);
            if (usersToFollow.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }
            if (!(request.getUser().getId().equals(ids))){
                Following follow = Following.builder()
                        .id(request.getId())
                        .build();

                follow.setUser(usersOptional.get());
                follow.setUser_following(usersToFollow.get());
                followingRepository.save(follow);
                FollowingRequest followingRequest = mapper.map(follow,FollowingRequest.class);
                return ResponseUtil.build(ResponseMessage.KEY_FOUND,followingRequest,HttpStatus.OK);
            }
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            log.error("Get an error by create follow, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> getAllFollowing(){
        try {
            log.info("Executing get all following");
            List<Following> followingList = followingRepository.findAll();
            List<FollowingRequest> followingRequestList = new ArrayList<>();

            if (followingList.isEmpty()){
                log.info("Following List Not Found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            for (Following follow:followingList) {
                followingRequestList.add(mapper.map(follow,FollowingRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,followingRequestList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error when executing get all following,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<Object> getFollowingByid(Long id){
        try {
            log.info("Executing get following by id,id : {}",id);
            Optional<Following> followingOptional = followingRepository.findById(id);

            if (followingOptional.isEmpty()){
                log.info("following not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            FollowingRequest followingRequest =mapper.map(followingOptional.get(),FollowingRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,followingRequest,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing get user by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> deleteFollowingById(Long id){
        try {
            log.info("Executing delete following by id,id : {}",id);
            Optional<Following> followingOptional = followingRepository.findById(id);

            if (followingOptional.isEmpty()){
                log.info("following not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            followingRepository.delete(followingOptional.get());
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing get user by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

