package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.BussinessLog;
import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.dictmap.UserDict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.persistence.dao.RoleMapper;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.TAgent;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.aliyun.AliyunService;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.Tip;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.db.Db;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.StringUtils;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.agent.service.ITAgentService;
import com.stylefeng.guns.modular.backend.form.ChangePwdForm;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.factory.UserFactory;
import com.stylefeng.guns.modular.system.transfer.UserDto;
import com.stylefeng.guns.modular.system.warpper.UserWarpper;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统管理员控制器
 *
 * @author bruce
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/mgr")
public class UserMgrController extends BaseController {

    private static String PREFIX = "/system/user/";

    @Resource
    private GunsProperties gunsProperties;

    @Resource
    private UserMgrDao managerDao;

    @Resource
    private UserMapper userMapper;
    
    @Autowired
    AliyunService aliyunService;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private ITAgentService tAgentService;

    /**
     * 跳转到查看管理员列表的页面
     */
    @RequestMapping("")
     
    public String index() {
        return PREFIX + "user.html";
    }

    /**
     * 跳转到查看管理员列表的页面
     */
     
    @RequestMapping("/user_add")
    public String addView() {
        return PREFIX + "user_add.html";
    }

    /**
     * 跳转到角色分配页面
     */
    //@RequiresPermissions("/mgr/role_assign")  //利用shiro自带的权限检查
     
    @Permission
    @RequestMapping("/role_assign/{userId}")
    public String roleAssign(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = (User) Db.create(UserMapper.class).selectOneByCon("id", userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userAccount", user.getAccount());
        return PREFIX + "user_roleassign.html";
    }

    /**
     * 跳转到编辑管理员页面
     */
     
    @Permission
    @RequestMapping("/user_edit/{userId}")
    public String userEdit(@PathVariable Integer userId, Model model) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userMapper.selectById(userId);
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_edit.html";
    }

    /**
     * 跳转到查看用户详情页面
     */
     
    @RequestMapping("/user_info")
    public String userInfo(Model model) {
        Integer userId = ShiroKit.getUser().getId();
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        User user = this.userMapper.selectById(userId);
        Role role = roleMapper.selectId(Integer.valueOf(user.getRoleid()));
        model.addAttribute("rate", "-");
        if("agent".equals(role.getTips().substring(0,5))){
            TAgent tAgent = tAgentService.selectTAgentByUId(user.getId());
            if(tAgent !=null){
                model.addAttribute("rate", tAgent.getFee());
            }
        }
        model.addAttribute(user);
        model.addAttribute("roleName", ConstantFactory.me().getRoleName(user.getRoleid()));
        model.addAttribute("deptName", ConstantFactory.me().getDeptName(user.getDeptid()));
        LogObjectHolder.me().set(user);
        return PREFIX + "user_view.html";
    }

    /**
     * 跳转到修改密码界面
     */
    @RequestMapping("/user_chpwd")
    public String chPwd() {
        return PREFIX + "user_chpwd.html";
    }

    /**
     * 修改当前用户的密码
     */
     
    @RequestMapping("/changePwd")
    @ResponseBody
    public Object changePwd(@Valid ChangePwdForm changePwdForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new GunsException(HttpStatus.SC_BAD_REQUEST, bindingResult.getFieldError().getDefaultMessage());
        }

        if (!changePwdForm.getNewPwd().equals(changePwdForm.getRePwd())) {
            throw new GunsException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroKit.getUser().getId();
        User user = userMapper.selectById(userId);
        String oldMd5 = ShiroKit.md5(changePwdForm.getOldPwd(), user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(changePwdForm.getNewPwd(), user.getSalt());
            user.setPassword(newMd5);
            user.updateById();

            Role role = roleMapper.selectId(Integer.valueOf(user.getRoleid()));
            if("agent".equals(role.getTips().substring(0,5))){
                TAgent tAgent = tAgentService.selectTAgentByUId(user.getId());
                if(tAgent != null){
                    tAgent.setUpdateTime(new Date());
                    tAgent.setPassword(newMd5);
                    tAgentService.updateById(tAgent);
                }
            }

            return SUCCESS_TIP;
        } else {
            throw new GunsException(BizExceptionEnum.OLD_PWD_NOT_RIGHT);
        }
    }

    /**
     * 查询管理员列表
     */
     
    @RequestMapping("/list")
    @Permission
    @ResponseBody
    public Object list(@RequestParam(required = false) String name, @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) Integer deptid) {
        DataScope dataScope =null;
        if (!ShiroKit.isAdmin()) {
            dataScope = new DataScope(ShiroKit.getDeptDataScope());
        }
        List<Map<String, Object>> users = managerDao.selectUsers(dataScope, name, beginTime, endTime, deptid);
        return new UserWarpper(users).warp();
    }

    /**
     * 添加管理员
     */
     
    @RequestMapping("/add")
    @BussinessLog(value = "添加管理员", key = "account", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip add(@Valid UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }

        // 判断账号是否重复
        User theUser = managerDao.getByAccount(user.getAccount());
        if (theUser != null) {
            throw new GunsException(BizExceptionEnum.USER_ALREADY_REG);
        }

        // 完善账号信息
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(user.getPassword(), user.getSalt()));
        user.setStatus(ManagerStatus.OK.getCode());
        user.setCreatetime(new Date());
        user.setRoleid("18");
        this.userMapper.insert(UserFactory.createUser(user));
        return SUCCESS_TIP;
    }

    /**
     * 修改管理员
     *
     * @throws NoPermissionException
     */
     
    @RequestMapping("/edit")
    @BussinessLog(value = "修改管理员", key = "account", dict = UserDict.class)
    @ResponseBody
    public Tip edit(@Valid UserDto user, BindingResult result) throws NoPermissionException {
        if (result.hasErrors()) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        if (ShiroKit.hasRole(Const.ADMIN_NAME)) {
            this.userMapper.updateById(UserFactory.createUser(user));
            return SUCCESS_TIP;
        } else {
            assertAuth(user.getId());
            ShiroUser shiroUser = ShiroKit.getUser();
            if (shiroUser.getId().equals(user.getId())) {
                this.userMapper.updateById(UserFactory.createUser(user));

                Role role = roleMapper.selectId(shiroUser.getRoleList().get(0));
                if("agent".equals(role.getTips().substring(0,5))){
                    TAgent tAgent = tAgentService.selectTAgentByUId(user.getId());
                    if(tAgent != null){
                        tAgent.setUpdateTime(new Date());
                        tAgent.setNickName(user.getName());
                        tAgent.setPhone(user.getPhone());
                        tAgentService.updateById(tAgent);
                    }
                }

                return SUCCESS_TIP;
            } else {
                throw new GunsException(BizExceptionEnum.NO_PERMITION);
            }
        }
    }

    /**
     * 删除管理员（逻辑删除）
     */
     
    @RequestMapping("/delete")
    @BussinessLog(value = "删除管理员", key = "userId", dict = UserDict.class)
    @Permission
    @ResponseBody
    public Tip delete(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能删除超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_DELETE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.DELETED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 查看管理员详情
     */
     
    @RequestMapping("/view/{userId}")
    @ResponseBody
    public User view(@PathVariable Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        return this.userMapper.selectById(userId);
    }

    /**
     * 重置管理员的密码
     */
     
    @RequestMapping("/reset")
    @BussinessLog(value = "重置管理员密码", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip reset(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        User user = this.userMapper.selectById(userId);
        user.setSalt(ShiroKit.getRandomSalt(5));
        user.setPassword(ShiroKit.md5(Const.DEFAULT_PWD, user.getSalt()));
        this.userMapper.updateById(user);
        return SUCCESS_TIP;
    }

    /**
     * 冻结用户
     */
     
    @RequestMapping("/freeze")
    @BussinessLog(value = "冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip freeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能冻结超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_FREEZE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.FREEZED.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 解除冻结用户
     */
     
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "解除冻结用户", key = "userId", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip unfreeze(@RequestParam Integer userId) {
        if (ToolUtil.isEmpty(userId)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        assertAuth(userId);
        this.managerDao.setStatus(userId, ManagerStatus.OK.getCode());
        return SUCCESS_TIP;
    }

    /**
     * 分配角色
     */
     
    @RequestMapping("/setRole")
    @BussinessLog(value = "分配角色", key = "userId,roleIds", dict = UserDict.class)
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Tip setRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") String roleIds) {
        if (ToolUtil.isOneEmpty(userId, roleIds)) {
            throw new GunsException(BizExceptionEnum.REQUEST_NULL);
        }
        //不能修改超级管理员
        if (userId.equals(Const.ADMIN_ID)) {
            throw new GunsException(BizExceptionEnum.CANT_CHANGE_ADMIN);
        }
        assertAuth(userId);
        this.managerDao.setRoles(userId, roleIds);
        return SUCCESS_TIP;
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
        //String pictureName = UUID.randomUUID().toString() + ".jpg";
       // try {
       //     String fileSavePath = gunsProperties.getFileUploadPath();
       //     picture.transferTo(new File(fileSavePath + pictureName));
        } catch (Exception e) {
            throw new GunsException(BizExceptionEnum.UPLOAD_ERROR);
        }
        return newFileUrl;
    }

    /**
     * 判断当前登录的用户是否有操作这个用户的权限
     */
     
    private void assertAuth(Integer userId) {
        if (ShiroKit.isAdmin()) {
            return;
        }
        List<Integer> deptDataScope = ShiroKit.getDeptDataScope();
        User user = this.userMapper.selectById(userId);
        Integer deptid = user.getDeptid();
        if (deptDataScope.contains(deptid)) {
            return;
        } else {
            throw new GunsException(BizExceptionEnum.NO_PERMITION);
        }

    }
}
