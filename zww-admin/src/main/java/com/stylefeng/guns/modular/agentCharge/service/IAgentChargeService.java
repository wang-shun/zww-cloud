package com.stylefeng.guns.modular.agentCharge.service;

import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
public interface IAgentChargeService extends IService<AgentCharge> {

    AgentCharge getSumNotAmountByAgentId(AgentCharge agentCharge);

}
