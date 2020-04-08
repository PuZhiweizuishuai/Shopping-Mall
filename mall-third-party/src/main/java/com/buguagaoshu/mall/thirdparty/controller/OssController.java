package com.buguagaoshu.mall.thirdparty.controller;

import com.buguagaoshu.common.utils.R;
import com.buguagaoshu.mall.thirdparty.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Pu Zhiwei {@literal puzhiweipuzhiwei@foxmail.com}
 * create          2020-04-08 21:28
 * 文件上传操作
 */
@RestController
public class OssController {
    private final FileStorageRepository fileStorageRepository;

    @Autowired
    public OssController(FileStorageRepository fileStorageRepository) {
        this.fileStorageRepository = fileStorageRepository;
    }


    @GetMapping("/oss/policy")
    public R policy() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String name = UUID.randomUUID().toString();
        String url = fileStorageRepository.createUploadUrl(name);
        if (url != null) {
            Map<String, String> map = new HashMap<>();
            map.put("filename", "/" +  simpleDateFormat.format(new Date()) + "/" + name);
            map.put("url", url);
            return R.ok().put("data", map);
        }
        return R.error(404, "获取上传地址失败，请稍后再试！");
    }
}
