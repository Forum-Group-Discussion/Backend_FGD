package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Following;
import com.capstone.fgd.domain.dao.GetListTotalReportThread;
import com.capstone.fgd.domain.dao.ReportThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportThreadRepository extends JpaRepository<ReportThread, Long> {
    @Query(value = "SELECT * FROM m_report_thread WHERE user_id = :userId AND thread_id = :threadId",nativeQuery = true)
    Optional<ReportThread> hasBeenReportThread(@Param("userId") Long userId,@Param("threadId") Long threadId);

    @Query(value = "SELECT count(*)AS Total_Report_Thread, thread_id  FROM m_report_thread GROUP BY thread_id " +
            "ORDER BY count(*) DESC",nativeQuery = true)
    List<GetListTotalReportThread> getListTotalReport();

}
