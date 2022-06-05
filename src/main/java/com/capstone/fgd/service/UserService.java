package com.capstone.fgd.service;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.capstone.fgd.domain.dao.Users;
import com.capstone.fgd.domain.dto.UsersRequest;
import com.capstone.fgd.repository.UserRepository;
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(email);

        if (user == null){
            throw new UsernameNotFoundException("Email Not Found");
        }
        return user;

    }

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



//    public ResponseEntity<Object> updateUser(Long id,UsersRequest request){
//        try {
//            log.info("Executing update team with id : {}",id);
//            Optional<Users> userDaoOptional = userRepository.findById(id);
//            if (userDaoOptional.isEmpty()){
//                log.info("user not found");
//                return ResponseUtil.build(ResponseMessage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
//            }
//            Users userDao = userDaoOptional.get();
//
//            userDao.setNameUser(request.getNameUser());
//            userDao.setTelephoneNumber(request.getTelephoneNumber());
//            userDao.setAlamat(request.getAlamat());
//            userRepository.save(userDao);
//
//            return ResponseUtil.build(ResponseMassage.KEY_FOUND,mapper.map(userDao,UserDto.class),HttpStatus.OK);
//
//        }catch (Exception e){
//            log.error("Get an error by exeuting update team,Error : {}",e.getMessage());
//            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    public ResponseEntity<Object> deleteUser(Long id){
//        try {
//            log.info("Executing delete user with id : {}",id);
//            Optional<UserDa> userDaoOptional = userRepository.findById(id);
//            if (userDaoOptional.isEmpty()){
//                log.info("user not found");
//                return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.BAD_REQUEST);
//            }
//            userRepository.delete(userDaoOptional.get());
//            return ResponseUtil.build(ResponseMassage.KEY_FOUND,null,HttpStatus.OK);
//
//        }catch (Exception e){
//            log.error("Get an error by executing delete user,Error : {}",e.getMessage());
//            return ResponseUtil.build(ResponseMassage.KEY_NOT_FOUND,null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
