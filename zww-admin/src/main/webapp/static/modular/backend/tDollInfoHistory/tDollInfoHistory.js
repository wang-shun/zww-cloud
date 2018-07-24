/**
 * 库存记录管理初始化
 */
var TDollInfoHistory = {
    id: "TDollInfoHistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TDollInfoHistory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            //{title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '房间名', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃的识别码', field: 'dollCode', visible: true, align: 'center', valign: 'middle'},
            {title: '库存数', field: 'dollTotal', visible: true, align: 'center', valign: 'middle'},
            {title: '存入前数量', field: 'numStart', visible: true, align: 'center', valign: 'middle'},
            {title: '存入后数量', field: 'numEnd', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'imgUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img  src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '发货地', field: 'agency', visible: true, align: 'center', valign: 'middle'},
            {title: '尺寸', field: 'size', visible: true, align: 'center', valign: 'middle'},
            {title: '材质', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '备注', field: 'note', visible: true, align: 'center', valign: 'middle'},
            {title: '操作人', field: 'modifyer', visible: true, align: 'center', valign: 'middle'},
            {title: '进货时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 查询库存记录列表
 */
TDollInfoHistory.search = function () {
    var queryData = {};
    queryData['dollCode'] = $("#dollCode").val();
    queryData['dollName'] = $("#dollName").val();
    TDollInfoHistory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TDollInfoHistory.initColumn();
    var table = new BSTable(TDollInfoHistory.id, "/tDollInfoHistory/list", defaultColunms);
    table.setPaginationType("server");
    TDollInfoHistory.table = table.init();
});
