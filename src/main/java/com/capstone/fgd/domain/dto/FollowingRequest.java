package com.capstone.fgd.domain.dto;

import com.capstone.fgd.domain.dao.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FollowingRequest {
    private Long id;
    private UsersRequest user;
    private UsersRequest users_following;

}
