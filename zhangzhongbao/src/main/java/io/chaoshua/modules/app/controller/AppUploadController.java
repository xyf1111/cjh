package io.chaoshua.modules.app.controller;

import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.oss.cloud.OSSFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by dws on 2018/11/14 0014.
 */
@RestController
@RequestMapping("/app/user/file")
public class AppUploadController {

    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        return R.ok().put("url", url);
    }

}
