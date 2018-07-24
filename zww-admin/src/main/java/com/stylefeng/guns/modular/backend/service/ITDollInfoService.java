package com.stylefeng.guns.modular.backend.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.TDollInfo;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-07-23
 */
public interface ITDollInfoService extends IService<TDollInfo> {

    List<Map<String, Object>> selectDollInfos(Page<TDollInfo> page,String dollCode,String dollName);

    TDollInfo selectDollInfoByDollCode(String dollCode);
}
