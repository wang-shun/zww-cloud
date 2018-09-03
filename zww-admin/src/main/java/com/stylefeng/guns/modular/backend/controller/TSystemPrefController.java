package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.persistence.model.TSystemPref;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.backend.service.ITSystemPrefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 设置参数列表控制器
 *
 * @author bruce
 * @Date 2018-01-17 20:07:26
 */
@Controller
@RequestMapping("/tSystemPref")
public class TSystemPrefController extends BaseController {

    private String PREFIX = "/backend/tSystemPref/";

    @Autowired
    private ITSystemPrefService tSystemPrefService;

    /**
     * 跳转到设置参数列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tSystemPref.html";
    }

    /**
     * 跳转到添加设置参数列表
     */
    @RequestMapping("/tSystemPref_add")
    public String tSystemPrefAdd() {
        return PREFIX + "tSystemPref_add.html";
    }

    /**
     * 跳转到修改设置参数列表
     */
     
    @RequestMapping("/tSystemPref_update/{tSystemPrefId}")
    public String tSystemPrefUpdate(@PathVariable String tSystemPrefId, Model model) {
        TSystemPref tSystemPref = tSystemPrefService.selectByCode(tSystemPrefId);
        model.addAttribute("item",tSystemPref);
        LogObjectHolder.me().set(tSystemPref);
        return PREFIX + "tSystemPref_edit.html";
    }

    /**
     * 获取设置参数列表列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition,String code) {
        if(ToolUtil.isEmpty(condition) && ToolUtil.isEmpty(condition)){
            return tSystemPrefService.selectList(null);
        }else{
            EntityWrapper<TSystemPref> entityWrapper = new EntityWrapper<>();
            Wrapper<TSystemPref> wrapper =  entityWrapper.like("name",condition).like("code",code);
            return tSystemPrefService.selectList(wrapper);
        }
    }



    /**
     * 新增设置参数列表
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TSystemPref tSystemPref) {
        tSystemPref.setModifiedDate(new Date());
        tSystemPref.setModifiedBy(ShiroKit.getUser().getId());
        tSystemPref.setType(1);
        tSystemPrefService.insert(tSystemPref);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除设置参数列表
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam String tSystemPrefId) {
        TSystemPref tSystemPref = new TSystemPref();
        tSystemPref.setCode(tSystemPrefId);
        tSystemPrefService.deleteByCode(tSystemPref);
        return SUCCESS_TIP;
    }

    /**
     * 修改设置参数列表
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TSystemPref tSystemPref) {
        tSystemPref.setModifiedDate(new Date());
        tSystemPref.setModifiedBy(ShiroKit.getUser().getId());
        tSystemPrefService.updateByCode(tSystemPref);
        return super.SUCCESS_TIP;
    }

    /**
     * 设置参数列表详情
     */
     
    @RequestMapping(value = "/detail/{tSystemPrefId}")
    @ResponseBody
    public Object detail(@PathVariable("tSystemPrefId") Integer tSystemPrefId) {
        return tSystemPrefService.selectById(tSystemPrefId);
    }
}
