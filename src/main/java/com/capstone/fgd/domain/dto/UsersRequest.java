package com.capstone.fgd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {
    private Long id;

    private String name;

    private String email;

    private String password;

    private Boolean isAdmin;

    private Boolean isSuspended;
}
