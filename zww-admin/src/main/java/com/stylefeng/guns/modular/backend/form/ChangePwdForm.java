package com.stylefeng.guns.modular.backend.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by moying on 2018/6/26.
 */
public class ChangePwdForm {

    @NotEmpty(message = "旧密码不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,16}$", message = "旧密码只能是英文字符或者数字")
    private String oldPwd;


    @NotEmpty(message = "新密码不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,16}$", message = "新密码只能是英文字符或者数字")
    private String newPwd;


    @NotEmpty(message = "确认密码不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,16}$", message = "确认密码只能是英文字符或者数字")
    private String rePwd;

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getRePwd() {
        return rePwd;
    }

    public void setRePwd(String rePwd) {
        this.rePwd = rePwd;
    }
}
