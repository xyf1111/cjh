package io.chaoshua.modules.app.controller;


import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.form.RegisterForm;
import io.chaoshua.modules.app.service.RegisterService;
import io.chaoshua.modules.oss.cloud.OSSFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * 注册
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/app")
@Api(value = "AppRegisterController",description = "APP刷手注册接口")
public class AppRegisterController {
    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    @ApiOperation("注册")
    @LocalLock
    public AppResult register(@RequestBody RegisterForm form){
        AppResult appResult = null;
        try {
            appResult = registerService.register(form);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误,请联系管理人!");
        }

        return appResult;
    }

    @PostMapping("picture/upload")
    @ApiOperation(value = "图片上传")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
//        long fileSize = file.getSize();
//        if (fileSize > 2 * 1024 * 1024) {
//            throw new RRException("上传文件大小不能超过2M");
//        }
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            //如果image = null 表示上传的不是图片格式
            throw new RRException("上传文件不是图片格式");
        }
//        //获取图片宽度，单位px
//        int imageWidth = image.getWidth();
//        //获取图片高度，单位px
//        int imageHeight = image.getHeight();
//        if (imageWidth != imageHeight) {
//            throw new RRException("图片宽高比不符合要求（1:1）");
//        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        return R.ok().put("url", url);
    }
}
