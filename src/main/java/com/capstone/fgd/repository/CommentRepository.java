package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Comment;
import com.capstone.fgd.domain.dao.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {
    List<Comment> findAllByThread(Threads threads);
}
