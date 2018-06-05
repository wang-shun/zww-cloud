package com.stylefeng.guns.modular.agent.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
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

            EntityWrapper<TAgent> entityWrapper = new EntityWrapper<>();
            Wrapper<TAgent> wrapper =  entityWrapper.like("username",condition);
            List<Map<String, Object>> tagent = tAgentService.selectMaps(wrapper);
            return new TagentWarpper(tagent).warp();

    }

    /**
     * 新增代理商管理
     */

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(TAgent tAgent) {
        User userdto =(User) ShiroKit.getSession().getAttribute("userL");
        Role role = roleMapper.selectId(Integer.valueOf(userdto.getRoleid()));
        //添加用户
        UserDto user = new UserDto();

        if("admin".equals(role.getTips())){
            tAgent.setLevel(0);
            user.setRoleid("2");
        }else{
            TAgent tAgent1 =tAgentService.selectLevelById(userdto.getId());
            Integer level = tAgent1.getLevel();
            switch(level){
                case 0: tAgent.setLevel(level+1);
                    user.setRoleid("3");
                break;
                case 1: tAgent.setLevel(level+1);
                    user.setRoleid("4");
                break;
                case 2: tAgent.setLevel(level+1);
                    user.setRoleid("5");
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
