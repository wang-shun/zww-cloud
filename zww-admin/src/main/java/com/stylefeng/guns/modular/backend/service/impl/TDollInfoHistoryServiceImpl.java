package com.stylefeng.guns.modular.backend.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.TDollInfoHistoryMapper;
import com.stylefeng.guns.common.persistence.model.TDollInfoHistory;
import com.stylefeng.guns.modular.backend.service.ITDollInfoHistoryService;
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
 * @since 2018-07-23
 */
@Service
public class TDollInfoHistoryServiceImpl extends ServiceImpl<TDollInfoHistoryMapper, TDollInfoHistory> implements ITDollInfoHistoryService {
    @Autowired
    private TDollInfoHistoryMapper tDollInfoHistoryMapper;

    @Override
    public List<Map<String, Object>> selectDollInfoHistorys(Page<TDollInfoHistory> page, String dollCode, String dollName){
        return tDollInfoHistoryMapper.selectDollInfoHistorys(page,dollCode,dollName);
    }
}
