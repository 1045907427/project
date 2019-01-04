<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>进销存汇总统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
    <style>
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
<table id="report-datagrid-deliveryAllReport"></table>
<div id="report-toolbar-deliveryAllReport" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/report/storage/buySaleReportMonthExport.do">
            <a href="javaScript:void(0);" id="report-buttons-buySaleReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-deliveryAllReport" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
                    到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
                </td>
                <td>品牌:</td>
                <td><input type="text" id="report-query-brandid" name="brandid" style="width: 180px;"/></td>
                <td>仓库:</td>
                <td><input type="text" id="report-query-storageid" name="storageid" style="width: 160px;"/></td>
            </tr>
            <tr>
                <td>商品:</td>
                <td><input type="text" id="report-query-goodsid" name="goodsid" style="width: 225px;"/></td>
                <td>供应商:</td>
                <td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
            </tr>
            <tr>
                <td>小计列:</td>
                <td colspan="3">
                    <div style="margin-top:3px">
                        <input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid" />
                        <label for="goodsid" class="divtext"> 商品</label>
                        <input type="checkbox" class="groupcols checkbox1" value="storageid" id="storageid"/>
                        <label for="storageid" class="divtext"> 仓库</label>
                        <input type="checkbox" class="groupcols checkbox1" value="brandid" id="brandid"/>
                        <label for="brandid" class="divtext"> 品牌</label>
                        <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                        <label for="supplierid" class="divtext"> 供应商</label>
                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    </div>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-deliveryAllReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-deliveryAllReport" class="button-qr">重置</a>

                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var page_cols = "";
    var initQueryJSON = $("#report-query-form-deliveryAllReport").serializeJSON();
    $(function(){
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

        $("#report-datagrid-deliveryAllReport").datagrid({
            columns:[[
                {field:'goodsid',title:'商品编码',width:70,rowspan:2},
                {field:'goodsname',title:'商品名称',width:130,rowspan:2},
                {field:'barcode',title:'条形码',sortable:true,width:90,rowspan:2},
                {field:'brandid',title:'品牌名称',width:60,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
//                {field:'deptid',title:'品牌部门',width:70,sortable:true,rowspan:2,
//                    formatter:function(value,rowData,rowIndex){
//                        return rowData.deptname;
//                    }
//                },
                {field:'storageid',title:'仓库名称',width:90,sortable:true,rowspan:2,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.storagename;
                    }
                },
                {field:'supplierid',title:'供应商编码',width:70,sortable:true,rowspan:2},
                {field:'suppliername',title:'供应商名称',width:200,rowspan:2},
                {field:'unitname',title:'单位',width:40,rowspan:2},
//                {field:'price',title:'成本价',width:50,align:'right',rowspan:2,
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterMoney(value);
//                    }
//                },
                {title:'期初',align:'center',colspan:4},
                {title:'采购',align:'center',colspan:8},
                {title:'销售',align:'center',colspan:10},
                {title:'期末',align:'center',colspan:4}
            ],[
                {field:'existingnum',title:'数量',width:80,sortable:true,align:'right'},
                {field:'auxinitnumdetail',title:'箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return formatterMoney(rowData.existingbox,3);
                        }
                    }
                },
                {field:'taxamount',title:'含税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxamount',title:'未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'intotalnum',title:'进货数量',width:80,align:'right'
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterBigNumNoLen(value);
//                    }
                },
                {field:'intotalbox',title:'进货箱数',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return rowData.auxbuyinnumdetail;
                        }else{
                            return value;
                        }
                    }
                },
                {field:'intaxamount',title:'进货金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'innotaxamount',title:'进货未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'outtotalnum',title:'退货数量',width:80,align:'right'
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterBigNumNoLen(value);
//                    }
                },
                {field:'outtotalbox',title:'退货箱数',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return rowData.auxbuyoutnumdetail;
                        }else{
                            return value;
                        }
                    }
                },
                {field:'outtaxamount',title:'退货金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'outnotaxamount',title:'退货未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleouttotalnum',title:'出库数量',width:80,align:'right'
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterBigNumNoLen(value);
//                    }
                },
                {field:'saleouttotalbox',title:'出库箱数',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return rowData.auxsaleoutnumdetail;
                        }else{
                            return value;
                        }
                    }
                },
                {field:'saleouttaxamount',title:'出库金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleoutnotaxamount',title:'出库未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleintotalnum',title:'退货数量',width:80,align:'right'
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterBigNumNoLen(value);
//                    }
                },
                {field:'saleintotalbox',title:'退货箱数',width:80,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return rowData.auxsaleinnumdetail;
                        }else{
                            return value;
                        }
                    }
                },
                {field:'saleintaxamount',title:'退货金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleinnotaxamount',title:'退货未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costoutamount',title:'合计成本金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'costnotaxoutamount',title:'合计成本未税金额',width:100,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'endnum',title:'数量',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'auxendnumdetail',title:'箱数',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if(page_cols.indexOf("goodsid") != -1){
                            return value;
                        }else{
                            return formatterMoney(rowData.endtotalbox,3);
                        }
                    }
                },
                {field:'endamount',title:'含税金额',width:80,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'endnotaxamount',title:'未税金额',width:80,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]],
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-deliveryAllReport'
        }).datagrid("columnMoving");
        $("#report-query-goodsid").goodsWidget({
            isHiddenUsenum:true
        });
        //品牌
        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:180,
            singleSelect:false
        });
        //仓库
        $("#report-query-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_DELIVERY_INFO',
            width:150,
            singleSelect:false
        });
        //供应商
        $("#report-query-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:180,
            singleSelect:false
        });

        //回车事件
        controlQueryAndResetByKey("report-queay-deliveryAllReport","report-reload-deliveryAllReport");

        //查询
        $("#report-queay-deliveryAllReport").click(function(){
            setColumn();
            var queryJSON = $("#report-query-form-deliveryAllReport").serializeJSON();
            $("#report-datagrid-deliveryAllReport").datagrid({
                url: 'report/delivery/cusout/showAllDeliveryData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-deliveryAllReport").click(function(){
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    $(this)[0].checked = false;
                }
            });
            $("#report-query-groupcols").val("");
            $("#report-query-goodsid").goodsWidget("clear");
            $("#report-query-brandid").widget("clear");
            $("#report-query-storageid").widget("clear");
            $("#report-query-supplierid").widget('clear');
            $("#report-query-form-deliveryAllReport").form("reset");
            var queryJSON = $("#report-query-form-deliveryAllReport").serializeJSON();
            $("#report-datagrid-deliveryAllReport").datagrid('loadData',{total:0,rows:[],footer:[]});
            setColumn();
        });

        $("#report-buttons-buySaleReportPage").Excel('export',{
            queryForm: "#report-query-form-deliveryAllReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'进销存汇总统计报表',
            url:'report/delivery/cusout/expertAllDeliveryData.do'
        });
    });

    function setColumn(){
        var cols = $("#report-query-groupcols").val();
        if("" == cols){
            page_cols = "goodsid";
        }else{
            page_cols = cols;
        }
        if(cols != ""){
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','goodsid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','goodsname');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','barcode');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','brandid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','storageid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','supplierid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','suppliername');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','unitname');
//            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','price');

            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                var col = colarr[i];
                if(col=='goodsid' || col == ""){
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','goodsid');
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','goodsname');
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','barcode');
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','brandid');
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','unitname');
//                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','price');
                }else if(col=='storageid'){
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','storageid');
                }
//                else if(col=='deptid'){
//                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','deptid');
//                }
                else if(col=='brandid'){
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','brandid');
                }else if(col=='supplierid'){
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','supplierid');
                    $("#report-datagrid-deliveryAllReport").datagrid('showColumn','suppliername');
                }
            }
        }else{
            $("#report-datagrid-deliveryAllReport").datagrid('showColumn','goodsid');
            $("#report-datagrid-deliveryAllReport").datagrid('showColumn','goodsname');
            $("#report-datagrid-deliveryAllReport").datagrid('showColumn','barcode');
            $("#report-datagrid-deliveryAllReport").datagrid('showColumn','brandid');
//            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','deptid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','storageid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','supplierid');
            $("#report-datagrid-deliveryAllReport").datagrid('hideColumn','suppliername');
            $("#report-datagrid-deliveryAllReport").datagrid('showColumn','unitname');
//            $("#report-datagrid-deliveryAllReport").datagrid('showColumn','price');
        }
    }

</script>
</body>
</html>
