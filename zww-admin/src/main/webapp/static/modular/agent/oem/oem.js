/**
 * 未审批管理初始化
 */
var OEM = {
    id: "OemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
OEM.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'OEM名称', field: 'name', visible: true, align: 'center', valign: 'middle'},
        {title: '简称', field: 'code', visible: true, align: 'center', valign: 'middle'},
        {title: 'appid', field: 'appid', visible: true, align: 'center', valign: 'middle'},
        {title: 'appsecret', field: 'appsecret', visible: true, align: 'center', valign: 'middle'},
        {title: '主体', field: 'company', visible: true, align: 'center', valign: 'middle'},
        {title: '域名', field: 'url', visible: true, align: 'center', valign: 'middle'},
        {title: '短信模板id', field: 'smsCode', visible: true, align: 'center', valign: 'middle'},
        {title: '短信签名', field: 'smsName', visible: true, align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', visible: true, align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
            if(value == 0){
                return "禁用";
            }else{
                return "启用";
            }
        }},
        {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
OEM.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        OEM.seItem = selected[0];
        return true;
    }
};

OEM.update = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: 'OEM修改',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/oem/oem_upd/' + OEM.seItem.id
        });
        this.layerIndex = index;
    }
};

$(function () {
    var defaultColunms = OEM.initColumn();
    var table = new BSTable(OEM.id, "/oem/list", defaultColunms);
    table.setPaginationType("client");
    OEM.table = table.init();
});
