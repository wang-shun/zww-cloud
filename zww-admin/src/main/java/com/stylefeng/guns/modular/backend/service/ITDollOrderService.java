package com.stylefeng.guns.modular.backend.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.ShareInvite;
import com.stylefeng.guns.common.persistence.model.TDollCatchHistory;
import com.stylefeng.guns.common.persistence.model.TDollOrder;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konghuanhuan
 * @since 2018-01-30
 */
public interface ITDollOrderService extends IService<TDollOrder> {

    //查询申请发货订单
    List<Map<String, Object>> selectTDollOrderApply(Page<TDollOrder> page,Integer id,String addrName,String phone,String dollName);

    //待发货
    List<Map<String, Object>> selectTDollOrder(Page<TDollOrder> page,Integer id, String addrName, String phone,String dollName);

    //已发货
    List<Map<String, Object>> selectTDollOrderOut(Page<TDollOrder> page,Integer id, String addrName, String phone,String dollName);

    //按订单编号查询
    TDollOrder selectByorderNum(String orderNum);

    //违规返币
    boolean dollBackCoins(Integer tDollOrderId, Integer memberId);

    //反娃娃
    boolean backDoll(TDollOrder tDollOrder);

    //揽件
    int updateTDollOrderApplyById(@Param("ids") List<Long> ids);

    //发货
    int updateTDollOrderById(List<Long> ids, String deliverMethod, String deliverNumber, BigDecimal deliverAmount, String comment);

    boolean insertTDollOrder(TDollCatchHistory history,Integer modifiedBy);

    List<TDollOrder> selectTDollOrderExecl(Integer id,String addrName, String phone,String dollName);
}
