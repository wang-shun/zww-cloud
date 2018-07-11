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
 * 提现
 */
AgentWithdrawInfoDlg.Withdraw = function() {
    $("#ensure").attr("disabled",true);
    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/agentWithdraw/withdraw", function(data){
        if(data.code == 200 ){
            Feng.success("提现成功!请等待财务审批");
            $("#ensure").removeAttr("disabled");
            window.parent.location.reload();
            window.parent.AgentWithdraw.table.refresh();
            AgentWithdrawInfoDlg.close();
        }else{
            Feng.error(data.message);
            $("#ensure").removeAttr("disabled");
        }
    },function(data){
        Feng.error("提现失败!" + data.responseJSON.message + "!");
        $("#ensure").removeAttr("disabled");
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
        if(data.code == 200 ){
            Feng.success("修改成功!");
            window.parent.AgentWithdraw.table.refresh();
            AgentWithdrawInfoDlg.close();
        }else{
            Feng.error(data.message);
        }
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.agentWithdrawInfoData);
    ajax.start();
}

$(function() {
    $.post(Feng.ctxPath + "/agentWithdraw/getWithdrawBankInfo",function(result){
        if(result.code == 200){
            var list = result.list;
            for(var i=0;i<list.length;i++){
                var pa = list[i];
                if(pa.status ==1){
                    $("#id").append("<option value='"+pa.id+"'>"+pa.cardBank + "(****" +pa.cardNo.substring(pa.cardNo.length-4,pa.cardNo)+")</option>");
                }
            }
        }else{
            Feng.error(result.msg);
            window.parent.AgentWithdraw.table.refresh();
            AgentWithdrawInfoDlg.close();
        }
    });
});
