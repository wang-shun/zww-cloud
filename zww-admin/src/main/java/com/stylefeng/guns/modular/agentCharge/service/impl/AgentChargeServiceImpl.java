package com.stylefeng.guns.modular.agentCharge.service.impl;

import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.stylefeng.guns.common.persistence.dao.AgentChargeMapper;
import com.stylefeng.guns.modular.agentCharge.service.IAgentChargeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
@Service
public class AgentChargeServiceImpl extends ServiceImpl<AgentChargeMapper, AgentCharge> implements IAgentChargeService {

    @Autowired
    private AgentChargeMapper agentChargeMapper;

    @Override
    public AgentCharge getSumNotAmountByAgentId(AgentCharge agentCharge) {
        return agentChargeMapper.getSumNotAmountByAgentId(agentCharge);
    }
}
