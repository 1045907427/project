<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户库存销量报表</title>
    <%@include file="/include.jsp"%>
</head>
<body>
<table id="crm-table-customerSummaryReportPage"></table>
<div id="crm-query-customerSummaryReportPage" style="height:85px;padding: 0px">
    <security:authorize url="/crm/report/exportCustomerSummaryReportGroup.do">
        <a href="javaScript:void(0);" id="crm-export-customerSummaryReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
    </security:authorize>

    <form action="" id="crm-form-customerSummaryReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                    到 <input type="text" name="businessdate1" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                </td>
                <td>商&nbsp;&nbsp;品：</td>
                <td class="tdinput"><input type="text" name="goodsid" id="crm-goodsid-customerSummaryReportPage" class="len160" /> </td>
                <%--<td>单据编号：</td>--%>
                <%--<td class="tdinput"><input type="text" name="id" style="width: 160px" /> </td>--%>
                <td>小计列：</td>
                <td colspan="2">
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid" checked />客户</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid" />商品</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdeptname"/>销售部门</label>
                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesusername"/>客户业务员</label>
                    <input id="report-query-groupcols" type="hidden" name="groupcols" value="customerid" />
                    <%--<div style="margin-top:2px">--%>
                    <%--<div style="line-height: 25px;margin-top: 2px;">--%>
                    <%----%>
                    <%--</div>--%>
                    <%--</div>--%>
                </td>
            </tr>
            <tr>
                <td>客&nbsp;&nbsp;户：</td>
                <td class="tdinput"><input type="text" id="crm-customer-customerSummaryReportPage" style="width: 210px" name="customerid" /></td>
                <td>供应商：</td>
                <td class="tdinput"><input type="text" name="supplierid" id="crm-supplierid-customerSummaryReportPage" class="len160" /> </td>
                <td colspan="2" class="tdbutton" style="padding-left: 5px">
                    <a href="javascript:;" id="crm-queay-customerSummaryReportPage" class="button-qr">查询</a>
                    <a href="javaScript:;" id="crm-resetQueay-customerSummaryReportPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="crm-importModel-dialog" ></div>
<div id="crm-updateSummary-dialog" ></div>
<div id="crm-clearSummary-dialog" ></div>
<div id="crm-goodsHistory-dialog"></div>
<script type="text/javascript">
    var footerobject  = null;
    var initQueryJSON = $("#crm-form-customerSummaryReportPage").serializeJSON();
    //小计列
    $(".groupcols").click(function(){
        var cols = "";
        $("#report-query-groupcols").val(cols);
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

    $(function(){
        //表头标题
        var crmTerminalJson = $("#crm-table-customerSummaryReportPage").createGridColumnLoad({
            name :'t_crm_customer_summary',
            commonCol : [[
//                {field:'id',title:'编号',width:150, align: 'left',hidden:true},
//                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:70,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:150,align:'left',isShow:true},
                {field:'supplierid',title:'供应商名称',sortable:true,width:200,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.suppliername;
                    }
                },
                {field:'goodsid',title:'商品编码',sortable:true,width:100},
                {field:'goodsname',title:'商品名称',width:200},
                {field:'spell',title:'助记符',width:80,hidden:true},
                {field:'barcode',title:'条形码',width:120,sortable:true,hidden:true},
                {field:'boxnum',title:'箱装量',width:50,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'brandid',title:'品牌名称',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'salesdeptname',title:'销售部门',sortable:true,width:120},
                {field:'salesusername',title:'客户业务员',sortable:true,width:120},
//                {field:'taxprice',title:'单价',width:60,align:'right',sortable:true,
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterMoney(value);
//                    }
//                },
                {field:'stocknum',title:'库存数量',width:90,
                    formatter:function(value,rowData,rowIndex){
                        if(value == 0){
                            return 0 ;
                        }else{
                            return formatterBigNumNoLen(value);
                        }
                    }
                },
                {field:'stockbox',title:'库存箱数',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'costtaxamount', title:'成本金额',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'costnotaxamount', title:'成本未税金额',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'taxamount', title:'零售金额',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxamount', title:'零售未税金额',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'salesnum',title:'销量数量',width:90,
                    formatter:function(value,rowData,rowIndex){
                        if(value == 0){
                            return 0 ;
                        }else{
                            return formatterBigNumNoLen(value);
                        }
                    }
                },
                {field:'salesbox',title:'销量箱数',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'salesamount', title:'销量金额',width:90,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
//                {field:'taxamount',title:'金额',width:80,align:'right',
//                    formatter:function(value,row,index){
//                        return formatterMoney(value);
//                    }
//                },
//                {field:'boxweight',title:'总重量(千克)',width:80,hidden:true},
//                {field:'boxvolume',title:'总体积(立方米)',width:100,hidden:true},
                {field:'remark',title:'备注',width:80}
            ]]
        });
        //导出
        $("#crm-export-customerSummaryReportPage").click(function(){
            //封装查询条件
            var objecr  = $("#crm-table-customerSummaryReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "crm/dataport/exportCustomerSummaryReportGroupData.do";
            exportByAnalyse(queryParam,"客户库存报表","crm-table-customerSummaryReportPage",url);
        });

        $("#crm-table-customerSummaryReportPage").datagrid({
            authority: crmTerminalJson,
            frozenColumns: crmTerminalJson.frozen,
            columns: crmTerminalJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            showFooter: true,
            pageSize: 20,
            checkOnSelect: true,
            singleSelect:true,
            queryParams:initQueryJSON,
            url: 'crm/dataport/getCustomerSummaryReportGroupData.do',
            toolbar:'#crm-query-customerSummaryReportPage',
            onLoadSuccess:function() {
                setColumn();
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    footerobject = footerrows[0];
                }
            }
        });
        //查询
        $("#crm-queay-customerSummaryReportPage").click(function(){
            setColumn();
            var queryJSON = $("#crm-form-customerSummaryReportPage").serializeJSON();
            $("#crm-table-customerSummaryReportPage").datagrid('load', queryJSON);
        });
        //重置
        $("#crm-resetQueay-customerSummaryReportPage").click(function(){
            $("#crm-customer-customerSummaryReportPage").customerWidget("clear");
            $("#crm-supplierid-customerSummaryReportPage").widget("clear");
            $("#crm-goodsid-customerSummaryReportPage").widget("clear");
            $("#crm-form-customerSummaryReportPage").form("reset");
            $("#report-query-groupcols").val("customerid");
            setColumn();
            var queryJSON = $("#crm-form-customerSummaryReportPage").serializeJSON();
            $("#crm-table-customerSummaryReportPage").datagrid('load', queryJSON);
        });
        //客户参照窗口
        $("#crm-customer-customerSummaryReportPage").customerWidget({
            singleSelect:false,
            isdatasql:false,
            onlyLeafCheck:false
        });
        //供应商
        $("#crm-supplierid-customerSummaryReportPage").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            singleSelect:false,
            width:160,
            onlyLeafCheck:false
        });
        //商品
        $("#crm-goodsid-customerSummaryReportPage").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:160,
            onlyLeafCheck:false
        });
        //查看历史库存
        function showGoodsHistorByCustomerid(){
            var row = $("#crm-table-customerSummaryReportPage").datagrid('getSelected');
            if(row == null){
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            $("#crm-goodsHistory-dialog").dialog({
                title:'客户['+row.customerid+'] 商品['+row.goodsid+']'+row.goodsname+' 历史库存表',
                width:600,
                height:400,
                closed:false,
                modal:true,
                cache:false,
                maximizable:true,
                resizable:true,
                href:'crm/dataport/showCustomerSummaryHistory.do',
                queryParams:{customerid:row.customerid,goodsid:row.goodsid}
            });

        }

        var $datagrid = $("#crm-table-customerSummaryReportPage");
        function setColumn(){
            var colume = "customerid";
            var cols = $("#report-query-groupcols").val();
            $datagrid.datagrid('hideColumn', "customerid");
            $datagrid.datagrid('hideColumn', "customername");
            $datagrid.datagrid('hideColumn', "supplierid");
            $datagrid.datagrid('hideColumn', "goodsid");
            $datagrid.datagrid('hideColumn', "goodsname");
            $datagrid.datagrid('hideColumn', "barcode");
            $datagrid.datagrid('hideColumn', "boxnum");
            $datagrid.datagrid('hideColumn', "brandid");
            $datagrid.datagrid('hideColumn', "salesdeptname");
            $datagrid.datagrid('hideColumn', "salesusername");
            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                var col = colarr[i];
                colume = col;
                if(col=='customerid'){
                    $datagrid.datagrid('showColumn', "customerid");
                    $datagrid.datagrid('showColumn', "customername");
                }else if(col=="salesdeptname"){
                    $datagrid.datagrid('showColumn', "salesdeptname");
                }else if(col=="salesusername"){
                    $datagrid.datagrid('showColumn', "salesusername");
                }else if(col=="goodsid"){
                    $datagrid.datagrid('showColumn', "goodsid");
                    $datagrid.datagrid('showColumn', "goodsname");
                    $datagrid.datagrid('showColumn', "barcode");
                    $datagrid.datagrid('showColumn', "boxnum");
                    $datagrid.datagrid('showColumn', "brandid");
                }else if(col=="brandid"){
                    $datagrid.datagrid('showColumn', "brandid");
                }else if(col=="supplierid"){
                    $datagrid.datagrid('showColumn', "supplierid");
                }
            }
            return colume;
        }


    });

</script>
</body>
</html>
