<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户收款分布统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-customerReceiptBank"></table>
    	<div id="report-toolbar-customerReceiptBank" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/customerReceiptBankExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-customerReceiptBankPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-customerReceiptBank" method="post">
    		<table>

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
                    <td>销售部门:</td>
                    <td><input type="text" id="report-query-salesdeptid" name="salesdeptid"/></td>
    			</tr>
                <tr>
                    <td>银行名称:</td>
                    <td>
                        <input type="text" id="report-query-bank" name="bank"/>
                    </td>
                    <td>银行部门:</td>
                    <td>
                        <input type="text" id="report-query-bankdeptid" name="bankdeptid"/>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="report-queay-customerReceiptBank" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="report-reload-customerReceiptBank" class="button-qr">重置</a>

                    </td>
                </tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-customerReceiptBank").serializeJSON();
    		$(function(){
    			$("#report-datagrid-customerReceiptBank").datagrid({ 
					columns:[[
								{field:'idok',checkbox:true,isShow:true},
								  {field:'customerid',title:'客户编号',width:80},
								  {field:'customername',title:'客户名称',width:180},
                                  {field:'salesdeptname',title:'销售部门',width:180},
								  {field:'amount',title:'收款总额',align:'right',resizable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
								  <c:forEach items="${bankList }" var="list">
								  ,{field:'bank${list.id}',title:'${list.name}',align:'right',resizable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
	    						  </c:forEach>
								  
					         ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-customerReceiptBank',
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
						}
			 		},
					onCheckAll:function(){
						countTotalAmount();
					},
					onUncheckAll:function(){
						countTotalAmount();
					},
					onCheck:function(){
						countTotalAmount();
					},
					onUncheck:function(){
						countTotalAmount();
					}
				});
                $("#report-query-salesdeptid").widget({
                    referwid:'RT_T_SYS_DEPT',
                    singleSelect:false,
                    width:150,
                    onlyLeafCheck:false,
                    view:true
                });
                $("#report-query-bankdeptid").widget({
                    referwid:'RT_T_SYS_DEPT',
                    singleSelect:false,
                    width:150,
                    onlyLeafCheck:false,
                    view:true
                });
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
		    		width:150,
					singleSelect:true
				});
				$("#report-query-bank").widget({
					referwid:'RL_T_BASE_FINANCE_BANK',
		    		width:225,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-customerReceiptBank","report-reload-customerReceiptBank");
				
				//查询
				$("#report-queay-customerReceiptBank").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-customerReceiptBank").serializeJSON();
		      		$("#report-datagrid-customerReceiptBank").datagrid({
		      			url: 'report/finance/showCustomerReceiptBankListData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-customerReceiptBank").click(function(){
                    $("#report-query-salesdeptid").widget("clear");
                    $("#report-query-bankdeptid").widget("clear");
					$("#report-query-customerid").widget("clear");
					$("#report-query-bank").widget("clear");
					$("#report-query-form-customerReceiptBank")[0].reset();
					var queryJSON = $("#report-query-form-customerReceiptBank").serializeJSON();
		       		$("#report-datagrid-customerReceiptBank").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-customerReceiptBankPage").Excel('export',{
					queryForm: "#report-query-form-customerReceiptBank", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户收款分布统计报表',
			 		url:'report/finance/exportCustomerReceiptBankData.do'
				});
				
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-customerReceiptBank").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		return false;
        		}
    			var amount = 0;
        		
        		for(var i=0;i<rows.length;i++){
        			amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
        		}
        		var foot=[{customername:'选中合计',bankname:'',amount:amount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-customerReceiptBank").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
