<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购订单追踪明细表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
    	<table id="report-datagrid-buyOrderTrack"></table>
    	<div id="report-toolbar-buyOrderTrack" style="padding: 0px">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="report-buttons-buyOrderTrackPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </div>
    		<form action="" id="report-query-form-buyOrderTrack" method="post">
    		<table  class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>订单编号:</td>
    				<td><input name="id" style="width: 150px;"/></td>
                    <td>品牌名称:</td>
                    <td><input type="text" id="report-query-brandid" name="brandid"/></td>
    			</tr>
    			<tr>
    				<td>进货日期:</td>
    				<td><input type="text" name="purchasedate1"  style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="purchasedate2"  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>供应商:</td>
    				<td><input type="text" id="report-query-supplier" name="supplierid"/></td>
                    <td>商品名称:</td>
                    <td><input type="text" id="report-query-goodsid" name="goodsid"/></td>
    			</tr>
    			<tr>
    				<td>数量差异：</td>
                    <td class="tdinput">
                        <select name="numdifference" style="width:150px;">
                      	    <option value="0" selected="selected"></option>
                            <option value="1" >无差异</option>
                            <option value="2">有差异</option>
                        </select>
                    </td>
    				<td colspan="2" ></td>
                    <td colspan="2" >
                        <a href="javaScript:void(0);" id="report-queay-buyOrderTrack" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-buyOrderTrack" class="button-qr">重置</a>
                    </td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-buyOrderTrack").serializeJSON();
    		$(function(){
    			var tableColumnListBrandJson = $("#report-datagrid-buyOrderTrack").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'businessdate',title:'业务日期',width:80,sortable:true},
								  {field:'orderid',title:'单据编号',width:130,sortable:true,
									  formatter:function(value,rowData,rowIndex){
										  if(null!=value){
											  return "<a href='javascript:showOrderView(\""+value+"\");' >"+value+"</a>";
										  }
							        	
							          }
								  },
								  {field:'supplierid',title:'供应商编号',width:80,sortable:true},
								  {field:'suppliername',title:'供应商名称',width:150},
								  {field:'goodsid',title:'商品编码',width:60,sortable:true},
								  {field:'goodsname',title:'商品名称',width:200,
									  formatter:function(value,rowData,rowIndex){
						        		if(rowData.deliverytype=='1'){
						       				return "<font color='blue'>&nbsp;赠 </font>"+value;
						       			}else{
						       				return value;
						       			} 
						        	}
								  },
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'brandname',title:'品牌名称',width:60},
								  {field:'unitname',title:'单位',width:40},
								  {field:'taxprice',title:'订单单价',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
							        	return formatterMoney(value);
						        	}
								  },
								  {field:'ordernum',title:'订单数量',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'ordertotalbox',title:'订单箱数',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'orderamount',title:'订单金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'enterprice',title:'入库单价',width:60,sortable:true,align:'right',
									  formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
							          }	  
								  },
								  {field:'enternum',title:'入库数量',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'entertotalbox',title:'入库箱数',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'enteramount',title:'入库金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
						        	}
								  },
								  {field:'arrivalorderprice',title:'进货单价',width:85,sortable:true,align:'right',
									  formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
							          }	  
								  },
								  {field:'arrivalordernum',title:'进货数量',width:80,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'arrivalordertotalbox',title:'进货箱数',width:80,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'arrivalorderamount',title:'进货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								 
								 
					         ]]
				});
    			$("#report-datagrid-buyOrderTrack").datagrid({ 
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
		  	 		sortName:'businessdate,orderid',
		  	 		sortOrder:'asc',
		  	 		pageSize:100,
					toolbar:'#report-toolbar-buyOrderTrack',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
						}
			 		},
					onCheckAll:function(){
						countTotalAmount();
					},
					onUncheckAll:function(){
						countTotalAmount();
					},
					onCheck:function(){
						countTotalAmount();
					},
					onUncheck:function(){
						countTotalAmount();
					}
				}).datagrid("columnMoving");
    			
				$("#report-query-goodsid").goodsWidget({});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:150,
					singleSelect:false
				});

				$("#report-query-supplier").supplierWidget({ 
					name:'t_purchase_buyorder',
					col:'supplierid',
					width:130,
					required:true,
					required:true,
					singleSelect:true,
					onlyLeafCheck:true,
				});	
				//回车事件
				controlQueryAndResetByKey("report-queay-buyOrderTrack","report-reload-buyOrderTrack");
				
				//查询
				$("#report-queay-buyOrderTrack").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-buyOrderTrack").serializeJSON();
		      		$("#report-datagrid-buyOrderTrack").datagrid({
		      			url:'report/buy/showBuyOrderTrackReportData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-buyOrderTrack").click(function(){
					$("#report-query-supplier").supplierWidget("clear");
					$("#report-query-goodsid").goodsWidget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-buyOrderTrack")[0].reset();
		       		$("#report-datagrid-buyOrderTrack").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-buyOrderTrackPage").Excel('export',{
					queryForm: "#report-query-form-buyOrderTrack", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'订单追踪明细表',
			 		url:'report/buy/exportBuyOrderTrackReportData.do'
				});
				
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-buyOrderTrack").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-buyOrderTrack").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var ordernum = 0;
    			var ordertotalbox = 0;
        		var orderamount = 0;
        		var enternum = 0;
        		var entertotalbox = 0;
        		var enteramount = 0;
        		var enternotaxamount=0;
        		var arrivalordernum = 0;
        		var arrivalordertotalbox = 0;
        		var arrivalorderamount = 0;
        		for(var i=0;i<rows.length;i++){
        			ordernum = Number(ordernum)+Number(rows[i].ordernum == undefined ? 0 : rows[i].ordernum);
        			ordertotalbox = Number(ordertotalbox)+Number(rows[i].ordertotalbox == undefined ? 0 : rows[i].ordertotalbox);
        			orderamount = Number(orderamount)+Number(rows[i].orderamount == undefined ? 0 : rows[i].orderamount);
        			enternum = Number(enternum)+Number(rows[i].enternum == undefined ? 0 : rows[i].enternum);
        			entertotalbox = Number(entertotalbox)+Number(rows[i].entertotalbox == undefined ? 0 : rows[i].entertotalbox);
        			enteramount = Number(enteramount)+Number(rows[i].enteramount == undefined ? 0 : rows[i].enteramount);
        			arrivalordernum = Number(arrivalordernum)+Number(rows[i].arrivalordernum == undefined ? 0 : rows[i].arrivalordernum);
        			arrivalordertotalbox = Number(arrivalordertotalbox)+Number(rows[i].arrivalordertotalbox == undefined ? 0 : rows[i].arrivalordertotalbox);
        			arrivalorderamount = Number(arrivalorderamount)+Number(rows[i].arrivalorderamount == undefined ? 0 : rows[i].arrivalorderamount);
        		}
        		var foot=[{businessdate:'选中金额',ordernum:ordernum,ordertotalbox:ordertotalbox,orderamount:orderamount,
        					enternum:enternum,entertotalbox:entertotalbox,enteramount:enteramount,
        					arrivalordernum:arrivalordernum,arrivalordertotalbox:arrivalordertotalbox,arrivalorderamount:arrivalorderamount,
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-buyOrderTrack").datagrid("reloadFooter",foot);
    		}
    		function showOrderView(id){
    			top.addOrUpdateTab('purchase/buyorder/buyOrderPage.do?type=edit&id='+ id, "采购订单查看");
        	}
    	</script>
  </body>
</html>
