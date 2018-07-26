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
           // {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃名', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '库存数', field: 'dollTotal', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'imgUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<img  src="'+value+'" width="80" class="img-rounded" />';
                }
            },
            {title: '娃娃编号', field: 'dollCode', visible: true, align: 'center', valign: 'middle'},
           /* {title: '发货地', field: 'agency', visible: true, align: 'center', valign: 'middle'},
            {title: '尺寸', field: 'size', visible: true, align: 'center', valign: 'middle'},
            {title: '材质', field: 'type', visible: true, align: 'center', valign: 'middle'},*/
            {title: '备注', field: 'note', visible: true, align: 'center', valign: 'middle'},
           // {title: '返币数', field: 'redeemCoins', visible: true, align: 'center', valign: 'middle'},
            {title: '娃娃成本(元)', field: 'dollCoins', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return value*0.01;
                }
            },
            {title: '快递费(元)', field: 'deliverCoins', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return value*0.01;
                }
            },
            {title: '进货时间', field: 'addTime', visible: true, align: 'center', valign: 'middle'}
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
        area: ['100%', '100%'], //宽高
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
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tDollInfo/tDollInfo_update/' + TDollInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除库存管理
 */
TDollInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/tDollInfo/delete", function (data) {
            Feng.success("删除成功!");
            TDollInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("tDollInfoId",this.seItem.id);
        ajax.start();
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
