<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售回单列表页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="sales-queryDiv-receiptListPage" style="padding:0px;height:auto">
            <div class="buttonBG" id="sales-buttons-receiptListPage" style="height:26px;"></div>
            <form id="sales-queryForm-receiptListPage">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td class="tdinput"><input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                        <td>单据编号:</td>
                        <td class="tdinput"><input type="text" name="id" style="width: 155px" /> </td>
                        <td>客户分类;</td>
                        <td class="tdinput"><input type="text" id="sales-customersort-receiptListPage" class="len130" name="customersort" /></td>

                    </tr>
                    <tr>
                        <td>客户:</td>
                        <td class="tdinput"><input type="text" id="sales-customer-receiptListPage" name="customerid" style="width: 224px"/></td>
                        <td>销售内勤:</td>
                        <td class="tdinput"><input type="text" id="sales-indooruserid-receiptListPage" style="width: 155px" name="indooruserid" /></td>
                        <td>客户业务员:</td>
                        <td class="tdinput"><input type="text" id="sales-salesuser-receiptListPage" class="len130" name="salesuser" /></td>
                    </tr>
                    <tr>
                        <td>状&nbsp;态:</td>
                        <td>
                            <select name="status" style="width: 70px">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">已关闭</option>
                                <option value="00">未关闭</option>
                            </select>
                            账期类型:
                            <select name="accounttype" style="width: 88px;">
                                <option></option>
                                <option value="1">账期</option>
                                <option value="2">现款</option>
                            </select>
                        </td>
                        <td>销售区域:</td>
                        <td class="tdinput"><input type="text" id="sales-salesarea-receiptListPage" name="salesarea" /></td>
                        <td>是否超账期:</td>
                        <td class="tdinput">
                            <select name="ispassdue" style="width: 125px"><option selected="selected"></option><option value="1" >是</option><option value="0">否</option></select>
                        </td>
                    </tr>
                    <tr>
                        <td>批次号:</td>
                        <td class="tdinput"><input type="text" id="sales-deliveryid-receiptListPage" name="batchno" style="width: 224px"/></td>
                        <td>客户单号:</td>
                        <td class="tdinput"><input type="text" id="sales-sourceid-receiptListPage" name="sourceid" style="width: 155px"/></td>
                        <td colspan="2" class="tdbutton" style="padding-left: 2px">
                            <a href="javascript:;" id="sales-queay-receiptListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="sales-resetQueay-receiptListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-datagrid-receiptListPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#sales-queryForm-receiptListPage").serializeJSON();
    $(function(){
        $("#sales-customer-receiptListPage").customerWidget({ //客户参照窗口
            isall:true,
            singleSelect:true,
            isdatasql:false
        });
        $("#sales-salesuser-receiptListPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            singleSelect:true,
            width:125
        });
        $("#sales-customersort-receiptListPage").widget({
            referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
            col:'customersort',
            singleSelect:false,
            width:125,
            onlyLeafCheck:false,
            view: true
        });
        $("#sales-salesarea-receiptListPage").widget({
            referwid:'RT_T_BASE_SALES_AREA',
            width:155,
            singleSelect:false,
            onlyLeafCheck:false
        });
        $("#sales-indooruserid-receiptListPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_INDOORSTAFF',
            width:155,
            singleSelect:true,
            initSelectNull:true
        });
        $("#sales-queay-receiptListPage").click(function(){
            var queryJSON = $("#sales-queryForm-receiptListPage").serializeJSON();
            $("#sales-datagrid-receiptListPage").datagrid('load', queryJSON);
        });
        $("#sales-resetQueay-receiptListPage").click(function(){
            $("#sales-customer-receiptListPage").customerWidget("clear");
            $("#sales-salesuser-receiptListPage").widget("clear");
            $("#sales-indooruserid-receiptListPage").widget("clear");
            $("#sales-salesarea-receiptListPage").widget('clear');
            $("#sales-customersort-receiptListPage").widget('clear');
            $("#sales-queryForm-receiptListPage").form("reset");
            var queryJSON = $("#sales-queryForm-receiptListPage").serializeJSON();
            $("#sales-datagrid-receiptListPage").datagrid('load', queryJSON);
        });

        //按钮
        $("#sales-buttons-receiptListPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/sales/receiptEditPage.do">
                <!--					{-->
                <!--						type: 'button-edit',-->
                <!--						handler: function(){-->
                <!--							var con = $("#sales-datagrid-receiptListPage").datagrid('getSelected');-->
                <!--							if(con == null){-->
                <!--								$.messager.alert("提醒","请选择一条记录");-->
                <!--								return false;-->
                <!--							}	-->
                <!--							top.addTab('sales/receiptPage.do?type=edit&id='+ con.id, "销售发货回单修改");-->
                <!--						}-->
                <!--					},-->
                </security:authorize>
                <security:authorize url="/sales/receiptViewPage.do">
                {
                    type: 'button-view',
                    handler: function(){
                        var con = $("#sales-datagrid-receiptListPage").datagrid('getSelected');
                        if(con == null){
                            $.messager.alert("提醒","请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('sales/receiptPage.do?type=edit&id='+con.id, '销售发货回单查看');
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receipteListExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#sales-datagrid-receiptListPage",
                        queryForm: "#sales-queryForm-receiptListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type:'exportUserdefined',
                        url:'sales/exportReceiptList.do',
                        name:'销售发货回单'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr:{
                        //查询针对的表
                        name:'t_sales_receipt',
                        plain:true,
                        //查询针对的表格id
                        datagrid:'sales-datagrid-receiptListPage'
                    }
                },
                {}
            ],
            buttons:[
                {},
                <security:authorize url="/sales/receiptMultiAudit.do">
                {
                    id:'button-mulitAudit',
                    name:'批量审核',
                    iconCls:'button-audit',
                    handler:function(){
                        var rows = $("#sales-datagrid-receiptListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请选中需要审核的记录。");
                            return false;
                        }
                        $.messager.confirm("提醒","确定审核这些回单？",function(r){
                            if(r){
                                var ids = "";
                                for(var i=0; i<rows.length; i++){
                                    ids += rows[i].id + ',';
                                }
                                loading("审核中..");
                                $.ajax({
                                    url:'sales/auditMultiReceipt.do',
                                    dataType:'json',
                                    type:'post',
                                    data:{ids:ids},
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            $.messager.alert("提醒","审核成功："+json.success+"&nbsp;审核失败："+json.failure+"<br/>"+json.msg);
                                            $("#sales-datagrid-receiptListPage").datagrid('reload');
                                        }
                                        else{
                                            $.messager.alert("提醒","审核出错");
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","审核出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_receipt'
        });
        var orderListJson = $("#sales-datagrid-receiptListPage").createGridColumnLoad({
            name :'t_sales_receipt',
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[{field:'id',title:'编号',width:120, align: 'left',sortable:true},
                {field:'saleorderid',title:'销售订单编号',width:120, align: 'left',sortable:true},
                {field:'sourceid',title:'客户单号',width:120, align: 'left',sortable:true,isShow:true,
                    formatter:function(value,row,index){
                        return row.sourceid;
                    }
                },
                {field:'deliveryid',title:'配送单号',width:120, align: 'left',sortable:true,isShow:true},
                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:100,align:'left',isShow:true},
                {field:'accounttype',title:'账期类型',width:70,align:'left',isShow:true,
                    formatter:function(value,row,index){
                        return row.accounttypename;
                    }
                },
                {field:'salesarea',title:'销售区域',width:80,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        return row.salesareaname;
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
                {field:'totaltaxamount',title:'发货金额',width:80,align:'right',isShow:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'totalreceipttaxamount',title:'应收金额',width:80,align:'right',isShow:true,
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
                {field:'isinvoice',title:'抽单状态',width:70,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        if(value == "0"){
                            return "未申请";
                        }else if(value == "1"){
                            return "已申请";
                        }else if(value == "2"){
                            return "已核销";
                        }else if(value == "3"){
                            return "未申请";
                        }else if(value == "4"){
                            return "申请中";
                        }else if(value == "5"){
                            return "部分核销";
                        }
                    }
                },
                {field:'isinvoicebill',title:'开票状态',width:70,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        if(value == "0"){
                            return "未开票";
                        }else if(value == "1"){
                            return "已开票";
                        }else if(value == "3"){
                            return "未开票";
                        }else if(value == "4"){
                            return "开票中";
                        }
                    }
                },
                {field:'source',title:'来源类型',width:80,align:'left',hidden:true,sortable:true,
                    formatter:function(value,row,index){
                        if(value == "1"){
                            return "发货单";
                        }
                        else{
                            return "无";
                        }
                    }
                },
                {field:'billno',title:'来源编号',width:120,align:'left',sortable:true},
                {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
                    formatter:function(value,rowData,index){
                        return rowData.indoorusername;
                    }
                },
                {field:'addusername',title:'制单人',width:80,sortable:true},
                {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
                {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
                {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
                {field:'remark',title:'备注',width:100,align:'left',sortable:true}
            ]]
        });
        $("#sales-datagrid-receiptListPage").datagrid({
            authority:orderListJson,
            frozenColumns: orderListJson.frozen,
            columns:orderListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter: true,
            sortName:'id',
            pageSize:100,
            sortOrder:'desc',
            //fitColumns:true,
            url: 'sales/getReceiptList.do',
            queryParams:initQueryJSON,
            toolbar:'#sales-queryDiv-receiptListPage',
            onDblClickRow:function(index, data){
                if (top.$('#tt').tabs('exists','销售发货回单查看')){
                    top.closeTab('销售发货回单查看');
                }
                top.addOrUpdateTab('sales/receiptPage.do?type=edit&id='+data.id, '销售发货回单查看');
            },
            onCheck: function(rowIndex,rowData){
                var rows = $("#sales-datagrid-receiptListPage").datagrid("getChecked");
                countTotal(rows);
            },
            onUncheck: function(rowIndex,rowData){
                var rows = $("#sales-datagrid-receiptListPage").datagrid("getChecked");
                countTotal(rows);
            },
            onCheckAll: function(rows){
                var rows = $("#sales-datagrid-receiptListPage").datagrid("getChecked");
                countTotal(rows);
            },
            onUncheckAll: function(rows){
                $("#sales-datagrid-receiptListPage").datagrid('reloadFooter',[{id:'合计', totaltaxamount: 0, totalreceipttaxamount: 0}]);
            }
        }).datagrid("columnMoving");
    });
    function countTotal(rows){
        var a1 = 0;
        var a2 = 0;
        if(rows != null){
            for(var i=0;i<rows.length;i++){
                a1 += parseFloat(rows[i].totaltaxamount == undefined ? 0 : rows[i].totaltaxamount);
                a2 += parseFloat(rows[i].totalreceipttaxamount == undefined ? 0 : rows[i].totalreceipttaxamount);
            }
        }
        $("#sales-datagrid-receiptListPage").datagrid('reloadFooter',[{id:'合计', totaltaxamount: a1, totalreceipttaxamount: a2}]);
    }
</script>
</body>
</html>
