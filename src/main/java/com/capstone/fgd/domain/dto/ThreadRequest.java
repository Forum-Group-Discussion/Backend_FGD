package com.capstone.fgd.domain.dto;

import com.capstone.fgd.domain.dao.Topic;
import com.capstone.fgd.domain.dao.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadRequest {
    private Long id;
    private Users users;
    private String title;
    private String content;
    private String image;
    private Topic topic;
    private Boolean save;
}
