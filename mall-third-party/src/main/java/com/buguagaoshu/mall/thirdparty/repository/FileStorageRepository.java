package com.buguagaoshu.mall.thirdparty.repository;

import java.util.Map;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 21:27
 * 文件保存接口
 */
public interface FileStorageRepository {
    /**
     * 生成客户端上传文件所需的URl
     * @param objectName 文件名字
     * @return 上传文件所需要的 URL
     * */
    Map<String, String> createUploadUrl(String objectName);


}
