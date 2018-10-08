package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.stylefeng.guns.common.persistence.model.AgentWithdraw;

import com.stylefeng.guns.common.util.DoubleUtils;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 代理商的包装类
 *
 * @author bruce
 * @date 2017年2月13日 下午10:47:03
 */
public class TagentWarpper extends BaseControllerWarpper {
    public TagentWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
        Integer level= (Integer) map.get("level");
        map.put("levelName", ConstantFactory.me().getleveName(level));

       Integer agentId= (Integer) map.get("agentId");
       Integer id= (Integer) map.get("id");
       Boolean isOem= (Boolean) map.get("oem");

       String agentName = "-",agentOneName = "-",agentTwoName = "-",oemName = "-";
        if(isOem){
            if(agentId != 0){
                 oemName = ConstantFactory.me().getOemNameById(agentId);
            }else{
                oemName = ConstantFactory.me().getOemNameById(id);
            }
        }
        map.put("oemName", oemName);
       if(agentId != 0){
           agentName = ConstantFactory.me().getAgentById(agentId);
       }
        map.put("agentName", agentName);
        Integer agentOneId= (Integer) map.get("agentOneId");
        if(agentOneId != 0){
            agentOneName = ConstantFactory.me().getAgentById(agentOneId);
        }
        map.put("agentOneName", agentOneName);
        Integer agentTwoId= (Integer) map.get("agentTwoId");
        if(agentTwoId != 0){
            agentTwoName = ConstantFactory.me().getAgentById(agentTwoId);
        }
        map.put("agentTwoName", agentTwoName);

        if((long) map.get("type") == 10l){
            map.put("inviteNum", ConstantFactory.me().getInviteNumByAgentIdAndLevel(id,level));//直推人数
            if(level != 3){
                map.put("inviteNum1", ConstantFactory.me().getInviteNum1ByAgentIdAndLevel(id,level));//间推人数
            }else{
                map.put("inviteNum1", 0);//间推人数
            }
            AgentWithdraw agentWithdraw = ConstantFactory.me().sumAmountFromAgentWithdrawByAgentId(id);
            if(agentWithdraw == null){
                map.put("withdraw", 0.00);//提现额
            }else{
                map.put("withdraw", DoubleUtils.getTwoDecimal(agentWithdraw.getAmount()*0.01));//提现额
            }
            AgentCharge agentCharge = new AgentCharge();
           if(level == 0){
               agentCharge.setAgentSuperId(id);
           }else if(level == 1){
               agentCharge.setAgentOneId(id);
           }else if(level == 2){
               agentCharge.setAgentTwoId(id);
           }else{
               agentCharge.setAgentThreeId(id);
           }
            agentCharge = ConstantFactory.me().sumAmountFromAgentChargeByAgentIdAndLevel(agentCharge);
            map.put("profit", DoubleUtils.getTwoDecimal(agentCharge.getAgentOneIncome()*0.01));//分润额

        }

    }

}
