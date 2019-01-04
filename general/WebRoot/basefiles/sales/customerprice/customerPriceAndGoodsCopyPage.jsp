<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>合同商品复制页面</title>
  </head>
  <body>
  	<table id="sales-datagrid-customerprice"></table>
  	<div id="sales-queryDiv-customerprice">
		<form id="sales-queryform-customerprice">
			<table cellpadding="0" cellspacing="0" border="0">
				<tr>
					<td>客户名称：</td>
					<td><input type="text" name="id" id="sales-customerid-customerprice"/></td>
					<td>销售区域：</td>
					<td><input type="text" name="salesarea" id="sales-salesarea-customerprice" /></td>
					<td>总店：</td>
					<td><input type="text" name="pid" id="sales-pid-customerprice"/></td>
				</tr>
				<tr>
					<td>助记符：</td>
					<td><input type="text" name="shortcode" style="width: 150px;"/></td>
					<td>客户分类：</td>
					<td><input type="text" name="customersort" id="sales-customersort-customerprice" /></td>
					<td colspan="4">
						<a href="javascript:;" class="button-qr" id="sales-query-customerprice">查询</a>
		  				<a href="javaScript:;" class="button-qr" id="sales-reset-customerprice">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function(){
			$.extend($.messager.defaults,{  
			    ok:"覆盖",  
			    cancel:"不覆盖"  
			});

			//客户
			$("#sales-customerid-customerprice").widget({
				width:150,
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
				param:[{field:'id',op:'notequal',value:'${customerid}'}]
			});
			//总店
			$("#sales-pid-customerprice").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
				width:170,
				singleSelect:false,
				onlyLeafCheck:true
			});
			//销售区域
			$("#sales-salesarea-customerprice").widget({
				referwid:'RT_T_BASE_SALES_AREA',
				width:120,
				singleSelect:false,
				onlyLeafCheck:false
			});
			//客户分类
			$("#sales-customersort-customerprice").widget({
				referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
				width:120,
				singleSelect:false,
				onlyLeafCheck:false
			});
			
			$("#sales-datagrid-customerprice").datagrid({
				frozenColumns:[[{field:'ck',checkbox:true}]],
				columns:[[
			        {field:'id',title:'编码',sortable:true,width:60},
     				{field:'name',title:'客户名称',sortable:true,width:180},
     				{field:'pid',title:'上级客户',sortable:true,width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.pname;
						}
     				},
     				{field:'shortcode',title:'助记符',sortable:true,width:60},
     				{field:'salesarea',title:'所属区域',sortable:true,width:60,
     					formatter:function(val,rowData,rowIndex){
							return rowData.salesareaname;
						}
     				},
     				{field:'customersort',title:'所属分类',sortable:true,width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.customersortname;
						}
     				},
     				{field:'state',title:'状态',sortable:true,width:40,
     					formatter:function(value,row,index){
				        	return row.statename;
					    }
     				}
			    ]],
			    fit:true,
				title:'',
				pageSize:500,
				idField:'id',
				border:false,
				rownumbers:true,
				pagination:true,
				singleSelect:false,
				checkOnSelect:true,
				selectOnCheck:true,
				url:'basefiles/getCustomerListForCustomerprice.do?customerid=${customerid}',
				toolbar:"#sales-queryDiv-customerprice"
			});
			
			//查询
			$("#sales-query-customerprice").click(function(){
				var queryJSON = $("#sales-queryform-customerprice").serializeJSON();
	       		$("#sales-datagrid-customerprice").datagrid('load', queryJSON);
			});
			//重置
			$("#sales-reset-customerprice").click(function(){
				$("#sales-customerid-customerprice").widget('clear');
				$("#sales-pid-customerprice").widget('clear');
				$("#sales-salesarea-customerprice").widget('clear');
				$("#sales-customersort-customerprice").widget('clear');
				$("#sales-queryform-customerprice")[0].reset();
				var queryJSON = $("#sales-queryform-customerprice").serializeJSON();
				$("#sales-datagrid-customerprice").datagrid('load',queryJSON);
			});
		});
		
		function copyCustomerPrice(goodspriceids,goodsids){
			if(goodspriceids != ""){
				var customerrows = $("#sales-datagrid-customerprice").datagrid('getChecked');
				if(customerrows.length == 0){
					$.messager.alert("提醒","请勾选要复制的客户");
					return false;
				}
				var customerids = "";
				for(var i=0;i<customerrows.length;i++){
					if(customerids == ""){
						customerids = customerrows[i].id;
					}else{
						customerids += "," + customerrows[i].id;
					}
				}
				var ret = pricecustmer_AjaxConn({goodsids:goodsids,customerids:customerids},'basefiles/doCheckIsExistCustomerPrice.do');
				var retjson = $.parseJSON(ret);
				if(retjson.flag){
					$.messager.confirm("提醒","客户："+retjson.retcustomerids+"中存在相同的合同商品,是否覆盖?",function(r){
						if(r){
							doneCopyCustomerPrice(goodspriceids,goodsids,customerids,"1");
						}else{
							doneCopyCustomerPrice(goodspriceids,goodsids,customerids,"0");
						}
					});
				}else{
					doneCopyCustomerPrice(goodspriceids,goodsids,customerids,"1");
				}
			}
		}
		
		//执行复制合同商品
		function doneCopyCustomerPrice(goodspriceids,goodsids,customerids,type){
			loading('操作中...');
			var ret = pricecustmer_AjaxConn({goodspriceids:goodspriceids,goodsids:goodsids,customerids:customerids,type:type},'basefiles/doCopyCustomerPrice.do');
			var json = $.parseJSON(ret);
			loaded();
			if(json.flag){
				$.messager.alert("提醒","操作成功!");
				$("#sales-dialog-goodsandpricecustmer1").dialog('close')
			}else{
				$.messager.alert("提醒","操作失败!");
			}
		}
	</script>
  </body>
</html>
