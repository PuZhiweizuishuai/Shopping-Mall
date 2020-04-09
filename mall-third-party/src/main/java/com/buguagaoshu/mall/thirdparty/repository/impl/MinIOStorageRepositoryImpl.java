package com.buguagaoshu.mall.thirdparty.repository.impl;

import com.buguagaoshu.mall.thirdparty.config.MinIOConfigProperties;
import com.buguagaoshu.mall.thirdparty.repository.FileStorageRepository;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 21:29
 * MinIO 对象存储实现
 * 实际部署可以采用如阿里云OSS等其它云服务的OSS服务
 */
@Repository
@Slf4j
public class MinIOStorageRepositoryImpl implements FileStorageRepository {
    private final MinioClient minioClient;

    private final MinIOConfigProperties properties;

    @Autowired
    public MinIOStorageRepositoryImpl(MinioClient minioClient, MinIOConfigProperties properties) {
        this.minioClient = minioClient;
        this.properties = properties;
    }

    @Override
    public Map<String, String> createUploadUrl(String objectName) {
        try {
            // 文件保存格式为 日期/UUID-文件名
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String name = UUID.randomUUID().toString();
            String filename = "/" +  simpleDateFormat.format(new Date()) + "/" + name + "-" + objectName;

            String url = minioClient.presignedPutObject(properties.getBucketName(), filename, properties.getExpiry());
            HashMap<String, String> map = new HashMap<>(2);
            map.put("url", url);
            map.put("filename", filename);
            return map;
        } catch(Exception e) {
            log.error("Error occurred:  {}", e.getMessage());
            return null;
        }
    }
}
