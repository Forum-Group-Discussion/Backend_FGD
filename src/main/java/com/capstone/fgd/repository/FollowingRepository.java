package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Following;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowingRepository extends JpaRepository<Following,Long> {
}
