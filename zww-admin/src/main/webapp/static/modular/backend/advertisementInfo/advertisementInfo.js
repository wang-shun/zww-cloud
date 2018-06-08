/**
 * 文案管理管理初始化
 */
var AdvertisementInfo = {
    id: "AdvertisementInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
AdvertisementInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            //{title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '图片', field: 'imgUrl', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    return '<a class="maincolor" href="javascript:;"><img  src="'+value+'" width="80" class="img-rounded" /></a>';
                }
            },
            {title: '下载次数', field: 'downCount', visible: true, align: 'center', valign: 'middle'},
            {title: 'x轴', field: 'xAxis', visible: true, align: 'center', valign: 'middle'},
            {title: 'y轴', field: 'yAxis', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '修改时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
AdvertisementInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        AdvertisementInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加文案管理
 */
AdvertisementInfo.openAddAdvertisementInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加文案管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/advertisementInfo/advertisementInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看文案管理详情
 */
AdvertisementInfo.openAdvertisementInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '文案管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/advertisementInfo/advertisementInfo_update/' + AdvertisementInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除文案管理
 */
AdvertisementInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/advertisementInfo/delete", function (data) {
            Feng.success("删除成功!");
            AdvertisementInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("advertisementInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询文案管理列表
 */
AdvertisementInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    AdvertisementInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AdvertisementInfo.initColumn();
    var table = new BSTable(AdvertisementInfo.id, "/advertisementInfo/list", defaultColunms);
    table.setPaginationType("client");
    AdvertisementInfo.table = table.init();
});
