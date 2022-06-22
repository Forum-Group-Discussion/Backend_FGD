package com.capstone.fgd.domain.dto;

import com.capstone.fgd.domain.Enum.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportThreadRequest {
    private Long id;
//    private ThreadsRequest thread;
    private UsersRequest users;
    private ReportType reportType;
}
