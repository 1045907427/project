<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>日常费用支付申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaDailPayPage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    <!--

	var minLength  = 10;

    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';
    var billtype = '${param.billtype }';

    if(type == 'handle') {
    	
    	url = 'oa/oaDailPayHandlePage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';

		if(step == '02') {
		    url = 'oa/oaDailPayHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
        } else if(step == '03'){
            url = 'oa/oaDailPayHandlePage3.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
        } else if(step == '04'){
            url = 'oa/oaDailPayHandlePage4.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
        } else if(step == '99'){
       		url = 'oa/oaDailPayViewPage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
		}
    	
    } else if(type == 'view') {

        url = 'oa/oaDailPayViewPage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
    } else if(type == 'print') {

        url = 'oa/oaDailPayPrintPage.do?id=${param.id }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
        window.location.href = url;
    }
    
    $(function() {
    
    	$('#oa-panel-oaDailPayPage').panel({
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

				// 
				$comments2.comments({
					businesskey: '${param.id }',
					processid: '${param.processid }',
					type: 'vertical',
					width: '120',
					agree: null
				});

				// 转交信息
                /*
				$comments.comments({
					businesskey: '${param.id }',
					definitionkey: '${param.definitionkey }',
					processid: '${param.processid }',
					taskkey: 'apply,office,financialManager,generalManager,cashier,accountant',
					agree: '1',
					width: 115
				});
				*/

				// 明细
				$paydetail.datagrid({
					columns: [[
						{field:'id', title: '编号', hidden: true},
						{field:'itemid', title: '物品编号', width: 90,
							editor: {
								type: 'validatebox',
								options:{
									required: false,
									validType: 'maxByteLength[20]'
								}
							}
						},
						{field:'itemname', title: '物品名称', width: 150,
							editor: {
								type: 'validatebox',
								options:{
									required: false,
									validType: 'maxByteLength[200]'
								}
							}
						},
						{field:'applydetpid', title: '领用部门', width: 90,
							formatter: function(value,row,index) {
							
								if($('#widget_RL_T_BASE_DEPATMENT_text').length == 0) {
									return row.applydetpname;
								}
							
								return $('#widget_RL_T_BASE_DEPATMENT_text').val();
							},
							editor: {
								type: 'widget',
								options: {
									referwid: 'RL_T_BASE_DEPATMENT',
									required: false,
									onlyLeafCheck: false,
									width: 70,
									onChecked: function(data) {

										$('#widget_RL_T_BASE_DEPATMENT_text').remove();
										$('<input type="hidden" id="widget_RL_T_BASE_DEPATMENT_text" value="' + data.name + '"/>').appendTo('body');
									},
									onClear: function() {

										$('#widget_RL_T_BASE_DEPATMENT_text').remove();
										var rows = $paydetail.datagrid('getRows');
										var row = rows[retPayDetailEditIndex()];
										row.applydetpname = '';
									}
								}
							}
						},
						{field:'uintname', title: '单位', width: 50,
							editor: {
								type: 'validatebox',
								options:{
									required: false,
									validType: 'maxByteLength[20]'
								}
							}
						},
						{field:'unitnum', title: '数量', width: 60, align: 'right',
							editor: {
								type: 'numberbox',
								options:{
									required: false,
									onChange: computeAmount
								}
							}
						},
						{field:'price', title: '单价', width: 60, align: 'right',
							formatter: function(value, row, index){
								return formatterMoney(value, 2);
							},
							editor: {
								type: 'numberbox',
								options:{
									required: false,
									precision: 2,
									min: 0,
									onChange: computeAmount
								}
							}
						},
						{field:'amount', title: '金额', width: 95, align: 'right',
							formatter: function(value, row, index){
								return formatterMoney(value, 2);
							},
							editor: {
								type: 'numberbox',
								options:{
									required: false,
									precision: 2,
									min: 0
								}
							}
						},
						{field:'isfix', title: '是否固产', width: 65,
							formatter: function(value,row,index) {
								
								if(value == undefined || value == null || value == '') {
									return '';
								} else if(value == '0') {
									return '否';
								}
								return '是';
							},
							editor: {
								type: 'selectboxText',
								options: {
									vals: '0,1',
									texts: '否,是'
								}
							}
						},
						{field:'uselife', title: '有效期(年)', width: 70,
							editor: {
								type: 'numberbox',
								options:{ 
									required: true,
									max: 99999
								}
							}
						},
					]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					singleSelect: true,
					<c:if test="${param.step eq '01' }">
						toolbar:[
								{text: '添加', iconCls: 'button-add',    handler: addDailPayDetail},
								{text: '修改', iconCls: 'button-edit',   handler: editDailPayDetail},
								{text: '确定', iconCls: 'button-save',   handler: endEditDailPayDetail},
								{text: '删除', iconCls: 'button-delete', handler: removeDailPayDetail}
								],
						onDblClickRow: editDailPayDetail,
						onClickRow: endEditDailPayDetail,
					</c:if>
				    url: 'oa/selectOaDailPayDetailList.do?billid=${param.id }',
					onLoadSuccess: function(data) {
			
						for(var i = data.total; i < minLength; i++) {
							$paydetail.datagrid('appendRow', {});
						}
					},
				}); // datagrid close
				
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
				
				// 大写金额
				<c:if test="${param.type eq 'handle' and param.step != '99' }">
				$payamount.focusout(function() {
				
					setTimeout(function() {
					
						var val = $payamount.numberbox('getValue');
						$upperpayamount.val(AmountUnitCnChange(val));
					}, 100);
					
				});
				</c:if>
				
				// 付款银行
				$paybank.widget({
					referwid: 'RL_T_BASE_FINANCE_BANK',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.step eq '03' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 144
				});
				
				// 费用科目
				$costsort.widget({
					referwid: 'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.step eq '01' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
								
				// 所属部门
				$applydeptid.widget({
					referwid: 'RL_T_BASE_DEPATMENT',
					onlyLeafCheck: false,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.step eq '01' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140
				});
				
				// 报销人
				$applyuserid.widget({
					referwid: 'RT_T_BASE_PERSONNEL',
					onlyLeafCheck: true,
					singleSelect: true,
					<c:choose>
						<c:when test="${param.step eq '01' }">
							readonly: false,
							required: true,
						</c:when>
						<c:otherwise>
							readonly: true,
							required: false,
						</c:otherwise>
					</c:choose>
					width: 140,
					onChecked: function(data) {
					
						var uid = $applyuserid.widget('getValue');
						if(uid != null || uid != '') {
						
							var deptid = data.pid;
							if(deptid != undefined && deptid != null && deptid != '' && deptid.length > 0) {
							
								deptid = deptid.substring(1);
								$applydeptid.widget('setValue', deptid);
							}
						}
					},
					onClear: function() {
					
						$applydeptid.widget('clear');
					}
				});

			} // panel.onLoad close
		});
    });
    
    // 获取目前Datagrid处于编辑的行号，返回-1时，表明当前Datagrid未处于编辑状态
    function retPayDetailEditIndex() {
    
    	var rows = $paydetail.datagrid('getRows');
    	
    	for(var i = 0; i < rows.length; i++) {
    		var editors = $paydetail.datagrid('getEditors', i);
    		if(editors.length > 0) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    // 添加明细
    function addDailPayDetail() {
        
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行添加。");
    		return false;
    	}
    	
	    // 搜索未编辑的行
	    var rows = $paydetail.datagrid('getRows');
	    var index = -1; 
	    for(var i = rows.length - 1; i >= 0; i--) {
	    	
	    	var row = rows[i];
	    	if(row.itemid == undefined || row.itemid == null || row.itemid == '') {
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
	    $paydetail.datagrid('appendRow', {});
	    beginEditPayDetailRow(index);

	    return true;
    }

    // 修改客户费用支付明细
    function editDailPayDetail() {
    
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改。");
    		return false;
    	}
    	
    	var selectedRow = $paydetail.datagrid('getSelected');
    	var index = $paydetail.datagrid('getRowIndex', selectedRow);
    	
    	beginEditPayDetailRow(index);
    	return true;
    }
    
    // 删除明细
    function removeDailPayDetail() {
    
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除。");
    		return false;
    	}
    	
    	var selectedRow = $paydetail.datagrid('getSelected');
    	var index = $paydetail.datagrid('getRowIndex', selectedRow);
    	
    	$paydetail.datagrid('deleteRow', index);
    	computeTotalAmount();
    	return true;
    }
    
    // 编辑明细
    function beginEditPayDetailRow(rowIndex) {

		if(rowIndex < 0){
			$.messager.alert("提醒", "请选择记录！");
			return false;
		}
	
		$paydetail.datagrid('beginEdit', rowIndex);
		$paydetail.datagrid('scrollTo', rowIndex);
		
		var isfix = $paydetail.datagrid('getEditor', {index: retPayDetailEditIndex(), field: 'isfix'});
		var uselife = $paydetail.datagrid('getEditor', {index: retPayDetailEditIndex(), field: 'uselife'});
		$(isfix.target).change(function() {
			if($(this).val() == '0') {
				$(uselife.target).numberbox({required: false});
				$(uselife.target).numberbox('clear');
				$(uselife.target).numberbox('disable');
			} else {
				$(uselife.target).numberbox('enable');
				$(uselife.target).numberbox({required: true});
			}
			$(uselife.target).numberbox('validate');
		});
		
		if($(isfix.target).val() == '0') {
			$(uselife.target).numberbox({required: false});
			$(uselife.target).numberbox('clear');
			$(uselife.target).numberbox('disable');
		} else {
			$(uselife.target).numberbox('enable');
			$(uselife.target).numberbox({required: true});
		}
		$(uselife.target).numberbox('validate');
		
		return true;
    }
    
    function endEditDailPayDetail() {
        
    	var editIndex = retPayDetailEditIndex();
    	if(editIndex < 0) {
    		return true;
    	}
    	
    	$paydetail.datagrid('endEdit', editIndex);
    	
    	computeTotalAmount();

    	return true;
    }
    
    // 计算金额
    function computeAmount() {
    		
		var editIndex = retPayDetailEditIndex();
		
	    var editor1 = $paydetail.datagrid('getEditor', {index: editIndex, field:'unitnum'});
	    var editor2 = $paydetail.datagrid('getEditor', {index: editIndex, field:'price'});
	    var editor3 = $paydetail.datagrid('getEditor', {index: editIndex, field:'amount'});

	    var unitnum = editor1.target.numberbox('getValue');
	    var price = editor2.target.numberbox('getValue');
	    var amount = '';
	    
	    if(unitnum == '' || price == '' || unitnum == null || price == null) {

			editor3.target.numberbox('clear');
			return false;
			
		} else {
		
			amount = unitnum * price;
		}

	    editor3.target.numberbox('setValue', amount);
    
    	return true;
    }
    
    // function 
    function computeTotalAmount() {
        	
    	var rows = $paydetail.datagrid('getRows');
    	var amount = parseFloat(0.00);
    	
    	for(var i = 0; i < rows.length; i++) {
    		
    		var row = rows[i];
    		if(row.amount == undefined || row.amount == null || row.amount == '') {
    		
    		} else {
    		
    			amount = parseFloat(amount) + parseFloat(row.amount);
    		}
    		
    	}
    	
    	$payamount.numberbox('setValue', amount);
    	$upperpayamount.val(AmountUnitCnChange(amount));
    }
    
    //
    function oadailpay_handle_save_form_submit(callBack, args) {
    
    	$form.form({
    		onSubmit: function(param) {
    		
    			var flag = $form.form('validate');
    			if(!flag) {
    			
    				return false;
    			}

				$detaillist.val(JSON.stringify($paydetail.datagrid('getRows')));
				
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

	// 提交表单(工作页面提交表单接口方法)
	function workFormSubmit(call, args) {

        oadailpay_handle_save_form_submit(call, args);
	}

	-->
	</script>
  </body>
</html>