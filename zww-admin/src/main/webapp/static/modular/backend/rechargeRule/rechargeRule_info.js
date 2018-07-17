/**
 * 初始化充值规则列表详情对话框
 */
var RechargeRuleInfoDlg = {
    rechargeRuleInfoData : {}
};

/**
 * 清除数据
 */
RechargeRuleInfoDlg.clearData = function() {
    this.rechargeRuleInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RechargeRuleInfoDlg.set = function(key, val) {
    this.rechargeRuleInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RechargeRuleInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
RechargeRuleInfoDlg.close = function() {
    parent.layer.close(window.parent.RechargeRule.layerIndex);
}

/**
 * 收集数据
 */
RechargeRuleInfoDlg.collectData = function() {
    this
    .set('id')
    .set('price')
    .set('coin')
    .set('sort')
    .set('createDate')
    .set('updateDate')
    ;
}

/**
 * 提交添加
 */
RechargeRuleInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rechargeRule/add", function(data){
        Feng.success("添加成功!");
        window.parent.RechargeRule.table.refresh();
        RechargeRuleInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rechargeRuleInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
RechargeRuleInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/rechargeRule/update", function(data){
        Feng.success("修改成功!");
        window.parent.RechargeRule.table.refresh();
        RechargeRuleInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.rechargeRuleInfoData);
    ajax.start();
}

$(function() {
    var typeValue = $("#typeValue").val();
    if(typeValue == undefined || typeValue == null){
        $("#type").val(1);
    }else{
        $("#type").val(typeValue);
    }
});
