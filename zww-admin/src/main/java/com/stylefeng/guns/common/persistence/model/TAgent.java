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
 * @since 2018-05-31
 */
@TableName("t_agent")
public class TAgent extends Model<TAgent> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户名
     */
	private String username;
    /**
     * 密码
     */
	private String password;
    /**
     * 手机号
     */
	private String phone;
    /**
     * 真实姓名
     */
	@TableField("nick_name")
	private String nickName;
    /**
     * 代理等级（0特级 1 ， 2 ,  3）
     */
	private Integer level;
    /**
     * 状态 (0未开启，1正常 ，2失效)
     */
	private Integer status;
    /**
     * 特级代理id
     */
	@TableField("agent_id")
	private Long agentId;
    /**
     * 一级代理id
     */
	@TableField("agent_one_id")
	private Long agentOneId;
    /**
     * 二级代理id
     */
	@TableField("agent_two_id")
	private Long agentTwoId;
    /**
     * 创建时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 最后修改时间
     */
	@TableField("update_time")
	private Date updateTime;
    /**
     * 费率
     */
	private BigDecimal fee;
    /**
     * 余额
     */
	private Long balance;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	public Long getAgentOneId() {
		return agentOneId;
	}

	public void setAgentOneId(Long agentOneId) {
		this.agentOneId = agentOneId;
	}

	public Long getAgentTwoId() {
		return agentTwoId;
	}

	public void setAgentTwoId(Long agentTwoId) {
		this.agentTwoId = agentTwoId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TAgent{" +
			"id=" + id +
			", username=" + username +
			", password=" + password +
			", phone=" + phone +
			", nickName=" + nickName +
			", level=" + level +
			", status=" + status +
			", agentId=" + agentId +
			", agentOneId=" + agentOneId +
			", agentTwoId=" + agentTwoId +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", fee=" + fee +
			", balance=" + balance +
			"}";
	}
}
