<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品调价单</title>
    <%@include file="/include.jsp"%>
    <script type="text/javascript" src="js/jquery.excel.js"></script>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<table id="goods-table-showAdjustPriceList"></table>
<div id="goods-query-showAdjustPriceList" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/basefiles/addAdjustPricePage.do">
            <a href="javaScript:void(0);" id="goods-add-addAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
        </security:authorize>
        <security:authorize url="/basefiles/editAdjustPricePage.do">
            <a href="javaScript:void(0);" id="goods-edit-editAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
        </security:authorize>
        <security:authorize url="/basefiles/deleteAdjustPrice.do">
            <a href="javaScript:void(0);" id="goods-audit-deleteAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
        </security:authorize>
        <security:authorize url="/basefiles/auditAdjustPrice.do">
            <a href="javaScript:void(0);" id="goods-audit-auditAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-audit'" title="审核">审核</a>
        </security:authorize>
        <security:authorize url="/basefiles/importAdjustPricePage.do">
            <a href="javaScript:void(0);" id="goods-in-inAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
        </security:authorize>
        <security:authorize url="/basefiles/exportAdjustPricePage.do">
            <a href="javaScript:void(0);" id="goods-out-outAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-export'" title="导出">导出</a>
        </security:authorize>


        <!--
		    <security:authorize url="/basefiles/previewAdjustPricePage.do">
	            <a href="javaScript:void(0);" id="goods-preview-previewAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-preview'" title="打印预览">打印预览</a>
		    </security:authorize>
		   	<security:authorize url="/basefiles/printAdjustPricePage.do">
	            <a href="javaScript:void(0);" id="goods-print-printAdjustPrice" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-print'" title="打印">打印</a>
		    </security:authorize>
		   	-->
    </div>
    <div>
        <form action="" id="goods-form-showAdjustPriceList" method="post">
            <table class="querytable">
                <tr>
                    <td>编号：</td>
                    <td><input type="text" id="delivery-id-deliveryAogorderSourceQueryPage" name="id" style="width: 225px;"/></td>
                    <td class="len30 right">调价单名称：</td>
                    <td><input type="text" id="goods-name-showAdjustPriceList" name="name" style="width: 225px;"/></td>
                    <td class="len30 right">状态：</td>
                    <td>
                        <select name="status" style="width:150px;">
                            <option ></option>
                            <option value="2" selected="selected">保存</option>
                            <option value="3">审核通过</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>业务日期：</td>
                    <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/>
                        到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                    </td>
                    <td class="len30 right">调价单类型：</td>
                    <td>
                        <select name="type" style="width:225px;">
                            <option selected="selected"></option>
                            <option value="1">商品采购价</option>
                            <option value="2">商品基准价</option>
                            <option value="3">价格套</option>
                            <option value="4">合同价</option>
                        </select>
                    </td>
                    <td colspan="2" >
                        <a href="javaScript:void(0);" id="goods-queay-goodsAogorder" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="goods-reload-goodsAogorder" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#goods-form-showAdjustPriceList").serializeJSON();
    //数据列表
    var goodsAogorderJson = $("#goods-table-showAdjustPriceList").createGridColumnLoad({
        name :'',
        frozenCol : [[
            {field:'ck',checkbox:true,isShow:true}
        ]],
        commonCol : [[
            {field:'id',title:'编号',width:125,sortable:true},
            {field:'businessdate',title:'业务日期',width:80,sortable:true},
            {field:'type',title:'调价单类型',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    if(value=="1"){
                        return "商品采购价";
                    }
                    else if(value=="2"){
                        return "商品基准销售价";
                    }
                    else if(value=="3"){
                        return "价格套";
                    }
                    else if(value=="4"){
                        return "合同价";
                    }
                }
            },
            {field:'status',title:'状态',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return getSysCodeName("status",value);
                }
            },
            {field:'addusername',title:'制单人',width:60,sortable:true},
            {field:'adddeptname',title:'制单人部门名称',width:100,sortable:true},
            {field:'addtime',title:'制单时间',width:130,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue!=undefined){
                        var newstr=dateValue.replace("T"," ");
                        return newstr;
                    }
                    return " ";
                }
            },
            {field:'auditusername',title:'审核人',width:60,sortable:true},
            {field:'remark',title:'备注',width:80,sortable:true},
        ]]
    });
    $(function(){
        $("#goods-table-showAdjustPriceList").datagrid({
            authority:goodsAogorderJson,
            frozenColumns: goodsAogorderJson.frozen,
            columns:goodsAogorderJson.common,
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            sortName:'id',
            sortOrder:'desc',
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter: false,
            pageSize:100,
            url: 'basefiles/showAdjustPriceList.do',
            queryParams:initQueryJSON,
            toolbar:'#goods-query-showAdjustPriceList',
            onDblClickRow:function(rowIndex, rowData){
                top.addOrUpdateTab('basefiles/showAdjustPriceEditPage.do?id='+rowData.id, "调价单查看");
            }
        }).datagrid("columnMoving");
        //回车时间
        controlQueryAndResetByKey("goods-queay-goodsAogorder","goods-reload-goodsAogorder");
        //查询
        $("#goods-queay-goodsAogorder").click(function(){
            var queryJSON = $("#goods-form-showAdjustPriceList").serializeJSON();
            $("#goods-table-showAdjustPriceList").datagrid("load",queryJSON);
        });
        //重置
        $("#goods-reload-goodsAogorder").click(function(){
            $("#goods-form-showAdjustPriceList").form("reset");
            var queryJSON = $("#goods-form-showAdjustPriceList").serializeJSON();
            $("#goods-table-showAdjustPriceList").datagrid('load', queryJSON);
        });
        //新增
        $("#goods-add-addAdjustPrice").click(function(){
            var url = "basefiles/showAdjustPriceAddPage.do";
            top.addTab(url,'新增调价单');
        });
        //修改
        $("#goods-edit-editAdjustPrice").click(function(){
            var con = $("#goods-table-showAdjustPriceList").datagrid('getSelected');
            if(null == con){
                $.messager.alert("提醒","请选择调价单!");
                return false
            }
            var url = "basefiles/showAdjustPriceEditPage.do?id="+con.id;
            top.addTab(url,'修改调价单');
        });
        //导入
        $("#goods-in-inAdjustPrice").Excel('import',{
            url:'basefiles/importAdjustPrice.do',
            type:'importUserdefined',
            onClose: function(){ //导入成功后窗口关闭时操作，
                $("#goods-table-showAdjustPriceList").datagrid('reload');	//更新列表
            }
        });
        //导出
        $("#goods-out-outAdjustPrice").Excel('export',{
            queryForm: "#goods-form-showAdjustPriceList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#goods-table-showAdjustPriceList",
            type:'exportUserdefined',
            name:'调价单列表',
            url:'basefiles/exportAdjustPrice.do'
        });
        //打印预览
        $("#goods-preview-previewAdjustPrice").click(function(){
            goodsAogorderPrintView();
        });
        //打印
        $("#goods-print-printAdjustPrice").click(function(){
            goodsAogorderPrint();
        });
        //批量审核goods-audit-deleteAdjustPrice
        $("#goods-audit-auditAdjustPrice").click(function(){
            var rows = $("#goods-table-showAdjustPriceList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择调价单!");
                return false
            }
            var ids = "";
            for(var i=0;i<rows.length;i++){
                if(ids == ""){
                    ids = rows[i].id;
                }else{
                    ids += "," + rows[i].id;
                }
            }
            $.messager.confirm('提醒','确定要审核吗?',function(r){
                if (r){
                    $.ajax({
                        url :'basefiles/auditsAdjustPrice.do?ids='+ids,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            $.messager.alert("提醒",json.msg);
                            var queryJSON = $("#goods-form-showAdjustPriceList").serializeJSON();
                            $("#goods-table-showAdjustPriceList").datagrid("load",queryJSON);
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","审核失败");
                        }
                    });
                }
            });
        });
        //批量删除
        $("#goods-audit-deleteAdjustPrice").click(function(){
            var rows = $("#goods-table-showAdjustPriceList").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请选择调价单!");
                return false
            }
            var ids = "";
            for(var i=0;i<rows.length;i++){
                if(ids == ""){
                    ids = rows[i].id;
                }else{
                    ids += "," + rows[i].id;
                }
            }
            $.messager.confirm('提醒','确定删除吗?',function(r){
                if (r){
                    $.ajax({
                        url :'basefiles/deletesAdjustPrice.do?ids='+ids,
                        type:'post',
                        dataType:'json',
                        async: false,
                        success:function(json){
                            $.messager.alert("提醒",json.msg);
                            var queryJSON = $("#goods-form-showAdjustPriceList").serializeJSON();
                            $("#goods-table-showAdjustPriceList").datagrid("load",queryJSON);
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","删除成功失败");
                        }
                    });
                }
            });
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //调价单打印
        //打印功能暂时无效
        AgReportPrint.init({
            id: "showAdjustPriceList-dialog-print",
            code: "delivery_aogorder",
            tableId: "goods-table-showAdjustPriceList",
            url_preview: "print/basefiles/aogorderPrintView.do",
            url_print: "print/basefiles/aogorderPrint.do",
            btnPreview: "goods-preview-previewAdjustPrice",
            btnPrint: "goods-print-printAdjustPrice"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>