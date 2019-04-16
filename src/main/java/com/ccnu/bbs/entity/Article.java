package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Article implements Serializable {

    /** 帖子id. */
    @Id
    private String articleId;
    /** 用户id. */
    private String articleUserId;
    /** 帖子标题. */
    private String articleTitle;
    /** 帖子内容. */
    private String articleContent;
    /** 帖子图片. */
    private String articleImg;
    /** 帖子浏览次数. */
    private Integer articleViewNum = 0;
    /** 帖子评论次数. */
    private Integer articleCommentNum = 0;
    /** 帖子热度指数. */
    private Double articleHotNum = 0.0;
    /** 帖子点赞次数. */
    private Integer articleLikeNum = 0;
    /** 帖子发表时间. */
    private Date articleCreateTime;
}
