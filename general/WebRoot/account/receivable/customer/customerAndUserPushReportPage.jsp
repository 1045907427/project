<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>客户及业务员冲差报表</title>
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
<table id="account-table-customerAndUserPushReportPage"></table>
<div id="account-datagrid-toolbar-customerAndUserPushReportPage" style="padding: 0px;height: 120px">
    <div class="buttonBG">
        <security:authorize url="/account/receivable/exportCustomerAndUserPushReportPage.do">
            <a href="javaScript:void(0);" id="account-export-customerAndUserPushReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form action="" id="account-form-queryReport-customerAndUserPushReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday}"/> 到
                    <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  value="${today}" /></td>
                <td>客户业务员:</td>
                <td>
                    <input id="account-queryReport-salesuser" type="text" name="salesuser" style="width: 165px;"/>
                </td>
                <td>品牌:</td>
                <td>
                    <input id="account-queryReport-brandid" type="text" name="brandid" style="width: 165px;"/>
                </td>
            </tr>
            <tr>
                <td>客户:</td>
                <td><input id="account-queryReport-customerid" type="text" name="customerid" style="width: 225px;"/></td>
                <td>品牌业务员:</td>
                <td>
                    <input id="account-queryReport-branduser" type="text" name="branduser" style="width: 165px;"/>
                </td>
                <td>总店:</td>
                <td><input id="account-queryReport-pcustomerid" type="text" name="pcustomerid" style="width: 165px;"/></td>
            </tr>
            <tr>
                <td>小计列:</td>
                <td colspan="3">
                    <input type="radio" class="groupcols checkbox1" value="customerid" name="col" id="customerid"/>
                    <label for="customerid" class="divtext">客户</label>
                    <input type="radio" class="groupcols checkbox1" value="brandid" name="col" id="brandid"/>
                    <label for="brandid" class="divtext">品牌</label>
                    <input type="radio" class="groupcols checkbox1" value="salesuser" name="col" id="salesuser"/>
                    <label for="salesuser" class="divtext">客户业务员</label>
                    <input type="radio" class="groupcols checkbox1" value="branduser" name="col" id="branduser"/>
                    <label for="branduser" class="divtext">品牌业务员</label>
                    <input id="report-queryReport-groupcols" type="hidden" name="groupcols"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="account-queay-customerAndUserPushReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="account-reload-customerAndUserPushReportPage" class="button-qr">重置</a>
                    <span id="account-queryReport-advanced-customerAndUserPushReportPage"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">

    var SR_footerobject  = null;
    var initQueryJSON = $("#account-form-queryReport-customerAndUserPushReportPage").serializeJSON();
    $(function(){
        var pubshBalanceReportJson=$("#account-table-customerAndUserPushReportPage").createGridColumnLoad({
            commonCol:[[
                {field:'customerid',title:'客户编码',width:60,sortable:true},
                {field:'customername',title:'客户名称',width:100,isShow:true},
                {field:'pcustomername',title:'总店',width:80,sortable:true},
                {field:'brandid',title:'品牌名称',width:60,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'salesuser',title:'客户业务员',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'branduser',title:'品牌业务员',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandusername;
                    }
                },
            <c:forEach items="${pushList }" var="list" varStatus="index">
                {field:'amount'+'${list.code}',title:'${list.codename}',width:60,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(value == undefined){
                            return "0.00";
                        }else{
                            return value;
                        }
                    }
                },
            </c:forEach>
                {field:'amountSum',title:'合计',width:60,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(value != undefined){
                            return value;
                        }
                    }
                }
            ]]
        });
        //全局导出
        $("#account-export-customerAndUserPushReportPage").click(function(){
            var queryJSON = $("#account-form-queryReport-customerAndUserPushReportPage").serializeJSON();
            //获取排序规则
            var objecr  = $("#account-table-customerAndUserPushReportPage").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "account/receivable/overalExportCutomerAndUserPushReport.do";
            exportByAnalyse(queryParam,"客户及业务员冲差报表","account-table-customerAndUserPushReportPage",url);
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

        $("#account-table-customerAndUserPushReportPage").datagrid({
            authority:pubshBalanceReportJson,
            frozenColumns: pubshBalanceReportJson.frozen,
            columns:pubshBalanceReportJson.common,
            fit:true,
            method:'post',
            idField:'id',
            rownumbers:true,
            pagination:true,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter: true,
            pageSize:100,
            url:'account/receivable/showCustomerAndUserPushReportData.do',
            queryParams:initQueryJSON,
            toolbar:'#account-datagrid-toolbar-customerAndUserPushReportPage',
            onLoadSuccess:function() {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                }
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
            singleSelect:true
        });
        //客户业务员
        $("#account-queryReport-salesuser").widget({
            referwid:"RL_T_BASE_PERSONNEL_CLIENTSELLER",
            singleSelect:true
        });
        //品牌业务员
        $("#account-queryReport-branduser").widget({
            referwid:"RL_T_BASE_PERSONNEL_BRANDSELLER",
            view:true,
            singleSelect:true
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
        $("#account-queay-customerAndUserPushReportPage").click(function(){
            setColumn();
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#account-form-queryReport-customerAndUserPushReportPage").serializeJSON();
            $("#account-table-customerAndUserPushReportPage").datagrid("load",queryJSON);
        });
        //重置
        $("#account-reload-customerAndUserPushReportPage").click(function(){
            $("#account-queryReport-customerid").customerWidget('clear');
            $("#account-queryReport-pcustomerid").widget('clear');
            $("#account-queryReport-salesuser").widget('clear');
            $("#account-queryReport-branduser").widget('clear');
            $("#account-queryReport-brandid").widget('clear');
            $("#account-form-queryReport-customerAndUserPushReportPage")[0].reset();
            var queryReportJSON = $("#account-form-queryReport-customerAndUserPushReportPage").serializeJSON();
            $("#account-table-customerAndUserPushReportPage").datagrid("load",queryReportJSON);
        });

    });
    var $datagrid = $("#account-table-customerAndUserPushReportPage");
    function setColumn(){
        var cols =  $("#report-queryReport-groupcols").val();
        if(cols != ""){
            $datagrid.datagrid('hideColumn', "customerid");
            $datagrid.datagrid('hideColumn', "customername");
            $datagrid.datagrid('hideColumn', "pcustomername");
            $datagrid.datagrid('hideColumn', "brandid");
            $datagrid.datagrid('hideColumn', "salesuser");
            $datagrid.datagrid('hideColumn', "branduser");
        }else{
            $datagrid.datagrid('showColumn', "customerid");
            $datagrid.datagrid('showColumn', "customername");
            $datagrid.datagrid('showColumn', "pcustomername");
            $datagrid.datagrid('showColumn', "brandid");
            $datagrid.datagrid('showColumn', "salesuser");
            $datagrid.datagrid('showColumn', "branduser");
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++) {
            var col = colarr[i];
            if (col == "customerid") {
                $datagrid.datagrid('showColumn', "customerid");
                $datagrid.datagrid('showColumn', "customername");
                $datagrid.datagrid('showColumn', "pcustomername");
            }else if(col == "brandid"){
                $datagrid.datagrid('showColumn', "brandid");
            }else if(col == "salesuser"){
                $datagrid.datagrid('showColumn', "salesuser");
            }else if(col == "branduser"){
                $datagrid.datagrid('showColumn', "branduser");
            }

        }
    }

</script>
</body>
</html>
