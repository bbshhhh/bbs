package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Keyword implements Serializable {

    /** 关键词id. */
    @Id
    private Integer keywordId;
    /** 关键词名字. */
    private String keywordName;
}
