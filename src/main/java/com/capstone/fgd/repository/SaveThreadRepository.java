package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.SaveThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveThreadRepository extends JpaRepository<SaveThread, Long> {
    @Query(value = "SELECT * FROM m_save_thread WHERE user_id = :userId", nativeQuery = true)
    List<SaveThread> getByUser(@Param("userId") Long userId);
}
