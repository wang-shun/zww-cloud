package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.TOemTemplate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-08-22
 */
public interface TOemTemplateMapper extends BaseMapper<TOemTemplate> {

    List<TOemTemplate> getOemTemplateByOemId(@Param("oemId") Integer oemId);
}