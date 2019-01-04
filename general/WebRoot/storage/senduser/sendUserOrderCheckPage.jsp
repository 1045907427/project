<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>核对发货单</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="sendUserOrderCheckPage-table"></table>
<div id="sendUserOrderCheckPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG" id="sendUserOrderCheckPage-buttons">
    </div>
    <div>
        <form action="" method="post" id="sendUserOrderCheckPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>单据编号:</td>
                    <td><input type="text"  name="sourceid" style="width:150px"/></td>
                    <td>仓库:</td>
                    <td><input type="text" id="sendUserOrderCheckPage-storageid" name="storageid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td>订单编号:</td>
                    <td><input type="text"  name="saleorderid" style="width:225px"/></td>
                    <td colspan="2"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="sendUserOrderCheckPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="sendUserOrderCheckPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="sendUserOrderCheckPage-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="sendUserOrderCheckPage-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#sendUserOrderCheckPage-form").serializeJSON();
    var loadData = false;
    var manufacturerdataJson = $("#sendUserOrderCheckPage-table").createGridColumnLoad({
        name :'',
        frozenCol : [[]],
        commonCol : [[
            {field:'ck', checkbox:true},
            {field:'sourceid',title:'单据编号',width:150,sortable:true},
            {field:'businessdate',title:'业务日期',width:125,sortable:true},
            {field:'saleorderid',title:'订单编号',width:150,sortable:true},
            {field:'storageid',title:'仓库编号',width:125,sortable:true,hidden:true},
            {field:'storagename',title:'仓库名称',width:125,sortable:true},
            {field:'num',title:'总数量',width:125,sortable:true},
            {field:'totalamount',title:'总金额',width:125,sortable:true},
            {field:'totalbox',title:'总箱数',width:125,sortable:true},
            {field:'totalvolume',title:'总体积',width:125,sortable:true},
            {field:'totalweight',title:'总重量',width:125,sortable:true},
        ]]
    });

    function confirmStorageWidget(){
        $("#storage-hidden-storager").widget({
            referwid:'RL_T_BASE_PERSONNEL_STORAGER',
            width:160,
            col:'name',
            singleSelect:false,
        });
    }

    $(function() {
        $("#sendUserOrderCheckPage-buttons").buttonWidget({
            initButton:[
            ],
            buttons:[
                <security:authorize url="/storage/senduser/SendUserOrderAssigned1.do">
                {
                    id:'button-editDispatchUser',
                    name:'核对',
                    iconCls:'button-edit',
                    handler:function(){
                        var fn=function(r){
                            if(r){
                                var rowArr = $("#sendUserOrderCheckPage-table").datagrid("getChecked");
                                var list = new Array();
                                var ids = null;
                                if(rowArr.length==0){
                                    $.messager.alert("提醒","请选择数据");
                                    return false;
                                }
                                var orders=JSON.stringify(rowArr);
                                var storager=$("#storage-hidden-storager").widget("getValue");
                                if(storager==null || storager==""){
                                    $.messager.alert("提醒","请选择发货人");
                                    return false;
                                }
                                loading("数据处理中..");
                                $.ajax({
                                    url:'storage/senduser/SendUserOrderAssigned.do',
                                    dataType:'json',
                                    type:'post',
                                    data:{orders:orders,storager:storager,type:'1'},
                                    success:function(json){
                                        loaded();
                                        if(null!=json.msg){
                                            $.messager.alert("提醒","分配完成。"+json.msg);
                                        }
                                        else{
                                            $.messager.alert("提醒","分配完成。");
                                        }
                                        var queryJSON = $("#sendUserOrderCheckPage-form").serializeJSON();
                                        $("#sendUserOrderCheckPage-table").datagrid("load",queryJSON);
                                    },
                                    error:function(){
                                        loaded();
                                    }
                                });
                            }
                        };
                        $.messager.confirm({
                            title:'核对',
                            onOpen:function(){
                                confirmStorageWidget()
                            },
                            msg:"<span style=\"float: left;\">选择核对人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
                            fn:fn
                        });
                    }
                },
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_order'
        });




        $("#sendUserOrderCheckPage-table").datagrid({
            authority: manufacturerdataJson,
            frozenColumns: manufacturerdataJson.frozen,
            columns: manufacturerdataJson.common,
            fit: true,
            rownumbers: true,
            pagination: true,
            pageSize: 100,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            url: 'storage/senduser/showOrderList.do?billtype=1',
            queryParams: initQueryJSON,
            toolbar: '#sendUserOrderCheckPage-toolbar',
            onBeforeLoad: function () {
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },

        }).datagrid('columnMoving');

        //查询
        $("#sendUserOrderCheckPage-queay-queryList").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#sendUserOrderCheckPage-form").serializeJSON();
            $("#sendUserOrderCheckPage-table").datagrid("load", queryJSON);
        });
        //重置
        $("#sendUserOrderCheckPage-queay-reloadList").click(function () {
            $("#sendUserOrderCheckPage-storageid").widget("clear");
            $("#sendUserOrderCheckPage-form").form("reset");
            var queryJSON = $("#sendUserOrderCheckPage-form").serializeJSON();
            $("#sendUserOrderCheckPage-table").datagrid("load", queryJSON);
        });

        $("#sendUserOrderCheckPage-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        setTimeout(function(){
            loadData = true;
        },100);
    })
</script>
</html>

