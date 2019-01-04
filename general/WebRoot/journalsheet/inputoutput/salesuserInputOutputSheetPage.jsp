<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<html>
<head>
    <title>业务员费比及产出比报表</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
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
<table id="report-datagrid-salesuserInputOutputSheetPage"></table>
<div id="report-toolbar-salesuserInputOutputSheetPage" style="padding:0px;height:auto">
    <div class="buttonBG">
        <%--<security:authorize url="/journalsheet/costsFee/exportSalesuserInputOutputSheet.do">--%>
        <a href="javaScript:void(0);" id="report-export-salesuserInputOutputSheetPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        <%--</security:authorize>--%>
    </div>
    <form id="report-form-salesuserInputOutputSheetPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td colspan="2">
                    <input type="text" name="businessdate1" id="businessdate1" style="width:100px;" class="Wdate" value="${today}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',isShowClear:false})" />
                    到<input type="text" name="businessdate2" id="businessdate2" class="Wdate" style="width:100px;" value="${today}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today}',isShowClear:false})" />
                </td>
                <td>供&nbsp;应&nbsp;商:</td>
                <td>
                    <input type="text" id="report-supplierid-salesuserInputOutputSheetPage" style="width: 170px;" name="supplierid"/>
                </td>
                <td>总&nbsp;店:</td>
                <td><input type="text" id="report-pid-salesuserInputOutputSheetPage" name="pid" style="width: 170px;"/></td>
            </tr>
            <tr>
                <td>客&nbsp;户:</td>
                <td colspan="2"><input type="text" id="report-customerid-salesuserInputOutputSheetPage" name="customerid" style="width: 215px;"/></td>
                <td>客户业务员:</td>
                <td><input type="text" id="report-salesuser-salesuserInputOutputSheetPage" name="salesuser" style="width:170px;"/></td>
                <td>销售数据来源:</td>
                <td>
                    <select name="salesdatatype" style="width: 170px;">
                        <option value="0">系统销售</option>
                        <option value="1">数据上报</option>
                    </select>
                </td>
                <td colspan="2" style="padding-left: 20px">
                    <a href="javaScript:void(0);" id="report-queay-salesuserInputOutputSheetPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-salesuserInputOutputSheetPage" class="button-qr">重置</a>
                </td>
            </tr>

        </table>
    </form>
</div>
<div id="customercost-table-payable-detail"></div>
<div id="dailycost-table-payable-detail"></div>
<script type="text/javascript">
    var initQueryJSON = $("#report-form-salesuserInputOutputSheetPage").serializeJSON();
    var SR_footerobject = null;
    $(function(){

        $("#report-export-salesuserInputOutputSheetPage").click(function(){
            //封装查询条件
            var objecr  = $("#report-datagrid-salesuserInputOutputSheetPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "journalsheet/costsFee/exportSalesuserInputOutputData.do";
            exportByAnalyse(queryParam,"业务员投入产出比报表","report-datagrid-salesuserInputOutputSheetPage",url);
        });

        var checkListJson = $("#report-datagrid-salesuserInputOutputSheetPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol : [[
                {field: 'salesuser', title: '业务员', sortable: true, width: 70,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {field:'saleamount',title:'销售金额',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costamount',title:'成本金额',hidden:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'realrate',title:'销售毛利率',width:70,align:'center',isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'expense',title:'日常费用',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.salesusername == '合计' || rowData.salesusername == '选中合计'){
                            return formatterMoney(value);
                        }else{
                            return "<a href='javascript:showExpenseDetail(\""+rowData.salesuser+"\");'>"+value+"</a>";
                        }
                    }
                },
                {field:'fee',title:'客户应付费用（贷）',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.salesusername == '合计' || rowData.salesusername == '选中合计'){
                            return formatterMoney(value);
                        }else{
                            return "<a href='javascript:showFeeDetail(\""+rowData.salesuser+"\");'>"+value+"</a>";
                        }

                    }
                },
                {field:'charge',title:'费用合计',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'salesusercost',title:'业务员费比',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }
                    }
                },
                {field:'salesuseroutput',title:'业务员产出比',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                }

            ]]
        });

        $("#report-datagrid-salesuserInputOutputSheetPage").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            method:'post',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            url:'journalsheet/costsFee/getSalesuserInputOutputData.do',
            queryParams:initQueryJSON,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-salesuserInputOutputSheetPage',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SR_footerobject = footerrows[0];
                }
            },
            onCheckAll:function(){
                amountTotalCount();
            },
            onUncheckAll:function(){
                amountTotalCount();
            },
            onCheck:function(){
                amountTotalCount();
            },
            onUncheck:function(){
                amountTotalCount();
            }
        }).datagrid("columnMoving");

        $("#report-supplierid-salesuserInputOutputSheetPage").supplierWidget();
        //客户业务员
        $("#report-salesuser-salesuserInputOutputSheetPage").widget({
            referwid: 'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect: false,
            width: 170,
            onlyLeafCheck: false
        });
        //客户
        $("#report-customerid-salesuserInputOutputSheetPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
            singleSelect:false,
            width:215,
            onlyLeafCheck:true
        });
        //总店
        $("#report-pid-salesuserInputOutputSheetPage").widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect: false,
            width: 170,
            onlyLeafCheck: false
        });
        //查询
        $("#report-queay-salesuserInputOutputSheetPage").click(function(){
            var queryJSON = $("#report-form-salesuserInputOutputSheetPage").serializeJSON();
            $("#report-datagrid-salesuserInputOutputSheetPage").datagrid('load', queryJSON);
        });
        //重置
        $("#report-reload-salesuserInputOutputSheetPage").click(function(){
            $("#report-salesuser-salesuserInputOutputSheetPage").widget('clear');
            $("#report-customerid-salesuserInputOutputSheetPage").widget('clear');
            $("#report-supplierid-salesuserInputOutputSheetPage").supplierWidget('clear');
            $("#report-form-salesuserInputOutputSheetPage")[0].reset();
            $("#report-query-groupcols").val("");
            var queryJSON = $("#report-form-salesuserInputOutputSheetPage").serializeJSON();
            $("#report-datagrid-salesuserInputOutputSheetPage").datagrid('load', queryJSON);
        });

    });

    function showFeeDetail(salesuser){
        var queryParams =  $("#report-form-salesuserInputOutputSheetPage").serializeJSON();
        if('' == salesuser){
            queryParams['salesuser'] = "empty" ;
        }else{
            queryParams['salesuser'] = salesuser ;
        }
        $('#customercost-table-payable-detail').dialog({
            title: '客户应付费用详情',
            width:1000,
            height:450,
            closed:false,
            modal:true,
            cache:false,
            maximizable:true,
            resizable:true,
            href: 'journalsheet/costsFee/showCustomerCostPayableDetailPage.do',
            queryParams:queryParams
        });
    }

    function showExpenseDetail(salesuser){
        var queryParams =  $("#report-form-salesuserInputOutputSheetPage").serializeJSON();
        if('' == salesuser){
            queryParams['salesuser'] = "empty" ;
        }else{
            queryParams['salesuser'] = salesuser ;
        }
        $('#dailycost-table-payable-detail').dialog({
            title: '业务员费用支出详情',
            width:600,
            height:300,
            closed:false,
            modal:true,
            cache:false,
            maximizable:true,
            resizable:true,
            href: 'journalsheet/costsFee/showDailyCostDetailPage.do',
            queryParams:queryParams
        });

    }

    function amountTotalCount(){
        var rows = $("#report-datagrid-salesuserInputOutputSheetPage").datagrid('getChecked');
        var saleamount = 0;
        var salemarginamount = 0;
        var costamount = 0;
        var expense = 0;
        var charge = 0 ;
        var fee = 0 ;
        var realrate = 0;
        var salesusercost = 0;
        var salesuseroutput = 0;
        for(var i=0;i<rows.length;i++){
            saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
            salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
            expense = Number(expense)+Number(rows[i].expense == undefined ? 0 : rows[i].expense);
            fee = Number(fee)+Number(rows[i].fee == undefined ? 0 : rows[i].fee);
            charge = Number(charge) + Number(rows[i].charge == undefined ? 0 : rows[i].charge);
            costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
        }
        if(Number(saleamount) != 0){
            salesusercost = charge/saleamount;
            salesusercost = salesusercost*100 ;
            realrate = (saleamount - costamount)/saleamount;
            realrate = realrate*100 ;
            salesuseroutput = (salemarginamount - charge )/saleamount;
            salesuseroutput = salesuseroutput*100;
        }
        var foot = [{salesusername:'选中合计',saleamount:saleamount,salemarginamount:salemarginamount,expense:expense,fee:fee,charge:charge,
            realrate:realrate,salesusercost:salesusercost,salesuseroutput:salesuseroutput}];
        if(null!=SR_footerobject){
            foot.push(SR_footerobject);
        }
        $("#report-datagrid-salesuserInputOutputSheetPage").datagrid("reloadFooter",foot);
    }



</script>

</body>
</html>
