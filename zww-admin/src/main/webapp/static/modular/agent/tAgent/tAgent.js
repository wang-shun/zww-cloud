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
       // {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
       // {title: '密码', field: 'password', visible: false, align: 'center', valign: 'middle'},
        {title: '真实姓名', field: 'nickName', visible: true, align: 'center', valign: 'middle'},
        {title: '登录名', field: 'username', visible: false, align: 'center', valign: 'middle'},
        {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
        {title: '代理等级', field: 'levelName', visible: true, align: 'center', valign: 'middle'},
       /* {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle'},*/
        {title: '费率', field: 'fee', visible: true, align: 'center', valign: 'middle'},
        {title: '余额(元)', field: 'balance', visible: true, align: 'center', valign: 'middle'},
        {title: '特级代理', field: 'agentName', visible: true, align: 'center', valign: 'middle'},
        {title: '一级代理', field: 'agentOneName', visible: true, align: 'center', valign: 'middle'},
        {title: '二级代理', field: 'agentTwoName', visible: true, align: 'center', valign: 'middle'},
      //  {title: '最后修改时间', field: 'updateTime', visible: false, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'}
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
            area: ['700px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tAgent/tAgent_update/' + TAgent.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 修改代理商管理
 */
/*TAgent.statusUpdate = function () {
    if (this.check()) {
        parent.layer.confirm(tip, {
            btn: ['确定', '取消']
        }, function (index) {
            var ajax = new $ax(Feng.ctxPath + "/tAgent/statusUpdate", function (data) {
                Feng.success("状态更改成功!");
                TAgent.table.refresh();
            }, function (data) {
                Feng.error("状态更改失败!" + data.responseJSON.message + "!");
            });
            ajax.set("tAgentId",this.seItem.id);
            ajax.start();
            parent.layer.close(index);
        }, function (index) {
            parent.layer.close(index);
        });
    }
};*/

TAgent.resetSearch = function () {
    $("#phone").val("");
    $("#createTime").val("");
    $("#condition").val("");
    $("#level").val("");
    TAgent.search();
}
TAgent.level = function () {
    $.post(Feng.ctxPath + "/tAgent/getlavel",function(result){
        var html = "<option value=''>全部</option>";
       if(result.type == 0){
           html += " <option value='0'>特级代理</option><option value=\"1\">一级代理</option><option value=\"2\">二级代理</option><option value=\"3\">三级代理</option>";
       }else if(result.type == 1){
           html += "<option value='1'>一级代理</option><option value=\"2\">二级代理</option><option value=\"3\">三级代理</option>";
       }else if(result.type == 2){
           html += "<option value='2'>二级代理</option><option value=\"3\">三级代理</option>";
       }else if(result.type == 3){
           html += "<option value='3'>三级代理</option>";
       }
        $("#level").html(html);
    });
};
/**
 * 查询代理商管理列表
 */
TAgent.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['phone'] = $("#phone").val();
    queryData['createTime'] = $("#createTime").val();
    queryData['level'] = $("#level").val();
    TAgent.table.refresh({query: queryData});

}
$(function () {
    TAgent.level();
    var defaultColunms = TAgent.initColumn();
    var table = new BSTable(TAgent.id, "/tAgent/list", defaultColunms);
    table.setPaginationType("server");
    TAgent.table = table.init();
});
