package io.chaoshua.modules.background.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.chaoshua.common.utils.PageUtils;
import io.chaoshua.modules.app.vo.SellerVo;
import io.chaoshua.modules.background.entity.SellerEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.chaoshua.modules.background.entity.TotalSellerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 商家表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-25 17:22:27
 */
@Mapper
public interface SellerDao extends BaseMapper<SellerEntity> {

    /**
     * 获取商家列表
     * @param pagination
     * @param params
     * @return
     */
    List<SellerEntity> getList(Pagination pagination , Map<String,Object> params);

    /**
     * 获取商家统计
     * @param params
     * @return
     */
    TotalSellerEntity getSumTotalBySellerName(Map params);
    /**
     * 获取商家统计
     * @param params
     * @return
     */
    List<TotalSellerEntity> getSumBalance(Map params);

    /***********************app开始***************************************/
    /**
     * 获取下级商家列表信息
     * @param pid
     * @return
     */
    List<SellerVo> getLowerSellerListByPid(Long pid);
    /***********************app结束***************************************/
}
