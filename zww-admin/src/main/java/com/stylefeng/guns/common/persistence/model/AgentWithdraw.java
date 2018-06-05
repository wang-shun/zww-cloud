package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author bruce
 * @since 2018-06-04
 */
@TableName("agent_withdraw")
public class AgentWithdraw extends Model<AgentWithdraw> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 交易流水号
     */
	@TableField("trade_no")
	private String tradeNo;
    /**
     * 代理ID
     */
	private Integer agentId;
    /**
     * 提现金额（单位：分）
     */
	private Long amount;
    /**
     * 手续费（单位：分）
     */
	private Long fee;
    /**
     * 到账金额（单位：分）
     */
	@TableField("actual_amount")
	private Long actualAmount;
    /**
     * 真实姓名
     */
	private String name;
    /**
     * 手机号
     */
	private String phone;
    /**
     * 身份证号
     */
	@TableField("id_card_no")
	private String idCardNo;
    /**
     * 卡号
     */
	@TableField("card_no")
	private String cardNo;
    /**
     * 交易备注
     */
	private String remark;
    /**
     * 状态(0 已提交 1审批成功 2审批失败 3未知)
     */
	private Integer status;
    /**
     * 创建时间
     */
	@TableField("create_date")
	private Date createDate;
    /**
     * 确认时间
     */
	@TableField("confirm_date")
	private Date confirmDate;
    /**
     * 更新时间
     */
	@TableField("update_date")
	private Date updateDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getFee() {
		return fee;
	}

	public void setFee(Long fee) {
		this.fee = fee;
	}

	public Long getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(Long actualAmount) {
		this.actualAmount = actualAmount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AgentWithdraw{" +
			"id=" + id +
			", tradeNo=" + tradeNo +
			", agentId=" + agentId +
			", amount=" + amount +
			", fee=" + fee +
			", actualAmount=" + actualAmount +
			", name=" + name +
			", phone=" + phone +
			", idCardNo=" + idCardNo +
			", cardNo=" + cardNo +
			", remark=" + remark +
			", status=" + status +
			", createDate=" + createDate +
			", confirmDate=" + confirmDate +
			", updateDate=" + updateDate +
			"}";
	}
}
