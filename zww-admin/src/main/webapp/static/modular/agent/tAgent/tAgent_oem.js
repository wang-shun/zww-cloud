/**
 * 初始化代理商管理详情对话框
 */
var TAgentInfoDlg = {
    tAgentInfoData : {}
};

/**
 * 清除数据
 */
TAgentInfoDlg.clearData = function() {
    this.tAgentInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TAgentInfoDlg.set = function(key, val) {
    this.tAgentInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TAgentInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TAgentInfoDlg.close = function() {
    parent.layer.close(window.parent.TAgent.layerIndex);
}

/**
 * 收集数据
 */
TAgentInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('code')
    .set('appid')
    .set('appsecret')
    .set('company')
    .set('url')
    .set('SMSCode')
    .set('SMSName')
    .set('isDollMerge')
    ;
}

/**
 * 提交进件
 */
TAgentInfoDlg.addoem = function() {
    $("#ensure").attr("disabled",true);
    this.clearData();
    this.collectData();
    var oemArr = new Array(), oem = this.tAgentInfoData;
    for(var i=0;i<6;i++){
        var oems={};
        var imgUrl = $("#banner"+i).val();
        var url = $("#oemtext"+i).val();
        if(imgUrl == "" && url == ""){
            continue;
        }
        oems.imgUrl = imgUrl;
        oems.url = url;
        oems.oemId = oem.id;
        oems.sort = i+1;
        oems.status = 1;
        oemArr.push(oems);
    }
    this.clearData();
    this.set("oem",oem);
    this.set("oemBanner",oemArr);
    $.ajax({
            url:Feng.ctxPath + "/tAgent/oemAdd",
            type:"post",
            dataType:"json",
            data:JSON.stringify( this.tAgentInfoData ),
            contentType:"application/json",
        success:function(data){
        if(data.code == 200){
            Feng.success("进件成功!");
            $("#ensure").removeAttr("disabled");
            window.parent.TAgent.table.refresh();
            TAgentInfoDlg.close();
        }else{
            Feng.error(data.message);
            $("#ensure").removeAttr("disabled");
        }
    }
});

}

TAgentInfoDlg.add = function(){
      var addnum = $("#addnum").val();
      if(addnum > 5){
          Feng.error("只能最多添加6个banner");
          return;
      }
      $("#row"+addnum).show();
      upd(addnum);
      $("#addnum").val(++addnum);

}

TAgentInfoDlg.del = function(num){
     $("#row"+num).hide();
     $("#oemtext" + num).val("");
     $("#banner"+num+"PreId img").attr("src",Feng.ctxPath + "/static/img/default.png");
    $("#banner" + num).val("");
    $("#addnum").val(num);
}

$(function() {
    $.post(Feng.ctxPath + "/tAgent/getBannerList?tAgentId=" + TAgentInfoDlg.get("id"),function(data){
        var list = data.oemBannerList,oem = data.oem;
        $("#addnum").val(list.length);
        $("#name").val(oem.name);
        $("#code").val(oem.code);
        $("#appid").val(oem.appid);
        $("#appsecret").val(oem.appsecret);
        $("#company").val(oem.company);
        $("#url").val(oem.url);
        $("#SMSCode").val(oem.smsCode);
        $("#SMSName").val(oem.smsName);
        $("#isDollMerge").val(oem.isDollMerge);

        for(var i=0;i<6;i++){
            var html = "";
            if(i < list.length){
                $("#banner"+i+"PreId img").attr("src",list[i].imgUrl);
                upd(i);
                $("#banner"+i).val(list[i].imgUrl);
                html += '<div class="row"  id="row'+i+'">';
            }else{
                html += '<div class="row" id="row'+i+'" style="display: none;">';
            }
            html += '<div class="col-sm-6">';
            html += $("#hiddenDiv"+i).html();
            html += '</div><div class="col-sm-6">';
            if(i < list.length){
                html += '<input type="text" class="oemtext" id="oemtext'+i+'" placeholder="请求地址" value="' + list[i].url + '"/>';
            }else{
                html += '<input type="text" class="oemtext" id="oemtext'+i+'" placeholder="请求地址"/>';
            }
            if(i==0){
                html += '<button class="info" onclick="TAgentInfoDlg.add()">+</button></div></div>';
            }else{
                html += '<button class="info" style="background-color: #ed5565;" onclick="TAgentInfoDlg.del(' + i + ')">-</button></div></div>';
            }
            $("#bannelDiv").append(html);

        }



    });


});


    function upd(i){
        // 初始化头像上传
            var avatarUp = new $WebUpload("banner"+i);
            avatarUp.setUploadBarId("progressBar");
            avatarUp.setUploadUrl(Feng.ctxPath + '/tAgent/upload/'+TAgentInfoDlg.get("id"));
            avatarUp.init();
    }