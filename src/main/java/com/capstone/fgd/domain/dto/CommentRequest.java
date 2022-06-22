package com.capstone.fgd.domain.dto;


import com.capstone.fgd.domain.dao.Threads;
import com.capstone.fgd.domain.dao.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private Long Id;
    private UsersRequest users;
    private ThreadsRequest thread;
    private String comment;
}
