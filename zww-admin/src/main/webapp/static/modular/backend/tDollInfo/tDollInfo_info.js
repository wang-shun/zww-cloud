/**
 * 初始化库存管理详情对话框
 */
var TDollInfoInfoDlg = {
    tDollInfoInfoData : {}
};

/**
 * 清除数据
 */
TDollInfoInfoDlg.clearData = function() {
    this.tDollInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TDollInfoInfoDlg.set = function(key, val) {
    this.tDollInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TDollInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TDollInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.TDollInfo.layerIndex);
}

/**
 * 收集数据
 */
TDollInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('dollName')
    .set('dollTotal')
    .set('imgUrl')
    .set('dollCode')
    .set('agency')
    .set('size')
    .set('type')
    .set('note')
    .set('redeemCoins')
    .set('deliverCoins')
    .set('stockDate')
    ;
    this.tDollInfoInfoData['dollCoins'] = parseInt($("#dollCoins").val() * 100);
}

/**
 * 提交添加
 */
TDollInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tDollInfo/add", function(data){
        if(data.code == 200){
            Feng.success("添加成功!");
            window.parent.TDollInfo.table.refresh();
            TDollInfoInfoDlg.close();
        }else{
            Feng.error(data.message);
        }
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tDollInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TDollInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tDollInfo/update", function(data){
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.TDollInfo.table.refresh();
            TDollInfoInfoDlg.close();
        }else{
            Feng.error(data.message);
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tDollInfoInfoData);
    ajax.start();
}

$(function() {
    // 初始化头像上传
    var avatarUp = new $WebUpload("imgUrl");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.setUploadUrl(Feng.ctxPath + '/tDollInfo/upload');
    avatarUp.setSuccessEvent(function(){
        if (window.parent.TDollInfo != undefined) {
            window.parent.TDollInfo.table.refresh();
        }
    });
    avatarUp.init();

});
