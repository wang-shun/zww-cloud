package com.stylefeng.guns.modular.agent.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.AgentWithdrawMapper;
import com.stylefeng.guns.common.persistence.model.AgentWithdraw;
import com.stylefeng.guns.common.persistence.model.BankInfo;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.modular.agent.service.IAgentWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public List<Map<String, Object>> selectAgentWithdrow(Page<AgentWithdraw> page,Integer agentId, Integer type,Integer status, String name, String phone, String createDate) {
        return agentWithdrawMapper.selectAgentWithdrow(page,agentId,type,status,name,phone,createDate);
    }

    @Override
    public int updateStatusById(AgentWithdraw agentWithdraw){
        return agentWithdrawMapper.updateStatusById(agentWithdraw);
    }

    @Override
    public AgentWithdraw getSumAmountByAgentId(Integer agentId){
        return agentWithdrawMapper.getSumAmountByAgentId(agentId);
    }

    @Override
    public AgentWithdraw getAgentWithdrawById(Integer id){
        return agentWithdrawMapper.getAgentWithdrawById(id);
    }

    @Override
    public int createAgentWithdraw(BankInfo bankInfo, Long amount, Long fee) {
        AgentWithdraw agentWithdraw = new AgentWithdraw();
        agentWithdraw.setAgentId(bankInfo.getAgentId());
        agentWithdraw.setStatus(0);
        agentWithdraw.setTradeNo(StringUtils.getOrderNumber());
        agentWithdraw.setAmount(amount);
        agentWithdraw.setFee(fee);
        agentWithdraw.setActualAmount(amount-fee);
        agentWithdraw.setName(bankInfo.getName());
        agentWithdraw.setPhone(bankInfo.getPhone());
        agentWithdraw.setIdCardNo(bankInfo.getIdCardNo());
        agentWithdraw.setCardNo(bankInfo.getCardNo());
        agentWithdraw.setCreateDate(new Date());
        return agentWithdrawMapper.insert(agentWithdraw);
    }

    @Override
    public Date getDateByAgentIdAndStatus(Integer agentId){
        return agentWithdrawMapper.getDateByAgentIdAndStatus(agentId);
    }
}
