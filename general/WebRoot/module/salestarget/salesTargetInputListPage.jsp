<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售目标列表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="salesTargetInput-buttons-ListPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="salesTargetInputListPage-table-detail-detail"></table>
        <div id="salesTargetInputListPage-form-div" style="padding:2px;height:auto">
            <form action="" id="salesTargetInputListPage-form-ListQuery" method="post">
                <table class="querytable">
                    <tr>
                        <td>年月：</td>
                        <td>
                        	<input type="text" name="yearmonthstart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" value="${yearfirstmonth }" /> 到 <input type="text" name="yearmonthend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" value="${yearmonth }"/>
                        </td>
                        <td>制单日期：</td>
                        <td>
                        	<input type="text" id="salesTargetInputListPage-form-addtime" name="addtime" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="" />
                        </td>
                        <td>品牌：</td>
                        <td>
                            <input id="salesTargetInputListPage-form-brandid" type="text" name="brandid" style="width: 150px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>门店:</td>
                        <td>
                            <input id="salesTargetInputListPage-form-customerid" type="text" name="customerid" style="width: 225px;"/>
                        </td>
                        <td>渠道:</td>
                        <td>
                        	<input id="salesTargetInputListPage-form-customersort" type="text" name="customersort" style="width: 150px;"/>
                        </td>
                        <td>客户业务员：</td>
                        <td>
                        	<input id="salesTargetInputListPage-form-salesuserid" name="salesuserid" type="text" style="width: 150px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>状态：</td>
                        <td>
                            <select name="status" style="width: 225px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                            </select>
                        </td>
                        <td></td>
	    				<td>
	    				</td>                  	
                        <td colspan="2" align="right">
                            <div>
                                <a href="javaScript:void(0);" id="salesTargetInput-query-List" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="salesTargetInput-query-reloadList" class="button-qr">重置</a>
                            </div>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div style="display: none;">
    <div id="salesTargetInput-dialog-detail"></div>
    <div id="salesTargetInput-dialog-settlement"></div>
    <a href="javaScript:void(0);" id="salesTargetInputListPage-buttons-exportclick" style="display: none"title="导出">导出</a>
    <a href="javaScript:void(0);" id="salesTargetInputListPage-buttons-importclick" style="display: none"title="导入">导入</a>
</div>
<script type="text/javascript">
//根据初始的列与用户保存的列生成以及字段权限生成新的列
var tableColJson=$("#salesTargetInputListPage-table-detail-detail").createGridColumnLoad({
    name:'t_salestarget_input',
    frozenCol:[[
        {field:'ck',checkbox:true,isShow:true}
    ]],
    commonCol:[[
        {field:'id',title:'编码',width:130,sortable:true},
        {field:'yearmonth',title:'录入年月',width:80,isShow:true},
        {field:'customerid',title:'门店',width:150,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.customername;
            }
        },
        {field:'customersort',title:'渠道',width:70,isShow:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.customersortname;
            }
        },
        {field:'salesuserid',title:'客户业务员',width:100,isShow:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.salesusername;
            }
        },
        {field:'brandid',title:'品牌',width:60,sortable:true,
            formatter:function(val,rowData,rowIndex){
                return rowData.brandname;
            }
        },
        {field:'firstsalestarget',title:'第一销售目标',width:100,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val);
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'firstgrossprofit',title:'第一目标毛利',width:100,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val);
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'firstgrossprofitrate',title:'第一目标毛利率',width:100,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val)+"%";
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'secondsalestarget',title:'第二销售目标',width:100,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val);
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'secondgrossprofit',title:'第二目标毛利',width:100,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val);
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'secondgrossprofitrate',title:'第二目标毛利率',width:100,sortable:true,align:'right',
            formatter:function(val,rowData,rowIndex){
                if(val != "" && val != null){
                    return formatterMoney(val)+"%";
                }
                else{
                    return "0.00";
                }
            }
        },
        {field:'status',title:'状态',width:80,
            formatter: function(value,row,index){
                return getSysCodeName('status',value);
            }
        },
        {field:'remark',title:'备注',width:100},
        {field:'addusername',title:'制单人',width:80},
        {field:'addtime',title:'制单时间',width:130,sortable:true},
        {field:'modifyusername',title:'修改人',width:80,hidden:true},
        {field:'modifytime',title:'修改时间',width:130,hidden:true},
        {field:'auditusername',title:'审核人',width:80,sortable:true},
        {field:'audittime',title:'审核时间',width:130,sortable:true}
    ]]
});
$("#salesTargetInput-buttons-ListPage").buttonWidget({
    initButton:[
        {},
        <security:authorize url="/module/salestargetinput/salesTargetInputAddBtn.do">
        {
            type: 'button-add',
            handler: function(){
                $('<div id="salesTargetInput-dialog-detail-content"></div>').appendTo('#salesTargetInput-dialog-detail');
                $('#salesTargetInput-dialog-detail-content').dialog({
                    title: '销售目标【新增】',
                    width: 800,
                    height: 300,
                    //fit:true,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'module/salestargetinput/showSalesTargetInputAddPage.do',
                    modal: true,
                    onLoad:function(){
                        $("#salesTargetInput-form-detail-customerid").focus();
                    },
                    onClose:function(){
                        $('#salesTargetInput-dialog-detail-content').dialog("destroy");
                    }
                });
                $('#salesTargetInput-dialog-detail-content').dialog("open");
            }
        },
        </security:authorize>
        <security:authorize url="/module/salestargetinput/salesTargetInputViewBtn.do">
        {
            type: 'button-view',
            handler: function(){
                var data = $("#salesTargetInputListPage-table-detail-detail").datagrid('getSelected');
                if(data == null){
                    $.messager.alert("提醒","请选择一条记录");
                    return false;
                }
                $('<div id="salesTargetInput-dialog-detail-content"></div>').appendTo('#salesTargetInput-dialog-detail');
                $('#salesTargetInput-dialog-detail-content').dialog({
                    title: '销售目标【详情】',
                    width: 800,
                    height: 300,
                    //fit:true,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'module/salestargetinput/showSalesTargetInputViewPage.do?id='+data.id,
                    modal: true,
                    onClose:function(){
                        $('#salesTargetInput-dialog-detail-content').dialog("destroy");
                    }
                });
                $('#salesTargetInput-dialog-detail-content').dialog("open");
            }
        },
        </security:authorize>
        <security:authorize url="/module/salestargetinput/salesTargetInputEditBtn.do">
        {
            type: 'button-edit',
            handler: function(){
                var data = $("#salesTargetInputListPage-table-detail-detail").datagrid('getSelected');
                if(data == null){
                    $.messager.alert("提醒","请选择一条记录");
                    return false;
                }
                $('<div id="salesTargetInput-dialog-detail-content"></div>').appendTo('#salesTargetInput-dialog-detail');
                $('#salesTargetInput-dialog-detail-content').dialog({
                    title: '销售目标【详情】',
                    width: 800,
                    height: 300,
                    //fit:true,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'module/salestargetinput/showSalesTargetInputEditPage.do?id='+data.id,
                    modal: true,
                    onClose:function(){
                        $('#salesTargetInput-dialog-detail-content').dialog("destroy");
                    }
                });
                $('#salesTargetInput-dialog-detail-content').dialog("open");
            }
        },
        </security:authorize>
        <security:authorize url="/module/salestargetinput/salesTargetInputDeleteBtn.do">
        {
            type: 'button-delete',
            handler: function(){
                $.messager.confirm("提醒","是否删除当前销售目标？",function(r){
                    if(r){
                        var rows = $("#salesTargetInputListPage-table-detail-detail").datagrid("getChecked");
                        var idArr=new Array();
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].status=='3') {
                                $.messager.alert("提醒","编号："+rows[i].id+"不能被删除。<br/>原因：审核通过的销售目标不能被删除<br/>")
                                return false;
                            }
                            if(rows[i].status=='4') {
                                $.messager.alert("提醒","编号："+rows[i].id+"不能被删除。<br/>原因：关闭的销售目标不能被删除<br/>")
                                return false;
                            }
                            idArr.push(rows[i].id);
                        }
                        if(idArr.length>0){
                            loading("正在删除中,请稍后..");
                            $.ajax({
                                url :'module/salestargetinput/deleteSalesTargetInputMore.do',
                                type:'post',
                                data:{idarrs:idArr.join(',')},
                                dataType:'json',
                                success:function(json){
                                    loaded();
                                    var htmlsb=new Array();
                                    htmlsb.push("删除");
                                    if(json.flag){
                                        htmlsb.push("成功");
                                    }else{
                                        htmlsb.push("失败");
                                    }
                                    htmlsb.push("。");
                                    if(json.isuccess!=null || json.ifailure!=null){
                                        htmlsb.push("详细如下：</br>");
                                        if(json.isuccess>0 && json.succssids!=null){
                                            htmlsb.push("删除成功编号:</br>");
                                            htmlsb.push(json.succssids);
                                            htmlsb.push("</br>");
                                        }
                                        if(json.ifailure>0 && json.failids!=null) {
                                            htmlsb.push("删除失败编号:</br>");
                                            htmlsb.push(json.failids);
                                            htmlsb.push("</br>");
                                        }
                                    }
                                    if(json.msg!=null && $.trim(json.msg)!=""){
                                        htmlsb.push("处理时提示消息:</br>");
                                        htmlsb.push(json.msg);
                                        htmlsb.push("</br>");
                                    }
                                    $.messager.alert("提醒", htmlsb.join(''));
                                    if(json.flag) {
                                        $("#salesTargetInputListPage-table-detail-detail").datagrid("reload");
                                    }
                                },
                                error:function(){
                                    $.messager.alert("错误", "删除出错");
                                    loaded();
                                },
                                complete:function(XHR, TS){
                                    loaded();
                                }
                            });
                        }else{
                            $.messager.alert("提醒","抱歉，未找到可以被审核的销售目标")
                            return false;
                        }
                    }
                });
            }
        },
        </security:authorize>
        <security:authorize url="/module/salestargetinput/salesTargetInputAuditBtn.do">
        {
            type: 'button-audit',
            handler: function(){
                $.messager.confirm("提醒","是否审核选中的销售目标？",function(r){
                    if(r){
                        var rows = $("#salesTargetInputListPage-table-detail-detail").datagrid("getChecked");
                        var idArr=new Array();
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].status=='3') {
                                $.messager.alert("提醒","编号："+rows[i].id+"不能被审核。<br/>原因：审核通过的销售目标不能被审核<br/>")
                                return false;
                            }
                            if(rows[i].status=='4') {
                                $.messager.alert("提醒","编号："+rows[i].id+"不能被审核。<br/>原因：关闭的销售目标不能被审核<br/>")
                                return false;
                            }
                            idArr.push(rows[i].id);
                        }
                        if(idArr.length>0){
                            loading("审核中..");
                            $.ajax({
                                url :'module/salestargetinput/auditSalesTargetInput.do',
                                type:'post',
                                data:{idarrs:idArr.join(',')},
                                dataType:'json',
                                success:function(json){
                                    loaded();
                                    var htmlsb=new Array();
                                    htmlsb.push("审核");
                                    if(json.flag){
                                        htmlsb.push("成功");
                                    }else{
                                        htmlsb.push("失败");
                                    }
                                    htmlsb.push("。");
                                    if(json.isuccess!=null || json.ifailure!=null){
                                        htmlsb.push("详细如下：</br>");
                                        if(json.isuccess>0 && json.succssids!=null){
                                            htmlsb.push("审核成功编号:</br>");
                                            htmlsb.push(json.succssids);
                                            htmlsb.push("</br>");
                                        }
                                        if(json.ifailure>0 && json.failids!=null) {
                                            htmlsb.push("审核失败编号:</br>");
                                            htmlsb.push(json.failids);
                                            htmlsb.push("</br>");
                                        }
                                    }
                                    if(json.msg!=null && $.trim(json.msg)!=""){
                                        htmlsb.push("处理时提示消息:</br>");
                                        htmlsb.push(json.msg);
                                        htmlsb.push("</br>");
                                    }
                                    $.messager.alert("提醒", htmlsb.join(''));
                                    if(json.flag) {
                                        $("#salesTargetInputListPage-table-detail-detail").datagrid("reload");
                                    }
                                },
                                error:function(){
                                    $.messager.alert("错误", "审核出错");
                                    loaded();
                                },
                                complete:function(XHR, TS){
                                    loaded();
                                }
                            });
                        }else{
                            $.messager.alert("提醒","抱歉，未找到可以被审核的销售目标")
                            return false;
                        }
                    }
                });
            }
        },
        </security:authorize>
        <security:authorize url="/module/salestargetinput/salesTargetInputOppauditBtn.do">
        {
            type:'button-oppaudit',
            handler:function(){
            	var rows = $("#salesTargetInputListPage-table-detail-detail").datagrid("getChecked");
                 if(rows.length == 0){
                 	$.messager.alert("提醒","请选择要反审的记录!");
                 	return false;
                 }
                $.messager.confirm("提醒","是否反审销售目标？",function(r){
                    if(r){
                        var idArr=new Array();
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].status!='3') {
                                $.messager.alert("提醒","编号："+rows[i].id+"不能被反审。<br/>原因：审核通过的销售目标才能被反审<br/>")
                                return false;
                            }
                            idArr.push(rows[i].id);
                        }
                        if(idArr.length>0){
                        	loading("正在反审中，请稍候..");
                            $.ajax({
                                url :'module/salestargetinput/oppauditSalesTargetInput.do',
                                type:'post',
                                data:{idarrs:idArr.join(',')},
                                dataType:'json',
                                success:function(json){
                                    loaded();
                                    var htmlsb=new Array();
                                    htmlsb.push("反审");
                                    if(json.flag){
                                        htmlsb.push("成功");
                                    }else{
                                        htmlsb.push("失败");
                                    }
                                    htmlsb.push("。");
                                    if(json.isuccess!=null || json.ifailure!=null){
                                        htmlsb.push("详细如下：</br>");
                                        if(json.isuccess>0 && json.succssids!=null){
                                            htmlsb.push("反审成功编号:</br>");
                                            htmlsb.push(json.succssids);
                                            htmlsb.push("</br>");
                                        }
                                        if(json.ifailure>0 && json.failids!=null) {
                                            htmlsb.push("反审失败编号:</br>");
                                            htmlsb.push(json.failids);
                                            htmlsb.push("</br>");
                                        }
                                    }
                                    if(json.msg!=null && $.trim(json.msg)!=""){
                                        htmlsb.push("处理时提示消息:</br>");
                                        htmlsb.push(json.msg);
                                        htmlsb.push("</br>");
                                    }
                                    $.messager.alert("提醒", htmlsb.join(''));
                                    if(json.flag) {
                                        $("#salesTargetInputListPage-table-detail-detail").datagrid("reload");
                                    }
                                },
                                error:function(){
                                    $.messager.alert("错误", "反审出错");
                                    loaded();
                                },
                                complete:function(XHR, TS){
                                    loaded();
                                }
                            });
                        }else{
                            $.messager.alert("提醒","抱歉，未找到可以被反审的销售目标")
                            return false;
                        }
                    }
                });
            }
        },
        </security:authorize>
        {}
    ],
    buttons:[
        <security:authorize url="/module/salestargetinput/salesTargetInputExportBtn.do">
        {
            id:'button-export-excel',
            name:'导出',
            iconCls:'button-export',
            handler: function(){
                var rows =  $("#salesTargetInputListPage-table-detail-detail").datagrid('getChecked');

                //查询参数直接添加在url中
                var idarrs=new Array();
                if(null !=rows && rows.length>0){
                    for(var i=0;i<rows.length;i++){
                        if(rows[i].id && rows[i].id!=""){
                            idarrs.push(rows[i].id);
                        }
                    }
                }
                $("#salesTargetInputListPage-buttons-exportclick").Excel('export',{
                    queryForm: "#salesTargetInputListPage-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                    type:'exportUserdefined',
                    name:'销售目标列表',
                    fieldParam:{idarrs:idarrs.join(",")},
                    url:'module/salestargetinput/exportSalesTargetInputData.do'
                });
                $("#salesTargetInputListPage-buttons-exportclick").trigger("click");
            }
        },
        </security:authorize>
        <security:authorize url="/module/salestargetinput/salesTargetInputImportBtn.do">
        {
            id:'button-import-excel',
            name:'导入',
            iconCls:'button-import',
            handler: function(){
                var importparam="批量导入销售目标<br/>";
                importparam=importparam+"<a href=\"basefiles/exceltemplet/SalesTargetInputTempletSample.xls\">"+"点击下载导入模板样式</a><br/><br/>";
                $("#salesTargetInputListPage-buttons-importclick").Excel('import',{
                    type:'importUserdefined',
                    version:'1',
                    importparam:importparam,
                    importPageRequestParam:{hideExportTip:true},
                    url:'module/salestargetinput/importSalesTargetInputData.do',
                    onClose: function(){ //导入成功后窗口关闭时操作，
                        $("#salesTargetInputListPage-table-detail").datagrid('reload');
                        $("#salesTargetInputListPage-table-detail").datagrid('clearSelections');
                    }
                });
                $("#salesTargetInputListPage-buttons-importclick").trigger("click");
            }
        },
        </security:authorize>
        {}
    ],
    model: 'bill',
    type: 'list',
    tname: 't_js_dept_income'
});

var initQueryJSON =  $("#salesTargetInputListPage-form-ListQuery").serializeJSON();
$(function(){
    $("#salesTargetInputListPage-table-detail-detail").datagrid({
        authority:tableColJson,
        frozenColumns:tableColJson.frozen,
        columns:tableColJson.common,
        fit:true,
        method:'post',
        rownumbers:true,
        sortName:'id',
        sortOrder:'desc',
        pagination:true,
        idField:'id',
        singleSelect:false,
        checkOnSelect:true,
        selectOnCheck:true,
        pageSize:100,
        queryParams:initQueryJSON,
        toolbar:'#salesTargetInputListPage-form-div',
        url: 'module/salestargetinput/getSalesTargetInputListPageData.do',
        onDblClickRow:function(rowIndex, rowData){
            $('<div id="salesTargetInput-dialog-detail-content"></div>').appendTo('#salesTargetInput-dialog-detail');
            $('#salesTargetInput-dialog-detail-content').dialog({
                title: '销售目标【详情】',
                width: 800,
                height: 300,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'module/salestargetinput/showSalesTargetInputViewPage.do?id='+rowData.id,
                modal: true,
                onClose:function(){
                    $('#salesTargetInput-dialog-detail-content').dialog("destroy");
                }
            });
            $('#salesTargetInput-dialog-detail-content').dialog("open");
        },
        onLoadSuccess:function(){
            var footerrows = $(this).datagrid('getFooterRows');
            if(null!=footerrows && footerrows.length>0){
            	listFooterobject = footerrows[0];
            }
        },
        onCheckAll:function(){
            STLCountTotalAmount();
        },
        onUncheckAll:function(){
            STLCountTotalAmount();
        },
        onCheck:function(){
            STLCountTotalAmount();
        },
        onUncheck:function(){
            STLCountTotalAmount();
        }
    }).datagrid("columnMoving");

    //客户
    $("#salesTargetInputListPage-form-customerid").widget({
        name:'t_salestarget_input',
        col:'customerid',
        singleSelect:true,
        width:225,
        onlyLeafCheck:true,
        onSelect:function(){
        }
    });

    $("#salesTargetInputListPage-form-customersort").widget({ //分类
        referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
        singleSelect:true,
        width:150,
        onlyLeafCheck:false,
        view: true
    });

    $("#salesTargetInputListPage-form-salesuserid").widget({ //客户业务员
        referwid:'RL_T_BASE_PERSONNEL_SELLER',
        singleSelect:true,
        width:150,
        onlyLeafCheck:false,
        view: true
    });

    //品牌
	$("#salesTargetInputListPage-form-brandid").widget({
        name:'t_salestarget_input',
        col:'brandid',
		singleSelect:true,
		width:150,
		onlyLeafCheck:true,
		onSelect:function(){
		}
	});

    $("#salesTargetInput-query-List").click(function(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#salesTargetInputListPage-form-ListQuery").serializeJSON();
        $("#salesTargetInputListPage-table-detail-detail").datagrid("load",queryJSON);
    });

    $("#salesTargetInput-query-reloadList").click(function(){
        $("#salesTargetInputListPage-form-salesuserid").widget("clear");
        $("#salesTargetInputListPage-form-costsort").widget("clear");
        $("#salesTargetInputListPage-form-brandid").widget("clear");
        $("#salesTargetInputListPage-form-ListQuery")[0].reset();
        var queryJSON =  $("#salesTargetInputListPage-form-ListQuery").serializeJSON();
        $("#salesTargetInputListPage-table-detail-detail").datagrid("load",queryJSON);
    });
    $("#salesTargetInputListPage-form-customersort-empty").click(function(){
    	if($(this).prop("checked")){
    		$("#salesTargetInputListPage-form-customersort").widget("clear");
    	}else{
    		$("#salesTargetInputListPage-form-customersort").widget("clear"); 		
    	}
    });
});
var listFooterobject=null;
function STLCountTotalAmount(){
	var rows =  $("#salesTargetInputListPage-table-detail-detail").datagrid('getChecked');
	if(null==rows || rows.length==0){
   		var foot=[];
		if(null!=listFooterobject){
    		foot.push({id:'选中金额',amount:0.00});
    		foot.push(listFooterobject);
		}
		$("#salesTargetInputListPage-table-detail-detail").datagrid("reloadFooter",foot);
   		return false;
	}
	var firstsalestarget = 0;
    var firstgrossprofit = 0;
    var secondsalestarget = 0;
    var secondgrossprofit = 0;
	
	for(var i=0;i<rows.length;i++){
        firstsalestarget = Number(firstsalestarget)+Number(rows[i].firstsalestarget == undefined ? 0 : rows[i].firstsalestarget);
        firstgrossprofit = Number(firstgrossprofit)+Number(rows[i].firstgrossprofit == undefined ? 0 : rows[i].firstgrossprofit);
        secondsalestarget = Number(secondsalestarget)+Number(rows[i].secondsalestarget == undefined ? 0 : rows[i].secondsalestarget);
        secondgrossprofit = Number(secondgrossprofit)+Number(rows[i].secondgrossprofit == undefined ? 0 : rows[i].secondgrossprofit);
	}
	var foot=[{id:'选中金额',firstsalestarget:firstsalestarget,firstgrossprofit:firstgrossprofit,secondsalestarget:secondsalestarget,secondgrossprofit:secondgrossprofit	}];
	if(null!=listFooterobject){
		foot.push(listFooterobject);
	}
	$("#salesTargetInputListPage-table-detail-detail").datagrid("reloadFooter",foot);
}

function calcTargetRate(targetid,mlid,rateid){
    var $targetObj=$("#"+targetid);
    var $mlObj=$("#"+mlid);
    var $rateObj=$("#"+rateid);
    var targetV=Number($targetObj.val() || 0);
    var mlV=Number($mlObj.val() || 0);
    var rateV=0;
    if(targetV==0){
        $rateObj.val(formatterMoney(0.00));
        return true;
    }
    rateV=Number(mlV/targetV*100);
    $rateObj.val(formatterMoney(rateV));
}
</script>
</body>
</html>
