package io.chaoshua.modules.background.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.chaoshua.modules.background.entity.InvitationEntity;
import io.chaoshua.modules.background.service.InvitationService;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;



/**
 * 邀请码表
 *
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 18:33:20
 */
@RestController
@RequestMapping("background/invitation")
public class InvitationController {
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:invitation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = invitationService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:invitation:info")
    public R info(@PathVariable("id") Long id){
        InvitationEntity invitation = invitationService.selectById(id);
        return R.ok().put("invitation", invitation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:invitation:save")
    public R save(@RequestBody InvitationEntity invitation){
        UserEntity userEntity =  userService.selectOne(new EntityWrapper<UserEntity>().eq("mobile",invitation.getMobile()).eq("status",2));
        if (userEntity != null){
            invitation.setUserId(userEntity.getId());
            invitation.setHolder(userEntity.getName());
            invitationService.insert(invitation);
        }else {
            return R.error(500,"没有该刷手");
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:invitation:update")
    @LocalLock
    public R update(@RequestBody InvitationEntity invitation) throws Exception{
        invitationService.updateById(invitation);
        return R.ok();
    }
    /**
     * 批量修改
     */
    @RequestMapping("/batchUpdate")
    @RequiresPermissions("background:invitation:update")
    public R batchUpdate(@RequestBody Map<String,Object> params){
        String ids = null;
        Integer status = null;
        if (params.containsKey("ids")){
            ids = params.get("ids").toString();
            ids = ids.replace('[',' ').replace(']',' ');
        }
        if (!StringUtils.isEmpty(ids) && ids.length()>0){
            String [] ids1 = ids.split(",");
            List<InvitationEntity> list = new ArrayList<>();
            if (params.containsKey("status")){
                status = Integer.parseInt(params.get("status").toString());
            }
            for (int i = 0;i<ids1.length;i++){
                InvitationEntity invitationEntity = new InvitationEntity();
                invitationEntity.setId(Long.parseLong(ids1[i].trim()));
                invitationEntity.setStatus(status);
                list.add(invitationEntity);
            }
            invitationService.updateBatchById(list);
        }else {
            return R.error(500,"请选择邀请码!");
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:invitation:delete")
    public R delete(@RequestBody Long[] ids){
        invitationService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

}
