package io.chaoshua.modules.app.vo;

import io.chaoshua.modules.background.entity.DetailEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dws on 2018/11/20 0020.
 */
@ApiModel("流水明细")
public class DetailVo {

    @ApiModelProperty("流水ID")
    private Long id;

    @ApiModelProperty("佣金类型")
    private Integer type;

   @ApiModelProperty("佣金变动金额总值")
    private BigDecimal amount;

    @ApiModelProperty("流水备注")
    private String note;

    @ApiModelProperty("订单编号")
    private String code;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("操作类型格式化")
    private String typeStr;

    /**
     * 设置：
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * 获取：
     */
    public Long getId() {
        return id;
    }
    /**
     * 设置：佣金流动类型（1商家充值，2刷手接单获得不可提现佣金，3商家发布任务被拒绝返回的佣金，
     4指定评价的佣金，5刷手订单被审核通过获得的佣金，6刷手提现的佣金，7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，8下家完成任务的佣金）
     */
    public void setType(Integer type) {
        this.type = type;
    }
    /**
     * 获取：佣金流动类型（1商家充值，2刷手接单获得不可提现佣金，3商家发布任务被拒绝返回的佣金，
     4指定评价的佣金，5刷手订单被审核通过获得的佣金，6刷手提现的佣金，7订单被撤销，返回给商家的佣金或是扣除刷手的佣金，8下家完成任务的佣金）
     */
    public Integer getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 设置：流水备注
     */
    public void setNote(String note) {
        this.note = note;
    }
    /**
     * 获取：流水备注
     */
    public String getNote() {
        return note;
    }
    /**
     * 设置：订单编号
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * 获取：订单编号
     */
    public String getCode() {
        return code;
    }
    /**
     * 设置：
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取：
     */
    public Date getCreateTime() {
        return createTime;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public static DetailVo toVO(DetailEntity entity){
        DetailVo detailVo = new DetailVo();
        detailVo.setId(entity.getId());
        detailVo.setCreateTime(entity.getCreateTime());
        detailVo.setCode(entity.getCode());
        detailVo.setAmount(entity.getAmount());
        detailVo.setType(entity.getType());
        detailVo.setTypeStr(entity.getTypeStr());
        detailVo.setNote(entity.getNote());
        return detailVo;
    }
}
