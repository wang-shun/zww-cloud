package com.stylefeng.guns.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.stylefeng.guns.common.persistence.model.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 菜单表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface MenuMapper extends BaseMapper<Menu> {

    Menu selectMenuByCode(@Param("code") String code);

    List<Menu> selectMenuByPcode(@Param("pcode") String pcode);

    int updMenuByPcode(@Param("pcode") String pcode,@Param("status") Integer status);
}