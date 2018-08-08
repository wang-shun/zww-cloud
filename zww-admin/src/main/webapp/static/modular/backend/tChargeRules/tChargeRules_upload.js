var TChargeRulesImageDlg = {
	ImageData: {}
};
/**
 * 清除数据
 */
TChargeRulesImageDlg.clearData = function () {
    this.ImageData = {};
};
/**
 * 收集数据
 */
TChargeRulesImageDlg.collectData = function () {
    this.set('id').set('icon');
};
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TChargeRulesImageDlg.set = function (key, val) {
    this.ImageData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};
/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TChargeRulesImageDlg.get = function (key) {
    return $("#" + key).val();
};
/**
 * 关闭此对话框
 */
TChargeRulesImageDlg.close = function () {
    parent.layer.close(window.parent.TChargeRulesUpload.layerIndex);
};


$(function() {
    // 初始化头像上传
    var avatarUp = new $WebUpload("icon");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.setUploadUrl(Feng.ctxPath + '/tChargeRules/upload/'+TChargeRulesImageDlg.get("id"));
    avatarUp.setSuccessEvent(function(){
    	 if (window.parent.TChargeRules != undefined) {
             window.parent.TChargeRules.table.refresh();
         }
    });
    avatarUp.init();
});