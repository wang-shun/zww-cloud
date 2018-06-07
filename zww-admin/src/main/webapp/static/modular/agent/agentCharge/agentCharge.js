/**
 * 代理商分润管理初始化
 */
var AgentCharge = {
    id: "AgentChargeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AgentCharge.initColumn = function () {
    return [
        {field: 'selectItem', radio: false},
            {title: '消费者', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
            {title: '推荐人', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
            {title: '充值金额(元)', field: 'amount', visible: true, align: 'center', valign: 'middle'},
            {title: '收益(元)', field: 'agentIncome', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return value/100;
                }},
            {title: '清算状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
/*AgentCharge.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AgentCharge.seItem = selected[0];
        return true;
    }
};*/

/**
 * 点击添加代理商分润
 */
/*AgentCharge.openAddAgentCharge = function () {
    var index = layer.open({
        type: 2,
        title: '添加代理商分润',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/agentCharge/agentCharge_add'
    });
    this.layerIndex = index;
};*/

/**
 * 打开查看代理商分润详情
 */
/*AgentCharge.openAgentChargeDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '代理商分润详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/agentCharge/agentCharge_update/' + AgentCharge.seItem.id
        });
        this.layerIndex = index;
    }
};*/

/**
 * 删除代理商分润
 */
/*AgentCharge.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/agentCharge/delete", function (data) {
            Feng.success("删除成功!");
            AgentCharge.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("agentChargeId",this.seItem.id);
        ajax.start();
    }
};*/

/**
 * 查询代理商分润列表
 */
/*AgentCharge.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AgentCharge.table.refresh({query: queryData});
};*/
AgentCharge.total = function () {
    $.post(Feng.ctxPath + "/agentCharge/totle",function(result){
        $("#yesterdayProfit").html(result.yesterdayProfit/100);//昨日分润
        $("#NotProfit").html(result.NotProfit/100);//未结算分润
        $("#profit").html(result.profit/100);//已结算分润
    });
};
$(function () {
    AgentCharge.total();
    var defaultColunms = AgentCharge.initColumn();
    var table = new BSTable(AgentCharge.id, "/agentCharge/list", defaultColunms);
    table.setPaginationType("server");
    AgentCharge.table = table.init();
});
