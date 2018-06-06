package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.transfer.UserDto;
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
        return PREFIX + "tAgent_add.html";
    }

    /**
     * 跳转到修改代理商管理
     */

    @RequestMapping("/tAgent_update/{tAgentId}")
    public String tAgentUpdate(@PathVariable Integer tAgentId, Model model) {
        TAgent tAgent = tAgentService.selectById(tAgentId);
        model.addAttribute("item",tAgent);
        LogObjectHolder.me().set(tAgent);
        return PREFIX + "tAgent_edit.html";
    }

    /**
     * 获取代理商管理列表
     */

    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam(required = false) String condition, @RequestParam(required = false) String phone, @RequestParam(required = false) String createTime, @RequestParam(required = false) Integer level) {
        Page<TAgent> page = new PageFactory<TAgent>().defaultPage();
        //设置查询的条件level,id
        Integer _level = null;
        Integer _id = null;
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        if(ToolUtil.isEmpty(level)){
            Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
            if("administrator".equals(role.getTips())){

            }else{
                Map map =tAgentService.getById(userdto.getId());
                Integer levels =Integer.valueOf(map.get("level").toString());
                _id = Integer.valueOf(map.get("id").toString());
                switch(levels){
                    case 0: _level = 0;
                        break;
                    case 1:_level = 1;
                        break;
                    case 2: _level = 2;
                        break;
                    case 3:throw new GunsException(BizExceptionEnum.REQUEST_NULL);
                }
            }
        }else {
            _level = level;
        }
        List<Map<String, Object>>  result= tAgentService.selectByLevel(page,condition,phone,createTime,_level,_id);
        page.setRecords((List<TAgent>)new TagentWarpper(result).warp());
        return super.packForBT(page);

    }


    //判断费率
    private BigDecimal GetFee(TAgent tAgent, String clod){
        //费率为空时
        if(ToolUtil.isEmpty(tAgent.getFee())){
            String _value = tAgentService.selectByValue(clod);
            return new  BigDecimal(_value);
        }else {
            User userdto =(User) ShiroKit.getSession().getAttribute("userL");
            Map map =tAgentService.getById(userdto.getId());
            int _fee =Integer.valueOf(map.get("fee").toString());
            int fee =tAgent.getFee().intValue();
            if(_fee >= fee){
                switch(_fee*10){
                    case  4: if(fee>=0&&fee<=0.4 ){ return new BigDecimal(fee);}
                        break;
                    case  3:if(fee>=0&&fee<=0.3 ){ return new BigDecimal(fee);}
                        break;
                    case  2:if(fee>=0&&fee<=0.2 ){ return new BigDecimal(fee);}
                        break;
                }
                return new BigDecimal(fee);
            }else {
                throw new GunsException(BizExceptionEnum.REQUEST_INVALIDATE);
            }
        }
    }

    /**
     * 新增代理商管理
     */

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TAgent tAgent) {

        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        //添加用户
        UserDto user = new UserDto();
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        //cji管理员添加费率
        if("administrator".equals(role.getTips())){
            tAgent.setLevel(0);
            user.setRoleid("2");
        }else{
            Map map =tAgentService.getById(userdto.getId());
            Integer level =Integer.valueOf(map.get("level").toString());
            switch(level){
                case 0: tAgent.setLevel(level+1);
                    user.setRoleid("3");
                    tAgent.setAgentId((long)Integer.valueOf(map.get("id").toString()));
                    tAgent.setFee(this.GetFee(tAgent,"AGENT_ONE_FEE"));
                    break;
                case 1: tAgent.setLevel(level+1);
                    user.setRoleid("4");
                    tAgent.setAgentId((long)Integer.valueOf(map.get("id").toString()));
                    tAgent.setAgentOneId((long)Integer.valueOf(map.get("id").toString()));
                    tAgent.setFee(this.GetFee(tAgent,"AGENT_TWO_FEE"));
                    break;
                case 2: tAgent.setLevel(level+1);
                    user.setRoleid("5");
                    tAgent.setAgentId((long)Integer.valueOf(map.get("id").toString()));
                    tAgent.setAgentOneId((long)Integer.valueOf(map.get("id").toString()));
                    tAgent.setAgentTwoId((long)Integer.valueOf(map.get("id").toString()));
                    tAgent.setFee(this.GetFee(tAgent,"AGENT_THREE_FEE"));
                    break;
                case 3:throw new GunsException(BizExceptionEnum.REQUEST_NULL);
            }
        }


        //设置md5和盐
        if(ToolUtil.isEmpty(tAgent.getPassword())){
            tAgent.setPassword(userdto.getPassword());
            tAgent.setSalt(userdto.getSalt());
        }else {
            tAgent.setSalt(ShiroKit.getRandomSalt(5));
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
     * 删除代理商管理
     */

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer tAgentId) {
        TAgent _tagent = tAgentService.selectById(tAgentId);
        User user = managerDao.getByAccount(_tagent.getUsername());
        this.managerDao.setStatus(user.getId(), ManagerStatus.DELETED.getCode());
        tAgentService.deleteById(tAgentId);
        return SUCCESS_TIP;
    }

    /**
     * 修改代理商管理
     */

    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(TAgent tAgent) {
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
}
