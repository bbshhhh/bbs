package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role {

    /** 身份id. */
    @Id
    private Integer roleId;
    /** 身份名称. */
    private String roleName;
}
