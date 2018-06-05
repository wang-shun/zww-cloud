package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.AgentWithdraw;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.common.persistence.model.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-04
 */
public interface AgentWithdrawMapper extends BaseMapper<AgentWithdraw> {

    /**
     * 根据条件查询用户列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectAgentWithdrow(@Param("page") Page<AgentWithdraw> page,@Param("agentId") Integer agentId,@Param("type") Integer type, @Param("status") Integer status, @Param("name") String name, @Param("phone") String phone, @Param("createDate") String createDate);

   int updateStatusById(AgentWithdraw agentWithdraw);

    AgentWithdraw getSumAmountByAgentId(Integer agentId);

    AgentWithdraw getAgentWithdrawById(Integer id);
}