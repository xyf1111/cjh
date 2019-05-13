package io.chaoshua.modules.seller.service.impl;


import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.chaoshua.common.exception.RRException;
import io.chaoshua.common.validator.Assert;
import io.chaoshua.modules.app.form.LoginForm;
import io.chaoshua.modules.seller.dao.AppSellerDao;
import io.chaoshua.modules.seller.entity.AppSellerEntity;
import io.chaoshua.modules.seller.service.AppSellerService;
import org.springframework.stereotype.Service;


@Service("appSellerService")
public class AppSellerServiceImpl extends ServiceImpl<AppSellerDao, AppSellerEntity> implements AppSellerService {

	@Override
	public AppSellerEntity queryByMobile(String mobile) {
		AppSellerEntity sellerEntity = new AppSellerEntity();
		sellerEntity.setMobile(mobile);
		sellerEntity.setStatus(2);
		return baseMapper.selectOne(sellerEntity);
	}

	@Override
	public long login(LoginForm form) {
		AppSellerEntity tem = new AppSellerEntity();
		tem.setMobile(form.getMobile());
		AppSellerEntity ase = baseMapper.selectOne(tem);
		Assert.isNull(ase, "手机号或密码错误");

		if (ase.getStatus() == 1) {
			throw new RRException("该商家还未通过平台审核");
		}

		//密码错误
		if (!BCrypt.checkpw(form.getPassword(), ase.getPassword())){
			throw new RRException("手机号或密码错误");
		}

		return ase.getId();
	}
}
