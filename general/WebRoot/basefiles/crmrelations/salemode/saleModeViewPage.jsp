<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售方式详情页面</title>
  </head>
  
  <body class="easyui-layout">
     <div region="north" data-options="border:true" style="height:50px;padding: 10px">
     	<div style="border: 1px black solid;">
     		<table cellpadding="2" cellspacing="2" border="0px">
	    		<tr>
	    			<td width="80px" align="right">编码:</td>
	    			<td><input id="saleMode-id-saleModeDetail" type="text" style="width: 120px" readonly="readonly" <c:if test="${showMap.id!=null}">value="<c:out value="${saleMode.id}"></c:out>"</c:if> />
	    				<input id="saleMode-id-hdSaleModeDetail" type="hidden" value="<c:out value="${saleMode.id }"></c:out>"/>
	    			</td>
	    			<td width="80px" align="right">名称:</td>
	    			<td><input type="text" style="width: 120px" readonly="readonly" <c:if test="${showMap.name!=null}">value="<c:out value="${saleMode.name}"></c:out>"</c:if>/></td>
	    			<td width="80px" align="right">状态:</td>
	    			<td><input id="common-combobox-state" type="text" value="${saleMode.state }" disabled="disabled" class="easyui-combobox" style="width: 120px" /></td>
	    		</tr>
	    		<tr>
	    			<td width="80px" align="right">备注:</td>
	    			<td colspan="5">
	    				<input type="text" style="width: 650px;" readonly="readonly" <c:if test="${showMap.remark!=null}">value="<c:out value="${saleMode.remark}"></c:out>"</c:if>/>
	    			</td>
	    		</tr>
	    	</table>
     	</div>
    </div>  
    <div region="center" data-options="fit:true,border:false" style="border: 0px;padding: 10px;">
    	<table id="saleMode-table-saleModeDetailList"></table>
    </div>
    <script type="text/javascript">
    	loadDropdown();
    	
    	$(function(){
    		$("#salemode-button-layout").buttonWidget("setDataID",{id:$("#saleMode-id-hdSaleModeDetail").val(),state:"${saleMode.state}",type:'view'});
    	
    		$dgSaleModeDetailList = $("#saleMode-table-saleModeDetailList");
			$dgSaleModeDetailList.datagrid({
	   			method:'post',
	   			title:'',
	  			singleSelect:true,
	  			url:'basefiles/crmrelations/showSaleModeDetailList.do?salemodeid='+$("#saleMode-id-hdSaleModeDetail").val(),
	  			border:false,
	  			pageSize:4,
	   			columns:[[
	   				{field:'id',title:'编号',hidden:true},
	   				{field:'salemodeid',title:'销售方式编码',width:150,hidden:true},
	   				{field:'code',title:'销售阶段编码',width:80,align:'center'},
	   				{field:'name',title:'销售阶段名称',width:100,align:'center'},
	  				{field:'stage',title:'阶段',width:120,align:'center',
	  					editor:{
	  						type:'selectboxText',
	  						options:{
	  							vals:'1,2,3,4',
		  						texts:'A-发现销售机会,B-处理交易,C-正在关闭,D-失去的交易',
		  						defaultChecked:'1'
	  						}
	  					},
	  					formatter:function(val,rowData,rowIndex){
	  						switch(val){
	  							case '1':
	  								return "A-发现销售机会";
	  							case '2':
	  								return "B-处理交易";
	  							case '3':
	  								return "C-正在关闭";
	  							case '4':
	  								return "D-失去的交易";
	  						}
	  					}
	  				},
	  				{field:'probability',title:'成功的概率%',width:80,align:'right',editor:{type:'numberbox',options:{precision:2,max:99,min:0}},
	  					formatter:function(val,rowData,rowIndex){
	  						if(val != "" && val != null){
	  							return formatterMoney(val);
	  						}
	  					}
	  				},
	  				{field:'remark',title:'备注',width:150,align:'center',editor:'text'}
	   			]]
	   		});
    	});
    </script>
  </body>
</html>
