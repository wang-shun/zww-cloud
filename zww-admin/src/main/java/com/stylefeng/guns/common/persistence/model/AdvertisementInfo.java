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
 * @since 2018-06-08
 */
@TableName("advertisement_info")
public class AdvertisementInfo extends Model<AdvertisementInfo> {

    private static final long serialVersionUID = 1L;

	private Integer id;
    /**
     * 标题
     */
	private String title;
    /**
     * 内容
     */
	private String content;
    /**
     * 图片地址
     */
	@TableField("img_url")
	private String imgUrl;
    /**
     * 下载次数
     */
	@TableField("down_count")
	private Long downCount;
	@TableField("x_axis")
	private Double xAxis;
	@TableField("y_axis")
	private Double yAxis;
	@TableField("word_color")
	private String wordColor;
	@TableField("create_date")
	private Date createDate;
	@TableField("update_date")
	private Date updateDate;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Long getDownCount() {
		return downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	public Double getxAxis() {
		return xAxis;
	}

	public void setxAxis(Double xAxis) {
		this.xAxis = xAxis;
	}

	public Double getyAxis() {
		return yAxis;
	}

	public void setyAxis(Double yAxis) {
		this.yAxis = yAxis;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getWordColor() {
		return wordColor;
	}

	public void setWordColor(String wordColor) {
		this.wordColor = wordColor;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "AdvertisementInfo{" +
			"id=" + id +
			", title=" + title +
			", content=" + content +
			", imgUrl=" + imgUrl +
			", downCount=" + downCount +
			", xAxis=" + xAxis +
			", yAxis=" + yAxis +
			", wordColor=" + wordColor +
			", createDate=" + createDate +
			", updateDate=" + updateDate +
			"}";
	}
}
