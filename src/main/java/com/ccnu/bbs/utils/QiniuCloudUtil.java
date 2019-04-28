package com.ccnu.bbs.utils;

import com.ccnu.bbs.config.QiNiuAccountConfig;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;

@Slf4j
public class QiniuCloudUtil {

    @Autowired
    private QiNiuAccountConfig qiNiuConfig;

    public String uploadQNImg(FileInputStream file, String key) {

        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传

        try {
            Auth auth = Auth.create(qiNiuConfig.getAccessKey(), qiNiuConfig.getSecretKey());
            String upToken = auth.uploadToken(qiNiuConfig.getBucket());
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = qiNiuConfig.getPath() + "/" + putRet.key;
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
                try {
                    log.error(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
