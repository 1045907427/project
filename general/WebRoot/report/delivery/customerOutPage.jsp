<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代配送客户出库统计</title>
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
    	<table id="report-datagrid-customerOutReport"></table>
    	<div id="report-toolbar-customerOutReport" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-customerOutPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>   	
			<a href="javaScript:void(0);" id="report-advancedExport-customerOutPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="导出">导出</a>   	
			
 		</div>
    	<div id="report-dialog-advancedQueryDialog" >
    		<form action="" id="report-query-form-customerOutReport" method="post">
	    		<table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" name="businessdatestart" value="${today }" style="width:87px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdateend" value="${today }" class="Wdate" style="width:87px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
		  				
		  			</tr>
		  			<tr>
		  				<td>客&nbsp;&nbsp;户:</td>
		  				<td><input type="text" name="customerid" id="report-customernamemore-advancedQuery"/></td>
		  			</tr>
		  			<tr>
		  				<td>品&nbsp;&nbsp;牌:</td>
			  			<td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
			  		</tr>
	    			<tr>
	    				<td>商&nbsp;&nbsp;品:</td>
		  				<td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
		  			</tr>
	    			<tr>
	    				<td>供 应 商:</td>
	    				<td><input type="text" name="supplierid" id="report-supplierid-advancedQuery"/></td>
	    			</tr>
	    			<tr>
	    				<td>小计列：</td>
	    				<td colspan="3">
	    					<div style="margin-top:2px">
		    					<div style="line-height: 25px;margin-top: 2px;">
			    					<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodssort"/>商品分类</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
                                    <input id="report-query-groupcols" type="hidden" name="groupcols"/>
		    					</div>
	    					</div>
	    				</td>
	    			</tr>
	    		</table>
    		</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#customerOutReport").serializeJSON();
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
    			var tableColumnListJson = $("#report-datagrid-customerOutReport").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:210},
								  
								  
								  {field:'goodsid',title:'商品编码',sortable:true,width:60,hidden:true},
								  {field:'goodsname',title:'商品名称',width:200,hidden:true},
								  
								  {field:'brandid',title:'品牌',hidden:true,width:60},
								  {field:'brandname',title:'品牌名称',width:150,hidden:true},
								  
								  {field:'goodssort',title:'商品分类',width:250,hidden:true},
								  {field:'goodssortname',title:'商品分类名称',width:130,hidden:true}, 
								  
								  
								  {field:'supplierid',title:'供应商编号',width:80,hidden:true},
								  {field:'suppliername',title:'供应商名称',width:200,hidden:true}, 
								  
								 
								  
								  {field:'saleprice',title:'销售金额',sortable:true,width:100,
									  	formatter:function(value,rowData,rowIndex){
									  		return formatterMoney(value);
									  	}
								  },
								  {field:'cost',title:'成本金额',sortable:true,width:100,
									  	formatter:function(value,rowData,rowIndex){
									  		return formatterMoney(value);
									  	}
								  },
								  {field:'totalbox',title:'箱数',sortable:true,width:100,
									  	formatter:function(value,rowData,rowIndex){
									  		return formatterBigNumNoLen(value);
									  	}
								  },
								  {field:'volume',title:'体积',sortable:true,width:100,
									  	formatter:function(value,rowData,rowIndex){
									  		return formatterMoney(value);
									  	}
								  },
								  {field:'weight',title:'重量',sortable:true,width:100,
								  		formatter:function(value,rowData,rowIndex){
									  		 return formatterMoney(value);
									  	}
								  }
								  
								  
								  
					            ]]
				});
    			//品牌
		  		$("#report-brandid-advancedQuery").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		   		});
		   		//供应商
		   		$("#report-supplierid-advancedQuery").widget({
		   			referwid:'RL_T_BASE_BUY_SUPPLIER',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		   		});
		   		//客户
		   		$("#report-customernamemore-advancedQuery").widget({
		  			referwid:'RL_T_BASE_SALES_CUSTOMER',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		  		});
		  		//商品
		  		$("#report-goodsid-advancedQuery").widget({
		  			referwid:'RL_T_BASE_GOODS_INFO',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		  		});
		   		
    			$("#report-datagrid-customerOutReport").datagrid({ 
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
					toolbar:'#report-toolbar-customerOutReport',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						console.log(footerrows)
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
						    footerReCalc(getColsname());
						}
			 		},
					onCheckAll:function(){
						footerReCalc(getColsname());
					},
					onUncheckAll:function(){
						footerReCalc(getColsname());
					},
					onCheck:function(){
						console.log(1)
						footerReCalc(getColsname());
					},
					onUncheck:function(){
						footerReCalc(getColsname());
					}
				}).datagrid("columnMoving");
		   		
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-customerOutPage","");

				//高级查询
				$("#report-advancedQuery-customerOutPage").click(function(){
					$("#report-dialog-advancedQueryDialog").dialog({
						maximizable:false,
						resizable:true,
						title: '客户代配送出库统计表查询',
						top:30,
					    width: 400,
					    height: 270,
					    closed: false,
					    cache: false,
					    modal: true,
					    buttons:[
							{
								text:'确定',
								handler:function(){
									setColumn();
									//把form表单的name序列化成JSON对象
						      		var queryJSON = $("#report-query-form-customerOutReport").serializeJSON();
						      		$("#report-datagrid-customerOutReport").datagrid({
						      			url:'report/delivery/cusout/showBaseDeliveryCustomerOut.do',
						      			//pageNumber:1, ?
				   						queryParams:queryJSON
						      		}).datagrid("columnMoving");

						      		$("#report-dialog-advancedQueryDialog").dialog('close');
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-query-form-customerOutReport").form("reset");
									$(".groupcols").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-groupcols").val("");
									$("#report-customernamemore-advancedQuery").widget("clear");
									$("#report-brandid-advancedQuery").widget("clear");
									$("#report-goodsid-advancedQuery").widget("clear");
									$("#report-supplierid-advancedQuery").widget('clear');
									$("#report-datagrid-customerOutReport").datagrid("loadData",[]);
									setColumn();
								}
							}
							],
						onClose:function(){
							
						}
					});
				});
				
				//导出
				$("#report-advancedExport-customerOutPage").Excel('export',{
					queryForm: "#report-query-form-customerOutReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 	    datagrid: "#report-datagrid-customerOutReport",
			 	    type:'exportUserdefined',
			 		name:'代配送客户出库报表',
			 		url:'report/delivery/cusout/ExportDeliveryCustomerReport.do?deliveyType=1',
				});
    		});
    		
    		
    		
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
	    			$("#report-datagrid-customerOutReport").datagrid('hideColumn', "customerid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "customername");
					
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodsname");
					
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "brandid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "brandname");
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodssort");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodssortname");
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "supplierid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "suppliername");
				}
				else{
					$("#report-datagrid-customerOutReport").datagrid('showColumn', "customerid");
					$("#report-datagrid-customerOutReport").datagrid('showColumn', "customername");
					
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodsname");
					
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "brandid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "brandname");
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodssort");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "goodssortname");
					
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "supplierid");
					$("#report-datagrid-customerOutReport").datagrid('hideColumn', "suppliername");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=='customerid'){
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "customerid");
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "customername");
					}else if(col=="goodsid"){
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "goodsid");
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "goodsname");
					}else if(col=="brandid"){
						//$("#report-datagrid-customerOutReport").datagrid('showColumn', "brandid");
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "brandname");
					}else if(col=='goodssort'){
						//$("#report-datagrid-customerOutReport").datagrid('showColumn', "goodssort");
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "goodssortname");
					}
					else if(col=="supplierid"){
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "supplierid");
						$("#report-datagrid-customerOutReport").datagrid('showColumn', "suppliername");
					}
				}
    		}
	  		
	  	
	  		
	  		
	  		function getColsname(){
	  			var colname = "";
	  			var cols = $("#report-query-groupcols").val();
	  			if(cols == ""){
	  				cols = "customerid";
	  			}
	  			var colarr = cols.split(",");
	  			if(colarr[0]=='customerid'){
					colname = "customername";
				}else if(colarr[0]=="goodsid"){
					colname = "goodsname";
				}else if(colarr[0]=="goodssort"){
					colname = "goodssortname";
				}else if(colarr[0]=="brandid"){
					colname = "brandname";
				}else if(colarr[0]=="supplierid"){
					colname = "suppliername";
				}
				return colname;
	  		}
	  		
	  		
	  		
	   function footerReCalc(colsname){
		var $potable=$("#report-datagrid-customerOutReport");
		var data = $potable.datagrid('getChecked');
		var saleprice = 0;
		var cost = 0;
		var totalbox=0;
		var volume=0;
		var weight=0;
		for(var i=0;i<data.length;i++){
 			if(data[i].saleprice){
				saleprice = Number(saleprice)+Number(data[i].saleprice);
 			}
 			if(data[i].cost){
				cost=Number(cost)+Number(data[i].cost);
 			}
 			if(data[i].totalbox){
 				totalbox=Number(totalbox)+Number(data[i].totalbox);
 			}
 			if(data[i].volume){
 				volume=Number(volume)+Number(data[i].volume);
 			}
 			if(data[i].weight){
 				weight=Number(weight)+Number(data[i].weight);
 			}
		}	
		console.log(colsname)
		console.log(saleprice)
		var obj={saleprice: saleprice,cost:cost,totalbox:totalbox,volume:volume,weight:weight};
		obj[colsname]='选中合计';
		console.log(obj)
		var foot=[];
		foot.push(obj);
		if(null!=SR_footerobject){
   			foot.push(SR_footerobject);
		}
		$potable.datagrid('reloadFooter',foot); 
	}
	  		
	  		
	  		
	  		
    	</script>
  </body>
</html>
