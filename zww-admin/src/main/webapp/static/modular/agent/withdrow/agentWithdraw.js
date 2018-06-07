/**
 * 已审批管理初始化
 */
var AgentWithdraw = {
    id: "AgentWithdrawTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AgentWithdraw.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
      //  {title: '交易流水号', field: 'tradeNo', visible: true, align: 'center', valign: 'middle'},
        {title: '提现金额(元)', field: 'amount', visible: true, align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                return value/100;
            }},
        {title: '手续费(元)', field: 'fee', visible: true, align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                return value/100;
            }},
        {title: '到账金额(元)', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                return value/100;
            }},
        {title: '真实姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'idCardNo', visible: true, align: 'center', valign: 'middle'},
        {title: '到账卡号', field: 'cardNo', visible: true, align: 'center', valign: 'middle'},
        {title: '提现状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
        {title: '审批备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
        {title: '确认时间', field: 'confirmDate', visible: true, align: 'center', valign: 'middle'}
        //{title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AgentWithdraw.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AgentWithdraw.seItem = selected[0];
        return true;
    }
};
AgentWithdraw.total = function () {
    $.post(Feng.ctxPath + "/agentWithdraw/totle",function(result){
           $("#undischarged").html(result.undischarged/100);//未清算
           $("#notPutForward").html(result.notPutForward/100);//未提现
           $("#alreadyPresented").html(result.alreadyPresented/100);//已提现
    });
};
/**
 * 点击提现
 */
AgentWithdraw.Withdraw = function () {
    var index = layer.open({
        type: 2,
        title: '提现',
        area: ['500px', '320px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/agentWithdraw/withdrawPage'
    });
    this.layerIndex = index;
};

/**
 * 打开查看已审批详情
 */
/*AgentWithdraw.openAgentWithdrawDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '已审批详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/agentWithdraw/agentWithdraw_update/' + AgentWithdraw.seItem.id
        });
        this.layerIndex = index;
    }
};*/

/**
 * 删除已审批
 */
/*AgentWithdraw.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/agentWithdraw/delete", function (data) {
            Feng.success("删除成功!");
            AgentWithdraw.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("agentWithdrawId",this.seItem.id);
        ajax.start();
    }
};*/

/**
 * 查询已审批列表
 */
AgentWithdraw.search = function () {
    var queryData = {};
    queryData['status'] = $("#status").val();
    queryData['createDate'] = $("#createDate").val();
    AgentWithdraw.table.refresh({query: queryData});
};

$(function () {
    AgentWithdraw.total();
    var defaultColunms = AgentWithdraw.initColumn();
    var table = new BSTable(AgentWithdraw.id, "/agentWithdraw/list2", defaultColunms);
    table.setPaginationType("server");
    AgentWithdraw.table = table.init();
});
