package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("t_oem_banner")
public class TOemBanner extends Model<TOemBanner> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 贴牌id
     */
	@TableField("oem_id")
	private Integer oemId;
    /**
     * 跳转地址
     */
	private String url;
    /**
     * 图片地址
     */
	@TableField("img_url")
	private String imgUrl;
    /**
     * 排序顺序
     */
	private Integer sort;
    /**
     * 状态（0不正常 1正常）
     */
	private Integer status;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOemId() {
		return oemId;
	}

	public void setOemId(Integer oemId) {
		this.oemId = oemId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
		return "TOemBanner{" +
			"id=" + id +
			", oemId=" + oemId +
			", url=" + url +
			", imgUrl=" + imgUrl +
			", sort=" + sort +
			", status=" + status +
			"}";
	}
}
