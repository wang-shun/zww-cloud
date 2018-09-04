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
 * @author bruce
 * @since 2018-07-23
 */
@TableName("t_doll_info_history")
public class TDollInfoHistory extends Model<TDollInfoHistory> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 娃娃的识别码
     */
	private String dollCode;
    /**
     * 库存数
     */
	private Integer dollTotal;
    /**
     * 存入前数量
     */
	@TableField("num_start")
	private Integer numStart;
    /**
     * 存入后数量
     */
	@TableField("num_end")
	private Integer numEnd;
    /**
     * 房间名
     */
	private String dollName;
    /**
     * 图片
     */
	@TableField("img_url")
	private String imgUrl;
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
     * 操作人
     */
	private Integer modifyer;
    /**
     * 修改时间
     */
	private Date createTime;

	/**
	 * 进货时间
	 */
	private Date stockDate;

	/**
	 * 进货时间
	 */
	private Long price;

	public TDollInfoHistory() {
	}

	public TDollInfoHistory(TDollInfo tDollInfo,Integer numStart,Integer numEnd,Integer userId) {
		this.dollCode = tDollInfo.getDollCode();
		this.dollTotal = numEnd-numStart;
		this.numStart = numStart;
		this.numEnd = numEnd;
		this.price = tDollInfo.getDollCoins();
		this.stockDate = tDollInfo.getStockDate();
		this.dollName = tDollInfo.getDollName();
		this.imgUrl = tDollInfo.getImgUrl();
		this.note = tDollInfo.getNote();
		this.modifyer = userId;
		this.createTime = new Date();
	}

	public TDollInfoHistory(TDollInfo tDollInfo,Integer numStart,Integer num,Integer userId,Date date,String note) {
		this.dollCode = tDollInfo.getDollCode();
		this.dollTotal = num;
		this.numStart = numStart;
		this.numEnd = numStart + num;
		this.price = tDollInfo.getDollCoins();
		this.stockDate = tDollInfo.getStockDate();
		this.dollName = tDollInfo.getDollName();
		this.imgUrl = tDollInfo.getImgUrl();
		this.note = note;
		this.modifyer = userId;
		this.createTime = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDollCode() {
		return dollCode;
	}

	public void setDollCode(String dollCode) {
		this.dollCode = dollCode;
	}

	public Integer getDollTotal() {
		return dollTotal;
	}

	public void setDollTotal(Integer dollTotal) {
		this.dollTotal = dollTotal;
	}

	public Integer getNumStart() {
		return numStart;
	}

	public void setNumStart(Integer numStart) {
		this.numStart = numStart;
	}

	public Integer getNumEnd() {
		return numEnd;
	}

	public void setNumEnd(Integer numEnd) {
		this.numEnd = numEnd;
	}

	public String getDollName() {
		return dollName;
	}

	public void setDollName(String dollName) {
		this.dollName = dollName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public Integer getModifyer() {
		return modifyer;
	}

	public void setModifyer(Integer modifyer) {
		this.modifyer = modifyer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStockDate() {
		return stockDate;
	}

	public void setStockDate(Date stockDate) {
		this.stockDate = stockDate;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TDollInfoHistory{" +
			"id=" + id +
			", dollCode=" + dollCode +
			", dollTotal=" + dollTotal +
			", numStart=" + numStart +
			", numEnd=" + numEnd +
			", dollName=" + dollName +
			", imgUrl=" + imgUrl +
			", agency=" + agency +
			", size=" + size +
			", type=" + type +
			", note=" + note +
			", modifyer=" + modifyer +
			", createTime=" + createTime +
			", stockDate=" + stockDate +
			", price=" + price +
			"}";
	}
}
