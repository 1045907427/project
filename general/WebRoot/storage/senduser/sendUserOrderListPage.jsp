<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发货单据列表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="sendUserOrderListPage-table"></table>
<div id="sendUserOrderListPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/storage/senduser/showSendUserOrderCheckPage.do">
            <a href="javaScript:void(0);" id="sendUserOrderListPage-button-check" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">核对</a>
        </security:authorize>
        <security:authorize url="/storage/senduser/showSendUserOrderLoadedPage.do">
            <a href="javaScript:void(0);" id="sendUserOrderListPage-button-loaded" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">发货</a>
        </security:authorize>
        <security:authorize url="/storage/senduser/showSendUserOrderUnloadedPage.do">
            <a href="javaScript:void(0);" id="sendUserOrderListPage-button-unloaded" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">卸货</a>
        </security:authorize>
        <security:authorize url="/storage/senduser/deleteSendUserOrder.do">
            <a href="javaScript:void(0);" id="sendUserOrderListPage-button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'">删除数据</a>
        </security:authorize>

    </div>
    <div>
        <form action="" method="post" id="sendUserOrderListPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>单据编号:</td>
                    <td><input type="text"  name="senduserid" style="width:150px"/></td>
                    <td>仓库:</td>
                    <td><input type="text" id="sendUserOrderListPage-storageid" name="storageid" style="width:150px"/></td>
                <tr>
                    <td>单据类型:</td>
                    <td>
                        <select  name="billtype" style="width:220px;">
                            <option value="" selected="selected"></option>
                            <option value="1">核对</option>
                            <option value="2">发货</option>
                            <option value="3">卸货</option>
                        </select>
                    </td>
                    <td>订单编号:</td>
                    <td><input type="text"  name="saleorderid" style="width:150px"/></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="sendUserOrderListPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="sendUserOrderListPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="sendUserOrderListPage-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="sendUserOrderListPage-dialog"></div>
<div class="easyui-menu" id="sendUserOrderListPage-contextMenu" style="display: none;">
    <div id="sendUserOrderListPage-addRow" data-options="iconCls:'button-add'">添加</div>
    <div id="sendUserOrderListPage-editRow" data-options="iconCls:'button-edit'">编辑</div>
    <div id="sendUserOrderListPage-removeRow" data-options="iconCls:'button-delete'">删除</div>
</div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#sendUserOrderListPage-form").serializeJSON();
    var loadData = false;
    var SMR_footerobject = null;
    var manufacturerdataJson = $("#sendUserOrderListPage-table").createGridColumnLoad({
        name :'',
        frozenCol : [[
            {field:'ck',checkbox:true,isShow:true}
        ]],
        commonCol : [[
            {field:'sourceid',title:'单据编号',width:150,sortable:true},
            {field:'businessdate',title:'业务日期',width:125,sortable:true},
            {field:'saleorderid',title:'订单编号',width:150,sortable:true},
            {field:'storageid',title:'仓库编号',width:125,sortable:true},
            {field:'storagename',title:'仓库名称',width:125,sortable:true},
            {field:'billtype',title:'单据类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "核对";
                    }else if(dateValue=='2'){
                        return "发货";
                    }else if(dateValue=='3'){
                        return "卸货";
                    }

                }
            },
            {field:'num',title:'总数量',width:125,sortable:true},
            {field:'totalamount',title:'总金额',width:125,sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'totalbox',title:'总箱数',width:125,sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'totalvolume',title:'总体积',width:125,sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'totalweight',title:'总重量',width:125,sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'senduserid',title:'发货人编号',width:125,sortable:true,hidden:true},
            {field:'checkusername',title:'核对上车发货人名称',width:125,sortable:true},
            {field:'loadedusername',title:'直接上车发货人名称',width:125,sortable:true},
            {field:'unloadedusername',title:'卸货发货人名称',width:125,sortable:true},
            {field:'sendtime',title:'发货时间',width:125,sortable:true},
            {field:'remark',title:'备注',width:125,sortable:true},
        ]]
    });

    $(function() {
        $("#sendUserOrderListPage-table").datagrid({
            authority: manufacturerdataJson,
            frozenColumns: manufacturerdataJson.frozen,
            columns: manufacturerdataJson.common,
            sortName: 'businessdate',
            sortOrder: 'asc',
            fit:true,
            rownumbers:true,
            pagination: true,
            pageSize:100,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            url: 'storage/senduser/showSendUserOrderList.do',
            queryParams: initQueryJSON,
            toolbar: '#sendUserOrderListPage-toolbar',
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },
        }).datagrid('columnMoving');

        $("#sendUserOrderListPage-button-check").click(function () {
            var url = "storage/senduser/showSendUserOrderCheckPage.do";
            top.addTab(url, '核对统计');
        });

        $("#sendUserOrderListPage-button-loaded").click(function () {
            var url = "storage/senduser/showSendUserOrderLoadedPage.do";
            top.addTab(url, '发货统计');
        });
        $("#sendUserOrderListPage-button-unloaded").click(function () {
            var url = "storage/senduser/showSendUserOrderUnloadedPage.do";
            top.addTab(url, '卸货统计');
        });

        $("#sendUserOrderListPage-button-delete").click(function () {
            var rowArr = $("#sendUserOrderListPage-table").datagrid("getChecked");
            var list = new Array();
            if(rowArr.length==0){
                $.messager.alert("提醒","请选择数据");
                return false;
            }
            for(var i=0;i<rowArr.length;i++){
                var order = new Object();
                order.sourceid =rowArr[i].sourceid;
                order.billtype = rowArr[i].billtype;
                order.senduserid = rowArr[i].senduserid;
                list[list.length] = order;
            }
            var orders=JSON.stringify(list);
            $.messager.confirm("提醒","确定删除选中数据相关单据编号下的所有明细？",function(r){
                if(r){
                    loading("数据处理中..");
                    $.ajax({
                        url:'storage/senduser/deleteSendUserOrder.do',
                        dataType:'json',
                        type:'post',
                        data:{orders:orders},
                        success:function(json){
                            loaded();
                            if(null!=json.msg){
                                $.messager.alert("提醒","删除完成。"+json.msg);
                            }
                            else{
                                $.messager.alert("提醒","删除完成。");
                            }
                            refresh();
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("提醒","删除失败。");
                        }
                    });
                }
            });
        });

        //查询
        $("#sendUserOrderListPage-queay-queryList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#sendUserOrderListPage-form").serializeJSON();
            $("#sendUserOrderListPage-table").datagrid("load",queryJSON);
        });
        //重置
        $("#sendUserOrderListPage-queay-reloadList").click(function(){
            $("#sendUserOrderListPage-storageid").widget("clear");
            $("#sendUserOrderListPage-form").form("reset");
            var queryJSON = $("#sendUserOrderListPage-form").serializeJSON();
            $("#sendUserOrderListPage-table").datagrid("load",queryJSON);
        });

        $("#sendUserOrderListPage-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });
        setTimeout(function(){
            loadData = true;
        },100);
    })
    function refresh(){
        var queryJSON = $("#sendUserOrderListPage-form").serializeJSON();
        $("#sendUserOrderListPage-table").datagrid("load",queryJSON);
    }
</script>
</html>

