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

    @Excel(name = "特级代理id", orderNum = "5", mergeVertical = true, isImportField = "agentSuperId")
    private Integer agentSuperId;

    @Excel(name = "特级代理昵称", orderNum = "6", mergeVertical = true, isImportField = "agentSuperName",width = 15.0D)
    private String agentSuperName = "-";

    @Excel(name = "特级代理手机号", orderNum = "7", mergeVertical = true, isImportField = "agentSuperPhone",width = 15.0D)
    private String agentSuperPhone = "-";

    @Excel(name = "特级费率", orderNum = "8", mergeVertical = true, isImportField = "agentSuperFee")
    private String agentSuperFee;

    @Excel(name = "特级收入", orderNum = "9", mergeVertical = true, isImportField = "agentSuperIncome")
    private String agentSuperIncome;

    @Excel(name = "一级代理id", orderNum = "10", mergeVertical = true, isImportField = "agentOneId")
    private Integer agentOneId;

    @Excel(name = "一级代理昵称", orderNum = "11", mergeVertical = true, isImportField = "agentOneName",width = 15.0D)
    private String agentOneName = "-";

    @Excel(name = "一级代理手机号", orderNum = "12", mergeVertical = true, isImportField = "agentOnePhone",width = 15.0D)
    private String agentOnePhone = "-";

    @Excel(name = "一级费率", orderNum = "13", mergeVertical = true, isImportField = "agentOneFee")
    private String agentOneFee;

    @Excel(name = "一级收入", orderNum = "14", mergeVertical = true, isImportField = "agentOneIncome")
    private String agentOneIncome;

    @Excel(name = "二级代理id", orderNum = "15", mergeVertical = true, isImportField = "agentTwoId")
    private Integer agentTwoId;

    @Excel(name = "二级代理昵称", orderNum = "16", mergeVertical = true, isImportField = "agentTwoName",width = 15.0D)
    private String agentTwoName = "-";

    @Excel(name = "二级代理手机号", orderNum = "17", mergeVertical = true, isImportField = "agentTwoPhone",width = 15.0D)
    private String agentTwoPhone = "-";

    @Excel(name = "二级费率", orderNum = "18", mergeVertical = true, isImportField = "agentTwoFee")
    private String agentTwoFee;

    @Excel(name = "二级收入", orderNum = "19", mergeVertical = true, isImportField = "agentTwoIncome")
    private String agentTwoIncome;

    @Excel(name = "三级代理id", orderNum = "20", mergeVertical = true, isImportField = "agentThreeId")
    private Integer agentThreeId;

    @Excel(name = "三级代理昵称", orderNum = "21", mergeVertical = true, isImportField = "agentThreeName",width = 15.0D)
    private String agentThreeName = "-";

    @Excel(name = "三级代理手机号", orderNum = "22", mergeVertical = true, isImportField = "agentThreePhone",width = 15.0D)
    private String agentThreePhone = "-";

    @Excel(name = "三级费率", orderNum = "23", mergeVertical = true, isImportField = "agentThreeFee")
    private String agentThreeFee;

    @Excel(name = "三级收入", orderNum = "24", mergeVertical = true, isImportField = "agentThreeIncome")
    private String agentThreeIncome;

    public AgentChargeVo() {
    }

    public AgentChargeVo(AgentChargeVo agentChargeVo, TAgent agentSuper,TAgent agentOne,TAgent agentTwo,TAgent agentThree) {
        this.chargeName = agentChargeVo.getChargeName();
        this.price = agentChargeVo.getPrice();
        this.memberName = agentChargeVo.getMemberName();
        this.createDate = agentChargeVo.getCreateDate();
        this.agentSuperId = agentChargeVo.getAgentSuperId();
        if(agentSuper != null){
            this.agentSuperName = agentSuper.getNickName();
            this.agentSuperPhone = agentSuper.getPhone();
        }
        this.agentSuperFee = agentChargeVo.getAgentSuperFee();
        this.agentSuperIncome = agentChargeVo.getAgentSuperIncome();
        this.agentOneId = agentChargeVo.getAgentOneId();
        if(agentOne != null){
            this.agentOneName = agentOne.getNickName();
            this.agentOnePhone = agentOne.getPhone();
        }
        this.agentOneFee = agentChargeVo.getAgentOneFee();
        this.agentOneIncome = agentChargeVo.getAgentOneIncome();
        this.agentTwoId = agentChargeVo.getAgentTwoId();
        if(agentTwo != null){
            this.agentTwoName = agentTwo.getNickName();
            this.agentTwoPhone = agentTwo.getPhone();
        }
        this.agentTwoFee = agentChargeVo.getAgentTwoFee();
        this.agentTwoIncome = agentChargeVo.getAgentTwoIncome();
        this.agentThreeId = agentChargeVo.getAgentThreeId();
        if(agentThree != null){
            this.agentThreeName = agentThree.getNickName();
            this.agentThreePhone = agentThree.getPhone();
        }
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

    public String getAgentSuperFee() {
        return agentSuperFee;
    }

    public void setAgentSuperFee(String agentSuperFee) {
        this.agentSuperFee = agentSuperFee;
    }

    public String getAgentSuperIncome() {
        return agentSuperIncome;
    }

    public void setAgentSuperIncome(String agentSuperIncome) {
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

    public String getAgentOneFee() {
        return agentOneFee;
    }

    public void setAgentOneFee(String agentOneFee) {
        this.agentOneFee = agentOneFee;
    }

    public String getAgentOneIncome() {
        return agentOneIncome;
    }

    public void setAgentOneIncome(String agentOneIncome) {
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

    public String getAgentTwoFee() {
        return agentTwoFee;
    }

    public void setAgentTwoFee(String agentTwoFee) {
        this.agentTwoFee = agentTwoFee;
    }

    public String getAgentTwoIncome() {
        return agentTwoIncome;
    }

    public void setAgentTwoIncome(String agentTwoIncome) {
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

    public String getAgentThreeFee() {
        return agentThreeFee;
    }

    public void setAgentThreeFee(String agentThreeFee) {
        this.agentThreeFee = agentThreeFee;
    }

    public String getAgentThreeIncome() {
        return agentThreeIncome;
    }

    public void setAgentThreeIncome(String agentThreeIncome) {
        this.agentThreeIncome = agentThreeIncome;
    }

    public String getAgentSuperPhone() {
        return agentSuperPhone;
    }

    public void setAgentSuperPhone(String agentSuperPhone) {
        this.agentSuperPhone = agentSuperPhone;
    }

    public String getAgentOnePhone() {
        return agentOnePhone;
    }

    public void setAgentOnePhone(String agentOnePhone) {
        this.agentOnePhone = agentOnePhone;
    }

    public String getAgentTwoPhone() {
        return agentTwoPhone;
    }

    public void setAgentTwoPhone(String agentTwoPhone) {
        this.agentTwoPhone = agentTwoPhone;
    }

    public String getAgentThreePhone() {
        return agentThreePhone;
    }

    public void setAgentThreePhone(String agentThreePhone) {
        this.agentThreePhone = agentThreePhone;
    }
}
