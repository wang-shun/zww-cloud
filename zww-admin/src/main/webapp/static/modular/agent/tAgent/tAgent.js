/**
 * 代理商管理管理初始化
 */
var TAgent = {
    id: "TAgentTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TAgent.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '用户名', field: 'username', visible: false, align: 'center', valign: 'middle'},
        {title: '密码', field: 'password', visible: false, align: 'center', valign: 'middle'},

        {title: '真实姓名', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '代理等级', field: 'levelName', visible: true, align: 'center', valign: 'middle'},
       /* {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},*/
        {title: '费率', field: 'fee', visible: true, align: 'center', valign: 'middle'},
        {title: '余额', field: 'balance', visible: true, align: 'center', valign: 'middle'},
        {title: '特级代理', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
        {title: '一级代理', field: 'agentOneName', visible: true, align: 'center', valign: 'middle'},
        {title: '二级代理', field: 'agentTwoName', visible: true, align: 'center', valign: 'middle'},
        {title: '最后修改时间', field: 'updateTime', visible: false, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true}
    ];
};

/**
 * 检查是否选中
 */
TAgent.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TAgent.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加代理商管理
 */
TAgent.openAddTAgent = function () {
    var index = layer.open({
        type: 2,
        title: '添加代理商管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tAgent/tAgent_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看代理商管理详情
 */
TAgent.openTAgentDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '代理商管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tAgent/tAgent_update/' + TAgent.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除代理商管理
 */
TAgent.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tAgent/delete", function (data) {
            Feng.success("删除成功!");
            TAgent.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tAgentId",this.seItem.id);
        ajax.start();
    }
};

TAgent.resetSearch = function () {
    $("#phone").val("");
    $("#createTime").val("");
    $("#condition").val("");
    $("#level").val("全部");

    TAgent.search();
}

/**
 * 查询代理商管理列表
 */
TAgent.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    queryData['phone'] = $("#phone").val();
    queryData['createTime'] = $("#createTime").val();
    queryData['level'] = $("#level").val();
    TAgent.table.refresh({query: queryData});

}

    $(function () {
    var defaultColunms = TAgent.initColumn();
    var table = new BSTable(TAgent.id, "/tAgent/list", defaultColunms);
    table.setPaginationType("server");
    TAgent.table = table.init();
});
