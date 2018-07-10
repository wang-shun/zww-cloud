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
TAgent.initColumn = function (type) {
    var column = new Array();
    column.push({field: 'selectItem', radio: true});
    column.push({title: '昵称', field: 'nickName', visible: true, align: 'center', valign: 'middle'});
    column.push({title: '登录名', field: 'username', visible: true, align: 'center', valign: 'middle'});
    column.push({title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'});
    column.push({title: '代理等级', field: 'levelName', visible: true, align: 'center', valign: 'middle'});
    column.push({title: '费率', field: 'fee', visible: true, align: 'center', valign: 'middle'});
    if(type == 0){
        column.push({title: '余额(元)', field: 'balance', visible: true, align: 'center', valign: 'middle'});
        column.push({title: '特级代理', field: 'agentName', visible: true, align: 'center', valign: 'middle'});
    }else if(type == 1){
        column.push({title: '一级代理', field: 'agentOneName', visible: true, align: 'center', valign: 'middle'});
    }else if(type == 2){
        column.push({title: '二级代理', field: 'agentTwoName', visible: true, align: 'center', valign: 'middle'});
    }
    column.push({title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'});
    column.push({title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'});
    if(type == 0){
        column.push({title: '是否为OEM', field: 'oem', visible: true, align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                if(value){ return "是"; }else{ return "不是"; }
            }});
        column.push({
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (value, row, index) {
                if(row.level == 0){
                    return '<button type="button" class="btn btn-primary button-margin" style="margin-left: 9px !important" onclick="TAgent.OEM(' + row.id + ')">O单</button>';
                }else{
                    return '-';
                }
            }
        });
    }
    return column;
};

TAgent.OEM = function (id) {
        var index = layer.open({
            type: 2,
            title: '添加O单材料',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tAgent/oemPage/' + id
        });
        this.layerIndex = index;
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
            title: '代理商管理修改',
            area: ['500px', '500px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tAgent/tAgent_update/' + TAgent.seItem.id
        });
        this.layerIndex = index;
    }
};

TAgent.resetSearch = function () {
    $("#name").val("");
    $("#username").val("");
    $("#phone").val("");
    $("#createTime").val("");
    $("#condition").val("");
    $("#level").val("");
    TAgent.search();
}

TAgent.execl = function (v_this) {
    $(v_this).attr("disabled","true");
    setTimeout(function(){
        $(v_this).removeAttr("disabled");
    },5000);
    $("#names").val($("#name").val());
    $("#usernames").val($("#username").val());
    $("#phones").val($("#phone").val());
    $("#createTimes").val($("#createTime").val());
    $("#levels").val($("#level").val());
    $("#test").submit();

}

/**
 * 查询代理商管理列表
 */
TAgent.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['username'] = $("#username").val();
    queryData['phone'] = $("#phone").val();
    queryData['createTime'] = $("#createTime").val();
    queryData['level'] = $("#level").val();
    TAgent.table.refresh({query: queryData});

}
$(function () {
    $.post(Feng.ctxPath + "/tAgent/getlavel",function(result){
        var html = "<option value=''>全部</option>",type = result.type;
        if(type == 0){
            html += " <option value='0'>特级代理</option><option value=\"1\">一级代理</option><option value=\"2\">二级代理</option><option value=\"3\">三级代理</option>";
        }else if(type == 1){
            html += "<option value='1'>一级代理</option><option value=\"2\">二级代理</option><option value=\"3\">三级代理</option>";
        }else if(type == 2){
            html += "<option value='2'>二级代理</option><option value=\"3\">三级代理</option>";
        }else if(type == 3){
            html += "<option value='3'>三级代理</option>";
        }
        $("#level").html(html);
        var defaultColunms = TAgent.initColumn(type);
        var table = new BSTable(TAgent.id, "/tAgent/list", defaultColunms);
        table.setPaginationType("server");
        TAgent.table = table.init();
    });
});
