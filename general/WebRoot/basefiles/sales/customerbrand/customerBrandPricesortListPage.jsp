<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户品牌对应价格套</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" title="" data-options="fit:true,border:true" id="sales-layout-customerBrandPricesort">
	   	<div data-options="region:'west',border:false" style="width:380px;">
	   		<div id="sales-button-custoemrlist">
	   			<form action="" method="post" id="sales-form-custoemrlist">
	   				<table cellpadding="1" cellspacing="0" border="0">
	   					<tr>
	   						<td width="60px">编码:</td>
	   						<td><input type="text" name="id" style="width: 110px"/></td>
	   						<td width="60px">客户名称:</td>
	   						<td>
	   							<input type="text" name="name" style="width: 110px" />
	   						</td>
	   					</tr>
	   					<tr>
	   						<td width="60px">销售区域:</td>
	   						<td><input type="text" id="sale-salesarea-customerlist" name="salesarea" /></td>
	   						<td width="60px">客户分类:</td>
	   						<td>
	   							<input type="text" name="customersort" id="sale-customersort-customerlist"/>
	   						</td>
	   					</tr>
	   					<tr>
	   						<td width="60px">品牌名称:</td>
	   						<td><input type="text" id="sale-brand-customerlist" name="brandpricesort" /></td>
	   						<td colspan="2" style="text-align: right;">
	   							<a href="javaScript:void(0);" id="sales-query-customerlist" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" title="[Alt+Q]查询">查询</a>
			    				<a href="javaScript:void(0);" id="sales-reload-customerlist" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" title="[Alt+R]重置">重置</a>
	   						</td>
	   					</tr>
	   				</table>
	   			</form>
	   		</div>
	   		<table id="sales-table-customerlist"></table>
	   	</div>
	   	<div data-options="region:'center',border:false">
	   		<table id="sales-table-brandPricesortlist"></table>
	   		<div id="sales-button-div" style="height:26px;">
				<security:authorize url="/sales/customerBrandPricesortAddBtn.do">
					<a href="javaScript:void(0);" id="sales-add-customerBrandPricesort" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
				</security:authorize>
				<security:authorize url="/sales/customerBrandPricesortSaveBtn.do">
					<a href="javaScript:void(0);" id="sales-save-customerBrandPricesort" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>
				 </security:authorize>
				 <security:authorize url="/sales/customerBrandPricesortDelBtn.do">
				 	<a href="javaScript:void(0);" id="sales-delete-customerBrandPricesort" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
				</security:authorize>
			</div>
	   	</div>
	</div>
	<div id="sales-div-customerBrandPricesort"></div>
	<script type="text/javascript">
		var $brandPricesortlist = $('#sales-table-brandPricesortlist');
		var editIndex = undefined;  
		var editfiled = null;
		var clickcustomerid = "";
		function endEditing(field){
			if (editIndex == undefined){
				return true
			}
			var row = $brandPricesortlist.datagrid('getRows')[editIndex];
			var pricesort = row.pricesort;
			if(field == "pricesort"){
				var ed = $brandPricesortlist.datagrid('getEditor', {index:editIndex,field:"pricesort"});
				pricesort = $(ed.target).widget("getValue");
				if(undefined != pricesort && "" != pricesort){
					$brandPricesortlist.datagrid('endEdit', editIndex);
				}else{
					return false;
				}
			}
			if(row.initpricesort != pricesort){
				row.isedit = "1";
			}else{
				row.isedit = "0";
			}
			$brandPricesortlist.datagrid('updateRow',{index:editIndex, row:row});
			if(row.isedit == "1"){
				$brandPricesortlist.datagrid('checkRow',editIndex);
			}else{
				$brandPricesortlist.datagrid('uncheckRow',editIndex);
			}
			editIndex = undefined;
			return true;
		}
		
		function onClickCell(index, field){
			if (endEditing(editfiled)){
				editfiled = field;
				if(field == "pricesort"){
					$brandPricesortlist.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
					editIndex = index;
					 var ed = $brandPricesortlist.datagrid('getEditor', {index:editIndex,field:field});
					$(ed.target).focus();
	                $(ed.target).select(); 
				}
			}
		}
		$(function(){
			 //客户列表
		     $('#sales-table-customerlist').datagrid({
			    fit:true,
				title:'客户列表',
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				pageSize:100,
				singleSelect:true,
				checkOnSelect:true,
				selectOnCheck:true,
				toolbar:"#sales-button-custoemrlist",
				frozenColumns:[[{field:'ck',width:60,checkbox:true}]],
				columns:[[
	  	 			{field:'id',title:'编码',width:70,sortable:true},
	    			{field:'name',title:'客户名称',width:120,sortable:true},
	    			{field:'pricesort',title:'默认价格套',width:80,sortable:true,
	    				formatter: function(value,row,index){
							return getSysCodeName("price_list",value);
						}
	    			},
	    			{field:'pid',title:'上级客户',width:80,sortable:true,
	    				formatter:function(val,rowData,rowIndex){
							return rowData.pname;
						}
	    			}
	  	 		]],
	  	 		onClickRow:function(index,row){
	  	 			var customerid = row.id;
	  	 			clickcustomerid = row.id;
	  	 			$('#sales-table-brandPricesortlist').datagrid({
	  	 				pageNumber:1,
		      			url: 'basefiles/showCustomerBrandPricesort.do?customerid='+customerid
	  	 			});
	  	 		}
			});
		   //品牌价格套
			$('#sales-table-brandPricesortlist').datagrid({
			    fit:true,
				title:'品牌列表',
				method:'post',
				sortName:'brandid',
				sortOrder:'asc',
				rownumbers:true,
				singleSelect:true,
				toolbar:"#sales-button-div",
				rowStyler: function(index,row){
					if (row.isedit == '1'){
						return 'background-color:rgb(190, 250, 241);';
					}
				},
				columns:[[
					{field:'ck',checkbox:true},
	  	 			{field:'id',title:'编码',hidden:true},
	  	 			{field:'brandid',title:'品牌编码',width:80},
					{field:'brandname',title:'品牌名称',width:80},
					{field:'deptname',title:'品牌部门',width:80},
					{field:'pricesort',title:'价格套',width:90,
						formatter: function(value,row,index){
							return getSysCodeName("price_list",value);
						},
						editor:{
		  					type:'comborefer',
		        		  	options:{
		        		  		referwid:'RL_T_SYS_CODE_PRICELIST',
								singleSelect:true,
								width:90,
								required:true,
								onlyLeafCheck:false
				    		}
		  				}
					},
					{field:'remark',title:'备注',width:100}
	  	 		]],
	  	 		onClickCell: function(index, field, value){
    				<security:authorize url="/basefiles/goodsAndPriceCustmerEditBtn.do">
    					onClickCell(index, field);
    				</security:authorize>
    			}
			});
			
			$(document).die("keydown").live("keydown",function(event){
				if(event.ctrlKey){
					$brandPricesortlist.datagrid('clearSelections');
					$brandPricesortlist.datagrid('selectRow',editIndex+1);
  					onClickCell(editIndex+1, editfiled);
				}
			});
			
			//客户分类
			$("#sale-customersort-customerlist").widget({
				referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
    			singleSelect:true,
    			width:100,
    			onlyLeafCheck:true
			});
			$("#sale-salesarea-customerlist").widget({
				referwid:'RT_T_BASE_SALES_AREA',
    			singleSelect:true,
    			width:100,
    			onlyLeafCheck:false
			});
			$("#sale-brand-customerlist").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
    			singleSelect:true,
    			width:110,
    			onlyLeafCheck:false
			});
			
			//查询
			$("#sales-query-customerlist").click(function(){
	      		var queryJSON = $("#sales-form-custoemrlist").serializeJSON();
	      		$('#sales-table-customerlist').datagrid('options').url = 'basefiles/getCustomerListForPact.do?type=pricesort';
	      		$("#sales-table-customerlist").datagrid("load",queryJSON);
			});
			
			//重置
			$("#sales-reload-customerlist").click(function(){
				$("#sales-form-custoemrlist")[0].reset();
				$("#sale-customersort-customerlist").widget('clear');
				$("#sale-salesarea-customerlist").widget('clear');
				$("#sale-brand-customerlist").widget('clear');
				$('#sales-table-customerlist').datagrid('loadData', { total: 0, rows: [] });
			});
			
			//新增
			$("#sales-add-customerBrandPricesort").click(function(){
				$("#sales-div-customerBrandPricesort").dialog({ 
		    		title:'客户品牌价格套新增或覆盖',
		    		width:600,
		    		height:450,
		    		closed:false,
		    		modal:true,
		    		cache:false,
		    		maximizable:true,
		    		resizable:true,
		    		fit:true,
		    		href:'basefiles/showCustomerBrandPricesortAddPage.do'
		    	});
			});
			
			//保存
			$("#sales-save-customerBrandPricesort").click(function(){
				endEditing("pricesort");
				var rows = $brandPricesortlist.datagrid('getChecked');
				for(var i=0;i<rows.length;i++){
					if(rows[i].isedit != "1"){
						var index = $brandPricesortlist.datagrid('getRowIndex',rows[i]);
						$brandPricesortlist.datagrid('uncheckRow',index);
					}
				}
				rows = $brandPricesortlist.datagrid('getChecked');
				if(rows.length == 0){
					$.messager.alert("提醒","请勾选已修改的数据!");
					return false;
				}
				loading('提交中...');
				$.ajax({
		            url :'basefiles/editCustomerBrandPricesort.do',
		            type:'post',
		            dataType:'json',
		            data:{rowsjsonstr:JSON.stringify(rows)},
		            success:function(json){
		            	loaded();
		            	$.messager.alert("提醒",json.msg);
		            	$brandPricesortlist.datagrid('reload');
		            }
		        });
			});
			
			//删除
			$("#sales-delete-customerBrandPricesort").click(function(){
				var rows = $brandPricesortlist.datagrid('getChecked');
				if(null == rows || rows.length == 0){
					$.messager.alert("提醒","请勾选要删除的品牌价格套！");
					return false;
				}
				var idstr = "";
				for(var i=0;i<rows.length;i++){
					if(idstr == ""){
						idstr = rows[i].id;
					}else{
						idstr += "," + rows[i].id;
					}
				}
				$.messager.confirm("提醒","是否删除品牌价格套?",function(r){
		  			if(r){
		  				loading("删除中..");
			  			$.ajax({
				  			url:'basefiles/deleteCustomerBrandPricesorts.do',
				  			data:{idstr:idstr,customerid:clickcustomerid},
				  			dataType:'json',
				  			type:'post',
				  			success:function(json){
				  				loaded();
				  				$.messager.alert("提醒",json.msg);
				  				$brandPricesortlist.datagrid('reload');
				  			}
			  			});
		  			}
		  		});
			});
		});
	</script>
  </body>
</html>
