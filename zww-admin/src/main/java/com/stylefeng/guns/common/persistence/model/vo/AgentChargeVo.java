package com.stylefeng.guns.common.persistence.model.vo;

import com.stylefeng.guns.common.persistence.model.TAgent;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;

import java.util.Date;

@ExcelTarget("agentCharge")
public class AgentChargeVo {

    @Excel(name = "充值规则名称", orderNum = "1", mergeVertical = true, isImportField = "chargeName",width = 15.0D)
    private String chargeName;

    @Excel(name = "充值金额", orderNum = "2", mergeVertical = true, isImportField = "price")
    private Double price;

    @Excel(name = "充值用户名称", orderNum = "3", mergeVertical = true, isImportField = "memberName",width = 35.0D)
    private String memberName;

    @Excel(name = "订单创建时间", orderNum = "4", mergeVertical = true, isImportField = "createDate",width = 25.0D,exportFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private Integer agentSuperId;

    @Excel(name = "特级代理", orderNum = "5", mergeVertical = true, isImportField = "agentSuperName",width = 15.0D)
    private String agentSuperName;

    @Excel(name = "特级费率", orderNum = "6", mergeVertical = true, isImportField = "agentSuperFee")
    private Double agentSuperFee;

    @Excel(name = "特级收入", orderNum = "7", mergeVertical = true, isImportField = "agentSuperIncome")
    private Double agentSuperIncome;

    private Integer agentOneId;

    @Excel(name = "一级代理", orderNum = "8", mergeVertical = true, isImportField = "agentOneName",width = 15.0D)
    private String agentOneName;

    @Excel(name = "一级费率", orderNum = "9", mergeVertical = true, isImportField = "agentOneFee")
    private Double agentOneFee;

    @Excel(name = "一级收入", orderNum = "10", mergeVertical = true, isImportField = "agentOneIncome")
    private Double agentOneIncome;

    private Integer agentTwoId;

    @Excel(name = "二级代理", orderNum = "11", mergeVertical = true, isImportField = "agentTwoName",width = 15.0D)
    private String agentTwoName;

    @Excel(name = "二级费率", orderNum = "12", mergeVertical = true, isImportField = "agentTwoFee")
    private Double agentTwoFee;

    @Excel(name = "二级收入", orderNum = "13", mergeVertical = true, isImportField = "agentTwoIncome")
    private Double agentTwoIncome;

    private Integer agentThreeId;

    @Excel(name = "三级代理", orderNum = "14", mergeVertical = true, isImportField = "agentThreeName",width = 15.0D)
    private String agentThreeName;

    @Excel(name = "三级费率", orderNum = "15", mergeVertical = true, isImportField = "agentThreeFee")
    private Double agentThreeFee;

    @Excel(name = "三级收入", orderNum = "16", mergeVertical = true, isImportField = "agentThreeIncome")
    private Double agentThreeIncome;

    public AgentChargeVo() {
    }

    public AgentChargeVo(AgentChargeVo agentChargeVo, TAgent agentSuper,TAgent agentOne,TAgent agentTwo,TAgent agentThree) {
        this.chargeName = agentChargeVo.getChargeName();
        this.price = agentChargeVo.getPrice();
        this.memberName = agentChargeVo.getMemberName();
        this.createDate = agentChargeVo.getCreateDate();
        this.agentSuperName = agentSuper == null ? "-" : agentSuper.getNickName();
        this.agentSuperFee = agentChargeVo.getAgentSuperFee();
        this.agentSuperIncome = agentChargeVo.getAgentSuperIncome();
        this.agentOneName = agentOne == null ? "-" : agentOne.getNickName();
        this.agentOneFee = agentChargeVo.getAgentOneFee();
        this.agentOneIncome = agentChargeVo.getAgentOneIncome();
        this.agentTwoName = agentTwo == null ? "-" : agentTwo.getNickName();
        this.agentTwoFee = agentChargeVo.getAgentTwoFee();
        this.agentTwoIncome = agentChargeVo.getAgentTwoIncome();
        this.agentThreeName = agentThree == null ? "-" : agentThree.getNickName();
        this.agentThreeFee = agentChargeVo.getAgentThreeFee();
        this.agentThreeIncome = agentChargeVo.getAgentThreeIncome();
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAgentSuperId() {
        return agentSuperId;
    }

    public void setAgentSuperId(Integer agentSuperId) {
        this.agentSuperId = agentSuperId;
    }

    public String getAgentSuperName() {
        return agentSuperName;
    }

    public void setAgentSuperName(String agentSuperName) {
        this.agentSuperName = agentSuperName;
    }

    public Double getAgentSuperFee() {
        return agentSuperFee;
    }

    public void setAgentSuperFee(Double agentSuperFee) {
        this.agentSuperFee = agentSuperFee;
    }

    public Double getAgentSuperIncome() {
        return agentSuperIncome;
    }

    public void setAgentSuperIncome(Double agentSuperIncome) {
        this.agentSuperIncome = agentSuperIncome;
    }

    public Integer getAgentOneId() {
        return agentOneId;
    }

    public void setAgentOneId(Integer agentOneId) {
        this.agentOneId = agentOneId;
    }

    public String getAgentOneName() {
        return agentOneName;
    }

    public void setAgentOneName(String agentOneName) {
        this.agentOneName = agentOneName;
    }

    public Double getAgentOneFee() {
        return agentOneFee;
    }

    public void setAgentOneFee(Double agentOneFee) {
        this.agentOneFee = agentOneFee;
    }

    public Double getAgentOneIncome() {
        return agentOneIncome;
    }

    public void setAgentOneIncome(Double agentOneIncome) {
        this.agentOneIncome = agentOneIncome;
    }

    public Integer getAgentTwoId() {
        return agentTwoId;
    }

    public void setAgentTwoId(Integer agentTwoId) {
        this.agentTwoId = agentTwoId;
    }

    public String getAgentTwoName() {
        return agentTwoName;
    }

    public void setAgentTwoName(String agentTwoName) {
        this.agentTwoName = agentTwoName;
    }

    public Double getAgentTwoFee() {
        return agentTwoFee;
    }

    public void setAgentTwoFee(Double agentTwoFee) {
        this.agentTwoFee = agentTwoFee;
    }

    public Double getAgentTwoIncome() {
        return agentTwoIncome;
    }

    public void setAgentTwoIncome(Double agentTwoIncome) {
        this.agentTwoIncome = agentTwoIncome;
    }

    public Integer getAgentThreeId() {
        return agentThreeId;
    }

    public void setAgentThreeId(Integer agentThreeId) {
        this.agentThreeId = agentThreeId;
    }

    public String getAgentThreeName() {
        return agentThreeName;
    }

    public void setAgentThreeName(String agentThreeName) {
        this.agentThreeName = agentThreeName;
    }

    public Double getAgentThreeFee() {
        return agentThreeFee;
    }

    public void setAgentThreeFee(Double agentThreeFee) {
        this.agentThreeFee = agentThreeFee;
    }

    public Double getAgentThreeIncome() {
        return agentThreeIncome;
    }

    public void setAgentThreeIncome(Double agentThreeIncome) {
        this.agentThreeIncome = agentThreeIncome;
    }
}
