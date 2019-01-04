<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商对应付账明细报表</title>
</head>
<body>
    <form action="" id="report-query-form-supplierPayBillDetail" method="post">
        <input type="hidden" name="supplierid" value="${supplierid}"/>
        <input type="hidden" name="businessdate1" value="${businessdate1}"/>
        <input type="hidden" name="businessdate2" value="${businessdate2}"/>
    </form>
    <table id="report-datagrid-supplierPayBillDetail"></table>
    <div id="report-toolbar-supplierPayBillDetail" class="buttonBG">
        <security:authorize url="/report/finance/supplierPayBillExport.do">
        <a href="javaScript:void(0);" id="report-buttons-supplierPayBillDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <div id="report-panel-billpage"></div>
    <script type="text/javascript">
        var initQueryJSON = $("#report-query-form-supplierPayBillDetail").serializeJSON();
        $(function(){
            $("#report-datagrid-supplierPayBillDetail").datagrid({
                columns:[[
                    {field:'businessdate',title:'业务日期',width:80},
                    {field:'billtype',title:'单据类型',width:90,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.billtypename;
                        }
                    },
                    {field:'id',title:'单据编号',width:130,
                        formatter:function(value,rowData,rowIndex){
                            if(value==undefined){
                                return "";
                            }
//                            if(rowData.billtype=='4'){
//                                return value;
//                            }
                            return '<a href="javascript:showBillPage(\''+rowData.billtype+'\',\''+value+'\');" style="text-decoration:underline">'+value+'</a>';
                        }
                    },
                    {field:'initbuyamount',title:'期初金额',resizable:true,sortable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'payableamount',title:'采购金额',resizable:true,sortable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'payamount',title:'付款金额',resizable:true,sortable:true,align:'right',
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'pushbalanceamount',title:'冲差金额',resizable:true,align:'right',sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'balanceamount',title:'余额',resizable:true,align:'right',sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    },
                    {field:'remark',title:'备注',align:'left',width:150}
                ]],
                method:'post',
                fit:true,
                rownumbers:true,
                singleSelect:true,
                url:'report/finance/getSupplierPayBillDetailData.do',
                queryParams:initQueryJSON,
                toolbar:"#report-toolbar-supplierPayBillDetail"
            });

            $("#report-buttons-supplierPayBillDetail").Excel('export',{
                queryForm: "#report-query-form-supplierPayBillDetail",
                type:'exportUserdefined',
                name:'供应商[${suppliername}]对应付账明细统计报表',
                url:'report/finance/exportSupplierPayBillDetailData.do'
            });
        });
        function showBillPage(billtype,id){
            if(billtype=="1"){
                top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do?type=view&id='+ id, "采购进货单查看");
            }else if(billtype=="2"){
                top.addOrUpdateTab('storage/showPurchaseRejectOutViewPage.do?type=view&id='+ id, "采购退货出库查看");
            }else if(billtype=="3"){
                $('#report-panel-billpage').dialog({
                    title: '付款单查看',
                    width: 680,
                    height: 300,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'account/payable/payorderEditPage.do?id='+id,
                    modal: true
                });
                $('#report-panel-billpage').dialog('open');
            }else if(billtype=="4"){
                top.addOrUpdateTab('account/payable/showPurchaseInvoiceEditPage.do?id='+id, "采购发票查看");
            }else if(billtype=="5"){
                $('#report-panel-billpage').dialog({
                    title: '应付款期初查看',
                    width: 680,
                    height: 300,
                    collapsible:false,
                    minimizable:false,
                    maximizable:true,
                    resizable:true,
                    closed: true,
                    cache: false,
                    href: 'account/begindue/showBeginDueViewPage.do?id='+id,
                    modal: true
                });
                $('#report-panel-billpage').dialog('open');
            }
        }
    </script>
</body>
</html>
