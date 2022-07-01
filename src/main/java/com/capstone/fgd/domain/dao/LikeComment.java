package com.capstone.fgd.domain.dao;

import com.capstone.fgd.domain.common.BaseDao;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "m_likecomment")
public class LikeComment extends BaseDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment commentLike;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users userLike;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Comment threadsId;

    @Column(name = "is_like")
    private Boolean isLike;

    @Column(name = "is_dislike")
    private Boolean isDislike;
}
