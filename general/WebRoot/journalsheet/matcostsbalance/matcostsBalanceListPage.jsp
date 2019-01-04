<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收支情况一览页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
	  	<div data-options="region:'north',fit:true,border:false" style="height: 100px;">
	  		<div id="journalsheet-condition-matcostsBalanceListPage" style="padding-top: 0px;">
                <div class="buttonBG">
                    <security:authorize url="/journalsheet/matcostsbalance/exportMatcostsBalanceListData.do">
                        <a href="javaScript:void(0);" id="journalsheet-export-matcostsBalanceListPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                    </security:authorize>
                </div>
				<form id="journalsheet-form-matcostsBalanceListPage">
					<table>
						<tr>
							<td class="left len70">业务日期：</td>
							<td class="len250"><input class="easyui-validatebox Wdate len100" id="journalsheet-begindate-matcostsBalanceListPage" name="begindate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" />　到　<input class="easyui-validatebox Wdate len100" id="journalsheet-enddate-matcostsBalanceListPage" name="enddate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" /></td>
                            <td class="left">品牌部门：</td>
                            <td><input class="easyui-validatebox" id="journalsheet-deptid-matcostsBalanceListPage"  name="deptid" data-options="required:false" /></td>
							<td class="left">客户分类：</td>
							<td>
								<input class="easyui-validatebox" id="journalsheet-customersort-matcostsBalanceListPage" name="customersort" data-options="required:false" />
							</td>
						</tr>
						<tr>
                            <td class="left len70">供 应 商：</td>
                            <td class="len250"><input class="easyui-validatebox" style="width: 235px" id="journalsheet-supplierid-matcostsBalanceListPage" name="supplierid" data-options="required:false" /></td>
							<td colspan="2" align="right">
								<div>
									<a href="javascript:void(0);" id="journalsheet-query-matcostsBalanceListPage" class="button-qr" iconCls="icon-search" plain="true" title="查询">查询</a>
									<a href="javascript:void(0);" id="journalsheet-reset-matcostsBalanceListPage" class="button-qr" iconCls="icon-reload" plain="true" title="重置">重置</a>　
								</div>
							</td>
							<td colspan="2"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
        <div data-options="region:'center',fit:true,border:false">
            <table id="journalsheet-datagrid-matcostsBalanceListPage">
            </table>
        </div>
	</div>
	<div id="journalsheet-dialog-matcostsBalanceListPage"></div>
    <script type="text/javascript">

    <!--

	var $bigindate = $('#journalsheet-begindate-matcostsBalanceListPage');
	var $enddate = $('#journalsheet-enddate-matcostsBalanceListPage');
	var $supplierid = $('#journalsheet-supplierid-matcostsBalanceListPage');
	var $datagrid = $('#journalsheet-datagrid-matcostsBalanceListPage');
	var $deptid = $('#journalsheet-deptid-matcostsBalanceListPage');
	var $customersortWidget = $('#journalsheet-customersort-matcostsBalanceListPage');
	
	var $query = $('#journalsheet-query-matcostsBalanceListPage');
	var $reset = $('#journalsheet-reset-matcostsBalanceListPage');
	var $dialog = $('#journalsheet-dialog-matcostsBalanceListPage');
	var $form = $('#journalsheet-form-matcostsBalanceListPage');

	var footers = new Array();

	$(function(){

        <security:authorize url="/journalsheet/matcostsbalance/exportMatcostsBalanceListData.do">
            $("#journalsheet-export-matcostsBalanceListPage").Excel('export',{
                queryForm: "#journalsheet-form-matcostsBalanceListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                type: 'exportUserdefined',
                name: '代垫收支情况表',
                url: 'journalsheet/matcostsbalance/exportMatcostsBalanceListData.do'
            });
        </security:authorize>

        // 供应商
		$supplierid.supplierWidget({});
	
		// 品牌部门
		$deptid.widget({
			required: false,
			width: 140,
			referwid: 'RL_T_BASE_DEPATMENT',
			onlyLeafCheck: false
		});
		$customersortWidget.widget({ //分类
			referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
			width:140,
			singleSelect:true,
			onlyLeafCheck:false,
			view: true
		});
		// List
		var cols = $datagrid.createGridColumnLoad({
			frozenCol: [[
							{field:'ck',checkbox:true},
							{field: 'id', title: '编号',hidden: true},
							{field: 'begindate', hidden: true},
							{field: 'enddate', hidden: true},
							{field: 'supplierid', title: '供应商编号',hidden: true},
							{field: 'suppliername', title: '供应商', sortable: true, width: 240},
							{field: 'deptid', title: '品牌部门编号',hidden: true},
							{field: 'deptname', title: '品牌部门', sortable: true, width: 112}
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
							{field: 'balance', title:'结余', width: 80, sortable: true, align: 'right',
								formatter: function(value,row,index){
									return formatterMoney(value);
								}
							}
					   ]]
		});
		
		$datagrid.datagrid({
  	 		frozenColumns: cols.frozen,
			columns: cols.common,
	  	 	fit:true,
	  	 	method:'post',
	  	 	showFooter: true,
	  	 	rownumbers: true,
	  	 	pagination: true,
		 	idField: 'id',
		 	border: false,
	  	 	singleSelect: false,
		 	checkOnSelect: true,
		 	selectOnCheck: true,
			pageSize:20,
			pageList:[10,20,30,50,100,200],
			toolbar: '#journalsheet-condition-matcostsBalanceListPage',
			onLoadSuccess: function(){

				footers = $datagrid.datagrid('getFooterRows');
                // 初始化之后显示“选中合计”
                {
                    var tempFooters = new Array();
                    var footer = {suppliername: '选中合计', income: 0.00, outcome: 0.00, balance: 0.00};

                    tempFooters.push(footer);

                    if(footers.length > 0) {

                        tempFooters.push(footers[0]);
                    }

                    $datagrid.datagrid('reloadFooter', tempFooters);
                }
			},
			onCheckAll: computeSelectedRows,
			onUncheckAll: computeSelectedRows,
			onCheck: computeSelectedRows,
			onUncheck: computeSelectedRows,
			onDblClickRow: function(index, row) {
			
				var supplierid = row.supplierid;
				var suppliername = row.suppliername;
				var deptid = row.deptid;
				var deptname = row.deptname;
				var begindate = row.begindate;
				var enddate = row.enddate;
				var customersort=row.customersort || "";
				var dlgtitle='代垫收支详情-' + '[' + suppliername + ']' + '-[' + deptname + ']';
			   	$('#journalsheet-dialog-matcostsBalanceListPage').dialog({  
					title: dlgtitle,
				    fit:true,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
					href: 'journalsheet/matcostsbalance/matcostsBalanceViewPage.do',
					method:'post',
					queryParams:{
						begindate:begindate,
						enddate:enddate,
						supplierid:supplierid,
						deptid:deptid,
						customersort:customersort,
                        dlgtitle:dlgtitle
					},
				    modal: true
				});
				$('#journalsheet-dialog-matcostsBalanceListPage').dialog("open");
				
			}
		}).datagrid('columnMoving');
		
		// 查询
		$query.unbind().click(function(){

			var paramJSON = $('#journalsheet-form-matcostsBalanceListPage').serializeJSON();

	      	$datagrid.datagrid({
	      		url: 'journalsheet/matcostsbalance/selectMatcostsBalanceList.do',
	      		pageNumber: 1,
				queryParams: paramJSON
	      	});
			
		});
		
		// 重置
		$reset.unbind().click(function(){
			
			$form[0].reset();
			
			$deptid.widget('clear');
			$supplierid.supplierWidget('clear');
			$customersortWidget.widget('clear');
			
			var paramJSON = $('#journalsheet-form-matcostsBalanceListPage').serializeJSON();
			
	      	$datagrid.datagrid({
	      		url: 'journalsheet/matcostsbalance/selectMatcostsBalanceList.do',
	      		pageNumber: 1,
				queryParams: paramJSON
	      	});
		});
		
	});
	
	function computeSelectedRows() {
	
		var rows = $datagrid.datagrid('getChecked');
		
		if(footers.length == 0) {
			return ;
		}
		
 		var income = 0.00;
 		var outcome = 0.00;
 		var balance = 0.00;

 		for(var i = 0; i < rows.length; i++){
 		
 			income = income + parseFloat(rows[i].income);
 			outcome = outcome + parseFloat(rows[i].outcome);
 			balance = balance + parseFloat(rows[i].balance);
 		}

 		var tempFooters = new Array();
 		var footer = {suppliername: '选中合计', income: income, outcome: outcome, balance: balance};
 		
 		tempFooters.push(footer);
 		
 		if(footers.length > 0) {

 			tempFooters.push(footers[0]);
 		}

 		$datagrid.datagrid('reloadFooter', tempFooters);
	
	}

    -->
	</script>
  </body>
</html>