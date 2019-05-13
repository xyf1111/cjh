package io.chaoshua.modules.app.controller;

import io.chaoshua.common.utils.AppResult;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.app.form.PageForm;
import io.chaoshua.modules.app.vo.InformVo;
import io.chaoshua.modules.background.entity.InformEntity;
import io.chaoshua.modules.background.service.InformService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dws on 2018/11/21 0021.
 */
@RestController
@Api(value = "AppInformController",description = "公告")
@RequestMapping("/app/user/inform")
public class AppInformController {
@Autowired
private InformService informService;

    @ApiOperation("列表")
    @PostMapping("/getList")
    public AppResult<List<InformVo>> getList(@RequestBody PageForm pageForm){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            Map map = pageForm.toMap(pageForm);
            map.put("receiverList",2);
            PageUtils page = informService.queryPage(map);
            List<InformEntity> list = ( List<InformEntity> )page.getList();
            List<InformVo> voList = new ArrayList<>();
            for (InformEntity informEntity : list){
                InformVo informVo = new InformVo();
                informVo.setTitle(informEntity.getTitle());
                informVo.setId(informEntity.getId());
                informVo.setCreateTime(informEntity.getCreateTime());
                voList.add(informVo);
            }
            appResult.setData(voList);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误,请联系客服!");
        }
        return  appResult;
    }

    @ApiOperation("公告详情")
    @PostMapping("/getList/{id}")
    public AppResult<InformVo> getList(@PathVariable("id") Long id){
        AppResult appResult = null;
        try {
            appResult = AppResult.success();
            InformEntity informEntity = informService.selectById(id);
            InformVo informVo = new InformVo();
            informVo.setContent(informEntity.getContent());
            appResult.setData(informVo);
        }catch (Exception e){
            e.printStackTrace();
            appResult = AppResult.error(500,"系统错误,请联系客服!");
        }
        return  appResult;
    }
}
