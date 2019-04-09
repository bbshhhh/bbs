package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Role implements Serializable {

    /** 身份id. */
    @Id
    private Integer roleId;
    /** 身份名称. */
    private String roleName;
}
