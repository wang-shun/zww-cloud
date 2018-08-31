/**
 * 待发货列表管理初始化
 */
var TDollOrder = {
    id: "TDollOrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TDollOrder.initColumn = function () {
    return [
        {field: 'selectItem', checkbox: true},
            {title: '用户昵称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃名称', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃头像', field: 'dollUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '姓名', field: 'addrName', visible: true, align: 'center', valign: 'middle',width:60},
            {title: '电话', field: 'addrPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '收货地址', field: 'address', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return row.province+row.city+row.county+row.street;
                }
            },
            {title: '申请发货时间', field: 'modifiedDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TDollOrder.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TDollOrder.seItem = selected[0];
        return true;
    }
};


/**
 * 订单详情
 */
TDollOrder.goodsDetail = function (title,url,orderUrl,w,h) {
    url = url+"?orderNum="+orderUrl;
    layer_show(title,url,800,400);

}
TDollOrder.execl = function (v_this) {
    $("#phoneForm").val($("#phone").val());
    $("#addrNameForm").val($("#addrName").val());
    $("#idForm").val($("#id").val());
    $("#dollNameForm").val($("#dollName").val());
    $(v_this).attr("disabled","true");
    setTimeout(function(){
        $(v_this).removeAttr("disabled");
    },10000);
    $("#test").submit();
};

/**
 * 点击添加待发货列表
 */
TDollOrder.openAddTDollOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加待发货列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tDollOrder/tDollOrder_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看待发货列表详情
 */
TDollOrder.openTDollOrderDetail = function () {
    if (this.check()) {
        var list = $('#' + this.id).bootstrapTable('getSelections'),ids="";
        for (var i=0;i<list.length;i++){
            ids += list[i].id;
            if(i != list.length-1){
                ids += ",";
            }
        }
        var index = layer.open({
            type: 2,
            title: '待发货列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
           // content: Feng.ctxPath + '/tDollOrder/tDollOrder_update/' + TDollOrder.seItem.id
            content: Feng.ctxPath + '/tDollOrder/tDollOrder_update/' + ids
        });
        this.layerIndex = index;
    }
};

/**
 * 删除待发货列表
 */
TDollOrder.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tDollOrder/delete", function (data) {
            Feng.success("删除成功!");
            TDollOrder.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tDollOrderId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询待发货列表列表
 */
TDollOrder.search = function () {
    var queryData = {};
    queryData['id'] = $("#id").val();
    queryData['phone'] = $("#phone").val();
    queryData['addrName'] = $("#addrName").val();
    queryData['dollName'] = $("#dollName").val();
    TDollOrder.table.refresh({query: queryData});
};

//jquery实现回车键触发事件
$(document).keyup(function(event){
    if(event.keyCode ==13){
        TDollOrder.search();
    }
});


$(function () {
    var defaultColunms = TDollOrder.initColumn();
    var table = new BSTable(TDollOrder.id, "/tDollOrder/list", defaultColunms);
    table.setPaginationType("server");
    TDollOrder.table = table.init();
});
