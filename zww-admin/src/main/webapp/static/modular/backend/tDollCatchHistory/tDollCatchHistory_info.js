/**
 * 初始化娃娃机抓取记录详情对话框
 */
var TDollCatchHistoryInfoDlg = {
    tDollCatchHistoryInfoData : {}
};

/**
 * 清除数据
 */
TDollCatchHistoryInfoDlg.clearData = function() {
    this.tDollCatchHistoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TDollCatchHistoryInfoDlg.set = function(key, val) {
    this.tDollCatchHistoryInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TDollCatchHistoryInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TDollCatchHistoryInfoDlg.close = function() {
    parent.layer.close(window.parent.TDollCatchHistory.layerIndex);
}

/**
 * 收集数据
 */
TDollCatchHistoryInfoDlg.collectData = function() {
    this
    .set('id')
    .set('catchStatus')
    ;
}

/**
 * 提交添加
 */
/*
TDollCatchHistoryInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tDollCatchHistory/add", function(data){
        Feng.success("添加成功!");
        window.parent.TDollCatchHistory.table.refresh();
        TDollCatchHistoryInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tDollCatchHistoryInfoData);
    ajax.start();
}
*/

/**
 * 提交修改
 */
TDollCatchHistoryInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tDollCatchHistory/update", function(data){
        if(data.code == 200){
            Feng.success("修改成功!");
            window.parent.TDollCatchHistory.table.refresh();
            TDollCatchHistoryInfoDlg.close();
        }else{
            Feng.error(data.message);
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tDollCatchHistoryInfoData);
    ajax.start();
}

$(function() {
    var catchStatusValue = $("#catchStatusValue").val();
    if(catchStatusValue == null || catchStatusValue == "null" || catchStatusValue == undefined){
        $("#catchStatus").val("");
    }else{
        $("#catchStatus").val(catchStatusValue);
    }
});
