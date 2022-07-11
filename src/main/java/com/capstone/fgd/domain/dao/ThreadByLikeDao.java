package com.capstone.fgd.domain.dao;

import javax.persistence.Lob;

public interface ThreadByLikeDao {
    Integer getId();
    Integer getLike();
    @Lob
    String getContent();
    String getTitle();

    String getImage();
    String getUserId();


}
