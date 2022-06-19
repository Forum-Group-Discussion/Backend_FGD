package com.capstone.fgd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadsRequest {
    private Long id;
    private UsersRequest users;
    private String title;
    private String content;
    private String image;
    private TopicRequest topic;
    private Boolean save;
}
