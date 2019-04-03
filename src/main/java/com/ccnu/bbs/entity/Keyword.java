package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Keyword {

    /** 关键词id. */
    @Id
    private Integer keywordId;
    /** 关键词名字. */
    private String keywordName;
}
