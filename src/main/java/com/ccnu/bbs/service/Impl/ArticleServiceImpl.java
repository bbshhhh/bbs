package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.ArticleVO;
import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.entity.Extract;
import com.ccnu.bbs.entity.Keyword;
import com.ccnu.bbs.entity.User;
import com.ccnu.bbs.enums.ResultEnum;
import com.ccnu.bbs.exception.BBSException;
import com.ccnu.bbs.forms.ArticleForm;
import com.ccnu.bbs.repository.*;
import com.ccnu.bbs.service.ArticleService;
import com.ccnu.bbs.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ExtractRepository extractRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private KeywordServiceImpl keywordService;

    @Autowired
    private LikeServiceImpl likeService;

    @Autowired
    private CollectServiceImpl collectService;

    @Autowired
    private QiniuServiceImpl qiniuService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final Double VIEW_NUM_WEIGHT = 1.0;
    private static final Double COMMENT_NUM_WEIGHT = 2.0;
    private static final Double LIKE_NUM_WEIGHT = 3.0;
    private static final Double TIME_WEIGHT = - 1.0;

    @Override
    /**
     * 帖子列表
     */
    public Page<ArticleVO> allArticle(Pageable pageable) {
        // 1.查找出帖子列表并按热度排序
        Page<Article> articles = articleRepository.findAll(pageable);
        // 2.对每一篇帖子进行拼装
        List<ArticleVO> articleVOList = articles.stream().
                map(e -> article2articleVO(e, e.getArticleId())).collect(Collectors.toList());
        return new PageImpl(articleVOList, pageable, articles.getTotalElements());
    }

    @Override
    /**
     * 创建帖子
     */
    public Article createArticle(String userId, ArticleForm articleForm){
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
        // 5.查看表单中的关键词,建立关键词与帖子关联
        List<String> keywords = articleForm.getArticleKeywords();
        if (keywords != null && !keywords.isEmpty()){
            for (String keywordName : keywords){
                // 查找该关键词是否出现过,未出现则新建
                Keyword keyword = keywordService.findKeyword(keywordName);
                if (keyword == null){
                    keyword = keywordService.createKeyword(keywordName);
                }
                // 新建帖子和关键词的关联,并存入数据库
                Extract extract = new Extract();
                extract.setExtractArticleId(article.getArticleId());
                extract.setExtractKeywordId(keyword.getKeywordId());
                extractRepository.save(extract);
            }
        }
        // 6.计算帖子热度
        article = calcHotNum(article);
        return articleRepository.save(article);
    }

    @Override
    /**
     * 上传图片
     */
    public String uploadImg(MultipartFile multipartFile) throws IOException{
        // 1.创建存储url的字符串
        String imgUrl = new String();
        // 2.判断文件是否为空
        if (multipartFile.isEmpty()){
            return imgUrl;
        }
        // 3.获得文件二进制流
        FileInputStream inputStream = (FileInputStream) multipartFile.getInputStream();
        // 4.使用KeyUtil生成唯一主键作为key进行上传，返回图片url
        imgUrl = qiniuService.uploadFile(inputStream, "bbs/" + KeyUtil.genUniqueKey());
        return imgUrl;
    }

    @Override
    /**
     * 浏览帖子
     */
    public ArticleVO findArticle(String articleId) {
        Article article = getArticle(articleId);
        ArticleVO articleVO = null;
        // 2.如果存在这篇帖子，将帖子浏览数+1，存入redis中
        article.setArticleViewNum(article.getArticleViewNum() + 1);
        redisTemplate.opsForValue().set("Article::" + articleId, article);
        articleVO = article2articleVO(article, article.getArticleUserId());
        return articleVO;
    }

    @Override
    /**
     * 从redis中或数据库中查找帖子
     */
    public Article getArticle(String articleId){
        Article article;
        if (redisTemplate.hasKey("Article::" + articleId)){
            article = (Article) redisTemplate.opsForValue().get("Article::" + articleId);
        }
        else {
            article = articleRepository.findArticle(articleId);
        }
        if (article == null){
            throw new BBSException(ResultEnum.ARTICLE_NOT_EXIT);
        }
        return article;
    }

    @Override
    /**
     * 查找用户发表的帖子
     */
    public Page<ArticleVO> findUserArticle(String userId, Pageable pageable) {
        // 1.查找用户发表的帖子
        Page<Article> articles = articleRepository.findUserArticle(userId, pageable);
        // 2.对每一篇帖子进行拼装
        List<ArticleVO> articleVOList = articles.stream().
                map(e -> article2articleVO(e, e.getArticleId())).collect(Collectors.toList());
        return new PageImpl(articleVOList, pageable, articles.getTotalElements());
    }

    @Override
    /**
     * 查找用户收藏的帖子
     */
    public Page<ArticleVO> findCollectArticle(String userId, Pageable pageable) {
        // 1.查找用户收藏的帖子
        Page<Article> articles = articleRepository.findUserCollect(userId, pageable);
        // 2.对每一篇帖子进行拼装
        List<ArticleVO> articleVOList = articles.stream().
                map(e -> article2articleVO(e, e.getArticleId())).collect(Collectors.toList());
        return new PageImpl(articleVOList, pageable, articles.getTotalElements());
    }

    @Override
    /**
     * 从redis更新帖子数据
     */
    public void updateArticleDatabase() {
        // 1.找到所有关于帖子的key
        Set<String> articleKeys = redisTemplate.keys("Article::*");
        for (String articleKey : articleKeys){
            // 2.根据每一个key得到帖子
            Article article = (Article) redisTemplate.opsForValue().get(articleKey);
            // 3.更新帖子热度
            article = calcHotNum(article);
            // 4.保存帖子进数据库，并删除redis里的数据
            articleRepository.save(article);
            redisTemplate.delete(articleKey);
        }
        return;
    }

    private Article calcHotNum(Article article){
        Double hotNum = LIKE_NUM_WEIGHT * article.getArticleLikeNum()
                + VIEW_NUM_WEIGHT * article.getArticleViewNum()
                + COMMENT_NUM_WEIGHT * article.getArticleCommentNum()
                + TIME_WEIGHT * (System.currentTimeMillis() - article.getArticleCreateTime().getTime()) / 6000;
        article.setArticleHotNum(hotNum);
        return article;
    }

    /**
     * 文章内容拼装
     * @param article
     * @param userId
     * @return
     */
    private ArticleVO article2articleVO(Article article, String userId){
        ArticleVO articleVO = new ArticleVO();
        // 获得帖子信息
        BeanUtils.copyProperties(article, articleVO);
        // 查找作者信息
        User user = userService.findUser(article.getArticleUserId());
        BeanUtils.copyProperties(user, articleVO);
        // 获得图片url
        if (article.getArticleImg()!=null){
            articleVO.setArticleImages(Arrays.asList(article.getArticleImg().split(";")));
        }
        // 查找关键词信息
        List<String> keywords = keywordService.articleKeywords(article.getArticleId());
        articleVO.setKeywords(keywords);
        // 查看帖子是否被当前用户点赞
        articleVO.setIsLike(likeService.isArticleLike(article.getArticleId(), userId));
        // 查看帖子是否被当前用户收藏
        articleVO.setIsCollect(collectService.isArticleCollect(article.getArticleId(), userId));
        return articleVO;
    }

}
