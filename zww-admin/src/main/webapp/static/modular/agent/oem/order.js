/**
 * 未审批管理初始化
 */
var ORDER = {
    id: "OrderTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ORDER.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单号', field: 'orderNo', visible: true, align: 'center', valign: 'middle'},
        {title: '礼包名称', field: 'chargeName', visible: true, align: 'center', valign: 'middle'},
        {title: '充值金额', field: 'price', visible: true, align: 'center', valign: 'middle'},
        {title: '用户标识', field: 'uid', visible: true, align: 'center', valign: 'middle'},
        {title: '用户昵称', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
        {title: '订单状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
        {title: '完成时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
    ];
};
/**
 * 查询未审批列表
 */
ORDER.search = function () {
    var queryData = {};
    queryData['orderNo'] = $("#orderNo").val();
    queryData['memberName'] = $("#memberName").val();
    queryData['uid'] = $("#uid").val();
    queryData['status'] = $("#status").val();
    queryData['startDate'] = $("#startDate").val();
    queryData['endDate'] = $("#endDate").val();
    ORDER.table.refresh({query: queryData});
};
$(function () {
    var defaultColunms = ORDER.initColumn();
    var table = new BSTable(ORDER.id, "/oem/orderlist", defaultColunms);
    table.setPaginationType("server");
    ORDER.table = table.init();
});
