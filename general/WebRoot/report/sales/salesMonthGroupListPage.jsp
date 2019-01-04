<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分月汇总销售报表</title>
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
  	    <table id="report-datagrid-salesMonthGroup"></table>
    	<div id="report-toolbar-salesMonthGroup" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-salesMonthGroupPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
    		<security:authorize url="/report/sales/exportMonthSalesReportData.do">
    			<a href="javaScript:void(0);" id="report-export-salesMonthGroupPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
    		</security:authorize>
    	</div>
    	<div id="report-dialog-advancedQueryDialog" >
    		<form action="" id="report-query-form-salesMonthGroup" method="post">
	    		<table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
	    			<tr>
	    				<td>年份:</td>
	    				<td><input id="journalsheet-budgetListPage-month" type="text" required='required' name="year" style="width:200px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy'})" value="${current}"/></td>
		  				<td>客&nbsp;&nbsp;户:</td>
		  				<td><input type="text" name="customerid" id="report-customernamemore-advancedQuery"/></td>
		  			</tr>
		  			<tr>
	    				<td>总&nbsp;&nbsp;店:</td>
		  				<td><input type="text" name="pcustomerid" id="report-pcustomernamemore-advancedQuery"/></td>
		  				<td>品&nbsp;&nbsp;牌:</td>
			  			<td><input type="text" name="brandid" id="report-brandid-advancedQuery"/></td>
			  		</tr>
			  		<tr>
			  			<td>品牌部门:</td>
			  			<td><input type="text" name="branddept" id="report-branddept-advancedQuery"/></td>
			  			<td>品牌业务员:</td>
			  			<td><input type="text" name="branduser" id="report-branduser-advancedQuery"/></td>
	    			</tr>
	    			<tr>
	    				<td>商&nbsp;&nbsp;品:</td>
		  				<td><input type="text" name="goodsid" id="report-goodsid-advancedQuery"/></td>
	    				<td>商品分类:</td>
		  				<td><input type="text" name="goodssort" id="report-goodssort-advancedQuery"/></td>
		  			</tr>
		  			<tr>
	    				<td>商品类型:</td>
		  				<td><select name="goodstype" id="report-goodstype-advancedQuery" style="width: 200px;"  data-options="multiple:true,onLoadSuccess:function(){$(this).combobox('clear');}">
		  						<option></option>
		  						<c:forEach items="${goodstypeList }" var="list">
	    							<option value="${list.code }">${list.codename }</option>
				    			</c:forEach>
		  					</select>
		  				</td>
	    				<td>销售区域:</td>
		  				<td><input type="text" name="salesarea" id="report-salesarea-advancedQuery"/></td>
		  			</tr>
		  			<tr>
		  				<td>客户分类:</td>
		  				<td><input type="text" name="customersort" id="report-customersort-advancedQuery"/></td>
		  				<td>客户业务员:</td>
		  				<td><input type="text" name="salesuser" id="report-salesuser-advancedQuery"/></td>
	    			</tr>
	    			<tr>
	    				<td>供 应 商:</td>
	    				<td><input type="text" name="supplierid" id="report-supplierid-advancedQuery"/></td>
	    				<td>厂家业务员:</td>
	    				<td><input type="text" name="supplieruser" id="report-supplieruser-advancedQuery"/></td>
	    			</tr>
	    			<tr>
  						<td>销售部门:</td>
                   		<td><input type="text" id="report-salesdept-advancedQuery"   name="salesdept" /></td>
	    			</tr>
	    			<tr>
	    				<td>小计列：</td>
	    				<td colspan="3">
	    					<div style="margin-top:2px">
		    					<div style="line-height: 25px;margin-top: 2px;">
			    					<label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="pcustomerid"/>总店</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customersort"/>客户分类</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesarea"/>销售区域</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser"/>客户业务员</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept"/>销售部门</label>
	    						<br/>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodssort"/>商品分类</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branddept"/>品牌部门</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branduser"/>品牌业务员</label>
                                    <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplieruser"/>厂家业务员</label>
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
    		var initQueryJSON = $("#report-query-form-salesMonthGroup").serializeJSON();
    		
		    var forzenCol = [[
								{field:'idok',checkbox:true,isShow:true}
		    			    ]]
			var commonCol =[
								[
								  {field:'customerid',title:'客户编号',rowspan:2,sortable:true,width:60},
								  {field:'customername',title:'客户名称',rowspan:2,width:210},
								  {field:'pcustomerid',title:'总店编码',rowspan:2,sortable:true,width:60,hidden:true},
								  {field:'pcustomername',title:'总店名称',rowspan:2,width:60,hidden:true},
								  
								  {field:'salesuser',title:'客户业务员',rowspan:2,sortable:true,width:70,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesusername;
						        	}
								  },
								  {field:'customersort',title:'客户分类',rowspan:2,sortable:true,width:60,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.customersortname;
						        	}
								  },
								  {field:'salesarea',title:'销售区域',rowspan:2,sortable:true,width:60,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'salesdept',title:'销售部门',rowspan:2,sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesdeptname;
						        	}
								  },
								  {field:'branddept',title:'品牌部门',rowspan:2,sortable:true,width:80,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.branddeptname;
						        	}
								  },
								  {field:'branduser',title:'品牌业务员',rowspan:2,sortable:true,width:70,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandusername;
						        	}
								  },
								  {field:'supplieruser',title:'厂家业务员',rowspan:2,sortable:true,width:70,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.supplierusername;
						        	}
								  },
								  {field:'goodsid',title:'商品编码',rowspan:2,sortable:true,width:60,hidden:true},
								  {field:'goodsname',title:'商品名称',rowspan:2,width:250,hidden:true},
								  {field:'goodssortname',title:'商品分类',rowspan:2,width:60,hidden:true},
								  {field:'goodstypename',title:'商品类型',rowspan:2,width:60,hidden:true},
								  {field:'barcode',title:'条形码',width:90,rowspan:2,hidden:true},
								  {field:'brandid',title:'品牌名称',rowspan:2,sortable:true,width:60,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.brandname;
						        	}
								  },
								  {field:'supplierid',title:'供应商名称',rowspan:2,sortable:true,width:200,hidden:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.suppliername;
						        	}
								  },
								    
								  {title:'1月份',colspan:4},{title:'2月份',colspan:4},{title:'3月份',colspan:4},{title:'4月份',colspan:4},{title:'5月份',colspan:4},{title:'6月份',colspan:4},{title:'7月份',colspan:4},{title:'8月份',colspan:4},{title:'9月份',colspan:4},{title:'10月份',colspan:4},{title:'11月份',colspan:4},{title:'12月份',colspan:4}
								],[

					{field:'saleboxnum01',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount01',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount01',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate01',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum02',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount02',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount02',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate02',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum03',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount03',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount03',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realrate03',title:'毛利率',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							if(value!=null && value!=0){
								return formatterMoney(value)+"%";
							}else{
								return "";
							}
						}},

					{field:'saleboxnum04',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount04',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount04',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate04',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum05',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount05',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount05',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate05',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum06',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount06',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount06',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate06',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum07',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount07',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount07',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate07',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum08',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount08',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount08',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate08',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum09',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount09',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount09',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate09',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum10',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount10',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount10',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate10',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum11',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount11',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount11',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate11',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}},

					{field:'saleboxnum12',title:'销售箱数',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'saleamount12',title:'销售金额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'realsaleamount12',title:'销售毛利额',rowspan:1,width:68,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
								    {field:'realrate12',title:'毛利率',rowspan:1,width:68,
								    formatter:function(value,rowData,rowIndex){
						        		if(value!=null && value!=0){
						        			return formatterMoney(value)+"%";
						        		}else{
						        			return "";
						        		}
					        		}}
								    ]
							]	
    		
    		
    		
    		$(function(){
		  		//小计列
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
				//rowspan:2,
	
    			//品牌
		  		$("#report-brandid-advancedQuery").widget({
		   			referwid:'RL_T_BASE_GOODS_BRAND',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		   		});
		   		//品牌部门
		   		$("#report-branddept-advancedQuery").widget({
		   			referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		   		});
		   		
		   		//品牌业务员
		   		$("#report-branduser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
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
		   		//厂家业务员
		   		$("#report-supplieruser-advancedQuery").widget({
		   			referwid:'RL_T_BASE_PERSONNEL_SUPPLIER',
		   			singleSelect:false,
		   			width:200
		   		});
		   		$("#report-customernamemore-advancedQuery").widget({
		  			referwid:'RL_T_BASE_SALES_CUSTOMER',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		  		});
		  		$("#report-pcustomernamemore-advancedQuery").widget({
		  			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		  		});
		  		$("#report-goodsid-advancedQuery").widget({
		  			referwid:'RL_T_BASE_GOODS_INFO',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:true
		  		});
		  		$("#report-goodssort-advancedQuery").widget({
		  			referwid:'RL_T_BASE_GOODS_WARESCLASS',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:false
		  		});
		  		$("#report-customersort-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
		   			singleSelect:true,
		   			width:200,
		   			onlyLeafCheck:false
		  		});
		  		$("#report-salesuser-advancedQuery").widget({
		  			referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:false
		  		});
		  		$("#report-salesarea-advancedQuery").widget({
		  			referwid:'RT_T_BASE_SALES_AREA',
		   			singleSelect:false,
		   			width:200,
		   			onlyLeafCheck:false
		  		});
		  		 //销售部门
                $("#report-salesdept-advancedQuery").widget({
	    			name:'t_sales_order',
					col:'salesdept',
	    			width:200,
					singleSelect:true,
					onlyLeafCheck:true
	    		});
    			$("#report-datagrid-salesMonthGroup").datagrid({ 
			 		frozenColumns: forzenCol,
					columns:commonCol,
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
					toolbar:'#report-toolbar-salesMonthGroup',
		  	 		onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
							calCulate(baseSales_retColsname(),this);
						}
			 		},
					onCheckAll:function(){
						calCulate(baseSales_retColsname(),this);
					},
					onUncheckAll:function(){
						calCulate(baseSales_retColsname(),this);
					},
					onCheck:function(){
						calCulate(baseSales_retColsname(),this);
					},
					onUncheck:function(){
						calCulate(baseSales_retColsname(),this);
					},
					onHeaderContextMenu : customHeaderContextMenu
				}).datagrid("columnMoving");
		   		
    			
    			
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-salesMonthGroupPage","");

				//高级查询
				$("#report-advancedQuery-salesMonthGroupPage").click(function(){
					$("#report-dialog-advancedQueryDialog").dialog({
						maximizable:false,
						resizable:true,
						title: '分月销售汇总表查询',
						top:30,
					    width: 565,
					    height: 400,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'确定',
								handler:function(){
									setColumn();
									//把form表单的name序列化成JSON对象
						      		var queryJSON = $("#report-query-form-salesMonthGroup").serializeJSON();
						      		$("#report-datagrid-salesMonthGroup").datagrid({
						      			url:'report/sales/showsalesMonthGroupData.do',
						      			pageNumber:1,
				   						queryParams:queryJSON
						      		}).datagrid("columnMoving");
						      		$("#report-dialog-advancedQueryDialog").dialog('close');
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-query-form-salesMonthGroup").form("reset");
									$(".groupcols").each(function(){
				    					if($(this).attr("checked")){
				    						$(this)[0].checked = false;
				    					}
									});
									$("#report-query-groupcols").val("");
									$("#report-datagrid-salesMonthGroup").datagrid("loadData",[]);
									$("#report-customernamemore-advancedQuery").widget("clear");
									$("#report-pcustomernamemore-advancedQuery").widget("clear");
									$("#report-customersort-advancedQuery").widget("clear");
									$("#report-brandid-advancedQuery").widget("clear");
									$("#report-branddept-advancedQuery").widget("clear");
									$("#report-branduser-advancedQuery").widget("clear");
									$("#report-salesarea-advancedQuery").widget("clear");
									$("#report-goodsid-advancedQuery").widget("clear");
									$("#report-goodssort-advancedQuery").widget("clear");
									$("#report-salesarea-advancedQuery").widget('clear');
									$("#report-supplierid-advancedQuery").widget('clear');
									$("#report-supplieruser-advancedQuery").widget('clear');
									$("#report-salesdept-advancedQuery").widget('clear');
									$("#report-salesuser-advancedQuery").widget('clear');
									setColumn();
									$("#report-goodstype-advancedQuery").combobox('clear');
								}
							}
							],
						onClose:function(){
						}
					});
				});
				
				
				//导出
				$("#report-export-salesMonthGroupPage").Excel('export',{
					queryForm: "#report-query-form-salesMonthGroup",
			 		type:'exportUserdefined',
			 		name:'销售情况按月汇总统计表',
			 		url:'report/sales/exportMonthSalesReportData.do'
				});
				
    		});
    		function setColumn(){
    			var cols = $("#report-query-groupcols").val();
    			if(cols!=""){
	    			$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "customerid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "customername");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "pcustomername");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "salesuser");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "customersort");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "salesarea");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "salesdept");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodssortname");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodstypename");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "barcode");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "brandid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "branduser");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "supplieruser");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "branddept");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "supplierid");
				}
				else{
					$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "customerid");
					$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "customername");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "pcustomerid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "pcustomername");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "salesuser");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "salesarea");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "salesdept");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodsid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodsname");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodssortname");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "goodstypename");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "barcode");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "brandid");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "branduser");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "supplieruser");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "branddept");
					$("#report-datagrid-salesMonthGroup").datagrid('hideColumn', "supplierid");
				}
				var colarr = cols.split(",");
				for(var i=0;i<colarr.length;i++){
					var col = colarr[i];
					if(col=='customerid'){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "customerid");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "customername");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "pcustomerid");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "pcustomername");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "salesuser");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "customersort");
					}else if(col=="pcustomerid"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "pcustomerid");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "pcustomername");
					}else if(col=="salesuser"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "salesuser");
					}else if(col=="salesarea"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "salesarea");
					}else if(col=="salesdept"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "salesdept");
					}else if(col=="goodsid"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "goodsid");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "goodsname");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "goodssortname");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "goodstypename");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "barcode");
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "brandid");
					}else if(col=="goodssort"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "goodssortname");
					}else if(col=="brandid"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "brandid");
					}else if(col=="branduser"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "branduser");
					}else if(col=="branddept"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "branddept");
					}else if(col=="customersort"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "customersort");
					}else if(col=="supplierid"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "supplierid");
					}else if(col=="supplieruser"){
						$("#report-datagrid-salesMonthGroup").datagrid('showColumn', "supplieruser");
					}
				}
    		}
	  		
	  		//选中合计
	  		function calCulate(col,datagrid){
	  			var rows =  $("#"+datagrid.id+"").datagrid("getChecked");
	  			var saleamount01 = 0; var realrate01 = 0;var saleboxnum01=0;var realsaleamount01=0;
	  			var saleamount02 = 0; var realrate02 = 0;var saleboxnum02=0;var realsaleamount02=0;
	  			var saleamount03 = 0; var realrate03 = 0;var saleboxnum03=0;var realsaleamount03=0;
	  			var saleamount04 = 0; var realrate04 = 0;var saleboxnum04=0;var realsaleamount04=0;
	  			var saleamount05 = 0; var realrate05 = 0;var saleboxnum05=0;var realsaleamount05=0;
	  			var saleamount06 = 0; var realrate06 = 0;var saleboxnum06=0;var realsaleamount06=0;
	  			var saleamount07 = 0; var realrate07 = 0;var saleboxnum07=0;var realsaleamount07=0;
	  			var saleamount08 = 0; var realrate08 = 0;var saleboxnum08=0;var realsaleamount08=0;
	  			var saleamount09 = 0; var realrate09 = 0;var saleboxnum09=0;var realsaleamount09=0;
	  			var saleamount10 = 0; var realrate10 = 0;var saleboxnum10=0;var realsaleamount10=0;
	  			var saleamount11 = 0; var realrate11 = 0;var saleboxnum11=0;var realsaleamount11=0;
	  			var saleamount12 = 0; var realrate12 = 0;var saleboxnum12=0;var realsaleamount12=0;
	  			for(var i=0;i<rows.length;i++){
	  				saleamount01 = Number(saleamount01)+Number(rows[i].saleamount01 == undefined ? 0 : rows[i].saleamount01);
	  				saleamount02 = Number(saleamount02)+Number(rows[i].saleamount02 == undefined ? 0 : rows[i].saleamount02);
	  				saleamount03 = Number(saleamount03)+Number(rows[i].saleamount03 == undefined ? 0 : rows[i].saleamount03);
	  				saleamount04 = Number(saleamount04)+Number(rows[i].saleamount04 == undefined ? 0 : rows[i].saleamount04);
	  				saleamount05 = Number(saleamount05)+Number(rows[i].saleamount05 == undefined ? 0 : rows[i].saleamount05);
	  				saleamount06 = Number(saleamount06)+Number(rows[i].saleamount06 == undefined ? 0 : rows[i].saleamount06);
	  				saleamount07 = Number(saleamount07)+Number(rows[i].saleamount07 == undefined ? 0 : rows[i].saleamount07);
	  				saleamount08 = Number(saleamount08)+Number(rows[i].saleamount08 == undefined ? 0 : rows[i].saleamount08);
	  				saleamount09 = Number(saleamount09)+Number(rows[i].saleamount09 == undefined ? 0 : rows[i].saleamount09);
	  				saleamount10 = Number(saleamount10)+Number(rows[i].saleamount10 == undefined ? 0 : rows[i].saleamount10);
	  				saleamount11 = Number(saleamount11)+Number(rows[i].saleamount11 == undefined ? 0 : rows[i].saleamount11);
	  				saleamount12 = Number(saleamount12)+Number(rows[i].saleamount12 == undefined ? 0 : rows[i].saleamount12);

					saleboxnum01 = Number(saleboxnum01)+Number(rows[i].saleboxnum01 == undefined ? 0 : rows[i].saleboxnum01);
					saleboxnum02 = Number(saleboxnum02)+Number(rows[i].saleboxnum02 == undefined ? 0 : rows[i].saleboxnum02);
					saleboxnum03 = Number(saleboxnum03)+Number(rows[i].saleboxnum03 == undefined ? 0 : rows[i].saleboxnum03);
					saleboxnum04 = Number(saleboxnum04)+Number(rows[i].saleboxnum04 == undefined ? 0 : rows[i].saleboxnum04);
					saleboxnum05 = Number(saleboxnum05)+Number(rows[i].saleboxnum05 == undefined ? 0 : rows[i].saleboxnum05);
					saleboxnum06 = Number(saleboxnum06)+Number(rows[i].saleboxnum06 == undefined ? 0 : rows[i].saleboxnum06);
					saleboxnum07 = Number(saleboxnum07)+Number(rows[i].saleboxnum07 == undefined ? 0 : rows[i].saleboxnum07);
					saleboxnum08 = Number(saleboxnum08)+Number(rows[i].saleboxnum08 == undefined ? 0 : rows[i].saleboxnum08);
					saleboxnum09 = Number(saleboxnum09)+Number(rows[i].saleboxnum09 == undefined ? 0 : rows[i].saleboxnum09);
					saleboxnum10 = Number(saleboxnum10)+Number(rows[i].saleboxnum10 == undefined ? 0 : rows[i].saleboxnum10);
					saleboxnum11 = Number(saleboxnum11)+Number(rows[i].saleboxnum11 == undefined ? 0 : rows[i].saleboxnum11);
					saleboxnum12 = Number(saleboxnum12)+Number(rows[i].saleboxnum12 == undefined ? 0 : rows[i].saleboxnum12);

					realsaleamount01 = Number(realsaleamount01)+Number(rows[i].realsaleamount01 == undefined ? 0 : rows[i].realsaleamount01);
					realsaleamount02 = Number(realsaleamount02)+Number(rows[i].realsaleamount02 == undefined ? 0 : rows[i].realsaleamount02);
					realsaleamount03 = Number(realsaleamount03)+Number(rows[i].realsaleamount03 == undefined ? 0 : rows[i].realsaleamount03);
					realsaleamount04 = Number(realsaleamount04)+Number(rows[i].realsaleamount04 == undefined ? 0 : rows[i].realsaleamount04);
					realsaleamount05 = Number(realsaleamount05)+Number(rows[i].realsaleamount05 == undefined ? 0 : rows[i].realsaleamount05);
					realsaleamount06 = Number(realsaleamount06)+Number(rows[i].realsaleamount06 == undefined ? 0 : rows[i].realsaleamount06);
					realsaleamount07 = Number(realsaleamount07)+Number(rows[i].realsaleamount07 == undefined ? 0 : rows[i].realsaleamount07);
					realsaleamount08 = Number(realsaleamount08)+Number(rows[i].realsaleamount08 == undefined ? 0 : rows[i].realsaleamount08);
					realsaleamount09 = Number(realsaleamount09)+Number(rows[i].realsaleamount09 == undefined ? 0 : rows[i].realsaleamount09);
					realsaleamount10 = Number(realsaleamount10)+Number(rows[i].realsaleamount10 == undefined ? 0 : rows[i].realsaleamount10);
					realsaleamount11 = Number(realsaleamount11)+Number(rows[i].realsaleamount11 == undefined ? 0 : rows[i].realsaleamount11);
					realsaleamount12 = Number(realsaleamount12)+Number(rows[i].realsaleamount12 == undefined ? 0 : rows[i].realsaleamount12);
	  			}
	  			var obj = {
	  				saleamount01:saleamount01,
	  				saleamount02:saleamount02,
	  				saleamount03:saleamount03,
	  				saleamount04:saleamount04,
	  				saleamount05:saleamount05,
	  				saleamount06:saleamount06,
	  				saleamount07:saleamount07,
	  				saleamount08:saleamount08,
	  				saleamount09:saleamount09,
	  				saleamount10:saleamount10,
	  				saleamount11:saleamount11,
	  				saleamount12:saleamount12,

					saleboxnum01:saleboxnum01,
					saleboxnum02:saleboxnum02,
					saleboxnum03:saleboxnum03,
					saleboxnum04:saleboxnum04,
					saleboxnum05:saleboxnum05,
					saleboxnum06:saleboxnum06,
					saleboxnum07:saleboxnum07,
					saleboxnum08:saleboxnum08,
					saleboxnum09:saleboxnum09,
					saleboxnum10:saleboxnum10,
					saleboxnum11:saleboxnum11,
					saleboxnum12:saleboxnum12,

					realsaleamount01:realsaleamount01,
					realsaleamount02:realsaleamount02,
					realsaleamount03:realsaleamount03,
					realsaleamount04:realsaleamount04,
					realsaleamount05:realsaleamount05,
					realsaleamount06:realsaleamount06,
					realsaleamount07:realsaleamount07,
					realsaleamount08:realsaleamount08,
					realsaleamount09:realsaleamount09,
					realsaleamount10:realsaleamount10,
					realsaleamount11:realsaleamount11,
					realsaleamount12:realsaleamount12
	  			}
	  			
	  			var foot=[];
				foot.push(obj);
				if(null!=SR_footerobject){
   					foot.push(SR_footerobject);
				}
	  			$("#"+datagrid.id+"").datagrid("reloadFooter",foot);
	  		}
	  		
	  		function baseSales_retColsname(){
	  			var colname = "";
	  			var cols = $("#report-query-groupcols").val();
	  			if(cols == ""){
	  				cols = "customerid";
	  			}
	  			var colarr = cols.split(",");
	  			if(colarr[0]=="pcustomerid"){
					colname = "pcustomername";
				}else if(colarr[0]=='customerid'){
					colname = "customername";
				}else if(colarr[0]=="salesuser"){
					colname = "salesusername";
				}else if(colarr[0]=="salesarea"){
					colname = "salesareaname";
				}else if(colarr[0]=="salesdept"){
					colname = "salesdeptname";
				}else if(colarr[0]=="goodsid"){
					colname = "goodsname";
				}else if(colarr[0]=="goodssort"){
					colname = "goodssortname";
				}else if(colarr[0]=="brandid"){
					colname = "brandname";
				}else if(colarr[0]=="branduser"){
					colname = "brandusername";
				}else if(colarr[0]=="branddept"){
					colname = "branddeptname";
				}else if(colarr[0]=="customersort"){
					colname = "customersortname";
				}else if(colarr[0]=="supplierid"){
					colname = "suppliername";
				}else if(colarr[0]=="supplieruser"){
					colname = "supplierusername";
				}
				return colname;
	  		}
	  		
	  		
	  		
	  		function customHeaderContextMenu(e, field) {
	    		var monthArr =	[{title:'1月份',colspan:2},{title:'2月份',colspan:2},{title:'3月份',colspan:2},{title:'4月份',colspan:2},{title:'5月份',colspan:2},{title:'6月份',colspan:2},{title:'7月份',colspan:2},{title:'8月份',colspan:2},{title:'9月份',colspan:2},{title:'10月份',colspan:2},{title:'11月份',colspan:2},{title:'12月份',colspan:2}]
	  			
	  			e.preventDefault();
	  			var grid = $(this);/* grid本身 */
	  			
	  		  	var gridoptions = $(this).datagrid('options');
	  		    
	  			var frozenColumnArray = this.frozenColumn;
	  			var columnArray = this.columnArray;

	  			
	  			var headerContextMenu = this.headerContextMenu;/* grid上的列头菜单对象 */
	  			if (!headerContextMenu|true) {
	  				var fields = grid.datagrid('getColumnFields');
	  				var frozenfields = grid.datagrid('getColumnFields',true);
	  				var tmenu;
	  				if(Number(fields.length)+Number(frozenfields.length)>10){
	  					if($.browser.msie){
	  						tmenu = $('<div style="overflow-y: scroll;width:150px;height:300px;"></div>').appendTo('body');
	  					}else{
	  						tmenu = $('<div class="menuContext"></div>').appendTo('body');
	  					}
	  				}else{
	  					tmenu = $('<div style="width:120px;"></div>').appendTo('body');
	  				}
	  				
	  				var fcolumn = []; 
	  				if(frozenfields.length>0){
	  					var frozenHtml = '<div><span>冻结列</span><div style="width:100px;">';
	  					
	  					for ( var i = 0; i < frozenfields.length; i++) {
	  						var frozenfildOption = grid.datagrid('getColumnOption', frozenfields[i]);
	  						if(fields[i]){
		 		  				if(fields[i].indexOf("realrate")>=0||fields[i].indexOf("saleamount")>=0){
		 		  					continue;
			  					}
	  						}
	  						fcolumn.push(frozenfildOption);
	  						var title = frozenfildOption.title;
	  						if(!frozenfildOption.title){
	  							title = "复选框";
	  						}
	  						if (!frozenfildOption.hidden) {
	  							frozenHtml += '<div iconCls="icon-ok" class="frozen-Column" field="' + frozenfields[i] + '">'+title+'</div>';
	  						} else {
	  							frozenHtml += '<div iconCls="icon-empty" class="frozen-Column" field="' + frozenfields[i] + '">'+title+'</div>';
	  						}
	  					}
	  					frozenHtml +='</div></div>';
	  					$(frozenHtml).appendTo(tmenu);
	  					$('<div class="menu-sep"/>').html("").appendTo(tmenu);
	  				}
	  				var frozenArray = [];
	  				frozenArray.push(fcolumn)
	  				frozenColumnArray = this.frozenColumnArray = frozenArray;
	  				var column = [];
	  				for ( var i = 0; i < fields.length; i++) {
	  					if(fields[i]){
	 		  				if(fields[i].indexOf("realrate")>=0||fields[i].indexOf("saleamount")>=0){
	 		  					continue;
		  					}
  						}
	  					var fildOption = grid.datagrid('getColumnOption', fields[i]);
	  					var colHtml = "";
	  					var title = fildOption.title;
	  					if(!fildOption.title){
	  						title = "复选框";
	  					}
	  					if (!fildOption.hidden) {
	  						colHtml+='<div iconCls="icon-ok" field="' + fields[i] + '"><span>'+title+'</span><div style="width:100px;">'+
	  						'<div iconCls="icon-remove" class="setcolumn-frozen" field="' + fields[i] + '">冻结</div>';
	  					} else {
	  						colHtml+='<div iconCls="icon-empty" field="' + fields[i] + '"><span>'+title+'</span><div style="width:100px;">'+
	  						'<div iconCls="icon-remove" class="setcolumn-frozen" field="' + fields[i] + '">冻结</div>';
	  					}
	  					colHtml +='</div></div>';
	  					$(colHtml).appendTo(tmenu);
	  					column.push(fildOption);
	  				}
	  				var cArray=[];
	  				cArray.push(column)
	  				for(var i = 0;i<monthArr.length;i++){
	  					cArray[0].push(monthArr[i])
	  				}
	  				cArray.push(commonCol[1])
	  				columnArray = this.columnArray = cArray;
	  		        var gridname = grid.context.id ;
	  				headerContextMenu = this.headerContextMenu = tmenu.menu({
	  					onClick : function(item) {
	  						console.log(item.iconCls)
	  						var field = $(item.target).attr('field');
	  						if (item.iconCls == 'icon-ok') {
	  							if($(item.target).hasClass("frozen-Column")){
	  								for(var i=0;i<frozenColumnArray[0].length;i++){
	  									console.log(frozenColumnArray[0][i])
	  									if(frozenColumnArray[0][i].field==field){
	  										columnArray[0].push(frozenColumnArray[0][i]);
	  										frozenColumnArray[0].splice(i,1);
	  									}
	  								}
	  								
	  								grid.datagrid({
	  									frozenColumns:frozenColumnArray,
	  									columns:columnArray
	  								})
	  							}else{
	  								grid.datagrid('hideColumn', field);
	  								$(this).menu('setIcon', {
	  									target : item.target,
	  									iconCls : 'icon-empty'
	  								});
	  							}
	  						}else if(item.iconCls == 'icon-remove'){
	  							for(var i=0;i<columnArray[0].length;i++){
	  								if(columnArray[0][i].field==field){
	  									frozenColumnArray[0].push(columnArray[0][i]);
	  									columnArray[0].splice(i,1);
	  								}
	  							}
	  							for(var i = 0 ;i<frozenColumnArray[0].length;i++){
	  								if(frozenColumnArray[0][i].rowspan){
	  									delete frozenColumnArray[0][i].rowspan
	  								}
	  							}
	  							
	  							grid.datagrid({
	  								frozenColumns:frozenColumnArray,
	  								columns:columnArray
	  							})
	  						}else {
	  							grid.datagrid('showColumn', field);
	  							$(this).menu('setIcon', {
	  								target : item.target,
	  								iconCls : 'icon-ok'
	  							});
	  						}
	  					}
	  				});
	  			}
	  			headerContextMenu.menu('show', {
	  				left : e.pageX,
	  				top : e.pageY
	  			});
	  		};
    	</script>
  
  </body>
</html>
