package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Portray {

    /** 扮演id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer portrayId;
    /** 扮演用户id. */
    private String portrayUserId;
    /** 扮演身份id. */
    private Integer portrayRoleId;
}
