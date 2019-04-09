package com.ccnu.bbs.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class User implements Serializable {

    /** 用户id. */
    @Id
    private String userId;
    /** 用户登录名. */
    private String userLoginName;
    /** 用户登录密码. */
    private String userLoginPwd;
    /** 用户昵称. */
    private String userName;
    /** 用户性别. */
    private Integer userSex;
    /** 用户电话. */
    private String userPhone;
    /** 用户邮件地址. */
    private String userEmail;
    /** 用户经验. */
    private String userEx;
    /** 用户个性签名. */
    private String userShow;
    /** 用户头像. */
    private String userImg;
    /** 用户粉丝数. */
    private Integer userFans;
    /** 用户关注数. */
    private Integer userAttention;
    /** 用户注册时间. */
    private Date userTime;
}
