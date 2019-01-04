<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>退货价格异常报表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north'">
        <div class="buttonBG">
            <security:authorize url="/report/exception/exportRejectPriceReportData.do">
                <a href="javaScript:void(0);" id="report-export-rejectpricereport" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
    </div>
    <div data-options="region:'center'">
        <div id="report-queryDiv-rejectpricereport" style="padding:0px;height:auto">
            <form id="report-queryForm-rejectpricereport">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today}" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
                            到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today}" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                        <td>单据编号:</td>
                        <td><input type="text" name="billid" class="len150" /></td>
                        <td>商品编码/条码:</td>
                        <td><input type="text" name="goodsidorbarcode" class="len150"/></td>
                    </tr>
                    <tr>
                        <td>客户名称:</td>
                        <td><input type="text" id="report-customerid-rejectpricereport" name="customerid" /></td>
                        <td>商品品牌：</td>
                        <td><input type="text" id="report-brandid-rejectpricereport" name="brandid" /></td>
                        <td>总店:</td>
                        <td><input type="text" id="report-pcustomerid-rejectpricereport" name="pcustomerid" /></td>
                    </tr>
                    <tr>
                        <td>供应商:</td>
                        <td><input type="text" id="report-supplierid-rejectpricereport" name="supplierid"/></td>
                        <td>交易价格：</td>
                        <td><select name="pricecompare" class="len150">
                            <option></option>
                            <option value="0">等于参照价格</option>
                            <option value="1">大于参照价格</option>
                            <option value="2">小于参照价格</option>
                        </select></td>
                        <td colspan="2">
                            <a href="javascript:;" id="report-query-rejectpricereport" class="button-qr">查询</a>
                            <a href="javaScript:;" id="report-reset-rejectpricereport" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="report-datagrid-rejectpricereport"></table>
    </div>
</div>
<script type="text/javascript">
    var SPR_footerobject = null;
    var initQueryJSON = $("#report-queryForm-rejectpricereport").serializeJSON();

    $(function(){
        //商品品牌
        $("#report-brandid-rejectpricereport").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:true,
            width:'150'
        });
        //客户
        $("#report-customerid-rejectpricereport").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            singleSelect:true,
            width:'225'
        });
        //总店
        $("#report-pcustomerid-rejectpricereport").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect:true,
            width:'150'
        });
        //供应商
        $("#report-supplierid-rejectpricereport").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            singleSelect:true,
            width:'225'
        });

        var rejectpricereportCol = $("#report-datagrid-rejectpricereport").createGridColumnLoad({
            frozenCol : [[{field:'ck',checkbox:true}]],
            commonCol : [[
                {field:'rejectid',title:'退货通知单号',width:125, align:'left',sortable:true},
                {field:'id',title:'销售退货入库单号',width:125, align:'left',sortable:true},
                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:60,align:'left'},
                {field:'customername',title:'客户名称',width:150,align:'left'},
                {field:'goodsid',title:'商品编码',width:60,align:'left',sortable:true},
                {field:'goodsname',title:'商品名称',width:200,align:'left'},
                {field:'barcode',title:'条形码',width:90,align:'left'},
                {field:'brandid',title:'商品品牌',width:90,align:'left',
                    formatter:function(value,row,index){
                        return row.brandname;
                    }
                },
                {field:'unitid',title:'单位',resizable:true,align:'left',
                    formatter:function(value,row,index){
                        return row.unitname;
                    }
                },
                {field:'taxprice',title:'交易价格',width:60,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    },
                    styler: function(value,row,index){
                        <c:choose>
                            <c:when test="${salesRejectCustomerGoodsPrice == '0'}">
                            if (value < row.defaultprice){
                                return 'background-color:red;';
                            }else if(value > row.defaultprice){
                                return 'background-color:#D8FFD8;';
                            }
                            </c:when>
                            <c:when test="${salesRejectCustomerGoodsPrice == '1'}">
                            if (value < row.lastprice){
                                return 'background-color:red;';
                            }else if(value > row.lastprice){
                                return 'background-color:#D8FFD8;';
                            }
                            </c:when>
                            <c:when test="${salesRejectCustomerGoodsPrice == '2'}">
                            if (value < row.lowestprice){
                                return 'background-color:red;';
                            }else if(value > row.lowestprice){
                                return 'background-color:#D8FFD8;';
                            }
                            </c:when>
                        </c:choose>
                    }
                },
                {field:'unitnum',title:'交易数量',width:60,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'taxamount',title:'交易金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                <c:choose>
                    <c:when test="${salesRejectCustomerGoodsPrice == '0'}">
                        {field:'defaultprice',title:'参照价格',width:80,align:'right',
                            formatter:function(value,row,index){
                                return formatterMoney(value);
                            }
                        }
                    </c:when>
                    <c:when test="${salesRejectCustomerGoodsPrice == '1'}">
                        {field:'lastprice',title:'参照价格',width:80,align:'right',
                            formatter:function(value,row,index){
                                return formatterMoney(value);
                            }
                        }
                    </c:when>
                    <c:when test="${salesRejectCustomerGoodsPrice == '2'}">
                        {field:'lowestprice',title:'参照价格',width:80,align:'right',
                            formatter:function(value,row,index){
                                return formatterMoney(value);
                            }
                        }
                    </c:when>
                </c:choose>
            ]]
        });

        $("#report-datagrid-rejectpricereport").datagrid({
            authority:rejectpricereportCol,
            frozenColumns: rejectpricereportCol.frozen,
            columns:rejectpricereportCol.common,
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            pagination:true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            showFooter: true,
            pageSize:100,
            toolbar:'#report-queryDiv-rejectpricereport',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    SPR_footerobject = footerrows[0];
                    countTotal();
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

        $("#report-query-rejectpricereport").click(function(){
            var queryJSON = $("#report-queryForm-rejectpricereport").serializeJSON();
            $("#report-datagrid-rejectpricereport").datagrid({
                url:'report/exception/getRejectPriceReportList.do',
                pageNumber:1,
                queryParams:queryJSON
            }).datagrid("columnMoving");
        });
        $("#report-reset-rejectpricereport").click(function(){
            $("#report-brandid-rejectpricereport").widget("clear");
            $("#report-customerid-rejectpricereport").widget("clear");
            $("#report-pcustomerid-rejectpricereport").widget("clear");
            $("#report-supplierid-rejectpricereport").widget('clear');
            $("#report-queryForm-rejectpricereport").form("reset");
            $("#report-datagrid-rejectpricereport").datagrid("loadData",[]);
        });

        $("#report-export-rejectpricereport").Excel('export',{
            queryForm: "#report-queryForm-rejectpricereport",
            type:'exportUserdefined',
            name:'退货价格异常报表',
            url:'report/exception/exportRejectPriceReportList.do'
        });
    });

    function countTotal(){
        var rows = $("#report-datagrid-rejectpricereport").datagrid("getChecked");
        var unitnum = 0;
        var taxamount = "";
        for(var i=0;i<rows.length;i++){
            unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
        }

        var foot = [{rejectid:'选中合计',unitnum:Number(unitnum.toFixed(3)),taxamount:taxamount}];

        if(null!=SPR_footerobject){
            foot.push(SPR_footerobject);
        }
        $("#report-datagrid-rejectpricereport").datagrid("reloadFooter",foot);
    }
</script>
</body>
</html>
