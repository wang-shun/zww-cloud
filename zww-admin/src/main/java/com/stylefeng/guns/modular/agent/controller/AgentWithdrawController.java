package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.common.persistence.model.vo.AgentChargeVo;
import com.stylefeng.guns.common.weixin.WXUtil;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.agent.service.IAgentChargeService;
import com.stylefeng.guns.modular.agent.service.IAgentWithdrawService;
import com.stylefeng.guns.modular.agent.service.IBankInfoService;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.stylefeng.guns.modular.agent.warpper.withdrawWarpper;
import com.stylefeng.guns.modular.backend.service.ITSystemPrefService;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 提现控制器
 *
 * @author bruce
 * @Date 2018-06-04 21:25:24
 */
@Controller
@RequestMapping("/agentWithdraw")
public class AgentWithdrawController extends BaseController {
    private String PREFIX = "/agent";

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
    @Autowired
    private ITAgentService tAgentService;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 跳转到提现管理首页
     */
    @RequestMapping("")
    public String agentWithdraw() {
        return  PREFIX +"/withdrow/agentWithdraw.html";
    }
    /**
     * 跳转到未审批管理首页
     */
    @RequestMapping("/noApproval")
    public String noApproval() {
        return  PREFIX +"/notApproval/agentWithdraw.html";
    }

    /**
     * 跳转到已审批管理首页
     */
    @RequestMapping("/approval")
    public String approval() {
        return PREFIX +"/approval/agentWithdraw.html";
    }

    /**
     * 跳转到提现首页
     */
    @RequestMapping("/withdrawPage")
    public String withdrawPage( Model model) {
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentByUId(userdto.getId());
        model.addAttribute("balance",(tAgent.getBalance() - tAgent.getBalanceDisabled())*0.01);
        TSystemPref MIN_WITHDRAW = systemPrefService.selectByCode("MIN_WITHDRAW");//最小提现金额(单位：分)
        TSystemPref SERVICE_CHARGE = systemPrefService.selectByCode("SERVICE_CHARGE");//手续费(单位：分)
        model.addAttribute("MIN_WITHDRAW",MIN_WITHDRAW == null ? 100 : Long.valueOf(MIN_WITHDRAW.getValue())*0.01);
        model.addAttribute("SERVICE_CHARGE",SERVICE_CHARGE == null ? 2 : Long.valueOf(SERVICE_CHARGE.getValue())*0.01);
        return PREFIX +"/withdrow/agentWithdraw_add.html";
    }

    /**
     * 跳转到修改代理商管理
     */

    @RequestMapping("/agentWithdraw_upd/{withdrawId}")
    public String tAgentUpdate(@PathVariable Integer withdrawId, Model model) {
        model.addAttribute("withdrawId",withdrawId);
        return  PREFIX +"/notApproval/agentWithdraw_upd.html";
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
        TAgent tAgent = agentService.selectTAgentByUId(userdto.getId());
        List<Map<String, Object>> result =  agentWithdrawService.selectAgentWithdrow(page,tAgent.getId(),2,status,null,null,createDate);
        page.setRecords((List<AgentWithdraw>) new withdrawWarpper(result).warp());
        return  super.packForBT(page);
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
        long notPutForward = tAgent.getBalance() - tAgent.getBalanceDisabled();//可提现金额=总余额-冻结金额
        AgentWithdraw agentWithdraw = agentWithdrawService.getSumAmountByAgentId(tAgent.getId());
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
        agentCharge = agentChargeService.getSumAmountByAgentId(agentCharge);
        resultMap.put("undischarged", agentCharge.getAgentSuperIncome());//未清算
        resultMap.put("notPutForward", notPutForward);//未提现
        resultMap.put("alreadyPresented", agentWithdraw != null ? agentWithdraw.getAmount() : 0l);//已提现
        return resultMap;
    }

    /**
     * 审批成功
     */
    @PostMapping("/updStatus")
    @ResponseBody
    public synchronized Map<String, Object> updStatus(Integer status ,Integer withdrawId) throws Exception{
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
    public synchronized Object update(AgentWithdraw agentWithdraw) {
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
    @Permission({Const.AGENT_SUPER,Const.AGENT_ONE,Const.AGENT_TWO,Const.AGENT_three})
    @ResponseBody
    public synchronized Object withdraw(AgentWithdraw agentWithdraw) {
        Date now = new Date();
        TSystemPref START_DATE = systemPrefService.selectByCode("START_DATE");//提现开始时间
        TSystemPref END_DATE = systemPrefService.selectByCode("END_DATE");//提现结束时间
        if(START_DATE != null && END_DATE != null){
            SimpleDateFormat sim = new SimpleDateFormat("HHmmss");
            long start = Long.valueOf(START_DATE.getValue());
            long end = Long.valueOf(END_DATE.getValue());
            long nowTime = Long.valueOf(sim.format(now));
            if(nowTime < start || nowTime > end){
                return new ErrorTip(500,"提现时间：早8:30到晚17:30，其他时间段关闭提现！");
            }
        }
        Integer isHostory = WXUtil.isHostory(now);
        if(isHostory != null && isHostory != 0){
            return new ErrorTip(500,"暂不支持节假日提现！");
        }
        if(isHostory == null && WXUtil.isWeekend(now)){
            return new ErrorTip(500,"暂不支持周末提现！");
        }
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentByUId(userdto.getId());
        if(tAgent.getStatus() == 2){
            return new ErrorTip(500,"该账户已被冻结，请联系上级代理查询！");
        }
        if(tAgent.getStatus() == 3) {
            return new ErrorTip(500,"该账户已失效!");
        }
        if(agentWithdraw.getId() == null || agentWithdraw.getId() == 0){
            return new ErrorTip(500,"请选择到账卡号！");
        }
        BankInfo bankInfo = bankInfoService.selectById(agentWithdraw.getId());
        if(bankInfo == null){
            return new ErrorTip(500,"到账卡号选择错误，请重新选择！");
        }
        Long balance = tAgent.getBalance()-tAgent.getBalanceDisabled();//余额(单位：分)
        TSystemPref MIN_WITHDRAW = systemPrefService.selectByCode("MIN_WITHDRAW");
        TSystemPref SERVICE_CHARGE = systemPrefService.selectByCode("SERVICE_CHARGE");
        long min = MIN_WITHDRAW == null ? 10000 : Long.valueOf(MIN_WITHDRAW.getValue());//最小提现金额(单位：分)
        long fee = SERVICE_CHARGE == null ? 200 : Long.valueOf(SERVICE_CHARGE.getValue());//手续费(单位：分)
        if(balance < min){
            return new ErrorTip(500,"余额不足" + min*0.01 + "元,提现失败！");
        }
        int i = agentWithdrawService.createAgentWithdraw(bankInfo,balance,fee,now);
        if(i == 0){
            return new ErrorTip(500,"插入失败,提现失败！");
        }
        //减余额
        agentService.updateAmount(balance,0,tAgent.getId());
        return super.SUCCESS_TIP;
    }

    /**
     * 获取银行卡信息
     */
    @PostMapping("/getWithdrawBankInfo")
    @ResponseBody
    public synchronized Map<String, Object> getWithdrawBankInfo() throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        TAgent tAgent = agentService.selectTAgentByUId(userdto.getId());
        try {
            List<BankInfo> list = bankInfoService.getBankInfoByAgentId(tAgent.getId());
            if(list.size() == 0 ){
                resultMap.put("code", 500);
                resultMap.put("msg", "银行卡信息不全，请在手机端代理商管理上绑卡后再进行此操作!");
                return resultMap;
            }
            resultMap.put("code", 200);
            resultMap.put("list", list);
            return resultMap;
        }catch (Exception e){
            resultMap.put("code", 500);
            resultMap.put("msg", e.getMessage());
            return resultMap;
        }
    }

    /***
     * 获取excel数据
     * @return 返回文件名称及excel文件的URL
     * @throws IOException
     */
    @RequestMapping(value = "/execl",method = RequestMethod.GET)
    public void execl(HttpServletResponse response) throws IOException {
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        Integer agentId = null,level = null;
        if("agent".equals(role.getTips().substring(0,5))){
            TAgent tAgent =tAgentService.selectTAgentByUId(userdto.getId());
            if(tAgent != null){
                agentId = tAgent.getId();
                level = tAgent.getLevel();
            }
        }
        List<AgentChargeVo>  AgentChargeList = agentChargeService.getAgentChargeExecl(agentId,level);
        AgentChargeList = AgentChargeList.stream().map(agentChargeVo -> {
            TAgent agentSuper = agentChargeVo.getAgentSuperId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentSuperId());
            TAgent agentOne = agentChargeVo.getAgentOneId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentOneId());
            TAgent agentTwo = agentChargeVo.getAgentTwoId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentTwoId());
            TAgent agentThree = agentChargeVo.getAgentThreeId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentThreeId());
            return  new AgentChargeVo(agentChargeVo,agentSuper,agentOne,agentTwo,agentThree);
        }).collect(Collectors.toList());
        String fileName = "代理商分润.xls";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), AgentChargeVo.class, AgentChargeList);
        workbook.write(response.getOutputStream());
    }



    /***
     * 获取excel数据
     * @return 返回文件名称及excel文件的URL
     * @throws IOException
     */
    @RequestMapping(value = "/profitHistory",method = RequestMethod.GET)
    public void profitHistory(HttpServletResponse response,Integer agentId,String createDate) throws IOException {
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        TAgent tAgent =tAgentService.selectTAgentById(agentId);
        if("agent".equals(role.getTips().substring(0,5))){
            return;
        }
        Date date = agentWithdrawService.getDateByAgentIdAndStatus(agentId);
        Date date1 = DateUtil.parseTime(createDate);
        List<AgentChargeVo>  AgentChargeList = agentChargeService.execlAgentChargeHistoryByAgentId(agentId,tAgent.getLevel(),date,date1);
        AgentChargeList = AgentChargeList.stream().map(agentChargeVo -> {
            TAgent agentSuper = agentChargeVo.getAgentSuperId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentSuperId());
            TAgent agentOne = agentChargeVo.getAgentOneId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentOneId());
            TAgent agentTwo = agentChargeVo.getAgentTwoId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentTwoId());
            TAgent agentThree = agentChargeVo.getAgentThreeId() == 0 ? null : tAgentService.selectTAgentById(agentChargeVo.getAgentThreeId());
            return  new AgentChargeVo(agentChargeVo,agentSuper,agentOne,agentTwo,agentThree);
        }).collect(Collectors.toList());
        String fileName = tAgent.getNickName() + "的分润记录统计.xls";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), AgentChargeVo.class, AgentChargeList);
        workbook.write(response.getOutputStream());
    }
}
