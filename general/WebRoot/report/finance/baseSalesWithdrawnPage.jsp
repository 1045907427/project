<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金回笼基础情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-baseSalesWithdrawn"></table>
    	<div id="report-toolbar-baseSalesWithdrawn" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/baseSalesWithdrawnExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-baseSalesWithdrawnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-baseSalesWithdrawn" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
    				<td>总店名称:</td>
    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
    			</tr>
    			<tr>
    				<td>品牌:</td>
		  			<td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
		  			<td>品牌部门:</td>
		  			<td><input type="text" name="branddept" id="report-branddept-advancedQuery"/></td>
		  			<td>品牌业务员:</td>
		  			<td><input type="text" name="branduser" id="report-branduser-advancedQuery"/></td>
    			</tr>
    			<tr>
    				<td>商品:</td>
	  				<td><input type="text" name="goodsid" id="report-goodsid-advancedQuery" style="width: 210px;"/>
    				<td>销售区域:</td>
	  				<td><input type="text" name="salesarea" id="report-salesarea-advancedQuery" style="width: 210px;"/>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-baseSalesWithdrawn" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-baseSalesWithdrawn" class="button-qr">重置</a>

    				</td>
    			</tr>
    			<tr>
    				<td>小计列：</td>
    				<td colspan="5">
    					<input type="checkbox" class="groupcols" value="customerid"/>客户
    					<input type="checkbox" class="groupcols" value="pcustomerid"/>总店
    					<input type="checkbox" class="groupcols" value="salesuserid"/>客户业务员
    					<input type="checkbox" class="groupcols" value="salesarea"/>销售区域
    					<input type="checkbox" class="groupcols" value="salesdept"/>销售部门
    					<input type="checkbox" class="groupcols" value="goodsid"/>商品
    					<input type="checkbox" class="groupcols" value="branddept"/>品牌部门
    					<input type="checkbox" class="groupcols" value="branduser"/>品牌业务员
    					<input type="checkbox" class="groupcols" value="brandid"/>品牌
    					<input id="report-query-groupcols" type="hidden" name="groupcols"/>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<div id="report-dialog-selectmoreparam"></div>
    	<div id="report-div-selectmoreparam">
    		<form action="" method="post" id="report-form-selectmoreparam">
    			<table cellpadding="0" cellspacing="0" border="0">
    				<tr>
    					<td>客户:</td>
    					<td><input type="text" name="id" id="report-customer-selectmoreparamlist"/></td>
    					<td>
    						<a href="javaScript:void(0);" id="report-queay-selectmoreparam" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
							<a href="javaScript:void(0);" id="report-reload-selectmoreparam" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
    					</td>
    				</tr>
    			</table>
		  	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-baseSalesWithdrawn").serializeJSON();
    		$(function(){
		  		
		   		//查询
				$("#report-queay-selectmoreparam").click(function(){
		      		var selectmoreQueryJSON = $("#report-form-selectmoreparam").serializeJSON();
		      		$("#report-datagrid-selectmoreparam").datagrid('load',selectmoreQueryJSON);
				});
				//重置
				$("#report-reload-selectmoreparam").click(function(){
			  		$("#report-customer-selectmoreparamlist").widget('clear');
				    $("#report-form-selectmoreparam").form('reset');
					$("#report-datagrid-selectmoreparam").datagrid("reload");
				});
				
    			$(".groupcols").click(function(){
    				var cols = "";
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
    			var tableColumnListJson = $("#report-datagrid-baseSalesWithdrawn").createGridColumnLoad({
					frozenCol : [[]],
					commonCol : [[
								  {field:'idok',checkbox:true,isShow:true},
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:130},
								  {field:'pcustomerid',title:'总店名称',sortable:true,width:130,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.pcustomername;
						        	}
								  },
								  {field:'salesuserid',title:'客户业务员',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesusername;
						        	}
								  },
								  {field:'salesarea',title:'销售区域',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesdeptname;
						        	}
								  },
								  {field:'branduser',title:'品牌业务员',sortable:true,width:80,
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
								  {field:'goodsname',title:'商品名称',sortable:true,width:130},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'brandid',title:'品牌名称',sortable:true,width:80,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
								  },
								  {field:'sendamount',title:'发货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'directreturnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'checkreturnamount',title:'售后退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'returntaxamount',title:'退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'pushbalanceamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'saleamount',title:'销售总额',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salecostamount',title:'销售成本',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salerate',title:'销售毛利率',width:80,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		if(null != value && "" != value){
								  			return formatterMoney(value)+'%';
								  		}
						        	}
								  },
								  {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'costwriteoffamount',title:'回笼成本',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'withdrawnmarginamount',title:'回笼毛利额',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:80,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(null != value && "" != value){
								  			return formatterMoney(value)+"%";
								  		}
						        	}
								  },
								  {field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
					   ]]
				});
    			$("#report-datagrid-baseSalesWithdrawn").datagrid({ 
    				authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-baseSalesWithdrawn',
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
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:210,
					singleSelect:false
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:210,
					singleSelect:false
				});
				$("#report-goodsid-advancedQuery").widget({
		  			referwid:'RL_T_BASE_GOODS_INFO',
		   			singleSelect:false,
		   			width:'210',
		   			onlyLeafCheck:true
		  		});
		  		
		  		$("#report-salesarea-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_AREA',
		   			singleSelect:false,
		   			width:'160',
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
				controlQueryAndResetByKey("report-queay-baseSalesWithdrawn","report-reload-baseSalesWithdrawn");
				
				//查询
				$("#report-queay-baseSalesWithdrawn").click(function(){
					setColumn();
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-baseSalesWithdrawn").serializeJSON();
		      		$("#report-datagrid-baseSalesWithdrawn").datagrid({
		      			url:'report/finance/showBaseSalesWithdrawnData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-baseSalesWithdrawn").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-brandid-advancedQuery").widget("clear");
					$("#report-branddept-advancedQuery").widget("clear");
					$("#report-branduser-advancedQuery").widget("clear");
					$("#report-goodsid-advancedQuery").widget("clear");
					$("#report-salesarea-advancedQuery").widget("clear");
					$("#report-query-form-baseSalesWithdrawn").form("reset");
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-baseSalesWithdrawn").serializeJSON();
		      		$("#report-datagrid-baseSalesWithdrawn").datagrid({
		      			url:'report/finance/showBaseSalesWithdrawnData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//导出
				$("#report-buttons-baseSalesWithdrawnPage").Excel('export',{
					queryForm: "#report-query-form-baseSalesWithdrawn", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'资金回笼情况表',
			 		url:'report/finance/exportBaseSalesWithdrawnData.do'
				});
    		});
    		var $datagrid = $("#report-datagrid-baseSalesWithdrawn");
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
	    			$datagrid.datagrid('hideColumn', "customerid");
					$datagrid.datagrid('hideColumn', "customername");
					$datagrid.datagrid('hideColumn', "pcustomerid");
					$datagrid.datagrid('hideColumn', "salesuserid");
					$datagrid.datagrid('hideColumn', "salesarea");
					$datagrid.datagrid('hideColumn', "salesdept");
					$datagrid.datagrid('hideColumn', "goodsid");
					$datagrid.datagrid('hideColumn', "goodsname");
					$datagrid.datagrid('hideColumn', "barcode");
					$datagrid.datagrid('hideColumn', "brandid");
					$datagrid.datagrid('hideColumn', "branduser");
					$datagrid.datagrid('hideColumn', "branddept");
				}
				else{
					$datagrid.datagrid('showColumn', "customerid");
					$datagrid.datagrid('showColumn', "customername");
					$datagrid.datagrid('showColumn', "pcustomerid");
					$datagrid.datagrid('showColumn', "salesuserid");
					//$datagrid.datagrid('showColumn', "salesarea");
					//$datagrid.datagrid('showColumn', "salesdept");
					$datagrid.datagrid('showColumn', "goodsid");
					$datagrid.datagrid('showColumn', "goodsname");
					$datagrid.datagrid('showColumn', "barcode");
					$datagrid.datagrid('showColumn', "brandid");
					$datagrid.datagrid('showColumn', "branduser");
					//$datagrid.datagrid('showColumn', "branddept");
				}
				
					var colarr = cols.split(",");
					for(var i=0;i<colarr.length;i++){
						var col = colarr[i];
						if(col=='customerid'){
							$datagrid.datagrid('showColumn', "customerid");
							$datagrid.datagrid('showColumn', "customername");
							$datagrid.datagrid('showColumn', "pcustomerid");
							$datagrid.datagrid('showColumn', "salesuserid");
							//$datagrid.datagrid('showColumn', "salesarea");
							//$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="pcustomerid"){
							$datagrid.datagrid('showColumn', "pcustomerid");
						}else if(col=="salesuserid"){
							$datagrid.datagrid('showColumn', "salesuserid");
							//$datagrid.datagrid('showColumn', "salesarea");
							//$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="salesarea"){
							$datagrid.datagrid('showColumn', "salesarea");
						}else if(col=="salesdept"){
							$datagrid.datagrid('showColumn', "salesdept");
						}else if(col=="goodsid"){
							$datagrid.datagrid('showColumn', "goodsid");
							$datagrid.datagrid('showColumn', "goodsname");
							$datagrid.datagrid('showColumn', "barcode");
							$datagrid.datagrid('showColumn', "brandid");
						}else if(col=="brandid"){
							$datagrid.datagrid('showColumn', "brandid");
							//$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="branduser"){
							$datagrid.datagrid('showColumn', "branduser");
							//$datagrid.datagrid('showColumn', "branddept");
						}else if(col=="branddept"){
							$datagrid.datagrid('showColumn', "branddept");
						}
					}
    		}
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-baseSalesWithdrawn").datagrid('getChecked');
    			var sendamount = 0;
        		var directreturnamount = 0;
        		var endamount=0;
        		var returntaxamount = 0;
        		var pushbalanceamount =0;
        		var saleamount = 0;
        		var salecostamount = 0;
        		var salemarginamount =0;
        		var withdrawnamount = 0;
        		var costwriteoffamount=0;
        		var allunwithdrawnamount =0;
        		var unauditamount = 0;
        		var auditamount = 0;
        		var rejectamount = 0;
        		var allpushbalanceamount = 0;
        		for(var i=0;i<rows.length;i++){
        			sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
        			directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
        			endamount = Number(endamount)+Number(rows[i].endamountmount == undefined ? 0 : rows[i].endamount);
        			returntaxamount = Number(returntaxamount)+Number(rows[i].returntaxamountmount == undefined ? 0 : rows[i].returntaxamount);
        			pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamountmount == undefined ? 0 : rows[i].pushbalanceamount);
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			salecostamount = Number(salecostamount)+Number(rows[i].salecostamount == undefined ? 0 : rows[i].salecostamount);
        			salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
        			withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
        			costwriteoffamount = Number(costwriteoffamount)+Number(rows[i].costwriteoffamount == undefined ? 0 : rows[i].costwriteoffamount);
        			allunwithdrawnamount = Number(allunwithdrawnamount)+Number(rows[i].allunwithdrawnamount == undefined ? 0 : rows[i].allunwithdrawnamount);
        			unauditamount = Number(unauditamount)+Number(rows[i].unauditamount == undefined ? 0 : rows[i].unauditamount);
        			auditamount = Number(auditamount)+Number(rows[i].auditamount == undefined ? 0 : rows[i].auditamount);
        			rejectamount = Number(rejectamount)+Number(rows[i].rejectamount == undefined ? 0 : rows[i].rejectamount);
        			allpushbalanceamount = Number(allpushbalanceamount)+Number(rows[i].allpushbalanceamount == undefined ? 0 : rows[i].allpushbalanceamount);
        		}
        		var foot=[{goodsname:'选中合计',sendamount:sendamount,directreturnamount:directreturnamount,
            			endamount:endamount,returntaxamount:returntaxamount,pushbalanceamount:pushbalanceamount,
            			saleamount:saleamount,salecostamount:salecostamount,salemarginamount : salemarginamount,withdrawnamount: withdrawnamount,
            			costwriteoffamount : costwriteoffamount,allunwithdrawnamount : allunwithdrawnamount,unauditamount: unauditamount,
            			auditamount : auditamount , rejectamount : rejectamount, allpushbalanceamount : allpushbalanceamount
            	}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-baseSalesWithdrawn").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
