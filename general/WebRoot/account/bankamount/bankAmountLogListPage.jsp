<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行账户日志</title>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div class="buttonBG" data-options="region:'north'">
			<%--<security:authorize url="/report/sales/salesGoodsReportExport.do">--%>
				<a href="javaScript:void(0);" id="bankamountlog-export-log" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
			<%--</security:authorize>--%>
		</div>
    	<div data-options="region:'center'">
   			<div id="bankamountlog-form-div">
				<form action="" id="bankamountlog-form-ListQuery-log" method="post">
					<table cellpadding="0" cellspacing="1" border="0">
						<tr>
							<td>业务日期：</td>
							<td>
								<input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date}" />
                                到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date}"/>
								<input type="hidden" name="bankid" value="${id }"/>
							</td>
                            <td>单据类型：</td>
                            <td><input id="bankAmountLog-form-billtype" name="billtype" style="width: 130px;"/></td>
							<td colspan="2">
								<a href="javaScript:void(0);" id="bankamountlog-query-List" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="bankamountlog-query-reloadList" class="button-qr">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
    		<table id="bankamountlog-table-detail"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	var initQueryJSON =  $("#bankamountlog-form-ListQuery-log").serializeJSON();
     	$(function(){

			$("#bankamountlog-export-log").click(function(){
				var queryJSON = $("#bankamountlog-form-ListQuery-log").serializeJSON();
				//获取排序规则
				var objecr  = $("#bankamountlog-table-detail").datagrid("options");
				if(null != objecr.sortName && null != objecr.sortOrder ){
					queryJSON["sort"] = objecr.sortName;
					queryJSON["order"] = objecr.sortOrder;
				}
				var queryParam = JSON.stringify(queryJSON);
				var url = "account/bankamount/exportBankAmountLogList.do";
				exportByAnalyse(queryParam,"银行账户日志","bankamountlog-table-detail",url);
			});

     		//根据初始的列与用户保存的列生成以及字段权限生成新的列
        	var tableColJson=$("#bankamountlog-table-detail").createGridColumnLoad({
    	     	frozenCol:[[
    				{field:'ck',checkbox:true,isShow:true}
    	     	]],
    	     	commonCol:[[
    				{field:'billtype',title:'单据类型',width:80,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						return getSysCodeName("bankAmountOthersBilltype",val);
    					}
    				},
    	    		{field:'billid',title:'单据编号',width:130,sortable:true},
    				{field:'inamount',title:'借方金额(收入)',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "0.00";
    						}
    					}
    				},
    				{field:'outamount',title:'贷方金额(支出)',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "0.00";
    						}
    					}
    				},
    				{field:'balanceamount',title:'余额',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "0.00";
    						}
    					}
    				},
    				{field:'remark',title:'备注',width:280,sortable:true},
    				{field:'addusername',title:'添加人',width:60,sortable:true},
    				{field:'adddeptname',title:'添加部门',width:60,sortable:true},
    				{field:'addtime',title:'添加时间',width:130,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val){
    							return val.replace(/[tT]/," ");
    						}
    					}	
    				}
    			]]
    	     });
     		$("#bankamountlog-table-detail").datagrid({ 
     			authority:tableColJson,
	  	 		frozenColumns:tableColJson.frozen,
				columns:tableColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'id',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				queryParams:initQueryJSON,
				toolbar:'#bankamountlog-form-div',
				url: 'account/bankamount/showBankAmountLogList.do'
			}).datagrid("columnMoving");
            $("#bankAmountLog-form-billtype").widget({
                name:'t_account_bankamount_others',
                col:'billtype',
                width:130,
                initSelectNull:true,
                singleSelect:true
            });
     		$("#bankamountlog-query-List").click(function(){
     			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#bankamountlog-form-ListQuery-log").serializeJSON();
	       		$("#bankamountlog-table-detail").datagrid("load",queryJSON);
     		});
     		
     		$("#bankamountlog-query-reloadList").click(function(){
     			$("#bankamountlog-form-ListQuery-log")[0].reset();
				var queryJSON =  $("#bankamountlog-form-ListQuery-log").serializeJSON();
				$("#bankamountlog-table-detail").datagrid("load",queryJSON);
     		});
     	});
    </script>
  </body>
</html>
