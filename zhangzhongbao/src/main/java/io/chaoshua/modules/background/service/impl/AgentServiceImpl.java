package io.chaoshua.modules.background.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import io.chaoshua.common.utils.R;
import io.chaoshua.common.utils.ShiroUtils;
import io.chaoshua.modules.background.entity.SellerEntity;
import io.chaoshua.modules.background.entity.UserEntity;
import io.chaoshua.modules.background.entity.agent.AgentMoneyEntity;
import io.chaoshua.modules.background.service.SellerService;
import io.chaoshua.modules.background.service.UserService;
import io.chaoshua.modules.background.service.agent.AgentMoneyService;
import io.chaoshua.modules.sys.entity.SysUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.common.utils.Query;

import io.chaoshua.modules.background.dao.agent.AgentDao;
import io.chaoshua.modules.background.entity.agent.AgentEntity;
import io.chaoshua.modules.background.service.agent.AgentService;
import org.springframework.transaction.annotation.Transactional;


@Service("agentService")
public class AgentServiceImpl extends ServiceImpl<AgentDao, AgentEntity> implements AgentService {
@Autowired
private UserService userService;
@Autowired
private SellerService sellerService;
@Autowired
private AgentMoneyService agentMoneyService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<AgentEntity> page = this.selectPage(
                new Query<AgentEntity>(params).getPage(),
                new EntityWrapper<AgentEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    @Transactional
    public R insertAgent(AgentEntity agentEntity) {
        String mobile = agentEntity.getMobile();
        if (sellerService.selectCount(new EntityWrapper<SellerEntity>().eq("mobile",mobile))>0){
            return R.error(500,"对不起，您的手机已经注册成商家!");
        }
        if (userService.selectCount(new EntityWrapper<UserEntity>().eq("mobile",mobile))>0){
            return R.error(500,"对不起，您的手机已注册成刷手!");
        }
        SysUserEntity sysUserEntity = ShiroUtils.getUserEntity();
        agentEntity.setInChargeId(sysUserEntity.getUserId());
        agentEntity.setStatus(1);
        agentEntity.setChargeName(sysUserEntity.getUsername());
        String password = BCrypt.hashpw("123456",BCrypt.gensalt());
        agentEntity.setPassword(password);
        agentEntity.setCreateTime(new Date());
        if (baseMapper.insert(agentEntity)>0){
            AgentMoneyEntity agentMoneyEntity = new AgentMoneyEntity();
            agentMoneyEntity.setAgentId(agentEntity.getId());
            agentMoneyEntity.setCreateTime(new Date());
            agentEntity.setInChargeId(ShiroUtils.getUserId());
            if(agentMoneyService.insert(agentMoneyEntity)){
                return R.ok();
            }else {
                return R.error(500,"系统错误，请联系管理员!");
            }
        }else {
            return R.error(500,"系统错误，请联系管理员!");
        }
    }

    @Override
    public R updatePw(Map map,Long agentId) {
        String oldPw = map.get("password").toString();
        String newPw = map.get("newPassword").toString();
        String conPw = map.get("confirmPassword").toString();
        if (!newPw.equals(conPw)){
            return R.error(500,"前后密码不一致!");
        }
        AgentEntity agentEntity = baseMapper.selectById(agentId);
        if(!BCrypt.checkpw(oldPw,agentEntity.getPassword())){
            return R.error(500,"旧密码错误!");
        }
        AgentEntity agent = new AgentEntity();
        agent.setId(agentEntity.getId());
        agent.setPassword(BCrypt.hashpw(newPw,BCrypt.gensalt()));
        baseMapper.updateById(agent);
        return R.ok();
    }

    @Override
    public AgentEntity queryByMobile(String mobile) {
        return this.selectOne(new EntityWrapper<AgentEntity>().eq("mobile", mobile));
    }
}
