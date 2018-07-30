/**
 * 库存管理管理初始化
 */
var TDollInfo = {
    id: "TDollInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TDollInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '娃娃名', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃编码', field: 'dollCode', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'imgUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img  src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '库存数', field: 'dollTotal', visible: true, align: 'center', valign: 'middle'},
            {title: '成本单价(元)', field: 'dollCoins', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return (value*0.01).toFixed(2);
                }
            },
            {title: '修改时间', field: 'addTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TDollInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TDollInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加库存管理
 */
TDollInfo.openAddTDollInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加库存管理',
        area: ['1089px', '608px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tDollInfo/tDollInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看库存管理详情
 */
TDollInfo.openTDollInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '库存管理详情',
            area: ['1089px', '608px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tDollInfo/tDollInfo_update/' + TDollInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 库存管理详情
 */
TDollInfo.detail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '库存管理详情',
            area: ['1089px', '608px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tDollInfo/tDollInfo_detail/' + TDollInfo.seItem.dollCode
        });
        this.layerIndex = index;
    }
};

/**
 * 查询库存管理列表
 */
TDollInfo.search = function () {
    var queryData = {};
    queryData['dollCode'] = $("#dollCode").val();
    queryData['dollName'] = $("#dollName").val();
    TDollInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TDollInfo.initColumn();
    var table = new BSTable(TDollInfo.id, "/tDollInfo/list", defaultColunms);
    table.setPaginationType("server");
    TDollInfo.table = table.init();
});
