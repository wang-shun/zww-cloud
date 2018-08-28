/**
 * 初始化娃娃机列表详情对话框
 */
var TDollInfoDlg = {
    tDollInfoData: {},
    validateFields: {
    	name: {
            validators: {
                notEmpty: {
                    message: '娃娃机名称不能为空'
                }
            }
        },
        dollID: {
            validators: {
                notEmpty: {
                    message: '娃娃编号不能为空'
                },
                regexp: {
                    regexp: /^[A-Za-z0-9]+$/,
                    message: '输入字母数字的组合'
                }
            }
        },
        price: {
            validators: {
                notEmpty: {
                    message: '价格不能为空'
                }
            }
        },
        redeemCoins: {
            validators: {
                notEmpty: {
                    message: '兑换币数不能为空'
                }
            }
        },
        timeout: {
            validators: {
                notEmpty: {
                    message: '游戏时间不能为空'
                }
            }
        },
        description: {
            validators: {
                notEmpty: {
                    message: '备注不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TDollInfoDlg.clearData = function() {
    this.tDollInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TDollInfoDlg.set = function(key, val) {
    this.tDollInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TDollInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TDollInfoDlg.close = function() {
    parent.layer.close(window.parent.TDoll.layerIndex);
}

/**
 * 收集数据
 */
TDollInfoDlg.collectData = function() {
    this
    .set('id')
    .set('name')
    .set('description')
    .set('quantity')
    .set('price')
    .set('redeemCoins')
    .set('machineStatus')
    .set('machineSerialNum')
    .set('machineIp')
    .set('machineUrl')
    .set('tbimgContextPath')
    .set('tbimgFileName')
    .set('tbimgRealPath')
    .set('createdDate')
    .set('createdBy')
    .set('modifiedDate')
    .set('modifiedBy')
    .set('rtmpUrl1')
    .set('rtmpUrl2')
    .set('rtmpUrl3')
    .set('rtmpPushUrl')
    .set('mnsTopicName')
    .set('sort')
    .set('timeout')
    .set('piliRoomName')
    .set('machineCode')
    .set('dollID')
    .set('machineType')
    .set('dollAddressId')
    .set('rtmpUrlH5')
    ;
}

/**
 * 验证数据是否为空
 */
TDollInfoDlg.validate = function(formId){
    $('#'+formId).data("bootstrapValidator").resetForm();
    $('#'+formId).bootstrapValidator('validate');
    return $('#'+formId).data('bootstrapValidator').isValid();
}

/**
 * 提交添加
 */
TDollInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate('tDollInfoForm')) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tDoll/add", function(data){
        Feng.success("添加成功!");
        window.parent.TDoll.table.refresh();
        TDollInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tDollInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TDollInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    // if (!this.validate('tDollInfoForm')) {
    //     return;
    // }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tDoll/update", function(data){
        Feng.success("修改成功!");
        window.parent.TDoll.table.refresh();
        TDollInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tDollInfoData);
    ajax.start();
}

/**
 * 加载默认值
 */
TDollInfoDlg.init = function(column,value){
    if($("#"+column+"Value").val() == undefined){
        $("#"+column).val(value);
    }else{
        $("#"+column).val($("#"+column+"Value").val());
    }
}
/**
 * 修改初始化表单
 */
TDollInfoDlg.editInit = function() {
	TDollInfoDlg.init("machineType",0);
	 if($("#dollAddressIdValue").val() == undefined){
		   $("#dollAddressId").attr("value","1");
	   }else{
	       $("#dollAddressId").attr("value",$("#dollAddressIdValue").val());
	   }
	TDollInfoDlg.init("machineStatus","空闲中");
}

$(function() {
	Feng.initValidator("tDollInfoForm", TDollInfoDlg.validateFields);
	//修改表单 时候  初始化select选项值 
	TDollInfoDlg.editInit();
});


//jquery实现回车键触发事件
$(document).keyup(function(event){
    if(event.keyCode ==13){
        if($("#operation").val()=='add'){
            // alert(1)
            TDollInfoDlg.addSubmit();
        }
        if($("#operation").val()=='update'){
            // alert(2)
            TDollInfoDlg.editSubmit();
        }
    }
});