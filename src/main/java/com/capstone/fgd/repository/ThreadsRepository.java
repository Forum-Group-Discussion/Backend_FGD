package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Threads;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThreadsRepository extends JpaRepository<Threads, Long> {


    @Query(value = "SELECT * FROM m_thread WHERE title LIKE %:Word% OR content LIKE %:word% ",nativeQuery = true)
    List<Threads> searchByThread(@Param("Word") String Word);
}




