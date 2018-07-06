/**
 * 娃娃机抓取记录管理初始化
 */
var TDollCatchHistory = {
    id: "TDollCatchHistoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
TDollCatchHistory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
           // {title: 'id', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '用户名称', field: 'memberName', visible: true, align: 'center', valign: 'middle'},
            {title: '机器名称', field: 'dollName', visible: true, align: 'center', valign: 'middle'},
            {title: '机器号', field: 'machineCode', visible: true, align: 'center', valign: 'middle'},
            {title: '封面', field: 'dollUrl', visible: true, align: 'center', valign: 'middle',
              formatter:function (value,row,index) {
                 return '<a class="maincolor" href="javascript:;"><img  src="'+value+'" width="80" class="img-rounded" /></a>';
              }
            },
        {title: '房间业务类型', field: 'machineType', visible: true, align: 'center', valign: 'middle',
            formatter:function (value,row,index) {
                if (value=="0")return '普通房';
                if (value=="1")return '练习房';
                if (value=="2")return '化妆';
                if (value=="3")return '数码';
            }
        },
            {title: '抓取状态', field: 'catchStatus', visible: true, align: 'center', valign: 'middle'},
            {title: '抓取时间', field: 'catchDate', visible: true, align: 'center', valign: 'middle'},
           // {title: '视频url', field: 'videoUrl', visible: true, align: 'center', valign: 'middle'},
          //  {title: '游戏编号', field: 'gameNum', visible: true, align: 'center', valign: 'middle'},
            {title: '机器编码', field: 'dollCode', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
TDollCatchHistory.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        TDollCatchHistory.seItem = selected[0];
        return true;
    }
};

/**
 * 打开查看娃娃机抓取记录详情
 */
TDollCatchHistory.update = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '娃娃机抓取记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tDollCatchHistory/tDollCatchHistory_update/' + TDollCatchHistory.seItem.id
        });
        this.layerIndex = index;
    }
};


/**
 * 查询娃娃机抓取记录列表
 */
TDollCatchHistory.search = function () {
    var queryData = {};
    queryData['dollName'] = $("#dollName").val();
    queryData['machineCode'] = $("#machineCode").val();
    queryData['dollCatchStates'] = $("#dollCatchStates").val();
    queryData['machineType'] = $("#machineType").val();
    queryData['memberName'] = $("#memberName").val();
    TDollCatchHistory.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = TDollCatchHistory.initColumn();
    var table = new BSTable(TDollCatchHistory.id, "/tDollCatchHistory/list", defaultColunms);
    table.setPaginationType("server");
    TDollCatchHistory.table = table.init();
});
