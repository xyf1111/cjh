package io.chaoshua.modules.background.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.modules.background.entity.ImgEntity;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.entity.UserImgEntity;
import io.chaoshua.modules.background.service.UserImgService;
import io.chaoshua.modules.oss.cloud.OSSFactory;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author dws
 * @date 2018-09-27 15:45:30
 */
@RestController
@RequestMapping("background/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserImgService userImgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.getList(params);
        return R.ok().put("page", page);
    }
    /**
     * 获取最高级用户
     */
    @RequestMapping("/highUserList")
    @RequiresPermissions("background:user:list")
    public R highUserList(){
        List<UserEntity> list = userService.getHighUserId(0L);
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:user:info")
    public R info(@PathVariable("id") Long id){
        UserEntity user = userService.selectById(id);
        return R.ok().put("user", user);
    }
    /**
     * 获取认证图片信息
     */
    @RequestMapping("/infoImg/{id}")
    public R infoImg(@PathVariable("id") Long id){
        List<UserImgEntity> list = userImgService.selectList(new EntityWrapper<UserImgEntity>().eq("user_id",id));
        Map map = new HashMap();
        map.put("list",list);
        return R.ok().put("auth", map);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:user:save")
    public R save(@RequestBody UserEntity user){
			userService.insert(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @LocalLock(key = "arg[0]")
    public R update(@RequestBody UserEntity user){
        userService.updateByUserId(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/batchUpdate")
    @RequiresPermissions("background:user:update")
    public R batchUpdate(@RequestBody Map<String,Object> params){

        return userService.batchUpdate(params);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:user:delete")
    public R delete(@RequestBody Long[] ids){
			userService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 根据用户id获取上下线列表
     * @return
     */
    @RequestMapping("/getHighList/{id}")
    public R getHighList(@PathVariable("id") Long id){
        Map<String,Object> map = new HashedMap();
        List<UserEntity> highUserList = userService.selectByUserId(id);
        List<UserEntity> lowUserList = userService.selectLowUserByUserId(id);
        List<UserEntity> lowerUserList = userService.selectLowerUserByUserId(id);
        map.put("highUserList",highUserList);
        map.put("lowUserList",lowUserList);
        map.put("lowerUserList",lowerUserList);
        return R.ok().put("list",map);
    }

    /**
     * 认证图片上传并修改
     */
    @PostMapping("/authImgUploadAndUpdate")
    public R authImgUploadAndUpdate(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);

        // 更新照片
        UserImgEntity img = new UserImgEntity();
        img.setId(id);
        img.setImg(url);
        userImgService.updateById(img);

        return R.ok();
    }
}
