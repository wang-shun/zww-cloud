package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.BankInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
public interface BankInfoMapper extends BaseMapper<BankInfo> {
    BankInfo getBankInfoByAgentId(Integer agentId);
}