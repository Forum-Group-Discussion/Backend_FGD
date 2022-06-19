package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByEmail(String email);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT t FROM Users t Where t.email LIKE %:userreq% ")
    List<Users> findEmail(@Param("userreq") String userreq);
}
