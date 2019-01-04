<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>批量特价审批单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',border:false">
    		<div id="oa-panel-oaOffPricePage">
    		</div>
    	</div>
    </div>
    <div id="oa-dialog-oaOffPricePage"></div>
    <style type="text/css">
        .len60 {
            width: 60px;
        }
        .len200 {
            width: 200px;
        }
    </style>
    <script type="text/javascript">
    <!--
    
    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';

    var minLength = 10;

    var customerWidgetFlag = false;
    var customerOption = {
        required: true,
        ishead: true,
        isall: true,
        isopen: true,
        disable: false,
        onSelect: function(data) {

//            $('td.displaywithid').last().text(data.id);
//            $('td.displaywithid').show();

            $indoorstaff.widget('setValue', data.indoorstaff);

            refreshPrice();
        },
        onClear: function() {
//            $('td.displaywithid').hide();
            $indoorstaff.widget('clear');
            refreshPrice();
        }
    };

    var goodsMap = {};

    if(type == 'handle') {
    	
    	if(step == '99') {
	    	url = 'oa/offprice/oaOffPriceViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    	} else {
    		url = 'oa/offprice/oaOffPriceHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
    	}
    } else if(type == 'view') {

        url = 'oa/offprice/oaOffPriceViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    } else if(type == 'print') {

        url = 'oa/offprice/oaOffPricePrintPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
        window.location.href = url;
    }
    
    $(function() {
    
    	$('#oa-panel-oaOffPricePage').panel({
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
                $comments.comments({
                    businesskey: '${param.id }',
                    processid: '${param.processid }',
                    type: 'vertical',
                    agree: null
                });

                customerOption.disable = false;
                initCustomerWidget($customerid, customerOption);

                $indoorstaff.widget({
                    referwid: 'RL_T_BASE_PERSONNEL_INDOORSTAFF',
                    width: 134,
                    <c:if test="${param.type eq 'handle' and param.step ne '99'}">
                        required: true,
                    </c:if>
                    <c:if test="${param.type eq 'view' or param.step eq '99'}">
                        readonly: true
                    </c:if>
                });

                $salesuserid.widget({
                    referwid: 'RL_T_BASE_PERSONNEL_BRAND_CUSTOMER_SELLER',
                    width: 134,
                    <c:if test="${param.type eq 'view' or param.step eq '99'}">
                        readonly: true,
                        width: 177,
                    </c:if>
                });

                $datagrid.datagrid({
                    columns: [[
                        {field:'goodsid', title: '商品编号', width: 70,
                            editor: {
                                type: 'span'
                            }
                        },
                        {field:'goodsname', title: '商品名称', width: 210,
                            formatter: function(value,row,index){
                                var goodsid = row.goodsid;
                                if(goodsMap['g' + row.goodsid] == undefined || goodsMap['g' + row.goodsid] == null || goodsMap['g' + row.goodsid] == '') {
                                    row.goodsname = value;
                                    return value;
                                }
                                row.goodsname = goodsMap['g' + row.goodsid];
                                return goodsMap['g' + row.goodsid];
                            },
                            editor: {
                                type: 'text'
                            }
                        },
                        {field:'barcode', title: '条形码', width: 120,
                            editor: {
                                type: 'span'
                            }
                        },
                        <%-- 5185 6.7&通用版：批量特价申请单可以配置哪个节点能否查看采购进价 --%>
                        {field:'buyprice', title: '进价', width: 55, align: 'right', hidden: true,<c:if test="${param.buyprice eq '1' or param.buyprice eq 'true'}">hidden: false,</c:if>
                            formatter: function(value,row,index){ return formatterMoney(value); },
                            editor: {
                                type: 'numberbox',
                                options:{
                                    required: false,
                                    precision: 2,
                                    disabled: true,
                                    onChange: computeProfit
                                }
                            }
                        },
                        {field:'oldprice', title: '原价', width: 55, align: 'right',
                            formatter: function(value,row,index){ return formatterMoney(value); },
                            editor: {
                                type: 'numberbox',
                                options:{
                                    required: false,
                                    precision: 2,
                                    disabled: true
                                }
                            }
                        },
                        {field:'offprice', title: '特价', width: 55, align: 'right',
                            formatter: function(value,row,index){ return formatterMoney(value); },
                            editor: {
                            type: 'numberbox',
                            options:{
                                required: true,
                                precision: 2,
                                onChange: computeProfit
                            }
                        }
                        },
                        {field:'profitrate', title: '毛利率%', width: 55, align: 'right', hidden: true,<c:if test="${param.buyprice eq '1' or param.buyprice eq 'true'}">hidden: false,</c:if>
                            formatter: function(value,row,index){ if(value == undefined || value == null || value == ''){return '';}return formatterMoney(value) + '%'; },
                            editor: {
                                type: 'numberbox',
                                options:{
                                    required: false,
                                    precision: 2,
                                    disabled: true
                                }
                            }
                        },
                        {field:'ordernum', title: '本次订单数量', width: 100, align: 'right',
                            editor: {
                                type: 'validatebox',
                                options:{
                                    required: false,
                                    validType: 'maxByteLength[20]'
                                }
                            }
                        },
                        {field:'remark', title: '说明', width: 145,
                            editor: {
                                type: 'validatebox',
                                options:{
                                    required: false,
                                    validType: 'maxByteLength[50]'
                                }
                            }
                        }
                    ]],
                    border: true,
                    fit: true,
                    rownumbers: true,
                    showFooter: true,
                    singleSelect: true,
                    <c:if test="${param.step ne '99' and param.type eq 'handle'}">
                        toolbar:[
                            {text: '添加', iconCls: 'button-add',    handler: addOffPriceDetail},
                            {text: '修改', iconCls: 'button-edit',   handler: editOffPriceDetail},
                            {text: '确定', iconCls: 'button-save',   handler: endEditOffPriceDetail},
                            {text: '删除', iconCls: 'button-delete', handler: removeOffPriceDetail},
                            {text: '批量添加商品', iconCls: 'button-add', handler: addOffPriceDetails}
                        ],
                        onDblClickRow: editOffPriceDetail,
                        onClickRow: endEditOffPriceDetail,
                    </c:if>
                    url: 'oa/offprice/selectOaOffPriceDetailList.do?billid=${param.id }',
                    onLoadSuccess: function(data) {

                        for(var i = data.total; i < minLength; i++) {
                            $datagrid.datagrid('appendRow', {});
                        }
                    }
                }); // datagrid close

			// panel onLoad close
			}
		});
    });

    /**
    * 添加特价明细
     */
    function addOffPriceDetail() {

        var editIndex = retOffPriceDetailEditIndex();
        if(editIndex >= 0) {
            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行添加。");
            return false;
        }

        // 搜索未编辑的行
        var rows = $datagrid.datagrid('getRows');
        var index = -1;
        for(var i = rows.length - 1; i >= 0; i--) {

            var row = rows[i];
            if(row.goodsid == undefined || row.goodsid == null || row.goodsid == '') {
                index = i;
            } else {
                break;
            }
        }

        // 明细中存在未编辑的空行
        if(index >= 0) {

            beginEditOffPriceDetailRow(index);
            return true;
        }

        // 明细中所有的行均已被编辑，添加空行
        index = rows.length;
        $datagrid.datagrid('appendRow', {});
        beginEditOffPriceDetailRow(index);

        return true;
    }

    /**
    * 编辑特价明细
     */
    function editOffPriceDetail() {

        var editIndex = retOffPriceDetailEditIndex();
        if(editIndex >= 0) {
            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行修改。");
            return false;
        }

        var selectedRow = $datagrid.datagrid('getSelected');
        var index = $datagrid.datagrid('getRowIndex', selectedRow);

        beginEditOffPriceDetailRow(index);
        return true;

    }

    /**
    * 结束编辑特价明细
     */
    function endEditOffPriceDetail() {

        var editIndex = retOffPriceDetailEditIndex();
        if(editIndex < 0) {
            return true;
        }

        var flag = $datagrid.datagrid('validateRow', editIndex);

        if(flag) {

            $datagrid.datagrid('endEdit', editIndex);

            customerOption.disable = false;
            initCustomerWidget($customerid, customerOption);
        }

        return true;
    }

    /**
    * 删除特价明细
     */
    function removeOffPriceDetail() {

        var editIndex = retOffPriceDetailEditIndex();
        if(editIndex >= 0) {
            $.messager.alert("提醒", "当前明细正处于修改状态，无法再进行删除。");
            return false;
        }

        var selectedRow = $datagrid.datagrid('getSelected');
        var index = $datagrid.datagrid('getRowIndex', selectedRow);

        if(index < 0) {
            return false;
        }
        $datagrid.datagrid('deleteRow', index);
        // computeTotalAmount();
        return true;

    }

    // 编辑明细
    function beginEditOffPriceDetailRow(rowIndex) {

        if(rowIndex < 0){
            $.messager.alert("提醒", "请选择记录！");
            return false;
        }

        if($customerid.customerWidget('getValue') == '') {
            $.messager.alert("提醒", "请选择客户！");
            return false;
        }

        customerOption.disable = true;
        initCustomerWidget($customerid, customerOption);

        $datagrid.datagrid('beginEdit', rowIndex);
        $datagrid.datagrid('scrollTo', rowIndex);

        var ed = $datagrid.datagrid('getEditor', {index: rowIndex, field: 'goodsname'});

        initGoodsWidget(ed.target);

        return true;
    }

    /**
    * 当前编辑行index
    * @returns {number}
     */
    function retOffPriceDetailEditIndex() {

        var rows = $datagrid.datagrid('getRows');

        for(var i = 0; i < rows.length; i++) {
            var editors = $datagrid.datagrid('getEditors', i);
            if(editors.length > 0) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 初始化客户控件
     */
    function initCustomerWidget(d, o) {

        <c:if test="${param.type eq 'view' or param.step eq '99'}">
            o.disable = true;
        </c:if>
        if(!customerWidgetFlag) {
            $(d).customerWidget(o);
            customerWidgetFlag = true;
        }

        if(o.disable) {
            $(d).customerWidget('readonly', true);
        } else {
            $(d).customerWidget('readonly', false);
        }
        return true;
    }

    /**
    * 初始化商品控件
    * @param d
     */
    function initGoodsWidget(d) {

        var id = $(d).val();
        $(d).attr('id', 'd' + getRandomid());

        setTimeout(function() {

            $(d).goodsWidget({
                required: false,
                onSelect: function(data) {

                    loading('正在获取商品信息...');
                    var index = retOffPriceDetailEditIndex();
                    // 商品重复
                    var rows = $datagrid.datagrid('getRows');
                    for(var i = 0; i < rows.length; i++) {
                        var row = rows[i];
                        if(i == index) {
                            continue;
                        }
                        if(row.goodsid == data.id) {
                            $.messager.alert('警告', '该商品已经存在！');
                            $(d).goodsWidget('clear');
                            loaded();
                            return ;
                        }
                    }

                    var editor1 = $datagrid.datagrid('getEditor', {index:index, field:'goodsid'});
                    var editor7 = $datagrid.datagrid('getEditor', {index:index, field:'barcode'});

                    if(data.id == $(editor1.target).text()) {
                        loaded();
                        return ;
                    }

                    $(editor1.target).text(data.id);
                    var editor2 = $datagrid.datagrid('getEditor', {index:index, field:'buyprice'});
                    var editor3 = $datagrid.datagrid('getEditor', {index:index, field:'oldprice'});
                    var editor4 = $datagrid.datagrid('getEditor', {index:index, field:'offprice'});
                    var editor5 = $datagrid.datagrid('getEditor', {index:index, field:'profitrate'});
                    var editor6 = $datagrid.datagrid('getEditor', {index:index, field:'profitrate'});

                    $(editor2.target).numberbox('clear');
                    $(editor3.target).numberbox('clear');
                    $(editor4.target).numberbox('clear');
                    $(editor5.target).numberbox('clear');
                    $(editor6.target).numberbox('clear');
                    $(editor7.target).html(data.barcode);

                    var buyprice = 0;
                    if(data.costaccountprice && data.costaccountprice > 0) {
                        buyprice = data.costaccountprice;
                    } else {
                        buyprice = data.highestbuyprice;
                        <c:choose>
                            <c:when test="${param.pricetype eq '1'}">
                                buyprice = data.newbuyprice;
                            </c:when>
                            <c:otherwise>
                                buyprice = data.highestbuyprice;
                            </c:otherwise>
                        </c:choose>
                    }

                    $(editor2.target).numberbox('setValue', buyprice);

                    //
                    goodsMap['g' + data.id] = data.name;

                    // 获取商品原价
                    $.ajax({
                        type: 'post',
                        dataType: 'json',
                        // url: 'sales/getGoodsDetail.do',
                        url: 'report/sales/showSalesGoodsQuotationReportData.do',
                        //data: {id: data.id, cid: $customerid.customerWidget('getValue'), type: 'reject'},
                        data: {customerid: $customerid.customerWidget('getValue'), brandid: data.brand, page: 1, rows: 9999},
                        success: function(json) {

                            for(var j in json.rows) {
                                if(json.rows[j].goodsid == data.id) {
                                    $(editor3.target).numberbox('setValue', json.rows[j].price);
                                    loaded();
                                    break;
                                }
                            }
                        },
                        error: function() {}
                    });

                },
                onClear: function() {

                    var index = retOffPriceDetailEditIndex();
                    var editor1 = $datagrid.datagrid('getEditor', {index:index, field:'goodsid'});
                    var editor2 = $datagrid.datagrid('getEditor', {index:index, field:'buyprice'});
                    var editor3 = $datagrid.datagrid('getEditor', {index:index, field:'oldprice'});
                    var editor4 = $datagrid.datagrid('getEditor', {index:index, field:'offprice'});
                    var editor5 = $datagrid.datagrid('getEditor', {index:index, field:'profitrate'});
                    var editor6 = $datagrid.datagrid('getEditor', {index:index, field:'ordernum'});

                    $(editor1.target).text('');
                    $(editor2.target).numberbox('clear');
                    $(editor3.target).numberbox('clear');
                    $(editor4.target).numberbox('clear');
                    $(editor5.target).numberbox('clear');
                    $(editor6.target).numberbox('clear');
                }
            });

            var editor7 = $datagrid.datagrid('getEditor', {index:retOffPriceDetailEditIndex(), field:'goodsid'});
            var goodsid = $(editor7.target).text();
            $(d).goodsWidget('setValue', goodsid);
            $(d).goodsWidget('setText', id);
        }, 100);

    }

    function refreshPrice() {

        var oldcustomerid = $customerid2.val();
        var newcustomerid = $customerid.customerWidget('getValue');

        if(oldcustomerid == newcustomerid) {
            return ;
        }

        loading('刷新中...');

        var rows = $datagrid.datagrid('getRows');
        for(var i = 0; i < rows.length; i++) {

            var row = rows[i];
            if(row.goodsid == undefined || row.goodsid == null || row.goodsid == '') {
                continue;
            }

            var brandid = '';
            $.ajax({
                type: 'post',
                dataType: 'json',
                async: false,   // 同步调用
                url: 'oa/getGoodsInfo.do',
                data: {goodsid: row.goodsid},
                success: function(json1) {
                    brandid = json1.goods.brand;

                    $.ajax({
                        type: 'post',
                        dataType: 'json',
                        async: false,   // 同步调用
                        // url: 'sales/getGoodsDetail.do',
                        url: 'report/sales/showSalesGoodsQuotationReportData.do',
                        // data: {id: row.goodsid, cid: newcustomerid, type: 'reject'},
                        data: {customerid: newcustomerid, brandid: brandid, page: 1, rows: 9999},
                        success: function(json) {

                            for(var j in json.rows) {
                                if(json.rows[j].goodsid == row.goodsid) {
                                    //$(editor3.target).numberbox('setValue', json.rows[j].price);

                                    row.oldprice = json.rows[j].price;
                                    $datagrid.datagrid('updateRow', {index: i, row: row});

                                    break;
                                }
                            }

                        },
                        error: function() {}
                    });

                },
                error: function() {}
            });


        }
        loaded();
    }

    /**
     * 计算毛利率
     * @param index
     */
    function computeProfit(index) {

        var editIndex = retOffPriceDetailEditIndex();

        if(editIndex < 0) {

            var rows = $datagrid.datagrid('getRows');
            var row = rows[index];
            var buyprice = row.buyprice;
            var offprice = row.offprice;

            if(buyprice == undefined || buyprice == null || buyprice == ''
                    || offprice == undefined || offprice == null || offprice == '' || formatterMoney(offprice) == 0.00) {
            } else {

                var profitrate = formatterMoney((offprice - buyprice) / offprice * 100);
                row.profitrate = profitrate;
                $datagrid.datagrid('updateRow', {index: index, row: row});
            }
            return ;
        }

        var pei = retOffPriceDetailEditIndex();
        var editor1 = $datagrid.datagrid('getEditor', {index:pei, field:'buyprice'});
        var editor2 = $datagrid.datagrid('getEditor', {index:pei, field:'offprice'});
        var editor3 = $datagrid.datagrid('getEditor', {index:pei, field:'profitrate'});

        var buyprice = $(editor1.target).numberbox('getValue');
        var offprice = $(editor2.target).numberbox('getValue');
        if(buyprice == undefined || buyprice == null || buyprice == ''
                || offprice == undefined || offprice == null || offprice == '' || formatterMoney(offprice) == 0.00) {
        } else {

            var profitrate = formatterMoney((offprice - buyprice) / offprice * 100);
            $(editor3.target).numberbox('setValue', profitrate);
        }
    }

    /**
     * 批量添加商品
     *
     * @returns {boolean}
     */
    function addOffPriceDetails() {

        var index = retOffPriceDetailEditIndex();
        if(index >= 0){
            $.messager.alert('提醒', '当前明细处于编辑状态，无法添加！');
            return false;
        }

        $('#oa-dialog-oaOffPricePage').dialog({
            maximizable: false,
            resizable: false,
            title: '批量添加商品',
            maximized: true,
            closed: false,
            cache: false,
            modal: true,
            href: 'oa/offprice/oaOffPriceDetailsAddPage.do?buyprice=${param.buyprice }&customerid=' + $customerid.customerWidget('getValue'),
            buttons: [{
                iconCls:'button-save',
                text: '确定',
                handler:function(){

                    var currentRows = $datagrid.datagrid('getRows');
                    var max = currentRows.length;
                    for(var k = currentRows.length - 1; k >= 0; k--){

                        var currentRow = currentRows[k];
                        if((currentRow.goodsid || '') == '') {
                            max = k;
                        } else {
                            break;
                        }
                    }

                    $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('endEdit', retDetailEditIndex());
                    var checkedRows = $('#oa-datagrid-oaOffPriceDetailsAddPage').datagrid('getChecked');
                    for(var i in checkedRows) {

                        var checkedRow = checkedRows[i];
                        var exist = false;
                        for(var j in currentRows) {

                            var currentRow = currentRows[j];
                            if(currentRow.goodsid == checkedRow.goodsid) {
                                exist = true;
                                break;
                            }
                        }

                        if(!exist) {
                            $datagrid.datagrid('insertRow', {index: max++, row: checkedRow});
                        }
                    }

                    $('#oa-dialog-oaOffPricePage').dialog('close');
                    return true;
                }
            }],
            onClose:function(){
            }
        });

        return true;
    }

    // 提交表单
    function oaoffprice_handle_save_form_submit(callBack, args) {

        endEditOffPriceDetail();

    	$form.form({
    		onSubmit: function(param) {
    		
    			var flag = $form.form('validate');
    			if(!flag) {
    			
    				return false;
    			}

                $detaillist.val(JSON.stringify($datagrid.datagrid('getRows')));

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
    	}).submit();
    }

	// 提交表单(工作页面提交表单接口方法)
	function workFormSubmit(call, args) {

        endEditOffPriceDetail();
        oaoffprice_handle_save_form_submit(call, args);
	}

	-->
	</script>
  </body>
</html>