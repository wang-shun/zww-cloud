/**
 * 银行卡管理管理初始化
 */
var BankInfo = {
    id: "BankInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BankInfo.initColumn = function () {
    return [
            {field: 'selectItem', radio: true},
            //{title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
          //  {title: '代理ID', field: 'agentId', visible: true, align: 'center', valign: 'middle'},
            {title: '姓名', field: 'name', visible: true, align: 'center', valign: 'middle'},
            {title: '身份证', field: 'idCardNo', visible: true, align: 'center', valign: 'middle'},
            {title: '手机号', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '银行名', field: 'cardBank', visible: true, align: 'center', valign: 'middle'},
            {title: '卡号', field: 'cardNo', visible: true, align: 'center', valign: 'middle'},
          //  {title: '支行名称', field: 'cardSubBank', visible: true, align: 'center', valign: 'middle'},
          //  {title: '银行省份', field: 'cardProvince', visible: true, align: 'center', valign: 'middle'},
         //   {title: '银行城市', field: 'cardCity', visible: true, align: 'center', valign: 'middle'},
        //    {title: '银行区域', field: 'cardArea', visible: true, align: 'center', valign: 'middle'},
         //   {title: '联行号', field: 'cardBankNo', visible: true, align: 'center', valign: 'middle'},
        //
         //   {title: '身份证正面', field: 'idCardPicturePos', visible: true, align: 'center', valign: 'middle'},
         //   {title: '身份证反面', field: 'idCardPictureRev', visible: true, align: 'center', valign: 'middle'},
          //  {title: '手持身份证', field: 'idCardPicture', visible: true, align: 'center', valign: 'middle'},
         //  {title: '银行卡正面', field: 'bankPicturePos', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'statusName', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
          //  {title: '修改时间', field: 'updateTime', visible: true, align: 'center', valign: 'middle'},
        {
            title: '操作', visible: true, align: 'center', valign: 'middle', formatter: function (value, row, index) {
                if(row.status == 0){
                    return '<button type="button" class="btn btn-primary button-margin" style="margin-left: 9px !important" onclick="BankInfo.openBankInfoDetail(' + row.id + ')">审核</button>';
                }else{
                    return '-';
                }
            }
        }
    ];
};

/**
 * 检查是否选中
 */
BankInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BankInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加银行卡管理
 */
BankInfo.openAddBankInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加银行卡管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/bankInfo/bankInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看银行卡管理详情
 */
BankInfo.openBankInfoDetail = function (id) {
   // if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '审核',
            area: ['1000px', '620px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/bankInfo/bankInfo_update/' + id
        });
        this.layerIndex = index;
  //  }
};

/**
 * 删除银行卡管理
 */
BankInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/bankInfo/delete", function (data) {
            Feng.success("删除成功!");
            BankInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("bankInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询银行卡管理列表
 */
BankInfo.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['phone'] = $("#phone").val();
    queryData['idcard'] = $("#idcard").val();
    queryData['cardno'] = $("#cardno").val();
    queryData['status'] = $("#status").val();
    BankInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BankInfo.initColumn();
    var table = new BSTable(BankInfo.id, "/bankInfo/list", defaultColunms);
    table.setPaginationType("server");
    BankInfo.table = table.init();
});
