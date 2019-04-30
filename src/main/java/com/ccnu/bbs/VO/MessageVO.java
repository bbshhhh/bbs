package com.ccnu.bbs.VO;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigInteger;
import java.util.Date;

@Data
public class MessageVO {

    /** 消息id. */
    BigInteger messageId;
    /** 消息对应帖子id. */
    String articleId;
    /** 消息对应评论id. */
    String commentId;
    /** 消息发送者昵称. */
    String senderUserName;
    /** 消息发送者头像. */
    String senderUserImg;
    /** 消息回复的内容. */
    String repliedContent;
    /** 消息内容. */
    String messageContent;
    /** 消息创建时间. */
    @CreatedDate
    Date messageTime;
}
