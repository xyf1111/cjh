package io.chaoshua.modules.app.service;


import com.baomidou.mybatisplus.service.IService;
import io.chaoshua.common.utils.AppResult;
import io.chaoshua.modules.app.entity.AppUserEntity;
import io.chaoshua.modules.app.form.RegisterForm;

/**
 * Created by dws on 2018/10/23 0023.
 */
public interface RegisterService extends  IService<AppUserEntity>{

    /**
     * 注册
     * @param form
     * @return
     */
   AppResult register(RegisterForm form);
}
