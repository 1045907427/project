<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购计划分析表</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<div id="purchase-buyOrderPage-layout" class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:false">
	    	<table id="purchase-datagrid-analysisReportView"></table>
	    	<div id="purchase-toolbar-analysisReportView">
	    		<form action="" id="purchase-query-form-analysisReportView" method="post">
	    		<table>
	    			<tr>
	    				<td>同期日期:</td>
	    				<td><input type="text" id="purchase-query-tqstartdate" name="tqstartdate" value="${prevyearfirstday }" style="width:100px;" class="Wdate easyui-validatebox" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="purchase-query-tqenddate" type="text" name="tqenddate" value="${prevyearcurday }" class="Wdate easyui-validatebox" data-options="required:true" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>供应商:</td>
		    			<td>
		    				<input type="text" id="purchase-query-analysisReportView-supplier" style="width: 212px;" name="supplierid" value="${param.supplierid }"/>		    				
		    			</td>
	    				
	    			</tr>
	    			<tr>
	    				<td>前期日期:</td>
	    				<td><input type="text" id="purchase-query-qqstartdate" name="qqstartdate" value="${firstDay }" style="width:100px;" class="Wdate easyui-validatebox" data-options="required:true" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input id="purchase-query-qqenddate" type="text" name="qqenddate" value="${businessdate }" class="Wdate easyui-validatebox" data-options="required:true" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>
    						显示全部：
    					</td>
    					<td>
    						<select id="purchase-query-analysisReportView-isShowAll" name="isShowAll" style="width:40px;">	    						
	    						<option value="1">是</option>
	    						<option value="0">否</option>
	    					</select>
	    					库存参考:
	    					<select id="purchase-query-referDataNumBy" name="referStorageDataNumBy" style="width:105px;">
	    						<option value="1">库存现存量</option>
								<option value="2">库存可用量</option>
	    					</select>
						销售数据来源:
							<select name="salesdatatype" style="width:75px;">
								<option value="0">系统销售</option>
								<option value="1">数据上报</option>
								<option value="2">大宗销售</option>
							</select>
    					</td>
	    			</tr>
	    			<tr>
	    				<td colspan="2">
	    					只计算发货仓:
	    					<select id="purchase-query-notContainCCStorage" name="isSendsStorage" style="width:50px;">	    						
	    						<option value="1">是</option>
	    						<option value="0">否</option>
	    					</select>
	    					日期参考:
	    					<select id="purchase-query-referDateBy" name="referDateBy" style="width:80px;">
	    						<option value="1">同期日期</option>
	    						<option value="2" selected="selected">前期日期</option>
	    					</select>
						</td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="purchase-queay-analysisReportView" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="purchase-reload-analysisReportView" class="button-qr">重置</a>
	    					<security:authorize url="/purchase/buyorder/exportAnalysisReportView.do">
	    						<a href="javaScript:void(0);" id="report-export-analysisReportView" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
	    					</security:authorize>
	    				</td>
	    			</tr>
	    		</table>
	    		<input type="hidden" name="orderDetailParam" id="purchase-queay-analysisReportView-orderInfoarr" />
	    		<input type="hidden" name="orderstatus" id="purchase-queay-analysisReportView-orderstatus" />
	    		<input type="hidden" name="goodsidarr" id="purchase-queay-analysisReportView-form-goodsidarr" />
	    	</form>
	    	<input type="hidden" id="purchase-query-notContainCCStorage-before" value="1"/>
	    	<input type="hidden" name="goodsidarr" id="purchase-queay-analysisReportView-goodsidarr" />
	    	<input type="hidden" id="purchase-query-analysisReportView-hidden-supplier" value="${param.supplierid }" />
	    	</div>
       </div>
       <div data-options="region:'south',border:false,tools:'#brandStoragePanel-tools',headerCls:'awinpanel-header'" title="按品牌合计库存" style="height:180px;" >
       		<table id="purchase-datagrid-storageSummaryReport"></table>
       </div>
    </div> 
    <div style="display:none">    	
	    <div id="brandStoragePanel-tools">  
	        <a href="javascript:void(0)" id="brandStoragePanel-tools-reload" class="icon-reload" title="刷新"></a>
	    </div>  
    </div>
    	<script type="text/javascript">
			var poaOldFormData=null;
    		var checkListJson = $("#purchase-datagrid-analysisReportView").createGridColumnLoad({
				frozenCol : [[
							  {field:'goodsid',title:'商品编码',width:60,sortable:true}
			    			]],
				commonCol : [[
							  {field:'goodsname',title:'商品名称',width:200,aliascol:'goodsid'},
							  {field:'barcode',title:'条形码',sortable:true,width:90},
							  {field:'goodssort',title:'商品分类',sortable:true,width:90,
								  	formatter:function(value,rowData,rowIndex){
				        				return rowData.goodssortname;
				        			}
					       	  },
							  {field:'brandname',title:'品牌名称',width:70,aliascol:'goodsid',hidden:true},
							  {field:'boxnum',title:'箱装量',width:50,align:'right',
								  	formatter:function(value,rowData,rowIndex){
				        				return formatterBigNumNoLen(value);
				        			}
					       		},
							  {field:'unitname',title:'主单位',width:50},
							  {field:'auxunitname',title:'单位',width:50,
								  	formatter:function(value,rowData,rowIndex){
								  		if(rowData.goodsid!='合计'){
								  			return value;
								  		}
						        	}
							  },
							  {field:'buyprice',title:'采购单价',width:70,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
							  },
							  {field:'boxamount',title:'箱价',width:50,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
						      {field:'existingunitnum',title:'实际数量',width:80,align:'right',sortable:true},
							  {field:'existingnum',title:'实际库存',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
								  	if(value !=null && $.trim(value)!=""){
								  		if(rowData.goodsid!='合计'){
						        			return formatterMoney(value)+rowData.auxunitname;			  			
								  		}else{							  				
						        			return formatterMoney(value);					
								  		}
					        		}else{
					        			return "";
					        		}
					        	}
							  },
							  {field:'existingamount',title:'实际库存金额',width:80,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		return formatterMoney(value);
						        	}
							  },
							  {field:'transitnum',title:'在途量',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
								  	if(value !=null && $.trim(value)!=""){
								  		if(rowData.goodsid!='合计'){
						        			return formatterMoney(value)+rowData.auxunitname;			  			
								  		}else{							  				
						        			return formatterMoney(value);					
								  		}
					        		}else{
					        			return "";
					        		}
					        	}
							  },
							  {field:'transitamount',title:'在途金额',width:80,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		return formatterMoney(value);
						        	}
							  },
							  {field:'curstoragenum',title:'本期存货',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
								  	if(value !=null && $.trim(value)!=""){
								  		if(rowData.goodsid!='合计'){
						        			return formatterMoney(value)+rowData.auxunitname;			  			
								  		}else{							  				
						        			return formatterMoney(value);					
								  		}
					        		}else{
					        			return "";
					        		}
					        	}
							  },
							  {field:'curstorageamount',title:'本期库存金额',width:80,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		return formatterMoney(value);
						        	}
							  },
								{field:'tqsaleunitnum',title:'同期销售主单位数量',width:100,align:'right',sortable:true,hidden:true,
									formatter:function(value,rowData,rowIndex){
										if(value !=null && $.trim(value)!=""){
											return formatterBigNumNoLen(value)+rowData.unitname;
										}else{
											return "";
										}
									}
								},
							  {field:'tqsalenum',title:'同期销售数量',width:80,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
									  	if(value !=null && $.trim(value)!=""){
									  		if(rowData.goodsid!='合计'){
							        			return formatterMoney(value)+rowData.auxunitname;			  			
									  		}else{							  				
							        			return formatterMoney(value);					
									  		}
						        		}else{
						        			return "";
						        		}
						        	}
							  },
								{field:'tqsaleamount',title:'同期销售金额',width:80,align:'right',sortable:true,hidden:true,
									formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
								{field:'qqsaleunitnum',title:'前期销售主单位数量',width:100,align:'right',sortable:true,hidden:true,
									formatter:function(value,rowData,rowIndex){
										if(value !=null && $.trim(value)!=""){
											return formatterBigNumNoLen(value)+rowData.unitname;
										}else{
											return "";
										}
									}
								},
							  {field:'qqsalenum',title:'前期销售数量',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
								  	if(value !=null && $.trim(value)!=""){
								  		if(rowData.goodsid!='合计'){
						        			return formatterMoney(value)+rowData.auxunitname;			  			
								  		}else{							  				
						        			return formatterMoney(value);					
								  		}
					        		}else{
					        			return "";
					        		}
					        	}
							  },
								{field:'qqsaleamount',title:'前期销售金额',width:80,align:'right',sortable:true,hidden:true,
									formatter:function(value,rowData,rowIndex){
										return formatterMoney(value);
									}
								},
							  {field:'canordernum',title:'可订量',width:70,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
									  	if(value>0){
									  		if(rowData.goodsid!='合计'){
							        			return formatterMoney(value)+rowData.auxunitname;			  			
									  		}else{							  				
							        			return formatterMoney(value);					
									  		}
						        		}else{
						        			return "";
						        		}
						        	},
						        	styler:function(value,rowData,rowIndex){
						        		if(value>0 && rowData.ordernum>value){
								        	return "background-color:#FFC0CB;font-weight:bold;";
							        	}
						        	}
							  },
							  {field:'orderunitnum',title:'本次采购主单位数量',width:120,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value>0){
								  			if(rowData.goodsid!='合计'){
							        			return formatterMoney(value)+rowData.unitname;			  			
									  		}else{							  				
							        			return formatterMoney(value);					
									  		}
						        		}else{
						        			return "";
						        		}
						        	},
						        	styler:function(value,rowData,rowIndex){
							        	if(canMarkColorByOrdernum(rowData)){
								        	return "background-color:#FFC0CB;font-weight:bold;";
							        	}
						        	}
							  },
							  {field:'ordernum',title:'本次采购数量',width:80,align:'right',
								  	formatter:function(value,rowData,rowIndex){
								  		if(value>0){
								  			if(rowData.goodsid!='合计'){
							        			return formatterMoney(value)+rowData.auxunitname;			  			
									  		}else{							  				
							        			return formatterMoney(value);					
									  		}
						        		}else{
						        			return "";
						        		}
						        	},
						        	styler:function(value,rowData,rowIndex){
							        	if(canMarkColorByOrdernum(rowData)){
								        	return "background-color:#FFC0CB;font-weight:bold;";
							        	}
						        	}
							  },
							  {field:'orderamount',title:'本次采购金额',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
							  },
							  {field:'totalstoragenum',title:'合计库存数',width:80,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value !=null && $.trim(value)!=""){
									  		if(rowData.goodsid!='合计'){
							        			return formatterMoney(value)+rowData.auxunitname;			  			
									  		}else{							  				
							        			return formatterMoney(value);					
									  		}
						        		}else{
						        			return "";
						        		}
						        	}
							  },
							  {field:'totalstorageamount',title:'合计库存金额',width:80,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		return formatterMoney(value);
						        	}
							  },
							  {field:'cansaleday',title:'可销售时段',width:70,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value!=null){
									  		return value;
								  		}else{
								  			return "";
								  		}
						        	},
						        	styler:function(value,rowData,rowIndex){
							        	if(value>30){
								        	return "background-color:#FFC0CB;font-weight:bold;";
							        	}
						        	}
							  },
						      {field:'saleday',title:'查询间隔天数',width:80,align:'right',hidden:true}
				             ]]
			});
    		var storageTableColJson = $("#purchase-datagrid-storageSummaryReport").createGridColumnLoad({
				//name :'storage_summary',
				frozenCol : [[]],
				commonCol : [[  
								{field:'brandid',title:'品牌名称',width:80,isShow:true,isShow:true,
						        	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
						        },
						        {field:'ordernum',title:'本次采购数',resizable:true,width:100,align:'right',
						        	formatter:function(value,rowData,rowIndex){
						        		/*
						        		var result="";
						        		if(rowData.ordernum!=null ){
							        		if(rowData.ordernum!=0){
							        			result= formatterNum(rowData.ordernum)
							        		}else{
								        		result="0";
							        		}
				        					result=result+rowData.auxunitname;
						        		}
						        		if(rowData.orderauxremainder!=null){
							        		if(rowData.orderauxremainder!=0){
							        			result=result+ formatterNum(rowData.orderauxremainder)
							        		}else{
								        		result=result+"0";
							        		}
				        					result=result+rowData.unitname;
						        		}
						        		*/
						        		return formatterMoney(value);
						        	}
						        },
						        {field:'orderamount',title:'本次采购金额',resizable:true,width:100,align:'right',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        },
						        {field:'curstoragenum',title:'当前库存数',resizable:true,width:100,align:'right',
						        	formatter:function(value,rowData,rowIndex){
						        	var result="";
						        		if(rowData.curstorageauxint!=null ){
							        		if(rowData.curstorageauxint!=0){
							        			result= formatterNum(rowData.curstorageauxint)
							        		}else{
								        		result="0";
							        		}
				        					result=result+rowData.auxunitname;
						        		}
						        		if(rowData.curstorageauxnum!=null){
							        		if(rowData.curstorageauxnum!=0){
							        			result=result+ formatterNum(rowData.curstorageauxnum)
							        		}else{
								        		result=result+"0";
							        		}
				        					result=result+rowData.unitname;
						        		}
						        		return result;
						        	}
						        },
						        {field:'curstorageamount',title:'当前库存金额',resizable:true,width:100,align:'right',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        },
						        {field:'totalstoragenum',title:'合计库存数',resizable:true,width:100,align:'right',
						        	formatter:function(value,rowData,rowIndex){
						        	var result="";
						        		if(rowData.totalstorageauxint!=null ){
							        		if(rowData.totalstorageauxint!=0){
							        			result= formatterNum(rowData.totalstorageauxint)
							        		}else{
								        		result="0";
							        		}
				        					result=result+rowData.auxunitname;
						        		}
						        		if(rowData.totalstorageauxnum!=null){
							        		if(rowData.totalstorageauxnum!=0){
							        			result=result+ formatterNum(rowData.totalstorageauxnum)
							        		}else{
								        		result=result+"0";
							        		}
				        					result=result+rowData.unitname;
						        		}
						        		return result;
						        	}
						        },
						        {field:'totalstorageamount',title:'合计库存金额',resizable:true,width:100,align:'right',
						        	formatter:function(value,rowData,rowIndex){
				        				return formatterMoney(value);
						        	}
						        }
						    ]]
			});
    		$(function(){
    			
				
				//回车事件
				controlQueryAndResetByKey("purchase-queay-analysisReportView","purchase-reload-analysisReportView");
				
				//查询
				$("#purchase-queay-analysisReportView").click(function(){
					//把form表单的name序列化成JSON对象
					var flag=$("#purchase-query-form-analysisReportView").form('validate');
		  		   	if(flag==false){
		  		   		return false;
		  		   	}
		      		var queryJSON = $("#purchase-query-form-analysisReportView").serializeJSON();
		      		$("#purchase-datagrid-analysisReportView").datagrid({
			      		url:'report/buy/showPlannedOrderAnalysisPageListInBuyOrder.do',
		      			pageNumber:1,
   						queryParams:queryJSON
			      	}).datagrid("columnMoving");
		      		$("#purchase-datagrid-analysisReportView").datagrid('clearSelections');

		      		$("#purchase-datagrid-storageSummaryReport").datagrid({  
			      		url:'report/buy/showStorageSummaryByBrandInBuyOrder.do',
		      			pageNumber:1,
						queryParams:queryJSON
			      	}).datagrid("columnMoving");
				});
				//重置
				$("#purchase-reload-analysisReportView").click(function(){
					var supplierid=$("#purchase-query-analysisReportView-hidden-supplier").val();
					$("#purchase-query-analysisReportView-supplier").widget('clear');
					$("#purchase-query-form-analysisReportView").form("reset");
					if(poaOldFormData!=null && poaOldFormData.supplierid!=null){
						$("#purchase-query-form-analysisReportView").form('load',poaOldFormData);
					}
					if(supplierid!=null){
						$("#purchase-query-analysisReportView-supplier").widget('setValue',supplierid);
					}
					//var queryJSON = $("#purchase-query-form-analysisReportView").serializeJSON();
		       		$("#purchase-datagrid-analysisReportView").datagrid('loadData',{total:0,rows:[]});
		       		$("#purchase-datagrid-analysisReportView").datagrid('clearSelections');
		       		//selectBrandCountTotalAmount({brandid:''});
				});
				
				//导出
				$("#report-export-analysisReportView").Excel('export',{
					queryForm: "#purchase-query-form-analysisReportView",
			 		type:'exportUserdefined',
			 		name:'导出相应采购计划分析表',
			 		url:'report/buy/exportAnalysisReportView.do'
				});

				$("#brandStoragePanel-tools-reload").click(function(){
					//把form表单的name序列化成JSON对象
					var flag=$("#purchase-query-form-analysisReportView").form('validate');
		  		   	if(flag==false){
		  		   		return false;
		  		   	}
		      		var queryJSON = $("#purchase-query-form-analysisReportView").serializeJSON();
		      		$("#purchase-datagrid-storageSummaryReport").datagrid({  
			      		url:'report/buy/showStorageSummaryByBrandInBuyOrder.do',
		      			pageNumber:1,
   						queryParams:queryJSON
			      	}).datagrid("columnMoving");
				});
				
				$("#purchase-query-analysisReportView-supplier").widget({ 
					name:'t_purchase_buyorder',
					col:'supplierid',
					width:212,
					required:true,
					readonly:true,
					singleSelect:true,
					onlyLeafCheck:true,
					onSelect:function(data){
					}
				});	
				
				$("#purchase-query-analysisReportView-isShowAll").change(function(){
					if($(this).val()=="1"){
						$("#purchase-queay-analysisReportView-form-goodsidarr").val("");
					}else{
						$("#purchase-queay-analysisReportView-form-goodsidarr").val($("#purchase-queay-analysisReportView-goodsidarr").val() || "");
					}
				});
				
    		});
    		var $wareList = $("#purchase-datagrid-analysisReportView"); //商品datagrid的div对象

    		function initAnalysisDatagrid(){
        		var initQueryJSON = $("#purchase-query-form-analysisReportView").serializeJSON();
    			$("#purchase-datagrid-analysisReportView").datagrid({ 
					authority:checkListJson,
		 			frozenColumns: checkListJson.frozen,
					columns:checkListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		idField:'goodsid',
		  	 		rownumbers:true,
		  	 		pagination:false,
		  	 		showFooter:true,
		  	 		singleSelect:true,
		  	 		pageSize:200,
					url: 'report/buy/showPlannedOrderAnalysisPageListInBuyOrder.do',
					queryParams:initQueryJSON,
					toolbar:'#purchase-toolbar-analysisReportView',
	    			onLoadSuccess:function(data){
	    			}
				}).datagrid("columnMoving");

    		}

    		function initStorageSummaryDategrid(){
        		var initQueryJSON = $("#purchase-query-form-analysisReportView").serializeJSON();
    			$("#purchase-datagrid-storageSummaryReport").datagrid({  
    				//authority:storageTableColJson,
    	  	 		frozenColumns: storageTableColJson.frozen,
    				columns:storageTableColJson.common,
    			    fit:true,
    			    //data:data,
    			    rownumbers:true,
    			    pagination: false,  
    			    idField:'brandid',  
    			    showFooter: false,
    			    singleSelect:true,
    			    pageSize:100,
					url: 'report/buy/showStorageSummaryByBrandInBuyOrder.do',
					queryParams:initQueryJSON
    			}).datagrid("columnMoving");
    		}
	        function dataChangeByOrdernum(data){
	        	var referDateBy=$("#purchase-query-referDateBy").val();
		        if(data!=null && typeof(data)=="object"){
			        var ordernum=0
			        if(data.ordernum != null && !isNaN(data.ordernum)){
			        	ordernum=Number(data.ordernum);
			        }
			        if(data.boxamount == null || isNaN(data.boxnum)){
			        	data.boxamount=0;
			        }
			        if(data.boxnum == null || isNaN(data.boxnum)){
			        	data.boxnum=0;
			        }
			        if(data.buyprice != null && !isNaN(data.buyprice) && data.buyprice>0){
			        	data.orderunitnum=Number(ordernum)* Number(data.boxnum);
			        }else{
			        	data.orderunitnum=0;
			        }
			        if(data.goodsid!=null && ""!=data.goodsid){
			        	storeChangeData[data.goodsid]=Number(ordernum);
			        }
			        data.orderamount=Number(data.boxamount) * Number(ordernum);
			        data.orderunitamount=Number(data.boxamount) * Number(data.orderunitnum);			        
			        if(data.curstoragenum !=null && !isNaN(data.curstoragenum)){
			        	data.totalstoragenum=Number(data.curstoragenum)+ordernum;
			        	data.totalstorageamount=Number(data.orderamount)+Number(data.totalstorageamount);
			        	if(referDateBy=="1"){
				        	if(data.tqsalenum!=null && !isNaN(data.tqsalenum) && data.tqsalenum>0){
					        	if(data.saleday !=null && !isNaN(data.saleday)){
							        data.cansaleday=Math.ceil(data.totalstoragenum * data.saleday /data.tqsalenum);
						        }else{
							        var saleday=getSaleday();
							        data.cansaleday=Math.ceil(data.totalstoragenum * saleday /data.tqsalenum);						        
						        }
				        	}
			        	}else{
				        	if(data.qqsalenum!=null && !isNaN(data.qqsalenum) && data.qqsalenum>0){
					        	if(data.saleday !=null && !isNaN(data.saleday)){
							        data.cansaleday=Math.ceil(data.totalstoragenum * data.saleday /data.qqsalenum);
						        }else{
							        var saleday=getQqsaleday();
							        data.cansaleday=Math.ceil(data.totalstoragenum * saleday /data.qqsalenum);						        
						        }
				        	}
			        	}
			        }
			        if(data.curstorageunitnum !=null && !isNaN(data.curstorageunitnum)){
			        	data.totalstorageunitnum=Number(data.curstorageunitnum)+Number(data.orderunitnum);
			        }
		        }
	        }
	        function getSaleday() {  
	        	var iDays=0;
	        	var referDateBy=$("#purchase-query-referDateBy").val();
		        try{
		           	var sDate1="";
		           	var sDate2="";
		           	if(referDateBy=="1"){
		           		sDate1=$.trim($("#purchase-query-tqstartdate").val()||"");
		           		sDate2=$.trim($("#purchase-query-tqedate").val() || "");
		           	}else{
		           		sDate1=$.trim($("#purchase-query-qqstartdate").val()||"");
		           		sDate2=$.trim($("#purchase-query-qqedate").val() || "");
		           	}
		           	if(sDate1=="" || sDate2 == "" ){
			           	return 0;
		           	}
		           	var aDate = sDate1.split("-");
					var oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]); 
					var aDate = sDate2.split("-");
					var oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
					iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24);  
		        }catch(e){
		        }
	            return iDays;
	        }
	        function selectBrandCountTotalAmount(rows){
	    		if(null==rows || rows.brandid==null || $.trim(rows.brandid)==""){
	           		var foot=[];
	        		foot.push(emptyChooseObjectFoot());
	    			$("#purchase-datagrid-analysisReportView").datagrid("reloadFooter",foot);
	           		return false;
	       		}
	       		var curstorageamount = 0;
	       		var orderamount =0 ;
	       		var totalstorageamount = 0;
	       		var brandname=rows.brandname;
	       		var brandid=rows.brandid;
	       		var dataRow= $("#purchase-datagrid-analysisReportView").datagrid('getRows');
	       		for(var i=0;i<dataRow.length;i++){
		       		if(brandid==dataRow[i].brandid){
		       			curstorageamount = Number(curstorageamount)+Number(dataRow[i].curstorageamount == undefined ? 0 : dataRow[i].curstorageamount);
		       			orderamount = Number(orderamount)+Number(dataRow[i].orderamount == undefined ? 0 : dataRow[i].orderamount);
		       			totalstorageamount = Number(totalstorageamount)+Number(dataRow[i].totalstorageamount == undefined ? 0 : dataRow[i].totalstorageamount);
		       		}
	       		}
	       		var foot=[{goodsid:'',goodsname:'按品牌合计金额',brandname:brandname,curstorageamount:curstorageamount,
	       			orderamount:orderamount,totalstorageamount:totalstorageamount
    			}];
	       	 	$("#purchase-datagrid-analysisReportView").datagrid("reloadFooter",foot);
	        }
	        function emptyChooseObjectFoot(){
		        var foot={goodsid:'',goodsname:'按品牌合计金额',brandname:'',curstorageamount:0,
	       			orderamount:0,totalstorageamount:0
    			};
    			return foot;
	        }
	        function canMarkColorByOrdernum(rowData){
	        	if(rowData.canordernum==null || rowData.canordernum<0){
	        		return false;
	        	}
	        	if(rowData.ordernum!=null && rowData.ordernum>rowData.canordernum){
	        		return true; 
	        	}
	        	if(rowData.boxnum!=null&&rowData.boxnum>0){
	        		var tmpd=0;
	        		var canorderunitnum=rowData.canordernum*rowData.boxnum;
	        		if(rowData.orderunitnum!=null && rowData.orderunitnum>canorderunitnum){
        				return true;
	        		}
	        	}
	        	return false;
	        }
    	</script>
  </body>
</html>