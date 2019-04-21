package com.ccnu.bbs.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVO {

    /** 评论id. */
    private String commentId;
    /** 用户昵称. */
    private String userName;
    /** 用户头像. */
    private String userImg;
    /** 评论内容. */
    private String commentContent;
    /** 评论点赞数. */
    private Integer commentLikeNum;
    /** 评论时间. */
    private Date commentTime;
    /** 回复列表. */
    private List<ReplyVO> replies;
}
