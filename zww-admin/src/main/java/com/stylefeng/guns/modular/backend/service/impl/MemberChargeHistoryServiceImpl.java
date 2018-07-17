package com.stylefeng.guns.modular.backend.service.impl;

import com.stylefeng.guns.common.persistence.dao.AccountMapper;
import com.stylefeng.guns.common.persistence.dao.ChargeOrderMapper;
import com.stylefeng.guns.common.persistence.model.Account;
import com.stylefeng.guns.common.persistence.model.MemberChargeHistory;
import com.stylefeng.guns.common.persistence.dao.MemberChargeHistoryMapper;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.modular.backend.service.IMemberChargeHistoryService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-01-03
 */
@Service
public class MemberChargeHistoryServiceImpl extends ServiceImpl<MemberChargeHistoryMapper, MemberChargeHistory> implements IMemberChargeHistoryService {

    @Autowired
    private MemberChargeHistoryMapper memberChargeHistoryMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public List<Map<String, Object>> selectList(Page<MemberChargeHistory> page,String name,String machineCode,String type,String chargeDate) {
        return memberChargeHistoryMapper.selectList(page,name,machineCode,type,chargeDate);
    }

    @Override
    public Integer insertChargeHistory(Account account,User userdto) {
        MemberChargeHistory chargeRecord = new MemberChargeHistory();
        Account acc = accountMapper.selectById(account.getId());
        chargeRecord.setChargeDate(new Date());
        chargeRecord.setChargeMethod(userdto.getName() + "改币："+account.getAddReason());
        chargeRecord.setCoins(acc.getCoins());
        chargeRecord.setCoinsSum(account.getCoins()-acc.getCoins());
        chargeRecord.setSuperTicket(acc.getSuperTicket());
        chargeRecord.setSuperTicketSum(account.getSuperTicket()-acc.getSuperTicket());
        chargeRecord.setMemberId(account.getId());
        chargeRecord.setType("income");
        return memberChargeHistoryMapper.insertChargeHistory(chargeRecord);
    }
}
