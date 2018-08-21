package com.stylefeng.guns.modular.backend.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.TDoll;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liangchangchun
 * @since 2018-01-24
 */
public interface ITDollService extends IService<TDoll> {

	List<Map<String, Object>> selectDolls(Page<TDoll> page,Integer dollId, String name, String machineCode, String machineStates,Integer machineType,Integer modifiedBy);


	String getDollH5ByDollId(Integer dollId);

	int updateDollH5ByDollId(String rtmpUrlH5,TDoll doll);

	int insertDoll(TDoll tDoll);


	TDoll getDollByMachineCode(String machineCode);
}
