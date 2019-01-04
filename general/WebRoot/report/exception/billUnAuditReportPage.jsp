<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>多日未验收单据列表页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="report-queryDiv-billUnAuditPage" style="padding:0px;height:auto">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="report-export-billUnAuditPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </div>
            <form id="report-queryForm-billUnAuditPage">
                <input type="hidden" name="exportids" id="report-exportids-billUnAuditPage" />
                <table class="querytable">
                    <tr>
                        <td>单据编号:</td>
                        <td><input type="text" name="id" class="len120" /> </td>
                        <td>客户:</td>
                        <td><input type="text" id="report-customer-billUnAuditPage" name="customerid" style="width: 150px;"/></td>
                        <td>销售内勤:</td>
                        <td><input type="text" id="report-indooruserid-billUnAuditPage" name="indooruserid" /></td>
                    </tr>
                    <tr>
                        <td>客户业务员：</td>
                        <td><input type="text" id="report-salesuser-billUnAuditPage" name="salesuser" /></td>
                        <td>总店:</td>
                        <td><input type="text" id="report-pcustomer-billUnAuditPage" name="pcustomerid" /></td>
                        <td>单据类型:</td>
                        <td><select name="billtype" style="width: 150px;">
                            <option></option>
                            <option value="1">销售回单</option>
                            <option value="2">销售退货通知单</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>未验收天数>=</td>
                        <td><input type="text" name="uncheckday" class="len120" value="3"/></td>
                        <td>退货类型:</td>
                        <td><select name="billbactype" style="width:150px;">
                                <option></option>
                                <option value="1">直退退货</option>
                                <option value="2">售后退货</option>
                            </select></td>
                        <td colspan="2" style="padding-left: 30px">
                            <a href="javascript:;" id="report-queay-billUnAuditPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="report-resetQueay-billUnAuditPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="report-datagrid-billUnAuditPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-queryForm-billUnAuditPage").serializeJSON();
    $(function(){

        $("#report-customer-billUnAuditPage").customerWidget({ //客户参照窗口
        });
        $("#report-salesuser-billUnAuditPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            singleSelect:true,
            width:'120'
        });
        $("#report-pcustomer-billUnAuditPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect:true,
            width:'150'
        });
        $("#report-indooruserid-billUnAuditPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_INDOORSTAFF',
            width:150,
            singleSelect:true,
            initSelectNull:true
        });
        //回车事件
        controlQueryAndResetByKey("report-queay-billUnAuditPage","report-resetQueay-billUnAuditPage");

        $("#report-queay-billUnAuditPage").click(function(){
            var queryJSON = $("#report-queryForm-billUnAuditPage").serializeJSON();
            $("#report-datagrid-billUnAuditPage").datagrid('load', queryJSON);
        });
        $("#report-resetQueay-billUnAuditPage").click(function(){
            $("#report-customer-billUnAuditPage").customerWidget("clear");
            $("#report-salesuser-billUnAuditPage").widget("clear");
            $("#report-indooruserid-billUnAuditPage").widget("clear");
            $("#report-pcustomer-billUnAuditPage").widget('clear');
            $("#report-exportids-billUnAuditPage").val("");
            $("#report-queryForm-billUnAuditPage").form("reset");
            var queryJSON = $("#report-queryForm-billUnAuditPage").serializeJSON();
            $("#report-datagrid-billUnAuditPage").datagrid('load', queryJSON);
        });

        $("#report-export-billUnAuditPage").Excel('export',{
            queryForm: "#report-queryForm-billUnAuditPage",
            type:'exportUserdefined',
            name:'多日未验收单据报表',
            url:'report/exception/exportBillUnAuditReportList.do'
        });

        var orderListJson = $("#report-datagrid-billUnAuditPage").createGridColumnLoad({
            frozenCol : [[{field:'ck',checkbox:true}]],
            commonCol : [[{field:'id',title:'编号',width:120, align: 'left',sortable:true},
                {field:'saleorderid',title:'订单编号',width:120, align: 'left',sortable:true},
                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:100,align:'left',isShow:true},
                {field:'pcustomerid',title:'总店',width:60,align:'left',sortable:true,isShow:true,
                    formatter:function(value,row,index){
                        return row.pcustomername;
                    }
                },
                {field:'handlerid',title:'对方经手人',width:70,align:'left',hidden:true,
                    formatter:function(value,row,index){
                        return row.handlername;
                    }
                },
                {field:'salesdept',title:'销售部门',width:80,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        return row.salesdeptname;
                    }
                },
                {field:'salesuser',title:'客户业务员',width:80,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        return row.salesusername;
                    }
                },
                {field:'amount',title:'金额',width:80,align:'right',isShow:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'duefromdate',title:'应收日期',width:80,align:'left',sortable:true},
                {field:'canceldate',title:'核销日期',width:80,align:'left',sortable:true},
                {field:'status',title:'状态',width:60,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        return getSysCodeName('status', value);
                    }
                },
                {field:'isinvoice',title:'发票状态',width:60,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        if(value == "0"){
                            return "未开票";
                        }else if(value == "1"){
                            return "已开票";
                        }else if(value == "2"){
                            return "已核销";
                        }else if(value == "3"){
                            return "未开票";
                        }else if(value == "4"){
                            return "开票中";
                        }else if(value == "5"){
                            return "部分核销";
                        }
                    }
                },
                {field:'billbactype',title:'退货类型',width:60,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        if(value == "1"){
                            return "直退退货";
                        }else if(value == "2"){
                            return "售后退货";
                        }
                    }
                },
                {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
                    formatter:function(value,rowData,index){
                        return rowData.indoorusername;
                    }
                },
                {field:'addusername',title:'制单人',width:80},
                {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
                {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
                {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
                {field:'remark',title:'备注',width:100,align:'left'}
            ]]
        });
        $("#report-datagrid-billUnAuditPage").datagrid({
            authority:orderListJson,
            frozenColumns: orderListJson.frozen,
            columns:orderListJson.common,
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter: true,
            sortName:'id',
            sortOrder:'desc',
            pageSize:100,
            url: 'report/exception/getBillUnAuditReportList.do',
            queryParams:initQueryJSON,
            toolbar:'#report-queryDiv-billUnAuditPage',
            onDblClickRow:function(index, data){
                if(data.billtype == "1"){
                    if (top.$('#tt').tabs('exists','销售发货回单查看')){
                        top.$('#tt').tabs('close','销售发货回单查看');
                    }
                    top.addTab('sales/receiptUnCheckPage.do?id='+data.id, '销售发货回单查看');
                }else if(data.billtype == "2"){
                    if (top.$('#tt').tabs('exists','销售退货验收')){
                        top.$('#tt').tabs('close','销售退货验收');
                    }
                    top.addOrUpdateTab('sales/rejectBillCheckPage.do?type=edit&id='+data.id, '销售退货验收');
                }
            },
            onCheck: function(rowIndex,rowData){
                countTotal();
            },
            onUncheck: function(rowIndex,rowData){
                countTotal();
            },
            onCheckAll: function(rows){
                countTotal();
            }
        }).datagrid("columnMoving");
    });
    function countTotal(){
        var rows = $("#report-datagrid-billUnAuditPage").datagrid("getChecked");
        var a1 = 0;
        var ids = "";
        if(rows != null && rows.length != 0){
            for(var i=0;i<rows.length;i++){
                if(ids == ""){
                    ids = rows[i].id;
                }else{
                    ids += "," + rows[i].id;
                }
                a1 += parseFloat(rows[i].amount == undefined ? 0 : rows[i].amount);
            }
            $("#report-exportids-billUnAuditPage").val(ids);
        }
    }
</script>
</body>
</html>
