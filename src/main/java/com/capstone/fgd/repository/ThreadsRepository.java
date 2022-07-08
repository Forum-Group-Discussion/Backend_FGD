package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Threads;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThreadsRepository extends JpaRepository<Threads, Long> {

    @Query(value = "SELECT * FROM m_thread WHERE title LIKE %:Word% OR content LIKE %:word% ",nativeQuery = true)
    List<Threads> searchByThread(@Param("Word") String Word);

    @Query(value = "SELECT * FROM m_thread WHERE topic_id = :topic", nativeQuery = true)
    List<Threads> getAllThreadByTopic(@Param("topic") Long topic);

    @Query(value = "SELECT * FROM m_thread ORDER BY created_at DESC", nativeQuery = true)
    List<Threads> getThreadDESC();

    @Query(value = "SELECT image FROM Threads ")
    List<Threads> getAllImage();

    @Query(value = "SELECT * FROM m_thread ORDER BY created_at DESC OFFSET :offset LIMIT :limit",nativeQuery = true)
    List<Threads> getAllThreadDESCUsingPagination(Long offset, Long limit);

//    @Query(value = "")

//    @Query(value = " new com.capstone.fgd.domain.dto.JoinThreadLK()" +
//            " FROM m_thread t " +
//            "INNER JOIN m_likethread lk ON t.id = lk.thread_id",nativeQuery = true)

//    @Query(value = "SELECT new com.capstone.fgd.domain.dto.ThreadRequest(Threads.id, Threads.content, Threads.image, " +
//            "Threads.title, LikeThread.isDislike, LikeThread.isLike)" +
//            "FROM Threads INNER JOIN LikeThread ON Threads.id = LikeThread.threadLike")
//    List<ThreadsRequest> getThreadJoinLikeThread();
}




