<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>厂商毛利率统计表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-supplierGross"></table>
<div id="report-toolbar-supplierGross" style="padding: 0px">
    <div class="buttonBG" style="height: 28px;" >
        <security:authorize url="/report/finance/supplierGrossExport.do">
            <a href="javaScript:void(0);" id="report-buttons-supplierGross" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-supplierGross" method="post">
        <table>
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="begindate" id="report-query-businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="enddate" id="report-query-businessdate2"  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>商&nbsp;&nbsp;品:</td>
                <td><input type="text" id="report-query-goodsid" name="goodsid"/></td>
                <td>品&nbsp;&nbsp;牌:</td>
                <td><input type="text" id="report-query-brandid" name="brandid"/></td>
            </tr>
            <tr>
                <td>供应商:</td>
                <td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
                <td>小计列：</td>
                <td >
                    <div style="margin-top:2px">
                        <div style="line-height: 25px;margin-top: 2px;">
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                            <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                            <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                        </div>
                    </div>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-supplierGross" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-supplierGross" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="report-dialog-supplierGrossFlowDetail"></div>
<script type="text/javascript">
    var initQueryJSON = $("#report-query-form-supplierGross").serializeJSON();
    $(function(){
        $("#report-datagrid-supplierGross").datagrid({
            columns:[[
                {field:'supplierid',title:'供应商编号',width:80},
                {field:'suppliername',title:'供应商名称',width:130},
                {field:'goodsid',title:'商品编号',width:80},
                {field:'goodsname',title:'商品名称',width:130},
                {field:'brandid',title:'品牌编号',width:80},
                {field:'brandname',title:'品牌名称',width:130},

                {field:'beginnum',title:'期初数量',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'beginnotaxamount',title:'期初未税金额',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'beginamount',title:'期初金额',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },

                {field:'enternum',title:'进货数量',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'enternotaxamount',title:'进货未税金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'enteramount',title:'进货金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },

                {field:'outnum',title:'销售数量',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'outnotaxamount',title:'销售未税金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },

                {field:'outamount',title:'销售金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },

                {field:'endnum',title:'期末数量',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'endnotaxamount',title:'期末未税金额',align:'right',resizable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },

                {field:'endamount',title:'期末金额',align:'right',resizable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },


                {field:'costamount',title:'销售成本',align:'right',resizable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'grossamount',title:'毛利',align:'right',resizable:true,isShow:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'grossrate',title:'毛利率',align:'right',resizable:true,isShow:true,
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
            singleSelect:true,
            pageSize:100,
            toolbar:'#report-toolbar-supplierGross'

        }).datagrid("columnMoving");


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
        
        $("#report-query-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:225,
            singleSelect:true
        });
        //商品
        $("#report-query-goodsid").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:225,
            onlyLeafCheck:true
        });
        //品牌
        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });
        //回车事件
        controlQueryAndResetByKey("report-queay-supplierGross","report-reload-supplierGross");

        //查询
        $("#report-queay-supplierGross").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-supplierGross").serializeJSON();
            $("#report-datagrid-supplierGross").datagrid({
                url: 'report/sales/showSalesSuppliserGrossReportData.do',
                queryParams:queryJSON,
                pageNumber:1
            });
            setColumn();
        });
        //重置
        $("#report-reload-supplierGross").click(function(){
            $(".groupcols").each(function(){
                if($(this).attr("checked")){
                    $(this)[0].checked = false;
                }
            });
            $("#report-query-supplierid").widget("clear");
            $("#report-query-brandid").widget("clear");
            $("#report-query-goodsid").widget("clear");
            $("#report-query-form-supplierGross")[0].reset();
            var queryJSON = $("#report-query-form-supplierGross").serializeJSON();
            $("#report-datagrid-supplierGross").datagrid('loadData',{total:0,rows:[]});
            setColumn();
        });

        $("#report-buttons-supplierGross").click(function(){
            //封装查询条件
            var objecr  = $("#report-datagrid-supplierGross").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "report/sales/exportSupplierGrossData.do?exporttype=base";
            exportByAnalyse(queryParam,"厂商毛利率统计表","report-datagrid-supplierGross",url);
        });


        function setColumn(){
            var cols = $("#report-query-groupcols").val();
            if(cols!=""){
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "supplierid");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "suppliername");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "goodsid");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "goodsname");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "brandid");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "brandname");
            }
            else{
                $("#report-datagrid-supplierGross").datagrid('showColumn', "supplierid");
                $("#report-datagrid-supplierGross").datagrid('showColumn', "suppliername");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "goodsid");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "goodsname");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "brandid");
                $("#report-datagrid-supplierGross").datagrid('hideColumn', "brandname");
            }
            var colarr = cols.split(",");
            for(var i=0;i<colarr.length;i++){
                var col = colarr[i];
                if(col=='supplierid'){
                    $("#report-datagrid-supplierGross").datagrid('showColumn', "supplierid");
                    $("#report-datagrid-supplierGross").datagrid('showColumn', "suppliername");
                }else if(col=="goodsid"){
                    $("#report-datagrid-supplierGross").datagrid('showColumn', "goodsid");
                    $("#report-datagrid-supplierGross").datagrid('showColumn', "goodsname");
                }else if(col=="brandid"){
                    $("#report-datagrid-supplierGross").datagrid('showColumn', "brandid");
                    $("#report-datagrid-supplierGross").datagrid('showColumn', "brandname");
                }
            }
        }

        function baseSales_retColsname(){
            var colname = "";
            var cols = $("#report-query-groupcols").val();
            if(cols == ""){
                cols = "customerid";
            }
            var colarr = cols.split(",");
            if(colarr[0]=="pcustomerid"){
                colname = "pcustomername";
            }else if(colarr[0]=='customerid'){
                colname = "customername";
            }else if(colarr[0]=="salesuser"){
                colname = "salesusername";
            }else if(colarr[0]=="salesarea"){
                colname = "salesareaname";
            }else if(colarr[0]=="salesdept"){
                colname = "salesdeptname";
            }else if(colarr[0]=="goodsid"){
                colname = "goodsname";
            }else if(colarr[0]=="goodssort"){
                colname = "goodssortname";
            }else if(colarr[0]=="brandid"){
                colname = "brandname";
            }else if(colarr[0]=="branduser"){
                colname = "brandusername";
            }else if(colarr[0]=="branddept"){
                colname = "branddeptname";
            }else if(colarr[0]=="customersort"){
                colname = "customersortname";
            }else if(colarr[0]=="supplierid"){
                colname = "suppliername";
            }else if(colarr[0]=="supplieruser"){
                colname = "supplierusername";
            }else if(colarr[0]=="storageid"){
                colname = "storagename";
            }
            return colname;
        }
    });
</script>
</body>
</html>
