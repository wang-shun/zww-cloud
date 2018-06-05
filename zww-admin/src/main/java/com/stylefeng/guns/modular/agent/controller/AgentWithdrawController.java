package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.AgentWithdraw;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.agent.service.IAgentWithdrawService;
import com.stylefeng.guns.modular.agent.warpper.withdrawWarpper;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 提现控制器
 *
 * @author bruce
 * @Date 2018-06-04 21:25:24
 */
@Controller
@RequestMapping("/agentWithdraw")
public class AgentWithdrawController extends BaseController {

    @Autowired
    private IAgentWithdrawService agentWithdrawService;


    /**
     * 跳转到提现管理首页
     */
    @RequestMapping("")
    public String agentWithdraw() {
        return  "/agent/withdrow/agentWithdraw.html";
    }
    /**
     * 跳转到未审批管理首页
     */
    @RequestMapping("/noApproval")
    public String noApproval() {
        return  "/notApproval/agentWithdraw.html";
    }

    /**
     * 跳转到已审批管理首页
     */
    @RequestMapping("/approval")
    public String approval() {
        return "/approval/agentWithdraw.html";
    }


    /**
     * 获取未审批列表
     */
     
    @RequestMapping(value = "/list0")
    @ResponseBody
    public Object list0(Integer status,String name,String phone,String createDate) {
        Page<AgentWithdraw> page = new PageFactory<AgentWithdraw>().defaultPage();
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,0,status,name,phone,createDate);
        page.setRecords((List<AgentWithdraw>) new withdrawWarpper(result).warp());
        return  super.packForBT(page);
    }

    /**
     * 获取已审批列表
     */

    @RequestMapping(value = "/list1")
    @ResponseBody
    public Object list1(Integer status,String name,String phone,String createDate) {
        Page<AgentWithdraw> page = new PageFactory<AgentWithdraw>().defaultPage();
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,1,status,name,phone,createDate);
        page.setRecords((List<AgentWithdraw>) new withdrawWarpper(result).warp());
        return  super.packForBT(page);
    }

    /**
     * 获取提现列表
     */

    @RequestMapping(value = "/list2")
    @ResponseBody
    public Object list2(Integer status,String name,String phone,String createDate) {
        Page<AgentWithdraw> page = new PageFactory<AgentWithdraw>().defaultPage();
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,2,status,name,phone,createDate);
        page.setRecords((List<AgentWithdraw>) new withdrawWarpper(result).warp());
        return  super.packForBT(page);
    }

    /**
     * 查看当前流程图
     */
    @PostMapping("/updStatus")
    @ResponseBody
    public Map<String, Object> updStatus(Integer status ,Integer withdrawId) throws Exception{
         Map<String, Object> resultMap = new HashedMap<String, Object>();
         AgentWithdraw agentWithdraw = new AgentWithdraw();
         agentWithdraw.setId(withdrawId);
         agentWithdraw.setStatus(status);
         try {
             int i = agentWithdrawService.updateStatusById(agentWithdraw);
             resultMap.put("code", i);
         }catch (Exception e){
             resultMap.put("msg", e.getMessage());
         }
         return resultMap;
    }

}
