package com.ccnu.bbs.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;
import java.util.UUID;

public class KeyUtil {

    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * @return
     */
    public static synchronized String genUniqueKey(){

        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    public static synchronized String getSessionId(String key) {
        String sessionId = DigestUtils.md5Hex(key);
        System.out.println("MD5加密后的sessionId为：" + sessionId);
        return sessionId;
    }
}
