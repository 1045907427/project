<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
<head>
    <title>客户应付费用详情</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="report-toolbar-customerCostPayableDetail" style="height: 18px">
    <form id="report-form-customerCostPayableDetail" >
        <input type="hidden" name="businessdate1" value="${businessdate1}">
        <input type="hidden" name="businessdate2" value="${businessdate2}">
        <input type="hidden" name="customerid" value="${customerid}">
        <input type="hidden" name="salesuser" value="${salesuser}">
        <input type="hidden" name="supplierid" value="${supplierid}">
        <input type="hidden" name="pid" value="${pid}">
        <%--<security:authorize url="/journalsheet/costsFee/exportCustomerCostPayableDetail.do">--%>
        <a href="javaScript:void(0);" id="report-export-customerCostPayableDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        <%--</security:authorize>--%>
    </form>
</div>
<table id="inputoutput-table-customerCostPayableDetail"></table>
<script type="text/javascript">
    $(function(){
        var initQueryJSON = $("#report-form-customerCostPayableDetail").serializeJSON();
        $("#report-export-customerCostPayableDetail").Excel('export',{
            queryForm: "#report-form-customerCostPayableDetail",
            type:'exportUserdefined',
            name:'客户应付费用详情',
            url:'journalsheet/costsFee/exportCustomerCostPayableDetail.do'
        });


        var checkListJson =  $("#inputoutput-table-customerCostPayableDetail").createGridColumnLoad({
            commonCol :[[
                {field:'oaid',title:'OA编号',width:70},
                {field:'businessdate',title:'业务日期',width:80},
                {field:'expensesortname',title:'费用类别',width:100,isShow:true},
                {field:'customerid',title:'客户编码',width:70,isShow:true},
                {field:'customername',title:'客户名称',width:200,isShow:true},
                {field:'supplierid',title:'供应商编码',width:70,isShow:true},
                {field:'suppliername',title:'供应商名称',width:200,isShow:true},
                {field:'amount',title:'费用金额',width:100,align:'right',
                    formatter:function(val,rowData,rowIndex){
                        if(val != "" && val != null){
                            return formatterMoney(val);
                        }else{
                            return "";
                        }
                    }
                },
            ]],
        });

        $("#inputoutput-table-customerCostPayableDetail").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
           // pageSize:100,
            singleSelect:false,
            url:'journalsheet/costsFee/getCustomerCostPayableDetailData.do',
            queryParams:initQueryJSON,
            toolbar:'#report-toolbar-customerCostPayableDetail'
        });
    });

</script>
</body>
</html>
