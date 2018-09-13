/**
 * 初始化未审批详情对话框
 */
var OEMInfoDlg = {
    OEMInfoData : {}
};

/**
 * 清除数据
 */
OEMInfoDlg.clearData = function() {
    this.OEMInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OEMInfoDlg.set = function(key, val) {
    this.OEMInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
OEMInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
OEMInfoDlg.collectData = function() {
    this
    .set('id')
    .set('callbackUrl')
    ;
}

/**
 * 提交修改
 */
OEMInfoDlg.editSubmit = function() {
    $("#ensure").attr("disabled",true);
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/oem/update", function(data){
        if(data.code == 200 ){
            Feng.success("修改成功!");
            $("#ensure").removeAttr("disabled");
        }else{
            Feng.error(data.message);
            $("#ensure").removeAttr("disabled");
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
        $("#ensure").removeAttr("disabled");
    });
    ajax.set(this.OEMInfoData);
    ajax.start();
}

$(function() {

});
