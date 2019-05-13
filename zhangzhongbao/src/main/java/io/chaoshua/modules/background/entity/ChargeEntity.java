package io.chaoshua.modules.background.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.chaoshua.common.UploadUrl.UploadUrl;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 商家充值记录表
 * 
 * @author dws
 * @email 825068490@gmail.com
 * @date 2018-10-31 09:41:58
 */
@TableName("t_charge")
public class ChargeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 商家ID
	 */
	private Long sellerId;
	/**
	 * 充值人类型（1：商家，2：代理）
	 */
	private Integer isSeller;
	/**
	 * 代理商ID
	 */
	private Long agentId;
	/**
	 * 商家充值截图
	 */
	private String img;
	/**
	 * 充值金额
	 */
	private BigDecimal amount;
	/**
	 * 打款银行
	 */
	private String bank;
	/**
	 * 充值人姓名
	 */
	private String name;
	/**
	 * 充值备注
	 */
	private String note;
	/**
	 * 联系人姓名
	 */
	private String contact;
	/**
	 * 管理人员id
	 */
	private Long inChargeId;
	/**
	 * 审核人员名称
	 */
	private String platformName;
	/**
	 * 充值审核状态
	 */
	private Integer status;
	/**
	 * 报错备注
	 */
	private String mistakeNote;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date updateTime;
	/**
	 * 充值审核时间
	 */
	private Date time;
	/****************添加字段开始**********************/
	@TableField(exist = false)
	/**
	 * 状态格式化
	 */
	private String statusStr;
	@TableField(exist = false)
	/**
	 * 审核端口(1:平台，2：代理)
	 */
	private Integer flag;
	@TableField(exist = false)
	/**
	 * 商家总额
	 */
	private String sellerMoney;
    /**
     * 代理商名称
     */
    @TableField(exist = false)
    private String agentName;
	/****************添加字段结束**********************/

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	/**
	 * 设置：id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：商家ID
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	/**
	 * 获取：商家ID
	 */
	public Long getSellerId() {
		return sellerId;
	}
	/**
	 * 设置：商家充值截图
	 */
	public void setImg(String img) {
		this.img = img;
	}
	/**
	 * 获取：商家充值截图
	 */
	public String getImg() {
		if (img != null){
			if (!img.contains("http")){
				img = UploadUrl.getUrl()+img;
			}
		}
		return img;
	}

	public String getStatusStr() {
		if (status != null){//充值审核状态(1：待审核，2：已审核，3：已拒绝）
			if (status == 1){
				statusStr = "待审核";
			}else if (status == 2){
				statusStr = "已审核";
			}else {
				statusStr = "已拒绝";
			}
		}
		return statusStr;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	/**
	 * 设置：充值金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取：充值金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置：打款银行
	 */
	public void setBank(String bank) {
		this.bank = bank;
	}
	/**
	 * 获取：打款银行
	 */
	public String getBank() {
		return bank;
	}
	/**
	 * 设置：充值人姓名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：充值人姓名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：充值备注
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取：充值备注
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置：联系人姓名
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	/**
	 * 获取：联系人姓名
	 */
	public String getContact() {
		return contact;
	}
	/**
	 * 设置：管理人员id
	 */
	public void setInChargeId(Long inChargeId) {
		this.inChargeId = inChargeId;
	}
	/**
	 * 获取：管理人员id
	 */
	public Long getInChargeId() {
		return inChargeId;
	}
	/**
	 * 设置：审核人员名称
	 */
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	/**
	 * 获取：审核人员名称
	 */
	public String getPlatformName() {
		return platformName;
	}
	/**
	 * 设置：充值审核状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：充值审核状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setMistakeNote(String mistakeNote) {
		this.mistakeNote = mistakeNote;
	}
	/**
	 * 获取：
	 */
	public String getMistakeNote() {
		return mistakeNote;
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
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：充值审核时间
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 获取：充值审核时间
	 */
	public Date getTime() {
		return time;
	}

	public Integer getIsSeller() {
		return isSeller;
	}

	public void setIsSeller(Integer isSeller) {
		this.isSeller = isSeller;
	}

	public String getSellerMoney() {
		return sellerMoney;
	}

	public void setSellerMoney(String sellerMoney) {
		this.sellerMoney = sellerMoney;
	}

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
