package com.ccnu.bbs.VO;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyVO {

    /** 回复id. */
    private String replyId;
    /** 用户昵称. */
    private String userName;
    /** 用户头像. */
    private String userImg;
    /** 回复内容. */
    private String replyContent;
    /** 回复时间. */
    private Date replyTime;
}
