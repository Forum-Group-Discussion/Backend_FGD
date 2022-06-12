package com.capstone.fgd.domain.dao;

import com.capstone.fgd.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_topic")
@Entity
public class Topic extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_name", nullable = false)
    private String topicName;

    @OneToMany(mappedBy = "topic")
    private List<Thread> thread;

}
