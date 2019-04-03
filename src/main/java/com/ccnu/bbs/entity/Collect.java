package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Collect {

    /** 收藏id. */
    @Id
    private Integer collectId;
    /** 收藏文章id. */
    private String collectArticleId;
    /** 收藏用户id. */
    private String collectUserId;
}
