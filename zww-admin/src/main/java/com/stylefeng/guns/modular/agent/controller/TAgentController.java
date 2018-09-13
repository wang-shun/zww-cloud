package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.*;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.common.persistence.model.vo.AgentVo;
import com.stylefeng.guns.common.weixin.WXUtil;
import com.stylefeng.guns.core.aliyun.AliyunService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.agent.service.ITOemTemplateService;
import com.stylefeng.guns.modular.backend.service.ITSystemPrefService;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.transfer.UserDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.modular.agent.service.ITAgentService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import com.stylefeng.guns.modular.system.warpper.TagentWarpper;
import org.springframework.web.multipart.MultipartFile;


/**
 * 代理商管理控制器
 *
 * @author bruce
 * @Date 2018-05-31 16:40:48
 */
@Controller
@RequestMapping("/tAgent")
@CrossOrigin
public class TAgentController extends BaseController {

    private String PREFIX = "/agent/tAgent/";

    @Autowired
    private ITAgentService tAgentService;
    @Autowired
    private ITSystemPrefService systemPrefService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TMemberMapper tMemberMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private TOemMapper toemMapper;

    @Resource
    private TOemBannerMapper tOemBannerMapper;

    @Resource
    private ITOemTemplateService itOemTemplateService;

    @Autowired
    AliyunService aliyunService;


    /**
     * 跳转到代理商管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "tAgent.html";
    }

    /**
     * 跳转到添加代理商管理
     */
    @RequestMapping("/tAgent_add")
    public String tAgentAdd(Model model) {
        model.addAttribute("fee",getFee());
        return PREFIX + "tAgent_add.html";
    }

    /**
     * 跳转到修改代理商管理
     */

    @RequestMapping("/tAgent_update/{tAgentId}")
    public String tAgentUpdate(@PathVariable Integer tAgentId, Model model) {
        TAgent tAgent = tAgentService.selectById(tAgentId);
        model.addAttribute("item",tAgent);
        model.addAttribute("fee",getFee());
        LogObjectHolder.me().set(tAgent);
        return PREFIX + "tAgent_edit.html";
    }


    /**
     * 跳转到oem代理商管理
     */

    @RequestMapping("/templatePage/{tAgentId}")
    public String templatePage(@PathVariable Integer tAgentId, Model model) {
        model.addAttribute("tAgentId",tAgentId);
        return PREFIX + "tAgent_template.html";
    }


    /**
     * 跳转到oem代理商管理
     */

    @RequestMapping("/oemPage/{tAgentId}")
    public String oemPage(@PathVariable Integer tAgentId, Model model) {
        TAgent tAgent = tAgentService.selectById(tAgentId);
        model.addAttribute("tAgent",tAgent);
        LogObjectHolder.me().set(tAgent);
        return PREFIX + "tAgent_oem.html";
    }


    /**
     * 获取代理商管理列表
     */

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String name,String username,String phone, String createTime,
                       Integer level) {
        Page<TAgent> page = new PageFactory<TAgent>().defaultPage();
        //设置查询的条件level,id
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        Integer type = null,agentId = null;
        if("agent_super".equals(role.getTips())){
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel(); agentId=tAgent.getId();
        }else if("agent_one".equals(role.getTips())){
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel();agentId=tAgent.getId();
        }else if("agent_two".equals(role.getTips())){
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel();agentId=tAgent.getId();
        }else if("agent_three".equals(role.getTips())){
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }else{
            type = 10;
        }
        List<Map<String, Object>>  result= tAgentService.selectByLevel(page,name,username,phone,createTime,level,type,agentId);
        page.setRecords((List<TAgent>)new TagentWarpper(result).warp());
        return super.packForBT(page);

    }

    //得到下级费率
    private double getFee(){
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        String code = "";
        if("agent_super".equals(role.getTips())){
            code = "AGENT_ONE_FEE";
        }else if("agent_one".equals(role.getTips())){
            code = "AGENT_TWO_FEE";
        }else if("agent_two".equals(role.getTips())){
            code = "AGENT_THREE_FEE";
        }else if("agent_three".equals(role.getTips())){
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }else{
            code = "AGENT_SUPER_FEE";
        }
        TSystemPref systemPref = systemPrefService.selectByCode(code);
        return Double.valueOf(systemPref.getValue());
    }

    /**
     * 新增代理商管理
     */

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TAgent tAgent) throws  Exception{
        if(StringUtils.isEmpty(tAgent.getNickName()) || "^[^\\s]*$".matches(tAgent.getNickName())){
            return  new ErrorTip(500,"真实姓名格式不正确，请输入正确的真实姓名");
        }
        TAgent agent = tAgentService.selectTAgentByUsername(tAgent.getUsername());
        if(agent != null ){
            return  new ErrorTip(500,"添加失败!用户名已存在");
        }
        User u = userMapper.selectByUsername(tAgent.getUsername());
        if(u != null){
            return  new ErrorTip(500,"添加失败!用户名已存在，该用户已是管理员");
        }
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        //添加用户
        UserDto user = new UserDto();
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        //cji管理员添加费率
        if(!role.getTips().contains("agent")){
            double defaultFee = getFee();
            if (defaultFee < tAgent.getFee() || tAgent.getFee() < 0){
                return  new ErrorTip(500,"添加失败!扣率必须在0-" + defaultFee + "之间");
            }
            tAgent.setLevel(0);
            user.setRoleid("2");
        }else{
            TAgent map =tAgentService.selectTAgentByUId(userdto.getId());
            double fee = map.getFee();
            if (fee < tAgent.getFee() || tAgent.getFee() < 0){
                return  new ErrorTip(500,"添加失败!扣率必须在0-" + fee + "之间");
            }

            Integer level = map.getLevel();
            switch(level){
                case 0:
                    tAgent.setLevel(level+1);
                    user.setRoleid("3");
                    tAgent.setAgentId(map.getId());
                    break;
                case 1:
                    tAgent.setLevel(level+1);
                    user.setRoleid("4");
                    tAgent.setAgentId(map.getAgentId());
                    tAgent.setAgentOneId(map.getId());
                    break;
                case 2:
                    tAgent.setLevel(level+1);
                    user.setRoleid("5");
                    tAgent.setAgentId(map.getAgentId());
                    tAgent.setAgentOneId(map.getAgentOneId());
                    tAgent.setAgentTwoId(map.getId());
                    break;
                case 3:throw new GunsException(BizExceptionEnum.REQUEST_NULL);
            }
        }


        //设置md5和盐
        tAgent.setSalt(ShiroKit.getRandomSalt(5));
        if(ToolUtil.isEmpty(tAgent.getPassword())){
            tAgent.setPassword(ShiroKit.md5(Const.DEFAULT_PWD,tAgent.getSalt()));
        }else {
            tAgent.setPassword(ShiroKit.md5(tAgent.getPassword(),tAgent.getSalt()));
        }
        tAgent.setCreateTime(new Date());
        tAgent.setUpdateTime(new Date());
        tAgent.setOem(false);

        user.setAccount(tAgent.getUsername());
        user.setName(tAgent.getNickName());
        user.setPassword(tAgent.getPassword());
        user.setSalt(tAgent.getSalt());
        user.setPhone(tAgent.getPhone());
        user.setStatus(1);
        user.setDeptid(52);
        userMapper.insert(UserFactory.createUser(user));
        tAgentService.insert(tAgent);
        return super.SUCCESS_TIP;
    }

    /**
     * 修改代理商管理
     */

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TAgent tAgent) {
        TAgent agent = tAgentService.selectTAgentById(tAgent.getId());
        if(agent.getLevel() == 0){//查询需要修改的代理等级
            double defaultFee = getFee();
            if (defaultFee < tAgent.getFee() || tAgent.getFee() < 0){
                return  new ErrorTip(500,"添加失败!扣率必须在0-" + defaultFee + "之间");
            }
        }else if(agent.getLevel() == 1){
            TAgent superAgent = tAgentService.selectTAgentById(agent.getAgentId());
            double fee = superAgent.getFee();
            if (fee < tAgent.getFee() || tAgent.getFee() < 0) {
                return new ErrorTip(500, "添加失败!扣率必须在0-" + fee + "之间");
            }
        }else if(agent.getLevel() == 2){
            TAgent oneAgent = tAgentService.selectTAgentById(agent.getAgentOneId());
            double fee = oneAgent.getFee();
            if (fee < tAgent.getFee() || tAgent.getFee() < 0) {
                return new ErrorTip(500, "添加失败!扣率必须在0-" + fee + "之间");
            }
        }else if(agent.getLevel() == 3){
            TAgent twoAgent = tAgentService.selectTAgentById(agent.getAgentTwoId());
            double fee = twoAgent.getFee();
            if (fee < tAgent.getFee() || tAgent.getFee() < 0) {
                return new ErrorTip(500, "添加失败!扣率必须在0-" + fee + "之间");
            }
        }

        tAgent.setUpdateTime(new Date());
        tAgentService.updateById(tAgent);
        return super.SUCCESS_TIP;
    }

    /**
     * 代理商管理详情
     */

    @RequestMapping(value = "/detail/{tAgentId}")
    @ResponseBody
    public Object detail(@PathVariable("tAgentId") Integer tAgentId) {
        return tAgentService.selectById(tAgentId);
    }


    /**
     * 得到代理商等级
     */
    @PostMapping("/getlavel")
    @ResponseBody
    public Map<String, Object> getlavel() throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        Integer type = null;
        if(!role.getTips().contains("agent")){
            type =0;
        }else{
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel()+1;
        }
        resultMap.put("type", type);//未提现
        return resultMap;
    }


    /**
     * 重置密码
     */
    @PostMapping("/resetPass/{tAgentId}")
    @ResponseBody
    public Object resetPass(@PathVariable("tAgentId") Integer tAgentId) throws Exception{
        TAgent tAgent = tAgentService.selectTAgentById(tAgentId);
        tAgent.setPassword(ShiroKit.md5(Const.DEFAULT_PWD,tAgent.getSalt()));
        tAgentService.updateById(tAgent);
        User user = userMapper.selectByUsername(tAgent.getUsername());
        user.setPassword(tAgent.getPassword());
        userMapper.updateById(user);
        return super.SUCCESS_TIP;
    }


    /**
     * 生成二维码以及链接
     */
    @GetMapping("/qrcode/{tAgentId}")
    public String qrcode(@PathVariable("tAgentId") Integer tAgentId, Model model) throws Exception{
        TAgent tAgent = tAgentService.selectTAgentById(tAgentId);
        String jsonStr = WXUtil.doPost("agentId="+tAgent.getId()+"&token=1&version=1","http://lanao.nat300.top/icrane/api/share/AgentImgUrl","POST");
       // {"success":true,"statusCode":"200","message":"right token","token":"","resultData":{"shareImgUrl":"http://lanao.oss-cn-shenzhen.aliyuncs.com/agent/share/1/f2d8afa2-5e65-4f4b-9252-fd2a193c34fc.jpg?Expires=1845252970&OSSAccessKeyId=LTAIibVdoyx3NRxN&Signature=bt%2BHdd6X%2BROQ2PONA3DjC9NP4UA%3D","shareUrl":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxcb4254f4b131fc12&redirect_uri=http%3A%2F%2Flanao.nat300.top/icrane/api/h5login&response_type=code&scope=snsapi_userinfo&state=agent1-lanaokj_null#wechat_redirect"}}
       JSONObject json = JSONObject.fromObject(jsonStr);
       JSONObject resultData = json.getJSONObject("resultData");
       String shareImgUrl = resultData.getString("shareImgUrl");
       String shareUrl = resultData.getString("shareUrl");
       model.addAttribute("shareImgUrl",shareImgUrl);
       model.addAttribute("shareUrl",shareUrl);
       model.addAttribute("name",tAgent.getNickName());
       return  PREFIX + "tAgent_qrcode.html";
    }

    /**
     * 得到o单banner总数
     */
    @RequestMapping("/getBannerList")
    @ResponseBody
    public Map<String, Object> getBannerList(Integer  tAgentId) throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        List<TOemBanner>  oemBannerList =  tOemBannerMapper.selectByOemId(tAgentId);
        resultMap.put("oemBannerList", oemBannerList);
        TOem oem = toemMapper.selectById(tAgentId);
        resultMap.put("oem",oem);
        return resultMap;
    }


    /**
     * oem进件
     */

    @PostMapping(value = "/oemAdd")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object oemAdd(@RequestBody JSONObject jsonObject) throws  Exception{
        TOem oem=(TOem)JSONObject.toBean(jsonObject.getJSONObject("oem"), TOem.class);
        List<TOemBanner> oemBanner=(List<TOemBanner>) JSONArray.toList(JSONArray.fromObject(jsonObject.getJSONArray("oemBanner")), TOemBanner.class);
        TOem tOem = toemMapper.selectById(oem.getId());
        if(oemBanner.size() > 0){
            tOemBannerMapper.deleteByOemId(oem.getId());
            tOemBannerMapper.insertBatch(oemBanner);
        }
        Date now = new Date();
        if(tOem == null){
            oem.setPartner("1503788561");
            oem.setPartnerKey("PtR5S9g88z8LTUFZTsPMWdtqUgDJ4f8V");
            oem.setNatappUrl("lanao.nat300.top");
            oem.setStatus(1);
            oem.setCreateTime(now);
            oem.setUpdateTime(now);
            toemMapper.insert(oem);
            //创建菜单
            WXUtil.doPost("", "http://lanao.nat300.top/icrane/api/createMenu?code=" + oem.getCode(),"GET");
            TAgent t = new TAgent();
            t.setOem(true);
            t.setId(oem.getId());
            t.setUpdateTime(now);
            tAgentService.updateById(t);
            //把自己商户身份化为自己的oem代理名下
            t=tAgentService.selectTAgentById(oem.getId());
            if(t == null){
                return super.SUCCESS_TIP;
            }
            TMember tMember =tMemberMapper.selectByMobile(t.getPhone());
            if(tMember == null){
                return super.SUCCESS_TIP;
            }
            tMember.setRegisterChannel(oem.getCode());
            tMember.setModifiedDate(now);
            tMemberMapper.updateById(tMember);

        }else{
            oem.setUpdateTime(now);
            toemMapper.updateById(oem);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 上传图片(上传到项目的webapp/static/img)
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload/{tAgentId}")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile picture, @PathVariable Integer tAgentId) {
        String originalFileName = picture.getOriginalFilename();
        // 获取后缀
        String suffix = originalFileName.substring(originalFileName.lastIndexOf(".")
                + 1);
        // 修改后完整的文件名称
        String fileKey = StringUtils.getRandomUUID();
        String NewFileKey = "oem/" + tAgentId + "/" + fileKey + "." + suffix;
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

    /***
     * 获取excel数据
     * @return 返回文件名称及excel文件的URL
     * @throws IOException
     */
    @RequestMapping(value = "/execl",method = RequestMethod.GET)
    public void execl(HttpServletResponse response, String name, String username, String phone, String createTime,
                        Integer level) throws IOException {
        //设置查询的条件level,id
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        Integer type = null,agentId = null;
        if("agent_super".equals(role.getTips())){
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel(); agentId=tAgent.getId();
        }else if("agent_one".equals(role.getTips())){
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel();agentId=tAgent.getId();
        }else if("agent_two".equals(role.getTips())){
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel();agentId=tAgent.getId();
        }else if("agent_three".equals(role.getTips())){
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }else{
            type = 10;
        }
         List<TAgent>  result = tAgentService.selectAndExecl(name,username,phone,createTime,level,type,agentId);
         List<AgentVo> agentVoList = result.stream().map(tAgent -> {
            TAgent agentSuper = tAgent.getAgentId() == 0 ? null : tAgentService.selectTAgentById(tAgent.getAgentId());
            TAgent agentOne = tAgent.getAgentOneId() == 0 ? null : tAgentService.selectTAgentById(tAgent.getAgentOneId());
            TAgent agentTwo = tAgent.getAgentTwoId() == 0 ? null : tAgentService.selectTAgentById(tAgent.getAgentTwoId());
            return new AgentVo(tAgent,agentSuper,agentOne,agentTwo);
        }).collect(Collectors.toList());

        String fileName = "代理商管理.xls";

        // 告诉浏览器用什么软件可以打开此文件
        response.setHeader("content-Type", "application/vnd.ms-excel");
        // 下载文件的默认名称
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), AgentVo.class, agentVoList);
        workbook.write(response.getOutputStream());
    }

    /**
     * 得到o单模板列表
     */
    @RequestMapping("/getTemplateList")
    @ResponseBody
    public Map<String, Object> getTemplateList(Integer  tAgentId) throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        List<TOemTemplate>  oemTemplateList =  itOemTemplateService.getOemTemplateByOemId(tAgentId);
        resultMap.put("oemTemplateList", oemTemplateList);
        return resultMap;
    }

    /**
     * 修改状态，变为失效
     */
    @RequestMapping("/delTemplate")
    @ResponseBody
    public Map<String, Object> delTemplate(Integer  id) throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        TOemTemplate template = new TOemTemplate();
        template.setId(id);
        template.setStatus(0);
        template.setUpdateTime(new Date());
        boolean flag =  itOemTemplateService.updateById(template);
        resultMap.put("code", flag);
        return resultMap;
    }


    /**
     * 提交模板
     */

    @PostMapping(value = "/templateAdd")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object templateAdd(@RequestBody JSONObject jsonObject) throws  Exception{
        List<TOemTemplate> oemTemplates = (List<TOemTemplate>) JSONArray.toList(jsonObject.getJSONArray("templateArr"), TOemTemplate.class);
        for (TOemTemplate template : oemTemplates) {
                Date date = new Date();
                template.setUpdateTime(date);
                if(template.getId() != 0 && template.getId() != null){
                    itOemTemplateService.updateById(template);
                }else{
                    template.setId(null);
                    template.setCreateTime(date);
                    itOemTemplateService.insert(template);
                }
        }
        return super.SUCCESS_TIP;
    }
}
