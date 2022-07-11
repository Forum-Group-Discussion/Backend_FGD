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


//    @Query(value = "SELECT new com.capstone.fgd.domain.dto.ThreadRequest(Threads.id, Threads.content, Threads.image, " +
//            "Threads.title, LikeThread.isDislike, LikeThread.isLike)" +
//            "FROM Threads INNER JOIN LikeThread ON Threads.id = LikeThread.threadLike")

    @Query(value = "SELECT COUNT(lt.is_like) AS like,t.id,t.content,t.title,t.image,t.user_id\n" +
            "FROM m_thread t \n" +
            "JOIN m_likethread lt \n" +
            "ON t.id = lt.thread_id WHERE lt.is_like = true\n" +
            "GROUP BY (lt.is_like,t.id,t.content,t.title,t.image,t.user_id) ORDER BY COUNT(lt.is_like) DESC",nativeQuery = true)
    List<ThreadByLikeDao> getListThreadByLike();
}




