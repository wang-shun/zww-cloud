package com.stylefeng.guns.modular.agent.service;

import com.stylefeng.guns.common.persistence.model.TAgent;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-05-31
 */
public interface ITAgentService extends IService<TAgent> {

   TAgent selectTAgentById(Integer id);

   void updateAmount(Long amount,int salt,Integer id);
}
