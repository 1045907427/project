<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订货单报表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="report-datagrid-orderGoodsReportPage"></table>
<div id="report-toolbar-orderGoodsReportPage"  style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/orderGoodsReportExport.do">
            <a href="javaScript:void(0);" id="report-autoExport-orderGoodsReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" >全局导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-orderGoodsReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input id="report-query-businessdate1" type="text" name="businessdate" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
                    到 <input id="report-query-businessdate2" type="text" name="businessdate1" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>商品:</td>
                <td><input type="text" id="report-query-goodsid" name="goodsid"/></td>
                <td>销售部门:</td>
                <td><input type="text" id="report-query-salesdept" name="salesdept"/></td>
            </tr>
            <tr>
                <td>订货单编号:</td>
                <td><input type="text" id="report-query-ordergoodsid" style="width: 225px;" name="ordergoodsid"/></td>
                <td>品牌:</td>
                <td><input type="text" id="report-query-brandid" name="brandid"/></td>
                <td>商品分类:</td>
                <td><input type="text" id="report-query-goodssort" name="goodssort"/></td>
            </tr>
            <tr>
                <td>客户:</td>
                <td><input type="text" id="report-query-customerid" name="customerid"/></td>
                <td>订单编号:</td>
                <td><input type="text" id="report-query-orderid"  style="width: 130px;" name="orderid"/></td>
                <td>是否完成:</td>
                <td>
                    <select id="report-query-iscomplete" name="iscomplete" style="width: 130px;">
                        <option></option>
                        <option value="0">未完成</option>
                        <option value="1">已完成</option>
                    </select>
                </td>
                <td colspan="4">
                    <a href="javaScript:void(0);" id="report-query-orderGoodsReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-orderGoodsReportPage" class="button-qr">重置</a>

                </td>
            </tr>
        </table>
    </form>
</div>
<div id="report-dialog-salesBrandGoodsDetail"></div>
<script type="text/javascript">
    var SR_footerobject  = null;
    var initQueryJSON = $("#report-query-form-orderGoodsReportPage").serializeJSON();
    $(function(){

        $("#report-autoExport-orderGoodsReportPage").click(function(){
            var queryJSON = $("#report-query-form-orderGoodsReportPage").serializeJSON();
            //获取排序规则
            var objecr  = $("#report-datagrid-orderGoodsReportPage").datagrid("options");
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "report/sales/exportOrderGoodsReportData.do";
            exportByAnalyse(queryParam,"销售订货单报表","report-datagrid-orderGoodsReportPage",url);
        });

        var tableColumnListBrandJson = $("#report-datagrid-orderGoodsReportPage").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field: 'orderid', title: '单据编码', width: 150, align: ' left', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != undefined) {
                            var functionstr = "showSourceData('" + value + "')";
                            return '<a href="javascript:;" onclick="' + functionstr + '" style="text-decoration:underline;cursor:hand;">' + value + '</a>';
                        }
                    }
                },
                {field: 'businessdate', title: '业务日期', width: 80, align: ' left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 70, align: ' left', sortable: true},
                {field: 'customername', title: '客户名称', width: 130, align: ' left', sortable: true},
                {field: 'salesdept', title: '销售部门', width: 100, align: 'left', sortable: true},
                {field: 'goodsid', title: '商品编码', width: 120, align: ' left', sortable: true},
                {
                    field: 'goodsname', title: '商品名称', width: 200, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            if (rowData.isdiscount == '1') {
                                return "（折扣）" + rowData.goodsInfo.name;
                            } else if (rowData.isdiscount == '2') {
                                return "（折扣）" + rowData.goodsInfo.name;
                            } else {
                                if (rowData.deliverytype == '1') {
                                    return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                                } else if (rowData.deliverytype == '2') {
                                    return "<font color='blue'>&nbsp;捆绑 </font>" + rowData.goodsInfo.name;
                                } else {
                                    return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" >' + rowData.goodsInfo.name + '</a>';
                                }
                            }
                        }else if (rowData.goodsname) {
                            return rowData.goodsname;
                        }else {
                            return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" ></a>';
                        }
                    }
                },
                {field: 'deliverytype', title: '商品类型', width: 70, align: ' left',hidden:true},
                {
                    field: 'spell', title: '助记符', width: 80, aliascol: 'goodsid', hidden: true, align: 'left',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != undefined) {
                            return rowData.goodsInfo.spell;
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'shopid', title: '店内码', width: 80, aliascol: 'goodsid', hidden: true, align: 'left'},
                {
                    field: 'barcode', title: '条形码', width: 90, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, row, rowIndex) {
                        if (row.isdiscount != '1' && row.goodsInfo != null) {
                            return row.goodsInfo.barcode;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'brandName', title: '商品品牌', width: 60, align: 'left', aliascol: 'goodsid', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.brandName;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                    formatter: function (value, row, rowIndex) {
                        if (row.isdiscount != '1' && row.goodsInfo != null) {
                            return formatterBigNumNoLen(row.goodsInfo.boxnum);
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'unitname', title: '单位', width: 35, align: 'left'},
                // {
                //     field: 'usablenum', title: '可用量', width: 60, align: 'right', isShow: true, sortable: true,
                //     formatter: function (value, row, index) {
                //         if (((row.isdiscount == '0' || row.isdiscount == '') && row.goodsInfo != null) || row.goodsid == "选中合计" || row.goodsid == "合计") {
                //             return formatterBigNumNoLen(value);
                //         } else {
                //             return "";
                //         }
                //     }
                // },
                {
                    field: 'unitnum', title: '数量', width: 80, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(value);
                    },
                    styler: function (value, row, index) {
                        var status = $("#sales-customer-status").val();
                        if (row.goodsid != null && row.goodsid != "合计" && row.goodsid != "选中合计" && (status == null || status == '1' || status == "2") && Number(row.usablenum) < Number(value)) {
                            return 'background-color:red;';
                        }
                    }
                },
                {
                    field: 'notorderunitnum', title: '未生成订单数量', width: 80, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        if(value==0){
                            return 0;
                        }
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'orderunitnum', title: '已生成订单数量', width: 80, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        if(value==0){
                            return 0;
                        }
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'referenceprice', title: '参考价', width: 60, align: 'right', isShow: true, sortable: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount == '0' ) {
                            return '<a id="sales-historyprice-orderpage" gid="' + row.goodsid + '" >' + formatterMoney(value) + '</a>';
                        }else{
                            return '';
                        }

                    }
                },
                {
                    field: 'taxprice', title: '单价', width: 60, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            if (parseFloat(value) > parseFloat(row.oldprice)) {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font></a>";
                                } else {
                                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                                }
                            }
                            else if (parseFloat(value) < parseFloat(row.oldprice)) {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                                } else {
                                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                                }
                            }
                            else {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font style='cursor: pointer;' title='最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                                } else {
                                    if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                                        return "<font  style='cursor: pointer;' title='要货价:" + formatterMoney(row.demandprice) + "'>" + formatterMoney(value) + "</font>";
                                    } else {
                                        return formatterMoney(value);
                                    }
                                }
                            }
                        } else {
                            return "";
                        }
                    },
                    styler: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                return 'background-color:yellow;';
                            } else {
                                if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                                    return 'background-color:#F4AE8A;';
                                }
                            }
                        }
                    }
                },


                {
                    field: 'taxamount', title: '金额', width: 60, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxprice', title: '未税单价', width: 60, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税金额', width: 60, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'taxtype', title: '税种', width: 60, align: 'left', hidden: true,
                    formatter: function (value, row, index) {
                        return row.taxtypename;
                    }
                },
                {
                    field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
                {field: 'remark', title: '备注', width: 200, align: 'left'}
            ]]
        });

        $("#report-datagrid-orderGoodsReportPage").datagrid({
            authority:tableColumnListBrandJson,
            frozenColumns: tableColumnListBrandJson.frozen,
            columns:tableColumnListBrandJson.common,
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
            toolbar:'#report-toolbar-orderGoodsReportPage',
            queryParams:initQueryJSON,
            url:'report/sales/showOrderGoodsReportData.do',
            onDblClickRow:function(rowIndex, rowData){

            }
        }).datagrid("columnMoving");

        $("#report-query-customerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            width:225,
            singleSelect:true
        });

        $("#report-query-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            width:130,
            singleSelect:true
        });

        $("#report-query-goodssort").widget({
            referwid:'RL_T_BASE_GOODS_WARESCLASS',
            width:130,
            singleSelect:true
        });

        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:130,
            singleSelect:true
        });
        $("#report-query-salesdept").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:130,
            singleSelect:false
        });

        //回车事件
        controlQueryAndResetByKey("report-query-orderGoodsReportPage","report-reload-orderGoodsReportPage");

        //查询
        $("#report-query-orderGoodsReportPage").click(function(){
            var queryJSON = $("#report-query-form-orderGoodsReportPage").serializeJSON();
            $("#report-datagrid-orderGoodsReportPage").datagrid('load',queryJSON);
        });
        //重置
        $("#report-reload-orderGoodsReportPage").click(function(){
            $("#report-query-brandid").widget("clear");
            $("#report-query-goodsid").widget("clear");
            $("#report-query-customerid").widget("clear");
            $("#report-query-goodssort").widget("clear");
            $("#report-query-salesdept").widget("clear");
            $("#report-query-form-orderGoodsReportPage")[0].reset();
            var queryJSON = $("#report-query-form-orderGoodsReportPage").serializeJSON();
            $("#report-datagrid-orderGoodsReportPage").datagrid('load',queryJSON);
        });

    });

    function showSourceData(id){
        top.addTab('sales/orderGoodsPage.do?type=edit&id='+id, '销售订货单查看');
    }
</script>
</body>
</html>
