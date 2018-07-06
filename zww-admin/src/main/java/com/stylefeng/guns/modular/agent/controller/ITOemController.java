package com.stylefeng.guns.modular.agent.controller;

import com.stylefeng.guns.common.persistence.model.TOem;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.agent.service.ITOemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 银行卡管理控制器
 *
 * @author fengshuonan
 * @Date 2018-06-05 20:19:21
 */
@Controller
@RequestMapping("/oem")
public class ITOemController extends BaseController {

    private String PREFIX = "/agent/oem/";

    @Autowired
    private ITOemService oemService;

    /**
     * 跳转到银行卡管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "oem.html";
    }

    /**
     * 跳转到修改银行卡管理
     */
     
    @RequestMapping("/oem_upd/{tOemId}")
    public String bankInfoUpdate(@PathVariable Integer tOemId, Model model) {
        TOem tOem = oemService.selectById(tOemId);
        model.addAttribute("item",tOem);
        LogObjectHolder.me().set(tOem);
        return PREFIX + "oem_upd.html";
    }

    /**
     * 获取银行卡管理列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        return oemService.selectList(null);
    }


    @RequestMapping("/update")
    public Object update(TOem tOem) {
       oemService.updateById(tOem);
        return super.SUCCESS_TIP;
    }

}
