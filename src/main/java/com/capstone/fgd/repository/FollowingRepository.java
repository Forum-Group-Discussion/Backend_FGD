package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowingRepository extends JpaRepository<Following,Long> {

    @Query(value = "SELECT * FROM m_following t Where t.user_id = :userId and t.user_follow_id = :userFollowId",nativeQuery = true)
    Optional<Following> followIsExists(@Param("userId") Long userId, @Param("userFollowId") Long userFollowId);

    @Query(value = "SELECT * FROM m_following t Where t.user_id = :userId ",nativeQuery = true)
    List<Following> listFollowedUser(@Param("userId") Long userId);

}