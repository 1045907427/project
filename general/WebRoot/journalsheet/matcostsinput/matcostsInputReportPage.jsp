<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
   	<div id="journalsheet-query-matcostsInput" style="padding:2px;height:auto">
    	<form action="" id="journalsheet-query-form-matcostsInput" method="post">
    		<table class="querytable">
                <tr>
                    <security:authorize url="/journalsheet/matcostsInput/exportMatcostsReportData.do">
                        <a href="javaScript:void(0);" id="journalsheet-export-buton" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    </security:authorize>
                </tr>
    			<tr>
    				<td>业务日期:</td>
    				<td><input id="journalsheet-query-businessdate1" type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/> 到 <input id="journalsheet-query-businessdate2" type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>供应商名称:</td>
    				<td><input id="journalsheet-query-supplierid" type="text" name="supplierid" style="width: 200px;"/></td>
    			</tr>
    			<tr>
    				<td>部门名称:</td>
    				<td><input id="journalsheet-query-deptid" type="text" name="deptid" /></td>
                    <td colspan="2"></td>
                    <td rowspan="3" colspan="2" class="tdbutton">
    					<a href="javaScript:void(0);" id="journalsheet-query-buton" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="journalsheet-reload-buton" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="journalsheet-table-matcostsInput"></table>
    <div style="display:none">
    	<div id="journalsheet-div-matcostsInput-detail"></div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		var columnJson = $("#journalsheet-table-matcostsInput").createGridColumnLoad({
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'supplierid',title:'供应商编号',width:80,sortable:true},
							  {field:'suppliername',title:'供应商名称',width:150,sortable:true},
							  {field:'supplierdeptid',title:'部门名称',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.supplierdeptname;
					        	}
							  },
							  {field:'beginamount',title:'期初代垫金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'factoryamount',title:'工厂投入',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  <%--
							  {field:'htcompdiscount',title:'电脑折让',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'htpayamount',title:'支付金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'branchaccount',title:'转入分公司',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  --%>
							  <c:forEach items="${reimbursetypeList }" var="list">
							  {field:'amount${list.code}',title:'${list.codename}',align:'right',resizable:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
				        			return formatterMoney(value);
					        	}
							  },
    						  </c:forEach>
							  {field:'reimburseamount',title:'收回金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'endamount',title:'期末代垫金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
				             ]]
			});
			$("#journalsheet-table-matcostsInput").datagrid({ 
		 		authority:columnJson,
		 		frozenColumns: columnJson.frozen,
				columns:columnJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'supplierid',
		 		sortName:'supplierid',
		 		sortOrder:'asc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#journalsheet-query-matcostsInput',
				onDblClickRow:function(rowIndex, rowData){
					var businessdate1 = $("#journalsheet-query-businessdate1").val();
   					var businessdate2 = $("#journalsheet-query-businessdate2").val();

   					var url = 'journalsheet/matcostsInput/showMatcostsReportDetailPage.do';
                    var urldata={
                        "supplierid":rowData.supplierid,
                        "businessdate1":businessdate1,
                        "businessdate2":businessdate2,
                        "deptid":rowData.supplierdeptid
                    };
   		  	  		$('<div id="journalsheet-div-matcostsInput-detail-content"></div>').appendTo("#journalsheet-div-matcostsInput-detail");
   		  	  		$DetailOper=$("#journalsheet-div-matcostsInput-detail-content");
   		  	  		var title='按供应商：['+rowData.suppliername+']';
   		  	  		if(rowData.supplierdeptid!=null){
   		  	  		    title=title+"-部门：["+rowData.supplierdeptname+"]";
                    }
                    title=title+"显示代垫明细列表";
					$DetailOper.dialog({
					    title: title,
			    		fit:true,
					    closed: true,  
					    cache: false,  
					    modal: true,
					    maximizable:true,
					    collapsible:false,
					    minimizable:false,
					    resizable:true,
                        method:'post',
					    href: url,
                        queryParams:urldata,
					    onClose:function(){
					    	$DetailOper.dialog('destroy');
					    },
					    onLoad:function(){
					    	loadMatcostsInputReportDetail();
					    }
					});
					$DetailOper.dialog("open");
				}
			}).datagrid("columnMoving");
			
			$("#journalsheet-query-supplierid").supplierWidget();
			$("#journalsheet-query-deptid").widget({
				width:220,
				name:'t_js_matcostsinput',
				col:'supplierdeptid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			$("#journalsheet-query-buton").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#journalsheet-query-form-matcostsInput").serializeJSON();
	      		$("#journalsheet-table-matcostsInput").datagrid({
	      			url: 'journalsheet/matcostsInput/showMatcostsReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#journalsheet-reload-buton").click(function(){
				$("#journalsheet-query-form-matcostsInput")[0].reset();
				$("#journalsheet-table-matcostsInput").datagrid('loadData',{total:0,rows:[]});
				$("#journalsheet-query-deptid").widget('clear');
				$("#journalsheet-query-supplierid").supplierWidget('clear');
			});
			$("#journalsheet-export-buton").Excel('export',{
				queryForm: "#journalsheet-query-form-matcostsInput", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'代垫汇总统计报表',
		 		url:'journalsheet/matcostsInput/exportMatcostsReportData.do'
			});
    	});
        function viewOa(id) {

            top.addTab('act/workViewPage.do?processid=' + id, '工作查看');
        }
    </script>
  </body>
</html>
