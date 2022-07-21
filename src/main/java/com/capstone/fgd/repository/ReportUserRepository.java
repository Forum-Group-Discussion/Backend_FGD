package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.GetListTotalReportUser;
import com.capstone.fgd.domain.dao.ReportUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportUserRepository extends JpaRepository<ReportUser,Long> {
    @Query(value = "SELECT * FROM m_report_user WHERE user_id = :userId AND user_report_id = :usp",nativeQuery = true)
    Optional<ReportUser> hasBeenReportUser(@Param("userId") Long userId,@Param("usp") Long usp);

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


    @Query(value = "SELECT count(*)AS Total_Report_User, user_report_id FROM m_report_user GROUP BY user_report_id " +
            "ORDER BY count(*) DESC",nativeQuery = true)
    List<GetListTotalReportUser> getListTotalReportUser();


}
