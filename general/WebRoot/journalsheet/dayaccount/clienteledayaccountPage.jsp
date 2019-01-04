<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户开单流水账</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:false">
			<div id="finance-toolbar-ClienteleDayAccount" style="padding:2px;height:auto">
				<form action="" id="finance-form-QueryClienteleDayAccount" method="post">
					<table class="querytable">
						<tr>
							<td>业务日期:</td>
							<td>
								<input id="begintime" name="begintime" class="Wdate" style="width:100px" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-M-d',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})"/>
								到 <input id="endtime" name="endtime" class="Wdate" style="width:100px" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-M-d',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})" />
							</td>
							<td rowspan="4">
								<div>
									<c:forEach var="list" items="${codeList}" varStatus="status">
										<input type="checkbox" class="finance-subjectid" value="${list.code }" checked="checked"/>${list.codename }
										<c:if test="${(status.index+1)%2==0}"><br/></c:if>
									</c:forEach>
									<input type="hidden" id="finance-subjectid" name="subjectid"/>
								</div>
							</td>
						</tr>
						<tr>
							<td>供应商:</td>
							<td><input id="finance-widget-Clienteleidquery" name="supplierid" type="text"/>
								<input id="finance-query-Clientelename" type="hidden" name="suppliername"/>
							</td>
							<td rowspan="4">
								<a href="javaScript:void(0);" id="finance-query-ClienteleList" class="button-qr">查询</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table id="finance-table-Clientelelist"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var expensesEnteringListColJson=$("#finance-table-Clientelelist").createGridColumnLoad({
	     	name:'t_finance_expensesentering',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'suppliername',title:'供应商编码',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(rowData.isshow == "1"){
							return rowData.supplierid;
						}else if(rowData.isshow == "0"){
							return "";
						}
					}
				},
				{field:'supplierid',title:'供应商名称',width:250,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.suppliername;
					}
				},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'businessdate',title:'业务日期',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.businessdate1;
					}
				},
				{field:'subjectid',title:'科目编码',width:80,sortable:true,hidden:true},
				{field:'subjectname',title:'科目名称',width:80,sortable:true},
				{field:'subjectexpenses',title:'科目费用',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
					} 
				},
				{field:'approvalAmount',title:'核准金额',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
					}
				},
				{field:'remark',title:'备注',width:100,sortable:true},
				{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true}
			]]
	     });
	     
		$(function(){
		
			//供应商
		  	$("#finance-widget-Clienteleidquery").widget({
		  		width:225,
				name:'t_finance_expensesentering',
				col:'supplierid',
				singleSelect:true
			});
			
			//查询
			$("#finance-query-ClienteleList").click(function(){
				var checkbox = [];
				var subjectid = "";
				$('.finance-subjectid').each(function(){    
					if($(this).attr("checked")){
						if(subjectid==""){
							subjectid = $(this).val();
						}else{
							subjectid += ","+$(this).val();
						}
					}  
				});
				$("#finance-subjectid").val(subjectid);
				var flag = $("#finance-form-QueryClienteleDayAccount").form('validate');
		    	if(flag==false){
		    		return false;
		    	}
	      		var queryJSON = $("#finance-form-QueryClienteleDayAccount").serializeJSON();
	      		$("#finance-table-Clientelelist").treegrid({queryParams:queryJSON}).datagrid("columnMoving");
			});
			
			$("#finance-table-Clientelelist").treegrid({ 
     			authority:expensesEnteringListColJson,
	  	 		frozenColumns:[[]],
				columns:expensesEnteringListColJson.common,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		showFooter: true,
	  	 		singleSelect:true,
	  	 		idField:'id',  
			    treeField:'supplierid',
			    toolbar:'#finance-toolbar-ClienteleDayAccount',
			    url:'journalsheet/dayaccount/getClienteleDayAccountList.do',
			    onBeforeLoad:function(row,param){
			    	var flag = $("#finance-form-QueryClienteleDayAccount").form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	if (!row) {
                        param.id = "";
                    }  
			    },
                onDblClickRow:function(row){
                	if(row.state=='closed'){
                		$("#finance-table-Clientelelist").treegrid("expand",row.id);
                	}else{
                		$("#finance-table-Clientelelist").treegrid("collapse",row.id);
                	}
                }
			}).datagrid("columnMoving");
		});
    </script>
  </body>
</html>
