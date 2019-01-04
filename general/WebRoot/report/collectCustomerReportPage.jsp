<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户汇总报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<table id="report-datagrid-collectReport"></table>
<div id="report-toolbar-collectReport" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/collectReportExport.do">
            <a href="javaScript:void(0);" id="report-export-collectReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-collectReport" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>销售部门:</td>
                <td><input id="report-query-salesdept" type="text" name="salesdept" style="width: 225px;"/></td>
                <td>销售区域:</td>
                <td><input id="report-query-salesarea" type="text" name="salesarea" style="width: 130px;"/></td>
            </tr>
            <tr>
                <td>客户名称</td>
                <td><input type="text" id="report-query-customerid" name="customerid"/></td>
                <td>客户业务员:</td>
                <td><input type="text" id="report-query-salesuser" name="salesuser"/></td>
                <td>总店名称:</td>
                <td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
            </tr>
            <tr>
                <td colspan="4"></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-collectReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-collectReport" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var CR_footerobject  = null;
    $(function(){
        var tableColumnListJson = $("#report-datagrid-collectReport").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'customerid',title:'客户编号',sortable:true,width:60},
                {field:'customername',title:'客户名称',width:210},
                {field:'pcustomerid',title:'总店编码',sortable:true,width:60,hidden:true},
                {field:'pcustomername',title:'总店名称',width:60,hidden:true},
                {field:'salesuser',title:'客户业务员',sortable:true,width:70,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'customersort',title:'客户分类',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customersortname;
                    }
                },
                {field:'salesarea',title:'销售区域',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesareaname;
                    }
                },
                {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesdeptname;
                    }
                },

                {field:'sendamount',title:'出库金额',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'sendnotaxamount',title:'出库未税金额',width:100,sortable:true,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'pushbalanceamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'returnamount',title:'退货合计',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleamount',title:'销售金额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'salenotaxamount',title:'销售无税金额',width:80,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saletax',title:'销售税额',width:60,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costamount',title:'成本金额',align:'right',resizable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'salemarginamount',title:'毛利额',resizable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'realrate',title:'毛利率',width:70,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'hidallunwithdrawnamount',title:'历史应收金额',align:'right',resizable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'allunwithdrawnamount',title:'应收金额',align:'right',resizable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'customeramount',title:'客户余额',align:'right',resizable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'balanceamount',title:'结余金额',align:'right',resizable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]]
        });

        $("#report-datagrid-collectReport").datagrid({
            authority:tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns:tableColumnListJson.common,
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            toolbar:'#report-toolbar-collectReport',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    CR_footerobject = footerrows[0];
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
        $("#report-query-customerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            width:225,
            singleSelect:true
        });
        $("#report-query-salesuser").widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            width:225,
            singleSelect:false
        });
        $("#report-query-pcustomerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            width:140,
            singleSelect:true
        });
        $("#report-query-salesarea").widget({
            referwid:'RT_T_BASE_SALES_AREA',
            width:140,
            onlyLeafCheck:false,
            singleSelect:true
        });
        $("#report-query-salesdept").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:225,
            singleSelect:false
        });

        //查询
        $("#report-queay-collectReport").click(function(){
            var queryJSON = $("#report-query-form-collectReport").serializeJSON();
            $("#report-datagrid-collectReport").datagrid({
                url: 'report/sales/showCollectReportData.do?groupcols=customerid',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-collectReport").click(function(){
            $("#report-query-customerid").widget("clear");
            $("#report-query-pcustomerid").widget("clear");
            $("#report-query-salesarea").widget("clear");
            $("#report-query-salesdept").widget("clear");
            $("#report-query-salesuser").widget('clear');
            $("#report-query-form-collectReport").form("reset");
            var queryJSON = $("#report-query-form-collectReport").serializeJSON();
            $("#report-datagrid-collectReport").datagrid('loadData',{total:0,rows:[]});
        });

        $("#report-export-collectReportPage").Excel('export',{
            queryForm: "#report-query-form-collectReport",
            type:'exportUserdefined',
            name:'分客户汇总统计报表',
            url:'report/sales/exportCollectReportData.do?groupcols=customerid'
        });
    });

    function countTotalAmount(){
        var rows =  $("#report-datagrid-collectReport").datagrid('getChecked');
        var sendamount = 0;
        var sendnotaxamount= 0;
        var pushbalanceamount = 0;
        var returnamount= 0;
        var saleamount = 0;
        var salenotaxamount = 0;
        var saletax = 0;
        var costamount = 0;
        var salemarginamount = 0;
        var realrate = 0;
        var withdrawnamount = 0;
        var hidallunwithdrawnamount = 0;
        var allunwithdrawnamount = 0;
        var customeramount = 0;
        var balanceamount = 0;
        for(var i=0;i<rows.length;i++){
            sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
            sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
            pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
            returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
            saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
            salenotaxamount = Number(salenotaxamount)+Number(rows[i].salenotaxamount == undefined ? 0 : rows[i].salenotaxamount);
            saletax = Number(saletax)+Number(rows[i].saletax == undefined ? 0 : rows[i].saletax);
            costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
            salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
            withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
            hidallunwithdrawnamount = Number(hidallunwithdrawnamount)+Number(rows[i].hidallunwithdrawnamount == undefined ? 0 : rows[i].hidallunwithdrawnamount);
            allunwithdrawnamount = Number(allunwithdrawnamount)+Number(rows[i].allunwithdrawnamount == undefined ? 0 : rows[i].allunwithdrawnamount);
            customeramount = Number(customeramount)+Number(rows[i].customeramount == undefined ? 0 : rows[i].customeramount);
            balanceamount = Number(balanceamount)+Number(rows[i].balanceamount == undefined ? 0 : rows[i].balanceamount);
        }
        if(Number(saleamount) != 0){
            realrate = salemarginamount/saleamount*Number(100);
        }
        var obj= {customername: '选中合计',sendamount:sendamount,sendnotaxamount:sendnotaxamount,pushbalanceamount:pushbalanceamount,returnamount:returnamount,saleamount:saleamount,
            salenotaxamount:salenotaxamount,saletax:saletax,costamount:costamount,salemarginamount:salemarginamount,realrate:realrate,withdrawnamount:withdrawnamount,
            hidallunwithdrawnamount:hidallunwithdrawnamount,allunwithdrawnamount:allunwithdrawnamount,customeramount:customeramount,balanceamount:balanceamount};
        var foot=[];
        foot.push(obj);
        if(null!=CR_footerobject){
            foot.push(CR_footerobject);
        }
        $("#report-datagrid-collectReport").datagrid("reloadFooter",foot);
    }
</script>
</body>
</html>
