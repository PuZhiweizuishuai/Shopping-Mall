package com.buguagaoshu.mall.thirdparty.repository;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 21:27
 * 文件保存接口
 */
public interface FileStorageRepository {
    /**
     * 生成客户端上传文件所需的URl
     * @param objectName 文件要保存的名字
     * @return 上传文件所需要的 URL
     * */
    String createUploadUrl(String objectName);

}
