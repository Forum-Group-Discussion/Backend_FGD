package com.capstone.fgd.security.handle;

import com.capstone.fgd.constantapp.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException)
            throws IOException {

        Map<String, Object> data = new HashMap<>();

        data.put("data",null);
        data.put("message", ResponseMessage.TOKEN_INVALID_NULL);
        data.put("responseCode", "400");
        data.put("localDateTime", String.valueOf(LocalDateTime.now()));

        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(400);
        res.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}
