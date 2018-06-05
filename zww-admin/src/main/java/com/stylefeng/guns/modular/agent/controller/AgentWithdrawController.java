package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.agent.service.IAgentWithdrawService;
import com.stylefeng.guns.modular.agent.service.IBankInfoService;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.stylefeng.guns.modular.agent.warpper.withdrawWarpper;
import com.stylefeng.guns.modular.agentCharge.service.IAgentChargeService;
import com.stylefeng.guns.modular.backend.service.ITSystemPrefService;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @Autowired
    private IAgentChargeService agentChargeService;
    @Autowired
    private ITAgentService agentService;
    @Autowired
    private IBankInfoService bankInfoService;
    @Autowired
    private ITSystemPrefService systemPrefService;

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
     * 跳转到提现首页
     */
    @RequestMapping("/withdrawPage")
    public String withdrawPage( Model model) {
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentById(userdto.getId());
        model.addAttribute("balance",(tAgent.getBalance() - tAgent.getBalanceDisabled())*0.01);
        TSystemPref MIN_WITHDRAW = systemPrefService.selectByCode("MIN_WITHDRAW");//最小提现金额(单位：分)
        TSystemPref SERVICE_CHARGE = systemPrefService.selectByCode("SERVICE_CHARGE");//手续费(单位：分)
        model.addAttribute("MIN_WITHDRAW",MIN_WITHDRAW == null ? 100 : Long.valueOf(MIN_WITHDRAW.getValue())/100);
        model.addAttribute("SERVICE_CHARGE",SERVICE_CHARGE == null ? 2 : Long.valueOf(SERVICE_CHARGE.getValue())/100);
        return "/agent/withdrow/agentWithdraw_add.html";
    }

    /**
     * 跳转到修改代理商管理
     */

    @RequestMapping("/agentWithdraw_upd/{withdrawId}")
    public String tAgentUpdate(@PathVariable Integer withdrawId, Model model) {
        model.addAttribute("withdrawId",withdrawId);
        return  "/notApproval/agentWithdraw_upd.html";
    }
    /**
     * 获取未审批列表
     */
     
    @RequestMapping(value = "/list0")
    @ResponseBody
    public Object list0(Integer status,String name,String phone,String createDate) {
        Page<AgentWithdraw> page = new PageFactory<AgentWithdraw>().defaultPage();
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,0,0,status,name,phone,createDate);
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
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,0,1,status,name,phone,createDate);
        page.setRecords((List<AgentWithdraw>) new withdrawWarpper(result).warp());
        return  super.packForBT(page);
    }

    /**
     * 获取提现列表
     */

    @RequestMapping(value = "/list2")
    @ResponseBody
    public Object list2(Integer status,String createDate) {
        Page<AgentWithdraw> page = new PageFactory<AgentWithdraw>().defaultPage();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentById(userdto.getId());
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,tAgent.getId(),2,status,null,null,createDate);
        page.setRecords((List<AgentWithdraw>) new withdrawWarpper(result).warp());
        return  super.packForBT(page);
    }

    /**
     * 总数据
     */
    @PostMapping("/totle")
    @ResponseBody
    public Map<String, Object> totle() throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentById(userdto.getId());
        long notPutForward = tAgent.getBalance() - tAgent.getBalanceDisabled();//可提现金额=总余额-冻结金额
        AgentWithdraw agentWithdraw = agentWithdrawService.getSumAmountByAgentId(tAgent.getId());
        AgentCharge agentCharge = new AgentCharge();
        long undischarged = 0l;
        if(tAgent.getLevel() == 0){
            agentCharge.setAgentSuperId(tAgent.getId());
            agentCharge = agentChargeService.getSumNotAmountByAgentId(agentCharge);
            undischarged = agentCharge != null ? agentCharge.getAgentSuperIncome() : 0l;
        }else if(tAgent.getLevel() == 1){
            agentCharge.setAgentOneId(tAgent.getId());
            agentCharge = agentChargeService.getSumNotAmountByAgentId(agentCharge);
            undischarged =  agentCharge != null ? agentCharge.getAgentOneIncome() : 0l;
        }else if(tAgent.getLevel() == 2){
            agentCharge.setAgentTwoId(tAgent.getId());
            agentCharge =  agentChargeService.getSumNotAmountByAgentId(agentCharge);
            undischarged =  agentCharge != null ? agentCharge.getAgentTwoIncome() : 0l;
        }else{
            agentCharge.setAgentThreeId(tAgent.getId());
            agentCharge =  agentChargeService.getSumNotAmountByAgentId(agentCharge);
            undischarged =  agentCharge != null ? agentCharge.getAgentThreeIncome() : 0l;
        }
        resultMap.put("undischarged", undischarged);//未清算
        resultMap.put("notPutForward", notPutForward);//未提现
        resultMap.put("alreadyPresented", agentWithdraw != null ? agentWithdraw.getAmount() : 0l);//已提现
        return resultMap;
    }

    /**
     * 审批成功
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

    /**
     * 审批失败
     */

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AgentWithdraw agentWithdraw) {
        agentWithdraw.setStatus(2);
        int i = agentWithdrawService.updateStatusById(agentWithdraw);
        if(i == 0)return super.ERROR_TIP;
        agentWithdraw = agentWithdrawService.getAgentWithdrawById(agentWithdraw.getId());
        if(agentWithdraw == null)return super.ERROR_TIP;
        //退钱  加余额
        agentService.updateAmount(agentWithdraw.getAmount(),1,agentWithdraw.getAgentId());
        return super.SUCCESS_TIP;
    }

    /**
     * 提现
     */

    @RequestMapping(value = "/withdraw")
    @ResponseBody
    public Object withdraw() {
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentById(userdto.getId());
        BankInfo bankInfo = bankInfoService.getBankInfoByAgentId(tAgent.getId());
        if(bankInfo == null || bankInfo.getCardNo() == null || bankInfo.getName() == null || bankInfo.getPhone() == null || bankInfo.getIdCardNo() == null) return new ErrorTip(500,"银行卡信息不全，请在手机端代理商管理上绑卡后再进行此操作!");
        Long balance = tAgent.getBalance()-tAgent.getBalanceDisabled();//余额(单位：分)
        TSystemPref MIN_WITHDRAW = systemPrefService.selectByCode("MIN_WITHDRAW");
        TSystemPref SERVICE_CHARGE = systemPrefService.selectByCode("SERVICE_CHARGE");
        long min = MIN_WITHDRAW == null ? 10000 : Long.valueOf(MIN_WITHDRAW.getValue());//最小提现金额(单位：分)
        long fee = SERVICE_CHARGE == null ? 200 : Long.valueOf(SERVICE_CHARGE.getValue());//手续费(单位：分)
        if(balance < min) return new ErrorTip(500,"余额不足" + min/100 + "元,提现失败!");
        int i = agentWithdrawService.createAgentWithdraw(bankInfo,balance,fee);
        if(i == 0) return new ErrorTip(500,"插入失败,提现失败!");
        //减余额
        agentService.updateAmount(balance,0,tAgent.getId());
        return super.SUCCESS_TIP;
    }
}
