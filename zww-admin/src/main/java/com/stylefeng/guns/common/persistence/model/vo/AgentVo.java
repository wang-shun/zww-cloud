package com.stylefeng.guns.common.persistence.model.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.stylefeng.guns.common.persistence.model.TAgent;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.util.Date;
@ExcelTarget("agent")
public class AgentVo {

    @Excel(name = "编号", orderNum = "1", mergeVertical = true, isImportField = "id")
    private Integer id;
    /**
     * 用户名
     */
    @Excel(name = "登录名", orderNum = "2", mergeVertical = true, isImportField = "username",width = 15.0D)
    private String username;

    /**
     * 真实姓名
     */
    @Excel(name = "真实姓名", orderNum = "3", mergeVertical = true, isImportField = "nickName",width = 15.0D)
    private String nickName;

    /**
     * 手机号
     */
    @Excel(name = "手机号", orderNum = "4", mergeVertical = true, isImportField = "phone",width = 15.0D)
    private String phone;

    /**
     * 代理等级（0特级 1 ， 2 ,  3）
     */
    @Excel(name = "代理等级", orderNum = "5", mergeVertical = true, isImportField = "level",width = 15.0D)
    private String level;
    /**
     * 状态 (1正常 ，2冻结，3失效)
     */
    @Excel(name = "状态", orderNum = "6", mergeVertical = true, isImportField = "status",width = 15.0D)
    private String status;

    /**
     * 费率
     */
    @Excel(name = "费率", orderNum = "7", mergeVertical = true, isImportField = "fee",width = 15.0D)
    private Double fee;
    /**
     * 余额
     */
    @Excel(name = "余额", orderNum = "8", mergeVertical = true, isImportField = "balance",width = 15.0D)
    private Double balance;


    /**
     * 是否是贴牌商（0不是   1是）
     */
    @Excel(name = "是否为贴牌商", orderNum = "9", mergeVertical = true, isImportField = "isOem",width = 15.0D)
    private String isOem;
    /**
     * 特级代理id
     */
    @Excel(name = "特级代理", orderNum = "10", mergeVertical = true, isImportField = "agentName",width = 15.0D)
    private String agentName;
    /**
     * 一级代理id
     */
    @Excel(name = "一级代理", orderNum = "11", mergeVertical = true, isImportField = "agentOneName",width = 15.0D)
    private String agentOneName;
    /**
     * 二级代理id
     */
    @Excel(name = "二级代理", orderNum = "12", mergeVertical = true, isImportField = "agentTwoName",width = 15.0D)
    private String agentTwoName;
    /**
     * 创建时间
     */
    @Excel(name = "注册时间", orderNum = "13", mergeVertical = true, isImportField = "createTime",exportFormat = "yyyy-MM-dd HH:mm:ss",width = 16.0D)
    private Date createTime;


    public AgentVo(TAgent tAgent,TAgent agentSuper,TAgent agentOne,TAgent agentTwo) {
        this.id = tAgent.getId();
        this.username = tAgent.getUsername();
        this.nickName = tAgent.getNickName();
        this.phone = tAgent.getPhone();
        if(tAgent.getLevel() == 0){
            this.level = "特级代理商";
        }else if(tAgent.getLevel() == 1){
            this.level = "一级代理商";
        }else if(tAgent.getLevel() == 2){
            this.level = "二级代理商";
        }else{
            this.level = "三级代理商";
        }
        if(tAgent.getStatus() == 1){
            this.status = "正常";
        }else if(tAgent.getLevel() == 2){
            this.status = "冻结";
        }else{
            this.status = "失效";
        }
        this.fee = tAgent.getFee();
        this.balance = tAgent.getBalance()*0.01;
        this.isOem = tAgent.getIsOem() ? "是" : "不是";
        this.agentName = agentSuper == null ? "-" : agentSuper.getNickName();
        this.agentOneName = agentOne == null ? "-" : agentOne.getNickName();
        this.agentTwoName = agentTwo == null ? "-" : agentTwo.getNickName();
        this.createTime = tAgent.getCreateTime();
    }

    public AgentVo() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getIsOem() {
        return isOem;
    }

    public void setIsOem(String isOem) {
        this.isOem = isOem;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentOneName() {
        return agentOneName;
    }

    public void setAgentOneName(String agentOneName) {
        this.agentOneName = agentOneName;
    }

    public String getAgentTwoName() {
        return agentTwoName;
    }

    public void setAgentTwoName(String agentTwoName) {
        this.agentTwoName = agentTwoName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
