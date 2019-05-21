package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
}
