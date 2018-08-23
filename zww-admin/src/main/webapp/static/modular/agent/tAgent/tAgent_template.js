/**
 * 初始化代理商管理详情对话框
 */
var TAgentTemDlg = {
    tAgentTemData : {}
};

/**
 * 清除数据
 */
TAgentTemDlg.clearData = function() {
    this.tAgentTemData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称b
 * @param val 数据的具体值
 */
TAgentTemDlg.set = function(key, val) {
    this.tAgentTemData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TAgentTemDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TAgentTemDlg.close = function() {
    parent.layer.close(window.parent.TAgent.layerIndex);
}

/**
 * 收集数据
 */
TAgentTemDlg.collectData = function() {
    this
    .set('id')
    ;
}

/**
 * 提交进件
 */
TAgentTemDlg.addtemplate = function() {
    var addnum = $("#addnum").val();
    var templateArr = new Array(), oemId = $("#id").val();
    for(var i=0;i<addnum;i++){
        var tOemTemplate={};
        var id = $("#temid"+i).val();
        var title = $("#title"+i).val();
        var type = $("#type"+i).val();
        var template = $("#template"+i).val();
        if(template != "" && type != ""){
            tOemTemplate.id = id;
            tOemTemplate.oemId = oemId;
            tOemTemplate.templateId = template;
            tOemTemplate.type = type;
            tOemTemplate.title = title;
            templateArr.push(tOemTemplate);
        }
    }
    this.clearData();
    this.set("templateArr",templateArr);
    $.ajax({
        url:Feng.ctxPath + "/tAgent/templateAdd",
        type:"post",
        dataType:"json",
        data:JSON.stringify(this.tAgentTemData),
        contentType:"application/json",
        success:function(data){
            if(data.code == 200){
                Feng.success("提交成功!");
                window.parent.TAgent.table.refresh();
                TAgentTemDlg.close();
            }else{
                Feng.error(data.message);
            }
        }
    });

}
TAgentTemDlg.add = function(){
    var addnum = $("#addnum").val();
    var html = "<div class='row'  id='row" + addnum + "'>";
    html += '<div class="col-sm-12"><input type="hidden" id="temid'+ addnum + '" value="0"/>';
    html += '<input type="text" class="oemtext" id="title'+ addnum +'" placeholder="标题"/>';
    html += '<input type="text" class="oemtext" id="type'+ addnum +'" placeholder="类型"/>';
    html += '<input type="text" class="oemtext" id="template'+ addnum +'" placeholder="模板id"/>';
    html += '<button class="info" style="background-color: #ed5565;" onclick="TAgentTemDlg.del(' + addnum + ',0)">-</button></div></div>';
    $("#bannelDiv").append(html);
    $("#addnum").val(++addnum);
}

TAgentTemDlg.del = function(num,id){
    if(id != 0){
        if(confirm("确认要删除这条模板消息？")){
            $.post(Feng.ctxPath + "/tAgent/delTemplate?id=" + id,function(data){
                if(data.code){
                    Feng.success("删除成功!");
                }else{
                    Feng.error("删除失败!");
                    return;
                }
            });
        }else{
            Feng.error("动作取消");
            return;
        }
    }
    $("#row" + num).remove();
    $("#temid" + num).val("");
    $("#type" + num).val("");
    $("#title" + num).val("");
    $("#addnum").val(num);
}

$(function() {
    $.post(Feng.ctxPath + "/tAgent/getTemplateList?tAgentId=" + TAgentTemDlg.get("id"),function(data){
        var list = data.oemTemplateList;
        $("#addnum").val(list.length);
        for(var i=0;i<list.length;i++){
            var html = "<div class='row'  id='row" + i + "'>";
               html += '<div class="col-sm-12"><input type="hidden" id="temid'+ i + '" value="' + list[i].id + '"/>';
               html += '<input type="text" class="oemtext" id="title'+i+'" placeholder="标题" value="' + list[i].title + '"/>';
               html += '<input type="text" class="oemtext" id="type'+i+'" placeholder="类型" value="' + list[i].type + '"/>';
               html += '<input type="text" class="oemtext" id="template'+i+'" placeholder="模板id" value="' + list[i].templateId + '"/>';
            if(i==0){
                html += '<button class="info" onclick="TAgentTemDlg.add()">+</button></div></div>';
            }else{
                html += '<button class="info" style="background-color: #ed5565;" onclick="TAgentTemDlg.del(' + i + ',' + list[i].id + ')">-</button></div></div>';
            }
            $("#bannelDiv").append(html);
        }
    });
});
