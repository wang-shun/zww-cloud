package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.TDollInfo;
import com.stylefeng.guns.common.persistence.model.TDollInfoHistory;
import com.stylefeng.guns.core.aliyun.AliyunService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.modular.backend.service.ITDollInfoHistoryService;
import com.stylefeng.guns.modular.backend.service.ITDollInfoService;
import com.stylefeng.guns.modular.backend.warpper.TDollInfoWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 库存管理控制器
 *
 * @author bruce
 * @Date 2018-07-23 15:52:22
 */
@Controller
@RequestMapping("/tDollInfo")
public class TDollInfoController extends BaseController {

    private String PREFIX = "/backend/tDollInfo/";

    @Autowired
    private ITDollInfoService tDollInfoService;

    @Autowired
    private ITDollInfoHistoryService tDollInfoHistoryService;

    @Autowired
    AliyunService aliyunService;

    /**
     * 跳转到库存管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tDollInfo.html";
    }

    /**
     * 跳转到添加库存管理
     */
    @RequestMapping("/tDollInfo_add")
    public String tDollInfoAdd() {
        return PREFIX + "tDollInfo_add.html";
    }

    /**
     * 跳转到修改库存管理
     */
     
    @RequestMapping("/tDollInfo_update/{tDollInfoId}")
    public String tDollInfoUpdate(@PathVariable Integer tDollInfoId, Model model) {
        TDollInfo tDollInfo = tDollInfoService.selectById(tDollInfoId);
        model.addAttribute("item",tDollInfo);
        SimpleDateFormat sim =new SimpleDateFormat("yyyy-MM-dd");
        model.addAttribute("time",sim.format(tDollInfo.getAddTime()));
        LogObjectHolder.me().set(tDollInfo);
        return PREFIX + "tDollInfo_edit.html";
    }

    @RequestMapping("/tDollInfo_detail/{dollCode}")
    public String tDollInfoDetail(@PathVariable String dollCode, Model model) {
        TDollInfo tDollInfo = tDollInfoService.selectDollInfoByDollCode(dollCode);
        model.addAttribute("item",tDollInfo);
        LogObjectHolder.me().set(tDollInfo);
        return PREFIX + "tDollInfo_detail.html";
    }

    /**
     * 获取库存管理列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String dollCode,String dollName) {
        Page<TDollInfo> page = new PageFactory<TDollInfo>().defaultPage();
        List<Map<String, Object>> result = tDollInfoService.selectDollInfos(page, dollCode,dollName);
        page.setRecords((List<TDollInfo>) new TDollInfoWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增库存管理
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TDollInfo tDollInfo) {
        TDollInfo tDollInfos = tDollInfoService.selectDollInfoByDollCode(tDollInfo.getDollCode());
        if(tDollInfos != null){
            return new ErrorTip(500,"娃娃编号已存在，请重新输入！");
        }
        tDollInfo.setAddTime(new Date());
        tDollInfo.setRedeemCoins(tDollInfo.getDollTotal());
        tDollInfo.setDeliverCoins(tDollInfo.getDollCoins() * tDollInfo.getDollTotal());
        tDollInfoService.insert(tDollInfo);
        TDollInfoHistory history = new TDollInfoHistory(tDollInfo,0,tDollInfo.getDollTotal(), ShiroKit.getUser().getId());
        tDollInfoHistoryService.insert(history);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除库存管理
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tDollInfoId) {
        tDollInfoService.deleteById(tDollInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改库存管理
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TDollInfo tDollInfo) {
        tDollInfo.setAddTime(new Date());
        TDollInfo info = tDollInfoService.selectById(tDollInfo.getId());
        TDollInfoHistory history = new TDollInfoHistory(tDollInfo,info.getDollTotal(),tDollInfo.getDollTotal(), ShiroKit.getUser().getId());
        //进货数量
        int num = tDollInfo.getDollTotal()-info.getDollTotal();
        //娃娃总数量
        int total = info.getRedeemCoins() + num;
        //娃娃总金额
        long sum = info.getDeliverCoins() + num * tDollInfo.getDollCoins();
        //单价
        long price = sum/total;
        tDollInfo.setRedeemCoins(total);
        tDollInfo.setDeliverCoins(sum);
        tDollInfo.setDollCoins(price);
        tDollInfoService.updateById(tDollInfo);
        tDollInfoHistoryService.insert(history);
        return super.SUCCESS_TIP;
    }

    /**
     * 库存管理详情
     */
     
    @RequestMapping(value = "/detail/{tDollInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("tDollInfoId") Integer tDollInfoId) {
        return tDollInfoService.selectById(tDollInfoId);
    }

    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String originalFileName = picture.getOriginalFilename();
        // 获取后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")
                + 1);
        // 修改后完整的文件名称
        String fileKey = StringUtils.getRandomUUID();
        String NewFileKey = "stock/" + fileKey + "." + suffix;
        byte[] bytes;
        try {
            bytes = picture.getBytes();
            InputStream fileInputStream = new ByteArrayInputStream(bytes);
            if(!aliyunService.putFileStreamToOSS(NewFileKey, fileInputStream)) {
                return "0";
            }
            String newFileUrl = aliyunService.generatePresignedUrl(NewFileKey,1000000).toString();
            return newFileUrl;
        } catch (Exception e) {
            return "0";
        }

    }
}
