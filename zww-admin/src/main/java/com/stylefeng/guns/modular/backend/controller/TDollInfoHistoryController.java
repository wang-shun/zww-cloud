package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.TDollInfoHistory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.backend.service.ITDollInfoHistoryService;
import com.stylefeng.guns.modular.backend.warpper.TDollInfoWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 库存记录控制器
 *
 * @author bruce
 * @Date 2018-07-23 17:11:29
 */
@Controller
@RequestMapping("/tDollInfoHistory")
public class TDollInfoHistoryController extends BaseController {

    private String PREFIX = "/backend/tDollInfoHistory/";

    @Autowired
    private ITDollInfoHistoryService tDollInfoHistoryService;

    /**
     * 跳转到库存记录首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tDollInfoHistory.html";
    }


    /**
     * 获取库存记录列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String dollCode,String dollName) {
        Page<TDollInfoHistory> page = new PageFactory<TDollInfoHistory>().defaultPage();
        List<Map<String, Object>> result = tDollInfoHistoryService.selectDollInfoHistorys(page, dollCode,dollName);
        page.setRecords((List<TDollInfoHistory>) new TDollInfoWarpper(result).warp());
        return super.packForBT(page);
    }

}
