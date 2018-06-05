package com.stylefeng.guns.modular.agent.warpper;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 提现的包装类
 *
 * @author bruce
 * @date 2017年2月13日 下午10:47:03
 */
public class withdrawWarpper  extends BaseControllerWarpper {

    public withdrawWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        String statusName = "";
        Integer status = (Integer) map.get("status");
        if(status == 0){
            statusName = "已提交";
        }else if(status == 1){
            statusName = "提现成功";
        }else if(status == 2){
            statusName = "提现失败";
        }else{
            statusName = "未知";
        }
        map.put("statusName", statusName);
    }

}
