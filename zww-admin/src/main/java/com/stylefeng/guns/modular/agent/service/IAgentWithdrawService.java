package com.stylefeng.guns.modular.agent.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.AgentWithdraw;
import com.stylefeng.guns.common.persistence.model.BankInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-06-04
 */
public interface IAgentWithdrawService extends IService<AgentWithdraw> {

    //用户列表
    List<Map<String, Object>> selectAgentWithdrow(@Param("page") Page<AgentWithdraw> page,@Param("agentId") Integer agentId,@Param("type") Integer type, @Param("status") Integer status, @Param("name") String name, @Param("phone") String phone, @Param("createDate") String createDate);

    int updateStatusById(AgentWithdraw agentWithdraw);

    AgentWithdraw getSumAmountByAgentId(Integer agentId);

    AgentWithdraw getAgentWithdrawById(Integer id);

    int createAgentWithdraw(BankInfo bankInfo, Long amount, Long fee,Date date);

    Date getDateByAgentIdAndStatus(Integer agentId);
}
