package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.ReportThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportThreadRepository extends JpaRepository<ReportThread, Long> {
}
