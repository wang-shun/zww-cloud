package com.stylefeng.guns.modular.agent.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-05-31
 */
public interface ITAgentService extends IService<TAgent> {

   TAgent selectTAgentByUId(Integer uid);

   void updateAmount(Long amount,int salt,Integer id);

   TAgent selectTAgentById(Integer id);

   Map getById(Integer id);

   String selectByValue(String clod);

   List<Map<String, Object>> selectByLevel(@Param("page") Page<TAgent> page, String condition, String phone, String createTime, Integer level, Integer id);

}
