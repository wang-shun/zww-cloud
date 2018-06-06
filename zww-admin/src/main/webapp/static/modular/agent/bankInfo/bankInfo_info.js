/**
 * 初始化银行卡管理详情对话框
 */
var BankInfoInfoDlg = {
    bankInfoInfoData : {}
};

/**
 * 清除数据
 */
BankInfoInfoDlg.clearData = function() {
    this.bankInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BankInfoInfoDlg.set = function(key, val) {
    this.bankInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BankInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BankInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.BankInfo.layerIndex);
}

/**
 * 收集数据
 */
BankInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('agentId')
    .set('cardBank')
    .set('cardSubBank')
    .set('cardProvince')
    .set('cardCity')
    .set('cardArea')
    .set('cardBankNo')
    .set('cardNo')
    .set('idCardNo')
    .set('name')
    .set('phone')
    .set('idCardPicturePos')
    .set('idCardPictureRev')
    .set('idCardPicture')
    .set('bankPicturePos')
    .set('createTime')
    ;
}

/**
 * 提交添加
 */
BankInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.BankInfo.table.refresh();
        BankInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BankInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.BankInfo.table.refresh();
        BankInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankInfoInfoData);
    ajax.start();
}

$(function() {

});
