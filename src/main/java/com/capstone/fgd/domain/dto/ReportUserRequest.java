package com.capstone.fgd.domain.dto;

import com.capstone.fgd.domain.Enum.ReportType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportUserRequest {
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private Long id;
    private UsersRequest user;
    private UsersRequest userReport;
    private ReportType reportType;
}
