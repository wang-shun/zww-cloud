/**
 * 用户交易列表管理初始化
 */
var MemberChargeHistory = {
    id: "MemberChargeHistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
MemberChargeHistory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '用户id', field: 'memberId', visible: true, align: 'center', valign: 'middle'},
            {title: '用户昵称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '房间名', field: 'dollName', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                   return value.substring(0,value.indexOf("-"));
                }
            },
            {title: '机器号', field: 'machineCode', visible: true, align: 'center', valign: 'middle'},
            {title: '方式', field: 'chargeMethod', visible: true, align: 'center', valign: 'middle'},
            {title: '金币数', field: 'coins', visible: true, align: 'center', valign: 'middle'},
            {title: '加币前', field: 'coinsBefore', visible: true, align: 'center', valign: 'middle'},
            {title: '加币后', field: 'coinsAfter', visible: true, align: 'center', valign: 'middle'},
            {title: '充值时间', field: 'chargeDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
MemberChargeHistory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberChargeHistory.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加用户交易列表
 */
MemberChargeHistory.openAddMemberChargeHistory = function () {
    var index = layer.open({
        type: 2,
        title: '添加用户交易列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberChargeHistory/memberChargeHistory_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看用户交易列表详情
 */
MemberChargeHistory.openMemberChargeHistoryDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '用户交易列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberChargeHistory/memberChargeHistory_update/' + MemberChargeHistory.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户交易列表
 */
MemberChargeHistory.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/memberChargeHistory/delete", function (data) {
            Feng.success("删除成功!");
            MemberChargeHistory.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("memberChargeHistoryId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询用户交易列表列表
 */
MemberChargeHistory.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['machineCode'] = $("#machineCode").val();
    queryData['memberId'] = $("#memberId").val();
    queryData['chargeDate'] = $("#chargeDate").val();
    MemberChargeHistory.table.refresh({query: queryData});
};

//jquery实现回车键触发事件
$(document).keyup(function(event){
    if(event.keyCode ==13){
        MemberChargeHistory.search();
    }
});

$(function () {
    var defaultColunms = MemberChargeHistory.initColumn();
    var table = new BSTable(MemberChargeHistory.id, "/memberChargeHistory/list", defaultColunms);
    table.setPaginationType("server");
    MemberChargeHistory.table = table.init();
});
