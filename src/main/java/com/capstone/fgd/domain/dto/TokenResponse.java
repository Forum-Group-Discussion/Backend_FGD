package com.capstone.fgd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String token;
    private Long id;
    private Boolean isAdmin;
    private String name;
    private Boolean isSupended;
}

