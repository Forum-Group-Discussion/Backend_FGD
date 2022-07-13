package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.ThreadByLikeDao;
import com.capstone.fgd.domain.dao.Threads;

import com.capstone.fgd.domain.dto.ThreadByLikeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ThreadsRepository extends JpaRepository<Threads, Long> {

    @Query(value = "SELECT * FROM m_thread WHERE title LIKE %:Word% OR content LIKE %:Word% ",nativeQuery = true)
    List<Threads> searchByThread(@Param("Word") String t);

    @Query(value = "SELECT * FROM m_thread WHERE topic_id = :topic", nativeQuery = true)
    List<Threads> getAllThreadByTopic(@Param("topic") Long topic);

    @Query(value = "SELECT * FROM m_thread ORDER BY created_at DESC", nativeQuery = true)
    List<Threads> getThreadDESC();

    @Query(value = "SELECT image FROM Threads ")
    List<Threads> getAllImage();

    @Query(value = "SELECT * FROM m_thread ORDER BY created_at DESC OFFSET :offset LIMIT :limit",nativeQuery = true)
    List<Threads> getAllThreadDESCUsingPagination(Long offset, Long limit);


    @Query(value = "SELECT COUNT(lt.is_like) AS like,t.created_at,t.id,t.content,t.title,us.name_user\n" +
            "FROM m_thread t \n" +
            "JOIN m_user us\n" +
            "ON us.id = t.user_id\n" +
            "JOIN m_likethread lt\n" +
            "ON t.id = lt.thread_id WHERE lt.is_like = true\n" +
            "GROUP BY (lt.is_like,t.created_at,t.id,t.content,t.title,us.name_user) ORDER BY COUNT(lt.is_like) DESC",nativeQuery = true)
    List<ThreadByLikeDao> getListThreadByLike();
}




