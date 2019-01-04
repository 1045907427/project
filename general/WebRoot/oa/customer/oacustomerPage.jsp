<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>新客户登录票页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<input type="hidden" id="oa-backid-oacustomerPage" value="${param.id }" />
  	<input type="hidden" id="button-interface-activiti" value="oa-buttons-oacustomerPage" />
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oacustomerPage">
    		</div>
    	</div>
    </div>
	<div class="easyui-menu" id="oa-contextMenu-oacustomerPage">
		<div id="oa-addRow-oacustomerPage" data-options="iconCls:'button-add'">添加</div>
		<div id="oa-editRow-oacustomerPage" data-options="iconCls:'button-edit'">编辑</div>
		<div id="oa-removeRow-oacustomerPage" data-options="iconCls:'button-delete'">删除</div>
	</div>
    <div id="oa-dialog-oacustomerPage"></div>
    <script type="text/javascript">
    
	var customerWidgetWidth = 130;
	var customerWidgetWidth2 = 130;
    
    var minLength = 10;	// 品牌明细最小行数
    
    var customer_url = '';
    var customer_type = '${param.type }';
    var customer_id = '${param.id }';
    var customer_step = '${param.step }';
    var customer_from = '${param.from }';

	var cid = '';

    // 针对于草稿页面
    if(customer_type == 'add' && customer_id != null && customer_id != '') {
    	customer_type = 'edit';
    }

    if(customer_type == 'add') {
    	customer_url = 'oa/oacustomerAddPage.do';
    } else if(customer_type == 'view' || customer_step == '99') {
    	customer_url = 'oa/oacustomerViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }&billtype=${param.billtype }';
    } else if(customer_type == 'handle') {
    	
    	customer_url = 'oa/oacustomerHandlePage.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
    	
    	if(customer_step == '01') {
    	
    		customer_url = 'oa/oacustomerHandlePage1.do?id=${param.id }&step=${param.step }&type=handle&billtype=${param.billtype }&processid=${param.processid }';
    	}
    	
    } else if(customer_type == 'print') {
        customer_url = 'oa/oacustomerPrintPage.do?id=${param.id }&step=${param.step }&type=print&processid=${param.processid }';
        window.location.href = customer_url;
    }
    
    $(function(){

		$('#oa-addRow-oacustomerPage').click(addCustomerBrand);
		$('#oa-editRow-oacustomerPage').click(editCustomerBrand);
		$('#oa-removeRow-oacustomerPage').click(removeCustomerBrand);

    	$("#oa-panel-oacustomerPage").panel({
			href: customer_url,
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

                // 上级客户
                $pcustomerid.widget({
                    referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                    singleSelect: true,
                    width: customerWidgetWidth,
                    required: false
                });

                // 销售部门
                $salesdeptid.widget({
                    referwid: 'RL_T_BASE_DEPARTMENT_SELLER',
                    singleSelect: true,
                    width: customerWidgetWidth2,
                    required: false
                });

				//销售区域参照窗口
				$oa_salesarea.widget({
					name: 't_oa_customer',
					col: 'salesarea',
					singleSelect:true,
					width: customerWidgetWidth2,
					required:true,
					onlyLeafCheck:true
				});

				// 人员档案
				$oa_payeeid.widget({
					name: 't_oa_customer',
					col: 'payeeid',
					singleSelect: true,
					width: customerWidgetWidth2,
					onlyLeafCheck: true
				});

				// 客户分类
				$oa_customersort.widget({
					name: 't_oa_customer',
					col: 'customersort',
					singleSelect: true,
					width: customerWidgetWidth2,
					required:true,
					onlyLeafCheck: true
				});

				// 客户业务员参照窗口
				$oa_salesuserid.widget({
					name:'t_oa_customer',
					col:'salesuserid',
					singleSelect:true,
					width: customerWidgetWidth,
					required:true,
					onlyLeafCheck:false
				});

				// 销售内勤参照窗口
				$oa_indoorstaff.widget({ 
					name:'t_oa_customer',
					col:'indoorstaff',
					singleSelect:true,
					width: customerWidgetWidth,
					required:true,
					onlyLeafCheck:false
				});

				// 结算方式参照窗口
				$oa_settletype.widget({ //结算方式参照窗口
					name:'t_oa_customer',
					col:'settletype',
					singleSelect:true,
					required:true,
					width: customerWidgetWidth,
					onlyLeafCheck:false,
					onSelect:function(data){
						if(data.type == '1'){//月结

							$oa_settleday.combobox({
								required:true
							});

                            // BUG:4042
                            $iscash.val('0');
                            $iscash.change();
						} else {

							$oa_settleday.combobox({
								required:false
							});

                            // BUG:4042
                            $iscash.val('1');
                            $iscash.change();
						}
					}
				});

                // 初始化时，结算方式为月结时，结算日必填
                (function() {

                    var settletype = ($oa_settletype.widget('getObject') || {}).type || '';

                    if(settletype == '1'){//月结

                        $oa_settleday.combobox({
                            required:true
                        });

                        // BUG:4042
                        $iscash.val('0');
                        $iscash.change();
                    } else if(settletype == '0') {

                        $oa_settleday.combobox({
                            required:false
                        });

                        // BUG:4042
                        $iscash.val('1');
                        $iscash.change();
                    }

                })();
			
				$oa_workAllot.unbind().click(function(){
					
					var pastr = $('#oa-allotpersonids-oacustomerHandlePage').val();
    				var dastr = $('#oa-allotcompanies-oacustomerHandlePage').val();
					
					$('#oa-dialog-oacustomerPage').dialog({  
						title: '分配品牌业务员',  
						width: 400,
						height: 280,
						closed: false,
						cache: false,
						href: 'oa/oaAllotPSNCustomerPage.do?id=${param.processid }',
						modal: true,
						buttons:[
							{
								text: '关闭',
								iconCls: 'button-close',
								plain: true,
								handler:function(){
									$('#oa-dialog-oacustomerPage').dialog('close');
								}
							}
							<c:if test="${param.type == 'handle' and param.step != '99' }">
							,{  
								text:'确定',  
								iconCls:'button-save',
								plain:true,
								handler:function(){

									var perids = getPersonidsValue();
									var delperids = getDelPersonidsValue();
									if("" == perids && "" == delperids){
										$.messager.alert("提醒", "请选择要分配的品牌业务员!");
										return false;
									}
									getCompanyValue();
									allotCustomerToPsn_form_submit();
									$("#oa-customer-oaAllotPSNCustomerPage").submit();
								}  
							}
							</c:if>
						]
					});
					
				});
			
				var customerBrandColListJSON = $oa_datagrid.createGridColumnLoad({
					frozenCol : [[{field:'id', title: '编号', hidden: true},
								  {field:'brandid', title: '品牌编号', width: 110},
								  {field:'brandname', title: '品牌名称', width: 205}
								]],
					commonCol : [[ {field: 'barcodenum', title: '品牌商品数', width:100, align: 'right',
										formatter: function(value,row,index){
						        			return formatterNum(value);
							        	}
							        },
								   {field: 'realnum', title: '实际进场数', width: 100, align: 'right',
								   		formatter: function(value,row,index){
						        			return value == '' ? '' :formatterNum(value);
							        	}
							        },
								   {field: 'displaynum', title: '阵列组数', width: 100, align: 'right', 
								   		formatter: function(value,row,index){
						        			return value == '' ? '' : formatterNum(value);
							        	}
							        },
								   {field: 'cost', title: '分摊费用', width: 100, align: 'right', 
								   		formatter:function(value,row,index){
						        			return value == '' ? '' : formatterMoney(value);
							        	}
							        }
								]]
				});
		
				$oa_datagrid.datagrid({ 
					//判断是否开启表头菜单
					//authority: customerBrandColListJSON,
					frozenColumns: customerBrandColListJSON.frozen,
					columns: customerBrandColListJSON.common,
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					striped:true,
					singleSelect: true,
					<c:if test="${param.step ne '01'}">
						url: 'oa/selectCustomerBrandList.do?billid=${param.id }',
						onLoadSuccess: function(data) {
			
							for(var i = data.total; i < minLength; i++) {
								$oa_datagrid.datagrid('appendRow', {});
							}
						},
					</c:if>
					onDblClickRow: function(rowIndex, rowData) {
						<c:if test="${param.type == 'view' or param.step eq '99' }">
							return false;
						</c:if>
						opeCustomerBrand();
					},
					onRowContextMenu: function(e, rowIndex, rowData){
						e.preventDefault();
						<c:if test="${param.type == 'view' or param.step eq '99' }">
							return false;
						</c:if>
						$wareList.datagrid('selectRow', rowIndex);
						var selectedRow = $wareList.datagrid('getSelected');
						
						$("#oa-contextMenu-oacustomerPage").menu('show', {  
							left:e.pageX,  
							top:e.pageY  
						});
						
						// 该行内容为空时，不能编辑
						if(selectedRow.brandid == undefined) {
							$("#oa-contextMenu-oacustomerPage").menu('disableItem', editItem);
						} else {
							$("#oa-contextMenu-oacustomerPage").menu('enableItem', editItem);
						}
					}
				});

				<c:if test="${param.step == '01'}">
					// $oa_datagrid.datagrid("loadData", {'total': 10, 'rows': [{},{},{},{},{},{},{},{},{},{} ]})
				</c:if>
		
				$islongterm.change(function(){
				
					var v = $(this).val();
                    $iscash.children().removeAttr('selected');
					if(v == '1') {
					
						$iscash.children('option[value=0]').attr('selected', 'selected');
					} else {

                        $iscash.children('option[value=1]').attr('selected', 'selected');
                    }
				});
				
				$iscash.change(function(){
				
					var v = $(this).val();
                    $islongterm.children().removeAttr('selected');
					if(v == '1') {
					
						$islongterm.children('option[value=0]').attr('selected', 'selected');
					} else {

                        $islongterm.children('option[value=1]').attr('selected', 'selected');
                    }
				});
			}
			
		});

		if(customer_from == 'work') {

			$('#oa-buttons-oacustomerPage').hide();
		}
    });
      	
    function addCustomerBrand(){

		$('<div id="oa-dialog-oacustomerBrandAddPage-content"></div>').appendTo('#oa-dialog-oacustomerPage');
		$("#oa-dialog-oacustomerBrandAddPage-content").dialog({ //弹出新添加窗口
			title:'添加进场品牌(按ESC退出)',
			maximizable:true,
			width:600,
			height:200,
			closed:false,
			modal:true,
			cache:false,
			resizable:true,
			href:'oa/oacustomerBrandAddPage.do',
			onClose:function(){
				$('#oa-dialog-oacustomerBrandAddPage-content').dialog("destroy");
			},
			onLoad:function(){
				$("#oa-name-oacustomerBrandAddPage").focus();
			}
		});
    }

	// 编辑品牌明细
	function editCustomerBrand(){

		var row = $line.datagrid('getSelected');

		if(row.brandid == undefined) {
			return false;
		}

		var dialogHref = 'oa/oacustomerBrandEditPage.do?brandid' + row.brandid 
							+ '&brandname=' + row.brandname 
							+ '&barcodenum=' + row.barcodenum
							+ '&realnum=' + row.realnum
							+ '&displaynum=' + row.displaynum
							+ '&cost=' + row.cost

		$('<div id="oa-dialog-oacustomerBrandEditPage-content"></div>').appendTo('#oa-dialog-oacustomerPage');
		$("#oa-dialog-oacustomerBrandEditPage-content").dialog({ //弹出新添加窗口
			title:'修改进场品牌(按ESC退出)',
			maximizable:true,
			width:600,
			height:200,
			closed:false,
			modal:true,
			cache:false,
			resizable:true,
			href: dialogHref,
			onClose:function(){
				$('#oa-dialog-oacustomerBrandEditPage-content').dialog("destroy");
			},
			onLoad:function(){

				$('#oa-brandid-oacustomerBrandEditPage').val(row.brandid);
				$('#oa-barcodenum-oacustomerBrandEditPage').val(row.barcodenum);
				$('#oa-brandname-oacustomerBrandEditPage').val(row.brandname);
				$('#oa-name-oacustomerBrandEditPage').val(row.brandname);
				$('#oa-realnum-oacustomerBrandEditPage').val(row.realnum);
				$('#oa-displaynum-oacustomerBrandEditPage').val(row.displaynum);
				$('#oa-cost-oacustomerBrandEditPage').val(row.cost);

				$('#oa-name-oacustomerBrandEditPage').widget('setValue', row.brandid);
				$("#oa-idtip-oacustomerBrandEditPage").removeClass("img-loading").html("商品编码："+ row.brandid + "&nbsp;&nbsp;&nbsp;品牌商品数：" + row.barcodenum);
				$('#oa-realnum-oacustomerBrandEditPage').focus();
			}
		});
	}
	
	// 删除品牌明细
	function removeCustomerBrand(){

		var brand = $line.datagrid('getSelected');

		if(brand.brandid == undefined) {
			return false;
		}

		var rows = $line.datagrid('getRows');
		var rowIndex = rows.length - 1;
		for(var i=0; i<rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.brandid == brand.brandid) {

				$line.datagrid('deleteRow', i);
				break;
			}
		}
		
		var recordCnt = $line.datagrid('getRows').length;
		
		for(var i = recordCnt; i < minLength; i++) {
			$line.datagrid('appendRow', {});
		}
	}
	
	// 添加/编辑品牌进场明细
	function opeCustomerBrand(){
		
		var brand = $line.datagrid('getSelected');
	
		if(brand.brandid == undefined) {
			addCustomerBrand();
			return false;
		} else {
			editCustomerBrand();
		}
		
	}

	//添加新数据确定后操作
	function addBrand(go){
	
		var flag = $("#oa-form-oacustomerBrandAddPage").form('validate');
		if(!flag){
			return false;
		}
				
		var form = $("#oa-form-oacustomerBrandAddPage").serializeJSON();

		var rows = $line.datagrid('getRows');
		var rowIndex = -1;
		for(var i = 0; i < rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.brandid == undefined){
				rowIndex = i;
				break;
			} else if(rowJson.brandid == form.brandid) {
				rowIndex = i;
				break;
			}
		}
		
		if(rowIndex == -1){
			rowIndex = rows.length;
			$line.datagrid('appendRow',{}); //如果是最后一行则添加一新行
		}
		$line.datagrid('updateRow',{index:rowIndex, row: {id: '', brandid: form.brandid, brandname: form.brandname, barcodenum: form.barcodenum, realnum: form.realnum, displaynum: form.displaynum, cost: form.cost}}); //将数据更新到列表中
		if(go){ //go为true确定并继续添加一条
			$("#oa-form-oacustomerBrandAddPage").form("clear");
		}
		else{ //否则直接关闭
			$("#oa-dialog-oacustomerBrandAddPage-content").dialog('close', true);
		}
	}
	
	function editBrand(go){
	
		var flag = $("#oa-form-oacustomerBrandEditPage").form('validate');
		if(!flag){
			return false;
		}
				
		var form = $("#oa-form-oacustomerBrandEditPage").serializeJSON();

		var rows = $line.datagrid('getRows');
		var rowIndex = rows.length - 1;
		for(var i=0; i<rows.length; i++){
			var rowJson = rows[i];
			if(rowJson.brandid == undefined){
				rowIndex = i;
				break;
			} else if(rowJson.brandid == form.brandid) {
				rowIndex = i;
				break;
			}
		}
		if(rowIndex == rows.length - 1){
			$line.datagrid('appendRow',{}); //如果是最后一行则添加一新行
		}
		$line.datagrid('updateRow',{index:rowIndex, row: {id: '', brandid: form.brandid, brandname: form.brandname, barcodenum: form.barcodenum, realnum: form.realnum, displaynum: form.displaynum, cost: form.cost}}); //将数据更新到列表中
		if(go){ //go为true确定并继续添加一条
			$("#oa-form-oacustomerBrandEditPage").form("clear");
		}
		else{ //否则直接关闭
			$("#oa-dialog-oacustomerBrandEditPage-content").dialog('close', true)
		}
	}
    
	$(function(){

		// 进场明细添加页面
		$(document).keydown(function(event){
			switch(event.keyCode){
				case 27: //Esc
					$("#oa-remark-oacustomerBrandAddPage").focus();
					$("#oa-dialog-oacustomerBrandAddPage-content").dialog('close');
				break;
			}
		});
		$(document).bind('keydown', 'ctrl+enter',function (){
			$("#oa-cost-oacustomerBrandAddPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oacustomerBrandAddPage").trigger("click");
				$("#oa-savegoon-oacustomerBrandEditPage").trigger("click");
			},100);
		});
		$(document).bind('keydown', '+',function (){
			$("#oa-cost-oacustomerBrandAddPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oacustomerBrandAddPage").trigger("click");
				$("#oa-savegoon-oacustomerBrandEditPage").trigger("click");
			},100);
			return false;
		});

		// 进场明细编辑页面
		$(document).keydown(function(event){
			switch(event.keyCode){
				case 27: //Esc
					$("#oa-dialog-oacustomerBrandEditPage-content").dialog('close');
				break;
			}
		});
		$(document).bind('keydown', 'ctrl+enter',function (){
			$("#oa-cost-oacustomerBrandEditPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oacustomerBrandEditPage").trigger("click");
				$("#oa-savegoon-oacustomerBrandEditPage").trigger("click");
			},100);
		});
		$(document).bind('keydown', '+',function (){
			$("#oa-cost-oacustomerBrandEditPage").blur();
			setTimeout(function(){
				$("#oa-savegoon-oacustomerBrandEditPage").trigger("click");
				$("#oa-savegoon-oacustomerBrandEditPage").trigger("click");
			},100);
			return false;
		});
	});

	var oacustomer_save_form_submit = function (callBack, args){ //保存表单方法
		
		$("#oa-form-oacustomerAddPage").form({
			onSubmit: function(){
				var flag = $(this).form('validate');
				if(flag==false){
					return false;
				}  
				loading("提交中..");
			},
			success:function(data){
				loaded();
				var json = $.parseJSON(data);
				if(json.lock == true){
					$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
					return false;
				}
				if(json.flag==true){
					
					if(callBack != undefined) {

						callBack(args.type, json.backid);
						return false;
					}
					$.messager.alert("提醒","保存成功");
					
					$("#oa-backid-oacustomerPage").val(json.backid);
					if(json.type == "add"){
						$("#oa-buttons-oacustomerPage").buttonWidget("addNewDataId", json.backid);
					}
					$("#oa-panel-oacustomerPage").panel('refresh', 'oa/oacustomerPage.do?type=view&id='+ json.backid);
				}
				else{
					$.messager.alert("提醒","保存失败");
				}
			}
		});
	};
	
	var oacustomer_edit_form_submit = function (callBack, args) { //保存表单方法
	
		$("#oa-form-oacustomerEditPage").form({
			onSubmit: function(){
				var flag = $(this).form('validate');
				if(flag==false){
					return false;
				}  
				loading("保存中..");
			},
			success:function(data){
				loaded();
				var json = $.parseJSON(data);
				if(json.lock == false){
					$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
					return false;
				}
				if(json.flag==true){
					
					if(callBack != undefined) {

						callBack(args.type, json.backid);
						return false;
					}
					$.messager.alert("提醒","保存成功");

					$("#oa-backid-oacustomerPage").val(json.backid);
					if(json.type == "edit"){
						$("#oa-buttons-oacustomerPage").buttonWidget("addNewDataId", json.backid);
					}
					$("#oa-panel-oacustomerPage").panel('refresh', 'oa/oacustomerPage.do?type=view&id='+ json.backid);
				}
				else{
					$.messager.alert("提醒","保存失败");
				}
			}
		});
	};

	function oacustomer_submit_workflow_form_submit() {
		$('#oa-form-oacustomerViewPage').form({
			onSubmit: function(){
				loading("正在提交到工作流中...");
			},
			success:function(data){
				loaded();
				var json = $.parseJSON(data);
				/*
				if(json.lock == true){
					$.messager.alert("提醒","其他用户正在修改该数据，无法修改");
					return false;
				}
				*/
				if(json.flag==true){
					$.messager.alert("提醒","该客户登录单已经提交到工作流中。");
					$("#oa-backid-oacustomerPage").val(json.backid);
					if(json.type == "add"){
						$("#oa-buttons-oacustomerPage").buttonWidget("addNewDataId", json.backid);
					}
					$("#oa-panel-oacustomerPage").panel('refresh', 'oa/oacustomerViewPage.do?id='+ json.backid);
				}
				else{
					$.messager.alert("提醒","提交失败，该客户登录单已经被提交了。");
				}
			}
		});
	}
	
	function oacustomer_handle_save_form_submit(callBack, args) {

		// 保存当前客户信息
		$("#oa-form-oacustomerHandlePage").form({
			onSubmit: function(){
	
				var json = $("#oa-datagrid-oacustomerHandlePage").datagrid('getRows');
				$("#oa-customerBrandJSON-oacustomerHandlePage").val(JSON.stringify(json));
	
				var flag = $("#oa-form-oacustomerHandlePage").form('validate');
				if(!flag) {
				
					return false;
				}
				
				$('select').removeAttr('disabled');
	
				loading("保存中...");
			},
			success:function(data){

    			loaded();
    			var json;
				try{
					json = $.parseJSON(data);
				}catch(e){
					$.messager.alert("提醒","保存失败");
    				return false;
				}
    			
    			// 保存失败
    			if(data.flag) {
    				$.messager.alert("提醒","保存失败");
    				return false;
    			}
    			
    			// 保存成功
				$.messager.alert("提醒","保存成功");
				$('#oa-id-oacustomerHandlePage').val(json.backid);
				if(callBack.data != undefined || callBack.data == null) {
				
					callBack.data(json.backid);
					return false;
				}
			}
		});
	
	}

	var customer_ajaxContent = function (param, url) { //同步ajax
		var ajax = $.ajax({
			type: 'post',
			cache: false,
			url: url,
			data: param,
			async: false
		});
		return ajax.responseText;
	}

	//验证长度且验证重复
    $.extend($.fn.validatebox.defaults.rules, {
        validLength:{
            validator:function(value){

                var reg=eval("/^[A-Za-z0-9]{2}$/");//正则表达式使用变量
                //if(reg.test(value) == true){
                // if(value == initValue){
                //     return true;
                // }

                var data=customer_ajaxContent({'customerid': value, 'id':'${param.id }'}, 'oa/checkCustomerUsed.do');
                var json = $.parseJSON(data);
                if(json.flag == true){
                    $.fn.validatebox.defaults.rules.validLength.message = '客户编码重复，请重新输入！';
                    return false;
                }else{
                    return true;
                }
            },
            message:''
        },
        validName: {
            validator:function(value){

                var data=customer_ajaxContent({'customername': value, 'id':'${param.id }'}, 'oa/checkCustomerNameUsed.do');
                var json = $.parseJSON(data);
                if(json.flag == true){
                    $.fn.validatebox.defaults.rules.validName.message = '客户名称重复，请重新输入！';
                    return false;
                }else{
                    return true;
                }
            },
            message:''
        }
    });

    function refreshPanel(url){
		$("#oa-panel-oacustomerPage").panel('refresh', url);
    }

	// 将DOM下所有控件Disable	
	function disableDom($dom) {

		if($dom.children().length > 0 && !($dom.is('select'))) {
			$dom.children().each(function(){
				disableDom($(this));
			});
		}  else if($dom.is('select')) {

			$dom.attr('disabled', 'disabled');
		} else {
			$dom.attr('disabled', 'disabled');
			$dom.widget('disable');
		}
	}
	
	// 将DOM下所有控件Enable
	function enableDom($dom) {
		if($dom.children().length > 0 && !($dom.is('select'))) {
			$dom.children().each(function(){
				$dom.removeAttr('disabled');
				$dom.widget('enable');
				enableDom($(this));
			});
		} else if($dom.is('select')) {
			$dom.removeAttr('disabled');
			$dom.widget('enable');
		} else {
			$dom.widget('enable');
		}
	}
	
	// 将DOM下所有控件readonly
	function readonlyDom($dom) {
		if($dom.children().length > 0 && !($dom.is('select'))) {
			$dom.children().each(function(){
				readonlyDom($(this));
			});
		} else if($dom.is('select')) {

			$dom.attr('disabled', 'disabled');
		} else {
			$dom.attr('readonly', 'readonly');
			$dom.widget('readonly', true);
		}
	}

	// 提交表单(工作页面提交表单接口方法)
	// call为newWorkAddPage.jsp中的save方法
	// args为save方法的参数，包含args.type
	function workFormSubmit(call, args) {

		//$.messager.confirm("提醒","确定保存该客户登录单吗？",function(r){
		//	if(r){
				if(customer_type == 'add') {
			
					var json = $("#oa-datagrid-oacustomerAddPage").datagrid('getRows');
								
					$("#oa-customerBrandJSON-oacustomerAddPage").val(JSON.stringify(json));
					oacustomer_save_form_submit(call, args);
					$("#oa-form-oacustomerAddPage").submit();
				} else if(customer_type == 'edit') {
			
					var json = $("#oa-datagrid-oacustomerEditPage").datagrid('getRows');
								
					$("#oa-customerBrandJSON-oacustomerEditPage").val(JSON.stringify(json));
					oacustomer_edit_form_submit(call, args);
					$("#oa-form-oacustomerEditPage").submit();
				} else {
			
					var json = $("#oa-datagrid-oacustomerHandlePage").datagrid('getRows');
								
					$("#oa-customerBrandJSON-oacustomerHandlePage").val(JSON.stringify(json));
					oacustomer_handle_save_form_submit(call, args);
					$("#oa-form-oacustomerHandlePage").submit();
				}
		//	}
		//});
		
	}

	</script>
  </body>
</html>