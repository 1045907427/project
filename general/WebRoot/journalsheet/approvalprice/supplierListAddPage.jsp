<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>初始化供应商列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
   <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="finance-table-supplierList"></table>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="finance-button-initsupplier" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="确定">确定</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	var approvalPrice_AjaxConn = function (Data, Action, Str) {
    		if(null != Str && "" != Str){
    			loading(Str);
    		}
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
    	var approvalPrice_supplier = "";
    	
	    $('#finance-table-supplierList').datagrid({
	    	fit:true,
  	 		method:'post',
  	 		rownumbers:true,
  	 		idField:'id',
		    url:'journalsheet/approvalPrice/getSupplierListForApproval.do',
		    frozenColumns:[[{field:'ck',checkbox:true}]],
		    columns:[[  
		        {field:'id',title:'供应商编码',width:100},  
		        {field:'name',title:'供应商名称',width:320}
		    ]],
		    onCheckAll:function(rows){
		    	for(var i=0;i<rows.length;i++){
		    		approvalPrice_supplier += rows[i].id + ",";
		    	}
		    },
		    onCheck:function(rowIndex,rowData){
		    	approvalPrice_supplier += rowData.id + ",";
		    }
		});
		
		//确定按钮
		$("#finance-button-initsupplier").click(function(){
			var ret = approvalPrice_AjaxConn({idarrs:approvalPrice_supplier},'journalsheet/approvalPrice/addApprovalPrice.do','提交中..');
			var retJson = $.parseJSON(ret);
			if(retJson.flag){
				$('#approvalPrice-dialog-supplier').dialog('close');
				$("#finance-table-approvalPrice").datagrid("reload");
			}
			else{
				$.messager.alert("提醒","初始化失败!");
			}
		});
    </script>
  </body>
</html>
