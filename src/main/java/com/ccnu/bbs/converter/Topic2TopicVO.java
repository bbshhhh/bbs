package com.ccnu.bbs.converter;

import com.ccnu.bbs.VO.TopicVO;
import com.ccnu.bbs.entity.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Topic2TopicVO {

    public static TopicVO convert(Topic topic){
        TopicVO topicVO = new TopicVO();
        BeanUtils.copyProperties(topic, topicVO);
        return topicVO;
    }

    public static List<TopicVO> convert(List<Topic> topics){
        return topics.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
