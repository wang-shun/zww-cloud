/**
 * 初始化代理商分润详情对话框
 */
var AgentChargeInfoDlg = {
    agentChargeInfoData : {}
};

/**
 * 清除数据
 */
AgentChargeInfoDlg.clearData = function() {
    this.agentChargeInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AgentChargeInfoDlg.set = function(key, val) {
    this.agentChargeInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AgentChargeInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AgentChargeInfoDlg.close = function() {
    parent.layer.close(window.parent.AgentCharge.layerIndex);
}

/**
 * 收集数据
 */
AgentChargeInfoDlg.collectData = function() {
    this
    .set('id')
    .set('orderId')
    .set('agentSuperId')
    .set('agentOneId')
    .set('agentTwoId')
    .set('agentThreeId')
    .set('agentSuperFee')
    .set('agentOneFee')
    .set('agentTwoFee')
    .set('agentThreeFee')
    .set('agentSuperIncome')
    .set('agentOneIncome')
    .set('agentTwoIncome')
    .set('agentThreeIncome')
    .set('updateTime')
    .set('createTime')
    ;
}

/**
 * 提交添加
 */
AgentChargeInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/agentCharge/add", function(data){
        Feng.success("添加成功!");
        window.parent.AgentCharge.table.refresh();
        AgentChargeInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.agentChargeInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AgentChargeInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/agentCharge/update", function(data){
        Feng.success("修改成功!");
        window.parent.AgentCharge.table.refresh();
        AgentChargeInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.agentChargeInfoData);
    ajax.start();
}

$(function() {

});
