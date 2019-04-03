package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Article {

    /** 帖子id. */
    @Id
    private String articleId;
    /** 用户id. */
    private Integer articleUserId;
    /** 帖子标题. */
    private String articleTitle;
    /** 帖子内容. */
    private String articleContent;
    /** 帖子浏览次数. */
    private Integer articleViewNum;
    /** 帖子评论次数. */
    private Integer articleCommentNum;
    /** 帖子热度指数. */
    private Double articleHotNum;
    /** 帖子点赞次数. */
    private Integer articleLikeNum;
    /** 帖子发表时间. */
    private Date articleCreateTime;
}
