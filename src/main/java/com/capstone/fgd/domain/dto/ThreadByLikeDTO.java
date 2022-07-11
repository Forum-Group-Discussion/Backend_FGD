package com.capstone.fgd.domain.dto;

import com.capstone.fgd.domain.dao.ThreadByLikeDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreadByLikeDTO{
    private Integer id;
    private Integer like;
    private String content;
    private String title;
    private String image;
    private String userId;

}
