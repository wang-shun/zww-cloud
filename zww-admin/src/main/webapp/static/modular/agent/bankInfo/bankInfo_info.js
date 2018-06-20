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
BankInfoInfoDlg.onclicks = function () {
    var word = $("#testmain").val();
    if(word == ""){
        alert("请输入审核失败理由");
        return;
    }
    this.set("message",word);
    BankInfoInfoDlg.updStatus(2);
}
BankInfoInfoDlg.showMessage = function () {
    $("#layui-layer1").show();
    return;
}

/**
 * 提交审核
 */
BankInfoInfoDlg.updStatus = function(status) {

    this.set('id');
    this.set("status",status);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/bankInfo/update", function(data){
         Feng.success("成功!");
         window.parent.BankInfo.table.refresh();
        BankInfoInfoDlg.close();
    },function(data){
        Feng.error("失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.bankInfoInfoData);
    ajax.start();
}

$(function() {
    var status = BankInfoInfoDlg.get("status");
    if(status != 1){
         $("#btnDiv").show();
    }
    var num1 = 0,num2 = 0,num3 = 0,num4 = 0;
    $("#idCardPicturePos").click(function () {
        num1 ++;
        $(this).rotate(90*num1);
    });
    $("#idCardPictureRev").click(function () {
        num2 ++;
        $(this).rotate(90*num2);
    });
    $("#idCardPicture").click(function () {
        num3 ++;
        $(this).rotate(90*num3);
    });
    $("#bankPicturePos").click(function () {
        num4 ++;
        $(this).rotate(90*num4);
    });
    $("#idCardPicturePos").dblclick(function () {
        $(this).toggleClass("minidCardPicturePos");
        $(this).toggleClass("maxidCardPicturePos");
    });
    $("#idCardPictureRev").dblclick(function () {
        $(this).toggleClass("minidCardPictureRev");
        $(this).toggleClass("maxidCardPictureRev");
    });
    $("#idCardPicture").dblclick(function () {
        $(this).toggleClass("minidCardPicture");
        $(this).toggleClass("maxidCardPicture");
    });
    $("#bankPicturePos").dblclick(function () {
        $(this).toggleClass("minbankPicturePos");
        $(this).toggleClass("maxbankPicturePos");
    });
});
