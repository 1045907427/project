<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>客户应收款冲差报表</title>
    <%@include file="/include.jsp" %>
    <style>
        .checkbox1{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:22px;
            float:left;
            display: block;
        }
    </style>
</head>
<body>
<table id="account-table-customerPushBalanceReportPage"></table>
<div id="account-datagrid-toolbar-customerPushBalanceReportPage" style="padding: 0px;height: 120px">
    <div class="buttonBG">
        <security:authorize url="/account/customercost/exportCustomerPushBalanceReportPage.do">
            <a href="javaScript:void(0);" id="account-export-customerPushBalanceReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
        <form action="" id="account-form-queryReport-customerPushBalanceReportPage" method="post">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday}"/> 到
                        <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  value="${today}" /></td>
                    <td>单据类型:</td>
                    <td>
                        <select name="isinvoice" style="width: 180px;">
                            <option></option>
                            <option value="0">正常冲差</option>
                            <option value="1">发票冲差</option>
                            <option value="2">回单冲差</option>
                        </select>
                    </td>
                    <td>品牌:</td>
                    <td>
                        <input id="account-queryReport-brandid" type="text" name="brandid" style="width: 165px;"/>
                    </td>
                </tr>
                <tr>
                    <td>客户:</td>
                    <td><input id="account-queryReport-customerid" type="text" name="customerid" style="width: 225px;"/></td>
                    <td>费用科目:</td>
                    <td>
                        <input id="account-queryReport-subject" type="text" name="subject" style="width: 180px;"/>
                    </td>
                    <td>总店:</td>
                    <td><input id="account-queryReport-pcustomerid" type="text" name="pcustomerid" style="width: 165px;"/></td>
                </tr>
                <tr>
                    <td>小计列:</td>
                    <td>
                        <input type="checkbox" class="groupcols checkbox1" value="customerid" id="customerid"/>
                        <label for="customerid" class="divtext">客户</label>
                        <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                        <label for="brandid" class="divtext">品牌</label>
                        <input type="checkbox" class="groupcols checkbox1" value="subject" id="subject"/>
                        <label for="subject" class="divtext">费用科目</label>
                        <input type="checkbox" class="groupcols checkbox1" value="pushtype" id="pushtype"/>
                        <label for="pushtype" class="divtext">冲差类型</label>

                        <input id="report-queryReport-groupcols" type="hidden" name="groupcols"/>
                    </td>
                    <td>冲差类型:</td>
                    <td>
                        <select id="account-queryReport-pushtype" name="pushtype" style="width: 180px;">
                            <option></option>
                            <c:forEach items="${pushList }" var="list">
                                <option value="${list.code }">${list.codename }</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="account-queay-customerPushBalanceReportPage" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="account-reload-customerPushBalanceReportPage" class="button-qr">重置</a>
                        <span id="account-queryReport-advanced-customerPushBalanceReportPage"></span>
                    </td>
                </tr>
            </table>
        </form>
</div>
<script type="text/javascript">

    var SR_footerobject  = null;
    var initQueryJSON = $("#account-form-queryReport-customerPushBalanceReportPage").serializeJSON();
    $(function(){
    var pubshBalanceReportJson=$("#account-table-customerPushBalanceReportPage").createGridColumnLoad({
        frozenCol:[[]],
        commonCol:[[
            {field:'ck',checkbox:true},
            {field:'customerid',title:'客户编码',width:60,sortable:true},
            {field:'customername',title:'客户名称',width:100,isShow:true},
            {field:'pcustomerid',title:'总店',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    if(value!=rowData.customerid){
                        return rowData.pcustomername;
                    }
                }
            },
            {field:'isinvoice',title:'单据类型',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.isinvoicename;
                }
            },
            {field:'pushtype',title:'冲差类型',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.pushtypename;
                }
            },
            {field:'brandid',title:'品牌名称',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.brandname;
                }
            },
            {field:'subject',title:'费用科目',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.subjectname;
                }
            },
            {field:'amount',title:'冲差金额',resizable:true,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            }
        ]]
    });
        //全局导出
        $("#account-export-customerPushBalanceReportPage").click(function(){
            var queryJSON = $("#account-form-queryReport-customerPushBalanceReportPage").serializeJSON();
            //获取排序规则
            var objecr  = $("#account-table-customerPushBalanceReportPage").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "account/receivable/overalExportCutomerPushBalanceReport.do";
            exportByAnalyse(queryParam,"客户应收款冲差报表","account-table-customerPushBalanceReportPage",url);
        });

        $(".groupcols").click(function(){
            var cols = "";
            $("#report-queryReport-groupcols").val(cols);
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    if(cols==""){
                        cols = $(this).val();
                    }else{
                        cols += ","+$(this).val();
                    }
                    $("#report-queryReport-groupcols").val(cols);
                }
            });
        });

        $("#account-table-customerPushBalanceReportPage").datagrid({
            authority:pubshBalanceReportJson,
            frozenColumns: pubshBalanceReportJson.frozen,
            columns:pubshBalanceReportJson.common,
            fit:true,
            method:'post',
  //          idField:'id',
//            sortName:'customerid',
//            sortOrder:'desc',
            rownumbers:true,
            pagination:true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter: true,
            pageSize:100,
            url:'account/receivable/showCustomerPushBalanceReportData.do',
            queryParams:initQueryJSON,
            toolbar:'#account-datagrid-toolbar-customerPushBalanceReportPage',
            onLoadSuccess:function() {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                }
            },
            onCheckAll:function(){
                countTotalAmount(setColumn());
            },
            onUncheckAll:function(){
                countTotalAmount(setColumn());
            },
            onCheck:function(){
                countTotalAmount(setColumn());
            },
            onUncheck:function(){
                countTotalAmount(setColumn());
            }

        });
        //客户
        $("#account-queryReport-customerid").customerWidget({
            name:'t_account_customer_push_balance',
            width:225,
            col:'customerid',
            isdatasql:false,
            view:true,
            singleSelect:true
        });
        //总店
        $("#account-queryReport-pcustomerid").widget({
            referwid:"RL_T_BASE_SALES_CUSTOMER_PARENT",
            width:165,
            view:true,
            singleSelect:true
        });
        //费用科目
        $("#account-queryReport-subject").widget({
            referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT',
            view:true,
            width:180,
            onlyLeafCheck:false,
            singleSelect:false
        });
        //品牌
        $("#account-queryReport-brandid").widget({
            name:'t_account_customer_push_balance',
            width:165,
            col:'brandid',
            view:true,
            singleSelect:true
        });
        //查询
        $("#account-queay-customerPushBalanceReportPage").click(function(){
            setColumn();
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#account-form-queryReport-customerPushBalanceReportPage").serializeJSON();
            $("#account-table-customerPushBalanceReportPage").datagrid("load",queryJSON);
        });
        //重置
        $("#account-reload-customerPushBalanceReportPage").click(function(){
            $("#account-queryReport-customerid").customerWidget('clear');
            $("#account-queryReport-pcustomerid").widget('clear');
            $("#account-queryReport-subject").widget('clear');
            $("#account-queryReport-brandid").widget('clear');
            $("#account-form-queryReport-customerPushBalanceReportPage")[0].reset();
            var queryReportJSON = $("#account-form-queryReport-customerPushBalanceReportPage").serializeJSON();
            $("#account-table-customerPushBalanceReportPage").datagrid("load",queryReportJSON);
        });

        function countTotalAmount() {
            var rows = $("#account-table-customerPushBalanceReportPage").datagrid('getChecked');
            var amount=0;
            for(var i=0;i<rows.length;i++){
                amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
            }
            var foot=[{customerid:'选中合计',amount:amount}];
            if(null!=SR_footerobject){
                foot.push(SR_footerobject);
            }
            $("#account-table-customerPushBalanceReportPage").datagrid("reloadFooter",foot);
        }

    });
    var $datagrid = $("#account-table-customerPushBalanceReportPage");
    function setColumn(){
        var cols =  $("#report-queryReport-groupcols").val();
        if(cols != ""){
            $datagrid.datagrid('hideColumn', "customerid");
            $datagrid.datagrid('hideColumn', "customername");
            $datagrid.datagrid('hideColumn', "pcustomerid");
            $datagrid.datagrid('hideColumn', "isinvoice");
            $datagrid.datagrid('hideColumn', "pushtype");
            $datagrid.datagrid('hideColumn', "brandid");
            $datagrid.datagrid('hideColumn', "subject");
        }else{
            $datagrid.datagrid('showColumn', "customerid");
            $datagrid.datagrid('showColumn', "customername");
            $datagrid.datagrid('showColumn', "pcustomerid");
            $datagrid.datagrid('showColumn', "pushtype");
            $datagrid.datagrid('showColumn', "brandid");
            $datagrid.datagrid('showColumn', "subject");
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++) {
            var col = colarr[i];
            if (col == "customerid") {
                $datagrid.datagrid('showColumn', "customerid");
                $datagrid.datagrid('showColumn', "customername");
                $datagrid.datagrid('showColumn', "pcustomerid");
            }else if(col == "brandid"){
                $datagrid.datagrid('showColumn', "brandid");
            }else if(col == "subject"){
                $datagrid.datagrid('showColumn', "subject");
            }else if(col == "pushtype"){
                $datagrid.datagrid('showColumn', "pushtype");
            }

        }
    }


</script>

</body>
</html>
