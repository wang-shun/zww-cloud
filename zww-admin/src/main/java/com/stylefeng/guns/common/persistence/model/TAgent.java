package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;


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
	 * md5密码盐
	 */
	private String salt;

	/**
	 * 真实姓名
	 */
	@TableField("nick_name")
	private String nickName;

    /**
     * 手机号
     */
	private String phone;

    /**
     * 代理等级（0特级 1 ， 2 ,  3）
     */
	private Integer level;
    /**
     * 状态 (1正常 ，2冻结，3失效)
     */
	private Integer status;

	/**
	 * 费率
	 */
	private Double fee;
	/**
	 * 余额
	 */
	private Long balance;

	/**
	 * 冻结金额
	 */
	@TableField("balance_disabled")
	private Long balanceDisabled;



	/**
	 * 是否是贴牌商（0不是   1是）
	 */
	@TableField("is_oem")
	private Boolean oem;
    /**
     * 特级代理id
     */
	@TableField("agent_id")
	private Integer agentId;
    /**
     * 一级代理id
     */
	@TableField("agent_one_id")
	private Integer agentOneId;
    /**
     * 二级代理id
     */
	@TableField("agent_two_id")
	private Integer agentTwoId;
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


	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSalt() {

		return salt;
	}

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

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
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

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public Long getBalanceDisabled() {
		return balanceDisabled;
	}

	public void setBalanceDisabled(Long balanceDisabled) {
		this.balanceDisabled = balanceDisabled;
	}


	public Boolean getOem() {
		return oem;
	}

	public void setOem(Boolean oem) {
		this.oem = oem;
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
			", balanceDisabled=" + balanceDisabled +
			", salt=" + salt +
			", oem=" + oem +
			"}";
	}
}
