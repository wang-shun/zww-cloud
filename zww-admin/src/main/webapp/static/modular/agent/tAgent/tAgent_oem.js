/**
 * 初始化代理商管理详情对话框
 */
var TAgentInfoDlg = {
    tAgentInfoData : {}
};

/**
 * 清除数据
 */
TAgentInfoDlg.clearData = function() {
    this.tAgentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TAgentInfoDlg.set = function(key, val) {
    this.tAgentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TAgentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TAgentInfoDlg.close = function() {
    parent.layer.close(window.parent.TAgent.layerIndex);
}

/**
 * 收集数据
 */
TAgentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('code')
    .set('appid')
    .set('appsecret')
    .set('company')
    .set('url')
    .set('smsCode')
    .set('smsName')
    ;
}

/**
 * 提交进件
 */
TAgentInfoDlg.addoem = function() {
    $("#ensure").attr("disabled",true);

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tAgent/oemAdd", function(data){
        if(data.code == 200){
            Feng.success("进件成功!");
            $("#ensure").removeAttr("disabled");
            window.parent.TAgent.table.refresh();
            TAgentInfoDlg.close();
        }else{
            Feng.error(data.message);
            $("#ensure").removeAttr("disabled");
        }

    },function(data){
        Feng.error("进件失败!" + data.responseJSON.message + "!");
        $("#ensure").removeAttr("disabled");
    });
    ajax.set(this.tAgentInfoData);
    ajax.start();
}

$(function() {

});
