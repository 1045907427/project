<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>按制单人分品牌分客户统计页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<form action="" id="report-query-form-salesAdduserDetail" method="post">
    <input type="hidden" name="adduserid" value="${adduserid}"/>
    <input type="hidden" name="businessdate1" value="${businessdate1}"/>
    <input type="hidden" name="businessdate2" value="${businessdate2}"/>
    <input type="hidden" name="salesarea" value="${salesarea}"/>
    <input type="hidden" name="customerid" value="${customerid}"/>
    <input type="hidden" name="brandid" value="${brandid}"/>
    <input type="hidden" name="salesdept" value="${salesdept}"/>
    <input type="hidden" name="datasqltype" value="adduserid"/>
</form>
<div id="report-tab-salesAdduserDetail">
    <security:authorize url="/report/sales/salesAdduserReportExport.do">
        <a href="javaScript:void(0);" id="report-export-salesAdduserDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    </security:authorize>
</div>
<div id="tt" class="easyui-tabs" data-options="fit:true">
    <div title="分品牌统计" style="padding:2px;">
        <table id="report-datagrid-salesAdduserDetail"></table>
    </div>
    <div title="分客户统计" style="padding:2px;">
        <table id="report-datagrid-salesAdduserDetail-customer"></table>
    </div>
    <div title="分商品统计" style="padding:2px;">
        <table id="report-datagrid-salesAdduserDetail-goodsid"></table>
    </div>
</div>
<script type="text/javascript">
$('#tt').tabs({
    tools:'#report-tab-salesAdduserDetail'
});
var initQueryJSON = $("#report-query-form-salesAdduserDetail").serializeJSON();
$(function(){
    var salesAdduserDetailtableColumnListJson = $("#report-datagrid-salesAdduserDetail").createGridColumnLoad({
        frozenCol : [[]],
        commonCol : [[
            {field:'brandid',title:'商品品牌',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.brandname;
                }
            },
            {field:'branddept',title:'品牌部门',width:80,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.branddeptname;
                }
            },
            {field:'ordernum',title:'订单数量',width:60,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'ordertotalbox',title:'订单箱数',width:60,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'initsendtotalbox',title:'发货单箱数',width:70,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'directreturntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'checkreturntotalbox',title:'退货箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'returntotalbox',title:'退货总箱数',width:70,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'returnamount',title:'退货合计',width:60,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'salenum',title:'销售数量',width:60,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
    var customerAdduserDetailtableColumnListJson = $("#report-datagrid-salesAdduserDetail-customer").createGridColumnLoad({
        frozenCol : [[]],
        commonCol : [[
            {field:'customerid',title:'客户编号',sortable:true,width:60},
            {field:'customername',title:'客户名称',width:210},
            {field:'pcustomerid',title:'总店名称',sortable:true,width:60,
                formatter:function(value,rowData,rowIndex){
                    return rowData.pcustomername;
                }
            },
            {field:'salesarea',title:'所属区域',sortable:true,width:60,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesareaname;
                }
            },
            {field:'salesdept',title:'所属部门',sortable:true,width:60,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.salesdeptname;
                }
            },
            {field:'ordernum',title:'订单数量',width:60,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'ordertotalbox',title:'订单箱数',width:60,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'initsendtotalbox',title:'发货单箱数',width:70,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'directreturntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'checkreturntotalbox',title:'退货箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'returntotalbox',title:'退货总箱数',width:70,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'returnamount',title:'退货合计',width:60,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'salenum',title:'销售数量',width:60,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
    var goodsAdduserDetailtableColumnListJson = $("#report-datagrid-salesAdduserDetail-goodsid").createGridColumnLoad({
        frozenCol : [[]],
        commonCol : [[
            {field:'goodsid',title:'商品编码',width:60,sortable:true},
            {field:'goodsname',title:'商品名称',width:250},
            {field:'barcode',title:'条形码',width:90},
            {field:'brandid',title:'商品品牌',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.brandname;
                }
            },
            {field:'goodssort',title:'商品分类',width:100,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.goodssortname;
                }
            },
            {field:'goodstype',title:'商品类型',width:60,
                formatter:function(value,rowData,rowIndex){
                    return rowData.goodstypename;
                }
            },
            {field:'unitname',title:'主单位',width:45,hidden:true},
            {field:'ordernum',title:'订单数量',width:60,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'ordertotalbox',title:'订单箱数',width:60,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'initsendtotalbox',title:'发货单箱数',width:70,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'sendtotalbox',title:'发货出库箱数',width:80,align:'right',isShow:true,sortable:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'directreturntotalbox',title:'直退箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'checkreturntotalbox',title:'退货箱数',width:60,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
                    return formatterNum(value);
                }
            },
            {field:'returntotalbox',title:'退货总箱数',width:70,sortable:true,align:'right',isShow:true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'returnamount',title:'退货合计',width:60,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'salenum',title:'销售数量',width:60,align:'right',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterNum(value);
                }
            },
            {field:'saletotalbox',title:'销售箱数',width:60,align:'right',isShow:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
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
    $("#report-datagrid-salesAdduserDetail").datagrid({
        authority:salesAdduserDetailtableColumnListJson,
        frozenColumns: salesAdduserDetailtableColumnListJson.frozen,
        columns:salesAdduserDetailtableColumnListJson.common,
        method:'post',
        fit:true,
        rownumbers:true,
        pagination:true,
        showFooter: true,
        singleSelect:true,
        pageSize:100,
        toolbar:'#report-tab-salesAdduserDetail',
        url: 'report/sales/showBaseSalesReportData.do?groupcols=brandid',
        queryParams:initQueryJSON
    }).datagrid("columnMoving");
    $("#report-datagrid-salesAdduserDetail-customer").datagrid({
        authority:customerAdduserDetailtableColumnListJson,
        frozenColumns: customerAdduserDetailtableColumnListJson.frozen,
        columns:customerAdduserDetailtableColumnListJson.common,
        method:'post',
        title:'',
        fit:true,
        rownumbers:true,
        pagination:true,
        showFooter: true,
        pageSize:100,
        singleSelect:true,
        url: 'report/sales/showBaseSalesReportData.do?groupcols=customerid',
        queryParams:initQueryJSON
    }).datagrid("columnMoving");
    $("#report-datagrid-salesAdduserDetail-goodsid").datagrid({
        authority:goodsAdduserDetailtableColumnListJson,
        frozenColumns: goodsAdduserDetailtableColumnListJson.frozen,
        columns:goodsAdduserDetailtableColumnListJson.common,
        method:'post',
        title:'',
        fit:true,
        rownumbers:true,
        pagination:true,
        showFooter: true,
        pageSize:100,
        singleSelect:true,
        url: 'report/sales/showBaseSalesReportData.do?groupcols=goodsid',
        queryParams:initQueryJSON
    }).datagrid("columnMoving");

    $("#report-export-salesAdduserDetail").Excel('export',{
        queryForm: "#report-query-form-salesAdduserDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
        type:'exportUserdefined',
        name:'按制单人：[${addusername}]统计表',
        url:'report/sales/exportSalesCustomerDetailReportData.do?groupcols=brandid;customerid;goodsid'
    });
});
</script>
</body>
</html>
