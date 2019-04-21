package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.forms.ArticleForm;
import com.ccnu.bbs.repository.ArticleRepository;
import com.ccnu.bbs.repository.KeywordRepository;
import com.ccnu.bbs.repository.UserRepository;
import com.ccnu.bbs.service.ArticleService;
import com.ccnu.bbs.utils.KeyUtil;
import com.ccnu.bbs.utils.QiniuCloudUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeywordRepository keywordRepository;

    @Override
    /**
     * 帖子列表
     */
    public List<ArticleVO> allArticle(Pageable pageable) {
        // 1.查找出帖子列表并按热度排序
        Page<Article> articles = articleRepository.findAll(pageable);
        List<ArticleVO> articleVOList = new ArrayList();
        // 2.对每一篇帖子进行拼装
        for (Article article : articles){
            ArticleVO articleVO = article2articleVO(article);
            articleVOList.add(articleVO);
        }
        return articleVOList;
    }

    @Override
    /**
     * 创建帖子
     */
    public Article createArticle(ArticleForm articleForm, String userId){
        Article article = new Article();
        if (!articleForm.getImgUrls().isEmpty()){
            // 1.获取图片url列表
            List<String> imgUrls = articleForm.getImgUrls();
            // 2.将字符串拼接存入帖子字段
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0 ; i < imgUrls.size(); i++){
                stringBuffer.append(imgUrls.get(i));
                if (i != imgUrls.size() - 1) stringBuffer.append(";");
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

    @Override
    /**
     * 上传图片
     */
    public List<String> uploadImg(List<MultipartFile> multipartFiles) throws IOException{
        // 1.创建存储url的列表
        List<String> imgUrls = new ArrayList();
        // 2.判断文件列表是否为空
        if (multipartFiles.isEmpty()){
            return imgUrls;
        }
        // 3.使用七牛云工具类进行上传
        QiniuCloudUtil qiniuCloudUtil = new QiniuCloudUtil();
        for (MultipartFile multipartFile : multipartFiles){
            FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
            // 4.使用KeyUtil生成唯一主键作为key进行上传，返回图片url
            String imgUrl = qiniuCloudUtil.uploadQNImg(inputStream, "bbs/" + KeyUtil.genUniqueKey());
            imgUrls.add(imgUrl);
        }
        return imgUrls;
    }

    @Override
    /**
     * 查看文章
     */
    @Cacheable(cacheNames = "Article", key = "#articleId")
    public ArticleVO findArticle(String articleId) {
        Article article = articleRepository.findArticle(articleId);
        ArticleVO articleVO = null;
        if (article != null){
            articleVO = article2articleVO(article);
        }
        return articleVO;
    }

    /**
     * 文章内容拼装
     * @param article
     * @return
     */
    private ArticleVO article2articleVO(Article article){
        ArticleVO articleVO = new ArticleVO();
        // 获得文章信息
        BeanUtils.copyProperties(article, articleVO);
        // 查找作者信息
        User user = userRepository.findByUserId(article.getArticleUserId());
        BeanUtils.copyProperties(user, articleVO);
        // 获得图片url
        if (article.getArticleImg()!=null){
            articleVO.setArticleImages(Arrays.asList(article.getArticleImg().split(";")));
        }
        // 查找关键词信息
        List<String> keywords = keywordRepository.findArticleKeyword(article.getArticleId());
        articleVO.setKeywords(keywords);
        return articleVO;
    }

}
