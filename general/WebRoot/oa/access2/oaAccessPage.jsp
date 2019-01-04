<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>特价、通路费用申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <style type="text/css">
        .len20 {
            width: 20px;
        }
        .len30 {
            width: 30px;
        }
        .len40 {
            width: 40px;
        }
        .len50 {
            width: 50px;
        }
        .len60 {
            width: 60px;
        }
        .len70 {
            width: 70px;
        }
        .len80 {
            width: 80px;
        }
        .len85 {
            width: 85px;
        }
        .len90 {
            width: 90px;
        }
        .len108 {
            width: 108px;
        }
        .len110 {
            width: 110px;
        }
        .len130 {
            width: 130px;
        }
        .len140 {
            width: 140px;
        }
        .len170 {
            width: 173px;
        }
        .len175 {
            width: 175px;
        }
        .len200 {
            width: 200px;
        }
        .len230 {
            width: 230px;
        }
        select {
            margin-left: 0px;
        }
        input[type=text] {
            margin-left: 0px;
        }
        .len116 {
            width: 117px;
        }
		.len150 {
			width: 150px;
		}
		.len173 {
			width: 173px;
		}
        .len180 {
            width: 180px;
        }
        .len190 {
            width: 195px;
        }
		.len192 {
			width: 192px;
		}
        .len212 {
            width: 212px;
        }
        .len220 {
            width: 220px;
        }
    </style>
  	<input type="hidden" id="oa-backid-oaAccessPage" value="${param.id }" />
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaAccessPage">
    		</div>
    	</div>
    </div>
	<div class="easyui-menu" id="oa-contextMenu-oaAccessPage">
		<div id="oa-addRow-oaAccessPage" data-options="iconCls:'icon-add'">添加</div>
		<div id="oa-editRow-oaAccessPage" data-options="iconCls:'icon-add'">编辑</div>
		<div id="oa-removeRow-oaAccessPage" data-options="iconCls:'icon-remove'">删除</div>
	</div>
	<input type="hidden" id="oa-oldcustomerid-oaAccessPage"/>
    <div id="oa-dialog-oaAccessPage"></div>
    <script type="text/javascript">
    <!--
    
    /**
     * 关于step的说明：
     * 01：第一部分
     * 02：第二部分
     * 03：第二、三部分
     * 04：无
     */

    var type = '${param.type }';
    var url = '';
    var id = '${param.id }';
    var step = '${param.step }';
    
    if(type == 'add') {
    	url = 'oa/hd/oaAccessAddPage.do';
    } else if(type == 'view') {
    	url = 'oa/hd/oaAccessViewPage.do?id=${param.id }&oaid=${param.processid }&processid=${param.processid }';
    } else if(type == 'handle') {

    	url = 'oa/hd/oaAccessHandlePage3.do?id=${param.id }&oaid=${param.processid }&processid=${param.processid }';
    	if(step == '01') {
    		url = 'oa/hd/oaAccessHandlePage1.do?id=${param.id }&oaid=${param.processid }&processid=${param.processid }';
    	} else if(step == '02') {
    		url = 'oa/hd/oaAccessHandlePage2.do?id=${param.id }&oaid=${param.processid }&processid=${param.processid }';
    	} else if(step == '03') {
    		url = 'oa/hd/oaAccessHandlePage3.do?id=${param.id }&oaid=${param.processid }&processid=${param.processid }';
    	} else if(step == '04') {
    		url = 'oa/hd/oaAccessHandlePage3.do?id=${param.id }&oaid=${param.processid }&processid=${param.processid }';
    	}
    } else if(type == 'print') {
        url = 'oa/hd/oaAccessPrintPage.do?id=${param.id }&type=print&processid=${param.processid }&billtype=${param.billtype }';
        window.location.href = url;
    }
    
    var priceEditIndex = -1;	// 商品价格Datagrid编辑行号
    var amountEditIndex = -1;	// 商品数量Datagrid编辑行号
    var discountEditIndex = -1;	// 票价折扣Datagrid编辑行号
    
    var pricesize = 5;
    var amountsize = 5;
    
    var tempname = '';
    
    var selectedSupplierid = '';
    var selectDisables = new Array();
    
    var params = [{field: 'defaultsupplier', op: 'equal', value: '' }];
    
    $(function(){
    
    	$('#oa-panel-oaAccessPage').panel({
			href: url,
			cache: false,
			maximized: true,
			border: false,
			width: 840,
			onLoad: function() {

				var attachflag = false;

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
					processid: '${param.processid }'});
				$comments.comments({businesskey: '${param.id }', processid: '${param.processid }', type: 'vertical', width: '120', agree: null});

				$goodsprice.datagrid({
					columns: [[{field:'id', title: '编号', hidden: true},
								  {
									field:'goodsid', 
									title: '商品编码', 
									width: 90,
									formatter: function(value, row, index) {
									
										if($('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').length == 0) {
											return value;
										} else {
											value = $('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').val();
											row.goodsid = $('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').val();
											return $('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').val();
										}
									},
									editor:{
										type:'text'
									}
								},
								{field:'goodsname', title: '商品名称', width: 214, editor: {type: 'validatebox', options:{required: /*true*/false}}},
                                {field:'buyprice', title: '进货价', <c:if test="${param.buyprice eq '0'}">hidden: true,</c:if> width: 60, formatter: function(value,row,index){ return formatterMoney(value); }, editor: {type: 'numberbox', options:{precision:2, onChange: function() {computeRate(retPriceEditIndex());}}}},
								{field:'factoryprice', title: '工厂让利', formatter: function(value,row,index){ /*if(value == null || value == '') {return '';}*/ return formatterMoney(value); }, width: 60, editor: {type: 'numberbox', options:{precision:2, onChange: function() {computeRate(retPriceEditIndex());computeMyprice(retPriceEditIndex());} }}},
								{field:'myprice', title: '自理', width: 60, formatter: function(value,row,index){ return formatterMoney(value); }, editor: {type: 'numberbox', options:{precision:2}}},
								{field:'oldprice', title: '原价', width: 60, formatter: function(value,row,index){ return formatterMoney(value); }, editor: {type: 'numberbox', options:{precision:2, onChange: function() {computeMyprice(retPriceEditIndex());}}}},
								{field:'newprice', title: '现价', width: 60, formatter: function(value,row,index){ return formatterMoney(value); }, editor: {type: 'numberbox', options:{precision:2, onChange: function(){computeMyprice(retPriceEditIndex()); computeRate(retPriceEditIndex());}}}},
								{field:'rate', title: '毛利率', width: 60, formatter: function(value,row,index){ if(value == '' || value == null) {return '';}return formatterMoney(value) + '%'; }, editor: {type: 'numberbox', options:{precision:2, suffix: '%'}}},
								{field:'senddetail', title: '门店出货', width: 90, editor: {type: 'validatebox', options:{validType: 'length[0,25]'}}}
							]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					//striped:true,
					singleSelect: true,
					onClickRow: endEditGoodsPrice,
				    url: 'oa/hd/selectOaAccessGoodsPriceList.do?billid=${param.id }',
					onLoadSuccess: function(data) {
			
						for(var i = data.total; i < pricesize; i++) {
							$goodsprice.datagrid('appendRow', {});
						}
					},
					<c:if test="${param.step eq '01' }">
			    	onDblClickRow: function(rowIndex, rowData) {beginEditPriceRow(rowIndex);},
					toolbar:[
							{text: '添加', iconCls: 'button-add', handler: addGoodsPrice},
							{text: '修改', iconCls: 'button-edit', handler: editGoodsPrice},
							{text: '确定', iconCls: 'button-save', handler: endEditGoodsPrice},
							{text: '删除', iconCls: 'button-delete', handler: removeGoodsPrice}
							]
					</c:if>
				});
				
				$goodsamount.datagrid({
					columns: [[{field:'id', title: '编号', hidden: true},
								  {field:'goodsid', title: '商品编码', width: 75},
								  {field:'goodsname', title: '商品名称', width: 248},
								  {field:'difprice', title: '单位差价', width: 60, formatter: function(value,row,index){ return formatterMoney(value, 2); }, editor: {type: 'numberbox', options:{precision:2, onChange: function(){computeAmount();computeDownAmount();}}}},
								  {field:'unitnum', title: '数量', width: 60, formatter: function(value,row,index){ return formatterMoney(value, <%=BillGoodsNumDecimalLenUtils.decimalLen %>); }, editor: {type: 'numberbox', options:{precision:<%=BillGoodsNumDecimalLenUtils.decimalLen %>, onChange: function(){computeAuxnum(); computeAmount(); /*computeTotalNum();*/}}}},
								  {field:'auxnum', title: '辅数量(整)', width: 65, formatter: function(value,row,index){ return formatterMoney(value, 0); }, editor: {type: 'numberbox', options:{precision:0, onChange: computeUnitnum}}},
								  {field:'auxremainder', title: '辅数量(余)', formatter: function(value,row,index){ return formatterMoney(value, <%=BillGoodsNumDecimalLenUtils.decimalLen %>); }, width: 65, editor: {type: 'numberbox', options:{precision:<%=BillGoodsNumDecimalLenUtils.decimalLen %>, onChange: computeUnitnum}}},
								  {field:'amount', title: '差价金额', width: 60, formatter: function(value,row,index){ return formatterMoney(value, 2); }, editor: {type: 'numberbox', options:{precision:2, onChange: function(){/*computeTotalAmount();*/ /*computeDownAmount();*/}}}},
								  {field:'erpnum', title: 'ERP数量', width: 60, formatter: function(value,row,index){ return formatterMoney(value, <%=BillGoodsNumDecimalLenUtils.decimalLen %>); }},
								  {field:'downamount', title: '降价金额', width: 60,editor: {type: 'span'/*, options:{precision:2, onChange: function(){computeDownAmount();}}*/},
								  		formatter: function(value,row,index){
								  			
								  			if(row.erpnum != undefined && row.erpnum != null && row.erpnum != ''
								  				&& row.difprice != undefined && row.difprice != null && row.difprice != '') {
								  			
								  				return formatterMoney(row.difprice * row.erpnum);
								  			}
								  			
								  			return '0.00';
								  		}
								  }
							]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					singleSelect: true,
					onClickRow: endEditGoodsAmount,
				    url: 'oa/hd/selectOaAccessGoodsAmountList.do?billid=${param.id }',
                    onLoadSuccess: function(data) {

                        // 补满5行
                        for(var i = data.total; i < amountsize; i++) {
                            $goodsamount.datagrid('appendRow', {});
                        }
                    },
				    <c:if test="${param.step eq '02' }">
					onDblClickRow: editGoodsAmount,
					toolbar:[{text: '修改', iconCls: 'button-edit', handler: editGoodsAmount},
							 {text: '确定', iconCls: 'button-save', handler: endEditGoodsAmount}
							]
					</c:if>
				});
			
				// 品牌折让				
				$branddiscount.datagrid({
					columns: [[{field:'id', title: '编号', hidden: true},
								  {field:'brandname', title: '品牌编号', width: 120, formatter: function(value,row,index){ return row.brandid; }, editor:{type: 'span'}},
								  {
								  	field:'brandid', 
								  	title: '品牌名称', 
								  	width: 220,
								  	formatter: function(value,row,index){
								  		
								  		var brandname = $('#editorwidget-text-' + value).val();
								  		
								  		if(brandname == undefined || brandname == null || brandname == '') {
								  			return row.brandname2;
								  		}
								  		return brandname;
								  	},
								  	editor: {
								  		type: 'widget',
								  		options:{
											referwid:'RL_T_BASE_GOODS_BRAND',
											required: false,
											singleSelect:true,
											//width:170,
											onSelect: function(data) {
												
												var brandid = data.id;
												var dei = retDiscountEditIndex();
												var editor1 = $branddiscount.datagrid('getEditor', {index:dei,field:'brandname'});

												editor1.target.html(brandid);
											}
										}
									}
											
								  },
								  {field:'amount', title: '折让金额', width: 120, editor: {type: 'numberbox', options:{required: false, precision:2}}},
								  {field:'remark', title: '备注', width: 290, editor: {type: 'validatebox', options:{required:false}}}
								]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					singleSelect: true,
					onClickRow: endEditDiscountRow,
				    url: 'oa/hd/selectOaAccessBrandDiscountList.do?billid=${param.id }',
					onLoadSuccess: function(data) {
			
						for(var i = data.total; i < amountsize; i++) {
							$branddiscount.datagrid('appendRow', {});
						}
					},
					<c:if test="${param.step eq '03'}">
					onDblClickRow: editBrandDiscount,
					toolbar:[
							{text: '添加', iconCls: 'button-add', handler: addBrandDiscount},
							{text: '修改', iconCls: 'button-edit', handler: editBrandDiscount},
							{text: '确定', iconCls: 'button-save', handler: endEditDiscountRow},
							{text: '删除', iconCls: 'button-delete', handler: removeDiscountRow}
							]
					</c:if>
				});

				// 结果分析
                /*
				$result.datagrid({
					columns: [[{field:'id', title: '编号', hidden: true},
								  {field:'brandid', title: '品牌编号', width: 100},
								  {field:'brandname', title: '品牌名称', width: 205},
								  {field:'expensesort', title: '项目', width: 80},
								  {field:'amount', title: '金额', width: 80},
								  {field:'salesamount', title: '销售金额', width: 80},
								  {field:'begindate', title: '执行开始日期', width: 95},
								  {field:'enddate', title: '执行结束日期', width: 95}
							]],
					border: true,
					fit: true,
					rownumbers: true,
					showFooter: true,
					singleSelect: true
				});
				*/

                // 供应商
                $supplierid.supplierWidget({
                    required: true
                });
                <c:choose>
                    <c:when test="${param.step eq '01'}">
                    </c:when>
                    <c:otherwise>
                        // 供应商
                        // $supplierid.supplierWidget('disable');
                        $supplierid.supplierWidget('readonly', true);
                    </c:otherwise>
                </c:choose>

                $supplierid.supplierWidget('setValue', '${supplier.id }');
                $supplierid.supplierWidget('setText', '${supplier.name }');

                // 通路费
				$expensesort.widget({
					name:'t_oa_access',
					col:'expensesort',
					<c:choose>
						<c:when test="${param.step eq '01' }">
							width: 173,
						</c:when>
						<c:otherwise>
							width: 173,
						</c:otherwise>
					</c:choose>
					<c:if test="${param.step ne '01' }">
						readonly: true,
					</c:if>
					singleSelect:true,
					<c:choose>
                        <c:when test="${param.type eq 'view' or param.step ne '01'}">
                            required: false,
                        </c:when>
						<c:when test="${empty access.pricetype and empty access.expensesort}">
							required: true,
						</c:when>
						<c:otherwise>
							required: false,
						</c:otherwise>
					</c:choose>
					onlyLeafCheck: true,
					// onSelect: function(data) {
					onChecked: function(data) {
						
						var expensesortvalue = $expensesort.widget('getValue');
						var expensesorttext = $expensesort.widget('getText');
						
						$expensesort_i.val(expensesorttext);

						if(expensesortvalue == null || expensesortvalue == '') {
						
							if($expensesort.widget('getValue') == '' && $pricetype.val() == '') {

								$pricetype.validatebox({required: true});
								$expensesort.validatebox({required: true});
							} else {

								$pricetype.validatebox({required: false});
								$expensesort.validatebox({required: false});
							}
							
							setTimeout(function() {
								$expensesort.validatebox('validate');
								$pricetype.validatebox('validate');
							}, 100);

						} else {

							if($expensesort.widget('getValue') == '' && $pricetype.val() == '') {

								$pricetype.validatebox({required: true});
								$expensesort.validatebox({required: true});
							} else {

								$pricetype.validatebox({required: false});
								$expensesort.validatebox({required: false});
							}
							
							setTimeout(function() {
								$expensesort.validatebox('validate');
								$pricetype.validatebox('validate');
							}, 100);

						}

						var expensesort_text = $expensesort.widget('getText');
						var pricetype_text = $pricetype.find('option:selected').text();
						
						return false;
					},
					onLoadSuccess:function(){

						$expensesort.validatebox({missingMessage:'通路费和特价至少应选择一个！'});
					},
					onClear: function() {

						var expensesort_text = $expensesort.widget('getText');
						var pricetype_text = $pricetype.find('option:selected').text();

						if($pricetype.val() == '' && $expensesort.val() == '') {
							
							if($expensesort.widget('getValue') == '' && $pricetype.val() == '') {

								$pricetype.validatebox({required: true});
								$expensesort.validatebox({required: true});
							} else {

								$pricetype.validatebox({required: false});
								$expensesort.validatebox({required: false});
							}
							
							setTimeout(function() {
								$expensesort.validatebox('validate');
								$pricetype.validatebox('validate');
							}, 100);
							
							return ;
						}
							
						setTimeout(function() {
							$expensesort.validatebox('validate');
							$pricetype.validatebox('validate');
						}, 100);

					}
				});
				
				$pricetype.change(function() {

					copyDate();

					if($(this).val() == '') {
						
						if($expensesort.widget('getValue') == '' && $pricetype.val() == '') {

							$pricetype.validatebox({required: true});
							$expensesort.validatebox({required: true});
						} else {

							$pricetype.validatebox({required: false});
							$expensesort.validatebox({required: false});
						}
						
						$expensesort.validatebox('validate');
						$pricetype.validatebox('validate');
						return true;

					} else {

						if($expensesort.widget('getValue') == '' && $pricetype.val() == '') {

							$pricetype.validatebox({required: true});
							$expensesort.validatebox({required: true});
						} else {

							$pricetype.validatebox({required: false});
							$expensesort.validatebox({required: false});
						}
						
						$expensesort.validatebox('validate');
						$pricetype.validatebox('validate');
						
						return true;
					}
				});
				
				// 客户
				<c:choose>
					<c:when test="${param.step eq '01' }">
						$customerid.customerWidget({
							required:true,
							isall: true,
							onSelect: function(data) {
		
								var oldcustomerid = $('#oa-oldcustomerid-oaAccessPage').val();
								var newcustomerid = $customerid.customerWidget('getValue');
								if(oldcustomerid == newcustomerid) {
									return false;
								}
								$('#oa-oldcustomerid-oaAccessPage').val(newcustomerid);
								
								var rows = $goodsprice.datagrid('getRows');
								for(var i = 0; i < rows.length; i++) {
								
									var row = rows[i];
		
									if(row.goodsid == undefined || row.goodsid == null || row.goodsid == '') {
										continue;
									}
									
									var goodsid = row.goodsid;
									var customerid = newcustomerid;
									
									$.ajax({
		
										type: 'post',
										data: {customerid: customerid, goodsid: goodsid, index: i},
										url: 'oa/hd/getGoodsPrice.do',
										success: function(data) {
		
											var json = $.parseJSON(data);
		
											var index = parseInt(json.index);
											$goodsprice.datagrid('endEdit', index);
											
											var rows = $goodsprice.datagrid('getRows');
											var row = rows[index];
											row.oldprice = json.goods.taxprice;
											$goodsprice.datagrid('updateRow', {index: index, row: row});
											
											computeMypriceUnedit(index);
		
										}
									});
								}
									
								$.ajax({
									type: 'post',
									data: {customerid: newcustomerid},
									url: 'oa/hd/getCustomerById.do',
									success: function(data) {
		
										var json =$.parseJSON(data);
										var customer = json.customer;
		
										if(customer != null && customer != undefined) {
										
											$companyid.val(newcustomerid);
											$companyname.val(customer.caraccount);
											$bank.val(customer.bank);
											$bankno.val(customer.cardno);
											$executeaddr.val(customer.address);
										} else {

											$companyid.val('');
											$companyname.val('');
											$bank.val('');
											$bankno.val('');
											$executeaddr.val('');
										}
									}
								});
							}
						});
						
						$customerid.customerWidget('setText', '${customer.name }');
					</c:when>
					<c:otherwise>
						$customerid.customerWidget({
						});
						
						//$customerid.customerWidget('disable');
						$customerid.customerWidget('readonly', true);
					</c:otherwise>
				</c:choose>
				
				$customerid.customerWidget('setValue', '${customer.id }');
				$customerid.customerWidget('setText', '${customer.name }');
				
				// 区域
				$salesarea.widget({
					name:'t_oa_access',
					col:'salesarea',
					width: 133,
					singleSelect:true,
					required:true,
					onlyLeafCheck:false
				});
				
				// 对所有的select项目根据其data属性进行赋值
				$('select').each(function() {
				
					var data = $(this).attr('data');
					$(this).removeAttr('data');
					$(this).children().removeAttr('selected');
					//$(this).val(data);
					$(this).children().each(function() {
					
						if($(this).attr('value') == data) {
							$(this).attr('selected', 'selected');
						}
					});
				});
				
				$paytype.change(switchInvoiceDiscount);
				$pricetype.change(initOffPriceDate);
				switchInvoiceDiscount();
				initOffPriceDate();

				/*
				$comment1.comments({
					businesskey: '${param.id }',
					definitionkey: '${param.definitionkey }',
					processid: '${param.processid }',
					taskkey: 'apply,financialManagerAudit,generalManagerAudit,salesManagerExec',
					agree: '1',
					width: 187
				});
	
				$comment2.comments({
					businesskey: '${param.id }',
					definitionkey: '${param.definitionkey }',
					processid: '${param.processid }',
					taskkey: 'salesmanExec,companyManager,dataManagerRegist',
					agree: '1',
					width: 253
				});
	
				$comment5.comments({
					businesskey: '${param.id }',
					definitionkey: '${param.definitionkey }',
					processid: '${param.processid }',
					taskkey: 'feeManager',
					agree: '1',
					width: 783
				});
				*/

				$planbegindate.blur(copyDate);
				$planenddate.blur(copyDate);

			}
    	});
    });
    
    function upperPayamount() {
    
    	var payamount = $payamount_i.numberbox('getValue');
    	$amountwords.val(AmountUnitCnChange(payamount));
    }
    
    // 支票支付和折扣支付切替
    function switchInvoiceDiscount() {
    				
		var v = $paytype.val();
		
		$compdiscount.removeAttr('disabled');
		$comdownamount.removeAttr('disabled');
		$payamount.removeAttr('disabled');
		
		// 未选中支付方式
		if(v == '') {
						
			$discount.panel('close');
			$comment3container.panel('close');
			$invoice.panel('close');
			$comment4container.panel('close');

			$companyname.validatebox({required: false});
			$companyid.validatebox({required: false});
			$bank.validatebox({required: false});
			$bankno.validatebox({required: false});
			$payamount_i.numberbox({required: false});
			$amountwords.validatebox({required: false});
			$expensesort_i.validatebox({required: false});
			$invoicetype.validatebox({required: false});

		// 折扣
		} else if(v == '1') {
						
			$discount.panel('close');
			$comment3container.panel('close');
			$discount.panel('setTitle', '票扣折让');
			$invoice.panel('close');
			$comment4container.panel('close');

			$companyname.validatebox({required: false});
			$companyid.validatebox({required: false});
			$bank.validatebox({required: false});
			$bankno.validatebox({required: false});
			$payamount_i.numberbox({required: false});
			$amountwords.validatebox({required: false});
			$expensesort_i.validatebox({required: false});
			$invoicetype.validatebox({required: false});

		// 支票
		} else if(v == '2') {
						
			$discount.panel('close');
			$comment3container.panel('close');
			// $discount.panel('setTitle', '支票折让');
			$invoice.panel('close');
			$comment4container.panel('close');

			if(step == '03' || step == '04') {

				$companyname.validatebox({required: false});
				$companyid.validatebox({required: false});
				$bank.validatebox({required: false});
				$bankno.validatebox({required: false});
				$payamount_i.numberbox({required: false});
				$amountwords.validatebox({required: false});
				$expensesort_i.validatebox({required: false});
				$invoicetype.validatebox({required: false});
			}

		} else if(v == '3') {
								
			$discount.panel('close');
			$comment3container.panel('close');
			$invoice.panel('close');
			$comment4container.panel('close');
	
			$compdiscount.attr('disabled', 'disabled');
			$comdownamount.attr('disabled', 'disabled');
			$payamount.attr('disabled', 'disabled');

			$companyname.validatebox({required: false});
			$companyid.validatebox({required: false});
			$bank.validatebox({required: false});
			$bankno.validatebox({required: false});
			$payamount_i.numberbox({required: false});
			$amountwords.validatebox({required: false});
			$expensesort_i.validatebox({required: false});
			$invoicetype.validatebox({required: false});
		
		}
    
    }
    
    // 初始化降价时间是否必填
    function initOffPriceDate() {
    
    	var v = $pricetype.val();
    	
    	$combegindate.validatebox({required: false});
    	$comenddate.validatebox({required: false});
    	
    	// 降价特价
    	if(v == 2) {

			<c:if test="${param.step == '02' or param.step == '03' }">    	
		    	$combegindate.validatebox({required: true});
		    	$comenddate.validatebox({required: true});
	    	</c:if>
    	}
    
    }
    
    // 第一部分：商品价格变更信息
    function addGoodsPrice() {
    
		setTimeout(function(){
	
			if(retPriceEditIndex() >= 0) {
			
				$.messager.alert("提醒", "当前处于编辑状态，无法添加！");
				return false;
			}
	    
	    	var rows = $goodsprice.datagrid('getRows');
	    	
	    	///////
	    	var index = -1; 
	    	for(var i = rows.length - 1; i >= 0; i--) {
	    	
	    		var row = rows[i];
	    		if(row.goodsname == undefined || row.goodsname == null || row.goodsname == '') {
	    			index = i;
	    		} else {
	    			break;
	    		}
	    	}
	    	///////
	    	$('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').remove();
	    	if(index >= 0) {
	    		beginEditPriceRow(index);
	    		$goodsprice.datagrid('scrollTo', index);
	    		return ;
	    	}
	    	
	    	priceEditIndex = rows.length;
	    	
	    	$goodsprice.datagrid('appendRow', {});
	    	beginEditPriceRow(priceEditIndex);
	    	
	    	$goodsprice.datagrid('scrollTo', priceEditIndex);
	    	
		}, 100);    	
    }
    
	function editGoodsPrice() {

    	var customerid = $customerid.widget('getValue');
    	
    	if(customerid == '' || customerid == null) {
	    		
    		$('#' + $customerid.attr('id')).focus();
    		$.messager.alert("提醒", "请先选择客户！");
    		return false;
    	}

		var row = $goodsprice.datagrid('getSelected');
		var rowIndex = $goodsprice.datagrid('getRowIndex', row);
		beginEditPriceRow(rowIndex);
	}
	
	function removeGoodsPrice() {
	
		if(retPriceEditIndex() >= 0) {
		
			$.messager.alert("提醒", "当前处于编辑状态，无法删除！");
			return false;
		}
		
		var row = $goodsprice.datagrid('getSelected');
		var goodsname = row.goodsname;
		var rowIndex = $goodsprice.datagrid('getRowIndex', row);
		if(rowIndex < 0) {
			$.messager.alert("提醒", "请选择记录！");
			return false;
		}
		$goodsprice.datagrid('deleteRow', rowIndex);
		{
			var amountRows = $goodsamount.datagrid('getRows');
			for(var i = 0; i < amountRows.length; i++) {
				
				if(amountRows[i].goodsname == goodsname) {
					$goodsamount.datagrid('deleteRow', i);
				}
			}
		}
		priceEditIndex = -1;
	}
    
    function beginEditPriceRow(rowIndex) {

    	if(rowIndex < 0){
    		$.messager.alert("提醒", "请选择记录！");
    		return false;
    	}
    	
	    var customerid = $customerid.widget('getValue');
	    if(customerid == '' || customerid == null) {
	    		
	    	$('#' + $customerid.attr('id')).focus();
	    	$.messager.alert("提醒", "请先选择客户！");
	    	return false;
	    }
    	
    	priceEditIndex = rowIndex;
    	$goodsprice.datagrid('beginEdit', rowIndex);
    	
    	var priceRows = $goodsprice.datagrid('getRows');
    	var priceRow = priceRows[rowIndex];
    	
    	$('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').remove();
    	if(priceRow.goodsid == undefined || priceRow.goodsid == null || priceRow.goodsid == '') {
    		$('<input type="hidden" id="widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage" value=""/>').appendTo('body');
    	} else {
    		$('<input type="hidden" id="widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage" value="' + priceRow.goodsid + '"/>').appendTo('body');
    	}
    	
    	setTimeout(function(){

	    	var editors = $goodsprice.datagrid('getEditors', rowIndex);
	    	
	    	editors[0].target.attr('id', 'd' + getRandomid());
	    	initGoodsWidget(editors[0].target);
	    	
	    	var ids = new Array();
	    	
	    	tempname = editors[1].target.val();
	    	
	    	for(var i = 1; i < editors.length; i++) {
	    	
	    		var editor = editors[i];
	    		var id = 'input' + getRandomid();
	    		ids.push(id);
	    		editor.target.attr('id', id);
	    	}

			$('#' + ids[0]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[0]).blur();
					$('#' + ids[1]).focus();
				}
			});

			$('#' + ids[1]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[1]).blur();
					$('#' + ids[2]).focus();
				}
			});

			$('#' + ids[2]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[2]).blur();
					$('#' + ids[3]).focus();
				}
			});

			$('#' + ids[3]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[3]).blur();
					$('#' + ids[4]).focus();
				}
			});

			$('#' + ids[4]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[4]).blur();
					$('#' + ids[5]).focus();
				}
			});

			$('#' + ids[5]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[5]).blur();
					$('#' + ids[6]).focus();
				}
			});

			$('#' + ids[6]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[6]).blur();
					$('#' + ids[7]).focus();
				}
			});
	    	
	    	/*
	    	for(var i = 0; i < ids.length - 1; i++) {
	    	
	    		var id = '#' + ids[i];
	    		var nid = '#' + ids[i + 1];
	    		
	    		$(id).die("keydown").live("keydown", function(event){

					//enter
					if(event.keyCode==13){

						$(id).blur();
						$(nid).focus();
					}
				});
	
	    	}
			*/
	    	
    	}, 100);
    }
    
    function endEditGoodsPrice() {
    	
    	if(retPriceEditIndex() < 0) {
    		return true;
    	}
    	
    	var rowIndex = retPriceEditIndex();
    	$goodsprice.datagrid('endEdit', rowIndex);
    	$goodsprice.datagrid('selectRow', rowIndex);
    	priceEditIndex = -1;
    	
    	var rowData = $goodsprice.datagrid('getSelected');
    	
    	if(rowData.goodsname == undefined || rowData.goodsname == '' || rowData.goodsname == null) {

			$goodsprice.datagrid('endEdit', rowIndex);
    		$goodsprice.datagrid('updateRow', {index: rowIndex, row: {}});
    		return false;
    	}
    	
    	var goodsid = rowData.goodsid;
    	//$.ajax({
    	//	type: 'post',
    	//	data: {goodsid: goodsid},
    	//	url: 'oa/hd/getErpNum.do',
    	//	success: function(data) {
    		
    			// var json = $.parseJSON(data);
    			// var erpnum = json.total;
    			var erpnum = '';
    			var difprice = '';
    			
    			if(rowData.newprice != '' && rowData.newprice != null && rowData.oldprice != '' && rowData.oldprice != null) {
    			
    				difprice = rowData.oldprice - rowData.newprice;
    				difprice = formatterMoney(difprice, 2);
    			}
    	
		    	var goodsAmount = {
		    		goodsid: rowData.goodsid,
		    		goodsname: rowData.goodsname,
		    		erpnum: erpnum,
		    		difprice: difprice
		    	};
		    	
		    	var amountRows = $goodsamount.datagrid('getRows');
		    	for(var i = 0; i < amountRows.length; i++) {
		    	
		    		var amountRow = amountRows[i];
		    		if(amountRow.goodsname == tempname) {
		    			$goodsamount.datagrid('updateRow', {index: i, row: goodsAmount});
		    			return ;
		    		}
		    	}
		    	
		    	var max = -1;
		    	for(var i = amountRows.length - 1; i >= 0; i--) {

					var amountRow = amountRows[i];
					if(amountRow.goodsname != undefined && amountRow.goodsname != null && amountRow.goodsname != '') {
						break;
					}
			    	max = i;
		    	}
		    	
		    	if(max < 0) {
		    		$goodsamount.datagrid('appendRow', goodsAmount);
		    		return ;
		    	}
		    	
		    	$goodsamount.datagrid('updateRow', {index: max, row: goodsAmount});
		    	return ;
		    	
    	//	}
    	//});
    	
    	return true;
    }
    
    function computeMyprice(index) {

		//setTimeout(function(){
		
			var pei = retPriceEditIndex();
		
	    	var editor1 = $goodsprice.datagrid('getEditor', {index:index, field:'oldprice'});
	    	var editor2 = $goodsprice.datagrid('getEditor', {index:index, field:'newprice'});
	    	var editor3 = $goodsprice.datagrid('getEditor', {index:index, field:'factoryprice'});
	    	var editor4 = $goodsprice.datagrid('getEditor', {index:index, field:'myprice'});

	    	var oldprice = editor1.target.numberbox('getValue');
	    	var newprice = editor2.target.numberbox('getValue');
	    	var factoryprice = editor3.target.numberbox('getValue');
	    	var myprice = oldprice - newprice - factoryprice;

	    	editor4.target.numberbox('setValue', myprice);
		//}, 100);    	
    }
    
    function computeMypriceUnedit(index) {
    
    	var rows = $goodsprice.datagrid('getRows');
    	var row = rows[index];
    	
    	var oldprice = 0;
    	var newprice = 0;
    	var factoryprice = 0;
    	
    	oldprice = parseFloat(row.oldprice);
    	newprice = parseFloat(row.newprice);
    	factoryprice = parseFloat(row.factoryprice);
    	
    	if(row.oldprice == undefined || row.oldprice == null || row.oldprice == '') {
    		oldprice =  parseFloat('0');
    	}
    	
    	if(row.newprice == undefined || row.newprice == null || row.newprice == '') {
    		newprice =  parseFloat('0');
    	}
    	
    	if(row.factoryprice == undefined || row.factoryprice == null || row.factoryprice == '') {
    		factoryprice =  parseFloat('0');
    	}

    	var myprice = parseFloat(oldprice - newprice - factoryprice);

    	if(isNaN(myprice)) {
    		row.myprice = '';
    		$goodsprice.datagrid('updateRow', {index: index, row: row});
    	} else {
    		row.myprice = formatterMoney(myprice);
    		$goodsprice.datagrid('updateRow', {index: index, row: row});
    	}
    }
    
    // 计算毛利率
    function computeRate(index) {
    
		//setTimeout(function(){
		
			// var pei = retPriceEditIndex();

			var editor1 = $goodsprice.datagrid('getEditor', {index:index, field:'newprice'});
			var editor2 = $goodsprice.datagrid('getEditor', {index:index, field:'buyprice'});
			var editor3 = $goodsprice.datagrid('getEditor', {index:index, field:'rate'});
			var editor4 = $goodsprice.datagrid('getEditor', {index:index, field:'factoryprice'});
			
			var newprice = editor1.target.numberbox('getValue');
			var buyprice = editor2.target.numberbox('getValue');
			var factoryprice = editor4.target.numberbox('getValue');
			
			// 工厂让利未填写时，无法计算毛利率
			if(factoryprice == null || factoryprice == '') {
			
				factoryprice = '0.00';
			}
			
			var n = newprice * 1;
			if(newprice == null || newprice == '' || n == 0) {
				editor3.target.numberbox('setValue', '');
				return false;
			}
			
			var profit = parseFloat(newprice) + parseFloat(factoryprice) - parseFloat(buyprice);
			var rate = profit / newprice * 100;
			
			editor3.target.numberbox('setValue', rate);
		//}, 100);    	
    }
    
    function retPriceEditIndex() {
    
    	var rows = $goodsprice.datagrid('getRows');
    	
    	for(var i = 0; i < rows.length; i++) {
    		var editors = $goodsprice.datagrid('getEditors', i);
    		if(editors.length > 0) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    // 第二部分：商品数量信息
    function beginEditAmountRow(rowIndex) {

    	if(rowIndex < 0){
    		$.messager.alert("提醒", "请选择记录！");
    		return false;
    	}
    	
    	amountEditIndex = rowIndex;
    	$goodsamount.datagrid('beginEdit', rowIndex);
    	
    	setTimeout(function() {
    	
	    	var editors = $goodsamount.datagrid('getEditors', rowIndex);
	    	var ids = new Array();
	    	
	    	for(var i = 0; i < editors.length; i++) {
	    	
	    		var editor = editors[i];
	    		var id = 'input' + getRandomid();
	    		ids.push(id);
	    		editor.target.attr('id', id);
	    	}

			$('#' + ids[0]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[0]).blur();
					$('#' + ids[1]).focus();
				}
			});

			$('#' + ids[1]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[1]).blur();
					$('#' + ids[2]).focus();
				}
			});

			$('#' + ids[2]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[2]).blur();
					$('#' + ids[3]).focus();
				}
			});

			$('#' + ids[3]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[3]).blur();
					$('#' + ids[4]).focus();
				}
			});

			$('#' + ids[4]).die("keydown").live("keydown", function(event){
				//enter
				if(event.keyCode==13){

					$('#' + ids[4]).blur();
					$('#' + ids[5]).focus();
				}
			});
    	
    	}, 100);
    }

    function editGoodsAmount() {

    	var amountRow = $goodsamount.datagrid('getSelected');
    	var amountRowIndex = $goodsamount.datagrid('getRowIndex', amountRow);
    
    	if(amountRowIndex < 0){
    		$.messager.alert("提醒", "请选择记录！");
    		return false;
    	}
    	
    	beginEditAmountRow(amountRowIndex);
    }
    
    function endEditGoodsAmount() {
        	
    	if(retAmountEditIndex() < 0) {
    		return true;
    	}
    	
    	var editors = $goodsamount.datagrid('getEditors');
    
    	$goodsamount.datagrid('endEdit', retAmountEditIndex());
    	amountEditIndex = -1;
    	
    	return true;
    }
    
    function computeAmount() {
    
    	//setTimeout(function() {
    	
    		var aei = retAmountEditIndex();
    	
			var editor1 = $goodsamount.datagrid('getEditor', {index:aei,field:'difprice'});
			var editor2 = $goodsamount.datagrid('getEditor', {index:aei,field:'unitnum'});
			var editor3 = $goodsamount.datagrid('getEditor', {index:aei,field:'auxnum'});
			var editor4 = $goodsamount.datagrid('getEditor', {index:aei,field:'auxremainder'});
			var editor5 = $goodsamount.datagrid('getEditor', {index:aei,field:'amount'});
			
			var difprice = editor1.target.numberbox('getValue');
			var unitnum = editor2.target.numberbox('getValue');
			
			var amount = difprice * unitnum;
			editor5.target.numberbox('setValue', amount);
			return false;

    	//}, 100);
    }
    
    // 根据主单位数量计算辅单位数量
    function computeAuxnum() {
    
    	//setTimeout(function() {
	    	var rows = $goodsamount.datagrid('getData');
			var row = rows.rows[amountEditIndex];
			
			var goodsid = row.goodsid;
			
			if(goodsid == undefined || goodsid == null || goodsid == '') {
			
				return false;
			}
			
			$.ajax({
			
				type: 'post',
				data: {goodsid: goodsid},
				url: 'oa/hd/getGoodsInfo.do',
				success: function(data) {
				
					var json = $.parseJSON(data).goods;
					var auxunitid = json.auxunitid;
					var unitnum = $goodsamount.datagrid('getEditor', {index:amountEditIndex,field:'unitnum'}).target.numberbox('getValue');
					
					$.ajax({
						type: 'post',
						data: {goodsid: goodsid, auxunitid: auxunitid, unitnum: unitnum},
						url: 'oa/hd/computeGoodsByUnitnum.do',
						success: function(data) {
						
							var json = $.parseJSON(data);
							var auxnum = json.auxInteger;
							var auxremainder = json.auxremainder;
	
							$goodsamount.datagrid('getEditor', {index:amountEditIndex,field:'auxnum'}).target.numberbox('setValue', auxnum);
							$goodsamount.datagrid('getEditor', {index:amountEditIndex,field:'auxremainder'}).target.numberbox('setValue', auxremainder);
							
						}
					});
				}
			});
		//}, 100);
    }
    
    // 根据辅数量计算数量
    function computeUnitnum() {
    
    	//setTimeout(function() {
	    	var rows = $goodsamount.datagrid('getData');
			var row = rows.rows[retAmountEditIndex()];
			
			var goodsid = row.goodsid;
			
			if(goodsid == undefined || goodsid == null || goodsid == '') {
			
				return false;
			}
			
			$.ajax({
			
				type: 'post',
				data: {goodsid: goodsid},
				url: 'oa/hd/getGoodsInfo.do',
				success: function(data) {
				
					var json = $.parseJSON(data).goods;
					var auxunitid = json.auxunitid;
					var auxnum = $goodsamount.datagrid('getEditor', {index: retAmountEditIndex(), field:'auxnum'}).target.numberbox('getValue');
					var auxremainder = $goodsamount.datagrid('getEditor', {index: retAmountEditIndex(), field:'auxremainder'}).target.numberbox('getValue');
					
					$.ajax({
						type: 'post',
						data: {goodsid: goodsid, auxunitid: auxunitid, auxInterger: auxnum, auxremainder: auxremainder, taxprice: '0'},/*taxprice: '0'*/
						url: 'oa/hd/computeGoodsByAuxnum.do',
						success: function(data) {
						
							var json = $.parseJSON(data);
							var unitnum = json.mainnum;
	
							$goodsamount.datagrid('getEditor', {index: retAmountEditIndex(), field:'unitnum'}).target.numberbox('setValue', unitnum);
							
						}
					});
				}
			});
		//}, 100);
    }
    
    // 计算第二部分费用总额
    function computeTotalAmount() {
    
    	//setTimeout(function() {
    	
    		var rows = $goodsamount.datagrid('getRows');
    		var sum = parseFloat($goodsamount.datagrid('getEditor', {index: retAmountEditIndex(),field:'amount'}).target.numberbox('getValue'));
    		for(var i = 0; i < rows.length; i++) {
    		
    			if(i == amountEditIndex) {
    				continue;
    			}
    		
    			if(rows[i].amount == undefined) {
    			} else {
    			sum = sum + parseFloat(rows[i].amount);
    			}
    		}

    		$totalamount.numberbox('setValue', sum);
    	//}, 100);
    }
    
    // 计算第二部分总数量
    function computeTotalNum() {
    
    	//setTimeout(function() {
    	
    		var rows = $goodsamount.datagrid('getRows');
    		var sum = parseFloat($goodsamount.datagrid('getEditor', {index: retAmountEditIndex(),field:'unitnum'}).target.numberbox('getValue'));
    		for(var i = 0; i < rows.length; i++) {
    		
    			if(i == amountEditIndex) {
    				continue;
    			}
    		
    			if(rows[i].amount == undefined) {
    			} else {
    				sum = sum + parseFloat(rows[i].unitnum);
    			}
    		}

    		$totalnum.numberbox('setValue', sum);
    	//}, 100);
    }
    
    // 计算降价金额		降价金额 = 差价金额
    function computeDownAmount() {
    
    	//setTimeout(function() {
    	
    		// var amount = $goodsamount.datagrid('getEditor', {index:amountEditIndex,field:'amount'}).target.numberbox('getValue');
    		var difprice = $goodsamount.datagrid('getEditor', {index: retAmountEditIndex(), field:'difprice'}).target.numberbox('getValue');
    		//var erpnum = $goodsamount.datagrid('getEditor', {index: retAmountEditIndex(), field:'erpnum'}).target.val();
			var erpnum = $goodsamount.datagrid('getRows')[retAmountEditIndex()].erpnum;

			var downamount;
    		if(difprice == null || difprice == '' || downamount == null || downamount == '') {
    		
    			downamount = '0.00'
    		} else {
    		
    			downamount = difprice * erpnum;
    		}
    		
    		$goodsamount.datagrid('getEditor', {index: retAmountEditIndex(),field:'downamount'}).target.text(downamount);
    	//}, 150);
    }
    
    function retAmountEditIndex() {
    
    	var rows = $goodsamount.datagrid('getRows');
    	
    	for(var i = 0; i < rows.length; i++) {
    		var editors = $goodsamount.datagrid('getEditors', i);
    		if(editors.length > 0) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    // 第三部分票价折扣 BrandDiscount
    function beginEditDiscountRow(rowIndex) {
    
    	if(rowIndex < 0){
    		$.messager.alert("提醒", "请选择记录！");
    		return false;
    	}
    	
    	discountEditIndex = rowIndex;
    	$branddiscount.datagrid('beginEdit', rowIndex);
		$branddiscount.datagrid('scrollTo', rowIndex);
    }
    
	function editBrandDiscount() {
		var row = $branddiscount.datagrid('getSelected');
		var rowIndex = $branddiscount.datagrid('getRowIndex', row);
		beginEditDiscountRow(rowIndex);
	}
    
    function endEditDiscountRow() {

    	$branddiscount.datagrid('endEdit', retDiscountEditIndex());
    	discountEditIndex = -1;
    }
    
    function removeDiscountRow() {

		if(retDiscountEditIndex() >= 0) {
		
			$.messager.alert("提醒", "当前处于编辑状态，无法删除！");
			return false;
		}
		
		var row = $branddiscount.datagrid('getSelected');
		var rowIndex = $branddiscount.datagrid('getRowIndex', row);
		if(rowIndex < 0) {
			$.messager.alert("提醒", "请选择记录！");
			return false;
		}
		$branddiscount.datagrid('deleteRow', rowIndex);
		discountEditIndex = -1;
    }

    function addBrandDiscount() {
    
    	//setTimeout(function() {
    	    // retDiscountEditIndex();
    	
    		if(retDiscountEditIndex() >= 0) {
    		
    			$.messager.alert("提醒", "当前处于编辑状态，无法添加！");
    			return false;
    		}
    		
    		var rows = $branddiscount.datagrid('getRows');
    		var index = -1;
    		for(var i = rows.length - 1; i >= 0; i--) {
    			
    			var row = rows[i];
    			if(row.brandid == undefined || row.brandid == null || row.brandid == '') {
    			
    				index = i;
    				discountEditIndex = i;
    			} else {
    				break;
    			}
    		}
    		
    		if(index >= 0) {
    		
    			beginEditDiscountRow(index);
    			return ;
    		}
    		
    		index = rows.length;
    		$branddiscount.datagrid('appendRow', {});
    		
    		beginEditDiscountRow(index);
    	//}, 100);
    }
    
    function retDiscountEditIndex() {
    
    	var rows = $branddiscount.datagrid('getRows');
    	
    	for(var i = 0; i < rows.length; i++) {
    		var editors = $branddiscount.datagrid('getEditors', i);
    		if(editors.length > 0) {
    			return i;
    		}
    	}
    	return -1;
    }

    function oaaccess_handle_save_form_submit(callBack, args) {
    
    	$accessform.form({
    		onSubmit: function(param) {
    		
    			var flag = $accessform.form('validate');
    			if(!flag) {
    			
    				return false;
    			}
    			
    			if(type == '02') {
    			
    				if(($factoryamount.val() != null && $factoryamount.val() != '' && $factoryamount.val() != '0.00')
    					|| ($myamount.val() != null && $myamount.val() != '' && $myamount.val() != '0.00')) {
    				
    					if($paytype.val() == '') {
    					
    						$.messager.alert("提醒","请选择支付方式！");
    						return false;
    					}
    				}
    			}
    			
    			if(type == '03') {
    			
    				if(($factoryamount.val() != null && $factoryamount.val() != '' && $factoryamount.val() != '0.00')
    					|| ($myamount.val() != null && $myamount.val() != '' && $myamount.val() != '0.00')) {
    				
    					if($paytype.val() == '') {
    					
    						$.messager.alert("提醒","请选择支付方式！");
    						return false;
    					}
    					
    					var bdrows = $branddiscount.datagrid('getRows');
    					var flag = false;
    					for(var i = 0; i < bd.length; i ++) {
    						
    						var bd = bdrows[i];
    						if(bd.brandid != undefined || bd.brandid != null || bd.brandid != '') {
    						
    							flag = true;
    						}
    					}
    					if(!flag) {
    					
    						$.messager.alert("提醒","请输入折扣！");
    						return false;
    					}
    				}
    			}
    			
    			if(type == '04') {
    			
    				if(($factoryamount.val() != null && $factoryamount.val() != '' && $factoryamount.val() != '0.00')
    					|| ($myamount.val() != null && $myamount.val() != '' && $myamount.val() != '0.00')) {
    					
    					var bdrows = $branddiscount.datagrid('getRows');
    					var flag = false;
    					for(var i = 0; i < bd.length; i ++) {
    						
    						var bd = bdrows[i];
    						if(bd.brandid != undefined || bd.brandid != null || bd.brandid != '') {
    						
    							flag = true;
    						}
    					}
    					if(!flag) {
    					
    						$.messager.alert("提醒","请输入折扣！");
    						return false;
    					}
    				}
    			}
    			
				$goodspricelist.val(JSON.stringify($goodsprice.datagrid('getRows')));
				$goodsamountlist.val(JSON.stringify($goodsamount.datagrid('getRows')));
				$branddiscountlist.val(JSON.stringify($branddiscount.datagrid('getRows')));

				// $("input:disabled")
				$('select:disabled').each(function() {
				
					// var selectid = $(this).attr('id');
					$(this).removeAttr('disabled');
					selectDisables.push($(this));
				});

    			loading("提交中...");
    		},
    		success: function(data) {

    			loaded();
				for(var si = 0; si < selectDisables.length; si++) {
					
					selectDisables[si].attr('disabled', 'disabled');
				}

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
				if(callBack.data != undefined && callBack.data != null) {
				
					callBack.data(json.backid);
					return false;
				}
				 
    		},
    		error: function() {
    			$.messager.alert("提醒","保存失败");
    			return false;
    		}
    	});
    }
    
	// 提交表单(工作页面提交表单接口方法)
	// call为newWorkAddPage.jsp中的save方法
	// args为save方法的参数，包含args.type
	function workFormSubmit(call, args) {
        	
    	endEditGoodsPrice(retPriceEditIndex());
    	endEditGoodsAmount(retAmountEditIndex());
    	endEditDiscountRow(retDiscountEditIndex());
    	
    	if(retPriceEditIndex() < 0 && retAmountEditIndex() < 0 && retDiscountEditIndex()< 0) {
    	} else {
    	
    		$.messager.alert('提醒', '当前有内容处于编辑状态，请确定后再保存！');
    		return false;
    	}

        oaaccess_handle_save_form_submit(call, args);
        $accessform.submit();
    }

	function filterEmptyNum(value) {
		
		if(value != null || value == '') {
			return '';
		}
		return formatterMoney(value);
	}
	
	function computeBranchaccount() {
		
		var factoryamount_v = $factoryamount.numberbox('getValue');
		var payamount_v = $payamount.numberbox('getValue');
		
		var branchaccount_v = (factoryamount_v - payamount_v);
		
		$branchaccount.numberbox('setValue', branchaccount_v);
	}
	
	function copyDate() {
		
		$combegindate.val('');
		$comenddate.val('');
		$conbegindate.val('');
		$conenddate.val('');
		
		var begindate = $planbegindate.val();
		var enddate = $planenddate.val();
		
		var v = $pricetype.val();
		// 2: 降价特价
		if(v == '2') {
		
			$combegindate.val(begindate);
			$comenddate.val(enddate);
		} else {
		
			$conbegindate.val(begindate);
			$conenddate.val(enddate);
		}
		
	}
	
	function initGoodsWidget(d, initid) {
	
		$(d).goodsWidget({
		
			referwid:'RL_T_BASE_GOODS_ID_INFO',
			param: [{field:'defaultsupplier',op:'equal',value: $supplierid.supplierWidget('getValue')}],
			width: 83,
			// initValue: $(d).val(),
			// initValue: $('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').val(),
			onSelect: function(data) {
			
				var pei = retPriceEditIndex();
				var goodsid = data.id;
				var goodsname = data.name;
				
				var rows = $goodsprice.datagrid('getRows');
				for(var i = 0; i < rows.length; i++) {
				
					if(pei == i) {
						continue;
					}
				
					var row = rows[i];
					if(row.goodsid == goodsid) {
					
						$.messager.alert("提醒", "商品编号重复！");
						
						var ed = $goodsprice.datagrid('getEditor', {index:pei,field:'goodsid'});
						
						ed.target.widget('clear');
						
						return false;
					}
					if(row.goodsname == goodsname) {
					
						$.messager.alert("提醒", "商品名称重复！");
						return false;
					}
				}
				
				//rows[pei].goodsid = goodsid;
				//$goodsprice.datagrid('updateRow', {index: pei,row: rows[pei]});

				var ed = $goodsprice.datagrid('getEditor', {index:pei,field:'goodsname'});
				ed.target.val(data.name);
				
				ed = $goodsprice.datagrid('getEditor', {index:pei,field:'buyprice'});
				ed.target.numberbox('setValue', data.newbuyprice);
				
				var customerid = $customerid.widget('getValue');
				var goodsid = data.id;
				
				$.ajax({
				
					type: 'post',
					data: {customerid: customerid, goodsid: goodsid},
					url: 'oa/hd/getGoodsPrice.do',
					success: function(data) {
					
						if($('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').length > 0 && $('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').val() == goodsid) {
							return ;
						}
					
						$('#widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage').remove();
						$('<input type="hidden" id="widget_RL_T_BASE_GOODS_ID_INFO_id_oaAccessPage" value="' + goodsid + '"/>').appendTo('body');

						var json = $.parseJSON(data);
						ed = $goodsprice.datagrid('getEditor', {index:pei,field:'oldprice'});
						ed.target.numberbox('setValue', json.goods.taxprice);
						
						// computeMyprice(retPriceEditIndex());
						
						var editor1 = $goodsprice.datagrid('getEditor', {index:pei, field:'factoryprice'});
						var editor2 = $goodsprice.datagrid('getEditor', {index:pei, field:'myprice'});
						var editor3 = $goodsprice.datagrid('getEditor', {index:pei, field:'newprice'});
						var editor4 = $goodsprice.datagrid('getEditor', {index:pei, field:'rate'});
						var editor5 = $goodsprice.datagrid('getEditor', {index:pei, field:'senddetail'});
						$(editor1.target).numberbox('clear');
						$(editor2.target).numberbox('clear');
						$(editor3.target).numberbox('clear');
						$(editor4.target).numberbox('clear');
						$(editor5.target).val('');
					}
				});
			},
			onClear: function() {
			
				var pei = retPriceEditIndex();
			
				var editor1 = $goodsprice.datagrid('getEditor', {index:pei, field:'factoryprice'});
				var editor2 = $goodsprice.datagrid('getEditor', {index:pei, field:'myprice'});
				var editor3 = $goodsprice.datagrid('getEditor', {index:pei, field:'newprice'});
				var editor4 = $goodsprice.datagrid('getEditor', {index:pei, field:'rate'});
				var editor5 = $goodsprice.datagrid('getEditor', {index:pei, field:'senddetail'});
				var editor6 = $goodsprice.datagrid('getEditor', {index:pei, field:'goodsname'});
				var editor7 = $goodsprice.datagrid('getEditor', {index:pei, field:'oldprice'});
				var editor8 = $goodsprice.datagrid('getEditor', {index:pei, field:'buyprice'});
				$(editor1.target).numberbox('clear');
				$(editor2.target).numberbox('clear');
				$(editor3.target).numberbox('clear');
				$(editor4.target).numberbox('clear');
				$(editor5.target).val('');
				$(editor6.target).val('');
				$(editor7.target).numberbox('clear');
				$(editor8.target).numberbox('clear');
			}
		
		});
		
		if(initid == undefined || initid == null || initid == '') {
		
			return ;
		}
		
		setTimeout(function() {
			
			$(d).goodsWidget('setValue', initid);
			$.ajax({
				type: 'post',
				dataType: 'json',
				url: 'oa/hd/common/selectGoodsInfo.do',
				data: {id: initid},
				success: function(json) {
				
					if(json.goods == null) {
						return ;
					}
				
					$(d).goodsWidget('setText', json.goods.name);
				},
				error: function() {}
			});
		}, 100);
	
	}
    -->
    </script>
  </body>
</html>
