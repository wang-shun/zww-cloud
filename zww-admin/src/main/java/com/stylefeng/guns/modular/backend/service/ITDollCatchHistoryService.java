package com.stylefeng.guns.modular.backend.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.TDoll;
import com.stylefeng.guns.common.persistence.model.TDollCatchHistory;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-06-14
 */
public interface ITDollCatchHistoryService extends IService<TDollCatchHistory> {

    List<Map<String, Object>> selectDollCatchHistorys(Page<TDollCatchHistory> page, Integer dollId, String dollName, String machineCode, String dollCatchStates, Integer machineType, String memberName,String beginDate,String endtime);
}
