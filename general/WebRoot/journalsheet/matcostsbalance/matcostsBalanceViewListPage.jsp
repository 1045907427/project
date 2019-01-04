<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收支明细页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<style type="text/css">
  	
  		.len50 {
  			width: 50px;
  		}
  		.len70 {
  			width: 70px;
  		}
  		.len190 {
  			width: 190px;
  		}
  		.len227 {
  			width: 227px;
  		}
  		.len240 {
  			width: 240px;
  		}
  		.len250 {
  			width: 250px;
  		}
  	
  	</style>
  	<div class="easyui-layout" data-options="fit:true,border:false">
	  	<div data-options="region:'center',fit:true,border:false">
	  		<div id="journalsheet-condition-matcostsBalanceViewPage">
				<form id="journalsheet-form-matcostsBalanceViewPage">
					<table>
						<tr>
							<td class="right len70">业务日期：</td>
							<td class="len190"><input class="easyui-validatebox Wdate" style="width: 100px" id="journalsheet-begindate-matcostsBalanceViewPage" name="begindate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" />&nbsp;到&nbsp;<input class="easyui-validatebox Wdate"  style="width: 100px" id="journalsheet-enddate-matcostsBalanceViewPage" name="enddate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" /></td>
							<td class="right len70">费用类别：</td>
							<td class="len150"><input class="easyui-validatebox len130" id="journalsheet-subjectid-matcostsBalanceViewPage" name="subjectid" data-options="required:false" /></td>
							<td class="right len50">收支：</td>
							<td>
								<select id="journalsheet-balancetype-matcostsBalanceViewPage" name="balancetype"  style="width:130px;">
									<option value="0">全部</option>
									<option value="1">收入</option>
									<option value="2">支出</option>
								</select>
							</td>						
						</tr>							
						<tr>
							<td class="right len70">供 应 商：</td>
							<td class="len250"><input class="easyui-validatebox len240" id="journalsheet-supplierid-matcostsBalanceViewPage" name="supplierid" data-options="required:false" /></td>					
							<td class="right">客&nbsp;&nbsp;户：</td>
	    					<td colspan="3">
	    						<input id="journalsheet-customerid-matcostsBalanceViewPage" type="text" name="customerid" style="width: 335px;"/>
	    					</td>	
						</tr>
						<tr>
							<td class="right">品牌部门：</td>
							<td><input class="easyui-validatebox len227" id="journalsheet-deptid-matcostsBalanceViewPage" name="deptid" data-options="required:false" /></td>
							<td class="right">是否红冲：</td>
							<td class="len100">
								<select id="journalsheet-hcflag-matcostsBalanceViewPage" name="ishcflag" class="len80">
									<option value="">全部</option>
	    							<option value="1">是</option>
	    							<option value="2">否</option>
								</select>
							</td>
							<td align="right" colspan="2">
								<div>
									<a href="javascript:void(0);" id="journalsheet-query-matcostsBalanceViewPage" class="button-qr">查询</a>
									<a href="javascript:void(0);" id="journalsheet-reset-matcostsBalanceViewPage" class="button-qr">重置</a>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</div>
			<table id="journalsheet-datagrid-matcostsBalanceViewPage">
			</table>
		</div>
	</div>
	<div id="journalsheet-dialog-matcostsBalanceViewPage"></div>
    <script type="text/javascript">

	var $bigindate = $('#journalsheet-begindate-matcostsBalanceViewPage');
	var $enddate = $('#journalsheet-enddate-matcostsBalanceViewPage');
	var $subjectid = $('#journalsheet-subjectid-matcostsBalanceViewPage');
	var $datagrid = $('#journalsheet-datagrid-matcostsBalanceViewPage');
	var $deptid = $('#journalsheet-deptid-matcostsBalanceViewPage');
	var $supplierid = $('#journalsheet-supplierid-matcostsBalanceViewPage');
	var $balancetype = $('#journalsheet-balancetype-matcostsBalanceViewPage');
	var $customerid = $('#journalsheet-customerid-matcostsBalanceViewPage');
	
	var $query = $('#journalsheet-query-matcostsBalanceViewPage');
	var $reset = $('#journalsheet-reset-matcostsBalanceViewPage');
	var $dialog = $('#journalsheet-dialog-matcostsBalanceViewPage');
	var $form = $('#journalsheet-form-matcostsBalanceViewPage');

	var footers = new Array();

	$(function(){
	
		// 供应商
		$supplierid.supplierWidget({
		
		});
	
		// 
		$deptid.widget({
			required: false,
			width: 223,
			referwid: 'RL_T_BASE_DEPATMENT',
			onlyLeafCheck: false
		});
		// 科目
		$subjectid.widget({
			required: false,
			width: 125,
			referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
			onlyLeafCheck: false
		});
		
        //客户
 		$customerid.customerWidget({
			width:335,
			isall:true,
			singleSelect:true,
			onlyLeafCheck:false
 		});
		
		// List
		var cols = $datagrid.createGridColumnLoad({
			frozenCol: [[
							{field:'ck',checkbox:true},
							{field: 'id', title: '编号',hidden: true},
							{field: 'businessdate', title: '业务日期', sortable: true, width: 80},
							{field: 'supplierid', title: '供应商编号'},
							{field: 'suppliername', title: '供应商', sortable: true, width: 180},
							{field: 'deptid', title: '部门编号',hidden: true},
                            {field: 'oaid', title: 'OA编号'},
							{field: 'deptname', title: '品牌部门', sortable: true, width: 112},
							{field: 'customerid', title: '客户编号',hidden: true},
							{field: 'customername', title: '客户名称', sortable: true, width: 180},
							{field: 'hcflag', title: '是否红冲', sortable: true, width: 60,
								formatter:function(val,rowData,rowIndex){
									if(rowData.hcflag=='0'){
										return "否";
									}else if(rowData.hcflag=='1' || rowData.hcflag=='2'){
										return "是";
									}
								}
							}
					   ]],
			commonCol: [[
							{field: 'income', title:'收入', width: 80, sortable: true, align: 'right',
								formatter: function(value,row,index){
									return formatterMoney(value);
								}
							},
							{field: 'outcome', title:'支出', width: 80, sortable: true, align: 'right',
								formatter: function(value,row,index){
									return formatterMoney(value);
								}
							},
							{field: 'subjectid', title: '科目编号', hidden: true},
							{field: 'subjectname', title: '费用科目', width: 90}
					   ]]
		});
	
		$datagrid.datagrid({
			//authority: cols2,
  	 		frozenColumns: cols.frozen,
			columns: cols.common,
	  	 	fit:true,
	  	 	method:'post',
	  	 	showFooter: true,
	  	 	rownumbers: true,
	  	 	sortName: 'businessdate',
	  	 	sortOrder: 'desc',
	  	 	pagination: true,
		 	idField: 'id',
		 	border: false,
	  	 	singleSelect: false,
		 	checkOnSelect: true,
		 	selectOnCheck: true,
			pageSize:20,
			pageList:[10,20,30,50,100,200],
			toolbar: '#journalsheet-condition-matcostsBalanceViewPage',
			url: 'journalsheet/matcostsbalance/selectMatcostsBalanceDetailList.do',
			queryParams: $('#journalsheet-form-matcostsBalanceViewPage').serializeJSON(),
			onCheckAll: computeSelectedDetailRows,
			onUncheckAll: computeSelectedDetailRows,
			onCheck: computeSelectedDetailRows,
			onUncheck: computeSelectedDetailRows,
			onLoadSuccess: function(){

				footers = $datagrid.datagrid('getFooterRows');
				$datagrid.datagrid('hideColumn', 'income');
				$datagrid.datagrid('hideColumn', 'outcome');
				
				var v = $balancetype.val();
				if(v == 1) {
				
					$datagrid.datagrid('showColumn', 'income');
					
				} else if(v == 2) {
				
					$datagrid.datagrid('showColumn', 'outcome');
					
				} else {
				
					$datagrid.datagrid('showColumn', 'income');
					$datagrid.datagrid('showColumn', 'outcome');
				}
			},
 			rowStyler:function(index,row){
 				if(row.hcflag && row.hcflag=='1'){
 					return "color:#f00";
 				}
 			}
		}).datagrid('columnMoving');
	
		// 查询
		$query.unbind().click(function(){
			
			var paramJSON = $('#journalsheet-form-matcostsBalanceViewPage').serializeJSON();
			
	      	$datagrid.datagrid({
	      		url: 'journalsheet/matcostsbalance/selectMatcostsBalanceDetailList.do',
	      		pageNumber: 1,
				queryParams: paramJSON
	      	});
			
		});
		
		// 重置
		$reset.unbind().click(function(){
			
			$form[0].reset();
			
			$subjectid.widget('clear');
			$deptid.widget('clear');
			$supplierid.supplierWidget('clear');
			$customerid.customerWidget('clear');
			
			var paramJSON = $('#journalsheet-form-matcostsBalanceViewPage').serializeJSON();
			
	      	$datagrid.datagrid({
	      		url: 'journalsheet/matcostsbalance/selectMatcostsBalanceDetailList.do',
	      		pageNumber: 1,
				queryParams: paramJSON
	      	});
		});
		
		//$query;
		
	});
	
	function computeSelectedDetailRows() {
	
		var rows = $datagrid.datagrid('getChecked');
		
		if(footers.length == 0) {
			return ;
		}
		
		if(rows.length == 0) {
			
			$datagrid.datagrid('reloadFooter', footers);
			return ;
		}
		
 		var income = 0.00;
 		var outcome = 0.00;

 		for(var i = 0; i < rows.length; i++){
 		
 			income = income + parseFloat(rows[i].income);
 			outcome = outcome + parseFloat(rows[i].outcome);
 			//balance = balance + parseFloat(rows[i].balance);
 		}

 		var tempFooters = new Array();
 		var footer = {businessdate: '选中合计', income: income, outcome: outcome/*, balance: balance*/};
 		
 		tempFooters.push(footer);
 		
 		if(footers.length > 0) {

 			tempFooters.push(footers[0]);
 		}

 		$datagrid.datagrid('reloadFooter', tempFooters);
	}
	
	</script>
  </body>
</html>