<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>按厂家业务员分品牌统计页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
    <form action="" id="report-query-form-supplieruserWithdrawnDetail" method="post">
        <input type="hidden" name="supplieruser" value="${supplieruser}"/>
        <input type="hidden" name="businessdate1" value="${businessdate1}"/>
        <input type="hidden" name="businessdate2" value="${businessdate2}"/>
    </form>
    <div id="report-tab-supplieruserWithdrawnDetail" class="buttonBG">
        <security:authorize url="/report/finance/supplieruserWithdrawnListExport.do">
            <a href="javaScript:void(0);" id="report-export-supplieruserWithdrawnDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <div id="tt" class="easyui-tabs" data-options="fit:true">
        <div title="分品牌统计" style="padding:2px;">
            <table id="report-datagrid-supplieruserWithdrawnDetail-brand"></table>
        </div>
        <div title="分客户统计" style="padding:2px;">
            <table id="report-datagrid-supplieruserWithdrawnDetail-customer"></table>
        </div>
        <div title="分客户分品牌统计" style="padding:2px;">
            <table id="report-datagrid-supplieruserWithdrawnDetail-brandAndCustomer"></table>
        </div>
    </div>
    <script type="text/javascript">
        $('#tt').tabs({
            tools:'#report-tab-supplieruserWithdrawnDetail'
        });
        var initQueryJSON = $("#report-query-form-supplieruserWithdrawnDetail").serializeJSON();
        $(function(){
            var brandTableColumnListJson = $("#report-datagrid-supplieruserWithdrawnDetail-brand").createGridColumnLoad({
                frozenCol : [[]],
                commonCol : [[
                    {field:'brandid',title:'商品品牌',width:80,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.brandname;
                        }
                    },
                    {field:'withdrawnamount',title:'回笼金额',align:'right',width:80,isShow:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                    ,
                    {field:'costwriteoffamount',title:'回笼成本',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    </c:if>
                    <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                    ,
                    {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    </c:if>
                    <c:if test="${map.writeoffrate == 'writeoffrate'}">
                    ,
                    {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            if(null != value && "" != value){
                                return formatterMoney(value)+"%";
                            }
                        }
                    }
                    </c:if>
                ]]
            });
            var customerTableColumnListJson = $("#report-datagrid-supplieruserWithdrawnDetail-customer").createGridColumnLoad({
                frozenCol : [[]],
                commonCol : [[
                    {field:'customerid',title:'客户编码',width:60},
                    {field:'customername',title:'客户名称',width:210},
                    {field:'pcustomerid',title:'总店名称',width:60,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.customerid!=rowData.pcustomerid){
                                return rowData.pcustomername;
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'salesarea',title:'销售区域',sortable:true,width:60,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.salesareaname;
                        }
                    },
                    {field:'withdrawnamount',title:'回笼金额',align:'right',width:80,isShow:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                    ,
                    {field:'costwriteoffamount',title:'回笼成本',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    </c:if>
                    <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                    ,
                    {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    </c:if>
                    <c:if test="${map.writeoffrate == 'writeoffrate'}">
                    ,
                    {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            if(null != value && "" != value){
                                return formatterMoney(value)+"%";
                            }
                        }
                    }
                    </c:if>
                ]]
            });
            var brandcustomerTableColumnListJson = $("#report-datagrid-supplieruserWithdrawnDetail-brandAndCustomer").createGridColumnLoad({
                frozenCol : [[]],
                commonCol : [[
                    {field:'brandid',title:'商品品牌',width:80,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.brandname;
                        }
                    },
                    {field:'customerid',title:'客户编码',width:60},
                    {field:'customername',title:'客户名称',width:210},
                    {field:'pcustomerid',title:'总店名称',width:60,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            if(rowData.customerid!=rowData.pcustomerid){
                                return rowData.pcustomername;
                            }else{
                                return "";
                            }
                        }
                    },
                    {field:'salesarea',title:'销售区域',sortable:true,width:60,
                        formatter:function(value,rowData,rowIndex){
                            return rowData.salesareaname;
                        }
                    },
                    {field:'withdrawnamount',title:'回笼金额',align:'right',width:80,isShow:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
                    ,
                    {field:'costwriteoffamount',title:'回笼成本',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    </c:if>
                    <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
                    ,
                    {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            return formatterMoney(value);
                        }
                    }
                    </c:if>
                    <c:if test="${map.writeoffrate == 'writeoffrate'}">
                    ,
                    {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,sortable:true,
                        formatter:function(value,rowData,rowIndex){
                            if(null != value && "" != value){
                                return formatterMoney(value)+"%";
                            }
                        }
                    }
                    </c:if>
                ]]
            });

            $("#report-datagrid-supplieruserWithdrawnDetail-brand").datagrid({
                authority:brandTableColumnListJson,
                frozenColumns: brandTableColumnListJson.frozen,
                columns:brandTableColumnListJson.common,
                method:'post',
                fit:true,
                rownumbers:true,
                pagination:true,
                showFooter: true,
                singleSelect:true,
                pageSize:100,
                //toolbar:'#report-tab-salesSupplieruserDetail',
                url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
                queryParams:initQueryJSON
            }).datagrid("columnMoving");
            $("#report-datagrid-supplieruserWithdrawnDetail-customer").datagrid({
                authority:customerTableColumnListJson,
                frozenColumns: customerTableColumnListJson.frozen,
                columns:customerTableColumnListJson.common,
                method:'post',
                title:'',
                fit:true,
                rownumbers:true,
                pagination:true,
                showFooter: true,
                pageSize:100,
                singleSelect:true,
                url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid',
                queryParams:initQueryJSON
            }).datagrid("columnMoving");
            $("#report-datagrid-supplieruserWithdrawnDetail-brandAndCustomer").datagrid({
                authority:brandcustomerTableColumnListJson,
                frozenColumns: brandcustomerTableColumnListJson.frozen,
                columns:brandcustomerTableColumnListJson.common,
                method:'post',
                title:'',
                fit:true,
                rownumbers:true,
                pagination:true,
                showFooter: true,
                pageSize:100,
                singleSelect:true,
                url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid,brandid',
                queryParams:initQueryJSON
            }).datagrid("columnMoving");

            $("#report-export-supplieruserWithdrawnDetail").Excel('export',{
                queryForm: "#report-query-form-supplieruserWithdrawnDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type:'exportUserdefined',
                name:'按厂家业务员：[${supplierusername}]统计表',
                url:'report/finance/exportFinanceWithdrawnDetailReportData.do?groupcols=brandid;customerid;customerid,brandid'
            });
        });
    </script>
</body>
</html>
