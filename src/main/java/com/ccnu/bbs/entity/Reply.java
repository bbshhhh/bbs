package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Reply {

    /** 回复id. */
    @Id
    private String replyId;
    /** 被回复评论id. */
    private String replyCommentId;
    /** 回复用户id. */
    private String replyUserId;
    /** 回复内容. */
    private String replyContent;
    /** 回复时间. */
    private Date replyTime;
}
