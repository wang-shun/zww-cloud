package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.agent.service.ITOemService;
import com.stylefeng.guns.modular.agent.warpper.TOemWarpper;
import com.stylefeng.guns.modular.backend.service.IChargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * oem管理控制器
 *
 * @author bruce
 * @Date 2018-06-05 20:19:21
 */
@Controller
@RequestMapping("/oem")
public class ITOemController extends BaseController {

    private String PREFIX = "/agent/oem/";

    @Autowired
    private ITOemService oemService;

    @Autowired
    private IChargeOrderService chargeOrderService;

    @Autowired
    RoleMapper roleMapper;

    /**
     * 跳转到oem管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "oem.html";
    }

    /**
     * 跳转到修改oem管理
     */
     
    @RequestMapping("/oem_upd/{tOemId}")
    public String bankInfoUpdate(@PathVariable Integer tOemId, Model model) {
        TOem tOem = oemService.selectById(tOemId);
        model.addAttribute("item",tOem);
        LogObjectHolder.me().set(tOem);
        return PREFIX + "oem_upd.html";
    }

    @RequestMapping("/personal")
    public String personal( Model model) {
        User user = (User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(user.getRoleid()));
        TOem tOem = oemService.getOemByCode(role.getTips());
        model.addAttribute("item",tOem);
        return PREFIX + "personal.html";
    }

    @RequestMapping("/order")
    public String order( Model model) {

        return PREFIX + "order.html";
    }

    /**
     * 获取oem管理列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list() {
        return oemService.selectList(null);
    }


    @RequestMapping("/update")
    @ResponseBody
    public Object update(TOem tOem) {
       oemService.updateById(tOem);
        return super.SUCCESS_TIP;
    }



    /**
     * 获取订单管理列表
     */

    @RequestMapping(value = "/orderlist")
    @ResponseBody
    public Object orderlist(String orderNo, String memberName,String uid,Integer status,String startDate,String endDate) {
        Page<ChargeOrder> page = new PageFactory<ChargeOrder>().defaultPage();
        User user = (User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(user.getRoleid()));
        List<Map<String, Object>> result = chargeOrderService.selectListByOem(page,memberName,uid,orderNo,status,startDate,endDate,role.getTips());
        page.setRecords((List<ChargeOrder>) new TOemWarpper(result).warp());
        return  super.packForBT(page);
    }

}
