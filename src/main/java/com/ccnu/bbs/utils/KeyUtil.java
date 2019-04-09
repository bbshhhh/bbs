package com.ccnu.bbs.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;

        return  System.currentTimeMillis() + String.valueOf(number);
    }

    public static synchronized String getSessionId(String key) {
        String sessionId = DigestUtils.md5Hex(key);
        System.out.println("MD5加密后的sessionId为：" + sessionId);
        return sessionId;
    }
}
