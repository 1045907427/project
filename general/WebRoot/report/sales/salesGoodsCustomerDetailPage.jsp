<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>按商品分客户统计页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-salesGoodsCustomer"></table>
<div id="report-toolbar-salesGoodsCustomer" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/salesGoodsReportExport.do">
            <a href="javaScript:void(0);" id="report-export-salesGoodsCustomer" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-salesGoodsCustomer" method="post">
        <table>
            <tr>
                <td>客户名称:</td>
                <td>
                    <input type="text" id="report-query-customerid" style="width: 210px;" name="customerid"/>
                    <input type="text" id="report-query-deptid" name="salesdept" style="display: none;"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-salesGoodsCustomer" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-salesGoodsCustomer" class="button-qr">重置</a>

                </td>
            </tr>
        </table>
        <input type="hidden" name="goodsid" value="${goodsid}"/>
        <input type="hidden" name="businessdate1" value="${businessdate1}"/>
        <input type="hidden" name="businessdate2" value="${businessdate2}"/>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-salesGoodsCustomer").serializeJSON();
    $(function(){

        $("#report-export-salesGoodsCustomer").click(function(){
            var queryJSON = $("#report-query-form-salesGoodsCustomer").serializeJSON();
            //获取排序规则
            var objecr  = $("#report-datagrid-salesGoodsCustomer").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "report/sales/exportBaseSalesReportData.do?groupcols=customerid";
            exportByAnalyse(queryParam,"按商品：[${goodsid}:${goodsname}],分客户统计表","report-datagrid-salesGoodsCustomer",url);
        });

        $("#report-query-customerid").customerWidget({});
        var tableColumnListDetailJson = $("#report-datagrid-salesGoodsCustomer").createGridColumnLoad({
            frozenCol : [[]],
            commonCol : [[
                {field:'customerid',title:'客户编号',width:60,sortable:true},
                {field:'customername',title:'客户名称',width:230},
                {field:'pcustomername',title:'总店名称',width:60},
                {field:'customersort',title:'客户分类',width:80,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customersortname;
                    }
                },
                {field:'salesuser',title:'客户业务员',width:70,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'unitid',title:'主单位',width:45,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.unitname;
                    }
                },
                {field:'ordernum',title:'订单数量',width:60,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'ordertotalbox',title:'订单箱数',width:60,align:'right',isShow:true,sortable:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'orderamount',title:'订单金额',width:60,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'ordernotaxamount',title:'订单未税金额',width:80,sortable:true,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'initsendnum',title:'发货单数量',width:70,align:'right',hidden:true,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'initsendtotalbox',title:'发货单箱数',width:70,align:'right',isShow:true,sortable:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'initsendamount',title:'发货单金额',width:70,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'initsendnotaxamount',title:'发货单未税金额',width:90,sortable:true,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'sendnum',title:'发货出库数量',width:80,align:'right',hidden:true,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',isShow:true,sortable:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'sendamount',title:'发货出库金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'sendnotaxamount',title:'发货出库未税金额',width:100,sortable:true,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                <c:if test="${map.sendcostamount == 'sendcostamount'}">
                {field:'sendcostamount',title:'发货出库成本',width:80,sortable:true,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                </c:if>
                {field:'pushbalanceamount',title:'冲差金额',width:60,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'directreturnnum',title:'直退数量',width:60,sortable:true,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'directreturntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'checkreturntotalbox',title:'退货箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'checkreturnamount',title:'退货金额',width:60,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'checkreturnrate',title:'退货率',width:60,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                },
                {field:'returnnum',title:'退货总数量',width:70,sortable:true,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'returntotalbox',title:'退货总箱数',width:70,sortable:true,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'returnamount',title:'退货合计',width:60,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'salenum',title:'销售数量',width:60,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'saleamount',title:'销售金额',width:60,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'salenotaxamount',title:'销售无税金额',width:80,align:'right',hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saletax',title:'销售税额',width:60,align:'right',isShow:true,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
                <c:if test="${map.costamount == 'costamount'}">
                ,
                {field:'costamount',title:'成本金额',align:'right',width:60,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
                </c:if>
                <c:if test="${map.salemarginamount == 'salemarginamount'}">
                ,
                {field:'salemarginamount',title:'销售毛利额',width:70,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
                </c:if>
                <c:if test="${map.realrate == 'realrate'}">
                ,
                {field:'realrate',title:'实际毛利率',width:70,align:'right',isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        if(value!=null && value!=0){
                            return formatterMoney(value)+"%";
                        }else{
                            return "";
                        }
                    }
                }
                </c:if>
            ]]
        });
        $("#report-datagrid-salesGoodsCustomer").datagrid({
            authority:tableColumnListDetailJson,
            frozenColumns: tableColumnListDetailJson.frozen,
            columns:tableColumnListDetailJson.common,
            method:'post',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:true,
            pageSize:100,
            toolbar:'#report-toolbar-salesGoodsCustomer',
            url: 'report/sales/showBaseSalesReportData.do?groupcols=customerid',
            queryParams:initQueryJSON
        }).datagrid("columnMoving");

        //查询
        $("#report-queay-salesGoodsCustomer").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-salesGoodsCustomer").serializeJSON();
            $("#report-datagrid-salesGoodsCustomer").datagrid("load",queryJSON);
        });
        $("#report-reload-salesGoodsCustomer").click(function(){
            $("#report-query-customerid").customerWidget("clear");
            $("#report-query-form-salesGoodsCustomer")[0].reset();
            var queryJSON = $("#report-query-form-salesGoodsCustomer").serializeJSON();
            $("#report-datagrid-salesGoodsCustomer").datagrid("load",queryJSON);
        });

        <%--$("#report-export-salesGoodsCustomer").Excel('export',{--%>
        <%--queryForm: "#report-query-form-salesGoodsCustomer", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。--%>
        <%--type:'exportUserdefined',--%>
        <%--name:'按商品：[${goodsid}:${goodsname}],分客户统计表',--%>
        <%--url:'report/sales/exportBaseSalesReportData.do?groupcols=customerid'--%>
        <%--});--%>

    });
</script>
</body>
</html>
