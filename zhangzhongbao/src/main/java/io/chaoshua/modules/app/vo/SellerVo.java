package io.chaoshua.modules.app.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dws on 2018/12/21 0021.
 */
@ApiModel("商家信息")
public class SellerVo {
    @ApiModelProperty("商家ID")
    private Long id;
    @ApiModelProperty("注册时间")
    private Date createTime;
    @ApiModelProperty("名称")
    private String contact;
    @ApiModelProperty("审核状态")
    private Integer status;
    @ApiModelProperty("审核状态格式化")
    private String statusStr;
    @ApiModelProperty("完成订单数")
    private Integer count;
    @ApiModelProperty("时间格式化")
    private String createTimeStr;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreateTimeStr() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return  simpleDateFormat.format(getCreateTime());
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatusStr() {//注册状态（1：未审核，2：已审核，3：被拒绝）
        if (status != null){
            if (status == 1){
                statusStr = "未审核";
            }else if (status == 2){
                statusStr = "已审核";
            }else if (status == 3){
                statusStr = "被拒绝";
            }
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
