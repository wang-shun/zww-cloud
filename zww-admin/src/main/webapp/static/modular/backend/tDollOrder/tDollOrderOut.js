/**
 * 待发货列表管理初始化
 */
var TDollOrderOut = {
    id: "TDollOrderOutTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TDollOrderOut.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '用户昵称', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '收件姓名', field: 'addrName', visible: true, align: 'center', valign: 'middle'},
            {title: '电话', field: 'addrPhone', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃名称', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '数量', field: 'quantity', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃头像', field: 'dollUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '收货地址', field: 'address', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return row.province+row.city+row.county+row.street;
                }
            },
            {title: '快递公司', field: 'deliverMethod', visible: true, align: 'center', valign: 'middle'},
            {title: '快递单号', field: 'deliverNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '快递价格', field: 'deliverAmount', visible: true, align: 'center', valign: 'middle'},
            {title: '申请发货时间', field: 'modifiedDate', visible: true, align: 'center', valign: 'middle'},
            {title: '发货时间', field: 'deliverDate', visible: true, align: 'center', valign: 'middle'}
    ];
};


/**
 * 检查是否选中
 */
TDollOrderOut.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TDollOrderOut.seItem = selected[0];
        return true;
    }
};


/**
 * 查询待发货列表列表
 */
TDollOrderOut.search = function () {
    var queryData = {};
    queryData['id'] = $("#id").val();
    queryData['phone'] = $("#phone").val();
    queryData['addrName'] = $("#addrName").val();
    queryData['dollName'] = $("#dollName").val();
    TDollOrderOut.table.refresh({query: queryData});
};

//jquery实现回车键触发事件
$(document).keyup(function(event){
    if(event.keyCode ==13){
        TDollOrderOut.search();
    }
});


$(function () {
    var defaultColunms = TDollOrderOut.initColumn();
    var table = new BSTable(TDollOrderOut.id, "/tDollOrder/outList", defaultColunms);
    table.setPaginationType("server");
    TDollOrderOut.table = table.init();
});
