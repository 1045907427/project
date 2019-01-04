<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>要货金额报表</title>
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
    	<table id="report-datagrid-demandReport"></table>
    	<div id="report-toolbar-demandReport" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-demandReportPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
            <security:authorize url="/report/sales/demandReportExport.do">
                <a href="javaScript:void(0);" id="report-autoExport-demandReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true">全局导出</a>
            </security:authorize>
			<security:authorize url="/report/sales/demandReportPrint.do">
				<a href="javaScript:void(0);" id="report-print-demandReportPage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
			</security:authorize>
            <div id="dialog-autoexport"></div>
    	</div>
    	<div style="display:none">
    	<div id="report-dialog-advancedQueryDialog" >
    		<form action="" id="report-query-form-demandReport" method="post">
    		<table cellpadding="0" style="margin-left:2px;margin-top: 2px;">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:98px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />到<input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    			</tr>
    			<tr>
	  				<td>客户:</td>
	  				<td><input type="text" name="customerid" id="report-customernamemore-advancedQuery" style="width: 210px;"/>
	  				</td>
	  			</tr>
	  			<tr>
	  				<td>客户业务员:</td>
		    		<td><input type="text" id="report-salesuser-advancedQuery"  name="salesuser" /></td>
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
	  				<td>销售部门:</td>
                    <td><input type="text" id="report-salesdept-advancedQuery"   name="salesdept" /></td>
	  			</tr>
	  			<tr>
	  				<td>客户分类:</td>
	  				<td><input type="text" name="customersort" id="report-customersort-advancedQuery" style="width: 210px;"/></td>
    			</tr>
				<tr>
					<td>商品分类:</td>
					<td><input type="text" name="goodssort" id="report-goodssort-advancedQuery" style="width: 210px;"/></td>
				</tr>
    			<tr>
    				<td>小计列：</td>
    				<td>
    					<div style="margin-top:1px">
	    					<div style="line-height: 25px;">
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="customerid"/>客户</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="customersort"/>客户分类</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="salesuser"/>客户业务员</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="salesarea"/>销售区域</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="brandid"/>品牌</label>
    						</div>
		    				<div style="line-height: 25px; margin-top: 1px;">
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="goodsid"/>商品</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="branddept"/>品牌部门</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="branduser"/>品牌业务员</label>
                                <label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="salesdept"/>销售部门</label>
								<label class="checkbox1"><input type="checkbox" class="groupcols divtext" value="goodssort"/>商品分类</label>
                                <label class="checkbox1"><input id="report-query-groupcols" type="hidden" name="groupcols"/>
	    					</div>
    					</div>
    				</td>
    			</tr>
    			<tr>
    				<td>状态：</td>
    				<td>
    					<div style="margin-top:1px">
	    					<div style="line-height: 25px;">
                                <label class="checkbox1"><input type="checkbox" class="status divtext" value="0"/>未生成订单</label>
                                <label class="checkbox1"><input type="checkbox" class="status divtext" value="1"/>已生成订单</label>
		    					<input id="report-query-status" type="hidden" name="status"/>
    						</div>
    					</div>
    				</td>
    			</tr>
    		</table>
    		</form>
    	</div>
    	</div>
    	<script type="text/javascript">
    		var SD_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-demandReport").serializeJSON();
    		$(function(){
                $("#report-print-demandReportPage").click(function () {
                    var msg = "";
                    printByAnalyse("要货金额报表", "report-datagrid-demandReport", "report/sales/printSalesDemandReportList.do", msg);
                });
                $("#report-autoExport-demandReportPage").click(function(){
                    var queryJSON = $("#report-query-form-demandReport").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-demandReport").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/sales/exportDemandReportData.do";
                    exportByAnalyse(queryParam,"要货金额报表","report-datagrid-demandReport",url);
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
    			
    			$(".status").click(function(){
    				var stus = "";
					$("#report-query-status").val(stus);
    				$(".status").each(function(){
    					if($(this).attr("checked")){
    						if(stus==""){
    							stus = $(this).val();
    						}else{
    							stus += ","+$(this).val();
    						}
    						$("#report-query-status").val(stus);
    					}
					});
    			});
    			var tableColumnListJson = $("#report-datagrid-demandReport").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:210},
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
								  {field:'branduser',title:'品牌业务员',sortable:true,width:70,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandusername;
						        	}
								  },
								  {field:'branddept',title:'品牌部门',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',sortable:true,width:60},
								  {field:'goodsname',title:'商品名称',sortable:true,width:250},
                                  {field:'goodssort',title:'商品分类',sortable:true,width:60,
                                      formatter:function(value,rowData,rowIndex){
                                          return rowData.goodssortname;
                                      }
								  },
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
								  {field:'taxprice',title:'单价',width:60,align:'right',sortable:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'unitnum',title:'数量',width:60,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'taxamount',title:'金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
								  /*{field:'notaxamount',title:'未税金额',resizable:true,sortable:true,align:'right',isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'tax',title:'税额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }*/
					        ]]
				});
    			$("#report-datagrid-demandReport").datagrid({ 
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
					toolbar:'#report-toolbar-demandReport',
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SD_footerobject = footerrows[0];
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
                $("#report-goodssort-advancedQuery").widget({
                    referwid:'RL_T_BASE_GOODS_WARESCLASS',
                    singleSelect:false,
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
		   			onlyLeafCheck:true
		   		});
		   		
		   		//品牌部门
		  		$("#report-branddept-advancedQuery").widget({
		   			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true
		   		});
		   		//品牌业务员
		   		$("#report-branduser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true
		   		});
		   		 //销售部门
                $("#report-salesdept-advancedQuery").widget({
	    			name:'t_sales_order',
					col:'salesdept',
	    			width:210,
					singleSelect:true,
					onlyLeafCheck:true
	    		});
              //客户业务员
				$("#report-salesuser-advancedQuery").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:210,
					singleSelect:false
				});
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-demandReportPage","");
				//高级查询
				$("#report-advancedQuery-demandReportPage").click(function(){
					$("#report-dialog-advancedQueryDialog").dialog({
						maximizable:true,
						resizable:true,
						title: '要货金额报表查询',
						top:30,
					    width: 500,
					    height: 430,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'确定',
								handler:function(){
									setColumn();
									//把form表单的name序列化成JSON对象
						      		var queryJSON = $("#report-query-form-demandReport").serializeJSON();
						      		$("#report-datagrid-demandReport").datagrid({
						      			url:'report/sales/getSalesDemandReportList.do',
						      			pageNumber:1,
				   						queryParams:queryJSON
						      		}).datagrid("columnMoving");

						      		$("#report-dialog-advancedQueryDialog").dialog('close');
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-query-form-demandReport").form("reset");
									$(".groupcols").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$(".status").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-groupcols").val("");
									$("#report-customernamemore-advancedQuery").widget("clear");
									$("#report-salesarea-advancedQuery").widget('clear');
									$("#report-customersort-advancedQuery").widget("clear");
									$("#report-branddept-advancedQuery").widget('clear');
									$("#report-branduser-advancedQuery").widget('clear');
									$("#report-brandid-advancedQuery").widget("clear");
									$("#report-goodsid-advancedQuery").widget("clear");
									$("#report-salesuser-advancedQuery").widget("clear");
									$("#report-salesdept-advancedQuery").widget("clear");
                                    $("#report-goodssort-advancedQuery").widget("clear");
									$("#report-datagrid-demandReport").datagrid("loadData",[]);
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
	    			$("#report-datagrid-demandReport").datagrid('hideColumn', "customerid");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "customername");
					//$("#report-datagrid-demandReport").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "salesuser");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "customersort");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "salesarea");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "salesdept");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "barcode");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "brandid");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "branduser");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "branddept");
                    $("#report-datagrid-demandReport").datagrid('hideColumn', "goodssort");
				}
				else{
					$("#report-datagrid-demandReport").datagrid('showColumn', "customerid");
					$("#report-datagrid-demandReport").datagrid('showColumn', "customername");
					//$("#report-datagrid-demandReport").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-demandReport").datagrid('showColumn', "salesuser");
					//$("#report-datagrid-demandReport").datagrid('showColumn', "salesarea");
					//$("#report-datagrid-demandReport").datagrid('showColumn', "salesdept");
					$("#report-datagrid-demandReport").datagrid('showColumn', "goodsid");
					$("#report-datagrid-demandReport").datagrid('showColumn', "goodsname");
					$("#report-datagrid-demandReport").datagrid('showColumn', "barcode");
					$("#report-datagrid-demandReport").datagrid('showColumn', "brandid");
					$("#report-datagrid-demandReport").datagrid('hideColumn', "branduser");
					$("#report-datagrid-demandReport").datagrid('showColumn', "goodssort");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=='customerid'){
						$("#report-datagrid-demandReport").datagrid('showColumn', "customerid");
						$("#report-datagrid-demandReport").datagrid('showColumn', "customername");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "pcustomerid");
						$("#report-datagrid-demandReport").datagrid('showColumn', "salesuser");
						$("#report-datagrid-demandReport").datagrid('showColumn', "customersort");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "salesarea");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "salesdept");
					}
					//else if(col=="pcustomerid"){
					//	$("#report-datagrid-demandReport").datagrid('showColumn', "pcustomerid");
					//}
					else if(col=="salesuser"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "salesuser");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "salesarea");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "salesdept");
					}else if(col=="salesarea"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "salesarea");
					}else if(col=="salesdept"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "salesdept");
					}else if(col=="goodsid"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "goodsid");
						$("#report-datagrid-demandReport").datagrid('showColumn', "goodsname");
						$("#report-datagrid-demandReport").datagrid('showColumn', "barcode");
						$("#report-datagrid-demandReport").datagrid('showColumn', "brandid");
                        $("#report-datagrid-demandReport").datagrid('showColumn', "goodssort");
					}else if(col=="brandid"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "brandid");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "branddept");
					}else if(col=="branduser"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "branduser");
						//$("#report-datagrid-demandReport").datagrid('showColumn', "branddept");
					}else if(col=="branddept"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "branddept");
					}else if(col=="customersort"){
						$("#report-datagrid-demandReport").datagrid('showColumn', "customersort");
					}else if(col=="goodssort"){
                        $("#report-datagrid-demandReport").datagrid('showColumn', "goodssort");
					}
				}
    		}

    		function countTotalAmount(){
	    		var rows =  $("#report-datagrid-demandReport").datagrid('getChecked');
	    		if(null==rows || rows.length==0){
	    			var foot=[];
	    			if(null!=SD_footerobject){
		        		foot.push(SD_footerobject);
		    		}
	    			$("#report-datagrid-demandReport").datagrid("reloadFooter",foot);
	        		return false;
	    		}
	    		var unitnum = 0;
	    		var taxamount = 0;
	    		var notaxamount=0;
	    		var tax = 0;
	    		for(var i=0;i<rows.length;i++){
	    			unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
	    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
	    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
	    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
	    		}
	    		var foot=[{goodsname:'选中合计',unitnum:unitnum,taxamount:taxamount,notaxamount:notaxamount,tax:tax}];
	    		if(null!=SD_footerobject){
	        		foot.push(SD_footerobject);
	    		}
	    		$("#report-datagrid-demandReport").datagrid("reloadFooter",foot);
	    	}
    	</script>
  </body>
</html>
