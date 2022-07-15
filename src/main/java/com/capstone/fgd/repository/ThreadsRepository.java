package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.GetCommentByThreadId;
import com.capstone.fgd.domain.dao.ThreadByLikeDao;
import com.capstone.fgd.domain.dao.Threads;

import com.capstone.fgd.domain.dto.ThreadByLikeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "SELECT * FROM m_thread ORDER BY created_at DESC OFFSET :offset LIMIT :limit",nativeQuery = true)
    List<Threads> getAllThreadDESCUsingPagination(Long offset, Long limit);


    @Query(value = "SELECT COUNT(lt.is_like) AS like,t.created_at,t.id,t.content,t.title,us.name_user\n" +
            "FROM m_thread t \n" +
            "JOIN m_user us\n" +
            "ON us.id = t.user_id\n" +
            "JOIN m_like_thread lt\n" +
            "ON t.id = lt.thread_id WHERE lt.is_like = true\n" +
            "GROUP BY (lt.is_like,t.created_at,t.id,t.content,t.title,us.name_user) ORDER BY COUNT(lt.is_like) DESC",nativeQuery = true)
    List<ThreadByLikeDao> getListThreadByLike();

    @Modifying
    @Query(value = "DELETE FROM m_save_thread WHERE thread_id = :id",nativeQuery = true)
    void deleteThreadFromSaveThread(@Param("id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM m_report_comment WHERE comment_id = :id",nativeQuery = true)
    void deleteThreadFromReportComment(@Param("id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM m_like_comment WHERE comment_id = :id",nativeQuery = true)
    void deleteThreadFromLikeComment(@Param("id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM m_report_thread WHERE thread_id = :id",nativeQuery = true)
    void deleteThreadFromReportThread(@Param("id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM m_like_thread WHERE thread_id = :id",nativeQuery = true)
    void deleteThreadFromLikeThread(@Param("id") Integer id);

    @Modifying
    @Query(value = "DELETE FROM m_comment WHERE thread_id = :id",nativeQuery = true)
    void deleteThreadFromComment(@Param("id") Integer id);

    @Modifying
    @Query(value = "SELECT cmt.id FROM m_comment cmt WHERE thread_id = :id",nativeQuery = true)
    List<GetCommentByThreadId> getidCommentByThreadId(@Param("id") Integer id);

    @Query (value = "select count(*) AS count_thread FROM m_thread",nativeQuery = true)
    void getCountThread();
}




