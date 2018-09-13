package com.stylefeng.guns.modular.agent.warpper;

import com.stylefeng.guns.common.constant.factory.ZwwContentFactory;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 提现的包装类
 *
 * @author bruce
 * @date 2017年2月13日 下午10:47:03
 */
public class TOemWarpper extends BaseControllerWarpper {

    public TOemWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        String statusName = "";
        Integer status = (Integer) map.get("chargeState");
        if(status == 0){
            statusName = "未支付";
        }else if(status == 1){
            statusName = "支付成功";
        }else if(status == 2){
            statusName = "支付失败";
        }else{
            statusName = "未知订单";
        }
        map.put("statusName", statusName);
    }

}
