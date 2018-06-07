package com.stylefeng.guns.modular.agent.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.AgentChargeMapper;
import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.stylefeng.guns.modular.agent.service.IAgentChargeService;
import org.apache.ibatis.annotations.Param;
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
 * @since 2018-06-05
 */
@Service
public class AgentChargeServiceImpl extends ServiceImpl<AgentChargeMapper, AgentCharge> implements IAgentChargeService {

    @Autowired
    private AgentChargeMapper agentChargeMapper;

    @Override
    public AgentCharge getSumAmountByAgentId(AgentCharge agentCharge) {
        return agentChargeMapper.getSumAmountByAgentId(agentCharge);
    }

    @Override
    public AgentCharge getSumAmountByYesterday(AgentCharge agentCharge) {
        return agentChargeMapper.getSumAmountByYesterday(agentCharge);
    }
    //代理商分润列表
    @Override
    public List<Map<String, Object>> selectAgentCharge(@Param("page") Page<AgentCharge> page, @Param("agentId") Integer agentId, @Param("level") Integer level){
        return agentChargeMapper.selectAgentCharge(page,agentId,level);
    }
}