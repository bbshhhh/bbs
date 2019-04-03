package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Comment {

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
}
