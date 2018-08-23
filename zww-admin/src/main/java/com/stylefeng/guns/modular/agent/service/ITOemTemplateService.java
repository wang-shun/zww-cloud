package com.stylefeng.guns.modular.agent.service;

import com.stylefeng.guns.common.persistence.model.TOemTemplate;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bruce
 * @since 2018-08-22
 */
public interface ITOemTemplateService extends IService<TOemTemplate> {
    List<TOemTemplate> getOemTemplateByOemId(Integer oemId);
	
}
