<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>放单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div id="account-datagrid-toolbar-freeorder" style="padding: 0px">
    	<div class="buttonBG" id="account-buttons-freeorder" style="height:26px;"></div>
    	<form action="" id="account-form-query-freeorder" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
    				<td>客户名称:</td>
    				<td><input type="text" name="customerid" id="account-customerid-freeorder" style="width: 150px;"/></td>
    				<td>状态:</td>
    				<td>
    					<select name="status" style="width:80px;"><option></option><option value="2" selected="selected">保存</option><option value="3">审核通过</option><option value="4">已关闭</option></select>
    				</td>
    				<td>
    					<a href="javaScript:void(0);" id="account-queay-freeorder" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-freeorder" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="account-datagrid-freeorder"></table>
    <script type="text/javascript">
    	var intiQueryJSON = $("#account-form-query-freeorder").serializeJSON();
    	$(function(){
    		$("#account-buttons-freeorder").buttonWidget({
    			initButton:[
    				{},
    				<security:authorize url="/account/receivable/freeorderAudit.do">
    				{
    					type: 'button-audit',
						handler: function(){
							var rows = $("#account-datagrid-freeorder").datagrid('getChecked');
							if(rows.length == 0){
								$.messager.alert("提醒","请勾选要审核的单据!");
								return false;
							}
							$.messager.confirm("提醒","确定审核单据信息？",function(r){
								if(r){
									var ids = "";
									for(var i=0;i<rows.length;i++){
										if(rows[i].status != "2"){
											$("#account-datagrid-freeorder").datagrid('uncheckRow',i);
											
										}else{
											if(ids == ""){
												ids = rows[i].id;
											}else{
												ids += "," + rows[i].id;
	 										}
										}
									}
									if(ids == ""){
										return false;
									}
									loading("审核中...");
									$.ajax({
										url:'account/receivable/auditSalesFreeOrder.do',
										dataType:'json',
										type:'post',
										data:'ids='+ ids,
										success:function(json){
											loaded();
											if(json.flag){
												$.messager.alert("提醒","审核成功!");
												$("#account-datagrid-freeorder").datagrid('reload');
											}else{
												$.messager.alert("提醒","审核失败!");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","审核出错");
										}
									});
								}
							});
						}
    				},
    				</security:authorize>
    				<security:authorize url="/account/receivable/freeorderOppaudit.do">
    				{
    					type: 'button-oppaudit',
						handler: function(){
							var rows = $("#account-datagrid-freeorder").datagrid('getChecked');
							if(rows.length == 0){
								$.messager.alert("提醒","请勾选要反审的单据!");
								return false;
							}
                            var count = 0;
                            for(var i=0;i<rows.length;i++){
                                var flag = isDoneOppauditBillCaseAccounting(rows[i].businessdate);
                                if(!flag){
                                    count++;
                                }
                            }
                            if(count != 0){
                                $.messager.alert("提醒","业务日期不在会计区间内或未设置会计区间,不允许反审!");
                                return false;
                            }
							$.messager.confirm("提醒","确定反审单据信息？",function(r){
								if(r){
									var ids = "";
									for(var i=0;i<rows.length;i++){
										if(rows[i].status != "3"){
											$("#account-datagrid-freeorder").datagrid('uncheckRow',i);
											
										}else{
											if(ids == ""){
												ids = rows[i].id;
											}else{
												ids += "," + rows[i].id;
	 										}
										}
									}
									if(ids == ""){
										return false;
									}
									loading("反审中...");
									$.ajax({
										url:'account/receivable/oppauditSalesFreeOrder.do',
										dataType:'json',
										type:'post',
										data:'ids='+ ids,
										success:function(json){
											loaded();
											if(json.flag){
												$.messager.alert("提醒","反审成功!");
												$("#account-datagrid-freeorder").datagrid('reload');
											}else{
												$.messager.alert("提醒","反审失败!");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","反审出错");
										}
									});
								}
							});
						}
    				},
    				</security:authorize>
    				<security:authorize url="/account/receivable/freeorderDeleteBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
							var rows = $("#account-datagrid-freeorder").datagrid('getChecked');
							if(rows.length == 0){
								$.messager.alert("提醒","请勾选要删除的单据!");
								return false;
							}
							$.messager.confirm("提醒","确定删除单据信息？",function(r){
								if(r){
									var ids = "";
									for(var i=0;i<rows.length;i++){
										if(rows[i].status != "2"){
											$("#account-datagrid-freeorder").datagrid('uncheckRow',i);
											
										}else{
											if(ids == ""){
												ids = rows[i].id;
											}else{
												ids += "," + rows[i].id;
	 										}
										}
									}
									if(ids == ""){
										return false;
									}
									loading("删除中...");
									$.ajax({
										url:'account/receivable/deleteSalesFreeOrder.do',
										dataType:'json',
										type:'post',
										data:'ids='+ ids,
										success:function(json){
											loaded();
											if(json.flag){
												$.messager.alert("提醒","删除成功!");
												$("#account-datagrid-freeorder").datagrid('reload');
											}else{
												$.messager.alert("提醒","删除失败!");
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","删除出错");
										}
									});
								}
							});
			 			}
		 			},
		 			</security:authorize>
		 			{}
    			],
				buttons:[
					<security:authorize url="/account/receivable/overalExportFreeOrder.do">
					{
						id:'saleFree-export',
						name:'全局导出',
						iconCls:'button-export',
						handler:function () {
							overalExport();
						}
					},
					</security:authorize>
					{}
				],
    			layoutid:'sales-orderPage-layout',
				model: 'bill',
				type: 'view',
				taburl:'/account/receivable/showSalesFreeOrderListPage.do',
				id:'',
				datagrid:'account-datagrid-freeorder'
    		});
    		
    		$("#account-customerid-freeorder").customerWidget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			singleSelect:true,
    			isdatasql:false,
    			onlyLeafCheck:false
    		});
    		
    		//回车事件
			controlQueryAndResetByKey("account-queay-freeorder","account-reload-freeorder");
			//全局导出
			function overalExport() {
				var objecr  = $("#account-datagrid-freeorder").datagrid("options");
				var queryParam = JSON.stringify(objecr.queryParams);
				var url = "account/receivable/overalExportSalesFreeOrderList.do";
				exportByAnalyse(queryParam,"放单管理","account-datagrid-freeorder",url);
			}
    		
			$("#account-queay-freeorder").click(function(){
	       		var queryJSON = $("#account-form-query-freeorder").serializeJSON();
	       		$("#account-datagrid-freeorder").datagrid('load', queryJSON);
			});
			$("#account-reload-freeorder").click(function(){
				$("#account-customerid-freeorder").customerWidget("clear");
				$("#account-form-query-freeorder").form("reset");
				var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
				queryJSON['status'] = '2';
				queryJSON['businessdate1'] = "${today}";
				queryJSON['businessdate2'] = "${today}";
				$("#account-datagrid-freeorder").datagrid('load', queryJSON);
			});

			var tableColumnListJson = $("#account-datagrid-freeorder").createGridColumnLoad({
				frozenCol : [[
					{field:'idok',checkbox:true,isShow:true}
				]],
				commonCol : [[
					{field:'businessdate',title:'业务日期',sortable:true,width:'80'},
					{field:'id',title:'放单编码',sortable:true,width:'140'},
					{field:'customerid',title:'客户编码',sortable:true,width:'60',
						formatter:function(value,rowData,rowIndex){
							return value;
						}
					},
					{field:'customername',title:'客户名称',width:'210'},
					{field:'saleamount',title:'应收款',align:'right',resizable:true,sortable:true,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						},
						styler:function(value,rowData,rowIndex){
							return 'color:blue';
						}
					},
					{field:'overreason',title:'超账期原因',resizable:true},
					{field:'commitmentdate',title:'承诺到款日期',width:90},
					{field:'commitmentamount',title:'承诺到款金额',align:'right',resizable:true,
						formatter:function(value,rowData,rowIndex){
							if(value != "" && value != null){
								return formatterMoney(value);
							}
						}
					},
					{field:'status',title:'状态',width:'60',align:'right',
						formatter:function(value,rowData,rowIndex){
							return rowData.statusname;
						}
					},
					{field:'applyid',title:'申请人',width:'90',
						formatter:function(value,rowData,rowIndex){
							return rowData.applyname;
						}
					}
				]]
			});
    		
    		$("#account-datagrid-freeorder").datagrid({
				authority:tableColumnListJson,
				frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
    			fit:true,
    			method:'post',
    			rownumbers:true,
		 		pagination:true,
		 		//showFooter: true,
		 		singleSelect:false,
		 		checkOnSelect:true,
			 	selectOnCheck:true,
		 		pageSize:100,
		 		rowStyler:function(index,row){
					if(undefined != row.eqflag && !row.eqflag){
						return 'background-color:#DFF1D1';
					}
				},
				url: 'account/receivable/getSalesFreeOrderList.do',
				queryParams:intiQueryJSON,
				toolbar:'#account-datagrid-toolbar-freeorder'
    		});
    	});
    </script>
  </body>
</html>
