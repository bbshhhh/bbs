package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

@Data
@Entity
public class Extract {

    /** 提取id. */
    @Id
    private BigInteger extractId;
    /** 提取文章id. */
    private String extractArticleId;
    /** 提取关键词id. */
    private Integer extractKeywordId;
}
