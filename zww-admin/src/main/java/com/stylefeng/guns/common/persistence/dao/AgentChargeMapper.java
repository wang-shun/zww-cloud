package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.common.persistence.model.vo.AgentChargeVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
public interface AgentChargeMapper extends BaseMapper<AgentCharge> {

    AgentCharge getSumAmountByAgentId(AgentCharge agentCharge);

    AgentCharge getSumAmountByYesterday(AgentCharge agentCharge);

    //代理商分润列表
    List<Map<String, Object>> selectAgentCharge(@Param("page") Page<AgentCharge> page, @Param("agentId") Integer agentId, @Param("level") Integer level);

    List<AgentChargeVo>  getAgentChargeExecl(@Param("agentId") Integer agentId, @Param("level") Integer level);

    List<AgentChargeVo>   execlAgentChargeHistoryByAgentId(@Param("agentId") Integer agentId, @Param("level") Integer level, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}