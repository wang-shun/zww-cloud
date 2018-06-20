package com.stylefeng.guns.modular.agent.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.BankInfo;
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
 * @since 2018-06-05
 */
public interface IBankInfoService extends IService<BankInfo> {
	List<BankInfo> getBankInfoByAgentId(Integer agentId);

	List<Map<String, Object>> selectBankInfo(@Param("page") Page<BankInfo> page,@Param("name") String name,@Param("phone") String phone,@Param("idcard") String idcard,@Param("cardno") String cardno,@Param("status") Integer status);
}
