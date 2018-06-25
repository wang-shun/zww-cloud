package com.stylefeng.guns.modular.backend.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.core.aliyun.AliyunService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.core.util.ToolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.mutidatasource.DSEnum;
import com.stylefeng.guns.core.mutidatasource.annotion.DataSource;
import com.stylefeng.guns.common.persistence.model.AdvertisementInfo;
import com.stylefeng.guns.modular.backend.service.IAdvertisementInfoService;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * 文案管理控制器
 *
 * @author fengshuonan
 * @Date 2018-06-08 14:54:12
 */
@Controller
@RequestMapping("/advertisementInfo")
public class AdvertisementInfoController extends BaseController {

    private String PREFIX = "/backend/advertisementInfo/";

    @Autowired
    private IAdvertisementInfoService advertisementInfoService;

    @Autowired
    AliyunService aliyunService;

    /**
     * 跳转到文案管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "advertisementInfo.html";
    }

    /**
     * 跳转到添加文案管理
     */
    @RequestMapping("/advertisementInfo_add")
    public String advertisementInfoAdd() {
        return PREFIX + "advertisementInfo_add.html";
    }

    /**
     * 跳转到修改文案管理
     */
     
    @RequestMapping("/advertisementInfo_update/{advertisementInfoId}")
    public String advertisementInfoUpdate(@PathVariable Integer advertisementInfoId, Model model) {
        AdvertisementInfo advertisementInfo = advertisementInfoService.selectById(advertisementInfoId);
        model.addAttribute("item",advertisementInfo);
        LogObjectHolder.me().set(advertisementInfo);
        return PREFIX + "advertisementInfo_edit.html";
    }

    /**
     * 获取文案管理列表
     */
     
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        if(ToolUtil.isEmpty(condition)){
            return advertisementInfoService.selectList(null);
        }else{
            EntityWrapper<AdvertisementInfo> entityWrapper = new EntityWrapper<>();
            Wrapper<AdvertisementInfo> wrapper =  entityWrapper.like("title",condition);
            return advertisementInfoService.selectList(wrapper);
        }

    }

    /**
     * 新增文案管理
     */
     
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(AdvertisementInfo advertisementInfo) {
        advertisementInfo.setCreateDate(new Date());
        advertisementInfo.setDownCount(0l);
        advertisementInfoService.insert(advertisementInfo);
        return super.SUCCESS_TIP;
    }

    /**
     * 删除文案管理
     */
     
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer advertisementInfoId) {
        advertisementInfoService.deleteById(advertisementInfoId);
        return SUCCESS_TIP;
    }

    /**
     * 修改文案管理
     */
     
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(AdvertisementInfo advertisementInfo) {
        advertisementInfo.setUpdateDate(new Date());
        advertisementInfoService.updateById(advertisementInfo);
        return super.SUCCESS_TIP;
    }

    /**
     * 文案管理详情
     */
     
    @RequestMapping(value = "/detail/{advertisementInfoId}")
    @ResponseBody
    public Object detail(@PathVariable("advertisementInfoId") Integer advertisementInfoId) {
        return advertisementInfoService.selectById(advertisementInfoId);
    }

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */

    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture) {
        String newFileUrl ="";
        String originalFileName = picture.getOriginalFilename();
        // 获取后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")
                + 1);
        // 修改后完整的文件名称
        String fileKey = StringUtils.getRandomUUID();
        String NewFileKey = "sys/" +  fileKey + "." + suffix;
        byte[] bytes;
        try {
            bytes = picture.getBytes();
            InputStream fileInputStream = new ByteArrayInputStream(bytes);
            if(!aliyunService.putFileStreamToOSS(NewFileKey, fileInputStream)) {
                return "0";
            }
            newFileUrl = aliyunService.generatePresignedUrl(NewFileKey,1000000).toString();
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        newFileUrl = newFileUrl.replace("lanao.oss-cn-shenzhen.aliyuncs.com","oss.lanao.fun");
        return newFileUrl;
    }
}
