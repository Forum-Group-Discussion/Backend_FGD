package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.SaveThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveThreadRepository extends JpaRepository<SaveThread, Long> {
    @Query(value = "SELECT * FROM m_save_thread WHERE user_id = :userId", nativeQuery = true)
    List<SaveThread> getByUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM m_save_thread WHERE user_id = :userId AND id = :id", nativeQuery = true)
    Optional<SaveThread> getDeleteSaveThread(  Long userId,Long id) ;

    @Query(value = "SELECT * FROM m_save_thread WHERE thread_id = :threadId AND user_id = :id", nativeQuery = true)
    Optional<SaveThread> checkUserSaveThread( Long threadId, Long id) ;

}
