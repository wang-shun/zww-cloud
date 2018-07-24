package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-07-23
 */
@TableName("t_doll_info")
public class TDollInfo extends Model<TDollInfo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 房间名
     */
	private String dollName;
    /**
     * 库存数
     */
	private Integer dollTotal;
    /**
     * 图片
     */
	@TableField("img_url")
	private String imgUrl;
    /**
     * 娃娃的识别码
     */
	private String dollCode;
    /**
     * 发货地
     */
	private String agency;
    /**
     * 尺寸
     */
	private String size;
    /**
     * 材质
     */
	private String type;
    /**
     * 备注
     */
	private String note;
    /**
     * 返币数
     */
	private Integer redeemCoins;
    /**
     * 娃娃成本
     */
	private Long dollCoins;
    /**
     * 快递费
     */
	private Long deliverCoins;
    /**
     * 进货时间
     */
	private Date addTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDollName() {
		return dollName;
	}

	public void setDollName(String dollName) {
		this.dollName = dollName;
	}

	public Integer getDollTotal() {
		return dollTotal;
	}

	public void setDollTotal(Integer dollTotal) {
		this.dollTotal = dollTotal;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDollCode() {
		return dollCode;
	}

	public void setDollCode(String dollCode) {
		this.dollCode = dollCode;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getRedeemCoins() {
		return redeemCoins;
	}

	public void setRedeemCoins(Integer redeemCoins) {
		this.redeemCoins = redeemCoins;
	}

	public Long getDollCoins() {
		return dollCoins;
	}

	public void setDollCoins(Long dollCoins) {
		this.dollCoins = dollCoins;
	}

	public Long getDeliverCoins() {
		return deliverCoins;
	}

	public void setDeliverCoins(Long deliverCoins) {
		this.deliverCoins = deliverCoins;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TDollInfo{" +
			"id=" + id +
			", dollName=" + dollName +
			", dollTotal=" + dollTotal +
			", imgUrl=" + imgUrl +
			", dollCode=" + dollCode +
			", agency=" + agency +
			", size=" + size +
			", type=" + type +
			", note=" + note +
			", redeemCoins=" + redeemCoins +
			", dollCoins=" + dollCoins +
			", deliverCoins=" + deliverCoins +
			", addTime=" + addTime +
			"}";
	}
}
