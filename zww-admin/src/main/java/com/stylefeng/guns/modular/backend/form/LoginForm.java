package com.stylefeng.guns.modular.backend.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by moying on 2018/6/26.
 */


public class LoginForm {

    @NotEmpty(message = "用户名不能为空")
    @Pattern(regexp = "^1[3456789]\\d{9}", message = "用户名格式不规范")
    private String username;


    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "^[0-9a-zA-Z]{6,16}$", message = "密码只能是英文字符或者数字")
    private String password;

    private String remember;

    @NotEmpty(message = "验证码不能为空")
    private String kaptcha;

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
