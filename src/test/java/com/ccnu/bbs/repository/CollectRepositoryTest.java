package com.ccnu.bbs.repository;

import com.ccnu.bbs.entity.Collect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CollectRepositoryTest {

    @Autowired
    private CollectRepository collectRepository;

    @Test
    public void save() {
        Collect collect = new Collect();
        collect.setIsCollect(0);
        collect.setCollectUserId("456");
        collect.setCollectArticleId("2");
        collect = collectRepository.save(collect);
        assertNotNull(collect);
    }
}