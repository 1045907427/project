<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门采购销售汇总统计报表</title>
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
    	<table id="report-datagrid-buySalesBillCountReport"></table>
    	<div id="report-toolbar-buySalesBillCountReport" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/sales/buySalesBillCountReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-buySalesBillCountReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-buySalesBillCountReport" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>业务员:</td>
    				<td><input id="report-query-branduser" type="text" name="branduser" style="width: 130px;"/></td>
    				<td>品牌部门:</td>
    				<td><input id="report-query-branddept" type="text" name="branddept" style="width: 130px;"/></td>
    			</tr>    			
    			<tr>
	  				<td>商品:</td>
	  				<td><input type="text" name="goodsid" id="report-query-goodsid" style="width:210px;"/></td>
	  				<td>品牌:</td>
	  				<td><input type="text" name="brand" id="report-query-brand"/></td>
    				<td colspan="2">
    					&nbsp;
    				</td>
    			</tr>   			
    			<tr>
	  				<td>供应商:</td>
	  				<td colspan="3"><input type="text" name="supplierid" id="report-query-supplierid" style="width:410px;"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-query-buySalesBillCountReport" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-buySalesBillCountReport" class="button-qr">重置</a>
						<%--
						<security:authorize url="/report/sales/buySalesBillCountReportAdvancedQuery.do">
							<a href="javaScript:void(0);" id="report-advancedQuery-buySalesBillCountReportReportPage" class="easyui-linkbutton" iconCls="icon-undo" plain="true" title="高级查询">高级查询</a>
						</security:authorize>
						 --%>
    				</td>
    			</tr>
    			<tr>
    				<td>小计列：</td>
    				<td colspan="6">
    					<input type="checkbox" class="groupcols checkbox1" value="goodsid" id="goodsid"/>
                        <label class="divtext" for="goodsid">商品</label>
    					<input type="checkbox" class="groupcols checkbox1" value="branddept" id="branddept" checked="checked"/>
                        <label class="divtext" for="branddept" >品牌部门</label>
                        <input type="checkbox" class="groupcols checkbox1" value="branduser" id="branduser"/>
                        <label class="divtext" for="branduser">业务员</label>
                        <input type="checkbox" class="groupcols checkbox1" value="brand" id="brand"/>
                        <label class="divtext" for="brand">品牌</label>
                        <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="supplierid"/>
                        <label class="divtext" for="supplierid">供应商</label>
                        <input id="report-query-groupcols" type="hidden" name="groupcols" value="branddept"/>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
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
	    				<td>业务员:</td>
	    				<td><input id="report-branduser-advancedQuery" type="text" name="branduser" style="width: 210px;"/></td>
	    			</tr>
	    			<tr>	    				
	    				<td>品牌部门:</td>
	    				<td><input id="report-branddept-advancedQuery" type="text" name="branddept" style="width: 210px;" ondblclick="selectmoreparam('customer');"/></td>
	    			</tr>
	    			<tr>	    				
	    				<td>品牌部门:</td>
	    				<td><input id="report-supplierid-advancedQuery" type="text" name="supplierid" style="width: 210px;" /></td>
	    			</tr>
	    			<tr>
	    				<td>小计列：</td>
	    				<td>
	    					<input type="checkbox" class="adgroupcols" value="goodsid"/>商品
	    					<input type="checkbox" class="adgroupcols" value="branddept"/>品牌部门
	    					<input type="checkbox" class="adgroupcols" value="branduser"/>业务员
	    					<input type="checkbox" class="adgroupcols" value="brand"/>品牌
    						<input type="checkbox" class="groupcols" value="supplierid"/>供应商
	    					<input id="report-advancedQuery-groupcols" type="hidden" name="groupcols"/>
	    				</td>
	    			</tr>
		  		</table>
		  	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-buySalesBillCountReport").serializeJSON();
			var SR_footerobject  = null;
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-buySalesBillCountReport").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[ 
					              {field:'supplierid',title:'供应商编码',width:70,align:'left'},
								  {field:'suppliername',title:'供应商名称',width:180,align:'left',sortable:true,isShow:true},
								  {field:'branddept',title:'品牌部门',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
								  },
								  {field:'branduser',title:'业务员',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandusername;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',sortable:true,width:60},
								  {field:'goodsname',title:'商品名称',width:130},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'brand',title:'品牌名称',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
								  },
								  {field:'buynum',title:'采购数量',width:80,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'buyamount',title:'采购金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'buynotaxamount',title:'采购未税金额',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'buybillcount',title:'采购单据数',width:70,sortable:true,align:'right',
									  	formatter:function(value,rowData,rowIndex){
							        		return value;
							        	}
								  },
								  {field:'salenum',title:'销售数量',width:80,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'saleamount',title:'销售金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salenotaxamount',title:'销售未税金额',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salebillcount',title:'销售单据数',width:70,sortable:true,align:'right',
									  	formatter:function(value,rowData,rowIndex){
							        		return value;
							        	}
								  },
								  {field:'costamount',title:'销售成本',resizable:true,sortable:true,align:'right',isShow:true,hidden:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
								  },
								  {field:'salegrossamount',title:'销售毛利',resizable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salegrossrate',title:'销售毛利率',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffamount',title:'回笼金额',resizable:true,align:'right',sortable:true,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'costwriteoffamount',title:'回笼成本金额',resizable:true,align:'right',sortable:true,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffgrossamount',title:'回笼毛利额',resizable:true,align:'right',isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffgrate',title:'回笼毛利率',width:70,align:'right',isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value!=null){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
						        	}
								  },
								  {field:'billcount',title:'单据总数',width:60,align:'right',sortable:true,isShow:true}
					             ]]
				});
    			$("#report-datagrid-buySalesBillCountReport").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
		  	 		checkOnSelect:true,
		  	 		selectOnCheck:true,
					toolbar:'#report-toolbar-buySalesBillCountReport',
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
					width:210,
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
				$("#report-query-branduser").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:130,
					singleSelect:false
				});
				$("#report-query-branddept").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:130,
					onlyLeafCheck:false,
					singleSelect:false
				});
				$("#report-query-supplierid").supplierWidget({});
				//回车事件
				controlQueryAndResetByKey("report-query-buySalesBillCountReport","report-reload-buySalesBillCountReport");

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
				//查询
				$("#report-query-buySalesBillCountReport").click(function(){
					//把form表单的name序列化成JSON对象
      				setColumn(0);
		      		var queryJSON = $("#report-query-form-buySalesBillCountReport").serializeJSON();
		      		$("#report-datagrid-buySalesBillCountReport").datagrid({
		      			url: 'report/sales/showBuySalesBillCountReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-buySalesBillCountReport").click(function(){
					$("#report-query-branduser").widget("clear");
					$("#report-query-branddept").widget("clear");
					$(".groupcols").each(function(){
    					if($(this).attr("checked")){
    						$(this)[0].checked = false;
    					}
					});
					$("#report-query-form-buySalesBillCountReport").form("reset");
					$("#report-query-groupcols").val("");

					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsid");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsname");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "barcode");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "brand");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branduser");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branddept");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "supplierid");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "suppliername");
					
					$("#report-query-form-buySalesBillCountReport").form("reset");
		       		$("#report-datagrid-buySalesBillCountReport").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-buySalesBillCountReportPage").Excel('export',{
					queryForm: "#report-query-form-buySalesBillCountReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'部门采购销售汇总统计表',
			 		url:'report/sales/exportBuySalesBillCountReportData.do'
				});
				<%--
				//高级查询
				$("#report-advancedQuery-buySalesBillCountReportReportPage").click(function(){
					$("#report-dialog-advancedQueryPage").dialog({
						maximizable:true,
						resizable:true,
						title: '部门采购销售汇总报表高级查询',
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
							  		$("#report-branddept-advancedQuery").widget('clear');
							  		$("#report-branduser-advancedQuery").widget('clear');
								    $("#report-form-advancedQuery").form('reset');
								    $(".adgroupcols").each(function(){
										if($(this).attr("checked")){
											$(this)[0].checked = false;
										}
									});


									$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsid");
									$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsname");
									$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "brand");
									$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branduser");
									$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branddept");

									$("#report-datagrid-buySalesBillCountReport").datagrid('loadData',{total:0,rows:[]});
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
		   		//品牌部门
		   		$("#report-branddept-advancedQuery").widget({
		   			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true,
		   			onSelect:function(data){
		   				$("#report-hdbranddept-advancedQuery").val("");
		   				$("input[class='adgroupcols'][value='branddept']").each(function () {
				            $(this).attr("checked", "checked");
				        });
		   			},
		  			onClear:function(){
		  				$("input[class='adgroupcols'][value='branddept']").each(function () {
				            $(this)[0].checked = false;
				        });
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
		   				$("input[class='adgroupcols'][value='branduser']").each(function () {
				            $(this).attr("checked", "checked");
				        });
		   			},
		  			onClear:function(){
		  				$("input[class='adgroupcols'][value='branduser']").each(function () {
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
			  		$("#report-branddept-advancedQuery").widget('clear');
			  		$("#report-branduser-advancedQuery").widget('clear');
				    $("#report-form-advancedQuery").form('reset');
					$("#report-datagrid-selectmoreparam").datagrid("reload");
				});
				--%>
    		});
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
      			$("#report-datagrid-buySalesBillCountReport").datagrid({
          			url:'report/sales/showBuySalesBillCountReportData.do',
          			pageNumber:1,
     				queryParams:adQueryJSON
          		}).datagrid("columnMoving");
          		$("#report-export-buySalesBillCountReportReportPage").Excel('export',{
    				queryForm: "#report-form-advancedQuery",
    		 		type:'exportUserdefined',
    		 		name:'部门采购销售汇总统计表',
    		 		url:'report/sales/exportBuySalesBillCountReportData.do'
    			});
    			/*
    		  	$(".groupcols").each(function(){
    				if($(this).attr("checked")){
    					$(this)[0].checked = false;
    				}
    			});
    			$("#report-query-groupcols").val("");
    	  		$("#report-brandid-advancedQuery").widget('clear');
    	  		$("#report-branddept-advancedQuery").widget('clear');
    	  		$("#report-branduser-advancedQuery").widget('clear');
    		    $("#report-form-advancedQuery").form('reset');
    		    $(".adgroupcols").each(function(){
    				if($(this).attr("checked")){
    					$(this)[0].checked = false;
    				}
    			});
    			*/
          		$("#report-dialog-advancedQueryPage").dialog('close');
      		}
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
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "barcode");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "brand");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "branduser");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "branddept");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "supplierid");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "suppliername")
				}
				else{
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsid");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsname");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "barcode");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "brand");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branduser");
					$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branddept");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "supplierid");
					$("#report-datagrid-buySalesBillCountReport").datagrid('hideColumn', "suppliername")
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=="goodsid"){
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsid");
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "goodsname");
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "barcode");
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "brand");
					}else if(col=="brand"){
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "brand");
						//$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branddept");
					}else if(col=="branduser"){
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branduser");
						//$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branddept");
					}else if(col=="branddept"){
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "branddept");
					}else if(col=="supplierid"){
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "supplierid");
						$("#report-datagrid-buySalesBillCountReport").datagrid('showColumn', "suppliername");
					}
				}
    		}
    		function countTotalAmount(){
        		var rows =  $("#report-datagrid-buySalesBillCountReport").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-buySalesBillCountReport").datagrid("reloadFooter",foot);
            		return false;
        		}
        		var buynum = 0;
        		var buyamount = 0;
        		var buynotaxamount=0;
        		var buybillcount = 0;
        		var salenum = 0;
        		var saleamount=0;
        		var salenotaxamount = 0;
        		var salebillcount = 0;
        		var costamount = 0;
        		var salegrossamount = 0;
        		var writeoffamount = 0;
        		var costwriteoffamount=0;
        		var writeoffgrossamount =0;
        		var billcount=0;
        		for(var i=0;i<rows.length;i++){
        			buynum = Number(buynum)+Number(rows[i].buynum == undefined ? 0 : rows[i].buynum);
        			buyamount = Number(buyamount)+Number(rows[i].buyamount == undefined ? 0 : rows[i].buyamount);
        			buynotaxamount = Number(buynotaxamount)+Number(rows[i].buynotaxamount == undefined ? 0 : rows[i].buynotaxamount);
        			buybillcount = Number(buybillcount)+Number(rows[i].buybillcount == undefined ? 0 : rows[i].buybillcount);
        			salenum = Number(salenum)+Number(rows[i].salenum == undefined ? 0 : rows[i].salenum);
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			salenotaxamount = Number(salenotaxamount)+Number(rows[i].salenotaxamount == undefined ? 0 : rows[i].salenotaxamount);
        			salebillcount = Number(salebillcount)+Number(rows[i].salebillcount == undefined ? 0 : rows[i].salebillcount);
        			costamount = Number(costamount)+Number(rows[i].costamount == undefined ? 0 : rows[i].costamount);
        			salegrossamount = Number(salegrossamount)+Number(rows[i].salegrossamount == undefined ? 0 : rows[i].salegrossamount);
        			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
        			costwriteoffamount = Number(costwriteoffamount)+Number(rows[i].costwriteoffamount == undefined ? 0 : rows[i].costwriteoffamount);
        			writeoffgrossamount = Number(writeoffgrossamount)+Number(rows[i].writeoffgrossamount == undefined ? 0 : rows[i].writeoffgrossamount);
        			billcount = Number(billcount)+Number(rows[i].billcount == undefined ? 0 : rows[i].billcount);
        		}
        		var foot=[{goodsname:'选中合计',buynum:buynum,buyamount:buyamount,buynotaxamount:buynotaxamount,buybillcount:buybillcount,
            				salenum:salenum,saleamount:saleamount,salenotaxamount:salenotaxamount,salebillcount:salebillcount,
            				costamount:costamount,salegrossamount:salegrossamount,writeoffamount:writeoffamount,costwriteoffamount:costwriteoffamount,writeoffgrossamount:writeoffgrossamount,billcount:billcount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-buySalesBillCountReport").datagrid("reloadFooter",foot);
        	}
    	</script>
  </body>
</html>
