package com.stylefeng.guns.modular.backend.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TDollInfo;
import com.stylefeng.guns.common.persistence.dao.TDollInfoMapper;
import com.stylefeng.guns.modular.backend.service.ITDollInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2018-07-23
 */
@Service
public class TDollInfoServiceImpl extends ServiceImpl<TDollInfoMapper, TDollInfo> implements ITDollInfoService {
@Autowired
private  TDollInfoMapper tDollInfoMapper;

   @Override
   public List<Map<String, Object>> selectDollInfos(Page<TDollInfo> page, String dollCode, String dollName){
      return tDollInfoMapper.selectDollInfos(page,dollCode,dollName);
   }

   @Override
   public TDollInfo selectDollInfoByDollCode(String dollCode){
      return tDollInfoMapper.selectDollInfoByDollCode(dollCode);
   }
}
