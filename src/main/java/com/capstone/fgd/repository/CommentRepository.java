package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Long> {
}
