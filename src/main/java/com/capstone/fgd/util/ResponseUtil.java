package com.capstone.fgd.util;


import com.capstone.fgd.domain.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ResponseUtil {

    private ResponseUtil(){}

    public static <T> ResponseEntity<Object> build(String message, T data, HttpStatus httpStatus){
        ApiResponse apiResponse = ApiResponse.builder()
                .localDateTime(LocalDateTime.now())
                .responseCode(String.valueOf(httpStatus.value()))
                .message(message)
                .data(data)
                .build();

        return new ResponseEntity<>(apiResponse,httpStatus);
    }

}
