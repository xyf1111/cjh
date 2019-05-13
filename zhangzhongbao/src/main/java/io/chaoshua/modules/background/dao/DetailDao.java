package io.chaoshua.modules.background.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.chaoshua.modules.background.entity.DetailEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 流水表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-18 11:54:46
 */
@Mapper
public interface DetailDao extends BaseMapper<DetailEntity> {

    /**
     * 获取列表
     * @param pagination
     * @param params
     * @return
     */
    List<DetailEntity> getList(Pagination pagination, Map<String,Object> params);

    /**
     * 根据父级id获取流水列表
     * @param params
     * @return
     */
    List<DetailEntity> getListByPid(Pagination pagination, Map<String,Object> params);

    /**
     * 获取平台修改总金额
     * @param params
     * @return
     */
    List<DetailEntity> getSumMoney(Map params);
}
