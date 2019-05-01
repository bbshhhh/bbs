package com.ccnu.bbs.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Message {

    /** 消息id. */
    @Id
    BigInteger messageId;
    /** 消息类型,0为点赞,1为回复. */
    Integer messageType;
    /** 消息对应帖子id. */
    String articleId;
    /** 消息对应评论id. */
    String commentId;
    /** 消息接收者id. */
    String receiverUserId;
    /** 消息发送者id. */
    String senderUserId;
    /** 消息回复的内容. */
    String repliedContent;
    /** 消息内容. */
    String messageContent;
    /** 消息是否已读,0为未读,1为已读. */
    Integer isRead = 0;
    /** 消息创建时间. */
    @CreatedDate
    Date messageTime;
}
