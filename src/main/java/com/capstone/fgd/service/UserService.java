package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.UserRepository;
import com.capstone.fgd.security.SecurityFilter;
import com.capstone.fgd.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("Email Not Found");
        }
        return user;
    }

    @Transactional
    public ResponseEntity<Object> getAllUser(){
        try {
            log.info("Executing get all user");
            List<Users> userDaoList = userRepository.findAll();
            List<UsersRequest> userDtoList = new ArrayList<>();

            if (userDaoList.isEmpty()){
                log.info("User List Not Found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            for (Users user:userDaoList) {
                userDtoList.add(mapper.map(user,UsersRequest.class));
            }
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,userDtoList,HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error when executing get all user,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Object> getUserByid(Long id){
        try {
            log.info("Executing get user by id,id : {}",id);
            Optional<Users> userDaoOptional = userRepository.findById(id);

            if (userDaoOptional.isEmpty()){
                log.info("user not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            Users userDao = userDaoOptional.get();
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,mapper.map(userDao,UsersRequest.class),HttpStatus.OK);
        }catch (Exception e){
            log.error("Get an error by executing get user by id, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Object> searchUser(String email){
        try {
            log.info("Executing search user with email : {}",email);
            List<Users> userDaoList = userRepository.findEmail(email);
            List<UsersRequest> userDtoList = new ArrayList<>();

            if (userDaoList.isEmpty()){
                log.info("User List Not Found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null, HttpStatus.BAD_REQUEST);
            }

            for (Users user:userDaoList) {
                userDtoList.add(mapper.map(user,UsersRequest.class));
            }

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,userDtoList,HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error by executing search user,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ResponseEntity<Object> updateSuspended(Long id,UsersRequest request){
        try {
            log.info("Suspend user with id : {}",id);
            Optional<Users> usersOptional = userRepository.findById(id);
            if (usersOptional.isEmpty()){
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            Users users = usersOptional.get();
            users.setIsSuspended(request.getIsSuspended());
            userRepository.save(users);
            UsersRequest usersRequest = mapper.map(users,UsersRequest.class);
            return ResponseUtil.build(ResponseMessage.KEY_FOUND,usersRequest,HttpStatus.OK);

        }catch (Exception e){
            log.info("Get An error by executing update Suspend, Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @Transactional
    public ResponseEntity<Object> updateUser(Long id,UsersRequest request){
        try {
            log.info("Executing update user with id : {}", id);
            Optional<Users> userDaoOptional = userRepository.findById(id);
            if (userDaoOptional.isEmpty()){
                log.info("user not found");
                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
            }
            Users users = userDaoOptional.get();
            users.setName(request.getName());
            users.setUsername(request.getUsername());
            users.setBio(request.getBio());
            users.setLocation(request.getLocation());
            users.setWebsite(request.getWebsite());

            userRepository.save(users);
            UsersRequest usersRequest = mapper.map(users,UsersRequest.class);

            return ResponseUtil.build(ResponseMessage.KEY_FOUND,usersRequest,HttpStatus.OK);

        }catch (Exception e){
            log.error("Get an error by executing update team,Error : {}",e.getMessage());
            return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
