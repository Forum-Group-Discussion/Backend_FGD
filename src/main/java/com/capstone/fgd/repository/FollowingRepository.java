package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.GetFollowingUser;
import com.capstone.fgd.domain.dao.GetUserByFollowers;
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

    @Query(value = "SELECT * FROM m_following t Where t.user_follow_id = :userId",nativeQuery = true)
    List<Following> listFollowersUser(@Param("userId") Long userId);

    @Query(value = "select f.user_follow_id as id, count(f.user_follow_id) as follower, us.name_user,us.ausername\n" +
            "from m_following f \n" +
            "join m_user us on us.id = f.user_follow_id\n" +
            "where f.is_follow = true\n" +
            "group by f.user_follow_id, us.name_user,us.ausername\n" +
            "order by count(f.user_follow_id) desc",nativeQuery = true)
    List<GetUserByFollowers> getUserByFollower();

    @Query(value = "select f.user_id as id, count(f.user_id)AS following, us.name_user,us.ausername\n" +
            "from m_following f \n" +
            "join m_user us on us.id = f.user_id\n" +
            "where f.is_follow = true\n" +
            "group by f.user_id,us.name_user,us.ausername\n" +
            "order by (f.user_id)",nativeQuery = true)
    List<GetFollowingUser> getAllUserFollowing();

}