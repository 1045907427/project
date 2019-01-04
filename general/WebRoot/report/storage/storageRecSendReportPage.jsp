<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>仓库收发存报表</title>
    <%@include file="/include.jsp" %>
</head>

<body>
	<table id="report-datagrid-storageRecSendReport"></table>
	<div id="report-toolbar-storageRecSendReport" style="padding: 0px">
   		<div class="buttonBG">
	        <security:authorize url="/report/storage/storageRecSendReportExport.do">
	            <a href="javaScript:void(0);" id="report-buttons-storageRecSendReportPage" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">全局导出</a>
	        </security:authorize>
	    </div>
   		<form action="" id="report-query-form-storageRecSendReport" method="post">
   		<table>
   			<tr>
   				<td>日期:</td>
   				<td><input type="text" id="report-query-businessdate1"  name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2"  name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
   				<td>仓库:</td>
   				<td><input type="text" id="report-query-storageid" name="storageid" /></td>
   				<td>是否出入库:</td>
   				<td>
   					<select name="isenterorout" style="width: 150px;">
   						<option></option>
   						<option value="1">是</option>
   						<option value="0">否</option>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td>商品名称:</td>
   				<td><input type="text" id="report-query-goodsid" name="goodsid" /></td>
   				<td>品牌名称:</td>
   				<td><input type="text" id="report-query-brandid" name="brandid" style="width: 150px;"/></td>
   				<td>商品分类:</td>
   				<td><input type="text" id="report-query-goodssort" name="goodssort" style="width: 150px;"/></td>
   			</tr>
   			<tr>
   				<td>供应商:</td>
   				<td>
   					<input type="text" id="report-query-supplierid" name="supplierid" style="width: 225px;"/>
   				</td>
   				<td colspan="2"></td>
   				<td colspan="2">
   					<a href="javaScript:void(0);" id="report-queay-storageRecSendReport" class="button-qr">查询</a>
					<a href="javaScript:void(0);" id="report-reload-storageRecSendReport" class="button-qr">重置</a>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
   	<div id="report-dialog-storageRecSendReport"></div>
   	<div id="report-dialog-receivenumDetailPage"></div>
   	<div id="report-dialog-sendnumDetailPage"></div>
<script type="text/javascript">
var initQueryJSON = $("#report-query-form-storageRecSendReport").serializeJSON();
var dataListJson = $("#report-datagrid-storageRecSendReport").createGridColumnLoad({
	frozenCol : [[
				{field:'storageid',title:'仓库名称',width:80,
				 	formatter:function(value,rowData,rowIndex){
		        		return rowData.storagename;
		        	}
				  },
				  {field:'goodsid',title:'商品编码',width:70}
    			]],
	commonCol : [[
				 {field:'goodsname',title:'商品名称',width:130,aliascol:'goodsid',sortable:true,},
				 {field:'barcode',title:'条形码',sortable:true,width:90,isShow:true},
				 {field:'brandid',title:'品牌名称',width:80,aliascol:'goodsid',sortable:true,
					 formatter:function(value,rowData,rowIndex){
			        		return rowData.brandname;
			        	}
				  },
				  {field:'goodssort',title:'商品分类',width:80,aliascol:'goodsid',sortable:true,
					  formatter:function(value,rowData,rowIndex){
			        		return rowData.goodssortname;
			        	}
				  },
				  {field:'boxnum',title:'箱装量',width:40,aliascol:'goodsid',align:'right',
					  formatter:function(value,rowData,rowIndex){
						  return formatterBigNumNoLen(value);
			          }
				  },
				  {field:'unitname',title:'单位',width:40},
				  {field:'beginnum',title:'期初数量',width:80,sortable:true,align:'right',
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterBigNumNoLen(value);
		        	}
				  },
				  {field:'auxbeginnum',title:'期初箱数',width:80,sortable:true,align:'right',hidden:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterBigNumNoLen(value);
		        	}
				  },
				  {field:'beginnumdetail',title:'期初辅数量',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return value;
						  }else{
							  return formatterBigNumNoLen(rowData.auxbeginnum);
						  }
			          }
				  },
				  {field:'beginamount',title:'期初金额',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return formatterMoney(value);
						  }else{
							  return formatterMoney(rowData.beginamount);
						  }
			          }
				  },
				  {field:'receivenum',title:'入库数量',width:80,align:'right',sortable:true,
				    formatter:function(value,rowData,rowIndex){
		        		if(value != 0 && rowData.goodsid!=null && rowData.goodsid!=""){
	        				return '<a href="javascript:showReceivenumDetailPage(\''+rowData.goodsid+'\',\''+rowData.storageid+'\')">'+formatterBigNumNoLen(value)+'</a>';
	        			}else{
	        				return formatterBigNumNoLen(value);
	        			}
		        	}
				  },
				  {field:'auxreceivenum',title:'入库箱数',width:80,sortable:true,align:'right',hidden:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterBigNumNoLen(value);
		        	}
				  },
				  {field:'receivenumdetail',title:'入库辅数量',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return value;
						  }else{
							  return formatterBigNumNoLen(rowData.auxreceivenum);
						  }
			          }  
				  },
				  {field:'receiveamount',title:'入库金额',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return formatterMoney(value);
						  }else{
							  return formatterMoney(rowData.receiveamount);
						  }
			          }
				  },
				  {field:'sendnum',title:'出库数量',width:80,align:'right',sortable:true,
					  formatter:function(value,rowData,rowIndex){
			        		if(value != 0 && rowData.goodsid!=null && rowData.goodsid!=""){
		        				return '<a href="javascript:showSendnumDetailPage(\''+rowData.goodsid+'\',\''+rowData.storageid+'\')">'+formatterBigNumNoLen(value)+'</a>';
		        			}else{
		        				return formatterBigNumNoLen(value);
		        			}
			        	}
				  },
				  {field:'auxsendnum',title:'出库箱数',width:80,sortable:true,align:'right',hidden:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterBigNumNoLen(value);
		        	}
				  },
				  {field:'sendnumdetail',title:'出库辅数量',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return value;
						  }else{
							  return formatterBigNumNoLen(rowData.auxsendnum);
						  }
			          }  
				  },
				  {field:'sendamount',title:'出库金额',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return formatterMoney(value);
						  }else{
							  return formatterMoney(rowData.sendamount);
						  }
			          }
				  },
				  {field:'endnum',title:'期末数量',width:90,align:'right',sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterBigNumNoLen(value);
		        	}
				  },
				  {field:'auxendnum',title:'期末箱数',width:80,sortable:true,align:'right',hidden:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterBigNumNoLen(value);
		        	}
				  },
				  {field:'endnumdetail',title:'期末辅数量',width:95,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return value;
						  }else{
							  return formatterBigNumNoLen(rowData.auxendnum);
						  }
			          }   
				  },
				  {field:'endamount',title:'期末金额',width:90,sortable:true,align:'right',
					  formatter:function(value,rowData,rowIndex){
						  if(value!=""){
							  return formatterMoney(value);
						  }else{
							  return formatterMoney(rowData.endamount);
						  }
			          }
				   },
	             ]]
		});
$(function(){
	$("#report-datagrid-storageRecSendReport").datagrid({ 
		   authority:dataListJson,
			frozenColumns: dataListJson.frozen,
			columns:dataListJson.common,
 			method:'post',
	 		title:'',
	 		fit:true,
	 		rownumbers:true,
	 		pagination:true,
	 		showFooter: true,
	 		pageSize:100,
	 		singleSelect:true,
		    toolbar:'#report-toolbar-storageRecSendReport',
		    onDblClickRow:function(rowIndex, rowData){
					var goodsid = rowData.goodsid;
					var storageid = rowData.storageid;
					var businessdate1 = $("#report-query-businessdate1").val();
					var businessdate2 = $("#report-query-businessdate2").val();
					var url = 'report/storage/showStorageRecSendReportDetailPage.do?goodsid='+goodsid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&storageid='+storageid;
					$("#report-dialog-storageRecSendReport").dialog({
						title:rowData.storagename+':'+rowData.goodsname+'的收发存明细',
			    		width:800,
			    		height:400,
			    		closed:true,
			    		modal:true,
			    		maximizable:true,
			    		cache:false,
			    		resizable:true,
			    		maximized:true,
					    href: url
					});
					$("#report-dialog-storageRecSendReport").dialog("open");
			 }
	}).datagrid("columnMoving");
	$("#report-query-supplierid").supplierWidget({
		width:225,
	});
	$("#report-query-goodsid").widget({
		referwid:'RL_T_BASE_GOODS_INFO',
		width:225,
		singleSelect:false
	});
	$("#report-query-brandid").widget({
		referwid:'RL_T_BASE_GOODS_BRAND',
		width:150,
		singleSelect:false
	});
	$("#report-query-storageid").widget({
		referwid:'RL_T_BASE_STORAGE_INFO',
		width:150,
		singleSelect:true
	});
	$("#report-query-goodssort").widget({
		referwid:'RL_T_BASE_GOODS_WARESCLASS',
		width:150,
		singleSelect:true,
		onlyLeafCheck:false
	});
	$("#report-query-deptid").widget({
		referwid:'RL_T_BASE_DEPARTMENT_SELLER',
		width:150,
		singleSelect:true,
		onlyLeafCheck:false
	});
	//查询
	$("#report-queay-storageRecSendReport").click(function(){
		//把form表单的name序列化成JSON对象
  		var queryJSON = $("#report-query-form-storageRecSendReport").serializeJSON();
  		$("#report-datagrid-storageRecSendReport").datagrid({
  			url: 'report/storage/showStorageRecSendList.do',
  			pageNumber:1,
			queryParams:queryJSON
  		});
	});
	//重置
	$("#report-reload-storageRecSendReport").click(function(){
		$("#report-query-goodsid").widget("clear");
		$("#report-query-storageid").widget("clear");
		$("#report-query-brandid").widget("clear");
		$("#report-query-supplierid").supplierWidget("clear");
		$("#report-query-goodssort").widget("clear");
		
		$("#report-query-form-storageRecSendReport").form("reset");
		var queryJSON = $("#report-query-form-storageRecSendReport").serializeJSON();
   		$("#report-datagrid-storageRecSendReport").datagrid('loadData',{total:0,rows:[]});
	});
    //导出
    $("#report-buttons-storageRecSendReportPage").click(function(){
        //封装查询条件
        var objecr  = $("#report-datagrid-storageRecSendReport").datagrid("options");
        var queryParam = objecr.queryParams;
        if(null != objecr.sortName && null != objecr.sortOrder ){
            queryParam["sort"] = objecr.sortName;
            queryParam["order"] = objecr.sortOrder;
        }
        var queryParam = JSON.stringify(queryParam);
        var url = "report/storage/exportStorageRecSendReportData.do";
        exportByAnalyse(queryParam,"仓库收发存报表","report-datagrid-storageRecSendReport",url);
    });
});

function showReceivenumDetailPage(goodsid,storageid){
	var businessdate1 = $("#report-query-businessdate1").val();
	var businessdate2 = $("#report-query-businessdate2").val();
    url = 'report/storage/showReceivenumDetailPage.do?goodsid='+goodsid+"&storageid="+storageid+"&businessdate1="+businessdate1+"&businessdate2="+businessdate2;
    $("#report-dialog-receivenumDetailPage").dialog({
    	title: '仓库:'+storageid+'中的商品:'+goodsid+'到货明细列表', 
	    width: 680,  
	    height: 400,  
	    collapsible:false,
	    minimizable:false,
	    maximizable:true,
	    resizable:true,
	    closed: false,  
	    cache: false,  
	    href: url,
	    modal: true
	});
}

function showSendnumDetailPage(goodsid,storageid){
	var businessdate1 = $("#report-query-businessdate1").val();
	var businessdate2 = $("#report-query-businessdate2").val();
    url = 'report/storage/showSendnumDetailPage.do?goodsid='+goodsid+"&storageid="+storageid+"&businessdate1="+businessdate1+"&businessdate2="+businessdate2;
    $("#report-dialog-sendnumDetailPage").dialog({
    	title: '仓库:'+storageid+'中的商品:'+goodsid+'发货明细列表', 
	    width: 680,  
	    height: 400,  
	    collapsible:false,
	    minimizable:false,
	    maximizable:true,
	    resizable:true,
	    closed: false,  
	    cache: false,  
	    href: url,
	    modal: true
	});
}

function showPurchaseEnterView(id){
	top.addOrUpdateTab('storage/showPurchaseEnterEditPage.do?id='+ id, "采购入库单查看");
	}
function showSaleoutView(id){
    top.addOrUpdateTab('storage/showSaleOutViewPage.do?id='+ id, "发货单查看");
	}
function showStorageOtherOutView(id){
    op.addOrUpdateTab('storage/showStorageOtherOutEditPage.do?id='+id, "其他出库单查看");
	}
function showSaleRejectEnterView(id){
    top.addOrUpdateTab('storage/showSaleRejectEnterEditPage.do?id='+ id, "销售退货入库单查看");
	}
function showStorageOtherEnterView(id){
    top.addOrUpdateTab('storage/showStorageOtherEnterEditPage.do?id='+ id, "其他入库单查看");
	}
function showAllocateOutView(id){
    top.addOrUpdateTab('storage/showAllocateOutEditPage.do?id='+ id, "调拨单查看");
	}
function showPurchaseRejectOutView(id){
    top.addOrUpdateTab('storage/showPurchaseRejectOutEditPage.do?id='+ id, "采购退货出库单查看");
	}
function showAdjustmentsView(id){
    top.addOrUpdateTab('storage/showAdjustmentsEditPage.do?id='+ id, "报溢调账单查看");
	}
function showStorageDeliveryEnterView(id){
	var type=null;
	$.ajax({   
        url :'storage/distrtibution/getDistributeRejectType.do',
        type:'post',
        data:{id:id},
        dataType:'json',
        async:false,
        success:function(json){
        	type=json.type;
        	console.log(type)
        }
    });
    top.addTab('storage/distrtibution/showDistributeRejectPage.do?billtype='+type+'&type=view&id='+ id, "代配送入库单查看");
	}
function showStorageDeliveryOutView(id){
	var type=null;
	$.ajax({   
        url :'storage/deliveryout/getDeliveryOutType.do',
        type:'post',
        data:{id:id},
        dataType:'json',
        async:false,
        success:function(json){
        	type=json.type;
        }
    });
    top.addTab('storage/deliveryout/showDeliveryOutPage.do?billtype='+type+'&type=view&id='+ id, "代配送出库单查看");
	}
</script>
</body>
</html>
