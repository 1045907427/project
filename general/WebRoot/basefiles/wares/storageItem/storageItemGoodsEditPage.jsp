<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>仓库货位新增页面</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true" id="b-layout-storageItemAddPage">
    <div data-options="region:'center',split:true,border:true">
        <form action="basefiles/editStorageItemGoods.do" method="post" id="storageItemAddPage-form-add">
            <div class="pageContent">
                <p>
                    <label style=" width: 60px">仓库:</label>
                    <input type="text" id="storageItemAddPage-widget-storageid" readonly="readonly" value="${storageItemGoods.storageid}" name="storageItemGoods.storageid" class="easyui-validatebox" required="true" style="width:200px;"/>
                </p>
                <p>
                    <label style=" width: 60px">商品:</label>
                    <input type="text" name="storageItemGoods.goodsid" readonly="readonly" value="${storageItemGoods.goodsid}" id="storageItemAddPage-widget-goodsid" class="easyui-validatebox" required="true" style="width:200px;"/>
                </p>
                <p>
                    <label style=" width: 60px">货位:</label>
                    <input type="text" name="storageItemGoods.itemno" value="${storageItemGoods.itemno}"  class="easyui-validatebox"  required="true" style="width:200px;" />
                </p>
            </div>
        </form>
    </div>
    <div data-options="region:'south'">
        <div class="buttonDetailBG" style="text-align:right;">
            <security:authorize url="/basefiles/goodesSimplifySaveBtn.do">
                <input type="button" id="basefiles-save-storageItemAddPage" value="保存"/>
            </security:authorize>
            <input type="button" id="basefiles-close-storageItemAddPage" value="关闭"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    var storage_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success: function (data) {
                loaded();
            }
        })
        return MyAjax.responseText;
    }
    //检验档案数据（唯一性，最大长度等）
    $.extend($.fn.validatebox.defaults.rules, {
        validName: {//名称唯一性,最大长度
            validator: function (value, param) {
                var oldname=$("#storageItemAddPage-input-oldname").val();
                if(oldname==value){
                    return true;
                }
                if (value.length <= param[0]) {
                    var ret = storage_AjaxConn({name: value,storageid:$("#storageItemAddPage-widget-storageid").widget('getValue'),id:$("input[name='storageItem.id']").val()}, 'basefiles/isRepeatStorageItemName.do');//true 重复，false 不重复
                    var retJson = $.parseJSON(ret);
                    if (retJson.flag) {
                        $.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
                        return false;
                    }
                }
                else {
                    $.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
                    return false;
                }
                return true;
            },
            message: ''
        }
    });
    $(function(){
        $("#storageItemAddPage-widget-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:200,
            required:true
        })
        $("#storageItemAddPage-form-add").form({
            onSubmit: function(){
                if(!$("#storageItemAddPage-form-add").form('validate')){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.flag){
                    var queryJSON = $("#basefiles-form-custoemrlist").serializeJSON();
                    $("#basefiles-table-storageitemgoodsList").datagrid("load",queryJSON);
                    $("#basefiles-dialog-storageItemEdit1").dialog('destroy');
                    $.messager.alert("提醒","保存成功!");
                }else{
                    $.messager.alert("提醒","保存失败!");
                }
            }
        });
        //保存
        $("#basefiles-save-storageItemAddPage").click(function(){
            $("#storageItemAddPage-form-add").submit();
        });
        //关闭
        $("#basefiles-close-storageItemAddPage").click(function(){
            $("#basefiles-dialog-storageItemEdit1").dialog('destroy');
        });
    });
</script>
</body>
</html>
