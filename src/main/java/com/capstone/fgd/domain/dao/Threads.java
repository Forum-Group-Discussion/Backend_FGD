package com.capstone.fgd.domain.dao;

import com.capstone.fgd.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_thread")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Transactional
public class Threads extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "content",columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id",nullable = false)
    private Topic topic;


    @OneToMany(mappedBy = "thread",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Comment> threadsComments;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "threadLike")
    private Set<LikeThread> threadsLike;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "thread")
    private Set<ReportThread> reportThreads;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "threads")
    private Set<SaveThread> saveThreads;


}
