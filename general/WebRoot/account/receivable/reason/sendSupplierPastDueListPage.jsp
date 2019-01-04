<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分供应商代垫应收款分析报表</title>
    <%@include file="/include.jsp" %>
    <%--<script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>--%>
</head>

<body>
<table id="report-datagrid-receivablePastDueReson"></table>
<div id="report-toolbar-receivablePastDueReson" style="padding: 0px">
    <form action="" id="report-query-form-receivablePastDueReson" method="post">
        <input type="hidden" name="ispastdue" value="1"/>
        <input id="report-query-groupcols" type="hidden" name="groupcols" value="supplierid"/>
    </form>
</div>
<div id="report-paymentdaysSet-dialog"></div>
<div id="report-customerSalesFlowDetail-dialog"></div>
<div id="report-customerReceivablePastDueReason-dialog"></div>
<div id="report-historyCustomerReceivablePastDueReason-dialog"></div>
<script type="text/javascript">
    var SR_footerobject  = null;
    var initQueryJSON = $("#report-query-form-receivablePastDueReson").serializeJSON();
    var reason_chooseNo;
    function frm_focus(val){
        reason_chooseNo = val;
    }
    function frm_blur(val){
        if(val == reason_chooseNo){
            reason_chooseNo = "";
        }
    }
    $(function(){
        var tableColumnListJson = $("#report-datagrid-receivablePastDueReson").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'supplierid',title:'供应商编号',width:60,sortable:true},
                {field:'suppliername',title:'供应商名称',width:210,
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.supplierid!=null && rowData.suppliername != "选中合计" && rowData.suppliername != "合计"){
                            return '<a href="javascript:showFlowDetail(\''+rowData.supplierid+'\',\''+value+'\');" style="text-decoration:underline">'+rowData.suppliername+'</a>';
                        }else{
                            return rowData.suppliername;
                        }
                    }
                },
                {field:'beginamount',title:'应收款期初',align:'right',resizable:true,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
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
                {field:'settletype',title:'结算方式',width:60,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.settletypename;
                    }
                },
                {field:'settleday',title:'结算日',width:60}
            ]]
        });
        $("#report-datagrid-receivablePastDueReson").datagrid({
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
            url:'account/receivable/showSupplierReceivablePastDueReasonListData.do',
            queryParams: initQueryJSON,
//            toolbar:'#report-toolbar-receivablePastDueReson',
            onDblClickRow:function(rowIndex, rowData){

            },
            onLoadSuccess:function(){
//                var footerrows = $(this).datagrid('getFooterRows');
//                if(null!=footerrows && footerrows.length>0){
//                    SR_footerobject = footerrows[0];
//                    countTotalAmount();
//                }
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

    });
    function countTotalAmount(){
        var rows =  $("#report-datagrid-receivablePastDueReson").datagrid('getChecked');
        var beginamount = 0;
        var saleamount = 0;
        var unpassamount = 0;
        var totalpassamount=0;
        var realsaleamount = 0;
        <c:forEach items="${list }" var="list">
        var passamount${list.seq} = 0;
        </c:forEach>
        var cstmerbalance = 0;
        for(var i=0;i<rows.length;i++){
            beginamount = Number(beginamount)+Number(rows[i].beginamount == undefined ? 0 : rows[i].beginamount);
            saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
            unpassamount = Number(unpassamount)+Number(rows[i].unpassamount == undefined ? 0 : rows[i].unpassamount);
            totalpassamount = Number(totalpassamount)+Number(rows[i].totalpassamount == undefined ? 0 : rows[i].totalpassamount);

            <c:forEach items="${list }" var="list">
            passamount${list.seq} = Number(passamount${list.seq})+Number(rows[i].passamount${list.seq} == undefined ? 0 : rows[i].passamount${list.seq});
            </c:forEach>
        }
        var foot=[{suppliername:'选中合计',bankname:'',beginamount:beginamount,saleamount:saleamount,unpassamount:unpassamount,totalpassamount:totalpassamount
            <c:forEach items="${list }" var="list">
            ,passamount${list.seq}:passamount${list.seq}
            </c:forEach>
        }];
        if(null!=SR_footerobject){
            foot.push(SR_footerobject);
        }
        $("#report-datagrid-receivablePastDueReson").datagrid("reloadFooter",foot);
    }

    //根据客户编码获取该客户的流水明细
    function showFlowDetail(supplierid,suppliername){
        var url = 'account/receivable/showSupplierMatcostsListPage.do?supplierid='+supplierid+"&suppliername="+suppliername;
        $('<div id="report-customerSalesFlowDetail-dialog1"></div>').appendTo('#report-customerSalesFlowDetail-dialog');
        $("#report-customerSalesFlowDetail-dialog1").dialog({
            title:'供应商：['+suppliername+']代垫明细表',
            width:800,
            height:400,
            closed:true,
            modal:true,
            maximizable:true,
            cache:false,
            resizable:true,
            maximized:true,
            href: url,
            queryParams: $("#report-query-form-receivablePastDueReson").serializeJSON(),
            onClose:function(){
                $('#report-customerSalesFlowDetail-dialog1').dialog("destroy");
            }
        });
        $("#report-customerSalesFlowDetail-dialog1").dialog("open");
    }
</script>
</body>
</html>
