package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Comment;
import com.capstone.fgd.domain.dao.GetTotalCommentByThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {

    @Query(value = "SELECT * FROM m_comment WHERE thread_id = :thread",nativeQuery = true)
    List<Comment> searchByIdThread(@Param("thread") Integer thread);

    @Modifying
    @Query(value = "DELETE FROM m_like_comment WHERE comment_id = :id",nativeQuery = true)
    void deleteLikeCommentUseCommentId(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM m_report_comment WHERE comment_id = :id",nativeQuery = true)
    void deleteReportCommentUseCommentId(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM m_comment WHERE id = :id",nativeQuery = true)
    void deleteComment(@Param("id") Long id);

    @Query(value = "SELECT count(comment) AS total_comment,thread_id,user_id FROM m_comment\n" +
            "GROUP BY thread_id,user_id",nativeQuery = true)
    List<GetTotalCommentByThread> getTotalCommentByThread();

    @Query(value = "SELECT * FROM m_comment WHERE comment LIKE %:comment%",nativeQuery = true)
    List<Comment> searchComment(@Param("comment") String comment);
}
