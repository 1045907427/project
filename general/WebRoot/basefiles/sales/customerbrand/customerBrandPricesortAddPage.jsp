<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>合同商品新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'north',border:false" style="height: 50px;">
  			<form action="basefiles/addCustomerBrandPricesort.do" method="post" id="sales-form-customerBrandSortAdd">
		    	<table cellpadding="3" cellspacing="3" border="0">
		    		<tr>
		    			<td><select id="sales-selecttype-customerBrandSortAdd" class="easyui-combobox">
		    				<option value="1">门店名称</option>
		    				<option value="2">总店</option>
		    				<option value="3">销售区域</option>
		    				<option value="4">客户分类</option>
		    			</select></td>
		    			<td><div id="sales-selecttypediv-customerBrandSortAdd">
			    				<input type="text" id="sales-customerid-customerBrandSortAdd" name="customerid"/>
			    			</div>
		    			</td>
		    			<td>价格套：</td>
		    			<td>
		    				<input type="text" id="sales-pricesort-customerBrandSortAdd" name="pricesort"/>
		    				<input type="hidden" id="sales-brandids-customerBrandSortAdd" name="brandids"/>
		    			</td>
		    			<td>备注：</td>
		    			<td>
		    				<input type="text" name="remark" style="width:200px;"/>
		    			</td>
		    		</tr>
		    	</table>
				<hr/>
		    </form>
  		</div>
  		<div data-options="region:'center',border:false">
  			<table id="sales-table-customerBrandSortAdd"></table>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				<input type="button" value="保存并继续新增" id="sales-savegoon-customerBrandSortAdd" />
	  			<input type="button" value="保存" id="sales-save-customerBrandSortAdd" />
  			</div>
  		</div>
  	</div>
  	<div id="sales-querybutton-customerBrandSortAdd">
  		<form action=""  method="post" id="sales-form-queryBrand">
  			<table cellpadding="2" cellspacing="2" border="0">
  				<tr>
  					<td>品牌部门：</td>
  					<td>
  						<input type="text" id="sales-branddeptid-queryBrand" name="deptid"/>
  					</td>
  					<td>供应商：</td>
  					<td>
  						<input type="text" id="sales-supplierid-queryBrand" name="supplierid"/>
  					</td>
  					<td>
  						<a href="javaScript:void(0);" id="sales-form-queryBrand-querybutton" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">查询</a>
			    		<a href="javaScript:void(0);" id="sales-form-queryBrand-reloadbutton" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'">重置</a>
  					</td>
  				</tr>
	    	</table>
  		</form>
  	</div>
    <script type="text/javascript">
    //保存并继续新增
    function savegoon(){
    	var rows = $("#sales-table-customerBrandSortAdd").datagrid('getChecked');
    	var brandids = "";
    	for(var i=0;i<rows.length;i++){
    		if(brandids==''){
    			brandids = rows[i].id;
    		}else{
    			brandids += ","+rows[i].id;
    		}
    	}
    	$("#sales-brandids-customerBrandSortAdd").val(brandids);
    	var queryJSON = $("#sales-form-customerBrandSortAdd").serializeJSON();
		loading("提交中..");
		$.ajax({
			url:'basefiles/addCustomerBrandPricesort.do',
			data:queryJSON,
			dataType:'json',
			type:'post',
			success:function(json){
				loaded();
				if(json.flag){
					$.messager.alert("提醒","保存成功");
					$("#sales-form-queryBrand")[0].reset();
					$("#sales-branddeptid-queryBrand").widget('clear');
					$("#sales-supplierid-queryBrand").widget('clear');
					var query = $("#sales-form-queryBrand").serializeJSON();
					$("#sales-table-customerBrandSortAdd").datagrid('load',query);

					$("#sales-form-customerBrandSortAdd")[0].reset();
					var type = $("#sales-selecttype-customerBrandSortAdd").combobox('getValue');
					if(type == "1"){
						$("#sales-customerid-customerBrandSortAdd").widget('clear');
					}else if(type == "2"){
						$("#sales-pcustomerid-customerBrandSortAdd").widget('clear');
					}else if(type == "3"){
						$("#sales-salesarea-customerBrandSortAdd").widget('clear');
					}else if(type == "4"){
						$("#sales-customersort-customerBrandSortAdd").widget('clear');
					}
					$("#sales-selecttype-customerBrandSortAdd").combobox('setValue','1');

					$("#sales-table-customerlist").datagrid("reload");
					$('#sales-table-brandPricesortlist').datagrid('loadData', { total: 0, rows: [] });
				}else{
					$.messager.alert("提醒","保存失败");
				}
			}
		});
    }
    
    //新增
    function save(){
    	var rows = $("#sales-table-customerBrandSortAdd").datagrid('getChecked');
    	var brandids = "";
    	for(var i=0;i<rows.length;i++){
    		if(brandids==''){
    			brandids = rows[i].id;
    		}else{
    			brandids += ","+rows[i].id;
    		}
    	}
    	$("#sales-brandids-customerBrandSortAdd").val(brandids);
    	var queryJSON = $("#sales-form-customerBrandSortAdd").serializeJSON();
		loading("提交中..");
		$.ajax({
			url:'basefiles/addCustomerBrandPricesort.do',
			data:queryJSON,
			dataType:'json',
			type:'post',
			success:function(json){
				loaded();
				if(json.flag){
					$.messager.alert("提醒","保存成功");
					$("#sales-div-customerBrandPricesort").dialog("close");

					$("#sales-table-customerlist").datagrid("reload");
					$('#sales-table-brandPricesortlist').datagrid('loadData', { total: 0, rows: [] });
				}else{
					$.messager.alert("提醒","保存失败");
				}
			}
		});
    }
    $(function(){
    	$("#sales-table-customerBrandSortAdd").datagrid({
		    fit:true,
			method:'post',
			sortName:'id',
			sortOrder:'asc',
			rownumbers:true,
			singleSelect:false,
			checkOnSelect:true,
			selectOnCheck:true,
			pagination:true,
			pageSize:500,
			toolbar:"#sales-querybutton-customerBrandSortAdd",
			url:'basefiles/getBrandListPage.do',
			columns:[[
				{field:'ck',checkbox:true},
  	 			{field:'id',title:'品牌编码'},
				{field:'name',title:'品牌名称',width:80},
				{field:'deptid',title:'所属部门',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.deptName;
					}
				},
				{field:'supplierid',title:'所属供应商',width:200,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierName;
					}
				},
				{field:'remark',title:'备注',width:100}
  	 		]]
		});
		
		$("#sales-selecttype-customerBrandSortAdd").combobox({
			onChange:function(newValue, oldValue){
				if(newValue == "1"){
					var html = '<input type="text" id="sales-customerid-customerBrandSortAdd" name="customerid"/>';
					$("#sales-selecttypediv-customerBrandSortAdd").html(html);
					$("#sales-customerid-customerBrandSortAdd").widget({
			    		referwid:'RL_T_BASE_SALES_CUSTOMER',
						singleSelect:false,
						required:true,
						width:150
			    	});
				}
				else if(newValue == "2"){
					var html = '<input type="text" id="sales-pcustomerid-customerBrandSortAdd" name="pcustomerid"/>';
					$("#sales-selecttypediv-customerBrandSortAdd").html(html);
					$("#sales-pcustomerid-customerBrandSortAdd").widget({
			    		referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
						singleSelect:false,
						required:true,
						width:150
			    	});
				}
				else if(newValue == "3"){
					var html = '<input type="text" id="sales-salesarea-customerBrandSortAdd" name="salesarea"/>';
					$("#sales-selecttypediv-customerBrandSortAdd").html(html);
					$("#sales-salesarea-customerBrandSortAdd").widget({
			    		referwid:'RT_T_BASE_SALES_AREA',
						singleSelect:false,
						required:true,
						width:150,
						onlyLeafCheck:false
			    	});
				}
				else if(newValue == "4"){
					var html = '<input type="text" id="sales-customersort-customerBrandSortAdd" name="customersort"/>';
					$("#sales-selecttypediv-customerBrandSortAdd").html(html);
					$("#sales-customersort-customerBrandSortAdd").widget({
			    		referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
						singleSelect:false,
						required:true,
						width:150,
						onlyLeafCheck:false
			    	});
				}
			},
			onLoadSuccess:function(){
				$("#sales-customerid-customerBrandSortAdd").widget({
		    		referwid:'RL_T_BASE_SALES_CUSTOMER',
					singleSelect:false,
					required:true,
					width:150 ,
					onlyLeafCheck:false
		    	});
			}
		});
    	
    	$("#sales-pricesort-customerBrandSortAdd").widget({
    		referwid:'RL_T_SYS_CODE_PRICELIST',
			singleSelect:true,
			width:100 ,
			required:true,
			onlyLeafCheck:false
    	});
    	$("#sales-branddeptid-queryBrand").widget({
    		referwid:'RL_T_BASE_DEPARTMENT_BUYER',
			singleSelect:true,
			width:130 ,
			onlyLeafCheck:false
    	});
    	$("#sales-supplierid-queryBrand").widget({
    		referwid:'RL_T_BASE_BUY_SUPPLIER',
			singleSelect:true,
			width:130 ,
			onlyLeafCheck:false
    	});
    	$("#sales-form-queryBrand-querybutton").click(function(){
    		var queryJSON = $("#sales-form-queryBrand").serializeJSON();
    		$("#sales-table-customerBrandSortAdd").datagrid("load",queryJSON);
    	});
    	$("#sales-form-queryBrand-reloadbutton").click(function(){
    		$("#sales-branddeptid-queryBrand").widget("clear");
    		$("#sales-supplierid-queryBrand").widget("clear");
    		$("#sales-form-queryBrand").form("reset");
			var queryJSON = $("#sales-form-queryBrand").serializeJSON();
    		$("#sales-table-customerBrandSortAdd").datagrid("load",queryJSON);
    	});
		
		//保存并继续新增
		$("#sales-savegoon-customerBrandSortAdd").click(function(){
			if(!$("#sales-form-customerBrandSortAdd").form('validate')){
				$.messager.alert('提醒',"有必填项未填写!");
   				return false;
   			}
			$.messager.confirm("提醒","是否保存?",function(r){
		    	if(r){
		    		savegoon();
		    	}
	    	});
		});
		//新增
    	$("#sales-save-customerBrandSortAdd").click(function(){
    		if(!$("#sales-form-customerBrandSortAdd").form('validate')){
				$.messager.alert('提醒',"有必填项未填写!");
   				return false;
   			}
    		$.messager.confirm("提醒","是否保存?",function(r){
		    	if(r){
		    		save();
		    	}
	    	});	
     	});
    });
    </script>
  </body>
</html>
