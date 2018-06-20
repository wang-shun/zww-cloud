package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.BankInfo;
import com.stylefeng.guns.common.persistence.model.Oem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author bruce
 * @since 2018-06-05
 */
public interface ToemMapper extends BaseMapper<Oem> {

    int insertSelective(Oem record);

    Oem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Oem record);

    Oem selectByCode(@Param("code") String code);

    List<Oem> selectAllOem();
}