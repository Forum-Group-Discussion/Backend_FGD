package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dao.ReportThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
    @Query(value = "SELECT * FROM m_report_comment WHERE user_id = :userId",nativeQuery = true)
    Optional<ReportComment> hasBeenReportComment(@Param("userId") Long userId);
}
