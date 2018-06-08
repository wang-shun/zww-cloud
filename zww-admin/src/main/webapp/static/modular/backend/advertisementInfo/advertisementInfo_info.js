/**
 * 初始化文案管理详情对话框
 */
var AdvertisementInfoInfoDlg = {
    advertisementInfoInfoData : {}
};

/**
 * 清除数据
 */
AdvertisementInfoInfoDlg.clearData = function() {
    this.advertisementInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdvertisementInfoInfoDlg.set = function(key, val) {
    this.advertisementInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdvertisementInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
AdvertisementInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.AdvertisementInfo.layerIndex);
}

/**
 * 收集数据
 */
AdvertisementInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('title')
    .set('content')
    .set('imgUrl')
    .set('downCount')
    .set('xAxis')
    .set('yAxis')
    .set('createDate')
    ;
}

/**
 * 提交添加
 */
AdvertisementInfoInfoDlg.addSubmit = function() {
    $("#ensure").attr("disabled",true);
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/advertisementInfo/add", function(data){
        Feng.success("添加成功!");
        $("#ensure").removeAttr("disabled");
        window.parent.AdvertisementInfo.table.refresh();
        AdvertisementInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
        $("#ensure").removeAttr("disabled");
    });
    ajax.set(this.advertisementInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
AdvertisementInfoInfoDlg.editSubmit = function() {
    $("#ensure").attr("disabled",true);
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/advertisementInfo/update", function(data){
        Feng.success("修改成功!");
        $("#ensure").removeAttr("disabled");
        window.parent.AdvertisementInfo.table.refresh();
        AdvertisementInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
        $("#ensure").removeAttr("disabled");
    });
    ajax.set(this.advertisementInfoInfoData);
    ajax.start();
}

$(function() {
    // 初始化头像上传
    var avatarUp = new $WebUpload("imgUrl");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();
});
