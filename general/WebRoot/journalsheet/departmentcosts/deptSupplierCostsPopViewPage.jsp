<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商费用摊派页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  		<div id="departmentCosts-table-deptSupplierCostsBtn">
	  		<form action="" id="departmentCosts-form-deptSupplierCostsListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
	   			<table cellpadding="0" cellspacing="1" border="0">
	   				<tr>
	   					<td style="padding-left: 10px;">所属供应商:&nbsp;</td>
	   					<td><input id="departmentCosts-widget-deptSupplierCosts-supplierquery" name="supplierid" type="text" style="width: 180px;"/></td>	   				    					
	   					<td>
	   						<a href="javaScript:void(0);" id="departmentCosts-query-deptSupplierCostsList" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
				    		<a href="javaScript:void(0);" id="departmentCosts-query-deptSupplierCosts-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
	   					</td>
	   				</tr>
	   			</table>
	   			<input type="hidden" name="deptcostsid" value="${deptcostsid}"/>
	   		</form>
  		</div>
  		<table id="departmentCosts-table-deptSupplierCosts"></table>
  		<div style="display:none">
   		<div id="departmentCosts-dialog-operate"></div>
   	</div>
    <script type="text/javascript">
		var footerobject=null;
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var costsFeeListColJson=$("#departmentCosts-table-deptSupplierCosts").createGridColumnLoad({
	     	//name:'t_js_departmentcosts',
	     	frozenCol:[[
				{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'deptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.deptname;
					}
				},
				{field:'supplierid',title:'供应商编号',width:65,sortable:true},
				{field:'suppliername',title:'供应商名称'}
				<c:forEach items="${deptCostsSubjectList }" var="list">
				  ,{field:'deptCostsSubject_${list.code}',title:'${list.name}<c:if test="${list.state !=1}">(<b style="color:#f00">禁用</b>)</c:if>',align:'right',resizable:true,isShow:true,
				  	formatter:function(value,rowData,rowIndex){
		  				return formatterMoney(value);	        		
		        	},
		        	styler:function(value,row,index){
		        		<c:if test="${list.state !=1}">
		        		return 'background-color:#ffee00;color:red;';
		        		</c:if>
		        	}
				  }
				</c:forEach>				
			]]
	     });
	     
		$(function(){
			var initQueryJSON = $("#departmentCosts-form-deptSupplierCostsListQuery").serializeJSON()
     		$("#departmentCosts-table-deptSupplierCosts").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'desc',
	  	 		pagination:true,
		 		idField:'supplierid',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		queryParams:initQueryJSON,
				pageSize:20,
				toolbar:'#departmentCosts-table-deptSupplierCostsBtn',
				pageList:[20,50,200,300,500],
				url: 'journalsheet/costsFee/getDeptSupplierCostsPageData.do',
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			deptCostsCountTotalAmount();
				},
				onUncheckAll:function(){
					deptCostsCountTotalAmount();
				},
				onCheck:function(){
					deptCostsCountTotalAmount();
				},
				onUncheck:function(){
					deptCostsCountTotalAmount();
				}
			}).datagrid("columnMoving");

     		$("#departmentCosts-widget-deptSupplierCosts-supplierquery").widget({ 
				name:'t_js_departmentcosts_detail',
				col:'supplierid'
			});

     		//查询
			$("#departmentCosts-query-deptSupplierCostsList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#departmentCosts-form-deptSupplierCostsListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#departmentCosts-table-deptSupplierCosts").datagrid('load',queryJSON);	
			});
			
			//重置按钮
			$("#departmentCosts-query-deptSupplierCosts-reloadList").click(function(){
				$("#departmentCosts-form-deptSupplierCostsListQuery")[0].reset();
				$("#departmentCosts-widget-deptSupplierCosts-supplierquery").widget('clear');

	      		var queryJSON = $("#departmentCosts-form-deptSupplierCostsListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#departmentCosts-table-deptSupplierCosts").datagrid('load',queryJSON);
			});
			
		});
		
		function deptCostsCountTotalAmount(){
    		var rows =  $("#departmentCosts-table-deptSupplierCosts").datagrid('getChecked');
    		if(null==rows || rows.length==0){
           		var foot=[];
    			if(null!=footerobject){
	        		foot.push(emptyChooseObjectFoot());
	        		foot.push(footerobject);
	    		}
    			$("#departmentCosts-table-deptSupplierCosts").datagrid("reloadFooter",foot);
           		return false;
       		}
    		
    		<c:forEach items="${deptCostsSubjectList }" var="list">
    			var deptCostsSubject_${list.code}=0;
    		</c:forEach>
    		
    		for(var i=0;i<rows.length;i++){
    			
        		<c:forEach items="${deptCostsSubjectList }" var="list">
        			deptCostsSubject_${list.code} = Number(deptCostsSubject_${list.code})+Number(rows[i].deptCostsSubject_${list.code} == undefined ? 0 : rows[i].deptCostsSubject_${list.code});
        		</c:forEach>
        		
    		}
    		var foot=[{deptname:'选中金额'
        				<c:forEach items="${deptCostsSubjectList }" var="list">
							,deptCostsSubject_${list.code} : deptCostsSubject_${list.code}
						</c:forEach>
        			}];
    		if(null!=footerobject){
        		foot.push(footerobject);
    		}
    		$("#departmentCosts-table-deptSupplierCosts").datagrid("reloadFooter",foot);
    	}
    	function emptyChooseObjectFoot(){
    		
    		<c:forEach items="${deptCostsSubjectList }" var="list">
    			var deptCostsSubject_${list.code}=0;
    		</c:forEach>
    		
    		var foot={deptname:'选中金额'
				<c:forEach items="${deptCostsSubjectList }" var="list">
					,deptCostsSubject_${list.code} : deptCostsSubject_${list.code}
				</c:forEach>
			};
			return foot;
    	}
    </script>
  </body>
</html>
