package io.chaoshua.modules.app.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.vo.ImgVo;
import io.chaoshua.modules.background.entity.ImgEntity;
import io.chaoshua.modules.background.service.ImgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dws on 2019/1/9 0009.
 */
@Api(value = "AppImgController",description = "示例接口")
@RestController
@RequestMapping("/app/user/img")
public class AppImgController {
    @Autowired
    private ImgService imgService;

    @ApiOperation("示例列表")
    @GetMapping("/getList/{type}")
    public AppResult<List<ImgVo>> getList(@PathVariable("type") Integer type){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            Map params = new HashMap();
            params.put("status",1);
            params.put("type",type);
            List<ImgEntity> list = imgService.selectList(new EntityWrapper<ImgEntity>().eq("status",1).eq("type",type).orderBy("sort"));
            List<ImgVo> imgVos = new ArrayList<>();
            for (ImgEntity imgEntity : list){
                ImgVo imgVo = new ImgVo();
                imgVo.setImgList(imgEntity.getImg().split(","));
                imgVo.setName(imgEntity.getName());
                imgVos.add(imgVo);
            }
            appResult.setData(imgVos);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误，请联系客服");
        }
        return  appResult;
    }
}
