package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.entity.Collect;
import com.ccnu.bbs.enums.CollectEnum;
import com.ccnu.bbs.forms.CollectForm;
import com.ccnu.bbs.repository.CollectRepository;
import com.ccnu.bbs.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectRepository collectRepository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    /**
     * 查看帖子是否被收藏
     */
    public Boolean isArticleCollect(String articleId, String userId) {
        Collect collect;
        if (redisTemplate.hasKey("Collect::" + articleId + '-' + userId)){
            collect = (Collect) redisTemplate.opsForValue().get("Collect::" + articleId + '-' + userId);
        }
        else {
            collect = collectRepository.findCollect(articleId, userId);
        }
        if (collect != null && collect.getIsCollect() == CollectEnum.COLLECT.getCode()) return true;
        else return false;
    }

    @Override
    /**
     * 帖子收藏更新
     */
    public Collect updateCollectArticle(CollectForm collectForm, String userId) {
        Collect collect;
        String articleId = collectForm.getCollectArticleId();
        // 1.先查看redis中有没有收藏信息
        if (redisTemplate.hasKey("Collect::" + articleId + '-' + userId)){
            collect = (Collect) redisTemplate.opsForValue().get("Collect::" + articleId + '-' + userId);
        }
        // 2.若没有,则去数据库查询收藏信息
        else {
            collect = collectRepository.findCollect(collectForm.getCollectArticleId(), userId);
        }
        // 3.如果均没有则新建收藏信息，设置帖子id和收藏用户id
        if (collect == null){
            collect = new Collect();
            collect.setCollectArticleId(collectForm.getCollectArticleId());
            collect.setCollectUserId(userId);
        }
        // 3.设置收藏标志并保存
        collect.setIsCollect(collectForm.getIsCollect());
        // 4.将收藏存入redis中
        redisTemplate.opsForValue().set("Collect::" + articleId + '-' + userId, collect);
        return collect;
    }

    @Override
    /**
     * 从redis更新数据库
     */
    public void updateCollectDatabase() {
        // 1.找到所有有关收藏的key
        Set<String> collectKeys = redisTemplate.keys("Collect::*");
        // 2.保存数据到数据库并清除redis中数据
        for (String collectKey : collectKeys){
            Collect collect = (Collect) redisTemplate.opsForValue().get(collectKey);
            collectRepository.save(collect);
            redisTemplate.delete(collectKey);
        }
        return;
    }
}