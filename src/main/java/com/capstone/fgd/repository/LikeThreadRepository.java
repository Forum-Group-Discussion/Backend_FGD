package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.GetCountLikeThreadByThread;
import com.capstone.fgd.domain.dao.GetDislikeThreadByThread;
import com.capstone.fgd.domain.dao.LikeThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeThreadRepository extends JpaRepository<LikeThread,Long> {

    @Query(value = "SELECT * FROM m_like_thread t Where t.user_id = :userId AND t.thread_id = :threadId",nativeQuery = true)
    Optional<LikeThread> userLikeThreads(@Param("userId") Long userId,@Param("threadId") Long threadId );

    @Query(value = "SELECT COUNT(*) FROM m_like_thread t WHERE t.is_like = TRUE AND t.thread_id = :threadId",nativeQuery = true)
    public Long userLikeThreads(@Param("threadId") Long threadId);

    @Query(value = "SELECT COUNT(*) FROM m_like_thread t WHERE t.is_dislike = TRUE AND t.thread_id = :threadId",nativeQuery = true)
    public Long userDislikeThreads(@Param("threadId") Long threadId);

    @Query(value = "SELECT count(is_like) AS Like,thread_id FROM m_like_thread WHERE is_like = true GROUP BY thread_id",nativeQuery = true)
    List<GetCountLikeThreadByThread> getCountLikeThreadByThread();

    @Query(value = "SELECT count(is_dislike) AS Dislike,thread_id FROM\n" +
            "m_like_thread WHERE is_dislike = true\n" +
            "GROUP BY thread_id",nativeQuery = true)
    List<GetDislikeThreadByThread> getCountDislikeThreadByThread();

}
