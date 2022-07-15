package com.capstone.fgd.domain.dao;

import com.capstone.fgd.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "m_like_thread")

public class LikeThread extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "thread_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Threads threadLike;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users userLike;

    @Column(name = "is_like")
    private Boolean isLike;

    @Column(name = "is_dislike")
    private Boolean isDislike;

}


