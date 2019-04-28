package com.ccnu.bbs.service.Impl;

import com.ccnu.bbs.config.QiniuUploadFileConfig;
import com.ccnu.bbs.service.QiniuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.InputStream;

public class QiniuServiceImpl implements QiniuService {

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.path}")
    private String path;


    @Override
    public Response uploadFile(File file, String key) throws QiniuException {

        Response response = this.uploadManager.put(file, key, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        return null;
    }

    @Override
    public Response delete(String key) throws QiniuException {
        return null;
    }

    /**
     * 获取上传凭证
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket);
    }

}
