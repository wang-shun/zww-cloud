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
 * @since 2018-06-21
 */
@TableName("t_oem")
public class TOem extends Model<TOem> {

    private static final long serialVersionUID = 1L;

    /**
     * 对应agent贴牌代理id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 贴牌名
     */
	private String name;
    /**
     * 商户简称（例如lanaokj）
     */
	private String code;
    /**
     * 微信公众号id
     */
	private String appid;
    /**
     * 微信公众号秘钥
     */
	private String appsecret;
    /**
     * 商户号
     */
	private String partner;
    /**
     * 商户秘钥
     */
	@TableField("partner_key")
	private String partnerKey;
    /**
     * 公众号主体
     */
	private String company;
    /**
     * 隧道地址
     */
	@TableField("natapp_url")
	private String natappUrl;
    /**
     * 域名
     */
	private String url;
    /**
     * 短信模板
     */
	@TableField("SMS_code")
	private String smsCode;
    /**
     * 短信签名名字（例如蓝澳科技）
     */
	@TableField("SMS_name")
	private String smsName;
    /**
     * 状态（0未启用  1启用）
     */
	private Integer status;
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
     * 房间合并（0单独 1 混合）
     */
	@TableField("is_doll_merge")
	private Integer isDollMerge;

	/**
	 * 图标
	 */
	private String icon;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getPartner() {
		return partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	public String getPartnerKey() {
		return partnerKey;
	}

	public void setPartnerKey(String partnerKey) {
		this.partnerKey = partnerKey;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getNatappUrl() {
		return natappUrl;
	}

	public void setNatappUrl(String natappUrl) {
		this.natappUrl = natappUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getSmsName() {
		return smsName;
	}

	public void setSmsName(String smsName) {
		this.smsName = smsName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getIsDollMerge() {
		return isDollMerge;
	}

	public void setIsDollMerge(Integer isDollMerge) {
		this.isDollMerge = isDollMerge;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "TOem{" +
			"id=" + id +
			", name=" + name +
			", code=" + code +
			", appid=" + appid +
			", appsecret=" + appsecret +
			", partner=" + partner +
			", partnerKey=" + partnerKey +
			", company=" + company +
			", natappUrl=" + natappUrl +
			", url=" + url +
			", SMSCode=" + smsCode +
			", SMSName=" + smsName +
			", status=" + status +
			", createTime=" + createTime +
			", updateTime=" + updateTime +
			", isDollMerge=" + isDollMerge +
			", icon=" + icon +
			"}";
	}
}
