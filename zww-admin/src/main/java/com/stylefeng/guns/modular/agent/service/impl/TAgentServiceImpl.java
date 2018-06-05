package com.stylefeng.guns.modular.agent.service.impl;

import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.common.persistence.dao.TAgentMapper;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2018-05-31
 */
@Service
public class TAgentServiceImpl extends ServiceImpl<TAgentMapper, TAgent> implements ITAgentService {

    @Autowired
    private  TAgentMapper tAgentMapper;

    @Override
    public TAgent selectLevelById(Integer id) {
        return tAgentMapper.selectLevelById(id);
    }
}
