<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>来源单据查看</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <%--<div data-options="region:'north',split:false,border:false" style="height: 70px;overflow: hidden;">--%>
        <%--<div id="thirdLog-queryDiv-thirdLogSourceDataPage" style="padding-bottom: 15px;">--%>
            <%--<form id="thirdLog-form-thirdLogSourceDataPage">--%>
                <%--<input type="hidden" name="id" value="${param.id}"/>--%>
            <%--</form>--%>
        <%--</div>--%>
    <%--</div>--%>
    <div data-options="region:'center',border:false" >
        <table id="thirdLog-datagrid-thirdLogSourceDataPage"></table>
    </div>
</div>
<div id="thirdLog-dialog-billPage"></div>
<script type="text/javascript">

    $(function () {
        $("#thirdLog-datagrid-thirdLogSourceDataPage").datagrid({
            frozenColumns: [[{field: 'ck', checkbox: true}]],
            columns:  [[
                {field: 'id', title: '单据编码', sortable: false, width: 150,
                    formatter:function(value,rowData){
                        var functionstr="showBillPage('"+value+"','"+rowData.billtype+"')";
                        var url='<label onclick="'+functionstr+'" style="text-decoration:underline;cursor:hand;">'+value+'</label>';
                        return url;
                    }
                },
                {field: 'billtype', title: '单据类型', sortable: false, width: 80},
                {field: 'businessdate', title: '业务日期', sortable: false, width: 100},
                {field: 'taxamount', title: '含税金额', align: 'right',sortable: false, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        if(value!=''){
                            return formatterMoney(value);
                        }
                    }
                },
                {field: 'notaxamount', title: '未税金额',align: 'right', sortable: false, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        if(value!=''){
                            return formatterMoney(value);
                        }
                    }
                },
                {field: 'tax', title: '税额', align: 'right',sortable: false, width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        if(value!=''){
                            return formatterMoney(value);
                        }
                    }
                }
            ]],
            fit: true,
            border: false,
            rownumbers: true,
            pagination: true,
            pageSize: 100,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            url: 'erpconnect/thirdlog/getThirdLogSourceData.do?id=${param.id}'
        }).datagrid("columnMoving");
        

    });

    function showBillPage(id,billtype){
        if(billtype=='采购进货'){
            top.addTab('purchase/arrivalorder/arrivalOrderPage.do?type=edit&id='+id, '采购进货单查看');
        }else if(billtype=="采购退货"){
            top.addTab('storage/showPurchaseRejectOutEditPage.do?id='+id, '采购退货入库查看');
        }else if(billtype=="采购发票"){
            top.addTab('account/payable/showPurchaseInvoiceEditPage.do?id='+id, '采购发票查看');
        }else if(billtype=="收款单"){
            openBillDataDialog('account/receivable/collectionOrderViewPage.do?id='+id, '收款单查看',650,350);
        }else if(billtype=="付款单"){
            openBillDataDialog('account/payable/payorderEditPage.do?id='+id, '付款单查看',650,350);
        }else if(billtype=="发货通知单"){
            top.addTab('sales/dispatchBill.do?type=edit&id='+id, '发货通知单查看');
        }else if(billtype=="发货单"){
            top.addTab('storage/showSaleOutEditPage.do?id='+id, '发货单查看');
        }else if(billtype=="销售退货入库"){
            top.addTab('storage/showSaleRejectEnterEditPage.do?id='+id, '销售退货入库查看');
        }else if(billtype=="客户应收款冲差"){
            openBillDataDialog('account/receivable/showCustomerPushBanlanceEditPage.do?id=' +id, '客户应收款冲差查看',400,400);
        }else if(billtype=="销售发票"){
            top.addTab('account/receivable/showSalesInvoiceEditPage.do?id='+id, '销售发票查看');
        }else if(billtype=="日常费用"){
            openBillDataDialog('journalsheet/deptdailycost/showDeptDailyCostInfoPage.do?id='+id, '日常费用查看',750,300);
        }else if(billtype=="客户费用"){
            openBillDataDialog('journalsheet/customercost/showCustomerCostPayableEditPage.do?id='+id, '客户费用查看',490,330);
        } else if(billtype=="代垫录入"){
            openBillDataDialog('journalsheet/matcostsInput/showMatcostsInputEditPage.do?id='+id, '代垫录入查看',540,390);
        }else if(billtype=="其它出库"){
            top.addTab('storage/showStorageOtherOutEditPage.do?id='+id, '其它出库查看');
        }else if(billtype=="其它入库"){
            top.addTab('storage/showStorageOtherEnterEditPage.do?id='+id, '其它入库查看');
        }else if(billtype=="报溢调账单"){
            top.addTab('storage/showAdjustmentsEditPage.do?id='+id, '报溢调账单查看');
        } else if(billtype=="报损调账单"){
            top.addTab('storage/showAdjustmentsEditPage.do?billypte=2&id='+id, '报损调账单查看');
        }else if(billtype=="申请开票"){
            top.addTab('account/receivable/showSalesInvoiceBillEditPage.do?id='+id, '申请开票查看');
        }else if(billtype=="发货单"){
            top.addTab('storage/showSaleOutViewPage.do?id='+id, '发货单查看');
        }
    }
    function openBillDataDialog(url,title,width,height){
        $('<div id="thirdLog-dialog-billPage-content"></div>').appendTo('#thirdLog-dialog-billPage');
        $('#thirdLog-dialog-billPage-content').dialog({
            title: title,
            width: width,
            height: height,
            closed: true,
            cache: false,
            href: url,
            maximizable:true,
            resizable:true,
            modal: true,
            onClose:function(){
                $('#thirdLog-dialog-billPage-content').dialog("destroy");
            }
        });
        $('#thirdLog-dialog-billPage-content').dialog('open');
    }

</script>
</body>
</html>
