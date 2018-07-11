package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.agent.service.IAgentChargeService;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.stylefeng.guns.modular.agent.warpper.AgentChargeWarpper;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 代理商分润控制器
 *
 * @author fengshuonan
 * @Date 2018-06-05 17:52:14
 */
@Controller
@RequestMapping("/agentCharge")
public class AgentChargeController extends BaseController {

    private String PREFIX = "/agent/agentCharge/";

    @Autowired
    private IAgentChargeService agentChargeService;
    @Autowired
    private ITAgentService agentService;

    /**
     * 跳转到代理商分润首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "agentCharge.html";
    }

    /**
     * 跳转到添加代理商分润
     */
    @RequestMapping("/agentCharge_add")
    public String agentChargeAdd() {
        return PREFIX + "agentCharge_add.html";
    }

    /**
     * 跳转到修改代理商分润
     */
    @RequestMapping("/agentCharge_update/{agentChargeId}")
    public String agentChargeUpdate(@PathVariable Long agentChargeId, Model model) {
        AgentCharge agentCharge = agentChargeService.selectById(agentChargeId);
        model.addAttribute("item",agentCharge);
        LogObjectHolder.me().set(agentCharge);
        return PREFIX + "agentCharge_edit.html";
    }

    /**
     * 获取代理商分润列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        Page<AgentCharge> page = new PageFactory<AgentCharge>().defaultPage();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentByUId(userdto.getId());
        List<Map<String, Object>>  result= agentChargeService.selectAgentCharge(page,tAgent.getId(),tAgent.getLevel());
        page.setRecords((List<AgentCharge>) new AgentChargeWarpper(result).warp());
        return  super.packForBT(page);
    }

    /**
     * 新增代理商分润
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AgentCharge agentCharge) {
        agentChargeService.insert(agentCharge);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除代理商分润
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Long agentChargeId) {
        agentChargeService.deleteById(agentChargeId);
        return SUCCESS_TIP;
    }

    /**
     * 修改代理商分润
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AgentCharge agentCharge) {
        agentChargeService.updateById(agentCharge);
        return super.SUCCESS_TIP;
    }

    /**
     * 代理商分润详情
     */
     
    @RequestMapping(value = "/detail/{agentChargeId}")
    @ResponseBody
    public Object detail(@PathVariable("agentChargeId") Long agentChargeId) {
        return agentChargeService.selectById(agentChargeId);
    }

    /**
     * 总数据
     */
    @PostMapping("/totle")
    @Permission({Const.AGENT_SUPER,Const.AGENT_ONE,Const.AGENT_TWO,Const.AGENT_three})
    @ResponseBody
    public Map<String, Object> totle() throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentByUId(userdto.getId());
        AgentCharge agentCharge = new AgentCharge();
        if(tAgent.getLevel() == 0){
            agentCharge.setAgentSuperId(tAgent.getId());
        }else if(tAgent.getLevel() == 1){
            agentCharge.setAgentOneId(tAgent.getId());
        }else if(tAgent.getLevel() == 2){
            agentCharge.setAgentTwoId(tAgent.getId());
        }else{
            agentCharge.setAgentThreeId(tAgent.getId());
        }
        AgentCharge  chargeProfit = agentChargeService.getSumAmountByAgentId(agentCharge);
        AgentCharge  chargeYesterday = agentChargeService.getSumAmountByYesterday(agentCharge);

        resultMap.put("yesterdayProfit", chargeYesterday.getAgentSuperIncome());//昨日分润
        resultMap.put("NotProfit", chargeProfit.getAgentSuperIncome());//未结算分润
        resultMap.put("profit", chargeProfit.getAgentOneIncome());//已结算分润
        return resultMap;
    }

}
