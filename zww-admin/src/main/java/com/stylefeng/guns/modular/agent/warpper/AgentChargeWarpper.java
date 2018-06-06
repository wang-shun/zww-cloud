package com.stylefeng.guns.modular.agent.warpper;

import com.stylefeng.guns.common.constant.factory.ZwwContentFactory;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.core.base.warpper.BaseControllerWarpper;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 提现的包装类
 *
 * @author bruce
 * @date 2017年2月13日 下午10:47:03
 */
public class AgentChargeWarpper extends BaseControllerWarpper {

    public AgentChargeWarpper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
        String statusName = "";
        Integer status = (Integer) map.get("status");
        if(status == 0){
            statusName = "未清算";
        }else if(status == 1){
            statusName = "已清算";
        }else{
            statusName = "未知";
        }
        String agentName = "-";
        Integer agentThreeId = (Integer) map.get("agentThreeId");
        if (agentThreeId !=0){
            TAgent tAgent = ZwwContentFactory.me().selectTAgentById(agentThreeId);
            agentName = tAgent != null ? tAgent.getNickName() : "-";
        }else{
            Integer agentTwoId = (Integer) map.get("agentTwoId");
            if(agentTwoId != 0){
                TAgent tAgent = ZwwContentFactory.me().selectTAgentById(agentTwoId);
                agentName = tAgent != null ? tAgent.getNickName() : "-";
            }else{
                Integer agentOneId = (Integer) map.get("agentOneId");
                if(agentOneId != 0){
                    TAgent tAgent = ZwwContentFactory.me().selectTAgentById(agentOneId);
                    agentName = tAgent != null ? tAgent.getNickName() : "-";
                }else{
                    Integer agentSuperId = (Integer) map.get("agentSuperId");
                    if(agentSuperId != 0){
                        TAgent tAgent = ZwwContentFactory.me().selectTAgentById(agentSuperId);
                        agentName = tAgent != null ? tAgent.getNickName() : "-";
                    }
                }
            }
        }

        map.put("statusName", statusName);
        map.put("agentName", agentName);
    }

}
