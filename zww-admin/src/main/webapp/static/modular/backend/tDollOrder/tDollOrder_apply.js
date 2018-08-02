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
            {title: '娃娃名称', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃头像', field: 'dollUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '姓名', field: 'addrName', visible: true, align: 'center', valign: 'middle'},
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
 * 揽件
 */
TDollOrder.order_apply = function () {
    if (this.check()) {
        var list = $('#' + this.id).bootstrapTable('getSelections'),ids="";
        for (var i=0;i<list.length;i++){
            ids += list[i].id;
            if(i != list.length-1){
                ids += ",";
            }
        }
        var ajax = new $ax(Feng.ctxPath + "/tDollOrder/order_apply", function (data) {
            Feng.success("揽件成功!");
            TDollOrder.table.refresh();
        }, function (data) {
            Feng.error("揽件失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tDollOrderIds",ids);
        ajax.start();
    }
};

/**
 * 查询申请发货列表列表
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
    var table = new BSTable(TDollOrder.id, "/tDollOrder/applylist", defaultColunms);
    table.setPaginationType("server");
    TDollOrder.table = table.init();
});
