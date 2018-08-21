package com.stylefeng.guns.modular.backend.service.impl;

import com.stylefeng.guns.common.persistence.model.TDoll;
import com.stylefeng.guns.common.persistence.dao.TDollMapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.modular.backend.service.ITDollService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liangchangchun
 * @since 2018-01-24
 */
@Service
public class TDollServiceImpl extends ServiceImpl<TDollMapper, TDoll> implements ITDollService {
	@Autowired
	TDollMapper tDollMapper;

	@Override
	public List<Map<String, Object>> selectDolls(Page<TDoll> page,Integer orderId, String name, String machineCode, String machineStates,Integer machineType,Integer modifiedBy) {
		// TODO Auto-generated method stub
		return tDollMapper.selectDolls(page,orderId,name, machineCode, machineStates,machineType,modifiedBy);
	}
	@Override
	public String getDollH5ByDollId(Integer dollId){
		return tDollMapper.getDollH5ByDollId(dollId);
	}
	@Override
	public int updateDollH5ByDollId(String rtmpUrlH5,TDoll doll){
		String dollUrlH5 = getDollH5ByDollId(doll.getId());
		if(StringUtils.isEmpty(dollUrlH5)){
			doll.setRtmpUrlH5(rtmpUrlH5);
			return tDollMapper.insertDollH5(doll);
		}else{
			return tDollMapper.updateDollH5ByDollId(rtmpUrlH5,doll.getId());
		}
	}

	@Override
	public int insertDoll(TDoll tDoll) {
		Date date = new Date();
		Integer userId = ShiroKit.getUser().getId();
		tDoll.setQuantity(99);
		tDoll.setTbimgRealPath("#");
		tDoll.setDeleteStatus(1);
		tDoll.setCreatedDate(date);
		tDoll.setCreatedBy(userId);
		tDoll.setModifiedDate(date);
		tDoll.setModifiedBy(userId);
		tDoll.setMachineIp("devicea_" + tDoll.getMachineCode());
		tDoll.setMachineUrl("devicea_" + tDoll.getMachineCode());
		tDoll.setMachineSerialNum("devicea_" + tDoll.getMachineCode());
		return tDollMapper.insert(tDoll);
	}

	@Override
	public TDoll getDollByMachineCode(String machineCode) {
		return tDollMapper.getDollByMachineCode(machineCode);
	}
}
