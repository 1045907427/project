<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行收支情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-bankReport"></table>
    	<div id="report-toolbar-bankReport" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/bankReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-bankReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-bankReport" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>银行名称</td>
    				<td>
    					<select id="report-query-bank" name="bank" class="len136">
    						<option value=""></option>
    						<option value="cash">现金</option>
    						<c:forEach items="${bankList }" var="list">
							<option value="${list.id }">${list.name }</option>
    						</c:forEach>
    					</select>
    				</td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-bankReport" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-bankReport" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-bankReport").serializeJSON();
    		$(function(){
    			$("#report-datagrid-bankReport").datagrid({ 
					columns:[[
						  {field:'idok',checkbox:true,isShow:true},
						  {field:'bank',title:'银行名称',width:130,
						  	formatter:function(value,rowData,rowIndex){
						  		if(rowData.bankname!=null && rowData.bankname!=""){
				        			return rowData.bankname;
				        		}
				        	}
						  },
						  {field:'receiptamount',title:'收款金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'payamount',title:'付款金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'endamount',title:'余额',resizable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
					 ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-bankReport',
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
				}).datagrid("columnMoving");
				
				//回车事件
				controlQueryAndResetByKey("report-queay-bankReport","report-reload-bankReport");
				
				//查询
				$("#report-queay-bankReport").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-bankReport").serializeJSON();
		      		$("#report-datagrid-bankReport").datagrid({
		      			url: 'report/finance/showBankReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-bankReport").click(function(){
					$("#report-query-form-bankReport").form("reset");
					var queryJSON = $("#report-query-form-bankReport").serializeJSON();
		       		$("#report-datagrid-bankReport").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-bankReportPage").Excel('export',{
					queryForm: "#report-query-form-bankReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'银行收支情况表',
			 		url:'report/finance/exportBankReportData.do'
				});
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-bankReport").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-bankReport").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var receiptamount = 0;
        		var payamount = 0;
        		var endamount=0;
        		
        		for(var i=0;i<rows.length;i++){
        			receiptamount = Number(receiptamount)+Number(rows[i].receiptamount == undefined ? 0 : rows[i].receiptamount);
        			payamount = Number(payamount)+Number(rows[i].payamount == undefined ? 0 : rows[i].payamount);
        			endamount = Number(endamount)+Number(rows[i].endamount == undefined ? 0 : rows[i].endamount);
        			
        		}
        		var foot=[{brandname:'选中合计',unitname:'',receiptamount:receiptamount,payamount:payamount,endamount:endamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-bankReport").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
