package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.TMember;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author liangchangchun
 * @since 2018-01-02
 */
public interface TMemberMapper extends BaseMapper<TMember> {

   TMember selectByMobile(@Param("mobile") String mobile);


   List<TMember> selectByRegisterChannel(@Param("registerChannel") String registerChannel);

}