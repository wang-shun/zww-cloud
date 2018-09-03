package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.model.TChargeRules;
import com.stylefeng.guns.common.persistence.model.TDoll;
import com.stylefeng.guns.core.aliyun.AliyunService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.TipType;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.modular.backend.service.ITChargeRulesService;
import com.stylefeng.guns.modular.backend.warpper.TChargeRulesWarpper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 充值规则列表控制器
 *
 * @author bruce
 * @Date 2018-01-17 20:12:56
 */
@Controller
@RequestMapping("/tChargeRules")
public class TChargeRulesController extends BaseController {

    private String PREFIX = "/backend/tChargeRules/";

    @Autowired
    private ITChargeRulesService tChargeRulesService;

    @Autowired
    AliyunService aliyunService;

    /**
     * 跳转到充值规则列表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tChargeRules.html";
    }

    /**
     * 跳转到添加充值规则列表
     */
    @RequestMapping("/tChargeRules_add")
    public String tChargeRulesAdd() {
        return PREFIX + "tChargeRules_add.html";
    }

    /**
     * 跳转到修改充值规则列表
     */
     
    @RequestMapping("/tChargeRules_update/{tChargeRulesId}")
    public String tChargeRulesUpdate(@PathVariable Integer tChargeRulesId, Model model) {
        TChargeRules tChargeRules = tChargeRulesService.selectById(tChargeRulesId);
        model.addAttribute("item",tChargeRules);
        LogObjectHolder.me().set(tChargeRules);
        return PREFIX + "tChargeRules_edit.html";
    }


    /**
     * 娃娃封面图片上传
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/toUpload")
    public String toUpload(Integer id, Model model) {
        TChargeRules tChargeRules = tChargeRulesService.selectById(id);
        model.addAttribute("item", tChargeRules);
        return PREFIX + "tChargeRules_upload.html";
    }


    /**
     * 上传图片
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload/{tChargeRulesId}")
    @ResponseBody
    public Object upload(@RequestPart("file") MultipartFile picture, @PathVariable Integer tChargeRulesId) {
        String originalFileName = picture.getOriginalFilename();
        // 获取后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")
                + 1);
        // 修改后完整的文件名称
        String fileKey = StringUtils.getRandomUUID();
        String NewFileKey = "chargeRules/" + fileKey + "." + suffix;
        byte[] bytes;
        try {
            bytes = picture.getBytes();
            InputStream fileInputStream = new ByteArrayInputStream(bytes);
            if (!aliyunService.putFileStreamToOSS(NewFileKey, fileInputStream)) {
                return "0";
            }
            TChargeRules dollmachine = tChargeRulesService.selectById(tChargeRulesId);
            if (dollmachine.getIcon() != null && !dollmachine.getIcon().equals("") && dollmachine.getIcon().length() > 10) {
                String oldFileKey = dollmachine.getIcon().substring(dollmachine.getIcon().lastIndexOf("/") + 1, dollmachine.getIcon().lastIndexOf("."));
                //如果有则删除原来的头像
                if (!"".equals(oldFileKey) && oldFileKey != null) {
                    aliyunService.deleteFileFromOSS(oldFileKey);
                }
            }
            String newFileUrl = aliyunService.generatePresignedUrl(NewFileKey, 1000000).toString();
            dollmachine.setIcon(newFileUrl);
            dollmachine.setModifiedBy(ShiroKit.getUser().getId());
            dollmachine.setModifiedDate(new Date());
            boolean result = tChargeRulesService.insertOrUpdate(dollmachine);
            if (result) {
                return SUCCESS_TIP;
            } else {
                return new ErrorTip(TipType.UPLOAD_ERROR.getCode(), TipType.UPLOAD_ERROR.getMessage());
            }
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
    }

    /**
     * 获取充值规则列表列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Page<TChargeRules> page = new PageFactory<TChargeRules>().defaultPage();
        List<Map<String, Object>> result = tChargeRulesService.selectRulesList(page);
        page.setRecords((List<TChargeRules>)new TChargeRulesWarpper(result).warp());
        return super.packForBT(page);
    }

    /**
     * 新增充值规则列表
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TChargeRules tChargeRules) {
        tChargeRulesService.insert(tChargeRules);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除充值规则列表
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tChargeRulesId) {
        tChargeRulesService.deleteById(tChargeRulesId);
        return SUCCESS_TIP;
    }

    /**
     * 修改充值规则列表
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TChargeRules tChargeRules) {
        tChargeRulesService.updateById(tChargeRules);
        return super.SUCCESS_TIP;
    }

    /**
     * 充值规则列表详情
     */
     
    @RequestMapping(value = "/detail/{tChargeRulesId}")
    @ResponseBody
    public Object detail(@PathVariable("tChargeRulesId") Integer tChargeRulesId) {
        return tChargeRulesService.selectById(tChargeRulesId);
    }
}
