package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TDollInfo;
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
public interface TDollInfoMapper extends BaseMapper<TDollInfo> {

    List<Map<String, Object>> selectDollInfos(@Param("page") Page<TDollInfo> page, @Param("dollCode") String dollCode, @Param("dollName") String dollName);

    TDollInfo selectDollInfoByDollCode(@Param("dollCode") String dollCode);
}