package io.chaoshua.modules.agent.service.impl;


import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.validator.Assert;
import io.chaoshua.modules.agent.dao.AppAgentDao;
import io.chaoshua.modules.agent.entity.AppAgentEntity;
import io.chaoshua.modules.agent.form.AgentLoginForm;
import io.chaoshua.modules.agent.service.AppAgentService;
import org.springframework.stereotype.Service;


@Service("appAgentService")
public class AppAgentServiceImpl extends ServiceImpl<AppAgentDao, AppAgentEntity> implements AppAgentService {

	@Override
	public AppAgentEntity queryByEmail(String email) {
		AppAgentEntity appAgentEntity = new AppAgentEntity();
		appAgentEntity.setEmail(email);
		return baseMapper.selectOne(appAgentEntity);
	}

	@Override
	public long login(AgentLoginForm form) {
		AppAgentEntity appAgentEntity = queryByEmail(form.getEmail());
		Assert.isNull(appAgentEntity, "账号或密码错误");

		//密码错误
		if(!BCrypt.checkpw(form.getPassword(),appAgentEntity.getPassword())){
			throw new RRException("账号或密码错误");
		}

		return appAgentEntity.getId();
	}
}
