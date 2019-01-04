<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫收支详情页面</title>
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
				<div class="buttonBG">
					<security:authorize url="/journalsheet/matcostsbalance/exportMatcostsBalanceDetailListData.do">
						<a href="javaScript:void(0);" id="journalsheet-export-matcostsBalanceViewPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
					</security:authorize>
				</div>
				<form id="journalsheet-form-matcostsBalanceViewPage">
					<input type="hidden" id="journalsheet-supplierid-matcostsBalanceViewPage" name="supplierid" value="${param.supplierid }"/>
					<input type="hidden" id="journalsheet-deptid-matcostsBalanceViewPage" name="deptid" value="${param.deptid }"/>
					<table>
						<tr>
							<td>业务日期：</td>
							<td><input class="easyui-validatebox Wdate len100" id="journalsheet-begindate-matcostsBalanceViewPage" name="begindate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${param.begindate }"/>到<input class="easyui-validatebox Wdate len100" id="journalsheet-enddate-matcostsBalanceViewPage" name="enddate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false"  value="${param.enddate }"/></td>
							<td class="len70">费用类别：</td>
							<td class="len150"><input class="easyui-validatebox len150" id="journalsheet-subjectid-matcostsBalanceViewPage" name="subjectid" data-options="required:false" /></td>
                            <td>收&nbsp;&nbsp;支：</td>
                            <td>
                                <select id="journalsheet-balancetype-matcostsBalanceViewPage" name="balancetype" style="width:185px">
                                    <option value="0">全部</option>
                                    <option value="1">收入</option>
                                    <option value="2">支出</option>
                                </select>
                            </td>
						</tr>							
						<tr>
                            <td>客&nbsp;&nbsp;户：</td>
                            <td>
                                <input id="journalsheet-customerid-matcostsBalanceViewPage" type="text" name="customerid" style="width: 212px"/>
                            </td>
							<td>客户分类：</td>
							<td>
								<input id="journalsheet-customersort-matcostsBalanceViewPage" type="text" name="customersort" class="len150" value="${param.customersort }"/>
							</td>
							<td class="right">是否红冲：</td>
							<td class="len100">
								<select id="journalsheet-hcflag-matcostsBalanceViewPage" name="ishcflag" style="width:185px">
									<option value="">全部</option>
	    							<option value="1">是</option>
	    							<option value="2">否</option>
								</select>
							</td>
						</tr>
						<tr>
							<td colspan="4"></td>
							<td colspan="2" align="right">
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

	var $bigindate2 = $('#journalsheet-begindate-matcostsBalanceViewPage');
	var $enddate2 = $('#journalsheet-enddate-matcostsBalanceViewPage');
	var $subjectid2 = $('#journalsheet-subjectid-matcostsBalanceViewPage');
	var $datagrid2 = $('#journalsheet-datagrid-matcostsBalanceViewPage');
	var $deptid2 = $('#journalsheet-deptid-matcostsBalanceViewPage');
	var $supplierid2 = $('#journalsheet-supplierid-matcostsBalanceViewPage');
	var $balancetype2 = $('#journalsheet-balancetype-matcostsBalanceViewPage');
    var $customerid2 = $("#journalsheet-customerid-matcostsBalanceViewPage");
	var $customerSort2 =$("#journalsheet-customersort-matcostsBalanceViewPage");
	
	var $query2 = $('#journalsheet-query-matcostsBalanceViewPage');
	var $reset2 = $('#journalsheet-reset-matcostsBalanceViewPage');
	var $dialog2 = $('#journalsheet-dialog-matcostsBalanceViewPage');
	var $form2 = $('#journalsheet-form-matcostsBalanceViewPage');

	var footers2 = new Array();

	$(function(){
	
		// 科目
		$subjectid2.widget({
			required: false,
			width: 150,
			referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
			onlyLeafCheck: false
		});
        //客户
        $customerid2.customerWidget({
            isall:true,
            singleSelect:true,
            onlyLeafCheck:false
        });

		$customerSort2.widget({ //分类
			referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
			required: false,
			singleSelect:true,
			width:150,
			onlyLeafCheck:false,
			view: true
		});
		// List
		var cols2 = $datagrid2.createGridColumnLoad({
			frozenCol: [[
							{field:'ck',checkbox:true},
							{field: 'id', title: '编号',hidden: true},
							{field: 'businessdate', title: '业务日期', sortable: true, width: 80},
                            {field: 'oaid', title: 'OA编号',
                                formatter: function(value,row,index){

                                    if(value != undefined
                                            && value != null
                                            && value != ''
                                            && value != '合计'
                                            && value != '选中金额') {

                                        return '<a href="javascript:void(0);" onclick="viewOa(\'' + value + '\')">' + value + '</a>';
                                    }

                                    return value;
                                }
                            },
							{field: 'supplierid', title: '供应商编号',hidden: true},
							{field: 'suppliername', title: '供应商', sortable: true, width: 180},
							{field: 'deptid', title: '部门编号',hidden: true},
							{field: 'deptname', title: '品牌部门', sortable: true, width: 112},
							{field: 'customerid', title: '客户编号',hidden:true},
							{field: 'customername', title: '客户名称', sortable: true, width: 180},
							{field: 'customersortname', title: '客户分类名称', sortable: true, width: 100},
							{field: 'hcflag', title: '是否红冲', sortable: true, width: 60,
								formatter:function(val,rowData,rowIndex){
									if(val=='0'){
										return "否";
									}else if(val=='1' || val=='2'){
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
							{field: 'subjectname', title: '费用科目', width: 90},
                            {field: 'remark', title: '备注'}
					   ]]
		});
	
		$datagrid2.datagrid({
			//authority: cols2,
  	 		frozenColumns: cols2.frozen,
			columns: cols2.common,
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

				footers2 = $datagrid2.datagrid('getFooterRows');
				$datagrid2.datagrid('hideColumn', 'income');
				$datagrid2.datagrid('hideColumn', 'outcome');
				
				var v = $balancetype2.val();
				if(v == 1) {
				
					$datagrid2.datagrid('showColumn', 'income');
					
				} else if(v == 2) {
				
					$datagrid2.datagrid('showColumn', 'outcome');
					
				} else {
				
					$datagrid2.datagrid('showColumn', 'income');
					$datagrid2.datagrid('showColumn', 'outcome');
				}
			},
 			rowStyler:function(index,row){
 				if(row.hcflag && row.hcflag=='1'){
 					return "color:#f00";
 				}else if(row.hcflag 
		 					&& row.hcflag=='2'){
		 			return "color:#00f";
		 		}
 			}
		}).datagrid('columnMoving');
		// 查询
		$query2.unbind().click(function(){
			
			var paramJSON = $('#journalsheet-form-matcostsBalanceViewPage').serializeJSON();
			
	      	$datagrid2.datagrid({
	      		url: 'journalsheet/matcostsbalance/selectMatcostsBalanceDetailList.do',
	      		pageNumber: 1,
				queryParams: paramJSON
	      	});
			
		});
		
		// 重置
		$reset2.unbind().click(function(){
			
			$form2[0].reset();
			
			$subjectid2.widget('clear');
            $customerid2.customerWidget('clear');

			$customerSort2.widget('clear');
			
			var paramJSON = $('#journalsheet-form-matcostsBalanceViewPage').serializeJSON();
			
	      	$datagrid2.datagrid({
	      		url: 'journalsheet/matcostsbalance/selectMatcostsBalanceDetailList.do',
	      		pageNumber: 1,
				queryParams: paramJSON
	      	});
		});
		
		//$query2;


        $("#journalsheet-export-matcostsBalanceViewPage").click(function(){
            //封装查询条件
            var objecr  = $datagrid2.datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "journalsheet/matcostsbalance/exportMatcostsBalanceDetailListData.do?exporttype=base";
            var title="${param.dlgtitle}";
            title=$.trim(title);
            if(title==""){
                title="代垫收支详情明细";
            }
            exportByAnalyse(queryParam,title,"journalsheet-datagrid-matcostsBalanceViewPage",url);
        });


    });
	
	function computeSelectedDetailRows() {
	
		var rows = $datagrid2.datagrid('getChecked');
		
		if(footers2.length == 0) {
			return ;
		}
		
		if(rows.length == 0) {
			
			$datagrid2.datagrid('reloadFooter', footers2);
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
 		
 		if(footers2.length > 0) {

 			tempFooters.push(footers2[0]);
 		}

 		$datagrid2.datagrid('reloadFooter', tempFooters);
	}

    function viewOa(id) {

        top.addTab('act/workViewPage.do?processid=' + id, '工作查看');
    }

    </script>
  </body>
</html>