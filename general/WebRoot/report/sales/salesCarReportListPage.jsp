<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>直营销售报表</title>
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
    	<table id="report-datagrid-carReport"></table>
    	<div id="report-toolbar-carReport" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-carReportPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
            <security:authorize url="/report/sales/carReportExport.do">
                <a href="javaScript:void(0);" id="report-autoExport-carReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true">全局导出</a>
            </security:authorize>
			<security:authorize url="/report/sales/carReportPrint.do">
				<a href="javaScript:void(0);" id="report-export-carReportPrint" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
			</security:authorize>
    	</div>
    	<div style="display:none">
	    	<div id="report-dialog-advancedQueryDialog" >
	    		<form action="" id="report-query-form-carReport" method="post">
		    		<table >
		    			<tr>
		    				<td>业务日期:</td>
		    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
		    			</tr>
		    			<tr>
			  				<td>客户:</td>
			  				<td><input type="text" name="customerid" id="report-customernamemore-advancedQuery" style="width: 225px;"/>
			  				</td>
			  			</tr>
			  			<tr>
		    				<td>总店:</td>
			  				<td><input type="text" name="pcustomerid" id="report-pcustomernamemore-advancedQuery" style="width: 225px;"/></td>
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
			  				<td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
			  			</tr>
			  			<tr>
		    				<td>销售区域:</td>
			  				<td><input type="text" name="salesarea" id="report-salesarea-advancedQuery"/></td>
			  			</tr>
			  			<tr>
			  				<td>客户分类:</td>
			  				<td><input type="text" name="customersort" id="report-customersort-advancedQuery"/></td>
		    			</tr>
						<tr>
							<td>车销业务员:</td>
							<td><input type="text" name="caruser" id="report-caruser-advancedQuery"/></td>
						</tr>
		    			<tr>
		    				<td>小计列：</td>
		    				<td>
		    					<div style="margin-top:1px">
			    					<div style="line-height: 25px;">
				    					<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid">客户</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="pcustomerid">总店</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customersort">客户分类</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser">客户业务员</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesarea">销售区域</label>
		    						</div>
				    				<div style="line-height: 25px; margin-top: 1px;">
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept">销售部门</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid">商品</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branddept">品牌部门</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branduser">品牌业务员</label>
                                        <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid">品牌</label>
									</div>
									<div style="line-height: 25px; margin-top: 1px;">
										<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="caruser">车销业务员</label>
										<label class="divtext"><input id="report-query-groupcols" type="hidden" name="groupcols"></label>
									</div>
		    					</div>
		    				</td>
		    			</tr>
		    			<tr>
		    				<td>状态：</td>
		    				<td>
		    					<div style="margin-top:1px">
			    					<div style="line-height: 25px;">
                                        <label class="divtext"><input type="checkbox" class="isClose checkbox1" value="oppaudit"/>未审核</label>
                                        <label class="divtext"><input type="checkbox" class="isClose checkbox1" value="audit"/>已审核</label>
				    					<input id="report-query-isClose" type="hidden" name="isClose"/>
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
    		var initQueryJSON = $("#report-query-form-carReport").serializeJSON();
    		$(function(){
                $("#report-export-carReportPrint").click(function () {
                    // var tablewidth=0;
                    // var arrays= $("#report-datagrid-carReport").datagrid('options').columns;
                    // for(var i=0;i<arrays[0].length;i++){
                    //     var filed=arrays[0][i];
                    //     if(!filed.hidden){
                    //         tablewidth+=filed.width;
						// }
                    // }
                    //
                    // console.log(dpi);
                    // console.log(tablewidth);

                    var msg = "";
                    printByAnalyse("车销销售报表", "report-datagrid-carReport", "report/sales/printSalesCarReportList.do", msg);
                });
                $("#report-autoExport-carReportPage").click(function(){
                    var queryJSON = $("#report-query-form-carReport").serializeJSON();
                    //获取排序规则
                    var objecr  = $("#report-datagrid-carReport").datagrid("options");
                    if(null != objecr.sortName && null != objecr.sortOrder ){
                        queryJSON["sort"] = objecr.sortName;
                        queryJSON["order"] = objecr.sortOrder;
                    }
                    var queryParam = JSON.stringify(queryJSON);
                    var url = "report/sales/exportCarReportData.do";
                    exportByAnalyse(queryParam,"车销销售报表","report-datagrid-carReport",url);
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
    			
    			$(".isClose").click(function(){
    				var iscls = "";
					$("#report-query-isClose").val(iscls);
    				$(".isClose").each(function(){
    					if($(this).attr("checked")){
    						if(iscls==""){
    							iscls = $(this).val();
    						}else{
    							iscls += ","+$(this).val();
    						}
    						$("#report-query-isClose").val(iscls);
    					}
					});
    			});
    			var tableColumnListJson = $("#report-datagrid-carReport").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'customerid',title:'客户编号',sortable:true,width:60},
						{field:'customername',title:'客户名称',width:225},
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
						{field:'caruser',title:'车销业务员',sortable:true,width:70,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.carusername;
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
						},
						{field:'notaxamount',title:'未税金额',resizable:true,sortable:true,align:'right',isShow:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'tax',title:'税额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'costamount',title:'成本金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'marginamount',title:'毛利金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'realrate',title:'毛利率',resizable:true,align:'right',isShow:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=0){
									return formatterMoney(value)+"%";
								}else{
									return "";
								}
							}
						}
					]]
				});
    			$("#report-datagrid-carReport").datagrid({ 
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
					toolbar:'#report-toolbar-carReport',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							countTotalAmount();
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
		   			width:'225',
		   			onlyLeafCheck:true
		  		});
		  		$("#report-pcustomernamemore-advancedQuery").widget({
		  			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		   			singleSelect:false,
		   			width:'225',
		   			onlyLeafCheck:true
		  		});
		  		$("#report-goodsid-advancedQuery").widget({
		  			referwid:'RL_T_BASE_GOODS_INFO',
		   			singleSelect:false,
		   			width:'225',
		   			onlyLeafCheck:true
		  		});
		  		$("#report-customersort-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
		   			singleSelect:true,
		   			width:'225',
		   			onlyLeafCheck:false
		  		});
		  		$("#report-salesarea-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_AREA',
		   			singleSelect:false,
		   			width:'225',
		   			onlyLeafCheck:false
		  		});
				$("#report-caruser-advancedQuery").widget({
					referwid:'RL_T_BASE_PERSONNEL_CARUSER',
					singleSelect:false,
					width:'225',
					onlyLeafCheck:false
				});
		  		
		  		//品牌
		  		$("#report-brandid-advancedQuery").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:'225',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbrandid-advancedQuery").val("");
		   			}
		   		});
		   		//品牌部门
		   		$("#report-branddept-advancedQuery").widget({
		   			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		   			singleSelect:false,
		   			width:'225',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranddept-advancedQuery").val("");
		   			}
		   		});
		   		//品牌业务员
		   		$("#report-branduser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		   			singleSelect:false,
		   			width:'225',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranduser-advancedQuery").val("");
		   			}
		   		});
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-carReportPage","");

				//高级查询
				$("#report-advancedQuery-carReportPage").click(function(){
					$("#report-dialog-advancedQueryDialog").dialog({
						maximizable:true,
						resizable:true,
						title: '直营销售报表查询',
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
						      		var queryJSON = $("#report-query-form-carReport").serializeJSON();
						      		$("#report-datagrid-carReport").datagrid({
						      			url:'report/sales/getSalesCarReportList.do',
						      			pageNumber:1,
				   						queryParams:queryJSON
						      		}).datagrid("columnMoving");

						      		$("#report-dialog-advancedQueryDialog").dialog('close');
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-query-form-carReport").form("reset");
									$(".groupcols").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-groupcols").val("");
									$(".isClose").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-isClose").val("");
									$("#report-customernamemore-advancedQuery").widget("clear");
									$("#report-pcustomernamemore-advancedQuery").widget("clear");
									$("#report-customersort-advancedQuery").widget("clear");
									$("#report-brandid-advancedQuery").widget("clear");
									$("#report-branddept-advancedQuery").widget("clear");
									$("#report-branduser-advancedQuery").widget("clear");
									$("#report-salesarea-advancedQuery").widget("clear");
									$("#report-goodsid-advancedQuery").widget("clear");
									$("#report-caruser-advancedQuery").widget('clear');
									$("#report-datagrid-carReport").datagrid("loadData",[]);
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
	    			$("#report-datagrid-carReport").datagrid('hideColumn', "customerid");
					$("#report-datagrid-carReport").datagrid('hideColumn', "customername");
					$("#report-datagrid-carReport").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-carReport").datagrid('hideColumn', "salesuser");
					$("#report-datagrid-carReport").datagrid('hideColumn', "customersort");
					$("#report-datagrid-carReport").datagrid('hideColumn', "salesarea");
					$("#report-datagrid-carReport").datagrid('hideColumn', "salesdept");
					$("#report-datagrid-carReport").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-carReport").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-carReport").datagrid('hideColumn', "barcode");
					$("#report-datagrid-carReport").datagrid('hideColumn', "brandid");
					$("#report-datagrid-carReport").datagrid('hideColumn', "branduser");
					$("#report-datagrid-carReport").datagrid('hideColumn', "branddept");
					$("#report-datagrid-carReport").datagrid('hideColumn', "caruser");
				}
				else{
					$("#report-datagrid-carReport").datagrid('showColumn', "customerid");
					$("#report-datagrid-carReport").datagrid('showColumn', "customername");
					$("#report-datagrid-carReport").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-carReport").datagrid('showColumn', "salesuser");
					//$("#report-datagrid-carReport").datagrid('showColumn', "salesarea");
					//$("#report-datagrid-carReport").datagrid('showColumn', "salesdept");
					$("#report-datagrid-carReport").datagrid('showColumn', "goodsid");
					$("#report-datagrid-carReport").datagrid('showColumn', "goodsname");
					$("#report-datagrid-carReport").datagrid('showColumn', "barcode");
					$("#report-datagrid-carReport").datagrid('showColumn', "brandid");
					$("#report-datagrid-carReport").datagrid('hideColumn', "branduser");
					//$("#report-datagrid-carReport").datagrid('showColumn', "branddept");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=='customerid'){
						$("#report-datagrid-carReport").datagrid('showColumn', "customerid");
						$("#report-datagrid-carReport").datagrid('showColumn', "customername");
						$("#report-datagrid-carReport").datagrid('showColumn', "pcustomerid");
						$("#report-datagrid-carReport").datagrid('showColumn', "salesuser");
						$("#report-datagrid-carReport").datagrid('showColumn', "customersort");
					}else if(col=="pcustomerid"){
						$("#report-datagrid-carReport").datagrid('showColumn', "pcustomerid");
					}else if(col=="salesuser"){
						$("#report-datagrid-carReport").datagrid('showColumn', "salesuser");
					}else if(col=="salesarea"){
						$("#report-datagrid-carReport").datagrid('showColumn', "salesarea");
					}else if(col=="salesdept"){
						$("#report-datagrid-carReport").datagrid('showColumn', "salesdept");
					}else if(col=="goodsid"){
						$("#report-datagrid-carReport").datagrid('showColumn', "goodsid");
						$("#report-datagrid-carReport").datagrid('showColumn', "goodsname");
						$("#report-datagrid-carReport").datagrid('showColumn', "barcode");
						$("#report-datagrid-carReport").datagrid('showColumn', "brandid");
					}else if(col=="brandid"){
						$("#report-datagrid-carReport").datagrid('showColumn', "brandid");
					}else if(col=="branduser"){
						$("#report-datagrid-carReport").datagrid('showColumn', "branduser");
					}else if(col=="branddept"){
						$("#report-datagrid-carReport").datagrid('showColumn', "branddept");
					}else if(col=="customersort"){
						$("#report-datagrid-carReport").datagrid('showColumn', "customersort");
					}else if(col=="caruser"){
						$("#report-datagrid-carReport").datagrid('showColumn', "caruser");
					}
				}
    		}

    	function countTotalAmount(){
    		var rows =  $("#report-datagrid-carReport").datagrid('getChecked');
    		var unitnum = 0;
    		var taxamount = 0;
    		var notaxamount=0;
    		var tax = 0;
			var costamount = 0;
			var marginamount = 0;
			var realrate = 0;
    		for(var i=0;i<rows.length;i++){
    			unitnum = Number(unitnum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
				costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
				marginamount = Number(marginamount)+Number(rows[i].marginamount == undefined ? 0 : rows[i].marginamount);
    		}
			if(Number(taxamount) != 0){
				realrate = marginamount/taxamount*Number(100);
			}
    		var obj={unitnum:unitnum,taxamount:taxamount,notaxamount:notaxamount,tax:tax,costamount:costamount,marginamount:marginamount,realrate:realrate};

			var col = "";
			var cols = $("#report-query-groupcols").val();
			if(cols == ""){
				cols = "customerid";
			}
			var colarr = cols.split(",");
			if(colarr[0]=="pcustomerid"){
				col = "pcustomername";
			}else if(colarr[0]=='customerid'){
				col = "customername";
			}else if(colarr[0]=="salesuser"){
				col = "salesusername";
			}else if(colarr[0]=="salesarea"){
				col = "salesareaname";
			}else if(colarr[0]=="salesdept"){
				col = "salesdeptname";
			}else if(colarr[0]=="goodsid"){
				col = "goodsname";
			}else if(colarr[0]=="brandid"){
				col = "brandname";
			}else if(colarr[0]=="branduser"){
				col = "brandusername";
			}else if(colarr[0]=="branddept"){
				col = "branddeptname";
			}else if(colarr[0]=="customersort"){
				col = "customersortname";
			}else if(colarr[0]=="caruser"){
				col = "carusername";
			}
			if(col != ""){
				obj[col] = '选中合计';
			}else{
				obj['goodsname'] = '选中合计';
			}
			var foot=[];
			foot.push(obj);
			if(null!=SR_footerobject){
        		foot.push(SR_footerobject);
    		}
    		$("#report-datagrid-carReport").datagrid("reloadFooter",foot);
    	}
    	</script>
  </body>
</html>
