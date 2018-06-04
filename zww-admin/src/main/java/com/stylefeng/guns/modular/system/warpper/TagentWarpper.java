package com.stylefeng.guns.modular.system.warpper;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 代理商的包装类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:47:03
 */
public class TagentWarpper extends BaseControllerWarpper {

    public TagentWarpper(List<Map<String, Object>> list) {


        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        map.put("statusName", ConstantFactory.me().getStatusName((Integer) map.get("status")));
        map.put("levelName", ConstantFactory.me().getleveName((Integer) map.get("level")));

       Integer agentId= (Integer) map.get("agentId");
       String agentName = "-",agentOneName = "-",agentTwoName = "-";
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
    }

}
