package io.chaoshua.modules.agent.service;


import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.modules.agent.entity.AppAgentEntity;
import io.chaoshua.modules.agent.form.AgentLoginForm;
import io.chaoshua.modules.app.form.LoginForm;
import io.chaoshua.modules.seller.entity.AppSellerEntity;

/**
 * 用户
 * 
 * @author dws
 * @date 2017-03-23 15:22:06
 */
public interface AppAgentService extends IService<AppAgentEntity> {

	AppAgentEntity queryByEmail(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	long login(AgentLoginForm form);
}
