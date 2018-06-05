package com.stylefeng.guns.modular.agent.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.AgentWithdrawMapper;
import com.stylefeng.guns.common.persistence.model.AgentWithdraw;
import com.stylefeng.guns.modular.agent.service.IAgentWithdrawService;
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
 * @since 2018-06-04
 */
@Service
public class AgentWithdrawServiceImpl extends ServiceImpl<AgentWithdrawMapper, AgentWithdraw> implements IAgentWithdrawService {

    @Autowired
    private AgentWithdrawMapper agentWithdrawMapper;

    @Override
    public List<Map<String, Object>> selectAgentWithdrow(Page<AgentWithdraw> page, Integer type,Integer status, String name, String phone, String createDate) {
        return agentWithdrawMapper.selectAgentWithdrow(page,type,status,name,phone,createDate);
    }

    @Override
    public int updateStatusById(AgentWithdraw agentWithdraw){
        return agentWithdrawMapper.updateStatusById(agentWithdraw);
    }
}
