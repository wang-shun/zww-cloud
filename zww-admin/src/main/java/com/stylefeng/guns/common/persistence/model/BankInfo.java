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
 * @since 2018-06-05
 */
@TableName("bank_info")
public class BankInfo extends Model<BankInfo> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 代理ID
     */
	@TableField("agent_id")
	private Integer agentId;
    /**
     * 银行名称
     */
	@TableField("card_bank")
	private String cardBank;
    /**
     * 支行名称
     */
	@TableField("card_sub_bank")
	private String cardSubBank;
    /**
     * 银行省份
     */
	@TableField("card_province")
	private String cardProvince;
    /**
     * 银行城市
     */
	@TableField("card_city")
	private String cardCity;
    /**
     * 银行区域
     */
	@TableField("card_area")
	private String cardArea;
    /**
     * 联行号
     */
	@TableField("card_bank_no")
	private String cardBankNo;
    /**
     * 卡号
     */
	@TableField("card_no")
	private String cardNo;
    /**
     * 身份证
     */
	@TableField("id_card_no")
	private String idCardNo;
    /**
     * 真实姓名
     */
	private String name;
    /**
     * 银行手机号
     */
	private String phone;

	/**
	 * 银行手机号
	 */
	private String message;
    /**
     * 身份证正面
     */
	@TableField("id_card_picture_pos")
	private String idCardPicturePos;
    /**
     * 身份证反面
     */
	@TableField("id_card_picture_rev")
	private String idCardPictureRev;
    /**
     * 手持身份证
     */
	@TableField("id_card_picture")
	private String idCardPicture;
    /**
     * 银行卡正面
     */
	@TableField("bank_picture_pos")
	private String bankPicturePos;
    /**
     * 银行添加时间
     */
	@TableField("create_time")
	private Date createTime;
    /**
     * 修改时间
     */
	@TableField("update_time")
	private Date updateTime;


	/**
	 * 银行手机号
	 */
	private Integer status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getCardSubBank() {
		return cardSubBank;
	}

	public void setCardSubBank(String cardSubBank) {
		this.cardSubBank = cardSubBank;
	}

	public String getCardProvince() {
		return cardProvince;
	}

	public void setCardProvince(String cardProvince) {
		this.cardProvince = cardProvince;
	}

	public String getCardCity() {
		return cardCity;
	}

	public void setCardCity(String cardCity) {
		this.cardCity = cardCity;
	}

	public String getCardArea() {
		return cardArea;
	}

	public void setCardArea(String cardArea) {
		this.cardArea = cardArea;
	}

	public String getCardBankNo() {
		return cardBankNo;
	}

	public void setCardBankNo(String cardBankNo) {
		this.cardBankNo = cardBankNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
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

	public String getIdCardPicturePos() {
		return idCardPicturePos;
	}

	public void setIdCardPicturePos(String idCardPicturePos) {
		this.idCardPicturePos = idCardPicturePos;
	}

	public String getIdCardPictureRev() {
		return idCardPictureRev;
	}

	public void setIdCardPictureRev(String idCardPictureRev) {
		this.idCardPictureRev = idCardPictureRev;
	}

	public String getIdCardPicture() {
		return idCardPicture;
	}

	public void setIdCardPicture(String idCardPicture) {
		this.idCardPicture = idCardPicture;
	}

	public String getBankPicturePos() {
		return bankPicturePos;
	}

	public void setBankPicturePos(String bankPicturePos) {
		this.bankPicturePos = bankPicturePos;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "BankInfo{" +
			"id=" + id +
			", agentId=" + agentId +
			", cardBank=" + cardBank +
			", cardSubBank=" + cardSubBank +
			", cardProvince=" + cardProvince +
			", cardCity=" + cardCity +
			", cardArea=" + cardArea +
			", cardBankNo=" + cardBankNo +
			", cardNo=" + cardNo +
			", idCardNo=" + idCardNo +
			", name=" + name +
			", phone=" + phone +
			", idCardPicturePos=" + idCardPicturePos +
			", idCardPictureRev=" + idCardPictureRev +
			", idCardPicture=" + idCardPicture +
			", bankPicturePos=" + bankPicturePos +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", status=" + status +
			", message=" + message +
			"}";
	}
}
