<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分客户应收款超账原因统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-salesuser-receivablePastDueReson"></table>
<div id="report-toolbar-salesuser-receivablePastDueReson" style="padding: 0px">
    <form action="" id="report-query-form-salesuser-receivablePastDueReson" method="post">
        <input type="hidden" name="groupcols" value="salesuser"/>
        <table class="querytable">
            <tr>
                <security:authorize url="/account/receivable/receivablePastDueResonExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesuser-receivablePastDueResonPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">导出</a>
                </security:authorize>
                <a href="javaScript:void(0);" id="report-setdays-baseReceivablePastDue" class="easyui-linkbutton button-list" iconCls="button-intervalSet"  plain="true">设置区间</a>
            </tr>
            <tr>
                <td>客户业务员:</td>
                <td><input type="text" id="report-query-salesuser" name="salesuser"/></td>
                <td>是否只显示超账:</td>
                <td>
                    <select id="report-query-ispastdue" name="ispastdue" style="width: 50px">
                        <option value="0" selected="selected">否</option>
                        <option value="1">是</option>
                    </select>
                </td>
                <td class="tdbutton">
                    <a href="javaScript:void(0);" id="report-queay-salesuser-receivablePastDueReson" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-salesuser-receivablePastDueReson" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="report-paymentdaysSet-dialog"></div>
<div id="report-salesuserReceivablePastDueReason-dialog"></div>
<script type="text/javascript">
var SR_footerobject  = null;
var initQueryJSON = $("#report-query-form-salesuser-receivablePastDueReson").serializeJSON();
$(function(){
    var tableColumnListJson = $("#report-datagrid-salesuser-receivablePastDueReson").createGridColumnLoad({
        frozenCol : [[
            {field:'idok',checkbox:true,isShow:true}
        ]],
        commonCol : [[
            {field:'salesuser',title:'客户业务员',sortable:true,width:80,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesusername;
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
            }
            <c:forEach items="${list }" var="list">
            ,
            {field:'passamount${list.seq}',title:'${list.detail}',align:'right',resizable:true,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            }
            </c:forEach>
        ]]
    });

    $("#report-datagrid-salesuser-receivablePastDueReson").datagrid({
        authority:tableColumnListJson,
        frozenColumns: tableColumnListJson.frozen,
        columns:tableColumnListJson.common,
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
        toolbar:'#report-toolbar-salesuser-receivablePastDueReson',
        onDblClickRow:function(rowIndex, rowData){
            <security:authorize url="/account/receivable/pastDueReasonDblClick.do">
            var ispastdue = $("#report-query-ispastdue").val();
            $('<div id="report-salesuserReceivablePastDueReason-dialog1"></div>').appendTo('#report-salesuserReceivablePastDueReason-dialog');
            $("#report-salesuserReceivablePastDueReason-dialog1").dialog({
                title:'按客户业务员【'+rowData.salesusername+'】 分客户应收款超账原因统计报表',
                width:800,
                height:400,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                maximizable:true,
                maximized:true,
                href: 'account/receivable/showSalesuserDetailReceivablePastDueReasonListPage.do?salesuser='+rowData.salesuser+'&ispastdue='+ispastdue+'&salesusername='+rowData.salesusername,
                onClose:function(){
                    $("#report-salesuserReceivablePastDueReason-dialog1").dialog('destroy');
                }
            });
            $("#report-salesuserReceivablePastDueReason-dialog1").dialog("open");
            </security:authorize>
        },
        onLoadSuccess:function(){
            var footerrows = $(this).datagrid('getFooterRows');
            if(null!=footerrows && footerrows.length>0){
                SR_footerobject = footerrows[0];
                countTotalAmount();
            }
        },
        onCheckAll:function(){
            countTotalAmount();
        },
        onUncheckAll:function(){
            countTotalAmount();
        },
        onCheck:function(){
            countTotalAmount();
        },
        onUncheck:function(){
            countTotalAmount();
        }
    }).datagrid("columnMoving");

    //客户业务员
    $("#report-query-salesuser").widget({
        referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
        width:225,
        singleSelect:false
    });

    //查询
    $("#report-queay-salesuser-receivablePastDueReson").click(function(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#report-query-form-salesuser-receivablePastDueReson").serializeJSON();
        $("#report-datagrid-salesuser-receivablePastDueReson").datagrid({
            url: 'account/receivable/showCustomerReceivablePastDueReasonListData.do',
            pageNumber:1,
            queryParams:queryJSON
        });
    });
    //重置
    $("#report-reload-salesuser-receivablePastDueReson").click(function(){
        $("#report-query-salesuser").widget('clear');
        $("#report-query-form-salesuser-receivablePastDueReson")[0].reset();
        $("#report-datagrid-salesuser-receivablePastDueReson").datagrid('loadData',{total:0,rows:[],footer:[]});
    });

    $("#report-buttons-salesuser-receivablePastDueResonPage").Excel('export',{
        queryForm: "#report-query-form-salesuser-receivablePastDueReson", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
        type:'exportUserdefined',
        name:'客户业务员应收款超账原因统计报表',
        url:'account/receivable/exportBasereceivablePastDueResonData.do?type=saleuser'
    });

    //设置超账期区间
    $("#report-setdays-baseReceivablePastDue").click(function(){
        $("#report-paymentdaysSet-dialog").dialog({
            title: '超账期区间设置',
            width: 400,
            height: 400,
            closed: false,
            cache: false,
            modal: true,
            href: 'report/paymentdays/showPaymetdaysSetPage.do'
        });
    });

});
function countTotalAmount(){
    var rows =  $("#report-datagrid-salesuser-receivablePastDueReson").datagrid('getChecked');
    var saleamount = 0;
    var unpassamount = 0;
    var totalpassamount=0;
    <c:forEach items="${list }" var="list">
    var passamount${list.seq} = 0;
    </c:forEach>
    for(var i=0;i<rows.length;i++){
        saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
        totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);
        <c:forEach items="${list }" var="list">
        passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
        </c:forEach>
    }
    var foot=[{salesusername:'选中合计',bankname:'',saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount
        <c:forEach items="${list }" var="list">
        ,passamount${list.seq}:passamount${list.seq}
        </c:forEach>}];
    if(null!=SR_footerobject){
        foot.push(SR_footerobject);
    }
    $("#report-datagrid-salesuser-receivablePastDueReson").datagrid("reloadFooter",foot);
}
</script>
</body>
</html>
