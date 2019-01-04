<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分客户投入产出比报表</title>
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
<table id="report-datagrid-customerInputOutputSheetPage"></table>
<div id="report-toolbar-customerInputOutputSheetPage" style="padding:0px;height:auto">
    <div class="buttonBG">
        <%--<security:authorize url="/journalsheet/costsFee/exportCustomerInputOutputSheet.do">--%>
            <a href="javaScript:void(0);" id="report-export-customerInputOutputSheetPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        <%--</security:authorize>--%>
    </div>
    <form id="report-form-customerInputOutputSheetPage" method="post">
        <table>
            <tr>
                <td>业务日期:</td>
                <td colspan="2">
                    <input type="text" name="businessdate1" id="businessdate1" style="width:100px;" class="Wdate" value="${today}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }',isShowClear:false})" />
                    到<input type="text" name="businessdate2" id="businessdate2" class="Wdate" style="width:100px;" value="${today}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today}',isShowClear:false})" />
                </td>
                <td>供&nbsp;应&nbsp;商:</td>
                <td>
                    <input type="text" id="report-supplierid-customerInputOutputSheetPage" style="width: 170px;" name="supplierid"/>
                </td>
                <td>销售数据来源:</td>
                <td>
                    <select name="salesdatatype">
                        <option value="0">系统销售</option>
                        <option value="1">数据上报</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户:</td>
                <td colspan="2"><input type="text" id="report-customerid-customerInputOutputSheetPage" name="customerid" style="width: 215px;"/></td>
                <td>客户业务员:</td>
                <td><input type="text" id="report-salesuser-customerInputOutputSheetPage" name="salesuser" style="width:170px;"/></td>
            </tr>
            <tr>
                <td>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;店:</td>
                <td colspan="2"><input type="text" id="report-pid-customerInputOutputSheetPage" name="pid" style="width: 215px;"/></td>
                <td>小&nbsp;计&nbsp;列:</td>
                <td>
                    <div>
                        <input type="checkbox" class="groupcols checkbox1" value="customerid" id="customerid" checked/>
                        <label class="divtext" for="customerid">客户</label>
                        <input type="checkbox" class="groupcols checkbox1" value="salesuser" id="salesuser"/>
                        <label class="divtext" for="salesuser">客户业务员</label>
                        <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                        <label class="divtext" for="supplierid">供应商</label>
                        <input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid"/>
                    </div>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-customerInputOutputSheetPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-customerInputOutputSheetPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="customercost-table-payable-detail"></div>
<script type="text/javascript">
    var initQueryJSON = $("#report-form-customerInputOutputSheetPage").serializeJSON();
    var SR_footerobject = null;
    $(function(){
        $(".groupcols").click(function(){
            var cols = "";
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    if(cols==""){
                        cols = $(this).val();
                    }else{
                        cols += ","+$(this).val();
                    }
                    $("#report-query-groupcols").val(cols);
                }
            });
        });
        $("#report-export-customerInputOutputSheetPage").click(function(){
            //封装查询条件
            var objecr  = $("#report-datagrid-customerInputOutputSheetPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "journalsheet/costsFee/exportCustomerInputOutputData.do";
            exportByAnalyse(queryParam,"分客户投入产出比报表","report-datagrid-customerInputOutputSheetPage",url);
        });

        var checkListJson = $("#report-datagrid-customerInputOutputSheetPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol : [[
                {field: 'customerid', title: '客户编号', sortable: true, width: 60},
                {field: 'customername', title: '客户名称', width: 150},
                {field: 'salesuser', title: '客户业务员', sortable: true, width: 70,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {field:'supplierid',title:'供应商编号',width:70,sortable:true},
                {field:'suppliername',title:'供应商名称',width:180},
                {field:'sendamount',title:'发货出库金额',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'pushbalanceamount',title:'冲差金额',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'directreturnamount',title:'直退金额',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'checkreturnamount',title:'退货金额',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
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
                {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'center',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'realrate',title:'销售毛利率',width:70,align:'center',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'factoryamount',title:'工厂投入',resizable:true,align:'center',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'fee',title:'费用合计',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(rowData.customerid == '合计' || rowData.customerid == '选中合计'|| rowData.salesusername == '合计'
                                || rowData.salesusername == '选中合计' || rowData.supplierid == '合计' || rowData.supplierid == '选中合计'){
                            return formatterMoney(value);
                        }else{
                           // return "<a href='javascript:showFeeDetail(\""+rowData.customerid+"','"+rowData.salesuser++"','"+rowData.supplierid+"\");'>"+value+"</a>";
                            return("<a href='javascript:;' onclick=\"showFeeDetail('"+ rowData.customerid +"','"+ rowData.salesuser +"','"+ rowData.supplierid +"')\">" + value + "</a>");

                        }
                    }
                },
                {field:'singlecost',title:'单店费比',resizable:true,align:'center',
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }
                    }
                },
                {field:'singleoutput',title:'单店产出比',resizable:true,align:'center',
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

        $("#report-datagrid-customerInputOutputSheetPage").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            method:'post',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            url:'journalsheet/costsFee/getCustomerInputOutputData.do',
            queryParams:initQueryJSON,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-customerInputOutputSheetPage',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SR_footerobject = footerrows[0];
                }
            },
            onCheckAll:function(){
                amountTotalCount(setColumn());
            },
            onUncheckAll:function(){
                amountTotalCount(setColumn());
            },
            onCheck:function(){
                amountTotalCount(setColumn());
            },
            onUncheck:function(){
                amountTotalCount(setColumn());
            }
        }).datagrid("columnMoving");

        $("#report-supplierid-customerInputOutputSheetPage").supplierWidget();
        //客户业务员
        $("#report-salesuser-customerInputOutputSheetPage").widget({
            referwid: 'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect: false,
            width: 170,
            onlyLeafCheck: false
        });
        //客户
        $("#report-customerid-customerInputOutputSheetPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
            singleSelect:false,
            width:215,
            onlyLeafCheck:true
        });
        //总店客户
        $("#report-pid-customerInputOutputSheetPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect:false,
            width:215,
            onlyLeafCheck:true
        });
        //查询
        $("#report-queay-customerInputOutputSheetPage").click(function(){
            setColumn();
            var queryJSON = $("#report-form-customerInputOutputSheetPage").serializeJSON();
            $("#report-datagrid-customerInputOutputSheetPage").datagrid('load', queryJSON);
        });
        //重置
        $("#report-reload-customerInputOutputSheetPage").click(function(){
            $("#report-salesuser-customerInputOutputSheetPage").widget('clear');
            $("#report-customerid-customerInputOutputSheetPage").widget('clear');
            $("#report-supplierid-customerInputOutputSheetPage").supplierWidget('clear');
            $("#report-form-customerInputOutputSheetPage")[0].reset();
            $("#report-query-groupcols").val("");

            setColumn();
            var queryJSON = $("#report-form-customerInputOutputSheetPage").serializeJSON();
            $("#report-datagrid-customerInputOutputSheetPage").datagrid('load', queryJSON);

        });

    });

    function showFeeDetail(customerid,salesuser,supplierid){
        var queryParams =  $("#report-form-customerInputOutputSheetPage").serializeJSON();
        var col = $("#report-query-groupcols").val();
        if(col.indexOf("customerid") > -1){
            queryParams["customerid"] = customerid;
        }
        if(col.indexOf("salesuser") > -1){
            queryParams["salesuser"] = salesuser;
        }
        if(col.indexOf("supplierid") > -1){
            queryParams["supplierid"] = supplierid;
        }

        if(customerid == ""){
            queryParams["customerid"] = "empty";
        }
        if(salesuser == ""){
            queryParams["salesuser"] = "empty";
        }
        if(supplierid == ""){
            queryParams["supplierid"] = "empty";
        }
        $('#customercost-table-payable-detail').dialog({
            title: '客户应付费用明细',
            width:1000,
            height:450,
            closed:true,
            modal:true,
            cache:false,
            maximizable:true,
            resizable:true,
            href: 'journalsheet/costsFee/showCustomerCostPayableDetailPage.do',
            queryParams:queryParams

        });
        $("#customercost-table-payable-detail").dialog("open");

    }

    $datagrid =  $("#report-datagrid-customerInputOutputSheetPage");
    function setColumn(){
        var colume = "customerid";
        var cols = $("#report-query-groupcols").val();
        if(cols!=""){
            $datagrid.datagrid('hideColumn', "customerid");
            $datagrid.datagrid('hideColumn', "customername");
            $datagrid.datagrid('hideColumn', "salesuser");
            $datagrid.datagrid('hideColumn', "supplierid");
            $datagrid.datagrid('hideColumn', "suppliername");
        }
        else{
            $datagrid.datagrid('showColumn', "customerid");
            $datagrid.datagrid('showColumn', "customername");
            $datagrid.datagrid('showColumn', "salesuser");
            $datagrid.datagrid('showColumn', "supplierid");
            $datagrid.datagrid('showColumn', "suppliername");
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            colume = col;
            if(col=='customerid'){
                $datagrid.datagrid('showColumn', "customerid");
                $datagrid.datagrid('showColumn', "customername");
            }else if(col=="supplierid"){
                $datagrid.datagrid('showColumn', "supplierid");
                $datagrid.datagrid('showColumn', "suppliername");
            }else if(col=="salesuser"){
                $datagrid.datagrid('showColumn', "salesuser");
            }
        }
        return colume;
    }


    function amountTotalCount(col){
        var rows = $("#report-datagrid-customerInputOutputSheetPage").datagrid('getChecked');
        var sendamount = 0;
        var pushbalanceamount = 0;
        var directreturnamount = 0;
        var checkreturnamount = 0;
        var saleamount = 0;
        var costamount = 0;
        var salemarginamount = 0;
        var factoryamount = 0;
        var fee = 0 ;
        var realrate = 0;
        var singlecost = 0;
        var singleoutput = 0;
        for(var i=0;i<rows.length;i++){
            sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
            pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
            directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
            checkreturnamount = Number(checkreturnamount)+Number(rows[i].checkreturnamount == undefined ? 0 : rows[i].checkreturnamount);
            saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
            salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
            factoryamount = Number(factoryamount)+Number(rows[i].factoryamount == undefined ? 0 : rows[i].factoryamount);
            fee = Number(fee)+Number(rows[i].fee == undefined ? 0 : rows[i].fee);
            costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
        }

        var cols = $("#report-query-groupcols").val();
        var col = "cutomerid";
        if(cols != ""){
            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                col = colarr[0] ;
            }
        }
        if(Number(saleamount) != 0){
            singlecost = fee/saleamount;
            singlecost = singlecost*100 ;
            realrate = (saleamount - costamount)/saleamount;
            realrate = realrate*100 ;
            singleoutput = (salemarginamount + factoryamount - fee )/saleamount;
            singleoutput = singleoutput*100;
        }

        var foot = null;
        if(col == "customerid"){
            foot = [{customerid:'选中合计',
                saleamount:saleamount,
                costamount:costamount,
                salemarginamount:salemarginamount,
                factoryamount:factoryamount,
                fee:fee,singlecost:singlecost,
                realrate:realrate,
                singleoutput:singleoutput,
                sendamount:sendamount,
                pushbalanceamount:pushbalanceamount,
                directreturnamount:directreturnamount,
                checkreturnamount:checkreturnamount
            }];
        }else if(col == "salesuser"){
            foot = [{salesusername:'选中合计',
                saleamount:saleamount,
                costamount:costamount,
                salemarginamount:salemarginamount,
                factoryamount:factoryamount,
                fee:fee,
                singlecost:singlecost,
                realrate:realrate,
                singleoutput:singleoutput,
                sendamount:sendamount,
                pushbalanceamount:pushbalanceamount,
                directreturnamount:directreturnamount,
                checkreturnamount:checkreturnamount
            }];
        }else if(col == "supplierid"){
            foot = [{supplierid:'选中合计',
                saleamount:saleamount,costamount:costamount,salemarginamount:salemarginamount,
                factoryamount:factoryamount,fee:fee,singlecost:singlecost,realrate:realrate,
                singleoutput:singleoutput,
                sendamount:sendamount,
                pushbalanceamount:pushbalanceamount,
                directreturnamount:directreturnamount,
                checkreturnamount:checkreturnamount
            }];
        }else{
            foot = [{customerid:'选中合计',
                saleamount:saleamount,costamount:costamount,salemarginamount:salemarginamount,
                factoryamount:factoryamount,fee:fee,singlecost:singlecost,realrate:realrate,
                singleoutput:singleoutput,
                sendamount:sendamount,
                pushbalanceamount:pushbalanceamount,
                directreturnamount:directreturnamount,
                checkreturnamount:checkreturnamount
            }];
        }

        if(null!=SR_footerobject){
            foot.push(SR_footerobject);
        }
        $("#report-datagrid-customerInputOutputSheetPage").datagrid("reloadFooter",foot);
    }


</script>
</body>
</html>
