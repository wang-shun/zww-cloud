package com.stylefeng.guns.modular.backend.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.TDollCatchHistoryMapper;
import com.stylefeng.guns.common.persistence.model.TDollCatchHistory;
import com.stylefeng.guns.modular.backend.service.ITDollCatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2018-06-14
 */
@Service
public class TDollCatchHistoryServiceImpl extends ServiceImpl<TDollCatchHistoryMapper, TDollCatchHistory> implements ITDollCatchHistoryService {

    @Autowired
    TDollCatchHistoryMapper tDollCatchHistoryMapper;

    @Override
    public List<Map<String, Object>> selectDollCatchHistorys(Page<TDollCatchHistory> page, Integer orderId, String dollName, String machineCode, String dollCatchStates, Integer machineType, String memberName) {
        // TODO Auto-generated method stub
        return tDollCatchHistoryMapper.selectDollCatchHistorys(page,orderId,dollName, machineCode, dollCatchStates,machineType,memberName);
    }
}
