package com.stylefeng.guns.modular.agent.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.BankInfo;
import com.stylefeng.guns.common.persistence.dao.BankInfoMapper;
import com.stylefeng.guns.modular.agent.service.IBankInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
 * @since 2018-06-05
 */
@Service
public class BankInfoServiceImpl extends ServiceImpl<BankInfoMapper, BankInfo> implements IBankInfoService {
    @Autowired
    private  BankInfoMapper bankInfoMapper;

    @Override
    public List<BankInfo> getBankInfoByAgentId(Integer agentId) {
        return bankInfoMapper.getBankInfoByAgentId(agentId);
    }

    @Override
    public List<Map<String, Object>> selectBankInfo(Page<BankInfo> page, String name, String phone, String idcard, String cardno,Integer status) {
        return bankInfoMapper.selectBankInfo(page,name,phone,idcard,cardno,status);
    }
}
