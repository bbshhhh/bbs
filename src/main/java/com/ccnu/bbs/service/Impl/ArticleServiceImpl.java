package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.forms.ArticleForm;
import com.ccnu.bbs.repository.ArticleRepository;
import com.ccnu.bbs.service.ArticleService;
import com.ccnu.bbs.utils.KeyUtil;
import com.ccnu.bbs.utils.QiniuCloudUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    /**
     * 帖子列表
     */
    public Page<Article> allArticle(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    public Article createArticle(ArticleForm articleForm, String userId) throws IOException {
        Article article = new Article();
        if (!articleForm.getMultipartFiles().isEmpty()){
            // 1.获得图片列表
            List<MultipartFile> imgList = articleForm.getMultipartFiles();
            // 2.将图片上传到七牛云并将返回的url拼接成字符串
            QiniuCloudUtil qiniuCloudUtil = new QiniuCloudUtil();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i=0; i<imgList.size(); i++){
                //获取文件流，并用KeyUtil生成唯一键
                FileInputStream inputStream = (FileInputStream) imgList.get(i).getInputStream();
                stringBuffer.append(qiniuCloudUtil.uploadQNImg(inputStream, "bbs/" + KeyUtil.genUniqueKey()));
                if (i!=imgList.size()-1) stringBuffer.append(";");
            }
            // 3.将生成的图片url给帖子的图片字段
            article.setArticleImg(stringBuffer.toString());
        }
        // 4.将帖子其余信息存入数据库中
        BeanUtils.copyProperties(articleForm, article);
        article.setArticleId(KeyUtil.genUniqueKey());
        article.setArticleUserId(userId);
        return articleRepository.save(article);
    }
}
