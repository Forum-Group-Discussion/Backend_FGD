package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReportUserRepository extends JpaRepository<ReportUser,Long> {
    @Query(value = "SELECT * FROM m_report_user WHERE user_id = :userId",nativeQuery = true)
    Optional<ReportUser> hasBeenReportUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM m_report_user WHERE user_id = :userId AND user_report_id = :userReportId",nativeQuery = true)
    Optional<ReportUser> cantReportYourSelf(@Param("userId") Long userId,@Param("userReportId") Long userReportId);
    
    @Query(value = "select sum(AllCount) AS Total_Report\n" +
            " from\n" +
            "(\n" +
            "(select count(*) AS AllCount from m_report_thread)\n" +
            " union all\n" +
            "(select count(*) AS AllCount from m_report_comment)\n" +
            "union all\n" +
            "(select count(*) AS AllCount from m_report_user)\n" +
            ")t",nativeQuery = true)
    Integer getCountReport();


}
