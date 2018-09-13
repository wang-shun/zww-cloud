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
    .set('smsCode')
    .set('smsName')
    .set('isDollMerge')
    .set('icon')
    .set('callbackUrl')
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
        var imgUrl = $("#showdiv"+i+" #image"+i).val();
        var url = $("#oemtext"+i).val();
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
      $("#showdiv" + addnum).html($("#hiddenDiv"+addnum).html());
      $("#row"+addnum).show();
      upd("image"+addnum);
      $("#addnum").val(++addnum);

}

TAgentInfoDlg.del = function(num){
     $("#row"+num).hide();
     $("#oemtext" + num).val("");
     $("#showdiv" + num).html("");
    $("#hiddenDiv"+num + " #image" + num + "PreId img").attr("src", Feng.ctxPath + "/static/img/default.png");
    $("#hiddenDiv"+num + " #image" + num).val("");
    $("#addnum").val(num);
}

$(function() {
    $.post(Feng.ctxPath + "/tAgent/getBannerList?tAgentId=" + TAgentInfoDlg.get("id"),function(data){
        var list = data.oemBannerList,oem = data.oem;
        $("#name").val(oem.name);
        $("#code").val(oem.code);
        $("#appid").val(oem.appid);
        $("#appsecret").val(oem.appsecret);
        $("#company").val(oem.company);
        $("#url").val(oem.url);
        $("#smsCode").val(oem.smsCode);
        $("#smsName").val(oem.smsName);
        $("#isDollMerge").val(oem.isDollMerge);
        $("#icon").val(oem.icon);
        $("#callbackUrl").val(oem.callbackUrl);
        $("#iconPreId img").attr("src", oem.icon);

        $("#addnum").val(list.length);
          for(var i=0;i<6;i++){
              var html = "";
              if(list.length > 0){
                  if (i < list.length) {
                      $("#image" + i + "PreId img").attr("src", list[i].imgUrl);
                      $("#image" + i).val(list[i].imgUrl);
                      html += '<div class="row"  id="row' + i + '">';
                  }else{
                      html += '<div class="row" id="row' + i + '" style="display: none;">';
                  }
              }else{
                 if(i == 0){
                      html += '<div class="row"  id="row0">';
                  }else{
                      html += '<div class="row" id="row' + i + '" style="display: none;">';
                  }
              }
            html += '<div class="col-sm-6" id="showdiv' + i + '">';
            html += $("#hiddenDiv"+i).html();
            html += '</div><div class="col-sm-6">';
            if (list.length > 0 && i < list.length) {
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

              if(list.length > 0){
                  if (i < list.length) {
                      upd("image"+i);
                  }
              }else{
                  if(i == 0){
                      upd("image"+i);
                  }
              }
           }
        upd("icon");
    });
});


    function upd(name){
        // 初始化头像上传
            var avatarUp = new $WebUpload(name);
            avatarUp.setUploadBarId("progressBar");
            avatarUp.setUploadUrl(Feng.ctxPath + '/tAgent/upload/'+TAgentInfoDlg.get("id"));
            avatarUp.init();
    }