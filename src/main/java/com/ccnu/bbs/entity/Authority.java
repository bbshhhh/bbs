package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Authority implements Serializable {

    /** 权限id. */
    @Id
    private Integer authorityId;
    /** 是否能够发帖. */
    private Integer authorityArticle;
    /** 是否能够评论. */
    private Integer authorityComment;
}
