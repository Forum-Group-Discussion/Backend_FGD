package com.capstone.fgd.repository;

import com.capstone.fgd.domain.dao.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {

    @Query(value = "SELECT * FROM m_topic WHERE topic_name = :topic",nativeQuery = true)
    Optional<Topic> getTopicName(String topic);
}
