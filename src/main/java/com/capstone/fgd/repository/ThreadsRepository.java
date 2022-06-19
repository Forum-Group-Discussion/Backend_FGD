package com.capstone.fgd.repository;


import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.Threads;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadsRepository extends JpaRepository<Threads, Long> {

    @Query(value = "SELECT * FROM m_thread t INNER JOIN m_following f ON t.user_id = f.user_id WHERE " +
            "f.user_id = :userId ",nativeQuery = true)
    List<Threads> listFollowedUserThread(@Param("userId") Long userId);
}




