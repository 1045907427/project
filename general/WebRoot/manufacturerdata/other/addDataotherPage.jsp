<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>厂家对接其他明细数据分配</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="dataotherAddPage-table"></table>
<div id="dataotherAddPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/manufacturerdata/dataOtherDefultAssigned.do">
            <a href="javaScript:void(0);" id="dataotherAddPage-button-defultAssigned" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">默认分配</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/pumpAssignedDataOther.do">
            <a href="javaScript:void(0);" id="dataotherAddPage-button-pumpAssigned" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">手动分配</a>
        </security:authorize>
    </div>
    <div>
        <form action="" method="post" id="dataotherAddPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>对接方:</td>
                    <td><input type="text" id="dataotherAddPage-markid" name="markid" style="width:200px"/></td>
                    <td>供应商名称:</td>
                    <td><input type="text" id="dataotherAddPage-supplierid" name="supplierid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td>单据编号:</td>
                    <td><input type="text" id="dataotherAddPage-id" name="id" style="width:220px"/></td>
                    <td>单据类型:</td>
                    <td>
                        <select  name="billtype" style="width:200px;">
                            <option value="" selected="selected"></option>
                            <option value="1">盘点报损</option>
                            <option value="2">盘点报溢</option>
                            <option value="3">其他出库</option>
                            <option value="4">其他入库</option>
                            <option value="5">调拨出库</option>
                            <option value="6">调拨入库</option>
                        </select>
                    </td>
                    <td>所属仓库:</td>
                    <td><input type="text" id="dataotherAddPage-storageid" name="storageid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td colspan="4"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="dataotherAddPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="dataotherAddPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="dataotherAddPage-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="dataotherAddPage-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#dataotherAddPage-form").serializeJSON();
    var loadData = false;
    var manufacturerdataJson = $("#dataotherAddPage-table").createGridColumnLoad({
        name :'',
        frozenCol : [[]],
        commonCol : [[
            {field:'ck', checkbox:true},
            {field:'id',title:'单据编号',width:150,sortable:true},
            {field:'billtype',title:'单据类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "报损出库";
                    }else if(dateValue=='2'){
                        return "报溢入库";
                    }else if(dateValue=='3'){
                        return "其他出库";
                    }else if(dateValue=='4'){
                        return "其他入库";
                    }else if(dateValue=='5'){
                        return "调拨出库";
                    }else if(dateValue=='6'){
                        return "调拨入库";
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

            {field:'taxamount',title:'出入库金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },

            {field:'defultMarkid',title:'默认对接方',width:200,sortable:true},
        ]]
    });


    $(function(){
        $("#dataotherAddPage-table").datagrid({
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
            url: 'manufacturerdata/showOtherStorageDataList.do',
            queryParams:initQueryJSON,
            toolbar:'#dataotherAddPage-toolbar',
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },

        }).datagrid('columnMoving');

        //查询
        $("#dataotherAddPage-queay-queryList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#dataotherAddPage-form").serializeJSON();
            $("#dataotherAddPage-table").datagrid("load",queryJSON);
        });
        //重置
        $("#dataotherAddPage-queay-reloadList").click(function(){
            var queryJSON = $("#dataotherAddPage-form").serializeJSON();
            $("#dataotherAddPage-table").datagrid("load",queryJSON);
        });

        $("#dataotherAddPage-button-defultAssigned").click(function(){//默认分配
            defultAssigned();
        });
        $("#dataotherAddPage-button-pumpAssigned").click(function(){//手动分配
            showPumpAssignDatasalesPage();
        });

        $("#dataotherAddPage-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        $("#dataotherAddPage-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        $("#dataotherAddPage-markid").widget({
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
        var rowArr = $("#dataotherAddPage-table").datagrid("getChecked");
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
            url:'manufacturerdata/dataOtherDefultAssigned.do',
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

        var rowArr = $("#dataotherAddPage-table").datagrid("getChecked");
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
        var markid=$("#dataother-markid-pumpAssign").widget("getValue");
        var storageid=$("#dataother-storageid-pumpAssign").widget("getValue");
        var businessdate=$("#dataother-businessdate-pumpAssign").val();
        var storageEditType=$("#dataother-storageEditType-pumpAssign").val();
        var orders=JSON.stringify(list);
        loading("数据处理中..");
        $.ajax({
            url:'manufacturerdata/pumpAssignedDataOther.do',
            dataType:'json',
            type:'post',
            data:{orders:orders,markid:markid,storageid:storageid,businessdate:businessdate,storageEditType:storageEditType},
            success:function(json){
                loaded();
                if(null!=json.msg){
                    $.messager.alert("提醒","分配完成。"+json.msg);
                }
                else{
                    $.messager.alert("提醒","分配完成。");
                }
                $("#dataotherAddPage-dialog-content").dialog('destroy');
                refresh();
            },
            error:function(){
                loaded();
                $.messager.alert("提醒","分配失败。");
                $("#dataotherAddPage-dialog-content").dialog('destroy');
            }
        });
    }

    function showPumpAssignDatasalesPage(){
        var rowArr = $("#dataotherAddPage-table").datagrid("getChecked");
        if(rowArr.length==0){
            $.messager.alert("提醒","请选择数据");
            return false;
        }
        $('<div id="dataotherAddPage-dialog-content"></div>').appendTo('#dataotherAddPage-dialog');
        $('#dataotherAddPage-dialog-content').dialog({
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
            href: 'manufacturerdata/showPumpAssignDataotherPage.do',
            onLoad:function(){
            },
            onClose:function(){
                $('#dataotherAddPage-dialog-content').dialog("destroy");
            }
        });
        $('#dataotherAddPage-dialog-content').dialog("open");
    }
    function refresh(){
        var queryJSON = $("#dataotherAddPage-form").serializeJSON();
        $("#dataotherAddPage-table").datagrid("load",queryJSON);
    }
</script>
</html>

