<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单模板信息添加</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div title="模板类型及URL" data-options="region:'west'" style="width:620px;">
        <div id="modelUrlList-toolbar-importAddPage" class="buttonBG">
            <a href="javaScript:void(0);" id="modelUrlList-button-saveurl" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">保存</a>
        </div>
        <table id="modelUrlList-table-importAddPage"></table>
    </div>
    <div title="" data-options="region:'center'" style="width:300px;">
        <table id="modelParamSet-table-importAddPage"></table>
    </div>
</div>
<script type="text/javascript">
    $('#modelUrlList-table-importAddPage').datagrid({
        fit:true,
        method:'post',
        //rownumbers:true,
        idField:'',
        singleSelect:true,
        toolbar:"#modelUrlList-toolbar-importAddPage",
        data:JSON.parse('${modelJSON}'),
        columns:[[
            {field:'id',title:'序号',width:20},
            {field:'urlname',title:'模板方法名称',width:160},
            {field:'urltype',title:'模板类型编码',hidden:true,width:50},
            {field:'urltypename',title:'模板类型名称',width:80},
            {field:'url',title:'模板url',width:300}
        ]],
        onClickRow:function(rowIndex, rowData){
            refreshUrlParam("方法参数配置","sales/import/showImportParamAddPage.do?id="+rowData.id+"&url="+rowData.url+"&urltype="+rowData.urltype);
        }
    });

    function refreshUrlParam(title, url){
        $("#modelParamSet-table-importAddPage").panel({
            title:title,
            href:url,
            cache:false,
            maximized:true
        });
    }

    $(function () {
        //保存
        $("#modelUrlList-button-saveurl").click(function () {
            var flag =  $("#importParam-form-addPage").form('validate');
            if(flag==false){
                return false;
            }
            var formdata=$("#importParam-form-addPage").serializeJSON();
            if($("#importParam-customercell-addPage").is(":visible")){
                var cell = $("#importParam-customercell-addPage").val();
                if(cell == "" || cell == undefined){
                    $("#importParam-customercell-addPage").focus();
                    $.messager.alert("提醒","请根据客户类型填写模板中客户信息所在的单元格");
                    return false;
                }
            }
            $.messager.confirm("提醒","是否添加该模板参数?",function(r){
                if(r){


                    loading('提交中..');
                    $.ajax({
                        type: 'post',
                        cache: false,
                        url: 'sales/manager/addModelParam.do',
                        data: formdata,
                        dataType:'json',
                        success:function(data){
                            loaded();
                            if(data.flag){
                                $.messager.alert("提醒","添加成功!");
                                parent.closeNowTab();
                                $("#saleOrderModel-query-importModelList").trigger("click");
                            }else{
                                if(data.msg){
                                    $.messager.alert("提醒","添加失败!"+data.msg);
                                }else{
                                    $.messager.alert("提醒","添加失败!");
                                }
                            }
                        }
                    });
                }
            });

        });

    });


</script>

</body>
</html>
