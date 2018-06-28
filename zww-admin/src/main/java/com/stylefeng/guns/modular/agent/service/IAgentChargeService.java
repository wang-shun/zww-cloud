package com.stylefeng.guns.modular.agent.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.stylefeng.guns.common.persistence.model.vo.AgentChargeVo;
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
 * @since 2018-06-05
 */
public interface IAgentChargeService extends IService<AgentCharge> {
    //代理商分润总数据
    AgentCharge getSumAmountByAgentId(AgentCharge agentCharge);
    //昨日代理商分润总数据
    AgentCharge getSumAmountByYesterday(AgentCharge agentCharge);

    //代理商分润列表
    List<Map<String, Object>> selectAgentCharge(@Param("page") Page<AgentCharge> page, @Param("agentId") Integer agentId,@Param("level") Integer level);

    List<AgentChargeVo>  getAgentChargeExecl(@Param("agentId") Integer agentId, @Param("level") Integer level);

    List<AgentChargeVo>   execlAgentChargeHistoryByAgentId(@Param("agentId") Integer agentId, @Param("level") Integer level, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

}
