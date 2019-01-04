<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购进货差额报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-arrivalOrderCostAccount"></table>
    	<div id="report-toolbar-arrivalOrderCostAccount" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/buy/exportArrivalOrderCostAccountReportData.do">
                    <a href="javaScript:void(0);" id="report-buttons-arrivalOrderCostAccountPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-arrivalOrderCostAccount" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>采购员:</td>
    				<td><input id="report-query-buyuser" type="text" name="buyuser" style="width: 130px;"/></td>
    				<td>采购部门:</td>    				
    				<td><input id="report-query-buydept" type="text" name="buydept" style="width: 130px;"/></td>
    			</tr>
    			<tr>
	  				<td>商品:</td>
	  				<td colspan="3"><input type="text" name="goodsid" id="report-query-goodsid" style="width:340px;"/></td>
	  				<td>品牌:</td>
	  				<td><input type="text" name="brandid" id="report-query-brand"/></td>
    				
    			</tr>
    			<tr>
    				<td>供应商:</td>
	  				<td colspan="3"><input type="text" name="supplierid" id="report-query-supplier" style="width:340px;"/></td>
	  				<td colspan="4">
    					<a href="javaScript:void(0);" id="report-query-arrivalOrderCostAccount" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-arrivalOrderCostAccount" class="button-qr">重置</a>

						<%--
						<security:authorize url="/report/buy/arrivalOrderCostAccountReportAdvancedQuery.do">
							<a href="javaScript:void(0);" id="report-advancedQuery-arrivalOrderCostAccountReportPage" class="easyui-linkbutton" iconCls="icon-undo" plain="true" title="高级查询">高级查询</a>
						</security:authorize>
						--%>
    				</td>
    			</tr>
    			<tr>
    				<td>小计列：</td>
    				<td colspan="6">
    					<input type="checkbox" class="groupcols" value="goodsid"/>商品    					
    					<input type="checkbox" class="groupcols" value="supplierid"/>供应商
    					<input type="checkbox" class="groupcols" value="buydeptid"/>采购部门
    					<input type="checkbox" class="groupcols" value="buyuserid"/>采购员
    					<input type="checkbox" class="groupcols" value="brandid"/>品牌
    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<%--
    	<div id="report-dialog-advancedQueryPage">
    		<form action="" method="post" id="report-form-advancedQuery">
		  		<table cellpadding="1" cellspacing="1" border="0" style="padding: 10px;">
		  			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" name="businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>	    				
	    			</tr>
	    			<tr>
	    				<td>品牌:</td>
	    				<td><input id="report-brandid-advancedQuery" type="text" name="brand" style="width: 210px;"/></td>
	    			</tr>
	    			<tr>
	    				<td>采购员:</td>
	    				<td><input id="report-buyuser-advancedQuery" type="text" name="buyuser" style="width: 210px;"/></td>
	    			</tr>
	    			<tr>	    				
	    				<td>采购部门:</td>
	    				<td><input id="report-buydept-advancedQuery" type="text" name="buydept" style="width: 210px;" ondblclick="selectmoreparam('customer');"/></td>
	    			</tr>
	    			<tr>
	    				<td>小计列：</td>
	    				<td>
	    					<input type="checkbox" class="adgroupcols" value="goodsid"/>商品
	    					<input type="checkbox" class="adgroupcols" value="buydept"/>采购部门
	    					<input type="checkbox" class="adgroupcols" value="buyuser"/>采购员
	    					<input type="checkbox" class="adgroupcols" value="brand"/>品牌
	    					<input id="report-advancedQuery-groupcols" type="hidden" name="groupcols"/>
	    				</td>
	    			</tr>
		  		</table>
		  	</form>
    	</div> 
    	--%>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-arrivalOrderCostAccount").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-arrivalOrderCostAccount").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'supplierid',title:'供应商编码',sortable:true,width:65},
								  {field:'suppliername',title:'供应商名称',sortable:true,width:200},
								  {field:'buydeptid',title:'采购部门',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.buydeptname;
						        	}
								  },
								  {field:'buyuserid',title:'采购员',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.buyusername;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',sortable:true,width:60},
								  {field:'goodsname',title:'商品名称',width:130,sortable:true},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'brandid',title:'品牌名称',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
								  },
								  {field:'boxnum',title:'箱装量',width:50,align:'right'},
								  {field:'buyunitname',title:'单位',width:40},
								  {field:'buynum',title:'采购数量',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(null!=value){
						        			return value + rowData.buyunitname;
									  	}else{
										  	return "";
									  	}
						        	}
								  },
								  {field:'buyunitnum',title:'采购数量主单位',width:80,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
									  	if(null!=value){
					        				return formatterBigNumNoLen(value);
									  	}else{
										  	return "";
									  	}
						        	}
								  },
								  {field:'buyamount',title:'采购金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'buynotaxamount',title:'采购未税金额',resizable:true,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'costaccountprice',title:'核算成本价',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'costaccountamount',title:'核算金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'blanceamount',title:'差额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
					             ]]
				});
    			$("#report-datagrid-arrivalOrderCostAccount").datagrid({ 
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
					toolbar:'#report-toolbar-arrivalOrderCostAccount',
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
				
    			$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
					width:390,
					onlyLeafCheck:false,
					singleSelect:false
				});
		  		//品牌
		  		$("#report-query-brand").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:'130',
		   			onlyLeafCheck:false
		   		});
				$("#report-query-buyuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BUYER',
		    		width:130,
					singleSelect:false
				});
				$("#report-query-buydept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:130,
					onlyLeafCheck:false,
					singleSelect:false
				});
				$("#report-query-supplier").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:390,
					onlyLeafCheck:false,
					singleSelect:false
				});
				//回车事件
				controlQueryAndResetByKey("report-query-arrivalOrderCostAccount","report-reload-arrivalOrderCostAccount");

    			$(".groupcols").click(function(){
    				var cols = "";
					$("#report-query-groupcols").val("");
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
				//查询
				$("#report-query-arrivalOrderCostAccount").click(function(){
					//把form表单的name序列化成JSON对象
      				setColumn(0);
		      		var queryJSON = $("#report-query-form-arrivalOrderCostAccount").serializeJSON();
		      		$("#report-datagrid-arrivalOrderCostAccount").datagrid({
		      			url: 'report/buy/showArrivalOrderCostAccountReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-arrivalOrderCostAccount").click(function(){
					$("#report-query-buyuser").widget("clear");
					$("#report-query-buydept").widget("clear");
					$("#report-query-supplier").widget("clear");
					
					$(".groupcols").each(function(){
    					if($(this).attr("checked")){
    						$(this)[0].checked = false;
    					}
					});
					$("#report-query-groupcols").val("");

					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "supplierid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "suppliername");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsname");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "barcode");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "brandid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buyuserid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buydeptid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "boxnum");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buyunitname");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buynum");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "costaccountprice");
					
					$("#report-query-form-arrivalOrderCostAccount").form("reset");
		       		$("#report-datagrid-arrivalOrderCostAccount").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-arrivalOrderCostAccountPage").Excel('export',{
					queryForm: "#report-query-form-arrivalOrderCostAccount", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'采购进货差额报表',
			 		url:'report/buy/exportArrivalOrderCostAccountReportData.do'
				});

				<%--
    			$(".adgroupcols").click(function(){
    				var cols = "";
					$("#report-advancedQuery-groupcols").val("");
    				$(".adgroupcols").each(function(){
    					if($(this).attr("checked")){
    						if(cols==""){
    							cols = $(this).val();
    						}else{
    							cols += ","+$(this).val();
    						}
    						$("#report-advancedQuery-groupcols").val(cols);
    					}
					});
    			});
				//高级查询
				$("#report-advancedQuery-arrivalOrderCostAccountReportPage").click(function(){
					$("#report-dialog-advancedQueryPage").dialog({
						maximizable:true,
						resizable:true,
						title: '采购进货差额报表高级查询',
					    width: 500,
					    height: 250,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'查询',
								handler:function(){
									searchAdvancedQueryForm();
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-advancedQuery-groupcols").val("");
							  		$("#report-brandid-advancedQuery").widget('clear');
							  		$("#report-buydept-advancedQuery").widget('clear');
							  		$("#report-buyuser-advancedQuery").widget('clear');
								    $("#report-form-advancedQuery").form('reset');
								    $(".adgroupcols").each(function(){
										if($(this).attr("checked")){
											$(this)[0].checked = false;
										}
									});


									$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsid");
									$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsname");
									$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "brand");
									$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buyuser");
									$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buydept");

									$("#report-datagrid-arrivalOrderCostAccount").datagrid('loadData',{total:0,rows:[]});
								}
							}
							],
						onClose:function(){
						}
					});
				});

		  		//品牌
		  		$("#report-brandid-advancedQuery").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbrandid-advancedQuery").val("");
		   				$("input[class='adgroupcols'][value='brand']").each(function () {
				            $(this).attr("checked", "checked");
				        });
		   			},
		  			onClear:function(){
		  				$("input[class='adgroupcols'][value='brand']").each(function () {
				            $(this)[0].checked = false;
				        });
		  			}
		   		});
		   		//采购部门
		   		$("#report-buydept-advancedQuery").widget({
		   			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbuydept-advancedQuery").val("");
		   				$("input[class='adgroupcols'][value='buydept']").each(function () {
				            $(this).attr("checked", "checked");
				        });
		   			},
		  			onClear:function(){
		  				$("input[class='adgroupcols'][value='buydept']").each(function () {
				            $(this)[0].checked = false;
				        });
		  			}
		   		});
		   		//采购员
		   		$("#report-buyuser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbuyuser-advancedQuery").val("");
		   				$("input[class='adgroupcols'][value='buyuser']").each(function () {
				            $(this).attr("checked", "checked");
				        });
		   			},
		  			onClear:function(){
		  				$("input[class='adgroupcols'][value='buyuser']").each(function () {
				            $(this)[0].checked = false;
				        });
		  			}
		   		});
		   		//查询
				$("#report-query-selectmoreparam").click(function(){
		      		var selectmoreQueryJSON = $("#report-form-selectmoreparam").serializeJSON();
		      		$("#report-datagrid-selectmoreparam").datagrid('load',selectmoreQueryJSON);
				});
				//重置
				$("#report-reload-selectmoreparam").click(function(){
			  		$("#report-brandid-advancedQuery").widget('clear');
			  		$("#report-buydept-advancedQuery").widget('clear');
			  		$("#report-buyuser-advancedQuery").widget('clear');
				    $("#report-form-advancedQuery").form('reset');
					$("#report-datagrid-selectmoreparam").datagrid("reload");
				});
				--%>
    		});
    		<%--
    		function searchAdvancedQueryForm(){
    			$("#report-advancedQuery-groupcols").val("");
    			var cols="";
				$(".adgroupcols").each(function(){
					if($(this).attr("checked")){
						if(cols==""){
							cols = $(this).val();
						}else{
							cols += ","+$(this).val();
						}
						$("#report-advancedQuery-groupcols").val(cols);
					}
				});
      			setColumn(1);
      			var adQueryJSON = $("#report-form-advancedQuery").serializeJSON();
      			$("#report-datagrid-arrivalOrderCostAccount").datagrid({
          			url:'report/buy/showArrivalOrderCostAccountReportData.do',
          			pageNumber:1,
     				queryParams:adQueryJSON
          		}).datagrid("columnMoving");
          		$("#report-export-arrivalOrderCostAccountReportPage").Excel('export',{
    				queryForm: "#report-form-advancedQuery",
    		 		type:'exportUserdefined',
    		 		name:'采购进货差额报表',
    		 		url:'report/buy/exportArrivalOrderCostAccountReportData.do'
    			});
    			/*
    		  	$(".groupcols").each(function(){
    				if($(this).attr("checked")){
    					$(this)[0].checked = false;
    				}
    			});
    			$("#report-query-groupcols").val("");
    	  		$("#report-brandid-advancedQuery").widget('clear');
    	  		$("#report-buydept-advancedQuery").widget('clear');
    	  		$("#report-buyuser-advancedQuery").widget('clear');
    		    $("#report-form-advancedQuery").form('reset');
    		    $(".adgroupcols").each(function(){
    				if($(this).attr("checked")){
    					$(this)[0].checked = false;
    				}
    			});
    			*/
          		$("#report-dialog-advancedQueryPage").dialog('close');
      		}
      		--%>
    		function setColumn(qtype){
        		if(qtype==null || typeof(qtype)=="undefined"){
            		qtype=0;
        		}else if(qtype!=1){
            		type=0;
        		}
    			var cols = "";
    			if(qtype==1){
        			cols=$("#report-advancedQuery-groupcols").val();
    			}else{
        			cols=$("#report-query-groupcols").val();
    			}
    			if(cols!=""){
    				$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "supplierid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "suppliername");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "barcode");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "brandid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "buyuserid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "buydeptid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "boxnum");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "buyunitname");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "buynum");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('hideColumn', "costaccountprice");
				}
				else{
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "supplierid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "suppliername");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsname");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "barcode");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "brandid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buyuserid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buydeptid");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "boxnum");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buyunitname");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buynum");
					$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "costaccountprice");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=="brandid"){
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "brandid");
						//$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buydeptid");
					}else if(col=="buyuserid"){
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buyuserid");
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buydeptid");
					}else if(col=="buydeptid"){
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "buydeptid");
					}else if(col=="goodsid"){
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsid");
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "goodsname");
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "barcode");
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "brandid");
					}else if(col=='supplierid'){
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "supplierid");
						$("#report-datagrid-arrivalOrderCostAccount").datagrid('showColumn', "suppliername");						
					} 
				}
    		}
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-arrivalOrderCostAccount").datagrid('getChecked');
        		if(null==rows || rows.length==0){
        			var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-arrivalOrderCostAccount").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var boxnum = 0;
        		var buynum = 0;
        		var buyunitnum=0;
        		var buyamount = 0;
        		var buynotaxamount = 0;
        		var costaccountprice=0;
        		var blanceamount = 0;
        		
        		for(var i=0;i<rows.length;i++){
        			boxnum = Number(boxnum)+Number(rows[i].boxnum == undefined ? 0 : rows[i].boxnum);
        			buynum = Number(buynum)+Number(rows[i].buynum == undefined ? 0 : rows[i].buynum);
        			buyunitnum = Number(buyunitnum)+Number(rows[i].buyunitnum == undefined ? 0 : rows[i].buyunitnum);
        			buyamount = Number(buyamount)+Number(rows[i].buyamount == undefined ? 0 : rows[i].buyamount);
        			buynotaxamount = Number(buynotaxamount)+Number(rows[i].buynotaxamount == undefined ? 0 : rows[i].buynotaxamount);
        			costaccountprice = Number(costaccountprice)+Number(rows[i].costaccountprice == undefined ? 0 : rows[i].costaccountprice);
        			blanceamount = Number(blanceamount)+Number(rows[i].blanceamount == undefined ? 0 : rows[i].blanceamount);        			
        		}
        		var foot=[{buyusername:'选中合计',buyunitname:'',boxnum:boxnum,buynum:buynum,buyunitnum:buyunitnum,buyamount:buyamount,
            				buynotaxamount:buynotaxamount,costaccountprice:costaccountprice,blanceamount:blanceamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-arrivalOrderCostAccount").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
