package io.chaoshua.modules.app.service.impl;


import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.modules.app.dao.AppUserDao;
import io.chaoshua.modules.app.entity.AppUserEntity;
import io.chaoshua.modules.app.service.AppUserService;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.modules.app.form.LoginForm;
import org.springframework.stereotype.Service;


@Service("appUserService")
public class AppUserServiceImpl extends ServiceImpl<AppUserDao, AppUserEntity> implements AppUserService {

	@Override
	public AppUserEntity queryByMobile(String mobile) {
		AppUserEntity userEntity = new AppUserEntity();
		userEntity.setMobile(mobile);
		return baseMapper.selectOne(userEntity);
	}

	@Override
	public long login(LoginForm form) {
		AppUserEntity user = queryByMobile(form.getMobile());
		if (user == null){
			throw new RRException("手机号或密码错误");
		}
		Integer status = user.getStatus();
		if (status == 1){
			throw new RRException("您的账号还是待审核状态，请联系客服!");
		}else if (status == 3){
			throw new RRException("您的账号审核被拒绝，请联系客服!");
		}else if (status == 4){
			throw new RRException("您的账号被禁用，请联系客服!");
		}
//		if (user.getRole() == 2){
//			throw new RRException("您已经被加入黑名单,请联系客服!");
//		}
		//密码错误
		if(!BCrypt.checkpw(form.getPassword(),user.getPassword())){
			throw new RRException("手机号或密码错误");
		}

		return user.getId();
	}
}
