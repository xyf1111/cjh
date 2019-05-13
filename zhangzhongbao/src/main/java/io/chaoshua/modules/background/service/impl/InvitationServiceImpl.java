package io.chaoshua.modules.background.service.impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.InvitationDao;
import io.chaoshua.modules.background.entity.InvitationEntity;
import io.chaoshua.modules.background.service.InvitationService;



@Service("invitationService")
public class InvitationServiceImpl extends ServiceImpl<InvitationDao, InvitationEntity> implements InvitationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String code = null;
        String mobile = null;
        String startTime = null;
        String endTime = null;
        Integer isUse = null;
        Integer status = null;
        String userId = null;
        if (params.containsKey("code") && !StringUtils.isEmpty(params.get("code").toString())){
            code = params.get("code").toString();
        }
        if(params.containsKey("mobile") && !StringUtils.isEmpty(params.get("mobile").toString())){
            mobile = params.get("mobile").toString();
        }
        if(params.containsKey("startTime") && !StringUtils.isEmpty(params.get("startTime").toString())){
            startTime = params.get("startTime").toString();
        }
        if(params.containsKey("endTime") && !StringUtils.isEmpty(params.get("endTime").toString())){
            endTime = params.get("endTime").toString();
        }
        if(params.containsKey("isUse") && !StringUtils.isEmpty(params.get("isUse").toString())){
            isUse = Integer.parseInt(params.get("isUse").toString());
        }
        if(params.containsKey("status") && !StringUtils.isEmpty(params.get("status").toString())){
            status = Integer.parseInt(params.get("status").toString());
        }
        if(params.containsKey("userId") && !StringUtils.isEmpty(params.get("userId").toString())){
            userId = params.get("userId").toString();
        }
        Page<InvitationEntity> page = this.selectPage(
                new Query<InvitationEntity>(params).getPage(),
                new EntityWrapper<InvitationEntity>()
                    .like(!StringUtils.isEmpty(code), "code", code)
                    .like(!StringUtils.isEmpty(mobile), "mobile", mobile)
                    .ge(!StringUtils.isEmpty(startTime), "create_time", startTime)
                    .le(!StringUtils.isEmpty(endTime), "create_time", endTime)
                    .eq(isUse != null, "is_use", isUse)
                    .eq(status != null, "status", status)
                    .eq(!StringUtils.isEmpty(userId), "user_id", userId)
                    .orderBy("id", false)
                //.orderBy("create_time",false) 按照时间排序可能会重复，因为创建时间是同一个时候生成的
        );

        return new PageUtils(page);
    }


    @Override
    public boolean insert(InvitationEntity entity) {
        Integer number = entity.getNumber();
        entity.setCreateTime(new Date());
        for (int i = 0;i<number;i++){
            entity.setCode(generateRandomStr(12));
            super.insert(entity);
        }
        return true;
    }

    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减

        String generateSource = "23456789abcdefghgklmnpqrstuvwxyz";//去掉1和i ，0和o
        generateSource = generateSource.toUpperCase();
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }
}
