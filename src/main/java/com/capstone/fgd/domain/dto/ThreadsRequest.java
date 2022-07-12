package com.capstone.fgd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadsRequest {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UsersRequest user;
    private String title;
    private String content;
    private String image;
    private TopicRequest topic;
}
