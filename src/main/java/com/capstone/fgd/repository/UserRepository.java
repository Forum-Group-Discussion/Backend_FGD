package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users getDistinctTopByUsername(String username);
    Boolean existsByUsername(String username);
}
