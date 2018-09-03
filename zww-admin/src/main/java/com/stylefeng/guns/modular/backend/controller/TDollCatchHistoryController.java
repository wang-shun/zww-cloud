package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.TDollMapper;
import com.stylefeng.guns.common.persistence.model.TDoll;
import com.stylefeng.guns.common.persistence.model.TDollCatchHistory;
import com.stylefeng.guns.common.persistence.model.TMember;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.backend.service.IMemberChargeHistoryService;
import com.stylefeng.guns.modular.backend.service.ITDollCatchHistoryService;
import com.stylefeng.guns.modular.backend.service.ITDollOrderService;
import com.stylefeng.guns.modular.backend.warpper.TDollWarpper;
import com.stylefeng.guns.modular.system.service.ITMemberService;
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
 * 娃娃机抓取记录控制器
 *
 * @author bruce
 * @Date 2018-06-14 09:53:24
 */
@Controller
@RequestMapping("/tDollCatchHistory")
public class TDollCatchHistoryController extends BaseController {

    private String PREFIX = "/backend/tDollCatchHistory/";

    @Autowired
    private ITDollCatchHistoryService tDollCatchHistoryService;
    @Autowired
    private ITMemberService memberService;
    @Autowired
    private ITDollOrderService  itDollOrderService;
    @Autowired
    private IMemberChargeHistoryService memberChargeHistoryService;
    @Autowired
    private TDollMapper dollMapper;

    /**
     * 跳转到娃娃机抓取记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tDollCatchHistory.html";
    }

    /**
     * 跳转到添加娃娃机抓取记录
     */
    @RequestMapping("/tDollCatchHistory_add")
    public String tDollCatchHistoryAdd() {
        return PREFIX + "tDollCatchHistory_add.html";
    }

    /**
     * 跳转到修改娃娃机抓取记录
     */
     
    @RequestMapping("/tDollCatchHistory_update/{tDollCatchHistoryId}")
    public String tDollCatchHistoryUpdate(@PathVariable Integer tDollCatchHistoryId, Model model) {
        TDollCatchHistory tDollCatchHistory = tDollCatchHistoryService.selectById(tDollCatchHistoryId);
        TMember member = memberService.selectById(tDollCatchHistory.getMemberId().intValue());
        model.addAttribute("item",tDollCatchHistory);
        model.addAttribute("name",member.getName());
        LogObjectHolder.me().set(tDollCatchHistory);
        return PREFIX + "tDollCatchHistory_edit.html";
    }

    /**
     * 获取娃娃机抓取记录列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(Integer dollId, String dollName, String machineCode, String dollCatchStates,Integer machineType,String memberName,String beginDate,String endtime) {
        Page<TDollCatchHistory> page = new PageFactory<TDollCatchHistory>().defaultPage();
        List<Map<String, Object>> result = tDollCatchHistoryService.selectDollCatchHistorys(page,dollId,dollName,machineCode,dollCatchStates,machineType,memberName,beginDate,endtime);
        page.setRecords((List<TDollCatchHistory>)new TDollWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增娃娃机抓取记录
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TDollCatchHistory tDollCatchHistory) {
        tDollCatchHistoryService.insert(tDollCatchHistory);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除娃娃机抓取记录
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tDollCatchHistoryId) {
        tDollCatchHistoryService.deleteById(tDollCatchHistoryId);
        return SUCCESS_TIP;
    }

    /**
     * 修改娃娃机抓取记录
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TDollCatchHistory tDollCatchHistory) throws  Exception{
        tDollCatchHistoryService.updateById(tDollCatchHistory);
        if("游戏异常,币已返回".equals(tDollCatchHistory.getCatchStatus())){
            tDollCatchHistory = tDollCatchHistoryService.selectById(tDollCatchHistory.getId());
            TDoll doll = dollMapper.selectById(tDollCatchHistory.getDollId());
            memberChargeHistoryService.insertChargeHistory(tDollCatchHistory.getMemberId().intValue(),doll.getRedeemCoins());
        }
        if("抓取成功".equals(tDollCatchHistory.getCatchStatus())){
            tDollCatchHistory = tDollCatchHistoryService.selectById(tDollCatchHistory.getId());
            Boolean flag = itDollOrderService.insertTDollOrder(tDollCatchHistory,ShiroKit.getUser().getId());
            if(!flag){
                return  new ErrorTip(500,"修改失败！");
            }
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 娃娃机抓取记录详情
     */
     
    @RequestMapping(value = "/detail/{tDollCatchHistoryId}")
    @ResponseBody
    public Object detail(@PathVariable("tDollCatchHistoryId") Integer tDollCatchHistoryId) {
        return tDollCatchHistoryService.selectById(tDollCatchHistoryId);
    }
}
