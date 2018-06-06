package com.stylefeng.guns.modular.agent.service;

import com.stylefeng.guns.common.persistence.model.BankInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
public interface IBankInfoService extends IService<BankInfo> {
	BankInfo getBankInfoByAgentId(Integer agentId);
}
