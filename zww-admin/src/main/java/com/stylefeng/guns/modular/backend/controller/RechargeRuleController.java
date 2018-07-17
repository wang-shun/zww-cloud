package com.stylefeng.guns.modular.backend.controller;

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
import com.stylefeng.guns.common.persistence.model.RechargeRule;
import com.stylefeng.guns.modular.backend.service.IRechargeRuleService;

import java.util.Date;

/**
 * 充值规则列表控制器
 *
 * @author fengshuonan
 * @Date 2018-07-17 16:09:43
 */
@Controller
@RequestMapping("/rechargeRule")
public class RechargeRuleController extends BaseController {

    private String PREFIX = "/backend/rechargeRule/";

    @Autowired
    private IRechargeRuleService rechargeRuleService;

    /**
     * 跳转到充值规则列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "rechargeRule.html";
    }

    /**
     * 跳转到添加充值规则列表
     */
    @RequestMapping("/rechargeRule_add")
    public String rechargeRuleAdd() {
        return PREFIX + "rechargeRule_add.html";
    }

    /**
     * 跳转到修改充值规则列表
     */
     
    @RequestMapping("/rechargeRule_update/{rechargeRuleId}")
    public String rechargeRuleUpdate(@PathVariable Integer rechargeRuleId, Model model) {
        RechargeRule rechargeRule = rechargeRuleService.selectById(rechargeRuleId);
        model.addAttribute("item",rechargeRule);
        LogObjectHolder.me().set(rechargeRule);
        return PREFIX + "rechargeRule_edit.html";
    }

    /**
     * 获取充值规则列表列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        return rechargeRuleService.selectList(null);
    }

    /**
     * 新增充值规则列表
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(RechargeRule rechargeRule) {
        rechargeRuleService.insert(rechargeRule);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除充值规则列表
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer rechargeRuleId) {
        rechargeRuleService.deleteById(rechargeRuleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改充值规则列表
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(RechargeRule rechargeRule) {
        rechargeRule.setUpdateDate(new Date());
        rechargeRuleService.updateById(rechargeRule);
        return super.SUCCESS_TIP;
    }

    /**
     * 充值规则列表详情
     */
     
    @RequestMapping(value = "/detail/{rechargeRuleId}")
    @ResponseBody
    public Object detail(@PathVariable("rechargeRuleId") Integer rechargeRuleId) {
        return rechargeRuleService.selectById(rechargeRuleId);
    }
}
