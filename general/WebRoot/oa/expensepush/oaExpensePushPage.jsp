<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用冲差支付申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaExpensePushPage">
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    <!--
    
    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';

    var ptype = '${param.ptype }';  // 冲差类型

	var goodsMap = {};
	var brandMap = {};
    var pushtypeMap = {};
	var deptMap = {};
	var expensesortMap = {};
    
    var minLength = 10;

    if(type == 'handle') {
    	
    	if(step == '99') {
	    	url = 'oa/expensepush/oaExpensePushViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    	} else {
    		url = 'oa/expensepush/oaExpensePushHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
    	}
    } else if(type == 'view') {

        url = 'oa/expensepush/oaExpensePushViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    } else if(type == 'print') {

        url = 'oa/expensepush/oaExpensePushPrintPage.do?id=${param.id }&type=view&processid=${param.processid }&noaccess=${param.noaccess }&buyprice=${param.buyprice }';
        window.location.href = url;
    }
    
    $(function() {
    
    	$('#oa-panel-oaExpensePushPage').panel({
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

				// 客户
				$customerid.customerWidget({
					<c:choose>
						<c:when test="${param.type eq 'handle' and param.step != '99' }">
							required: true,
						</c:when>
						<c:otherwise>
							required: false,
						</c:otherwise>
					</c:choose>
                    isopen: true,
					initValue: $customerid.val(),
					onSelect: function(data) {

						$salesdeptid.val(data.salesdeptid);
						refreshPrice();
					}
				});
				$customerid.customerWidget('setValue', $customerid.val());
				$customerid.customerWidget('setText', '${customer.name }');
				<c:if test="${param.type ne 'handle' or param.step eq '99' }">
					$customerid.customerWidget('readonly', true);
				</c:if>
				
				// 明细
				$detail.datagrid({
					columns: [[
						{field:'id', title: '编号', hidden: true},
						{field:'brandid', title: '折差品牌', width: 90,
							formatter: function(value,row,index) {

                                if(value == '合计') {

                                    return '合计';
                                }

								if(brandMap[value]) {
									return brandMap[value];
								}
								return row.brandname;
							},
							editor: {
								type: 'widget',
								options:{
									required: true,
									referwid: 'RL_T_BASE_GOODS_BRAND',
									width: 90,
                                    async: false,
									onSelect: function(data) {

										brandMap[data.id] = data.name;
										<c:if test="${param.noaccess ne 1}">
											var editor = $detail.datagrid('getEditor', {index: retExpensePushDetailEditIndex(), field: 'deptid'});
											<c:choose>
												<c:when test="${param.dept eq 'salesdept'}">
													$(editor.target).widget('setValue', $salesdeptid.val());
												</c:when>
												<c:otherwise>
													$(editor.target).widget('setValue', data.deptid);
												</c:otherwise>
											</c:choose>
										</c:if>

									},
									onClear: function() {

                                        var editor = $detail.datagrid('getEditor', {index: retExpensePushDetailEditIndex(), field: 'deptid'});
                                        $(editor.target).widget('clear');
                                    }
                                }
                            }
                        },
                        {field:'pushtype', title: '冲差类型', width: 105,
                            formatter: function(value,row,index) {

                                if(pushtypeMap[value]) {
                                    return pushtypeMap[value];
                                }
                                return row.pushtypename;
                            },
                            editor: {
                                type: 'widget',
                                options:{
                                    required: true,
                                    referwid: 'RL_T_PUSHTYPE_LIST',
                                    width: 100,
                                    async: false,
                                    onSelect: function(data) {

                                        pushtypeMap[data.id] = data.name;

                                    },
                                    onClear: function() {
									}
								}
							}
						},
						{field:'amount', title: '折让金额', width: 80, align: 'right',
							formatter: function(value,row,index) {
							
								return formatterMoney(value);
							},
							editor: {
								type: 'numberbox',
								options:{
									required: true,
									precision: 2,
									min: -999999999999999.99,
									max: 999999999999999.99
								}
							}
						},
						<c:choose>
							<c:when test="${param.noaccess eq 1}">
								{field:'expensesort', title: '费用科目', width: 100,
									formatter: function(value,row,index) {

										if(row.expensesortname) {
											expensesortMap[value] = row.expensesortname;
										}

										var name = value || '';
										if((expensesortMap[value] || '') != '') {

											name = expensesortMap[value];
										}

										if(name == '') {
											return value;
										}

										return name;
									},
									editor: {
										type: 'widget',
										options: {
											referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
											required: false,
                                            async: false,
											onlyLeafCheck: true,
											width: 100,
											onSelect: function(data) {

												expensesortMap[data.id] = data.name;											},
											onClear: function() {
											}
										}
									}
								},
								{
									field: 'startdate', title: '开始日期', width: 100,
									editor: {
										type: 'dateText'
									}
								},
								{
									field: 'enddate', title: '结束日期', width: 100,
									editor: {
										type: 'dateText'
									}
								},
							</c:when>
							<c:otherwise>
								{field:'deptid', title: '费用部门', width: 100,
									formatter: function(value,row,index) {

										if(!value) {
											return ;
										}

										if(deptMap[value]) {
											return deptMap[value];
										}
										deptMap[value] = row.deptname;
										return row.deptname;
									},
									editor: {
										type: 'widget',
										options: {
											referwid: 'RL_T_BASE_DEPARTMENT_SELLER',
											required: true,
                                            async: false,
											onlyLeafCheck: false,
											width: 100,
											onSelect: function(data) {

												deptMap[data.id] = data.name;
//												$('#widget_RL_T_BASE_DEPARTMENT_SELLER_text').remove();
//												$('<input type="hidden" id="widget_RL_T_BASE_DEPARTMENT_SELLER_text" value="' + data.name + '"/>').appendTo('body');
											},
											onClear: function() {

												$('#widget_RL_T_BASE_DEPARTMENT_SELLER_text').remove();
												var rows = $detail.datagrid('getRows');
												var row = rows[retExpensePushDetailEditIndex()];
												row.deptname = '';
											}
										}
									}
								},
								{field:'oaid', title: '已批OA号', width: 60, align: 'left',
									formatter: function(value, row, index){

										if((value || '') != '') {

											return '<a href="javascript:void(0);" onclick="viewDetail(' + value + ')">' + value + '</a>'
										}
									},
									editor: {
										type: 'numberbox',
										options:{
											<c:choose>
                                                <c:when test="${param.oarequired eq '0'}">
                                                    required: false,
                                                </c:when>
                                                <c:otherwise>
                                                    required: true,
                                                </c:otherwise>
											</c:choose>
                                            validType: ['validOa'],
                                            min: 0,
											max: 9999999999
										}
									}
								},
							</c:otherwise>
						</c:choose>
						{field: 'goodsid', title: '商品', width: 200,
							formatter: function(value,row,index) {

								if(!value) {
									return ;
								}

								if((goodsMap[value] || '') != '') {

									return '[' + value + ']' + goodsMap[value];
								}

								goodsMap[value] = row.goodsname;
								return '[' + value + ']' + row.goodsname;
							},
							editor: {
								type: 'goodswidget',
								options:{
									required: false,
									width: 200,
									onSelect: function(data) {

										goodsMap[data.id] = data.name;

										var index = retExpensePushDetailEditIndex();

										var editor1 = $detail.datagrid('getEditor', {index: index, field: 'unitnum'});
										var editor2 = $detail.datagrid('getEditor', {index: index, field: 'oldprice'});
										var editor3 = $detail.datagrid('getEditor', {index: index, field: 'newprice'});

										$(editor1.target).validatebox({required: true/*, disabled: false*/});
										$(editor2.target).numberbox({required: true, disabled: false});
										$(editor3.target).numberbox({required: true, disabled: false});
										<c:if test="${param.buyprice eq '1'}">
											var editor4 = $detail.datagrid('getEditor', {index: index, field: 'buyprice'});
											$(editor4.target).html(data.newbuyprice);
										</c:if>

										// 更新原价
										$.ajax({
											type: 'post',
											url: 'oa/common/getGoodsPrice.do',
											data: {customerid: $customerid.customerWidget('getValue'), goodsid: data.id, index: index},
											dataType: 'json',
											async: false,
											success: function(json) {

												$(editor2.target).numberbox('setValue', json.goods.taxprice);
											}
										});
									},
									onClear: function(data) {

										var index = retExpensePushDetailEditIndex();

										var editor1 = $detail.datagrid('getEditor', {index: index, field: 'unitnum'});
										var editor2 = $detail.datagrid('getEditor', {index: index, field: 'oldprice'});
										var editor3 = $detail.datagrid('getEditor', {index: index, field: 'newprice'});

//										$(editor1.target).numberbox('clear');
										$(editor1.target).val('');
										$(editor2.target).numberbox('clear');
										$(editor3.target).numberbox('clear');

										$(editor1.target).validatebox({required: false/*, disabled: true*/});
										$(editor2.target).numberbox({required: false, disabled: true});
										$(editor3.target).numberbox({required: false, disabled: true});
									}
								}
							}
						},
						{field: 'unitnum', title: '数量', width: 60, align: 'right',
							formatter: function(value,row,index) {

								return formatterMoney(value, <%=BillGoodsNumDecimalLenUtils.decimalLen %>);
							},
							editor: {
								type: 'numberbox',
								options:{
                                    required: false,
                                    precision: <%=BillGoodsNumDecimalLenUtils.decimalLen %>,
                                    min: 0,
                                    max: 999999999999999,
                                    onChange: function (nv, ov) {
                                    }
								}
							}
						},
						{field: 'oldprice', title: '原价', width: 60, align: 'right',
							formatter: function(value,row,index) {

								return formatterMoney(value);
							},
							editor: {
								type: 'numberbox',
								options:{
									required: false,
									precision: 2,
									disabled: true,
									min: 0,
									max: 999999999999999.99,
                                    onChange: function (nv, ov) {
                                    }
								}
							}
						},
						{field: 'newprice', title: '现价', width: 60, align: 'right',
							formatter: function(value,row,index) {

								return formatterMoney(value);
							},
							editor: {
								type: 'numberbox',
								options:{
									required: false,
									precision: 2,
									disabled: true,
									min: 0,
									max: 999999999999999.99,
                                    onChange: function (nv, ov) {
                                    }
								}
							}
						},
						<c:if test="${param.buyprice eq '1'}">
							{field: 'buyprice', title: '采购价', width: 70, align: 'right',
								formatter: function(value,row,index) {

									return formatterMoney(value);
								},
								editor: {
									type: 'span'
								}
							},
						</c:if>
						{field:'remark', title: '备注', width: 145,
							editor: {
								type: 'validatebox',
								options:{
									required: false,
									validType: 'maxByteLength[130]'
								}
							}
						}
					]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					singleSelect: true,
					<c:if test="${param.step eq '01' }">
						toolbar:[
								{text: '添加', iconCls: 'button-add',    handler: addExpensePushDetail},
								{text: '修改', iconCls: 'button-edit',   handler: editExpensePushDetail},
								{text: '确定', iconCls: 'button-save',   handler: endEditExpensePushDetail},
								{text: '删除', iconCls: 'button-delete', handler: removeExpensePushDetail}
								],
						onDblClickRow: editExpensePushDetail,
						onClickRow: endEditExpensePushDetail,
					</c:if>
				    url: 'oa/expensepush/selectOaExpensePushDetailList.do?billid=${param.id }',
					onLoadSuccess: function(data) {
			
						for(var i = data.total; i < minLength; i++) {
							$detail.datagrid('appendRow', {});
						}

                        countTotalAmount();
					}
				}); // datagrid close

                if(ptype) {
                    var ptypeArr = ptype.split('');
                    var currentPtypeVal = $ptype.val();

                    var newPtypeArray = new Array();
                    for(var i in ptypeArr) {
                        newPtypeArray[ptypeArr[i]] = ptypeArr[i];
                    }

                    $ptype.html('');
                    if(newPtypeArray[1]) {
                        $ptype.append('<option value="1">冲差</option>');
                    }
                    if(newPtypeArray[2]) {
                        $ptype.append('<option value="2">货补</option>');
                    }

//                    // 还原原先的值
//                    $ptype.val(currentPtypeVal);
                }

                // 对所有的select项目根据其data属性进行赋值
                $('select[data]').each(function() {

                    var data = $(this).attr('data');
                    $(this).removeAttr('data');
                    $(this).children().removeAttr('selected');

                    $(this).children().each(function() {

                        if($(this).attr('value') == data) {
                            $(this).attr('selected', 'selected');
                        }
                    });
                });

                // panel onLoad close
			}
		});
    });
    
    /**
     * 添加明细
     */
    function addExpensePushDetail() {
        
    	var editIndex = retExpensePushDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行添加！");
    		return false;
    	}
    	
	    // 搜索未编辑的行
	    var rows = $detail.datagrid('getRows');
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
	    
	    	beginEditExpensePushDetailRow(index);
	    	return true;
	    }
	    
	    // 明细中所有的行均已被编辑，添加空行
	    index = rows.length;
	    $detail.datagrid('appendRow', {});
	    beginEditExpensePushDetailRow(index);

	    return true;
    }
    
    /**
     * 编辑明细
     */
    function editExpensePushDetail() {
    
    	var editIndex = retExpensePushDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改！");
    		return false;
    	}
    	
    	var selectedRow = $detail.datagrid('getSelected');
    	var index = $detail.datagrid('getRowIndex', selectedRow);
    	
    	beginEditExpensePushDetailRow(index);
    	return true;
    }
    
    /**
     * 删除明细
     */
    function removeExpensePushDetail() {
    
    	var editIndex = retExpensePushDetailEditIndex();
    	if(editIndex >= 0) {
    		$.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除。");
    		return false;
    	}
    	
    	var selectedRow = $detail.datagrid('getSelected');
    	var index = $detail.datagrid('getRowIndex', selectedRow);
    	
    	$detail.datagrid('deleteRow', index);
        countTotalAmount();
    	return true;
    }
    
    /**
     * 结束编辑
     */
    function endEditExpensePushDetail() {
        
    	var editIndex = retExpensePushDetailEditIndex();
    	if(editIndex < 0) {
    		return true;
    	}
    	
    	$detail.datagrid('endEdit', editIndex);
        countTotalAmount();
//		$customerid.customerWidget('enable');
    	return true;
    }
    
    /**
     * 开始编辑
     */
    function beginEditExpensePushDetailRow(rowIndex) {

		if(rowIndex < 0){
			$.messager.alert("提醒", "请选择记录！");
			return false;
		}

		var row = $detail.datagrid('getRows')[rowIndex];
	
		$detail.datagrid('beginEdit', rowIndex);
		$detail.datagrid('scrollTo', rowIndex);

//		$customerid.customerWidget('disable');

		var goodsEditor = $detail.datagrid('getEditor', {index: rowIndex,field: 'goodsid'});
		$(goodsEditor.target).goodsWidget('setText', goodsMap[row.goodsid] || row.goodsname);

        if(row.goodsid) {

//            var editor1 = $detail.datagrid('getEditor', {index: rowIndex,field: 'unitnum'});
//            var editor2 = $detail.datagrid('getEditor', {index: rowIndex,field: 'oldprice'});
            var editor3 = $detail.datagrid('getEditor', {index: rowIndex,field: 'newprice'});

//            $(editor1.target).numberbox('enable');
//            $(editor2.target).numberbox('enable');
            $(editor3.target).numberbox('enable');

            setTimeout(function () {

                var editor1 = $detail.datagrid('getEditor', {index: rowIndex,field: 'unitnum'});
                var editor2 = $detail.datagrid('getEditor', {index: rowIndex,field: 'oldprice'});
                var editor3 = $detail.datagrid('getEditor', {index: rowIndex,field: 'newprice'});

                var options1 = $(editor1.target).numberbox('options');
                var options2 = $(editor2.target).numberbox('options');
                var options3 = $(editor3.target).numberbox('options');

                var functionOption = {onChange: function () {
                    refreshAmount();
                }};

                jQuery.extend(options1, functionOption);
                jQuery.extend(options2, functionOption);
                jQuery.extend(options3, functionOption);

                $(editor1.target).numberbox(options1);
                $(editor2.target).numberbox(options2);
                $(editor3.target).numberbox(options3);
            }, 200);
        }

		return true;
    }
    
    /**
     * 当前编辑行index
     */
    function retExpensePushDetailEditIndex() {
    
    	var rows = $detail.datagrid('getRows');
    	
    	for(var i = 0; i < rows.length; i++) {
    		var editors = $detail.datagrid('getEditors', i);
    		if(editors.length > 0) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * 当前编辑行index
     */
    function checkOA(oaid) {
    	
    	if(oaid == undefined || oaid == null || oaid == '') {
    	
    		return false;
    	}

    	var $current = $(this);
    	    	
    	$.ajax({
    		type: 'post',
    		dataType: 'json',
    		url: 'act/selectProcess.do',
            async: false,
    		data: {id: oaid},
    		success: function(json) {

    			if(json.process == null) {
    			
    				$.messager.alert('警告', '该OA编号对应的通路单不存在！');
    				$current.numberbox('clear');
    				return false;
    			}

		    	$.ajax({
		    		type: 'post',
		    		dataType: 'json',
		    		url: 'oa/selectAccessInfo.do',
		    		data: {id: json.process.businessid},
                    async: false,
		    		success: function(json) {
		    			
		    			if(json.access == null) {
		    			
		    				$.messager.alert('警告', '该OA编号对应的通路单不存在！');
		    				$current.numberbox('clear');
		    				return false;
		    			}
		    		},
		    		error: function() {}
		    		
		    	});
    		},
    		error: function() {}
    		
    	});
    }

    /**
    * 合计
     */
    function countTotalAmount(){

        var amount = 0;

        var rows =  $detail.datagrid('getRows');

        for(var i in rows) {

            var row = rows[i];
            amount = parseFloat(amount) + parseFloat(row.amount || 0);
        }

        var footerrows = [{brandid: '合计', brandname: '合计', amount: amount}];
//        if(null != footerobject){
//            footerrows.push(footerobject);
//        }
        $detail.datagrid("reloadFooter", footerrows);
    }

    /**
    * 查看工作
    * @param processid
     */
    function viewDetail(processid){
        top.addOrUpdateTab("act/workViewPage.do?processid=" + processid, "工作查看");
    }


    // 提交表单
    function oaexpensepush_handle_save_form_submit(callBack, args) {

		var index = retExpensePushDetailEditIndex();
		if(index >= 0) {

			$detail.datagrid('endEdit', index);
		}
		index = retExpensePushDetailEditIndex();
    	if(index >= 0) {
    	
    		$.messager.alert('警告', '明细正在编辑中，无法保存！');
			return true;
    	}
    
    	$form.form({
    		onSubmit: function(param) {
    		
    			var flag = $form.form('validate');
    			if(!flag) {
    			
    				return false;
    			}
    			
    			$detaillist.val(JSON.stringify($detail.datagrid('getRows')));
    			
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

	/**
	* 刷新原价
	 */
	function refreshPrice(e) {

		var customerid = $customerid.customerWidget('getValue');
		var rows = $detail.datagrid('getRows');
		for(var i in rows) {

			if((rows[i].goodsid || '') == '') {
				continue;
			}

			var row = rows[i];
			var goodsid = rows[i].goodsid;

			$.ajax({
				type: 'post',
				url: 'oa/common/getGoodsPrice.do',
				data: {customerid: customerid, goodsid: goodsid, index: i},
				dataType: 'json',
				async: false,
				success: function(json) {

					var idx = parseInt(json.index);
					row.oldprice = json.goods.taxprice;
					$detail.datagrid('updateRow', {index: idx, row: row});
				}
			});
		}
	}

    /**
     * 刷新金额
     * @returns {boolean}
     */
	function refreshAmount() {

	    var index = retExpensePushDetailEditIndex();
        var editor1 = $detail.datagrid('getEditor', {index: index, field: 'unitnum'});
        var editor2 = $detail.datagrid('getEditor', {index: index, field: 'oldprice'});
        var editor3 = $detail.datagrid('getEditor', {index: index, field: 'newprice'});
        var editor4 = $detail.datagrid('getEditor', {index: index, field: 'amount'});

        if($(editor3.target).numberbox('getValue') == '') {
            return false;
        }

        var unitnum = parseFloat($(editor1.target).val()) || 0;
        var oldprice = $(editor2.target).numberbox('getValue') || 0;
        var newprice = $(editor3.target).numberbox('getValue') || 0;

        var amount = unitnum * (parseFloat(oldprice) - parseFloat(newprice));
        amount = formatterMoney(amount);

        var oldamount = $(editor4.target).numberbox('getValue');
        $(editor4.target).numberbox('setValue', amount);

        return true;
    }

    $.extend($.fn.validatebox.defaults.rules, {
        validOa: {
                validator: function (value, param) {

                    <c:if test="${param.oacheck eq 0}">
                        return true;
                    </c:if>

                    var ajaxText = (function (oaid) {
                        var ajax = $.ajax({
                            type: 'post',
                            dataType: 'json',
                            url: 'act/selectProcess.do',
                            async: false,
                            data: {id: oaid},
                            success: function (json) {


                            }
                        });
                        return ajax.responseText;
                    })(value);

                    var json = JSON.parse(ajaxText);

                    if(json.process
                        && json.definition
                        && /(oa(\/hd){0,1}\/oaAccessPage\.do)/.test(json.definition.businessurl)) {
                        return true;
                    }

                    return false;

                },
                message:'请输入正确的通路单OA号！'
            }
        }
    );

        // 提交表单(工作页面提交表单接口方法)
	function workFormSubmit(call, args) {

        oaexpensepush_handle_save_form_submit(call, args);
	}

	-->
	</script>
  </body>
</html>