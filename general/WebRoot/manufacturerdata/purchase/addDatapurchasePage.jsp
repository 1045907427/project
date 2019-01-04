<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>厂家对接采购明细数据分配</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="datapurchaseAddPage-table"></table>
<div id="datapurchaseAddPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/manufacturerdata/dataPurchaseDefultAssigned.do">
            <a href="javaScript:void(0);" id="datapurchaseAddPage-button-defultAssigned" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">默认分配</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/pumpAssignedDataPurchase.do">
            <a href="javaScript:void(0);" id="datapurchaseAddPage-button-pumpAssigned" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">手动分配</a>
        </security:authorize>
    </div>
    <div>
        <form action="" method="post" id="datapurchaseAddPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>对接方:</td>
                    <td><input type="text" id="datapurchaseAddPage-markid" name="markid" style="width:200px"/></td>
                    <td>供应商名称:</td>
                    <td><input type="text" id="datapurchaseAddPage-supplierid" name="supplierid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td>单据编号:</td>
                    <td><input type="text" id="datapurchaseAddPage-id" name="id" style="width:220px"/></td>
                    <td>单据类型:</td>
                    <td>
                        <select  name="billtype" style="width:200px;">
                            <option value="" selected="selected"></option>
                            <option value="1">采购入库</option>
                            <option value="2">采购退货</option>
                        </select>
                    </td>
                    <td>所属仓库:</td>
                    <td><input type="text" id="datapurchaseAddPage-storageid" name="storageid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td colspan="4"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="datapurchaseAddPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="datapurchaseAddPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="datapurchaseAddPage-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="datapurchaseAddPage-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#datapurchaseAddPage-form").serializeJSON();
    var loadData = false;
    var manufacturerdataJson = $("#datapurchaseAddPage-table").createGridColumnLoad({
        name :'',
        frozenCol : [[]],
        commonCol : [[
            {field:'ck', checkbox:true},
            {field:'id',title:'单据编号',width:150,sortable:true},
            {field:'billtype',title:'单据类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "采购入库单";
                    }else if(dateValue=='2'){
                        return "采购退货出库";
                    }
                }
            },
            {field:'businessdate',title:'业务日期',width:125,sortable:true},
            {field:'sourceid',title:'来源单据号',width:130,sortable:true},
            {field:'storageid',title:'出入库仓库',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.storagename;
                }
            },
            {field:'supplierid',title:'供应商编号',width:60,sortable:true},
            {field:'suppliername',title:'供应商名称',width:150,isShow:true},
            {field:'taxamount',title:'出入库金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'auditusername',title:'审核人',width:60,sortable:true,hidden:true},
            {field:'audittime',title:'审核时间',width:120,sortable:true,hidden:true},
            {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true,hidden:true},
            {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true,hidden:true},
            {field:'phprinttimes',title:'配货打印次数',width:80,hidden:true,hidden:true},
            {field:'remark',title:'备注',width:80,sortable:true},
            {field:'addusername',title:'制单人',width:60,sortable:true,hidden:true},
            {field:'addtime',title:'制单时间',width:120,sortable:true,hidden:true},
            {field:'isrefer',title:'是否被参照',width:60,hidden:true,sortable:true,hidden:true,
                formatter:function(value,rowData,index){
                    if(value=='0'){
                        return "未参照";
                    }else if(value=='1'){
                        return "已参照";
                    }
                }
            },
            {field:'defultMarkid',title:'默认对接方',width:200,sortable:true},
        ]]
    });


    $(function(){
        $("#datapurchaseAddPage-table").datagrid({
            authority:manufacturerdataJson,
            frozenColumns: manufacturerdataJson.frozen,
            columns:manufacturerdataJson.common,
            fit:true,
            rownumbers:true,
            pagination: true,
            pageSize:100,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter:true,
            url: 'manufacturerdata/showPurchaseStorageDataList.do',
            queryParams:initQueryJSON,
            toolbar:'#datapurchaseAddPage-toolbar',
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },
        }).datagrid('columnMoving');

        //查询
        $("#datapurchaseAddPage-queay-queryList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#datapurchaseAddPage-form").serializeJSON();
            $("#datapurchaseAddPage-table").datagrid("load",queryJSON);
        });
        //重置
        $("#datapurchaseAddPage-queay-reloadList").click(function(){
            var queryJSON = $("#datapurchaseAddPage-form").serializeJSON();
            $("#datapurchaseAddPage-table").datagrid("load",queryJSON);
        });

        $("#datapurchaseAddPage-button-defultAssigned").click(function(){//默认分配
            defultAssigned();
        });
        $("#datapurchaseAddPage-button-pumpAssigned").click(function(){//手动分配
            showPumpAssignDatasalesPage();
        });

        $("#datapurchaseAddPage-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        $("#datapurchaseAddPage-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        $("#datapurchaseAddPage-markid").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:200,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        setTimeout(function(){
            loadData = true;
        },100);
    })

    function defultAssigned(){
        var rowArr = $("#datapurchaseAddPage-table").datagrid("getChecked");
        var list = new Array();
        var ids = null;
        if(rowArr.length==0){
            $.messager.alert("提醒","请选择数据");
            return false;
        }
        for(var i=0;i<rowArr.length;i++){
            var order = new Object();
            order.id =rowArr[i].id;
            order.billtype = rowArr[i].billtype;
            list[list.length] = order;
        }
        var orders=JSON.stringify(list);
        loading("数据处理中..");
        $.ajax({
            url:'manufacturerdata/dataPurchaseDefultAssigned.do',
            dataType:'json',
            type:'post',
            data:{orders:orders},
            success:function(json){
                loaded();
                if(null!=json.msg){
                    $.messager.alert("提醒","分配完成。"+json.msg);
                }
                else{
                    $.messager.alert("提醒","分配完成。");
                }
                refresh();
            },
            error:function(){
                loaded();
            }
        });

    }

    function pumpAssigned(){
        var flag = $("#datasales-form-pumpAssign").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善分配信息');
            return false;
        }

        var rowArr = $("#datapurchaseAddPage-table").datagrid("getChecked");
        var list = new Array();
        var ids = null;
        if(rowArr.length==0){
            $.messager.alert("提醒","请选择数据");
            return false;
        }
        for(var i=0;i<rowArr.length;i++){
            var order = new Object();
            order.id =rowArr[i].id;
            order.billtype = rowArr[i].billtype;
            list[list.length] = order;
        }
        var markid=$("#datapurchase-markid-pumpAssign").widget("getValue");
        var supplierid=$("#datapurchase-supplierid-pumpAssign").widget("getValue");
        var businessdate=$("#datapurchase-businessdate-pumpAssign").val();
        var supplierEditType=$("#datapurchase-supplierEditType-pumpAssign").val();
        var orders=JSON.stringify(list);
        loading("数据处理中..");
        $.ajax({
            url:'manufacturerdata/pumpAssignedDataPurchase.do',
            dataType:'json',
            type:'post',
            data:{orders:orders,markid:markid,supplierid:supplierid,businessdate:businessdate,supplierEditType:supplierEditType},
            success:function(json){
                loaded();
                if(null!=json.msg){
                    $.messager.alert("提醒","分配完成。"+json.msg);
                }
                else{
                    $.messager.alert("提醒","分配完成。");
                }
                $("#datapurchaseAddPage-dialog-content").dialog('destroy');
                refresh();
            },
            error:function(){
                loaded();
                $.messager.alert("提醒","分配失败。");
                $("#datapurchaseAddPage-dialog-content").dialog('destroy');
            }
        });
    }

    function showPumpAssignDatasalesPage(){
        var rowArr = $("#datapurchaseAddPage-table").datagrid("getChecked");
        if(rowArr.length==0){
            $.messager.alert("提醒","请选择数据");
            return false;
        }
        $('<div id="datapurchaseAddPage-dialog-content"></div>').appendTo('#datapurchaseAddPage-dialog');
        $('#datapurchaseAddPage-dialog-content').dialog({
            title: '手动分配',
            width: 500,
            height: 320,
            collapsible:true,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            modal: true,
            href: 'manufacturerdata/showPumpAssignDatapurchasePage.do',
            onLoad:function(){
            },
            onClose:function(){
                $('#datapurchaseAddPage-dialog-content').dialog("destroy");
            }
        });
        $('#datapurchaseAddPage-dialog-content').dialog("open");
    }
    function refresh(){
        var queryJSON = $("#datapurchaseAddPage-form").serializeJSON();
        $("#datapurchaseAddPage-table").datagrid("load",queryJSON);
    }
</script>
</html>

