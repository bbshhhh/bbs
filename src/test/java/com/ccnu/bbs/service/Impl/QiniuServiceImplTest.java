package com.ccnu.bbs.service.Impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class QiniuServiceImplTest {

    @Autowired
    private QiniuServiceImpl qiniuService;

    @Test
    public void uploadFileTest1() {
        String fileName = "f:/1.jpeg";
        File file = new File(fileName);
        System.out.println(file.exists());
        assertTrue(file.exists());
        try{
            String res = qiniuService.uploadFile(file, "bbs/123");
            assertFalse(res.isEmpty());
        }catch (QiniuException e){
            e.printStackTrace();
        }
    }

    @Test
    public void uploadFileTest2() {
        String fileName = "f:/1.jpeg";
        try{
            InputStream inputStream = new FileInputStream(fileName);
            try{
                String res = qiniuService.uploadFile(inputStream, "bbs/124");
                assertFalse(res.isEmpty());
            }catch (QiniuException e){
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTest() {
/*        String key = "bbs/f6704b58c9404c958627b7ff28441fde";
        try {
            Response response = qiniuService.delete(key);
            Assert.assertEquals(true, response.isOK());
        }catch (QiniuException e){
            e.printStackTrace();
        }*/
    }
}