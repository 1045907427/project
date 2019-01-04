<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分客户应收款超账原因统计报表</title>
</head>

<body>
<form action="" id="report-query-form-salesuserdetail-receivablePastDueReson" method="post">
    <input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid"/>
    <input id="report-query-salesuser" type="hidden" name="salesuser" value="${salesuser}"/>
    <input id="report-query-ispastdue" type="hidden" name="ispastdue" value="${ispastdue}"/>
</form>
<table id="report-datagrid-salesuserdetail-receivablePastDueReson"></table>
<div id="report-toolbar-salesuserdetail-receivablePastDueReson" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/account/receivable/receivablePastDueResonExport.do">
            <a href="javaScript:void(0);" id="report-buttons-salesuserdetail-receivablePastDueResonPage" class="easyui-linkbutton" iconCls="button-export" plain="true">导出</a>
        </security:authorize>
    </div>
</div>
<script type="text/javascript">
var initQueryJSON = $("#report-query-form-salesuserdetail-receivablePastDueReson").serializeJSON();
$(function(){
    var detailTableColumnListJson = $("#report-datagrid-salesuserdetail-receivablePastDueReson").createGridColumnLoad({
        frozenCol : [[]],
        commonCol : [[
            {field:'customerid',title:'客户编号',width:60,sortable:true},
            {field:'customername',title:'客户名称',width:210,
                formatter:function(value,rowData,rowIndex){
                    return rowData.customername;
                }
            },
            {field:'branddept',title:'品牌部门',width:60,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.branddeptname;
                }
            },
            {field:'saleamount',title:'应收款',align:'right',resizable:true,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                },
                styler:function(value,rowData,rowIndex){
                    return 'color:blue';
                }
            },
            <c:forEach items="${list }" var="list">
            {field:'passamount${list.seq}',title:'${list.detail}',align:'right',resizable:true,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            </c:forEach>
            {field:'overreason',title:'超账期原因',resizable:true,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.overreason != undefined && rowData.overreason != ""){
                        return '<a href="javascript:showHistory('+rowData.customerid+');" style="text-decoration:underline">'+rowData.overreason+'</a>';
                    }
                }
            },
            {field:'commitmentdate',title:'承诺到款日期',width:90},
            {field:'commitmentamount',title:'承诺到款金额',align:'right',resizable:true,
                formatter:function(value,rowData,rowIndex){
                    if(value != "" && value != null){
                        return formatterMoney(value);
                    }
                }
            },
            {field:'actualamount',title:'实际到款金额',resizable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                },
                styler:function(value,rowData,rowIndex){
                    if(value < rowData.commitmentamount){
                        return  'background-color:#D4FDD7;';
                    }
                }
            },
            {field:'changenum',title:'变更次数',width:60},
            {field:'cstmerbalance',title:'客户余额',resizable:true,align:'right',sortable:true,
                formatter:function(value,rowData,rowIndex){
                    if(null != value && "" != value){
                        return formatterMoney(value);
                    }
                }
            },
            {field:'realsaleamount',title:'实际应收款',align:'right',resizable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'salesuser',title:'客户业务员',sortable:true,width:80,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesusername;
                }
            },
            {field:'salesarea',title:'销售区域',sortable:true,width:80,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesareaname;
                }
            },
            {field:'payeeid',title:'收款人',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.payeename;
                }
            },
            {field:'settletype',title:'结算方式',width:60,
                formatter:function(value,rowData,rowIndex){
                    return rowData.settletypename;
                }
            },
            {field:'settleday',title:'结算日',width:60}
        ]]
    });
    $("#report-datagrid-salesuserdetail-receivablePastDueReson").datagrid({
        authority:detailTableColumnListJson,
        frozenColumns: detailTableColumnListJson.frozen,
        columns:detailTableColumnListJson.common,
        method:'post',
        title:'',
        fit:true,
        rownumbers:true,
        pagination:true,
        pageSize:100,
        showFooter: true,
        singleSelect:false,
        checkOnSelect:true,
        selectOnCheck:true,
        queryParams:initQueryJSON,
        url: 'account/receivable/showCustomerReceivablePastDueReasonListData.do',
        toolbar:'#report-toolbar-salesuserdetail-receivablePastDueReson'
    }).datagrid("columnMoving");

    $("#report-buttons-salesuserdetail-receivablePastDueResonPage").Excel('export',{
        queryForm: "#report-query-form-salesuserdetail-receivablePastDueReson", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
        type:'exportUserdefined',
        name:'按客户业务员【${salesusername}】 分客户应收款超账原因统计报表',
        url:'account/receivable/exportBasereceivablePastDueResonData.do?type=customerid'
    });
});
</script>
</body>
</html>
