package com.ccnu.bbs.forms;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ArticleForm {

    /** 帖子标题. */
    @NotEmpty
    private String articleTitle;
    /** 帖子内容. */
    @NotEmpty
    private String articleContent;
    /** 上传的图片. */
    private List<MultipartFile> multipartFiles;
}
