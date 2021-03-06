package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.GetTotalLikeCommentByComment;
import com.capstone.fgd.domain.dao.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {

    @Query(value = "SELECT * FROM m_like_comment t WHERE t.user_id = :userId AND t.comment_id = :commentId"
            ,nativeQuery = true)

    Optional<LikeComment> userLikeComment(
            @Param("userId") Long userId,
            @Param("commentId") Long commentId
    );

    @Query(value = "SELECT COUNT(*) FROM m_like_comment t WHERE t.is_like = TRUE AND t.comment_id = :commentId",
            nativeQuery = true)

    public Long userLikeComment(
            @Param("commentId") Long commentId
    );

    @Query(value = "SELECT count(is_like) AS Like,comment_id FROM m_like_comment WHERE is_like = true GROUP BY " +
            "comment_id " +
            "ORDER BY comment_id  ASC",nativeQuery = true)
    List<GetTotalLikeCommentByComment> getTotalCommentLikeByComment();





}
