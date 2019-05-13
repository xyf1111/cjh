package io.chaoshua.modules.job.task;

import io.chaoshua.modules.background.dao.ShopDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 修改店铺每日剩余发单数
 * Created by dws on 2018/12/5 0005.
 */
@Component("shopTask")
public class ShopTask {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ShopDao shopDao;

    public void task(){
        logger.info("更新店铺每日剩余发单量正在被执行!");
        shopDao.updateAll();
    }

}
