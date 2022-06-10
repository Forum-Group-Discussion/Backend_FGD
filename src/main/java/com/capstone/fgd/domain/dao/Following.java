//package com.capstone.fgd.domain.dao;
//
//import com.capstone.fgd.domain.common.BaseDaoDelete;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "m_following")
//public class Following extends BaseDaoDelete {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users users;
//
//    @ManyToOne
//    @JoinColumn(name = "user_following_id")
//}
