package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.GetListTotalReportComment;
import com.capstone.fgd.domain.dao.ReportComment;
import com.capstone.fgd.domain.dao.ReportThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
    @Query(value = "SELECT * FROM m_report_comment WHERE user_id = :userId AND comment_id = :comId ",nativeQuery = true)
    Optional<ReportComment> hasBeenReportComment(@Param("userId") Long userId,@Param("comId") Long comId);


    @Query(value = "SELECT count(*)AS Total_Report_Comment, comment_id  FROM m_report_comment " +
            "GROUP BY comment_id ORDER BY count(*) DESC",nativeQuery = true)
    List<GetListTotalReportComment> getListTotalReportComment();

}
