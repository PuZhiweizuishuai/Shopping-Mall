package com.buguagaoshu.mall.thirdparty.controller;

import com.buguagaoshu.common.utils.R;
import com.buguagaoshu.mall.thirdparty.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


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
    public R policy(@RequestParam(value = "filename", required = false) String filename)  {
        Map<String, String> map = fileStorageRepository.createUploadUrl(filename);
        if (map != null && filename != null) {
            return R.ok().put("data", map);
        }
        return R.error(404, "获取上传地址失败，请稍后再试！");
    }
}
