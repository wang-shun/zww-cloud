package com.stylefeng.guns.modular.backend.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.dao.*;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.core.util.TimeUtil;
import com.stylefeng.guns.modular.backend.service.ITDollOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konghuanhuan
 * @since 2018-01-30
 */
@Service
@Transactional
public class TDollOrderServiceImpl extends ServiceImpl<TDollOrderMapper, TDollOrder> implements ITDollOrderService {

    @Autowired
    private TDollOrderMapper tDollOrderMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TDollOrderItemMapper tDollOrderItemMapper;

    @Autowired
    private MemberChargeHistoryMapper memberChargeHistoryMapper;

    @Autowired
    private TMemberAddrMapper tMemberAddrMapper;

    @Autowired
    private TSystemPrefMapper tSystemPrefMapper;

    @Autowired
    private TDollMapper dollMapper;

    @Override
    public List<Map<String, Object>> selectTDollOrderApply(Page<TDollOrder> page,Integer id,String addrName,String phone){
        return tDollOrderMapper.selectTDollOrderApply(page,id,addrName,phone);
    }
    //待发货订单
    @Override
    public List<Map<String, Object>> selectTDollOrder(Page<TDollOrder> page, Integer id,String addrName, String phone) {
        return tDollOrderMapper.selectTDollOrder(page,id,addrName,phone);
    }

    //已发货订单
    @Override
    public List<Map<String, Object>> selectTDollOrderOut(Page<TDollOrder> page, Integer id,String addrName, String phone) {
        return tDollOrderMapper.selectTDollOrderOut(page,id,addrName,phone);
    }

    //按订单编号查询
    @Override
    public TDollOrder selectByorderNum(String orderNum) {
        return tDollOrderMapper.selectByOrderNum(orderNum);
    }

    //寄存娃娃违规返币
    @Override
    public boolean dollBackCoins(Integer tDollOrderId,String memberId) {
        //根据memberId查询
        Member member = memberMapper.selectIdByMemberId(memberId);
        //查询金币信息
        Account account = accountMapper.selectById(member.getId());
        //查询订单信息
        TDollOrder tDollOrder = tDollOrderMapper.selectById(tDollOrderId);
        //查询订单关联
//        TDollOrderItem tDollOrderItem = tDollOrderItemMapper.selectByOrderId(tDollOrder.getId());
        //查询娃娃名称
//        TDoll tDoll = tDollMapper.selectById(tDollOrderItem.getDollId());
        //生成消费记录 返娃娃币
        MemberChargeHistory chargeRecord = new MemberChargeHistory();
        chargeRecord.setChargeDate(new Date());
        chargeRecord.setChargeMethod("违规返币");
        chargeRecord.setCoins(account.getCoins()==null?0:account.getCoins());
        chargeRecord.setCoinsSum(tDollOrder.getDollRedeemCoins()==null?0:tDollOrder.getDollRedeemCoins());
//        chargeRecord.setDollId(tDollOrderItem.getDollId());
        chargeRecord.setMemberId(account.getId());
        chargeRecord.setType("income");
        memberChargeHistoryMapper.updateMemberCount(chargeRecord);
        memberChargeHistoryMapper.insertChargeHistory(chargeRecord);

        tDollOrder.setStatus("已兑换");
        tDollOrder.setModifiedDate(new Date());
        tDollOrder.setModifiedBy(ShiroKit.getUser().getId());
        return retBool(tDollOrderMapper.updateById(tDollOrder));
    }

    @Override
    public int updateTDollOrderApplyById(@Param("ids") List<Long> ids){
        return tDollOrderMapper.updateTDollOrderApplyById(ids);
    }

    @Override
    public int updateTDollOrderById(List<Long> ids, String deliverMethod, String deliverNumber, BigDecimal deliverAmount, String comment) {
        return tDollOrderMapper.updateTDollOrderById(ids,deliverMethod,deliverNumber,deliverAmount,comment);
    }


    //添加娃娃
    @Override
    public boolean backDoll(TDollOrder tDollOrder) {
        //根据memberId查询
        Member member = memberMapper.selectIdByMemberId(tDollOrder.getMemberIDs());
        TMemberAddr memberAddr = tMemberAddrMapper.selectDefaultAddr(member.getId());
        TSystemPref systemPref = tSystemPrefMapper.selectByPrimaryKey("DOLL_STOCK_DAYS");
        TSystemPref deliverCoins = tSystemPrefMapper.selectByPrimaryKey("DELIVERY_COINS");
        Integer deliverCoin = Integer.valueOf(deliverCoins.getValue());
        Integer validDate = Integer.valueOf(systemPref.getValue());
        //通过娃娃编号查询tdoll
        TDoll doll = dollMapper.getDollByMachineCode(tDollOrder.getDollCodes());
        TDollOrderItem dollOrderItem = new TDollOrderItem();
        String orderNum = StringUtils.getOrderNumber();
        while (tDollOrderMapper.selectByOrderNum(orderNum) != null) {
            orderNum = StringUtils.getOrderNumber();
        }
        if (memberAddr != null) {
            tDollOrder.setAddressId(memberAddr.getId());
        }
        if (deliverCoin != null) {
            tDollOrder.setDeliverCoins(deliverCoin);
        }
        tDollOrder.setOrderNumber(orderNum);
        tDollOrder.setOrderDate(new Date());
        tDollOrder.setOrderBy(member.getId());
        tDollOrder.setModifiedDate(new Date());
        tDollOrder.setModifiedBy(ShiroKit.getUser().getId());
        tDollOrder.setStatus("寄存中");
        if(doll != null){
            tDollOrder.setDollRedeemCoins(doll.getPrice());
        }else {
            tDollOrder.setDollRedeemCoins(19);
        }
        tDollOrder.setStockValidDate(TimeUtil.plusDay(validDate));
        tDollOrderMapper.insert(tDollOrder);

        dollOrderItem.setOrderId(tDollOrder.getId());
        dollOrderItem.setDollId(doll.getId());
        dollOrderItem.setQuantity(1);
        dollOrderItem.setCreatedDate(new Date());
        dollOrderItem.setDollCode(tDollOrder.getDollCodes());
        dollOrderItem.setDollName(doll.getName());
        dollOrderItem.setDollUrl(doll.getTbimgRealPath());
        return retBool(tDollOrderItemMapper.insert(dollOrderItem));
    }
   @Override
   public boolean insertTDollOrder(TDollCatchHistory history,Integer modifiedBy){
        Date orderDate = history.getCatchDate();
        String orderNumber = StringUtils.getOrderNumber();
        TDollOrder order = new TDollOrder(orderNumber,orderDate,history.getMemberId().intValue(),"寄存中",
                DateUtils.addDays(orderDate,15), 50,  modifiedBy, 19);
       int i = tDollOrderMapper.insert(order);
       if(i < 0){
           return false;
       }
       order = tDollOrderMapper.selectByOrderNum(orderNumber);
       TDollOrderItem item = new TDollOrderItem(order.getId(),history.getDollId(),1,orderDate,
               history.getDollCode(),history.getDollName(),history.getDollUrl());
       return retBool(tDollOrderItemMapper.insert(item));
    }

    @Override
    public   List<TDollOrder> selectTDollOrderExecl(String addrName,String phone){
        return tDollOrderMapper.selectTDollOrderExecl(addrName,phone);
    }
}
