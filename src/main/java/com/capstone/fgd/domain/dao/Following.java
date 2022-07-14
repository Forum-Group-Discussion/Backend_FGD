package com.capstone.fgd.domain.dao;

import com.capstone.fgd.domain.common.BaseDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_following")
public class Following extends BaseDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_follow_id",nullable = false)
    private Users userFollow;


    @JoinColumn(name = "type")
    private String type;

    @JoinColumn(name = "is_fdllow")
    private Boolean isFollow;

}
