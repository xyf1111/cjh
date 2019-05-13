package io.chaoshua.modules.background.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.chaoshua.common.resetInterceptor.LocalLock;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.R;
import io.chaoshua.common.utils.ShiroUtils;
import io.chaoshua.modules.background.entity.UserAuthImgEntity;
import io.chaoshua.modules.background.entity.ViewEntity;
import io.chaoshua.modules.background.entity.mission.MissionDetailEntity;
import io.chaoshua.modules.background.enums.MissionStatusEnum;
import io.chaoshua.modules.background.service.UserAuthImgService;
import io.chaoshua.modules.background.service.ViewService;
import io.chaoshua.modules.background.service.mission.MissionDetailService;
import io.chaoshua.modules.sys.entity.SysUserRoleEntity;
import io.chaoshua.modules.sys.service.SysUserRoleService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.util.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.mission.MissionService;


/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-09-18 10:51:16
 */
@RestController
@RequestMapping("background/mission")
public class MissionController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MissionService missionService;
    @Autowired
    private MissionDetailService missionDetailService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private ViewService viewService;
    @Autowired
    private UserAuthImgService userAuthImgService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("background:mission:list")
    public R list(@RequestParam Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        SysUserRoleEntity sysUserRoleEntity = sysUserRoleService.selectOne(new EntityWrapper<SysUserRoleEntity>().eq("user_id", userId));
        if (sysUserRoleEntity != null && sysUserRoleEntity.getRoleId() == 2) {
            params.put("inChargeId", userId);
        }
        PageUtils page = missionService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 平台端查询列表
     */
    @RequestMapping("/findList")
    public R findList(@RequestBody Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        SysUserRoleEntity sysUserRoleEntity = sysUserRoleService.selectOne(new EntityWrapper<SysUserRoleEntity>().eq("user_id", userId));
        if (sysUserRoleEntity != null && sysUserRoleEntity.getRoleId() == 2) {
            params.put("inChargeId", userId);
        }
        PageUtils page = missionService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("background:mission:info")
    public R info(@PathVariable("id") Long id) {
        MissionEntity mission = missionService.selectById(id);
        MissionDetailEntity missionDetail = missionDetailService.selectOne(new EntityWrapper<MissionDetailEntity>().eq("mission_id", id));
        List<ViewEntity> list = viewService.selectList(new EntityWrapper<ViewEntity>().eq("mission_id", id).eq("user_id", mission.getUserId()));
        Map<String, Object> missions = new HashedMap();
        for (ViewEntity viewEntity : list) {
            List<String> list1 = Arrays.asList(viewEntity.getImglist().split(","));
            if (viewEntity.getDay() == 1) {
                missions.put("view1", list1);
            } else if (viewEntity.getDay() == 2) {
                missions.put("view2", list1);
            }
        }
        missions.put("mission", mission);
        missions.put("missionDetail", missionDetail);
        return R.ok().put("mission", missions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("background:mission:save")
    public R save(@RequestBody MissionEntity mission) {
        missionService.insert(mission);

        return R.ok();
    }

    /**
     * 修改是否标记
     */
    @RequestMapping("/update")
    @RequiresPermissions("background:mission:update")
    public R update(@RequestBody MissionEntity mission) {
        if (mission.getMark() != null) {
            mission.setIsMark(2);
        } else {
            mission.setIsMark(1);
        }
        missionService.updateById(mission);
        return R.ok();
    }

    /**
     * 平台价格修改
     */
    @RequestMapping("/updatePrice")
    public R updatePrice(@RequestBody MissionEntity mission) {
        MissionEntity missionEntity = missionService.selectById(mission.getId());
        if (MissionStatusEnum.TAKEN.getValue().equals(missionEntity.getStatus())) {
            // 订单已拍下才可以确认
            missionService.updateById(mission);
        } else if (MissionStatusEnum.PLATFORM_CHECK.getValue().equals(missionEntity.getStatus())) {
            return R.error("订单已被确认过，请勿重复确认");
        } else {
            return R.error("不是已拍下状态，无法修改!");
        }
        return R.ok();
    }

    /**
     * 审核订单
     */
    @RequestMapping("/updateRole")
    @RequiresPermissions("background:mission:update")
    @Transactional
    @LocalLock
    public R updateRole(@RequestBody MissionEntity mission) {
        return missionService.updateRole(mission);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("background:mission:delete")
    public R delete(@RequestBody Long[] ids) {
        missionService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }

    /**
     * 获取认证信息
     */
    @RequestMapping("/getAuth")
    public R getAuth(@RequestParam Map params) {
        List<UserAuthImgEntity> list2 = userAuthImgService.selectList(new EntityWrapper<UserAuthImgEntity>().eq("user_id", params.get("userId")).eq("mission_id", params.get("id")));
        return R.ok().put("list", list2);
    }

    /**
     * 获取刷手过往记录
     */
    @RequestMapping("/getHistory")
    public R getHistory(@RequestParam Map<String, Object> params) {
        PageUtils page = missionService.getHistoryByMissionId(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据userId获取刷手过往记录
     */
    @RequestMapping("/getHistoryByUserId")
    public R getHistoryByUserId(@RequestParam Map<String, Object> params) {
        PageUtils page = missionService.getHistoryByUserId(params);
        return R.ok().put("page", page);
    }

    /**
     * 获取指定评价与追评列表
     */
    @RequestMapping("/getAppointList")
    public R getAppointList(@RequestParam Map<String, Object> params) {
        Long userId = ShiroUtils.getUserId();
        SysUserRoleEntity sysUserRoleEntity = sysUserRoleService.selectOne(new EntityWrapper<SysUserRoleEntity>().eq("user_id", userId));
        if (sysUserRoleEntity != null && sysUserRoleEntity.getRoleId() == 2) {
            params.put("inChargeId", userId);
        }
        PageUtils page = missionService.getAppointList(params);
        return R.ok().put("page", page);
    }

    /**
     * 撤销评价与追评
     */
    @RequestMapping("/revokeComment")
    public R revokeComment(@RequestBody MissionEntity missionEntity){
        return missionService.revokeComment(missionEntity);
    }

    /**
     * 确认价格(确认价格之后把刷手的地址信息更新入mission表)
     *
     * @param mission
     * @return
     */
    @PostMapping("/confirmPrice")
    public R confirmPrice(@RequestBody MissionEntity mission) {
        missionService.confirmPrice(mission.getId(), mission.getPrice());
        return R.ok();
    }

    /**
     * 平台来确认价格和是否需要物流信息(确认价格之后把刷手的地址信息更新入mission表)
     *
     * @param mission
     * @return
     */
    @PostMapping("/platformConfirm")
    public R platformConfirm(@RequestBody MissionEntity mission) {
        // 验证该填的信息是否有填写
        if (mission.getPrice() == null || mission.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            return R.error("商品最终确认价格必填且金额大于0！");
        }
        if (mission.getWeight() == null) {
            return R.error("商品货重必填！");
        }
        if(mission.getIsLogistics() == 1){
            //校验地址正确性！
            String address = mission.getLogisticsAddress();
            if (StringUtils.isEmpty(address)) {
                return R.error("需要物流发货的收货地址必填！");
            }
            if (StringUtils.isEmpty(mission.getLogisticsPhone())) {
                return R.error("需要物流发货的收货电话必填！");
            }
            if (StringUtils.isEmpty(mission.getLogisticsName())) {
                return R.error("需要物流发货的收货人必填！");
            }
            String[] addresses = address.split(",");
            if(addresses.length != 4) {
                return R.error("地址格式输入有误！地址请按照：‘省,市,县(区),详细地址’的格式中间以英文逗号,隔开");
            }
        }
        missionService.platformConfirm(mission);
        return R.ok();
    }


}
