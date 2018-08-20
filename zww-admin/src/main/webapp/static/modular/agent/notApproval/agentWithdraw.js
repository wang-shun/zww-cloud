/**
 * 未审批管理初始化
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
           // {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
          //  {title: '交易流水号', field: 'tradeNo', visible: true, align: 'center', valign: 'middle'},
           // {title: '代理ID', field: 'agentId', visible: true, align: 'center', valign: 'middle'},
            {title: '真实姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '提现金额', field: 'amount', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                      return value/100;
                }},
            {title: '手续费', field: 'fee', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return value/100;
                }},
            {title: '到账金额', field: 'actualAmount', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return value/100;
                }},
           // {title: '身份证号', field: 'idCardNo', visible: true, align: 'center', valign: 'middle'},
           // {title: '卡号', field: 'cardNo', visible: true, align: 'center', valign: 'middle'},
            {title: '提现状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
           // {title: '审批备注', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '提现时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
           // {title: '确认时间', field: 'confirmDate', visible: true, align: 'center', valign: 'middle'},
          //  {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (value, row, index) {
                return '<button type="button" class="btn btn-primary button-margin" style="margin-left: 9px !important" onclick="AgentWithdraw.profitHistory(this,' + row.agentId + ',\'' + row.createDate + '\')">分润记录execl</button>' +
                    '<button type="button" class="btn btn-primary button-margin" style="margin-left: 9px !important" onclick="AgentWithdraw.updateSuccess(' + row.id + ')">审批成功</button>' +
                    '<button type="button" class="btn btn-danger button-margin" style="margin-left: 9px !important" onclick="AgentWithdraw.updateFail(' + row.id + ')">审批失败</button>';
            }
        }
    ];
};
AgentWithdraw.profitHistory = function (v_this,agentId,date) {
    $(v_this).attr("disabled","true");
    console.log(date)
    setTimeout(function(){
        $(v_this).removeAttr("disabled");
    },10000);
    $("#agentIdForm").val(agentId);
    $("#dateForm").val(date);
    $("#test1").submit();
};

/**
 * 审批
 */
AgentWithdraw.updateSuccess = function (id) {
    var index = layer.open({
        type: 2,
        title: '审批成功确认',
        area: ['840px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/agentWithdraw/agentWithdraw_confirm/' +id
    });
    this.layerIndex = index;
};
AgentWithdraw.updateFail = function (id) {
    var index = layer.open({
        type: 2,
        title: '审批失败理由',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/agentWithdraw/agentWithdraw_upd/' +id
    });
    this.layerIndex = index;
};
/**
 * 检查是否选中
 */
AgentWithdraw.execl = function (v_this) {
    $(v_this).attr("disabled","true");
    setTimeout(function(){
        $(v_this).removeAttr("disabled");
    },10000);
    $("#test").submit();
};

/**
 * 查询未审批列表
 */
AgentWithdraw.search = function () {
    var queryData = {};
    queryData['status'] = 0;
    queryData['name'] = $("#name").val();
    queryData['phone'] = $("#phone").val();
    queryData['createDate'] = $("#createDate").val();
    AgentWithdraw.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AgentWithdraw.initColumn();
    var table = new BSTable(AgentWithdraw.id, "/agentWithdraw/list0", defaultColunms);
    table.setPaginationType("server");
    AgentWithdraw.table = table.init();
});
