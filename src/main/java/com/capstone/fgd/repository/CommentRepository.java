package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {

    @Query(value = "SELECT * FROM m_comment WHERE thread_id = :thread",nativeQuery = true)
    List<Comment> searchByIdThread(@Param("thread") Integer thread);
}
