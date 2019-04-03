package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Browse {

    /** 浏览id. */
    @Id
    private Integer browseId;
    /** 浏览用户id. */
    private String browseUserId;
    /** 浏览关键词id. */
    private Integer browseKeywordId;
    /** 浏览数. */
    private Integer browseCount;
}
