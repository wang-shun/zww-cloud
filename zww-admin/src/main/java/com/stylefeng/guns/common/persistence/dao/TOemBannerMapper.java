package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.TOemBanner;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-21
 */
public interface TOemBannerMapper extends BaseMapper<TOemBanner> {

   List<TOemBanner> selectByOemId(Integer oemId);

   int deleteByOemId(Integer oemId);

   int insertBatch(@Param("oemBanner") List<TOemBanner> oemBanner);
}