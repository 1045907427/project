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
        <form action="basefiles/addStorageItemGoods.do" method="post" id="storageItemAddPage-form-add">
            <div class="pageContent">
                <p>
                    <label style=" width: 60px">仓库:</label>
                    <input type="text" id="storageItemAddPage-widget-storageid" value="${param.storageid}" readonly="readonly" name="storageItemGoods.storageid" class="easyui-validatebox" required="true" style="width:200px;"/>
                </p>
                <p>
                    <label style=" width: 60px">商品:</label>
                    <input type="text" name="storageItemGoods.goodsid" id="storageItemAddPage-widget-goodsid" class="easyui-validatebox" validType="validId[20]" required="true" style="width:200px;"/>
                </p>
                <p>
                    <label style=" width: 60px">货位:</label>
                    <input type="text" name="storageItemGoods.itemno"  class="easyui-validatebox"  required="true" style="width:200px;" />
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

    $(function(){
        $("#storageItemAddPage-widget-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:200,
            required:true
        })
        $("#storageItemAddPage-widget-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width:200,
            singleSelect:true
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
//                     var queryJSON = $("#basefiles-form-custoemrlist").serializeJSON();
//                    $("#basefiles-table-storageitemgoodsList").datagrid("load",queryJSON);
                     $("#basefiles-table-storageitemgoodsList").datagrid('reload')
                    $("#basefiles-dialog-storageItemAdd1").dialog('destroy');
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
            $("#basefiles-dialog-storageItemAdd1").dialog('destroy');
        });
    });
</script>
</body>
</html>
