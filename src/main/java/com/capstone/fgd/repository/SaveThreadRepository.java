package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.SaveThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveThreadRepository extends JpaRepository<SaveThread, Long> {
}
