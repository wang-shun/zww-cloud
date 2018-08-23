package com.stylefeng.guns.modular.agent.service.impl;

import com.stylefeng.guns.common.persistence.model.TOemTemplate;
import com.stylefeng.guns.common.persistence.dao.TOemTemplateMapper;
import com.stylefeng.guns.modular.agent.service.ITOemTemplateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bruce
 * @since 2018-08-22
 */
@Service
public class TOemTemplateServiceImpl extends ServiceImpl<TOemTemplateMapper, TOemTemplate> implements ITOemTemplateService {
   @Autowired
    private  TOemTemplateMapper tOemTemplateMapper;


    @Override
    public List<TOemTemplate> getOemTemplateByOemId(Integer oemId) {
        return tOemTemplateMapper.getOemTemplateByOemId(oemId);
    }
}
