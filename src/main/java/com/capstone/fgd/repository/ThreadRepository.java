package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadRepository extends JpaRepository<Threads, Long> {
}