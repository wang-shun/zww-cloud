package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.TOem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-21
 */
public interface TOemMapper extends BaseMapper<TOem> {
   TOem getOemByCode(@Param("code") String code);
}