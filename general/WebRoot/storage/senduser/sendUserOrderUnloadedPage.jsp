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
<table id="sendUserOrderUnloadedPage-table"></table>
<div id="sendUserOrderUnloadedPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG" id="sendUserOrderUnloadedPage-buttons">
    </div>
    <div>
        <form action="" method="post" id="sendUserOrderUnloadedPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>单据编号:</td>
                    <td><input type="text"  name="sourceid" style="width:150px"/></td>
                    <td>仓库:</td>
                    <td><input type="text" id="sendUserOrderUnloadedPage-storageid" name="storageid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td colspan="4"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="sendUserOrderUnloadedPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="sendUserOrderUnloadedPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="sendUserOrderUnloadedPage-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="sendUserOrderUnloadedPage-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#sendUserOrderUnloadedPage-form").serializeJSON();
    var loadData = false;
    var manufacturerdataJson = $("#sendUserOrderUnloadedPage-table").createGridColumnLoad({
        name :'',
        frozenCol : [[]],
        commonCol : [[
            {field:'ck', checkbox:true},
            {field:'sourceid',title:'单据编号',width:150,sortable:true},
            {field:'businessdate',title:'业务日期',width:125,sortable:true},
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
        $("#sendUserOrderUnloadedPage-buttons").buttonWidget({
            initButton:[
            ],
            buttons:[
                <security:authorize url="/storage/senduser/SendUserOrderAssigned4.do">
                {
                    id:'button-editDispatchUser',
                    name:'卸货',
                    iconCls:'button-edit',
                    handler:function(){
                        var fn=function(r){
                            if(r){
                                var rowArr = $("#sendUserOrderUnloadedPage-table").datagrid("getChecked");
                                var list = new Array();
                                var ids = null;
                                if(rowArr.length==0){
                                    $.messager.alert("提醒","请选择数据");
                                    return false;
                                }
                                var orders=JSON.stringify(rowArr);
                                var storager=$("#storage-hidden-storager").widget("getValue");
                                if(storager==null || storager==""){
                                    $.messager.alert("提醒","请选择卸货人");
                                    return false;
                                }
                                loading("数据处理中..");
                                $.ajax({
                                    url:'storage/senduser/SendUserOrderAssigned.do',
                                    dataType:'json',
                                    type:'post',
                                    data:{orders:orders,storager:storager,type:'3'},
                                    success:function(json){
                                        loaded();
                                        if(null!=json.msg){
                                            $.messager.alert("提醒","分配完成。"+json.msg);
                                        }
                                        else{
                                            $.messager.alert("提醒","分配完成。");
                                        }
                                        var queryJSON = $("#sendUserOrderUnloadedPage-form").serializeJSON();
                                        $("#sendUserOrderUnloadedPage-table").datagrid("load",queryJSON);
                                    },
                                    error:function(){
                                        loaded();
                                    }
                                });
                            }
                        };
                        $.messager.confirm({
                            title:'卸货',
                            onOpen:function(){
                                confirmStorageWidget()
                            },
                            msg:"<span style=\"float: left;\">选择卸货人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
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




        $("#sendUserOrderUnloadedPage-table").datagrid({
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
            url: 'storage/senduser/showOrderList.do?billtype=3',
            queryParams: initQueryJSON,
            toolbar: '#sendUserOrderUnloadedPage-toolbar',
            onBeforeLoad: function () {
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },

        }).datagrid('columnMoving');

        //查询
        $("#sendUserOrderUnloadedPage-queay-queryList").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#sendUserOrderUnloadedPage-form").serializeJSON();
            $("#sendUserOrderUnloadedPage-table").datagrid("load", queryJSON);
        });
        //重置
        $("#sendUserOrderUnloadedPage-queay-reloadList").click(function () {
            $("#sendUserOrderUnloadedPage-storageid").widget("clear");
            $("#sendUserOrderUnloadedPage-form").form("reset");
            var queryJSON = $("#sendUserOrderUnloadedPage-form").serializeJSON();
            $("#sendUserOrderUnloadedPage-table").datagrid("load",queryJSON);
        });

        $("#sendUserOrderUnloadedPage-storageid").widget({
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

