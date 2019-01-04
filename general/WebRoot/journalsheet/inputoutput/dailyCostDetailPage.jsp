<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
<head>
    <title>客户应付费用详情</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="report-toolbar-dailyCostDetailPage" style="height: 18px">
    <form id="report-form-dailyCostDetailPage" >
        <input type="hidden" name="businessdate1" value="${businessdate1}">
        <input type="hidden" name="businessdate2" value="${businessdate2}">
        <input type="hidden" name="salesuser" value="${salesuser}">
        <input type="hidden" name="supplierid" value="${supplierid}">
        <%--<security:authorize url="/journalsheet/costsFee/exportCustomerCostPayableDetail.do">--%>
        <a href="javaScript:void(0);" id="report-export-dailyCostDetailPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        <%--</security:authorize>--%>
    </form>
</div>
<table id="inputoutput-table-dailyCostDetailPage"></table>
<script type="text/javascript">
    $(function(){
        var initQueryJSON = $("#report-form-dailyCostDetailPage").serializeJSON();
        $("#report-export-dailyCostDetailPage").Excel('export',{
            queryForm: "#report-form-dailyCostDetailPage",
            type:'exportUserdefined',
            name:'业务员费用支出',
            url:'journalsheet/costsFee/exportDailyCostDetailPageData.do'
        });


        var checkListJson =  $("#inputoutput-table-dailyCostDetailPage").createGridColumnLoad({
            commonCol :[[
                {field:'businessdate',title:'业务日期',width:80,sortable:true},
                {field:'supplierid',title:'供应商编码',width:70,hidden: true, },
                {field:'suppliername',title:'供应商名称',width:100,hidden: true, },
                {field:'bankid',title:'银行名称',width:100,hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.bankname;
                    }
                },
                {field: 'salesuser', title: '客户业务员', width: 70,isShow:true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {field:'deptid',title:'所属部门',width:70,sortable:true,hidden: true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.deptname;
                    }
                },
                {field:'costsort',title:'费用科目',width:100,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.costsortname;
                    }
                },
                {field:'amount',title:'金额',width:80,sortable:true,align:'right',
                    formatter:function(val,rowData,rowIndex){
                        if(val != "" && val != null){
                            return formatterMoney(val);
                        }else{
                            return "0.00";
                        }
                    }
                },
                {field:'remark',title:'备注',width:100,isShow:true}
            ]],
        });

        $("#inputoutput-table-dailyCostDetailPage").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            pageSize:100,
            singleSelect:false,
            url:'journalsheet/costsFee/getDailyCostDetailPageData.do',
            queryParams:initQueryJSON,
            toolbar:'#report-toolbar-dailyCostDetailPage'
        });

    });

</script>
</body>
</html>
