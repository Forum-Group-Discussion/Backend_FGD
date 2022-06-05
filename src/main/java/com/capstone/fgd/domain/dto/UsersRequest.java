package com.capstone.fgd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {
    private String name;
    private String email;
    private String password;
    private Boolean isAdmin;
    private Boolean isSuspended;
}
