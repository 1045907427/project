<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌对应商品</title>
  </head>
  
  <body>
  	<input type="hidden" value="${brandid }"/>
    <div class="easyui-layout" data-options="fit:true" id="goods-layout-brandtogoods">
	    <div data-options="region:'center',split:true,border:true">
	    	<p>备注:<input id="personnel-remark-brandtogoods" style="width: 180px;" value="${remark }"/></p>
	    	<table id="goods-table-brandtogoods"></table>
		</div>
		<div data-options="region:'south'" style="height: 30px;" align="right">
			<a href="javaScript:void(0);" id="personnel-save-brandtogoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定">确定</a>
		</div> 
     </div>
    <script type="text/javascript">
    	$(function(){
    		var brandid3 = "";
   			var brandRows = $dgPersonnelBrand.datagrid('getRows');
   			if(brandRows.length != 0){
   				for(var i=0;i<brandRows.length;i++){
    				brandid3 += brandRows[i].brandid + ",";
    			}
   			}
   			brandid3 = brandid3.replace("${brandid},","");
   			
			$("#goods-table-brandtogoods").datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
				queryParams:{brandid:brandid3},
				pageSize:500,
				columns:[[  
			        {field:'ck',title:'',width:100,checkbox:true},  
			        {field:'id',title:'编码',width:100},  
			        {field:'name',title:'名称',width:320}
			    ]],
			    url:'basefiles/getBrandListForCombobox.do',
			    onLoadSuccess:function(param){
			    	var row = $("#goods-table-brandtogoods").datagrid('selectRecord',"${brandid}").datagrid('getSelected');
	    			var index = $("#goods-table-brandtogoods").datagrid('getRowIndex',row);
	    			$("#goods-table-brandtogoods").datagrid('checkRow',index);
		    		$("#goods-table-brandtogoods").datagrid('clearSelections');
			    }
			});
    		
    		$("#personnel-save-brandtogoods").click(function(){
    			var rows = $("#goods-table-brandtogoods").datagrid('getChecked');
    			if(rows.length == 0){
    				$.messager.alert("提醒","请勾选对应品牌对应的商品名称！");
    				return false;
    			}
    			var brandid = "";
    			for(var i=0;i<rows.length;i++){
    				brandid += rows[i].id + ",";
    			}
    			var ret = personnel_AjaxConn({brandid:JSON.stringify(rows),brandrow:JSON.stringify(brandRows),remark:$("#personnel-remark-brandtogoods").val()},'basefiles/showBrandListData.do','提交中..');
				var retJson = $.parseJSON(ret);
				$dgPersonnelBrand.datagrid('loadData',retJson.brandList);
				$("#personnel-dialog-goodsids").dialog('close',true);
    		});
    	});
    </script>
  </body>
</html>
