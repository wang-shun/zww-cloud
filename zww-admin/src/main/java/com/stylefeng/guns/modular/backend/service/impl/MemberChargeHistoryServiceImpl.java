package com.stylefeng.guns.modular.backend.service.impl;

import com.stylefeng.guns.common.persistence.dao.AccountMapper;
import com.stylefeng.guns.common.persistence.dao.ChargeOrderMapper;
import com.stylefeng.guns.common.persistence.dao.TSystemCoinMapper;
import com.stylefeng.guns.common.persistence.model.Account;
import com.stylefeng.guns.common.persistence.model.MemberChargeHistory;
import com.stylefeng.guns.common.persistence.dao.MemberChargeHistoryMapper;
import com.stylefeng.guns.common.persistence.model.TSystemCoin;
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

    @Autowired
    private TSystemCoinMapper tSystemCoinMapper;

    @Override
    public List<Map<String, Object>> selectList(Page<MemberChargeHistory> page,Integer memberId,String name,String machineCode,String chargeDate) {
        return memberChargeHistoryMapper.selectList(page,memberId,name,machineCode,chargeDate);
    }

    @Override
    public Integer insertChargeHistory(Account account,User userdto) {
        MemberChargeHistory chargeRecord = new MemberChargeHistory();
        Account acc = accountMapper.selectById(account.getId());
        Date date = new Date();
        chargeRecord.setChargeDate(date);
        chargeRecord.setChargeMethod(userdto.getName() + "改币："+account.getAddReason());
        chargeRecord.setCoins(acc.getCoins());
        chargeRecord.setCoinsSum(account.getCoins()-acc.getCoins());
        chargeRecord.setMemberId(account.getId());
        chargeRecord.setType("income");

        TSystemCoin tSystemCoin = new TSystemCoin();
        tSystemCoin.setCoin(account.getCoins()-acc.getCoins());
        tSystemCoin.setCreateTime(date);
        tSystemCoin.setMemberId(account.getId());
        tSystemCoin.setModifyId(userdto.getId());
        tSystemCoin.setRemark(account.getAddReason());
        tSystemCoinMapper.insert(tSystemCoin);

        return memberChargeHistoryMapper.insertChargeHistory(chargeRecord);
    }
}
