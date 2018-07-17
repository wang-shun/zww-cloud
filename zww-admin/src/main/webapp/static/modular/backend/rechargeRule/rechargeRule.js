/**
 * 充值规则列表管理初始化
 */
var RechargeRule = {
    id: "RechargeRuleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
RechargeRule.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            //{title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '充值金额(元)或者抓取次数', field: 'price', visible: true, align: 'center', valign: 'middle'},
            {title: '赠送金币', field: 'coin', visible: true, align: 'center', valign: 'middle'},
            {title: '充值规则排序', field: 'sort', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createDate', visible: true, align: 'center', valign: 'middle'},
            {title: '更新时间', field: 'updateDate', visible: true, align: 'center', valign: 'middle'},
            {title: '参数规则', field: 'type', visible: true, align: 'center', valign: 'middle',
                formatter:function (value,row,index) {
                    if(value == 1){
                        return '充值';
                    }
                    if(value == 2){
                        return '抓取';
                    }
                }
            }
    ];
};

/**
 * 检查是否选中
 */
RechargeRule.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        RechargeRule.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加充值规则列表
 */
RechargeRule.openAddRechargeRule = function () {
    var index = layer.open({
        type: 2,
        title: '添加充值规则列表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/rechargeRule/rechargeRule_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看充值规则列表详情
 */
RechargeRule.openRechargeRuleDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '充值规则列表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/rechargeRule/rechargeRule_update/' + RechargeRule.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除充值规则列表
 */
RechargeRule.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/rechargeRule/delete", function (data) {
            Feng.success("删除成功!");
            RechargeRule.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("rechargeRuleId",this.seItem.id);
        ajax.start();
    }
};

$(function () {
    var defaultColunms = RechargeRule.initColumn();
    var table = new BSTable(RechargeRule.id, "/rechargeRule/list", defaultColunms);
    table.setPaginationType("client");
    RechargeRule.table = table.init();
});
