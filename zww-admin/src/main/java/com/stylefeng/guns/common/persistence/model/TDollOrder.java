package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author konghuanhuan
 * @since 2018-01-30
 */
@TableName("t_doll_order")
@ExcelTarget("tDollOrder")
public class TDollOrder extends Model<TDollOrder> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 抓到娃娃后生成的订单编号
     */
	@TableField("order_number")
	private String orderNumber;
	@TableField("order_date")
	private Date orderDate;
    /**
     * 收货人外键 t_member
     */
	@TableField("order_by")
	private Integer orderBy;

	/**
	 * 用户姓名
	 */
	@TableField("name")
	private String username;
    /**
     * 订单状态
     */
	private String status;
    /**
     * 寄存有效期
     */
	@TableField("stock_valid_date")
	private Date stockValidDate;
    /**
     * 发货时间
     */
	@TableField("deliver_date")
	private Date deliverDate;
    /**
     * 发货方式（快递公司名称）
     */
	@TableField("deliver_method")
	private String deliverMethod;
    /**
     * 快递单号
     */
	@TableField("deliver_number")
	private String deliverNumber;
    /**
     * 邮费金额，暂不用，预留字段
     */
	@TableField("deliver_amount")
	private BigDecimal deliverAmount;
    /**
     * 邮费等值的游戏币
     */
	@TableField("deliver_coins")
	private Integer deliverCoins;

	@TableField("lover")
	@Excel(name = "用户七夕活动", orderNum = "5", isImportField = "street",width = 20.0D)
	private String lover;
    /**
     * 发货地址外键 t_member_addr
     */
	@TableField("address_id")
	private Integer addressId;
	@TableField("modified_date")
	private Date modifiedDate;
	@TableField("modified_by")
	private Integer modifiedBy;
	private String comment;

	@TableField(exist=false)
	@Excel(name = "收货人", orderNum = "3", isImportField = "addrName",width = 20.0D)
	private String addrName;//收货人
	@TableField(exist=false)
	@Excel(name = "收货手机", orderNum = "4", isImportField = "addrPhone",width = 15.0D)
	private String addrPhone;//收货手机
	@TableField(exist=false)
	@Excel(name = "省", orderNum = "6", isImportField = "province",width = 15.0D)
	private String province;//省
	@TableField(exist=false)
	@Excel(name = "市", orderNum = "7",  isImportField = "city",width = 15.0D)
	private String city;//市
	@TableField(exist=false)
	@Excel(name = "区", orderNum = "8", isImportField = "county",width = 15.0D)
	private String county;//区
	@TableField(exist=false)
	@Excel(name = "街道", orderNum = "9", isImportField = "street",width = 70.0D)
	private String street;//街道

	@TableField(exist=false)
	private String memberIDs;

	@TableField(exist=false)
	private String dollCodes; //娃娃编号

	@TableField(exist=false)
	@Excel(name = "数量", orderNum = "2", isImportField = "quantity")
	private Integer quantity; //娃娃数量

	@TableField(exist=false)
	@Excel(name = "娃娃名称", orderNum = "1",isImportField = "dollName",width = 20.0D)
	private String dollName; //娃娃名称

	@TableField(exist=false)
	private String imgUrl; //娃娃url

	public TDollOrder() {
	}

	public TDollOrder(String orderNumber, Date orderDate, Integer orderBy, String status, Date stockValidDate,Integer deliverCoins, Integer modifiedBy,Integer dollRedeemCoins) {
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.orderBy = orderBy;
		this.status = status;
		this.stockValidDate = stockValidDate;
		this.deliverCoins = deliverCoins;
		this.modifiedBy = modifiedBy;
		this.dollRedeemCoins = dollRedeemCoins;
	}

	public String getDollCodes() {
		return dollCodes;
	}

	public void setDollCodes(String dollCodes) {
		this.dollCodes = dollCodes;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

	@TableField("doll_redeem_coins")
	private Integer dollRedeemCoins;//返币数

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getDollRedeemCoins() {
		return dollRedeemCoins;
	}

	public void setDollRedeemCoins(Integer dollRedeemCoins) {
		this.dollRedeemCoins = dollRedeemCoins;
	}

	public String getMemberIDs() {
		return memberIDs;
	}

	public void setMemberIDs(String memberIDs) {
		this.memberIDs = memberIDs;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public String getAddrPhone() {
		return addrPhone;
	}

	public void setAddrPhone(String addrPhone) {
		this.addrPhone = addrPhone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStockValidDate() {
		return stockValidDate;
	}

	public void setStockValidDate(Date stockValidDate) {
		this.stockValidDate = stockValidDate;
	}

	public Date getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getDeliverMethod() {
		return deliverMethod;
	}

	public void setDeliverMethod(String deliverMethod) {
		this.deliverMethod = deliverMethod;
	}

	public String getDeliverNumber() {
		return deliverNumber;
	}

	public void setDeliverNumber(String deliverNumber) {
		this.deliverNumber = deliverNumber;
	}

	public BigDecimal getDeliverAmount() {
		return deliverAmount;
	}

	public void setDeliverAmount(BigDecimal deliverAmount) {
		this.deliverAmount = deliverAmount;
	}

	public Integer getDeliverCoins() {
		return deliverCoins;
	}

	public void setDeliverCoins(Integer deliverCoins) {
		this.deliverCoins = deliverCoins;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLover() {
		return lover;
	}

	public void setLover(String lover) {
		this.lover = lover;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TDollOrder{" +
			"id=" + id +
			", orderNumber=" + orderNumber +
			", orderDate=" + orderDate +
			", orderBy=" + orderBy +
			", status=" + status +
			", stockValidDate=" + stockValidDate +
			", deliverDate=" + deliverDate +
			", deliverMethod=" + deliverMethod +
			", deliverNumber=" + deliverNumber +
			", deliverAmount=" + deliverAmount +
			", deliverCoins=" + deliverCoins +
			", addressId=" + addressId +
			", modifiedDate=" + modifiedDate +
			", modifiedBy=" + modifiedBy +
			", comment=" + comment +
			", addrName=" + addrName +
			", addrPhone=" + addrPhone +
			", province=" + province +
			", city=" + city +
			", county=" + county +
			", street=" + street +
			", memberIDs=" + memberIDs +
			", dollRedeemCoins=" + dollRedeemCoins +
			", dollCode=" + dollCodes +
			", dollName=" + dollName +
			", imgUrl=" + imgUrl +
			", quantity=" + quantity +
			", lover=" + lover +
			"}";
	}
}
