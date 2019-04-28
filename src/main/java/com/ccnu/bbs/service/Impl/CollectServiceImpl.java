package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Article;
import com.ccnu.bbs.entity.Collect;
import com.ccnu.bbs.enums.CollectEnum;
import com.ccnu.bbs.forms.CollectForm;
import com.ccnu.bbs.repository.ArticleRepository;
import com.ccnu.bbs.repository.CollectRepository;
import com.ccnu.bbs.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectRepository collectRepository;

    @Override
    /**
     * 查看帖子是否被收藏
     */
    @Cacheable(cacheNames = "Collect", key = "(#articleId) + '-' + (#userId)")
    public Boolean isArticleCollect(String articleId, String userId) {
        Collect collect = collectRepository.findCollect(articleId, userId);
        if (collect != null && collect.getIsCollect() == CollectEnum.COLLECT.getCode()) return true;
        else return false;
    }

    @Override
    /**
     * 帖子收藏更新
     */
    @CacheEvict(cacheNames = "Collect", key = "(#collectForm.collectArticleId) + '-' + (#userId)")
    public Collect updateCollectArticle(CollectForm collectForm, String userId) {
        // 1.先去数据库查询收藏信息
        Collect collect = collectRepository.findCollect(collectForm.getCollectArticleId(), userId);
        // 2.如果没有则新建收藏信息，设置帖子id和收藏用户id
        if (collect == null){
            collect = new Collect();
            collect.setCollectArticleId(collectForm.getCollectArticleId());
            collect.setCollectUserId(userId);
        }
        // 3.设置收藏标志并保存
        collect.setIsCollect(collectForm.getIsCollect());
        return collectRepository.save(collect);
    }
}
