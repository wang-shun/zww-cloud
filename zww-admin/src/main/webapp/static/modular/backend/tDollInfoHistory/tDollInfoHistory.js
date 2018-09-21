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
            {title: '娃娃名', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃编码', field: 'dollCode', visible: true, align: 'center', valign: 'middle'},
            {title: '存入数', field: 'dollTotal', visible: true, align: 'center', valign: 'middle'},
            {title: '存入前数量', field: 'numStart', visible: true, align: 'center', valign: 'middle'},
            {title: '存入后数量', field: 'numEnd', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'imgUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img  src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '单价(元)', field: 'price', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return (value*0.01).toFixed(2);
                }
            },
            {title: '备注', field: 'note', visible: true, align: 'center', valign: 'middle'},
            {title: '操作人', field: 'modifyName', visible: true, align: 'center', valign: 'middle'},
            {title: '进货时间', field: 'stockDateStr', visible: true, align: 'center', valign: 'middle'},
            {title: '修改时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
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
    if($("#dollCode").val() == null){
        table.setPaginationType("server");
        TDollInfoHistory.table = table.init();
    }else{
        var queryData = {};
        queryData['dollCode'] = $("#dollCode").val();
        queryData['dollName'] = $("#dollName").val();
        table.setQueryParams(queryData);
        TDollInfoHistory.table = table.init();
    }
});
