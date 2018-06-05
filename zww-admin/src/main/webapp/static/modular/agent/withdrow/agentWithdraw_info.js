/**
 * 初始化已审批详情对话框
 */
var AgentWithdrawInfoDlg = {
    agentWithdrawInfoData : {}
};

/**
 * 清除数据
 */
AgentWithdrawInfoDlg.clearData = function() {
    this.agentWithdrawInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AgentWithdrawInfoDlg.set = function(key, val) {
    this.agentWithdrawInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AgentWithdrawInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AgentWithdrawInfoDlg.close = function() {
    parent.layer.close(window.parent.AgentWithdraw.layerIndex);
}

/**
 * 收集数据
 */
AgentWithdrawInfoDlg.collectData = function() {
    this
    .set('id')
    .set('tradeNo')
    .set('agentId')
    .set('amount')
    .set('fee')
    .set('actualAmount')
    .set('name')
    .set('phone')
    .set('idCardNo')
    .set('cardNo')
    .set('remark')
    .set('status')
    .set('createDate')
    .set('confirmDate')
    ;
}

/**
 * 提交添加
 */
AgentWithdrawInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/agentWithdraw/add", function(data){
        Feng.success("添加成功!");
        window.parent.AgentWithdraw.table.refresh();
        AgentWithdrawInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.agentWithdrawInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AgentWithdrawInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/agentWithdraw/update", function(data){
        Feng.success("修改成功!");
        window.parent.AgentWithdraw.table.refresh();
        AgentWithdrawInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.agentWithdrawInfoData);
    ajax.start();
}

$(function() {

});
