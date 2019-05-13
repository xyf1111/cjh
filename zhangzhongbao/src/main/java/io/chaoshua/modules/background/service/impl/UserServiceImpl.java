package io.chaoshua.modules.background.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.Query;
import io.chaoshua.common.utils.R;
import io.chaoshua.modules.app.vo.user.LowUserVo;
import io.chaoshua.modules.background.entity.IntervalStepEntity;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.entity.mission.MissionEntity;
import io.chaoshua.modules.background.service.IntervalStepService;
import io.chaoshua.modules.background.service.UserImgService;
import io.chaoshua.modules.background.service.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.chaoshua.common.utils.PageUtils;

import io.chaoshua.modules.background.dao.UserDao;
import io.chaoshua.modules.background.service.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private MissionService missionService;
    @Autowired
    private UserImgService userImgService;
    @Autowired
    private IntervalStepService intervalStepService;

    @Override
    public PageUtils getList(Map<String, Object> params) {
        Page<UserEntity> page = new Query<UserEntity>(params).getPage();
        page.setRecords(baseMapper.getList(page, params));
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UserEntity> page = this.selectPage(
                new Query<UserEntity>(params).getPage(),
                new EntityWrapper<UserEntity>().eq("status", params.get("status"))
        );

        return new PageUtils(page);
    }

    @Override
    public R updateByUserId(UserEntity userEntity) {
        if (!StringUtils.isEmpty(userEntity.getPassword())) {
            // 修改密码
            userEntity.setPassword(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()));
        }
        baseMapper.updateById(userEntity);
        if (userEntity.getStatus() != null && userEntity.getStatus() == 2) {
            if (userEntity.getHighUserId() != null) {
                IntervalStepEntity intervalStepEntity = intervalStepService.selectById(1);
                UserEntity highOneUser = baseMapper.selectById(userEntity.getHighUserId());
                UserEntity highTwoUser = new UserEntity();
                if (highOneUser != null) {//修改一级下级
                    if (highOneUser.getLowUserId() != null) {
                        StringBuilder str = new StringBuilder(highOneUser.getLowUserId()).append(",").append(userEntity.getId());
                        highOneUser.setLowUserId(str.toString());
                        highOneUser.setFreeMoney(highOneUser.getFreeMoney().add(intervalStepEntity.getRegisterUser()));
                    } else {
                        highOneUser.setLowUserId(userEntity.getId().toString());
                    }
                    if (baseMapper.updateById(highOneUser) > 0) {//修改二级下级
                        highTwoUser = baseMapper.selectById(highOneUser.getHighUserId());
                        if (highTwoUser != null) {
                            if (highTwoUser.getLowerUserId() != null) {
                                StringBuilder str1 = new StringBuilder(highTwoUser.getLowerUserId()).append(",").append(userEntity.getId());
                                highTwoUser.setLowerUserId(str1.toString());
                            } else {
                                highTwoUser.setLowerUserId(userEntity.getId().toString());
                            }
                            baseMapper.updateById(highTwoUser);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<UserEntity> selectByUserId(Long id) {
        List<UserEntity> list = new ArrayList<>();
        UserEntity userEntity = baseMapper.selectByUserId(id);
        while (userEntity != null) {
            UserEntity userEntity1 = baseMapper.selectByUserId(Long.parseLong(userEntity.getHighUserId()));
            userEntity = userEntity1;
            if (userEntity1 != null) {
                list.add(userEntity1);
            }
        }
        return list;
    }

    @Override
    public List<UserEntity> selectLowUserByUserId(Long id) {
        String lowUserId = baseMapper.selectLowUserByUserId(id).getLowUserId();
        return getUserListByIds(lowUserId);
    }

    @Override
    public List<UserEntity> selectLowerUserByUserId(Long id) {
        String lowUserId = baseMapper.selectLowerUserByUserId(id).getLowerUserId();
        return getUserListByIds(lowUserId);
    }

    private List<UserEntity> getUserListByIds(String userIds) {
        List<UserEntity> list = new ArrayList<>();

        if (userIds == null || userIds.trim().length() == 0) {
            return list;
        }

        String[] userIdStrings = userIds.split(",");
        if (userIdStrings.length > 0) {
            List<String> userIdList = new ArrayList<>();
            // 获取下级用户详情信息 并去除重复值
            for (String userIdString : userIdStrings) {
                if (StringUtils.isNumber(userIdString) && !userIdList.contains(userIdString)) {
                    userIdList.add(userIdString);
                    UserEntity userEntity = baseMapper.selectByUserId(Long.parseLong(userIdString));
                    list.add(userEntity);
                }
            }
        }
        return list;
    }

    @Override
    public R batchUpdate(Map<String, Object> params) {
        String ids = null;
        BigDecimal limit = null;
        Integer dayLimit = null;
        Integer monthLimit = null;
        Integer withLimit = null;
        Integer role = null;
        Integer status = null;
        String[] ids1 = null;
        if (params.containsKey("ids")) {
            ids = params.get("ids").toString();
            if (ids != null && ids.length() > 0) {
                ids = ids.replace('[', ' ').replace(']', ' ');
                ids1 = ids.split(",");
            }
            if (params.containsKey("status")) {
                if (!StringUtils.isEmpty(params.get("status").toString())) {
                    status = Integer.parseInt(params.get("status").toString());
                }
            }
            if (params.containsKey("role")) {
                if (!StringUtils.isEmpty(params.get("role").toString())) {
                    role = Integer.parseInt(params.get("role").toString());
                }
            }
            if (params.containsKey("limit")) {
                if (!StringUtils.isEmpty(params.get("limit").toString())) {
                    limit = new BigDecimal(params.get("limit").toString());
                }
            }
            if (params.containsKey("dayLimit")) {
                if (!StringUtils.isEmpty(params.get("dayLimit").toString())) {
                    dayLimit = Integer.parseInt(params.get("dayLimit").toString());
                }
            }
            if (params.containsKey("monthLimit")) {
                if (!StringUtils.isEmpty(params.get("monthLimit").toString())) {
                    monthLimit = Integer.parseInt(params.get("monthLimit").toString());
                }
            }
            if (params.containsKey("withLimit")) {
                if (!StringUtils.isEmpty(params.get("withLimit").toString())) {
                    withLimit = Integer.parseInt(params.get("withLimit").toString());
                }
            }
            if (ids1.length > 0) {
                for (String str : ids1) {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setStatus(status);
                    userEntity.setRole(role);
                    userEntity.setLimit(limit);
                    userEntity.setDayLimit(dayLimit);
                    userEntity.setMonthLimit(monthLimit);
                    userEntity.setWithLimit(withLimit);
                    userEntity.setId(Long.parseLong(str.toString().trim()));
                    baseMapper.updateById(userEntity);
                }
                return R.ok();
            }
        } else {
            return R.error(500, "请选择用户!");
        }
        return null;
    }

    @Override
    public List<UserEntity> getHighUserId(Long highUserId) {
        return baseMapper.getHighUserId(highUserId);
    }


    @Override
    public List<LowUserVo> getFirstUserByUserId(Map<String, Object> params, Long userId, Integer type) {
        Page<UserEntity> page = new Query<UserEntity>(params).getPage();
        List<LowUserVo> voList = new ArrayList<>();
        List<UserEntity> list = new ArrayList<>();
        if (type == 1) {
            list = baseMapper.getFirstUserByUserId(page, userId);
        } else if (type == 2) {
            list = baseMapper.getSecondUserByUserId(page, userId);
        }
        for (UserEntity userEntity : list) {
            LowUserVo lowUserVo = new LowUserVo();
            Integer count = missionService.selectCount(new EntityWrapper<MissionEntity>().eq("user_id", userEntity.getId()).eq("status", 7));
            lowUserVo.setUserName(userEntity.getName());
            lowUserVo.setCreateTime(userEntity.getCreateTime());
            lowUserVo.setCount(count);
            voList.add(lowUserVo);
        }
        return voList;
    }
}
