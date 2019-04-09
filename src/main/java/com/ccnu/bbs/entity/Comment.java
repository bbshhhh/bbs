package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Comment implements Serializable {

    /** 评论id. */
    @Id
    private String commentId;
    /** 评论文章id. */
    private String commentArticleId;
    /** 评论用户id. */
    private String commentUserId;
    /** 评论内容. */
    private String commentContent;
    /** 评论点赞数. */
    private Integer commentLikeNum;
    /** 评论时间. */
    private Date commentTime;
}
