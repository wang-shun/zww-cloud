package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.TSystemPref;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.backend.service.ITSystemPrefService;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.transfer.UserDto;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.stylefeng.guns.common.persistence.model.User;
import java.math.BigDecimal;
import javax.annotation.Resource;
import com.stylefeng.guns.common.constant.state.ManagerStatus;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.stylefeng.guns.modular.system.warpper.TagentWarpper;


/**
 * 代理商管理控制器
 *
 * @author fengshuonan
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
    private UserMgrDao managerDao;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

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
     * 获取代理商管理列表
     */

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String phone, @RequestParam(required = false) String createTime, @RequestParam(required = false) Integer level) {
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
        List<Map<String, Object>>  result= tAgentService.selectByLevel(page,name,phone,createTime,level,type,agentId);

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
        TAgent agent = tAgentService.selectTAgentByUsername(tAgent.getUsername());
        if(agent != null ){
            return  new ErrorTip(500,"添加失败!用户名已存在");
        }
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        //添加用户
        UserDto user = new UserDto();
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        //cji管理员添加费率
        if(!"agent".equals(role.getTips().substring(0,5))){
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
            tAgent.setPassword(ShiroKit.md5("123456",tAgent.getSalt()));
        }else {
            tAgent.setPassword(ShiroKit.md5(tAgent.getPassword(),tAgent.getSalt()));
        }
        tAgent.setCreateTime(new Date());
        tAgent.setUpdateTime(new Date());

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
     * 修改代理商状态
     */

   /*  @RequestMapping(value = "/statusUpdate")
    @ResponseBody
    public Object delete(@RequestParam Integer tAgentId) {
        TAgent tagent = new TAgent();
        tagent.setId(tAgentId);
        tagent.setStatus(0);
        tAgentService.deleteById(tAgentId);
        return SUCCESS_TIP;
    }*/

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
            TAgent superAgent = tAgentService.selectTAgentById(tAgent.getAgentId());
            double fee = superAgent.getFee();
            if (fee < tAgent.getFee() || tAgent.getFee() < 0) {
                return new ErrorTip(500, "添加失败!扣率必须在0-" + fee + "之间");
            }
        }else if(agent.getLevel() == 2){
            TAgent oneAgent = tAgentService.selectTAgentById(tAgent.getAgentOneId());
            double fee = oneAgent.getFee();
            if (fee < tAgent.getFee() || tAgent.getFee() < 0) {
                return new ErrorTip(500, "添加失败!扣率必须在0-" + fee + "之间");
            }
        }else if(agent.getLevel() == 3){
            TAgent twoAgent = tAgentService.selectTAgentById(tAgent.getAgentTwoId());
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
    public Map<String, Object> totle() throws Exception{
        Map<String, Object> resultMap = new HashedMap<String, Object>();
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        Integer type = null;
        if(!"agent".equals(role.getTips().substring(0,5))){
            type =0;
        }else{
            TAgent tAgent = tAgentService.selectTAgentByUId(userdto.getId());
            type=tAgent.getLevel()+1;
        }
        resultMap.put("type", type);//未提现
        return resultMap;
    }
}
