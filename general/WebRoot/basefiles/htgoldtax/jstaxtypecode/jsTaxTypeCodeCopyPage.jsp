<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品和服务税收分类与编码编辑</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <div align="center" style="padding: 10px;">
            <form action="basefiles/htgoldtax/addJsTaxTypeCode.do" method="post" id="htgoldtax-form-editJsTaxTypeCode">
                <table cellpadding="3" cellspacing="3" border="0">
                    <tr>
                        <td colspan="2">注意：具体编码值以金税档案编码为准</td>
                    </tr>
                    <tr>
                        <td width="100px" align="right">编码:</td>
                        <td align="left">
                            <input type="text" name="jsTaxTypeCode.id" value="" class="easyui-validatebox" data-options="required:true,validType:['validJsTaxTypeCodeId']" style="width:200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">货物和劳务名称:</td>
                        <td align="left">
                            <input type="text" name="jsTaxTypeCode.goodsname" value="${jsTaxTypeCode.goodsname}" class="easyui-validatebox" data-options="required:true,validType:['maxByteLength[120]']" style="width:200px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">说明:</td>
                        <td align="left">
                            <textarea rows="0" cols="0" name="jsTaxTypeCode.description" style="width:200px;height:50px" class="easyui-validatebox" validType="maxByteLength[1000]">${jsTaxTypeCode.description}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">关键字:</td>
                        <td align="left">
                            <textarea rows="0" cols="0" name="jsTaxTypeCode.keyword" style="width:200px;height:50px" class="easyui-validatebox" validType="maxByteLength[1000]">${jsTaxTypeCode.keyword}</textarea>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" name="savegoon" id="htgoldtax-save-editJsTaxTypeCode" value="确定"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#htgoldtax-form-editJsTaxTypeCode").form({
            onSubmit: function(){

                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
            },
            success:function(data){
                //$.parseJSON()解析JSON字符串
                var json = $.parseJSON(data);
                if(json.flag==true){
                    $.messager.alert("提醒","编辑成功!");
                    $("#htgoldtax-dialogOper-jsTaxTypeCodeList-content").dialog('close',true);
                    $("#htgoldtax-table-jsTaxTypeCodeList").datagrid('reload');
                }
                else{
                    if(json.msg!=null){
                        $.messager.alert("提醒","编辑失败,"+json.msg);
                    }else{
                        $.messager.alert("提醒","编辑失败!");
                    }
                }
            }
        });
        $("#htgoldtax-save-editJsTaxTypeCode").click(function(){
            $.messager.confirm("提醒","是否添加金税税收分类信息?",function(r){
                if(r){
                    $("#htgoldtax-form-editJsTaxTypeCode").submit();
                }
            });
        });

    });
</script>
</body>
</html>
