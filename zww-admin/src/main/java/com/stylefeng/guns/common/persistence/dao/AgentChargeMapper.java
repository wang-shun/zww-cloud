package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
public interface AgentChargeMapper extends BaseMapper<AgentCharge> {

    AgentCharge getSumNotAmountByAgentId(AgentCharge agentCharge);

}