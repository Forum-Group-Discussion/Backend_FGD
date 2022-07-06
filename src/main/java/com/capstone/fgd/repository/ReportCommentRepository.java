package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
}
