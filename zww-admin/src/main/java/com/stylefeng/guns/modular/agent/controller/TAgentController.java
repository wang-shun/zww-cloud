package com.stylefeng.guns.modular.agent.controller;

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
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.modular.agent.service.ITAgentService;

/**
 * 代理商管理控制器
 *
 * @author fengshuonan
 * @Date 2018-05-31 16:40:48
 */
@Controller
@RequestMapping("/tAgent")
public class TAgentController extends BaseController {

    private String PREFIX = "/agent/tAgent/";

    @Autowired
    private ITAgentService tAgentService;

    /**
     * 跳转到代理商管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tAgent.html";
    }

    /**
     * 跳转到添加代理商管理
     */
    @RequestMapping("/tAgent_add")
    public String tAgentAdd() {
        return PREFIX + "tAgent_add.html";
    }

    /**
     * 跳转到修改代理商管理
     */
     
    @RequestMapping("/tAgent_update/{tAgentId}")
    public String tAgentUpdate(@PathVariable Integer tAgentId, Model model) {
        TAgent tAgent = tAgentService.selectById(tAgentId);
        model.addAttribute("item",tAgent);
        LogObjectHolder.me().set(tAgent);
        return PREFIX + "tAgent_edit.html";
    }

    /**
     * 获取代理商管理列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return tAgentService.selectList(null);
    }

    /**
     * 新增代理商管理
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TAgent tAgent) {
        tAgentService.insert(tAgent);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除代理商管理
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tAgentId) {
        tAgentService.deleteById(tAgentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改代理商管理
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TAgent tAgent) {
        tAgentService.updateById(tAgent);
        return super.SUCCESS_TIP;
    }

    /**
     * 代理商管理详情
     */
     
    @RequestMapping(value = "/detail/{tAgentId}")
    @ResponseBody
    public Object detail(@PathVariable("tAgentId") Integer tAgentId) {
        return tAgentService.selectById(tAgentId);
    }
}
