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
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '订单ID', field: 'orderId', visible: true, align: 'center', valign: 'middle'},
            {title: '特级代理', field: 'agentSuperId', visible: true, align: 'center', valign: 'middle'},
            {title: '一级代理', field: 'agentOneId', visible: true, align: 'center', valign: 'middle'},
            {title: '二级代理', field: 'agentTwoId', visible: true, align: 'center', valign: 'middle'},
            {title: '三级代理', field: 'agentThreeId', visible: true, align: 'center', valign: 'middle'},
            {title: '特级费率', field: 'agentSuperFee', visible: true, align: 'center', valign: 'middle'},
            {title: '一级费率', field: 'agentOneFee', visible: true, align: 'center', valign: 'middle'},
            {title: '二级费率', field: 'agentTwoFee', visible: true, align: 'center', valign: 'middle'},
            {title: '三级费率', field: 'agentThreeFee', visible: true, align: 'center', valign: 'middle'},
            {title: '特级收入', field: 'agentSuperIncome', visible: true, align: 'center', valign: 'middle'},
            {title: '一级收入', field: 'agentOneIncome', visible: true, align: 'center', valign: 'middle'},
            {title: '二级收入', field: 'agentTwoIncome', visible: true, align: 'center', valign: 'middle'},
            {title: '三级收入', field: 'agentThreeIncome', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'updateTime', visible: true, align: 'center', valign: 'middle'},
            {title: '', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '(0未清算1已清算)', field: 'status', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AgentCharge.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AgentCharge.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加代理商分润
 */
AgentCharge.openAddAgentCharge = function () {
    var index = layer.open({
        type: 2,
        title: '添加代理商分润',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/agentCharge/agentCharge_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看代理商分润详情
 */
AgentCharge.openAgentChargeDetail = function () {
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
};

/**
 * 删除代理商分润
 */
AgentCharge.delete = function () {
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
};

/**
 * 查询代理商分润列表
 */
AgentCharge.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AgentCharge.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AgentCharge.initColumn();
    var table = new BSTable(AgentCharge.id, "/agentCharge/list", defaultColunms);
    table.setPaginationType("client");
    AgentCharge.table = table.init();
});
