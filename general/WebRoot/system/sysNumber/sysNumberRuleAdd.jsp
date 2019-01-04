<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>单据编号规则添加信息</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',fit:true" align="center">
        <form action="" method="post" id="sysNumberRule-form-addNumberRule">
            <input id="sysNumberRule-select-hdsubtype" name="sysNumberRule.subtype" value="1" type="hidden" />
            <input id="system-valName-sysNumberRule" name="sysNumberRule.valName" type="hidden"/>
            <input name="tablename" type="hidden" value="${tablename }"/>
            <input id="system-coldatatype-sysNumberRule" type="hidden"/>
            <table cellpadding="2" cellspacing="2" border="0">
                <tr>
                    <td><label>类型:</label></td>
                    <td><select id="system-coltype-sysNumberRule" name="sysNumberRule.coltype" style="width:120px;">
                        <option value="1" selected>固定值</option>
                        <option value="2">字段</option>
                        <option value="3">系统日期</option>
                    </select></td>
                    <td><label>值:</label></td>
                    <td>
                        <div id="sysNumber-div-inputhtml">
                            <input type="text" id="system-fixedval-sysNumberRule" class="easyui-validatebox" validType="fixedval" name="sysNumberRule.val" required="true" style="width: 120px;" maxlength="20"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><label>排序:</label></td>
                    <td>
                        <input type="text" id="sysNumber-sysNumberRule-sequencing" name="sysNumberRule.sequencing" required="true" validType="validIntOnly" style="width:120px;" class="easyui-numberbox" data-options="min:1,max:999"/>
                    </td>
                    <td><label>前缀:</label></td>
                    <td><input type="text" name="sysNumberRule.prefix" style="width:120px;"/></td>
                </tr>
                <tr>
                    <td><label>后缀:</label></td>
                    <td><input type="text" name="sysNumberRule.suffix" style="width:120px;"/></td>
                    <td><label>长度:</label></td>
                    <td><input type="text" id="sysNumber-sysNumberRule-length" readonly="readonly"  name="sysNumberRule.length" style="width: 120px;" class="easyui-validatebox" validType="numspaceone"/></td>
                </tr>
                <tr>
                    <td><label>截取方向:</label></td>
                    <td><select id="sysNumberRule-select-subtype" disabled="disabled" style="width: 120px;">
                        <option value="1" selected="selected">从前向后</option>
                        <option value="2">从后向前</option>
                    </select></td>
                    <td><label>截取开始位置:</label></td>
                    <td><input type="text" id="sysNumber-sysNumberRule-substart" readonly="readonly" value="0" style="width: 120px;" name="sysNumberRule.substart" class="easyui-validatebox" validType="numspacezore"/></td>
                </tr>
                <tr>
                    <td><label>补位符:</label></td>
                    <td><input id="system-cover-sysNumberRule" type="text" name="sysNumberRule.cover" value="0" readonly="readonly" style="width: 120px;"/></td>
                    <td><label>流水依据:</label></td>
                    <td><select id="sysNumber-sysNumberRule-state" onchange="checkRuleStateCanChange('add')" name="sysNumberRule.state" style="width: 120px;">
                        <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
                    </select></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="text-align:right;">
            <input type="button" value="确定" name="savenogo" id="system-save-addNumberRule" />
            <input type="button" value="继续添加" name="savegoon" id="system-saveGoOn-addNumberRule" />
        </div>
    </div>
</div>
<script type="text/javascript">
$(function(){
    fixedvalBlur();

    $.extend($.fn.validatebox.defaults.rules, {
        validIntOnly:{
            validator:function(value){
                var booleanreg=true;
                var rowflag = new Array();
                var rows=$("#system-table-sysNumberRule").datagrid('getRows');
                if(rows.length != 0){
                    for(var i=0;i<rows.length;i++){
                        if(rows[i].sequencing != value){
                            rowflag[i]=true;
                        }
                        else{
                            rowflag[i]=false;
                        }
                        booleanreg=booleanreg && rowflag[i];
                    }
                }
                return booleanreg;
            },
            message:'请输入与顺序列不同的整数类型数据!'
        },
        fixedval: {
            validator: function(value, param){
                var ret = sysNumber_AjaxConn({val:value},'sysNumber/isRepeatFixedVal.do');
                var retJson = $.parseJSON(ret);
                return !retJson.flag;
            },
            message: '固定值重复,请重新输入!'
        },
        numspacezore: {
            validator: function(value, param){
                return /^[0-9][0-9]{0,1}$/.test(value);
            },
            message: '请输入0-99的数字!'
        },
        numspaceone: {
            validator: function(value, param){
                return /^[1-9][0-9]{0,1}$/.test(value);
            },
            message: '请输入1-99的数字!'
        }
    });

    $("#system-save-addNumberRule").click(function(){
        $sysNumberRuleInfo = $("#sysNumberRule-form-addNumberRule");
        if(!$sysNumberRuleInfo.form("validate")){
            return false;
        }
        sysNumberRuleSubmit();
        $("#system-dialog-sysNumberRule-add").dialog("close");
    });

    $("#system-saveGoOn-addNumberRule").click(function(){
        $sysNumberRuleInfo = $("#sysNumberRule-form-addNumberRule");
        if(!$sysNumberRuleInfo.form("validate")){
            return false;
        }
        sysNumberRuleSubmit();
        var l = document.getElementById("sysNumber-sysNumberRule-length");//长度
        var s = document.getElementById("sysNumber-sysNumberRule-substart");//截取开始位置
        var t = document.getElementById("sysNumberRule-select-subtype");//截取方向
        var inputhtml='<input id="system-fixedval-sysNumberRule" class="easyui-validatebox" validType="fixedval" type="text" name="sysNumberRule.val" required="true" style="width:120px;" maxlength="20"/>';
        $("#sysNumber-div-inputhtml").html(inputhtml);
        $("#sysNumber-sysNumberRule-sequencing").numberbox('clear');
        $("#system-valName-sysNumberRule").val("");
        $("#sysNumberRule-form-addNumberRule").form('reset');
        t.options[0].selected = true;
        $("#sysNumberRule-select-hdsubtype").val(t.options[0].value);
        l.setAttribute("readonly","readonly");
        s.setAttribute("readonly","readonly");
        t.setAttribute("disabled","disabled");
        fixedvalBlur();
    });
});

//单据编号规则ajax提交
function sysNumberRuleSubmit(){
    var rows = $("#system-table-sysNumberRule").datagrid('getRows');
    var effectRow = new Object();
    if(rows.length != 0){
        effectRow["sysNumberRuleRows"] = JSON.stringify(rows);
    }
    $sysNumberRuleInfo = $("#sysNumberRule-form-addNumberRule");
    var sysNumberRuleJson = $sysNumberRuleInfo.serializeJSON();
    for(key in sysNumberRuleJson){
        effectRow[key] = sysNumberRuleJson[key];
    };
    var ret = sysNumber_AjaxConn(effectRow,'sysNumber/addSysNumberRuleToList.do');
    var retJson = $.parseJSON(ret);
    //流水号字段值testvalue，流水号字段名称valName
    var seqRows = retJson.list;
    var temp = new Object();
    for(var i=0;i<seqRows.length;i++){
        for(var j=0;j<seqRows.length;j++){
            if(seqRows[i].sequencing < seqRows[j].sequencing){
                temp = seqRows[i];
                seqRows[i]=seqRows[j];
                seqRows[j]=temp;
            }
        }
    }
    $("#system-table-sysNumberRule").datagrid('loadData',seqRows);
    //获取预览效果
    var preViewObj = new Object();
    if(seqRows.length != 0){
        preViewObj["preView"] = JSON.stringify(seqRows);
    }
    var sysNumberJson = $("#sysNumber-form-billInfo").serializeJSON();
    for(key in sysNumberJson){
        preViewObj[key] = sysNumberJson[key];
    };
    var preViewRet = sysNumber_AjaxConn(preViewObj,'sysNumber/getPreViewSysNumber.do');
    var preViewJSON = $.parseJSON(preViewRet);
    $("#system-preView-sysNumber").val(preViewJSON.preViewNo);
    getTestValueAndName(seqRows);
}

$("#sysNumberRule-select-subtype").change(function(){
    $("#sysNumberRule-select-hdsubtype").val(this.options[this.selectedIndex].value);
});

//类型转变值的变化
$("#system-coltype-sysNumberRule").change(function(){
    var l = document.getElementById("sysNumber-sysNumberRule-length");//长度
    var s = document.getElementById("sysNumber-sysNumberRule-substart");//截取开始位置
    var t = document.getElementById("sysNumberRule-select-subtype");//截取方向
    var textvalue = $("#system-testvalue-sysNumber").val();//流水依据字段
    if("1" == this.value){//固定值
        var inputhtml='<input id="system-fixedval-sysNumberRule" class="easyui-validatebox" validType="fixedval" type="text" name="sysNumberRule.val" required="true" style="width:120px;" maxlength="20"/>';
        $("#sysNumber-div-inputhtml").html(inputhtml);
        $("#sysNumber-sysNumberRule-sequencing").numberbox('clear');
        $("#system-valName-sysNumberRule").val("");
        $("#sysNumberRule-form-addNumberRule").form('reset');
        $("#system-cover-sysNumberRule").val("*");
        t.options[0].selected = true;
        $("#sysNumberRule-select-hdsubtype").val(t.options[0].value);
        l.setAttribute("readonly","readonly");
        s.setAttribute("readonly","readonly");
        t.setAttribute("disabled","disabled");
        $("#sysNumber-sysNumberRule-state").val("0");
        $("#system-coldatatype-sysNumberRule").val("");
        fixedvalBlur();
    }
    else if("2" == this.value){//字段
        var inputhtml='<input id="system-val-sysNumberRule" class="easyui-combobox" required="true" type="text" name="sysNumberRule.val" style="width:120px;"/>';
        $("#sysNumber-div-inputhtml").html(inputhtml);
        $("#sysNumber-sysNumberRule-sequencing").numberbox('clear');
        $("#sysNumber-sysNumberRule-length").val("");
        $("#sysNumber-sysNumberRule-substart").val(0);
        $("#system-cover-sysNumberRule").val("0");
        $("#sysNumber-sysNumberRule-state").val("0");
        $('#system-val-sysNumberRule').combobox({
            url:'common/getDictTableColumnList.do?tablename=${tablename}',
            valueField:'columnname',
            textField:'colchnname',
            onSelect:function(record){
                //字段名称
                $("#system-valName-sysNumberRule").val(record.colchnname);
                l.removeAttribute("readonly");
                s.removeAttribute("readonly");
                //t.removeAttribute("disabled");
                $("#system-coldatatype-sysNumberRule").val(record.coldatatype);
                if("datetime" != record.coldatatype){
                    $("#sysNumber-sysNumberRule-state").val("0");
                }
            }
        });
    }
    else{//系统日期
        var inputhtml='<input id="system-dateval-sysNumberRule" type="text" value="yyyyMMdd" name="sysNumberRule.val" style="width:120px;"/>';
        $("#sysNumber-div-inputhtml").html(inputhtml);
        l.removeAttribute("readonly");
        s.removeAttribute("readonly");
        $("#system-valName-sysNumberRule").val($("#system-dateval-sysNumberRule").val());
        $("#sysNumber-sysNumberRule-sequencing").numberbox('clear');
        $("#sysNumber-sysNumberRule-substart").val(0);
        $("#system-cover-sysNumberRule").val("0");
        var dateval = $("#system-dateval-sysNumberRule").val();
        var len = 0;
        if(dateval != ""){
            len = dateval.length;
        }
        $("#sysNumber-sysNumberRule-length").val(len);
        t.removeAttribute("disabled");
        $("#system-coldatatype-sysNumberRule").val("");
        fixedvalBlur();
    }
});

</script>
</body>
</html>
