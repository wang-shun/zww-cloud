package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.TAgent;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-05-31
 */
public interface TAgentMapper extends BaseMapper<TAgent> {

    TAgent selectLevelById(Integer id);

}