<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户费用支付申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <style type="text/css">
        .len138{
            width: 138px;
        }
		.len148{
			width: 148px;
		}
        .len160{
            width: 160px;
        }
    </style>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaCustomerPayPage">
    		</div>
    	</div>
    </div>
	<div id="oa-dialog-oaCustomerPayPage"></div>
    <script type="text/javascript">
    <!--
    
    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';

    if(type == 'handle') {
    
    	if(step == '99') {
    		url = 'oa/hd/oaCustomerPayViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    	} else if(step == '02') {
    	
    		url = 'oa/hd/oaCustomerPayHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
    	} else {
    	
    		url = 'oa/hd/oaCustomerPayHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
    	}
    } else if(type == 'view') {

        url = 'oa/hd/oaCustomerPayViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    } else if(type == 'print') {

        url = 'oa/hd/oaCustomerPayPrintPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
        window.location.href = url;
    }

    var minLength = 10;	// 品牌明细最小行数
    var defaultExecuteDate = (new Date()).Format("yyyy-MM-dd");
    
    $(function() {
    
    	$('#oa-panel-oaCustomerPayPage').panel({
			href: url,
			cache: false,
			maximized: true,
			border: false,
			onLoad: function() {
				
				// 附件
				$attach.attach({
					<c:choose>
						<c:when test="${param.type == 'view' }">
							attach: false,
						</c:when>
						<c:otherwise>
							attach: true,
						</c:otherwise>
					</c:choose>
					businessid: '${param.id }', 
					processid: '${param.processid }'
				});
				
				$comments2.comments({
					businesskey: '${param.id }',
					processid: '${param.processid }',
					type: 'vertical',
					width: '120',
					agree: null
				});

                /*
				$comments.comments({
					businesskey: '${param.id }',
					definitionkey: '${param.definitionkey }',
					processid: '${param.processid }',
					taskkey: 'apply,departmentManager,financialManager,generalManager,cashier,accountant',
					agree: '1',
					width: 117
				});
				*/

				// 客户
				initCustomerWidget({
                    readonly: true,
					initValue: ''
				});
                // 客户
                initCustomerWidget2({
                    <c:choose>
                        <c:when test="${param.type eq 'handle' and param.step ne '99' }">
                            readonly: false,
                        </c:when>
                        <c:otherwise>
                            readonly: true,
                        </c:otherwise>
                    </c:choose>
                    initValue: ''
                });
								
				// 所属部门
				$deptid.widget({
					referwid: 'RL_T_BASE_DEPATMENT',
					onlyLeafCheck: false,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							readonly: false,
							required: false,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				// 付款银行
				$paybank.widget({
					referwid: 'RL_T_BASE_FINANCE_BANK',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and empty param.step }">
							required: false,
						</c:when>
						<c:when test="${param.type eq 'handle' and param.step eq '02' }">
							required: true,
						</c:when>
						<c:when test="${param.type eq 'handle' and param.step eq '99' }">
							required: false,
						</c:when>
						<c:otherwise>
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				// 费用科目
				$expensesort.widget({
					referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							required: true,
						</c:when>
						<c:otherwise>
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				initSupplierWidget({
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							required: true,
							readonly: false,
						</c:when>
						<c:otherwise>
							required: false,
							readonly: true,
						</c:otherwise>
					</c:choose>
					id: '',
					name: '',
                    onSelect: function(data) {

                        $deptid.widget('setValue', data.buydeptid);
//                        var supplierid = $supplierid.supplierWidget('getValue');
//                        $.ajax({
//                            type: 'post',
//                            dataType: 'json',
//                            url: 'oa/common/selectSupplierInfo.do',
//                            data: {id: supplierid},
//                            success: function(json) {
//
//                                $deptid.widget('setValue', json.supplier.buydeptid);
//                            }
//                        });
                    },
                    onClear: function() {

                        $deptid.widget('clear');
                    }
				});
				
				// 支付明细
				$customerpaydetail.datagrid({
					columns: [[
						{field:'id', title: '编号', hidden: true},
						{field:'brandid', title: '品牌', width: 130,
							formatter: function(value,row,index){
								
								var brandname = $('#oa-RL_T_BASE_GOODS_BRAND-oaCustomerPayPage'+ value).val();
								if($('#oa-RL_T_BASE_GOODS_BRAND-oaCustomerPayPage'+ value).length == 0) {
									return row.brandname;
								}
								return brandname;
							},
							editor: {
								type: 'widget', 
								options:{
									referwid: 'RL_T_BASE_GOODS_BRAND',
									required: false,
									singleSelect: true,
									width: 125,
									onSelect: function(data) {
									
										$('#oa-RL_T_BASE_GOODS_BRAND-oaCustomerPayPage' + data.id).remove();
										$('<input type="hidden" class="RL_T_BASE_GOODS_BRAND-widget" id="oa-RL_T_BASE_GOODS_BRAND-oaCustomerPayPage' + data.id + '" value="' + data.name + '" />').appendTo('body');
										
										var brandid = data.id;
										var customerid = $customerid.widget('getValue');
										var begindate = $sharebegindate.val();
										var enddate = $shareenddate.val();
										
										$.ajax({
											type: 'post',
											dataType: 'json',
											url: 'oa/hd/getCustomerBrandSalesAmount.do',
											data: {customerid: customerid, brandid: brandid, begindate: begindate, enddate: enddate},
											success: function(json) {
												
												var salesamount = json.salesamount;
												var editor = $customerpaydetail.datagrid('getEditor', {index: retPayDetailEditIndex(), field:'salesamount'});
												editor.target.text(formatterMoney(salesamount, 2));
												
												computeRate();
											},
											error: function() {
											
											}
										});
									},
									onClear: function() {
										$('.RL_T_BASE_GOODS_BRAND-widget').remove();
										var rows = $customerpaydetail.datagrid('getRows');
										var row = rows[retPayDetailEditIndex()];
										row.brandname = '';
									}
								}
							}
						},
						{field:'expensesort', title: '费用科目', width: 80,
							formatter: function(value,row,index){
								
								var expensesortname = $('#oa-RT_T_BASE_FINANCE_EXPENSES_SORT-oaCustomerPayPage'+ value).val();
								if(expensesortname == undefined || expensesortname == null || expensesortname == '') {
									return row.expensesortname;
								}
								return expensesortname;
							},
							editor: {
								type: 'widget', 
								options:{
									referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
									required: false,
									singleSelect: true,
									width: 55,
									onChecked: function(data) {
									
										if(data.checked) {

											$('#oa-RT_T_BASE_FINANCE_EXPENSES_SORT-oaCustomerPayPage' + data.id).remove();
											$('<input type="hidden" id="oa-RT_T_BASE_FINANCE_EXPENSES_SORT-oaCustomerPayPage' + data.id + '" value="' + data.name + '" />').appendTo('body');
										}
									},
									onClear: function() {
										var rows = $customerpaydetail.datagrid('getRows');
										var row = rows[retPayDetailEditIndex()];
										row.expensesortname = '';
									}
								}
							}
						},
						{field:'executedate', title: '费用执行时间', width: 100,
							editor: {
								type: 'dateText'
							}
						},
						{field:'deptid', title: '费用部门', width: 100,
							formatter: function(value,row,index){ 
								return row.deptname; 
							},
							formatter: function(value,row,index){
								
								var deptname = $('#oa-RL_T_BASE_DEPATMENT-oaCustomerPayPage'+ value).val();
								if(deptname == undefined || deptname == null || deptname == '') {
									return row.deptname;
								}
								return deptname;
							},
							editor: {
								type: 'widget', 
								options:{
									referwid: 'RL_T_BASE_DEPATMENT',
									required: false,
									singleSelect: true,
									onlyLeafCheck: false,
									width: 75,
									onChecked: function(data) {
									
										if(data.checked) {

											$('#oa-RL_T_BASE_DEPATMENT-oaCustomerPayPage' + data.id).remove();
											$('<input type="hidden" id="oa-RL_T_BASE_DEPATMENT-oaCustomerPayPage' + data.id + '" value="' + data.name + '" />').appendTo('body');
										}
									},
									onClear: function() {
										var rows = $customerpaydetail.datagrid('getRows');
										var row = rows[retPayDetailEditIndex()];
										row.deptname = '';
									}
								}
							}
						},
						{field:'amount', title: '费用金额', width: 80, align: 'right',
							formatter: function(value,row,index){ 
								return formatterMoney(value, 2); 
							},
							editor: {
								type: 'numberbox', 
								options:{
									precision:2, 
									onChange: computeRate
								}
							}
						},
						{field:'salesamount', title: '销售金额', width: 80, align: 'right',
							formatter: function(value,row,index){
							
								row.salesamount = formatterMoney(value, 2);
								return formatterMoney(value, 2); 
							},
							editor: {
								type: 'span'
							}
						},
						{field:'rate', title: '费比', width: 50, align: 'right',
							formatter: function(value,row,index){
							
								var rate = formatterMoney(value, 2);
								if(rate == null || rate == '') {
								
									return '';
								}
								return formatterMoney(value, 2) + '%'; 
							},
							editor: {
								type: 'numberbox', 
								options:{
									precision:2,
									suffix: '%',
									onChange: function(){
									}
								}
							}
						},
						{field:'remark', title: '备注', width: 120,
							editor: {
								type: 'validatebox', 
								options:{
									required: false,
									validType:{
										length:[0, 100]
									},
									onChange: function(){
									}
								}
							}
						}
					]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							toolbar:[
									{text: '添加', iconCls: 'button-add',    handler: addCustomerPayDetail},
									{text: '修改', iconCls: 'button-edit',   handler: editCustomerPayDetail},
									{text: '确定', iconCls: 'button-save',   handler: endEditCustomerPayDetail},
									{text: '删除', iconCls: 'button-delete', handler: removeCustomerPayDetail}
									],
							onDblClickRow: editCustomerPayDetail,
							onClickRow: endEditCustomerPayDetail,
						</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose>
				    url: 'oa/hd/selectOaCustomerPayDetailList.do?billid=${param.id }',
					onLoadSuccess: function(data) {
			
						for(var i = data.total; i < minLength; i++) {
							$customerpaydetail.datagrid('appendRow', {});
						}
					}
				});

                // OA号
                // OA号输入后，显示查看链接
                switchViewLink(-1);

                // 对所有的select项目根据其data属性进行赋值
				$('select').each(function() {
				
					var data = $(this).attr('data');
					$(this).removeAttr('data');
					$(this).children().removeAttr('selected');

					$(this).children().each(function() {
					
						if($(this).attr('value') == data) {
							$(this).attr('selected', 'selected');
						}
					});
				});
				
				$payamount.focusout(function() {
				
					setTimeout(function() {
					
						var val = $payamount.numberbox('getValue');
						$upperpayamount.val(AmountUnitCnChange(val));
					}, 100);
					
				});
				
				// 摊销开始日期变动时，重新获取销售金额
				$sharebegindate.blur(function() {
				
					var olddate = $sharebegindate_hidden.val();
					var newdate = $sharebegindate.val();
					
					if(olddate == newdate) {
						
						return true;
					}
					$sharebegindate_hidden.val(newdate);
					
					refreshBrandSalesAmount();
					
				});
				
				// 摊销结束日期变动时，重新获取销售金额
				$shareenddate.blur(function() {
				
					var olddate = $shareenddate_hidden.val();
					var newdate = $shareenddate.val();
					
					if(olddate == newdate) {
						
						return true;
					}
					$shareenddate_hidden.val(newdate);
					
					refreshBrandSalesAmount();
					
				});
				
				$sharetype.change(switchSharetype);
				switchSharetype();
				
			// panel onLoad close
			}
		});
    });
    
    // 添加客户费用支付明细
    function addCustomerPayDetail() {
    
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行添加。");
    		return false;
    	}
    	
	    // 搜索未编辑的行
	    var rows = $customerpaydetail.datagrid('getRows');
	    var index = -1; 
	    for(var i = rows.length - 1; i >= 0; i--) {
	    	
	    	var row = rows[i];
	    	if(row.brandid == undefined || row.brandid == null || row.brandid == '') {
	    		index = i;
	    	} else {
	    		break;
	    	}
	    }
	    
	    // 明细中存在未编辑的空行
	    if(index >= 0) {
	    
	    	beginEditPayDetailRow(index);
	    	return true;
	    }
	    
	    // 明细中所有的行均已被编辑，添加空行
	    index = rows.length;
	    $customerpaydetail.datagrid('appendRow', {});
	    beginEditPayDetailRow(index);

	    return true;
    	
    }
    
    // 修改客户费用支付明细
    function editCustomerPayDetail() {
    
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改。");
    		return false;
    	}
    	
    	var selectedRow = $customerpaydetail.datagrid('getSelected');
    	var index = $customerpaydetail.datagrid('getRowIndex', selectedRow);
    	
    	beginEditPayDetailRow(index);
    	return true;
    }
    
    // 结束对客户费用支付明细的修改
    function endEditCustomerPayDetail() {
    
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex < 0) {
    		return true;
    	}
    	
    	$customerpaydetail.datagrid('endEdit', editIndex);
		// $customerid.widget('readonly', false);
		initCustomerWidget({
			readonly: false,
			initValue: $customerid.widget('getValue')
		});
		/*
		initOaWidget({
			readonly: false,
			initValue: $relateoaid.widget('getValue')
		});
		*/
		$relateoaid.numberbox('enable');
		$sharebegindate.removeAttr('disabled');
		$shareenddate.removeAttr('disabled');

    	return true;
    }
    
    // 删除客户费用支付明细
    function removeCustomerPayDetail() {
    
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除。");
    		return false;
    	}
    	
    	var selectedRow = $customerpaydetail.datagrid('getSelected');
    	var index = $customerpaydetail.datagrid('getRowIndex', selectedRow);
    	
    	$customerpaydetail.datagrid('deleteRow', index);
    	return true;
    }
    
    // 获取目前Datagrid处于编辑的行号，返回-1时，表明当前Datagrid未处于编辑状态
    function retPayDetailEditIndex() {
    
    	var rows = $customerpaydetail.datagrid('getRows');
    	
    	for(var i = 0; i < rows.length; i++) {
    		var editors = $customerpaydetail.datagrid('getEditors', i);
    		if(editors.length > 0) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    // 编辑明细
    function beginEditPayDetailRow(rowIndex) {
    
    	var defaultDeptFlag = false;
    	var defaultExpenseFlag = false;
    	// defaultExecuteDate
    	var defaultExecuteDateFlag = false;

		if($customerid.widget('getValue') == null || $customerid.widget('getValue') == '') {

			$.messager.alert("提醒", "请选择客户！");
			$('#' + $customerid.attr('id')).focus();
			$('#' + $customerid.attr('id')).validatebox('validate');
			return false;
		}

		if($sharebegindate.val() == null || $sharebegindate.val() == '') {

			$.messager.alert("提醒", "请选择输入摊销时间！");
			$sharebegindate.focus();
			$sharebegindate.validatebox('validate');
			return false;
		}

		if($shareenddate.val() == null || $shareenddate.val() == '') {

			$.messager.alert("提醒", "请选择输入摊销时间！");
			$shareenddate.focus();
			$shareenddate.validatebox('validate');
			return false;
		}

		if(rowIndex < 0){
			$.messager.alert("提醒", "请选择记录！");
			return false;
		}
		
		var rows = $customerpaydetail.datagrid('getRows');
		row = rows[rowIndex];
		if(row.brandid == undefined || row.brandid == null || row.brandid == '') {
			defaultDeptFlag = true;
			defaultExpenseFlag = true;
			defaultExecuteDateFlag = true;
		}
	
		// $customerid.widget('readonly', true);
		initCustomerWidget({
			readonly: true,
			initValue: $customerid.widget('getValue')
		});
		/*
		initOaWidget({
			readonly: true,
			initValue: $relateoaid.widget('getValue')
		});
		*/
		$relateoaid.numberbox('disable');
		$sharebegindate.attr('disabled', 'disabled');
		$shareenddate.attr('disabled', 'disabled');
	
		$customerpaydetail.datagrid('beginEdit', rowIndex);
		$customerpaydetail.datagrid('scrollTo', rowIndex);
		
		// 设置默认部门、默认费用科目、默认执行日期
		var deptid = $deptid.widget('getValue');
		var expensesort = $expensesort.widget('getValue');
		var executedate = defaultExecuteDate;
		
		if(deptid != undefined && deptid != null && deptid != '' && !defaultDeptFlag) {
		
			// return true;
		} else {
		
			setTimeout(function() {
	
			    var editor = $customerpaydetail.datagrid('getEditor', {index: rowIndex, field:'deptid'});
				editor.target.widget('setValue', deptid);
				return true;
			}, 100);
		}
		
		if(expensesort != undefined && expensesort != null && expensesort != '' && !defaultExpenseFlag) {
		
			// return true;
		} else {
		
			setTimeout(function() {
	
			    var editor = $customerpaydetail.datagrid('getEditor', {index: rowIndex, field:'expensesort'});
				editor.target.widget('setValue', expensesort);
				return true;
			}, 100);
		}
		
		if(executedate != executedate && executedate != null && executedate != '' && !defaultExecuteDateFlag) {
		
			// return true;
		} else {
		
			setTimeout(function() {
	
			    var editor = $customerpaydetail.datagrid('getEditor', {index: rowIndex, field:'executedate'});
				editor.target.val(executedate);
				return true;
			}, 100);
		}
		
		return true;
    }
    
    // 计算费率
    function computeRate() {
    		
		var editIndex = retPayDetailEditIndex();
		
	    var editor1 = $customerpaydetail.datagrid('getEditor', {index: editIndex, field:'amount'});
	    var editor2 = $customerpaydetail.datagrid('getEditor', {index: editIndex, field:'salesamount'});
	    var editor3 = $customerpaydetail.datagrid('getEditor', {index: editIndex, field:'rate'});

	    var amount = editor1.target.numberbox('getValue');
	    var salesamount = editor2.target.text();
	    var rate = '';
	    
	    if(amount != null && amount != '' 
	    	&& salesamount != null && salesamount != '' && salesamount != '0' && salesamount != '0.00') {

			rate = amount / salesamount * 100;
		}

	    editor3.target.numberbox('setValue', rate);
    
    	return true;
    }
    
    // 
    function refreshBrandSalesAmount() {

		var begindate = $sharebegindate.val();
		var enddate = $shareenddate.val();
		var customerid = $customerid.widget('getValue');
					
		var rows = $customerpaydetail.datagrid('getRows');
					
		for(var i = 0; i < rows.length; i++) {
					
			var brandid = rows[i].brandid;
			if(brandid == undefined || brandid == null || brandid == '') {
				continue;
			}
			
			var tempRows2 = $customerpaydetail.datagrid('getRows');
			var tempRow2 = tempRows2[i];
			tempRow2.salesamount = '0.00';
			tempRow2.rate = '0.00';
			$customerpaydetail.datagrid('updateRow', {index: i, row: tempRow2});
				
			$.ajax({
				type: 'post',
				dataType: 'json',
				url: 'oa/hd/getCustomerBrandSalesAmount.do',
				data: {customerid: customerid, brandid: brandid, begindate: begindate, enddate: enddate, index: i},
				success: function(json) {

					var index = json.index;
					var salesamount = json.salesamount;

					var tempRows = $customerpaydetail.datagrid('getRows');
					var tempRow = tempRows[index];
					tempRow.salesamount = salesamount;
					
					var amount = tempRow.amount;
					if(salesamount == undefined || salesamount == null || salesamount == '' || salesamount == '0' || salesamount == '0.00') {
					
						tempRow.rate = '0.00';
					} else if(amount == undefined || amount == null || amount == '' || amount == '0' || amount == '0.00') {
					
						tempRow.rate = '0.00';
					} else {
					
						tempRow.rate = formatterMoney(amount / salesamount * 100, 2);
					}

					$customerpaydetail.datagrid('updateRow', {index: index, row: tempRow});
				},
				error: function() {
							
				}
			});
		}
    }
    
    // 初始化客户控件
    function initCustomerWidget(options) {
    
    	var defaults = {
    		readonly: false,
    		initValue: ''
    	};

		options = $.extend(defaults, options);

		// 客户
		$customerid.widget({
			referwid: 'RL_T_BASE_SALES_CUSTOMER_OPEN',
			singleSelect: true,
			<c:choose>
				<c:when test="${param.type eq 'handle' and param.step != '99' }">
					required: true,
				</c:when>
				<c:otherwise>
					required: false,
				</c:otherwise>
			</c:choose>
			width: 140,
			readonly: options.readonly,
			initValue: options.initValue,
			onSelect: function(data) {
				
				var oldid = $customerid_hidden.val();
				var newid = data.id;
				
				if(oldid == newid) {
				
					return true;
				}
				$customerid_hidden.val(data.id);
				refreshBrandSalesAmount();
			},
			onClear: function() {
				
				var oldid = $customerid_hidden.val();
				var newid = '';
				
				if(oldid == newid) {
				
					return true;
				}
				$customerid_hidden.val('');
				refreshBrandSalesAmount();
			}
		});

        // $customerid.widget('disable');
        //$customerid.widget('readonly', true);

    }

    // 初始化客户控件
    function initCustomerWidget2(options) {

        // 客户
        $customerid2.customerWidget({
            singleSelect: true,
            required: false,
            width: 143,
            readonly: options.readonly,
            initValue: options.initValue,
            isall: true,
            onSelect: function(data) {

                $collectionname.val(data.caraccount);
                $collectionbank.val(data.bank);
                $collectionbankno.val(data.cardno);
            }
        });

        $customerid2.customerWidget('setText', '${customer2.name }');
    }

    // 初始化OA控件
    function initOaWidget(options) {
    
    	var defaults = {
    		readonly: false,
    		initValue: ''
    	};

		options = $.extend(defaults, options);

		// OA号
		$relateoaid.widget({
			referwid: 'RL_T_OA_ACCESS_OANO',
			singleSelect: true,
			<c:choose>
				<c:when test="${param.type eq 'handle' and param.step != '99' }">
					required: true,
				</c:when>
				<c:otherwise>
					required: false,
				</c:otherwise>
			</c:choose>
			readonly: options.readonly,
			initValue: options.initValue,
			width: 144,
			onSelect: function(data) {

				initCustomerWidget({
					readonly: false,
					initValue: $customerid.widget('getValue')
				});

			}
		});

    }
    
    // 控制查看链接
    // 页面初始化时，initflag设为-1
    function switchViewLink(initflag) {

    	$link.hide();
    	var oaid = $relateoaid.numberbox('getValue');

    	if(oaid == undefined || oaid == null || oaid == '') {

            if(initflag >= 0) {

                $customerid.widget('clear');
                $deptid.widget('clear');
                $supplierid.supplierWidget('clear');
            }
            return false;
    	}

		$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'act/selectProcess.do',
			data: {id: oaid},
			success: function(json) {
				
				//if(json.process != null && json.process.definitionkey == 'addOaAccess' && json.process.status == '9') {
				//	$link.show();
					
					$.ajax({
						type: 'post',
						dataType: 'json',
						url: 'oa/selectAccessInfo.do',
						data: {id: json.process.businessid },
						success: function(json2) {

                            if(json.process != null && json.process.status == '9' && json2.access != null) {

                                $link.show();
                                if (initflag >= 0) {

                                    $customerid.widget('setValue', json2.customer.id);
                                    setTimeout(function() {

                                        $customerid2.customerWidget('setValue', json2.customer.id);
                                        $customerid2.customerWidget('setText', $customerid.widget('getText'));
                                    }, 300);


                                    $collectionname.val(json2.customer.caraccount);
                                    $collectionbank.val(json2.customer.bank);
                                    $collectionbankno.val(json2.customer.cardno);

                                    $.ajax({
                                        type: 'post',
                                        dataType: 'json',
                                        url: 'oa/common/selectSupplierInfo.do',
                                        data: {id: json2.access.supplierid },
                                        success: function (json3) {

                                            $supplierid.supplierWidget('setValue', json3.supplier.id);
                                            $supplierid.supplierWidget('setText', json3.supplier.name);
                                            $deptid.widget('setValue', json3.supplier.buydeptid);
                                        }
                                    });
                                }
                            } else {

                                $.messager.alert("提醒", "请输入已经完结的OA通路单号！");
                                $relateoaid.numberbox('reset');
                                setTimeout(function() {
                                    $relateoaid.focus();
                                }, 100);
                            }
						}
					});
					
					return true;
				//}
				
				$.messager.alert("提醒", "请输入已经完结的OA通路单号！");
				$relateoaid.numberbox('reset');
				setTimeout(function() {
					$relateoaid.focus();
				}, 100);
							
				$customerid.widget('clear');
                $deptid.widget('clear');
                $supplierid.supplierWidget('clear');
							
				$collectionname.val('');
				$collectionbank.val('');
				$collectionbankno.val('');
			}
		});
    }
    
    // 查看OA通路单
    function viewOaAccess() {

    	var oaid = $relateoaid.numberbox('getValue');

    	$.ajax({
			type: 'post',
			dataType: 'json',
			url: 'act/selectProcess.do',
			data: {id: oaid},
			success: function(json) {

    			top.addOrUpdateTab("act/workViewPage.do?from=1&taskid="+ json.process.taskd+ "&taskkey="+ json.process.taskkey +"&instanceid=" + json.process.instanceid, "工作查看");
    		}
		});
    	
    }
    
    // 初始化供应商控件
    function initSupplierWidget(options) {

		// 供应商
		$supplierid.supplierWidget(options);
		//$supplierid.supplierWidget('readonly', options.readonly);
        $supplierid.supplierWidget('readonly', true);
    	$supplierid.supplierWidget('setValue', '${supplier.id }');
    	$supplierid.supplierWidget('setText', '${supplier.name }');
        if(options.val == undefined || options.val == null || options.val == '') {

        } else {

            $supplierid.supplierWidget('setValue', options.val);
            $supplierid.supplierWidget('setText', options.text);
        }
    }

	function payamountToUpper() {

		var val = $payamount.numberbox('getValue');
		$upperpayamount.val(AmountUnitCnChange(val));
	}

    // 提交客户费用申请单表单
    function oacustomerpay_handle_save_form_submit(callBack, args) {
    
    	$form.form({
    		onSubmit: function(param) {
    		
    			var flag = $form.form('validate');
    			if(!flag) {
    			
    				return false;
    			}

				$detaillist.val(JSON.stringify($customerpaydetail.datagrid('getRows')));
				
				$('select').removeAttr('disabled');

    			loading("提交中...");
    		},
    		success: function(data) {

    			loaded();
    			var json;
				try{
					json = $.parseJSON(data);
				}catch(e){
					$.messager.alert("提醒","保存失败！");
    				return false;
				}
    			
    			// 保存失败
    			if(data.flag) {
    				$.messager.alert("提醒","保存失败！");
    				return false;
    			}
    			
    			// 保存成功
				$.messager.alert("提醒","保存成功。");
				if(callBack.data != undefined && callBack.data != null) {
				
					callBack.data(json.backid);
					return false;
				}
				 
    		}
    	});
    	
    	$form.submit();
    }
    
    // 切换分摊方式时动作
    function switchSharetype() {
    
    	var v = $sharetype.val();
    	
    	// 是
    	if(v == '0') {
    		
    		$sharebegindate.val('');
    		$shareenddate.val('');
    		
    		$(this).parent().next().hide();
    		$(this).parent().next().next().hide();
    	} else {
    	
    	  	$(this).parent().next().show();
    	  	$(this).parent().next().next().show();
    	}
    	
    	refreshBrandSalesAmount();
    }

	// 提交表单(工作页面提交表单接口方法)
	function workFormSubmit(call, args) {

        endEditCustomerPayDetail();
		if(retPayDetailEditIndex() >= 0) {
		
			$.messager.alert("提醒", "明细正处于编辑中，无法提交！");
			return true;
		}

        oacustomerpay_handle_save_form_submit(call, args);
		
	}

	-->
	</script>
  </body>
</html>