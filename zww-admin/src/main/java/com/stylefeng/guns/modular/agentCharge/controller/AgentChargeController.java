package com.stylefeng.guns.modular.agentCharge.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.mutidatasource.DSEnum;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.AgentCharge;
import com.stylefeng.guns.modular.agentCharge.service.IAgentChargeService;

/**
 * 代理商分润控制器
 *
 * @author fengshuonan
 * @Date 2018-06-05 17:52:14
 */
@Controller
@RequestMapping("/agentCharge")
public class AgentChargeController extends BaseController {

    private String PREFIX = "/agentCharge/agentCharge/";

    @Autowired
    private IAgentChargeService agentChargeService;

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
    public String agentChargeUpdate(@PathVariable Integer agentChargeId, Model model) {
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
    public Object list(String condition) {
        return agentChargeService.selectList(null);
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
    public Object delete(@RequestParam Integer agentChargeId) {
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
    public Object detail(@PathVariable("agentChargeId") Integer agentChargeId) {
        return agentChargeService.selectById(agentChargeId);
    }
}
