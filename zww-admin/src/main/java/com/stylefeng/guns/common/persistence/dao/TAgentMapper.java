package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-05-31
 */
public interface TAgentMapper extends BaseMapper<TAgent> {

    TAgent selectTAgentByUId(Integer uid);

    TAgent selectTAgentById(Integer id);

    int updateAmount(TAgent tAgent);

    Map selectByIdAndLevel(Integer id);

    TAgent selectByIdS(Integer id);

    String selectByValue(String clod);

    List<Map<String, Object>> selectByLevel(@Param("page") Page<TAgent> page, @Param("nickName") String nickName,@Param("username") String  username, @Param("phone") String phone, @Param("createTime") String createTime, @Param("level") Integer level, @Param("type")Integer type,@Param("agentId") Integer agentId);

    TAgent selectTAgentByUsername(@Param("username") String username);

}