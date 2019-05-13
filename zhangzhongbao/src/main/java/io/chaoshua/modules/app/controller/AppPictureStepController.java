package io.chaoshua.modules.app.controller;

import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.vo.PictureStepVo;
import io.chaoshua.modules.background.entity.PictureStepEntity;
import io.chaoshua.modules.background.service.PictureStepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dws on 2018/10/24 0024.
 */
@RestController
@RequestMapping("/app/pictureStep")
@Api(value = "AppPictureStepController",description = "二维码")
public class AppPictureStepController {

    @Autowired
    private PictureStepService pictureStepService;

    @GetMapping("/info")
    @ApiOperation("获取信息")
    public AppResult<PictureStepVo> getInfo(){
        AppResult appResult = null;
        try {
            PictureStepEntity pictureStepEntity = pictureStepService.selectById(1L);
            PictureStepVo pictureStepVo = PictureStepVo.toVo(pictureStepEntity);
            appResult = AppResult.success();
            appResult.setData(pictureStepVo);

        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误，请联系管理员!");
        }
        return appResult;
    }
}
