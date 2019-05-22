package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.VO.TopicVO;
import com.ccnu.bbs.converter.Topic2TopicVO;
import com.ccnu.bbs.repository.TopicRepository;
import com.ccnu.bbs.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicRepository topicRepository;


    @Override
    /**
     * 获取所有版块
     */
    @Cacheable(value = "allTopics")
    public List<TopicVO> allTopic() {
        return Topic2TopicVO.convert(topicRepository.findAll());
    }

    @Override
    @Cacheable(value = "listTopics")
    public List<TopicVO> listTopic(){
        return Topic2TopicVO.convert(topicRepository.topicList());
    }
}
