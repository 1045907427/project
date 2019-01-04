<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>厂家数据抓取配置参数</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="datasalesAddPage-table"></table>
<div id="datasalesAddPage-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/manufacturerdata/dataSalesDefultAssigned.do">
            <a href="javaScript:void(0);" id="datasalesAddPage-button-defultAssigned" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">默认分配</a>
        </security:authorize>
        <security:authorize url="/manufacturerdata/pumpAssignedDataSales.do">
            <a href="javaScript:void(0);" id="datasalesAddPage-button-pumpAssigned" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">手动分配</a>
        </security:authorize>
    </div>
    <div>
        <form action="" method="post" id="datasalesAddPage-form">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',minDate:'${initdate }'})" value="${tomonth}"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today}"/></td>
                    <td>对接方:</td>
                    <td><input type="text" id="datasalesAddPage-markid" name="markid" style="width:200px"/></td>
                    <td>客户名称:</td>
                    <td><input type="text" id="datasalesAddPage-customerid" name="customerid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td>单据编号:</td>
                    <td><input type="text" id="datasalesAddPage-id" name="id" style="width:220px"/></td>
                    <td>单据类型:</td>
                    <td>
                        <select  name="billtype" style="width:200px;">
                            <option value="" selected="selected"></option>
                            <option value="1">销售出库</option>
                            <option value="2">销售退货入库</option>
                        </select>
                    </td>
                    <td>所属仓库:</td>
                    <td><input type="text" id="datasalesAddPage-storageid" name="storageid" style="width:150px"/></td>
                </tr>
                <tr>
                    <td colspan="4"></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="datasalesAddPage-queay-queryList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="datasalesAddPage-queay-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="datasalesAddPage-dataDetail" name="detailJson" />
        </form>
    </div>
</div>
<div id="datasalesAddPage-dialog"></div>
</body>
<script type="text/javascript">
    var initQueryJSON = $("#datasalesAddPage-form").serializeJSON();
    var loadData = false;
    var manufacturerdataJson = $("#datasalesAddPage-table").createGridColumnLoad({
        name :'',
        frozenCol : [[]],
        commonCol : [[
            {field:'ck', checkbox:true},
            {field:'id',title:'单据编号',width:150,sortable:true},
            {field:'billtype',title:'单据类型',width:90,sortable:true,
                formatter: function(dateValue,row,index){
                    if(dateValue=='1'){
                        return "销售出库";
                    }else if(dateValue=='2'){
                        return "销售退货入库";
                    }
                }
            },
            {field:'businessdate',title:'业务日期',width:125,sortable:true},
            {field:'saleorderid',title:'销售订单编号',width:130,sortable:true},
            {field:'storageid',title:'出库仓库',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.storagename;
                }
            },
            {field:'customerid',title:'客户编码',width:60,sortable:true},
            {field:'customername',title:'客户名称',width:150,isShow:true},
            {field:'salesdept',title:'销售部门',width:80,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesdeptname;
                }
            },
            {field:'salesuser',title:'客户业务员',width:70,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesusername;
                }
            },
            {field:'sendamount',title:'发货出库金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'sendnotaxamount',title:'发货出库未税金额',width:80,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },

            {field:'defultMarkid',title:'默认对接方',width:200,sortable:true},
            {field:'status',title:'状态',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return getSysCodeName("status",value);
                }
            },
            {field:'indooruserid',title:'销售内勤',width:60,sortable:true,hidden:true,
                formatter:function(value,rowData,index){
                    return rowData.indoorusername;
                }
            },
            {field:'duefromdate',title:'应收日期',width:80,hidden:true,sortable:true,hidden:true},
            {field:'auditusername',title:'审核人',width:60,sortable:true,hidden:true},
            {field:'audittime',title:'审核时间',width:120,sortable:true,hidden:true},
            {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true,hidden:true},
            {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true,hidden:true},
            {field:'phprinttimes',title:'配货打印次数',width:80,hidden:true,hidden:true},
            {field:'remark',title:'备注',width:80,sortable:true},
            {field:'addusername',title:'制单人',width:60,sortable:true,hidden:true},
            {field:'addtime',title:'制单时间',width:120,sortable:true,hidden:true},
            {field:'ischeck',title:'是否核对',width:60,hidden:true,sortable:true,hidden:true,
                formatter:function(value,rowData,index){
                    if(value=='0'){
                        return "未核对";
                    }else if(value=='1'){
                        return "已核对";
                    }
                }
            },
            {field:'checkusername',title:'核对人',width:60,sortable:true,hidden:true},
            {field:'checktime',title:'核对时间',width:120,hidden:true,sortable:true,hidden:true},
            {field:'isbigsaleout',title:'是否大单发货',width:60,hidden:true,sortable:true,isShow:true,hidden:true,
                formatter:function(value,rowData,index){
                    if(value=='0'){
                        return "否";
                    }else if(value=='1'){
                        return "是";
                    }
                }
            },
            {field:'storagername',title:'发货人',width:80,sortable:true,isShow:true,hidden:true},
        ]]
    });


    $(function(){
        $("#datasalesAddPage-table").datagrid({
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
            url: 'manufacturerdata/showSalesStorageDataList.do',
            queryParams:initQueryJSON,
            toolbar:'#datasalesAddPage-toolbar',
            onBeforeLoad:function(){
                $(this).datagrid('clearChecked');
                $(this).datagrid('clearSelections');
                return loadData;
            },

        }).datagrid('columnMoving');

        //查询
        $("#datasalesAddPage-queay-queryList").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#datasalesAddPage-form").serializeJSON();
            $("#datasalesAddPage-table").datagrid("load",queryJSON);
        });
        //重置
        $("#datasalesAddPage-queay-reloadList").click(function(){
            var queryJSON = $("#datasalesAddPage-form").serializeJSON();
            $("#datasalesAddPage-table").datagrid("load",queryJSON);
        });

        $("#datasalesAddPage-button-defultAssigned").click(function(){//默认分配
            defultAssigned();
        });
        $("#datasalesAddPage-button-pumpAssigned").click(function(){//手动分配
            showPumpAssignDatasalesPage();
        });

        $("#datasalesAddPage-customerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        $("#datasalesAddPage-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
        });

        $("#datasalesAddPage-markid").widget({
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
        var rowArr = $("#datasalesAddPage-table").datagrid("getChecked");
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
            url:'manufacturerdata/dataSalesDefultAssigned.do',
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

        var rowArr = $("#datasalesAddPage-table").datagrid("getChecked");
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
        var markid=$("#datasales-markid-pumpAssign").widget("getValue");
        var customerid=$("#datasales-customerid-pumpAssign").widget("getValue");
        var businessdate=$("#datasales-businessdate-pumpAssign").val();
        var customerEditType=$("#datasales-customerEditType-pumpAssign").val();
        var orders=JSON.stringify(list);
        loading("数据处理中..");
        $.ajax({
            url:'manufacturerdata/pumpAssignedDataSales.do',
            dataType:'json',
            type:'post',
            data:{orders:orders,markid:markid,customerid:customerid,businessdate:businessdate,customerEditType:customerEditType},
            success:function(json){
                loaded();
                if(null!=json.msg){
                    $.messager.alert("提醒","分配完成。"+json.msg);
                }
                else{
                    $.messager.alert("提醒","分配完成。");
                }
                $("#datasalesAddPage-dialog-content").dialog('destroy');
                refresh();
            },
            error:function(){
                loaded();
                $.messager.alert("提醒","分配失败。");
                $("#datasalesAddPage-dialog-content").dialog('destroy');
            }
        });
    }

    function showPumpAssignDatasalesPage(){
        var rowArr = $("#datasalesAddPage-table").datagrid("getChecked");
        if(rowArr.length==0){
            $.messager.alert("提醒","请选择数据");
            return false;
        }
        $('<div id="datasalesAddPage-dialog-content"></div>').appendTo('#datasalesAddPage-dialog');
        $('#datasalesAddPage-dialog-content').dialog({
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
            href: 'manufacturerdata/showPumpAssignDatasalesPage.do',
            onLoad:function(){
            },
            onClose:function(){
                $('#datasalesAddPage-dialog-content').dialog("destroy");
            }
        });
        $('#datasalesAddPage-dialog-content').dialog("open");
    }
    function refresh(){
        var queryJSON = $("#datasalesAddPage-form").serializeJSON();
        $("#datasalesAddPage-table").datagrid("load",queryJSON);
    }
</script>
</html>

