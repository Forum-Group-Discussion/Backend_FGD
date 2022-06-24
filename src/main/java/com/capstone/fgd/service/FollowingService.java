package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.FollowingRequest;
import com.capstone.fgd.repository.FollowingRepository;
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
public class FollowingService {

    @Autowired
    private FollowingRepository followingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;


    public ResponseEntity<Object> followUser(FollowingRequest request,Principal principal) {
        try {
            log.info("Executing service create follow");
            Users userLogin = (Users) userService.loadUserByUsername(principal.getName());

            Optional<Users> userToFollow = userRepository.findById(request.getUserFollow().getId());
            if (userToFollow.isEmpty()){
                log.info("User to follow is empty");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }


            if (userLogin.getId().equals(request.getUserFollow().getId())){
                return ResponseUtil.build("CAN'T_FOLLOW_YOURSELF",null,HttpStatus.BAD_REQUEST);
            }
            log.info("i user : {}, user folow : {}",userLogin.getId(),request.getUserFollow().getId());

            Optional<Following> checkFollow = followingRepository.followIsExists(userLogin.getId(),
                   userToFollow.get().getId());

            if (checkFollow.isEmpty()){
                log.info("Follow is empty");
                log.info("i user : {}, user folow : {}",userLogin.getId(),userToFollow.get().getId());
                Following following = Following.builder()
                        .user(userLogin)
                        .userFollow(userToFollow.get())
                        .type("FOLLOW")
                        .isFollow(true)
                        .build();
                followingRepository.save(following);
                FollowingRequest followingRequest = mapper.map(following,FollowingRequest.class);
                return ResponseUtil.build(ResponseMessage.KEY_FOUND,followingRequest,HttpStatus.OK);
            }

            if (checkFollow.isPresent()){
                Following follows = checkFollow.get();
                if (follows.getType().equals("FOLLOW")){
                    log.info("Unfollow user");
                    follows.setId(follows.getId());
                    follows.setUser(follows.getUser());
                    follows.setUserFollow(follows.getUserFollow());
                    follows.setType("UNFOLLOW");
                    follows.setIsFollow(false);
                    followingRepository.save(follows);

                    FollowingRequest followingRequest = mapper.map(follows,FollowingRequest.class);
                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,followingRequest,HttpStatus.OK);
                }

                if (follows.getType().equals("UNFOLLOW")){
                    log.info("follow user again");

                    follows.setId(follows.getId());
                    follows.setUser(follows.getUser());
                    follows.setUserFollow(follows.getUserFollow());
                    follows.setType("FOLLOW");
                    follows.setIsFollow(true);
                    followingRepository.save(follows);

                    FollowingRequest followingRequest = mapper.map(follows,FollowingRequest.class);

                    return ResponseUtil.build(ResponseMessage.KEY_FOUND,followingRequest,HttpStatus.OK);
                }
            }
            return null;

        } catch (Exception e){
            log.error("Get an error by create follow, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    public ResponseEntity<Object> getAllFollowing(Principal principal){
        try {
            log.info("Executing get all following");
            Users userLogin = (Users) userService.loadUserByUsername(principal.getName());

            List<Following> followingList = followingRepository.listFollowedUser(userLogin.getId());
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
//
//    public ResponseEntity<Object> deleteFollowingById(Long id){
//        try {
//            log.info("Executing delete following by id,id : {}",id);
//            Optional<Following> followingOptional = followingRepository.findById(id);
//
//            if (followingOptional.isEmpty()){
//                log.info("following not found");
//                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
//            }
//            followingRepository.delete(followingOptional.get());
//            return ResponseUtil.build(ResponseMessage.KEY_FOUND,null,HttpStatus.OK);
//        }catch (Exception e){
//            log.error("Get an error by executing get user by id, Error : {}",e.getMessage());
//            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
}