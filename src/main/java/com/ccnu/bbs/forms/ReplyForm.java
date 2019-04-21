package com.ccnu.bbs.forms;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ReplyForm {

    /** 评论文章id. */
    @NotEmpty
    private String commentId;
    /** 评论内容. */
    @NotEmpty
    private String replyContent;
}
