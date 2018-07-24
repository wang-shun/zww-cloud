package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TDollInfo;
import com.stylefeng.guns.common.persistence.model.TDollInfoHistory;
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
 * @since 2018-07-23
 */
public interface TDollInfoHistoryMapper extends BaseMapper<TDollInfoHistory> {

    List<Map<String, Object>> selectDollInfoHistorys(@Param("page") Page<TDollInfoHistory> page, @Param("dollCode") String dollCode, @Param("dollName") String dollName);
}