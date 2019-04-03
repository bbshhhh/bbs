package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Attention {

    /** 关注id. */
    @Id
    private Integer attentionId;
    /** 被关注人id. */
    private String attentionUserId;
    /** 关注人id. */
    private String attentionFollowerId;
}
