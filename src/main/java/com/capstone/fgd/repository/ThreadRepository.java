package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Thread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {
}