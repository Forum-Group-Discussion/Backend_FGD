package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.LikeThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeThreadRepository extends JpaRepository<LikeThread,Long> {

    @Query(value = "SELECT * FROM m_likethread t Where t.user_id = :userId AND t.thread_id = :threadId",nativeQuery = true)
    Optional<LikeThread> userLikeThreads(@Param("userId") Long userId,@Param("threadId") Long threadId );

    @Query(value = "SELECT COUNT(*) FROM m_likethread t WHERE t.is_like = TRUE AND t.thread_id = :threadId",nativeQuery = true)
     public Long userLikeThreads(@Param("threadId") Long threadId );
}
