package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2018-06-05
 */
@TableName("agent_charge")
public class AgentCharge extends Model<AgentCharge> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 订单ID
     */
	@TableField("order_id")
	private Long orderId;
    /**
     * 特级代理
     */
	@TableField("agent_super_id")
	private Integer agentSuperId;
    /**
     * 一级代理
     */
	@TableField("agent_one_id")
	private Integer agentOneId;
    /**
     * 二级代理
     */
	@TableField("agent_two_id")
	private Integer agentTwoId;
    /**
     * 三级代理
     */
	@TableField("agent_three_id")
	private Integer agentThreeId;
    /**
     * 特级费率
     */
	@TableField("agent_super_fee")
	private BigDecimal agentSuperFee;
    /**
     * 一级费率
     */
	@TableField("agent_one_fee")
	private BigDecimal agentOneFee;
    /**
     * 二级费率
     */
	@TableField("agent_two_fee")
	private BigDecimal agentTwoFee;
    /**
     * 三级费率
     */
	@TableField("agent_three_fee")
	private BigDecimal agentThreeFee;
    /**
     * 特级收入
     */
	@TableField("agent_super_income")
	private Long agentSuperIncome;
    /**
     * 一级收入
     */
	@TableField("agent_one_income")
	private Long agentOneIncome;
    /**
     * 二级收入
     */
	@TableField("agent_two_income")
	private Long agentTwoIncome;
    /**
     * 三级收入
     */
	@TableField("agent_three_income")
	private Long agentThreeIncome;
	@TableField("update_time")
	private Date updateTime;
	@TableField("create_time")
	private Date createTime;
    /**
     * (0未清算1已清算)
     */
	private Integer status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getAgentSuperId() {
		return agentSuperId;
	}

	public void setAgentSuperId(Integer agentSuperId) {
		this.agentSuperId = agentSuperId;
	}

	public Integer getAgentOneId() {
		return agentOneId;
	}

	public void setAgentOneId(Integer agentOneId) {
		this.agentOneId = agentOneId;
	}

	public Integer getAgentTwoId() {
		return agentTwoId;
	}

	public void setAgentTwoId(Integer agentTwoId) {
		this.agentTwoId = agentTwoId;
	}

	public Integer getAgentThreeId() {
		return agentThreeId;
	}

	public void setAgentThreeId(Integer agentThreeId) {
		this.agentThreeId = agentThreeId;
	}

	public BigDecimal getAgentSuperFee() {
		return agentSuperFee;
	}

	public void setAgentSuperFee(BigDecimal agentSuperFee) {
		this.agentSuperFee = agentSuperFee;
	}

	public BigDecimal getAgentOneFee() {
		return agentOneFee;
	}

	public void setAgentOneFee(BigDecimal agentOneFee) {
		this.agentOneFee = agentOneFee;
	}

	public BigDecimal getAgentTwoFee() {
		return agentTwoFee;
	}

	public void setAgentTwoFee(BigDecimal agentTwoFee) {
		this.agentTwoFee = agentTwoFee;
	}

	public BigDecimal getAgentThreeFee() {
		return agentThreeFee;
	}

	public void setAgentThreeFee(BigDecimal agentThreeFee) {
		this.agentThreeFee = agentThreeFee;
	}

	public Long getAgentSuperIncome() {
		return agentSuperIncome;
	}

	public void setAgentSuperIncome(Long agentSuperIncome) {
		this.agentSuperIncome = agentSuperIncome;
	}

	public Long getAgentOneIncome() {
		return agentOneIncome;
	}

	public void setAgentOneIncome(Long agentOneIncome) {
		this.agentOneIncome = agentOneIncome;
	}

	public Long getAgentTwoIncome() {
		return agentTwoIncome;
	}

	public void setAgentTwoIncome(Long agentTwoIncome) {
		this.agentTwoIncome = agentTwoIncome;
	}

	public Long getAgentThreeIncome() {
		return agentThreeIncome;
	}

	public void setAgentThreeIncome(Long agentThreeIncome) {
		this.agentThreeIncome = agentThreeIncome;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AgentCharge{" +
			"id=" + id +
			", orderId=" + orderId +
			", agentSuperId=" + agentSuperId +
			", agentOneId=" + agentOneId +
			", agentTwoId=" + agentTwoId +
			", agentThreeId=" + agentThreeId +
			", agentSuperFee=" + agentSuperFee +
			", agentOneFee=" + agentOneFee +
			", agentTwoFee=" + agentTwoFee +
			", agentThreeFee=" + agentThreeFee +
			", agentSuperIncome=" + agentSuperIncome +
			", agentOneIncome=" + agentOneIncome +
			", agentTwoIncome=" + agentTwoIncome +
			", agentThreeIncome=" + agentThreeIncome +
			", updateTime=" + updateTime +
			", createTime=" + createTime +
			", status=" + status +
			"}";
	}
}
