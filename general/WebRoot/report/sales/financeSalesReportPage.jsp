<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>财务销售情况基础统计报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-baseSalesReport"></table>
    	<div id="report-toolbar-baseSalesReport" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-baseSalesReportPage" class="easyui-linkbutton" iconCls="icon-search" title="[Alt+Q]查询">查询</a>
    		<security:authorize url="/report/sales/baseFinanceReportExport.do">
				<a href="javaScript:void(0);" id="report-export-baseSalesReportPage" class="easyui-linkbutton" iconCls="button-export" title="导出">导出</a>
			</security:authorize>
    	</div>
    	<div id="report-dialog-selectmoreparam"></div>
    	<div id="report-div-selectmoreparam">
    		<form action="" method="post" id="report-form-selectmoreparam">
    			<table cellpadding="0" cellspacing="0" border="0">
    				<tr>
    					<td>客户:</td>
    					<td><input type="text" name="id" id="report-customer-selectmoreparamlist"/></td>
    					<td>
    						<a href="javaScript:void(0);" id="report-query-selectmoreparam" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
							<a href="javaScript:void(0);" id="report-reload-selectmoreparam" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
    					</td>
    				</tr>
    			</table>
		  	</form>
    	</div>
    	<div style="display:none">
    	<div id="report-dialog-advancedQueryDialog" >
    		<form action="" id="report-query-form-baseSalesReport" method="post">
    		<table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    			</tr>
    			<tr>
	  				<td>客户:</td>
	  				<td><input type="text" name="customerid" id="report-customernamemore-advancedQuery" style="width: 210px;"/>
	  				</td>
	  			</tr>
	  			<tr>
    				<td>总店:</td>
	  				<td><input type="text" name="pcustomerid" id="report-pcustomernamemore-advancedQuery" style="width: 210px;"/></td>
    			</tr>
    			<tr>
	  				<td>品牌:</td>
		  			<td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
		  		</tr>
		  		<tr>
		  			<td>品牌部门:</td>
		  			<td><input type="text" name="branddept" id="report-branddept-advancedQuery"/></td>
		  		</tr>
		  		<tr>
		  			<td>品牌业务员:</td>
		  			<td><input type="text" name="branduser" id="report-branduser-advancedQuery"/></td>
    			</tr>
    			<tr>
    				<td>商品:</td>
	  				<td><input type="text" name="goodsid" id="report-goodsid-advancedQuery" style="width: 210px;"/></td>
	  			</tr>
	  			<tr>
    				<td>销售区域:</td>
	  				<td><input type="text" name="salesarea" id="report-salesarea-advancedQuery" style="width: 210px;"/></td>
	  			</tr>
	  			<tr>
	  				<td>客户分类:</td>
	  				<td><input type="text" name="customersort" id="report-customersort-advancedQuery" style="width: 210px;"/></td>
    			</tr>
    			<tr>
    				<td>小计列：</td>
    				<td>
    					<div style="margin-top:3px">
	    					<div style="line-height: 25px;">
		    					<input type="checkbox" class="groupcols" value="customerid"/>客户
		    					<input type="checkbox" class="groupcols" value="pcustomerid"/>总店
		    					<input type="checkbox" class="groupcols" value="customersort"/>客户分类
		    					<input type="checkbox" class="groupcols" value="salesuser"/>客户业务员
		    					<input type="checkbox" class="groupcols" value="salesarea"/>销售区域
    						</div>
		    				<div style="line-height: 25px; margin-top: 2px;">
		    					<input type="checkbox" class="groupcols" value="salesdept"/>销售部门
		    					<input type="checkbox" class="groupcols" value="goodsid"/>商品
		    					<input type="checkbox" class="groupcols" value="branddept"/>品牌部门
		    					<input type="checkbox" class="groupcols" value="branduser"/>品牌业务员
		    					<input type="checkbox" class="groupcols" value="brandid"/>品牌
		    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
	    					</div>
    					</div>
    				</td>
    			</tr>
    		</table>
    		</form>
    	</div>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-baseSalesReport").serializeJSON();
    		$(function(){
		  		
		   		//查询
				$("#report-query-selectmoreparam").click(function(){
		      		var selectmoreQueryJSON = $("#report-form-selectmoreparam").serializeJSON();
		      		$("#report-datagrid-selectmoreparam").datagrid('load',selectmoreQueryJSON);
				});
				//重置
				$("#report-reload-selectmoreparam").click(function(){
			  		$("#report-customer-selectmoreparamlist").widget('clear');
				    $("#report-form-selectmoreparam").form('clear');
					$("#report-datagrid-selectmoreparam").datagrid('reload');
				});
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
    			var tableColumnListJson = $("#report-datagrid-baseSalesReport").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:210},
								  {field:'pcustomerid',title:'总店名称',sortable:true,width:60,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.pcustomername;
						        	}
								  },
								  {field:'salesuser',title:'客户业务员',sortable:true,width:70,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesusername;
						        	}
								  },
								  {field:'customersort',title:'客户分类',sortable:true,width:60,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.customersortname;
						        	}
								  },
								  {field:'salesarea',title:'销售区域',sortable:true,width:60,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesdeptname;
						        	}
								  },
								  {field:'branddept',title:'品牌部门',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
								  },
								  {field:'branduser',title:'品牌业务员',sortable:true,width:70,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandusername;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',sortable:true,width:60},
								  {field:'goodsname',title:'商品名称',sortable:true,width:250},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'brandid',title:'品牌名称',sortable:true,width:60,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
								  },
								  {field:'unitname',title:'主单位',width:45,hidden:true},
								  {field:'auxunitname',title:'辅单位',width:45,hidden:true},
								  <c:if test="${map.costprice == 'costprice'}">
									  {field:'costprice',title:'成本价',width:60,align:'right',hidden:true,sortable:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  },
								  </c:if>
								  {field:'price',title:'单价',width:60,align:'right',hidden:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'initsendnum',title:'发货单数量',width:70,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'initsendamount',title:'发货单金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'initsendnotaxamount',title:'发货单未税金额',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'pushbalanceamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'directreturnnum',title:'直退数量',width:60,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'directreturnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'checkreturnnum',title:'退货数量',width:60,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'checkreturnamount',title:'退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'returnnum',title:'退货总数量',width:70,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'returnamount',title:'退货合计',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salenum',title:'销售数量',width:60,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'saleamount',title:'销售金额',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salenotaxamount',title:'销售无税金额',resizable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'saletax',title:'销售税额',resizable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
								  <c:if test="${map.costamount == 'costamount'}">
									  ,
									  {field:'costamount',title:'成本金额',align:'right',resizable:true,isShow:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  }
								  </c:if>
								  <c:if test="${map.salemarginamount == 'salemarginamount'}">
									  ,
									  {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'right',
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
    			$("#report-datagrid-baseSalesReport").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
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
					toolbar:'#report-toolbar-baseSalesReport',
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
				$("#report-customernamemore-advancedQuery").widget({
		  			referwid:'RL_T_BASE_SALES_CUSTOMER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true
		  		});
		  		$("#report-pcustomernamemore-advancedQuery").widget({
		  			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true
		  		});
		  		$("#report-goodsid-advancedQuery").widget({
		  			referwid:'RL_T_BASE_GOODS_INFO',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true
		  		});
		  		$("#report-customersort-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
		   			singleSelect:true,
		   			width:'210',
		   			onlyLeafCheck:false
		  		});
		  		$("#report-salesarea-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_AREA',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:false
		  		});
		  		
		  		//品牌
		  		$("#report-brandid-advancedQuery").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbrandid-advancedQuery").val("");
		   			}
		   		});
		   		//品牌部门
		   		$("#report-branddept-advancedQuery").widget({
		   			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranddept-advancedQuery").val("");
		   			}
		   		});
		   		//品牌业务员
		   		$("#report-branduser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranduser-advancedQuery").val("");
		   			}
		   		});
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-baseSalesReportPage","");

				//导出
				$("#report-export-baseSalesReportPage").Excel('export',{
					queryForm: "#report-query-form-baseSalesReport",
			 		type:'exportUserdefined',
			 		name:'财务销售情况统计表',
			 		url:'report/sales/exportBaseSalesReportData.do?exporttype=finance'
				});
				//高级查询
				$("#report-advancedQuery-baseSalesReportPage").click(function(){
					$("#report-dialog-advancedQueryDialog").dialog({
						maximizable:true,
						resizable:true,
						title: '销售情况统计表查询',
						top:30,
					    width: 500,
					    height: 450,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'确定',
								handler:function(){
									setColumn();
									//把form表单的name序列化成JSON对象
						      		var queryJSON = $("#report-query-form-baseSalesReport").serializeJSON();
						      		$("#report-datagrid-baseSalesReport").datagrid({
						      			url:'report/sales/getFinanceSalesReportData.do',
						      			pageNumber:1,
				   						queryParams:queryJSON
						      		}).datagrid("columnMoving");

						      		$("#report-dialog-advancedQueryDialog").dialog('close');
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-query-form-baseSalesReport").form("reset");
									$(".groupcols").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-groupcols").val("");
									$("#report-customernamemore-advancedQuery").widget("clear");
									$("#report-pcustomernamemore-advancedQuery").widget("clear");
									$("#report-customersort-advancedQuery").widget("clear");
									$("#report-brandid-advancedQuery").widget("clear");
									$("#report-branddept-advancedQuery").widget("clear");
									$("#report-branduser-advancedQuery").widget("clear");
									$("#report-salesarea-advancedQuery").widget("clear");
									$("#report-goodsid-advancedQuery").widget("clear");
									$("#report-datagrid-baseSalesReport").datagrid("loadData",[]);
									setColumn();
								}
							}
							],
						onClose:function(){
						}
					});
				});
    		});
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
	    			$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "customerid");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "customername");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "salesuser");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "customersort");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "salesarea");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "salesdept");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "barcode");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "brandid");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "branduser");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "branddept");
				}
				else{
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "customerid");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "customername");
					$("#report-datagrid-baseSalesReport").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesuser");
					//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesarea");
					//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesdept");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "goodsid");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "goodsname");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "barcode");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "brandid");
					$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branduser");
					//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branddept");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=='customerid'){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "customerid");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "customername");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "pcustomerid");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesuser");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "customersort");
						//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesarea");
						//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesdept");
					}else if(col=="pcustomerid"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "pcustomerid");
					}else if(col=="salesuser"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesuser");
						//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesarea");
						//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesdept");
					}else if(col=="salesarea"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesarea");
					}else if(col=="salesdept"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesdept");
					}else if(col=="goodsid"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "goodsid");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "goodsname");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "barcode");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "brandid");
					}else if(col=="brandid"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "brandid");
						//$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branddept");
					}else if(col=="branduser"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branduser");
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branddept");
					}else if(col=="branddept"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branddept");
					}else if(col=="customersort"){
						$("#report-datagrid-baseSalesReport").datagrid('showColumn', "customersort");
					}
				}
    		}
    	function selectmoreparam(val){
  			var title=null;
  			if(val == "customer"){
  				title="客户多选";
  				$("#report-customer-selectmoreparamlist").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
					width:130,
					onlyLeafCheck:false,
					singleSelect:true
				});
  			}else if(val == "pcustomer"){
  				title="总店多选";
  				$("#report-customer-selectmoreparamlist").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
					width:130,
					onlyLeafCheck:false,
					singleSelect:true
				});
  			}
  			$("#report-dialog-selectmoreparam").dialog({
				maximizable:true,
				resizable:true,
				title: title,
			    width: 600,
			    height: 400,
			    closed: false,
			    cache: false,
			    href: 'report/sales/showSelectMoreParamPage.do?type='+val,
			    modal: true,
			    toolbar:'#report-div-selectmoreparam',
			    buttons:[{
					text:'确定',
					handler:function(){
						getCustomerValues();
					}
				}]
			});
  		}

    	function countTotalAmount(){
    		var rows =  $("#report-datagrid-baseSalesReport").datagrid('getChecked');
    		if(null==rows || rows.length==0){
        		var foot=[];
    			if(null!=SR_footerobject){
	        		foot.push(SR_footerobject);
	    		}
    			$("#report-datagrid-baseSalesReport").datagrid("reloadFooter",foot);
        		return false;
    		}
    		var pushbalanceamount=0;
    		var initsendnum = 0;
    		var initsendamount = 0;
    		var initsendnotaxamount=0;
    		var sendnum = 0;
    		var sendamount = 0;
    		var sendnotaxamount = 0;
    		var sendcostamount = 0;
    		var pushbalanceamount = 0;
    		var directreturnnum=0;
    		var directreturnamount =0;
    		var checkreturnnum=0;
    		var checkreturnamount=0;
    		var returnnum =0;
    		var returnamount = 0;
    		var salenum = 0;
    		var saleamount = 0;
    		var salenotaxamount = 0;
    		var saletax = 0;
    		var costamount = 0;
    		var salemarginamount = 0;
    		var writeoffamount = 0;
    		var costwriteoffamount =0 ;
    		var writeoffmarginamount =0;
    		for(var i=0;i<rows.length;i++){
    			pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
    			initsendnum = Number(initsendnum)+Number(rows[i].initsendnum == undefined ? 0 : rows[i].initsendnum);
    			initsendamount = Number(initsendamount)+Number(rows[i].initsendamount == undefined ? 0 : rows[i].initsendamount);
    			initsendnotaxamount = Number(initsendnotaxamount)+Number(rows[i].initsendnotaxamount == undefined ? 0 : rows[i].initsendnotaxamount);
    			sendnum = Number(sendnum)+Number(rows[i].sendnum == undefined ? 0 : rows[i].sendnum);
    			sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
    			sendnotaxamount = Number(sendnotaxamount)+Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
    			sendcostamount = Number(sendcostamount)+Number(rows[i].sendcostamount == undefined ? 0 : rows[i].sendcostamount);
    			pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
    			directreturnnum = Number(directreturnnum)+Number(rows[i].directreturnnum == undefined ? 0 : rows[i].directreturnnum);
    			directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
    			checkreturnnum = Number(checkreturnnum)+Number(rows[i].checkreturnnum == undefined ? 0 : rows[i].checkreturnnum);
    			checkreturnamount = Number(checkreturnamount)+Number(rows[i].checkreturnamount == undefined ? 0 : rows[i].checkreturnamount);
    			returnnum = Number(returnnum)+Number(rows[i].returnnum == undefined ? 0 : rows[i].returnnum);
    			returnamount = Number(returnamount)+Number(rows[i].returnamount == undefined ? 0 : rows[i].returnamount);
    			salenum = Number(salenum)+Number(rows[i].salenum == undefined ? 0 : rows[i].salenum);
    			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
    			salenotaxamount = Number(salenotaxamount)+Number(rows[i].salenotaxamount == undefined ? 0 : rows[i].salenotaxamount);
    			saletax = Number(saletax)+Number(rows[i].saletax == undefined ? 0 : rows[i].saletax);
    			costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
    			salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
    			costwriteoffamount = Number(costwriteoffamount)+Number(rows[i].costwriteoffamount == undefined ? 0 : rows[i].costwriteoffamount);
    			writeoffmarginamount = Number(writeoffmarginamount)+Number(rows[i].writeoffmarginamount == undefined ? 0 : rows[i].writeoffmarginamount);
    		}
    		var foot=[{goodsname:'选中合计',pushbalanceamount:pushbalanceamount,initsendnum:initsendnum,
        				initsendamount:initsendamount,initsendnotaxamount:initsendnotaxamount,sendnum:sendnum,sendamount:sendamount,
        				sendnotaxamount:sendnotaxamount,sendcostamount:sendcostamount,pushbalanceamount:pushbalanceamount,directreturnnum:directreturnnum,directreturnamount:directreturnamount,checkreturnnum:checkreturnnum,
        				checkreturnamount:checkreturnamount,returnnum:returnnum,returnamount:returnamount,salenum:salenum,saleamount:saleamount,
        				salenotaxamount:salenotaxamount,saletax:saletax,costamount:costamount,salemarginamount:salemarginamount,
        				writeoffamount:writeoffamount,costwriteoffamount:costwriteoffamount,writeoffmarginamount:writeoffmarginamount
        			}];
    		if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#report-datagrid-baseSalesReport").datagrid("reloadFooter",foot);
    	}
    	</script>
  </body>
</html>
