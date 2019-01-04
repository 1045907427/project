<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>要货单列表</title>
    <%@include file="/include.jsp" %>

</head>
<body>
   <div class="easyui-layout" data-options="fit:true,border:true">
       <input type="hidden" id="personnelid" value="${personnelid}"/>
       <div data-options="region:'center'">
           <table id="sales-datagrid-demandPersonnelDataPage"></table>
       </div>
       <div data-options="region:'south'" >
           <div class="buttonDetailBG" style="text-align:right;">
               <input type="button" name="savegoon" id="sales-close-demandPersonnelDataPage" value="关闭"/>
           </div>
       </div>
       <div id="sales-dialog-demandPersonnelDataPage"></div>
   </div>
</body>
<script type="text/javascript">
    function refreshLayout1(title, url,type){
        $('<div id="sales-dialog-demandPersonnelDataPage1"></div>').appendTo('#sales-dialog-demandPersonnelDataPage');
        $('#sales-dialog-demandPersonnelDataPage1').dialog({
            maximizable:true,
            resizable:true,
            title: title,
            width: 970,
            height: 450,
            closed: true,
            cache: false,
            href: url,
            modal: true,
            onClose:function(){
                $('#sales-dialog-demandPersonnelDataPage1').dialog("destroy");
            }
        });
        $("#sales-dialog-demandPersonnelDataPage1").dialog("open");
    }
    $(function() {
        //销售部门控件
        $("#sales-salesDept-demandListPage").salesWidget({
            name:'t_base_department',
            col:'name',
            singleSelect:true,
            isdatasql:false,
            width:400,
            onlyLeafCheck:false
        });
        //关闭
        $("#sales-close-demandPersonnelDataPage").click(function(){
            $("#sales-dialog-demangImagePage1").dialog('close');
        });
        var demandListJson = $("#sales-datagrid-demandPersonnelDataPage").createGridColumnLoad({
            name: 't_sales_order',
            frozenCol: [[]],
            commonCol: [[{field: 'id', title: '编号', width: 130, align: 'left', sortable: true},
//            {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 220, align: 'left', isShow: true},
                {field: 'handlerid', title: '对方经手人', width: 80, align: 'left'},
                {field: 'salesdept', title: '销售部门', width: 80, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {
                    field: 'field01', title: '金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field02', title: '未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field03', title: '税额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未生成订单";
                        }
                        else if (value == "1") {
                            return "已生成订单";
                        }
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
//            {field:'addusername',title:'制单人',width:60,sortable:true},
//            {field:'addtime',title:'制单日期',width:120,sortable:true},
//            {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
//            {field:'audittime',title:'审核日期',width:80,sortable:true,hidden:true},
                {field: 'remark', title: '备注', width: 100}
            ]]
        });
        $("#sales-datagrid-demandPersonnelDataPage").datagrid({
            authority: demandListJson,
            frozenColumns: [[]],
            columns: demandListJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            checkOnSelect: true,
            selectOnCheck: true,
            sortName: 'addtime',
            sortOrder: 'desc',
//            pageSize: 10,
            url: 'sales/getPersonnelDemandData.do?id='+$("#personnelid").val(),
            onDblClickRow: function (index, data) {
//                $("#sales-dialog-demangImagePage1").dialog('close');
                refreshLayout1( "要货申请单查看",'sales/showDemandPersonnelDataDetailPage.do?type=view&id=' + data.id,"view");
//                top.addOrUpdateTab('sales/showDemandPersonnelDataDetailPage.do?type=view&id=' + data.id, "要货申请单查看");
            }
        }).datagrid("columnMoving");
    })
</script>
</body>
</html>
