package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
/**
 * <p>
 *
 * </p>
 *
 * @author bruce
 * @since 2018-06-14
 */
@TableName("t_doll_catch_history")
public class TDollCatchHistory extends Model<TDollCatchHistory>{
	private static final long serialVersionUID = 1L;
	/**
	 * 唯一标识
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;

	/**
	 * 机器编号
	 */
	@TableField("doll_id")
	private String dollId;
	/**
	 * userId编号
	 */
	@TableField("member_id")
	private Integer memberId;

	/**
	 * 抓取时间
	 */
	@TableField("catch_date")
	private Date catchDate;
	/**
	 * 抓取状态
	 */
	@TableField("catch_status")
	private String catchStatus;
	/**
	 * 视频url
	 */
	@TableField("video_url")
	private String videoUrl;
	/**
	 * 游戏编号
	 */
	@TableField("game_num")
	private String gameNum;

	/**
	 * 房间业务类型0为普通房1为练习房2化妆 3 数码
	 */
	@TableField("machine_type")
	private Integer machineType;

	@TableField("doll_code")
	private String dollCode;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDollId() {
		return dollId;
	}

	public void setDollId(String dollId) {
		this.dollId = dollId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Date getCatchDate() {
		return catchDate;
	}

	public void setCatchDate(Date catchDate) {
		this.catchDate = catchDate;
	}

	public String getCatchStatus() {
		return catchStatus;
	}

	public void setCatchStatus(String catchStatus) {
		this.catchStatus = catchStatus;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getGameNum() {
		return gameNum;
	}

	public void setGameNum(String gameNum) {
		this.gameNum = gameNum;
	}

	public Integer getMachineType() {
		return machineType;
	}

	public void setMachineType(Integer machineType) {
		this.machineType = machineType;
	}

	public String getDollCode() {
		return dollCode;
	}

	public void setDollCode(String dollCode) {
		this.dollCode = dollCode;
	}

	@Override
	public String toString() {
		return "TDollCatchHistory{" +
				"id=" + id +
				", doll_id=" + dollId +
				", member_id=" + memberId +
				", catch_date=" + catchDate +
				", catch_status=" + catchStatus +
				", video_url=" + videoUrl +
				", game_num=" + gameNum +
				", machineType=" + machineType +
				", dollCode=" + dollCode +
				"}";
	}

}
