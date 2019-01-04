<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行账户余额</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
   			<div id="bankamount-form-div" style="padding: 0px">
                <div class="buttonBG">
                    <security:authorize url="/account/bankamount/showBankAmountTransferPage.do">
                    <a href="javaScript:void(0);" id="departmentCosts-transfer-list" class="easyui-linkbutton" iconCls="button-oppaudit" plain="true">转账</a>
                    </security:authorize>
                </div>
				<form action="" id="bankamount-form-ListQuery" method="post">
					<table cellpadding="0" cellspacing="1" border="0">
						<tr>
							<td>银行名称：</td>
							<td>
								<input id="bankamount-form-bankid" name="bankid" type="text" style="width: 180px;"/>
							</td>
							<td>所属部门：</td>
							<td>
								<input id="bankamount-form-deptid" name="deptid" type="text" style="width: 130px;"/>
							</td>
							<td colspan="2">
								<a href="javaScript:void(0);" id="departmentCosts-query-List" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="button-qr">重置</a>

							</td>
						</tr>
					</table>
				</form>
			</div>
    		<table id="bankamount-table-detail"></table>
    	</div>
    </div>
    <div id="bankamount-dialog-detail"></div>
    <div id="bankamount-dialog-transfer"></div>
    <script type="text/javascript">
        var footerobject = {brankid:"合计"};
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var tableColJson=$("#bankamount-table-detail").createGridColumnLoad({
	     	frozenCol:[[

	     	]],
	     	commonCol:[[
                {field:'ck',checkbox:true,isShow:true},
	    		{field:'bankid',title:'编码',width:80,sortable:true},
				{field:'bankname',title:'银行名称',width:120},
				{field:'deptid',title:'所属部门',width:120,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.deptname;
					}
				},
				{field:'amount',title:'金额',width:80,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				}
			]]
	     });
    	
    	var initQueryJSON =  $("#bankamount-form-ListQuery").serializeJSON();
     	$(function(){
     		$("#bankamount-table-detail").datagrid({ 
     			authority:tableColJson,
	  	 		frozenColumns:tableColJson.frozen,
				columns:tableColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
                pagination:true,
	  	 		sortName:'bankid',
	  	 		sortOrder:'desc',
		 		idField:'bankid',
                singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
				pageSize:20,
				queryParams:initQueryJSON,
				toolbar:'#bankamount-form-div',
				url: 'account/bankamount/showBankAmountList.do',
		    	onDblClickRow:function(rowIndex, rowData){
		    		$('#bankamount-dialog-detail').dialog({  
					    title: '银行账户日志',  
					    width: 900,  
					    height: 450,
					    fit:true,
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'account/bankamount/showBankAmountLogListPage.do?id='+rowData.bankid,  
					    modal: true
					});
					$('#bankamount-dialog-detail').dialog("open");
		    	},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
                        countTotal();
					}
		 		},
				onCheckAll:function(){
		 			countTotal();
				},
				onUncheckAll:function(){
					countTotal();
				},
				onCheck:function(){
					countTotal();
				},
				onUncheck:function(){
					countTotal();
				}
			}).datagrid("columnMoving");
     		
     		$("#bankamount-form-bankid").widget({
     			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:180,
				singleSelect:true,
				onlyLeafCheck:false
     		});
     		$("#bankamount-form-deptid").widget({
     			referwid:'RT_T_SYS_DEPT',
    			width:130,
				singleSelect:true,
				onlyLeafCheck:false
     		});
     		$("#departmentCosts-query-List").click(function(){
     			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#bankamount-form-ListQuery").serializeJSON();
	       		$("#bankamount-table-detail").datagrid("load",queryJSON);
     		});
     		
     		$("#departmentCosts-query-reloadList").click(function(){
     			$("#bankamount-form-bankid").widget("clear");
     			$("#bankamount-form-deptid").widget("clear");
     			$("#bankamount-form-ListQuery")[0].reset();
				var queryJSON =  $("#bankamount-form-ListQuery").serializeJSON();
				$("#bankamount-table-detail").datagrid("load",queryJSON);
     		});
     		$("#departmentCosts-transfer-list").click(function(){
     			$('#bankamount-dialog-transfer').dialog({  
				    title: '银行账户转账',  
				    width: 400,  
				    height: 300,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
				    href: 'account/bankamount/showBankAmountTransferPage.do',  
				    modal: true,
				    onLoad:function(){
				    	$("#bankamount-form-outbankid").focus();
				    }
				});
				$('#bankamount-dialog-transfer').dialog("open");
     		});
     		
     	});
     	function countTotal(){
            var checkrows =  $("#bankamount-table-detail").datagrid('getChecked');
     	    console.log(checkrows)
            if(checkrows.length>0){
                var amount = 0;
                for(var i=0; i<checkrows.length; i++){
                    amount += Number(checkrows[i].amount == undefined ? 0 : checkrows[i].amount);
                }
                var foot = [{bankid:'选中合计',amount:amount}];
                foot.push(footerobject);
                $("#bankamount-table-detail").datagrid('reloadFooter',foot);
            }else{
                var foot = [{bankid:'选中合计'}];
                foot.push(footerobject);
                $("#bankamount-table-detail").datagrid('reloadFooter',foot);
            }
     	}
    </script>
  </body>
</html>
