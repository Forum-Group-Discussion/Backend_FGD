package com.capstone.fgd.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseDaoDelete extends BaseDao {
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @PrePersist
    void onCreate(){
        this.isDeleted = Boolean.FALSE;
    }
}
