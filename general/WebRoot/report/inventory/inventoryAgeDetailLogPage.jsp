<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>库龄报表</title>
  </head>
  <body>
    	<table id="report-datagrid-inventoryAageDetailLog"></table>
    	<script type="text/javascript">
    		$(function(){
    			$("#report-datagrid-inventoryAageDetailLog").datagrid({
					columns:[[
                        {field:'businessdate',title:'日期',width:80},
                        {field:'billid',title:'单据编号',width:130},
                        {field:'detailid',title:'单据明细编号',sortable:true,width:80},
                        {field:'unitnum',title:'原数量',align:'right',width:60,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'inum',title:'计算数量',align:'right',width:60,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterMoney(value);
                            }
                        },
                        {field:'days',title:'库存天数',align:'right',width:80,sortable:true,
                            formatter:function(value,rowData,rowIndex){
                                return formatterBigNum(value);
                            }
                        },
                        {field:'bustime',title:'入库时间',align:'right',width:130,sortable:true,
                            formatter:function(val,rowData,rowIndex){
                                if(val){
                                    return val.replace(/[tT]/," ");
                                }
                            }
                        }
                    ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:true,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
                    url: 'report/inventory/showInventoryAgeDetailLogData.do',
                    queryParams:{businessdate:"${businessdate}",storageid:"${storageid}",goodsid:"${goodsid}"}
				});
    		});
    	</script>
  </body>
</html>
