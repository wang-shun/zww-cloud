package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.BankInfo;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.agent.service.IBankInfoService;
import com.stylefeng.guns.modular.agent.warpper.BankInfoWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 银行卡管理控制器
 *
 * @author bruce
 * @Date 2018-06-05 20:19:21
 */
@Controller
@RequestMapping("/bankInfo")
public class BankInfoController extends BaseController {

    private String PREFIX = "/agent/bankInfo/";

    @Autowired
    private IBankInfoService bankInfoService;

    /**
     * 跳转到银行卡管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bankInfo.html";
    }

    /**
     * 跳转到添加银行卡管理
     */
    @RequestMapping("/bankInfo_add")
    public String bankInfoAdd() {
        return PREFIX + "bankInfo_add.html";
    }

    /**
     * 跳转到修改银行卡管理
     */
     
    @RequestMapping("/bankInfo_update/{bankInfoId}")
    public String bankInfoUpdate(@PathVariable Integer bankInfoId, Model model) {
        BankInfo bankInfo = bankInfoService.selectById(bankInfoId);
        model.addAttribute("item",bankInfo);
        LogObjectHolder.me().set(bankInfo);
        return PREFIX + "bankInfo_edit.html";
    }

    /**
     * 获取银行卡管理列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String name,String phone,String idcard,String cardno,Integer status) {
        Page<BankInfo> page = new PageFactory<BankInfo>().defaultPage();
        List<Map<String, Object>> result= bankInfoService.selectBankInfo(page,name,phone,idcard,cardno,status);
        page.setRecords((List<BankInfo>)new BankInfoWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增银行卡管理
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(BankInfo bankInfo) {
        bankInfoService.insert(bankInfo);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除银行卡管理
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer bankInfoId) {
        bankInfoService.deleteById(bankInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改银行卡管理
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(BankInfo bankInfo) {
        bankInfoService.updateById(bankInfo);
        return super.SUCCESS_TIP;
    }

    /**
     * 银行卡管理详情
     */
     
    @RequestMapping(value = "/detail/{bankInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("bankInfoId") Integer bankInfoId) {
        return bankInfoService.selectById(bankInfoId);
    }
}
