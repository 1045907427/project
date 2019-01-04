<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>货款支付申请单</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <div id="oa-panel-oaSupplierPayPage">
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

if(type == 'handle') {

    if(step == '99') {
        url = 'oa/oaSupplierPayViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
    } else {
        url = 'oa/oaSupplierPayHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
    }
} else if(type == 'view') {

    url = 'oa/oaSupplierPayViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
} else if(type == 'print') {

    url = 'oa/oaSupplierPayPrintPage.do?id=${param.id }&type=print&processid=${param.processid }';
    window.location.href = url;
}

$(function() {

    $('#oa-panel-oaSupplierPayPage').panel({
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

            // 供应商
            $supplierid.supplierWidget({
                <c:choose>
                <c:when test="${param.type eq 'handle' and param.step != '99' }">
                required: true,
                </c:when>
                <c:otherwise>
                required: false,
                </c:otherwise>
                </c:choose>
                initValue: $supplierid.val(),
                onSelect: function(data) {

                    loading('加载数据中...');

                    var supplierid = data.id;

                    $collectionbank.val(data.bank);
                    $collectionbankno.val(data.cardno);

                    // 资金暂用报表中获取金额...
                    $.ajax({
                        type: 'post',
                        dataType: 'json',
                        url: 'report/storage/showCapitalOccupyListData.do',
                        data: {supplierid: supplierid},
                        success: function(json) {

                            loaded();

                            var d = json[0];
                            if(d.supplierid == undefined || d.supplierid == null || d.supplierid == '') {

                                return ;
                            }

                            var prepayamount = formatterMoney(d.prepayamount);			// 预付金额
                            var storageamount = formatterMoney(d.storageamount);		// 库存金额
                            var receivableamount = formatterMoney(d.receivableamount);	// 应收金额
                            var advanceamount = formatterMoney(d.advanceamount);		// 代垫金额
                            var payableamount = formatterMoney(d.payableamount);		// 应付金额
                            var totalamount = formatterMoney(d.totalamount);			// 合计金额

                            $advanceamount.numberbox('setValue', prepayamount);
                            $stockamount.numberbox('setValue', storageamount);
                            $receivableamount.numberbox('setValue', receivableamount);
                            $actingmatamount.numberbox('setValue', advanceamount);
                            $payableamount.numberbox('setValue', payableamount);
                            $totalamount.numberbox('setValue', totalamount);

                        },
                        error: function() {

                            $advanceamount.numberbox('setValue', '0.00');
                            $stockamount.numberbox('setValue', '0.00');
                            $receivableamount.numberbox('setValue', '0.00');
                            $actingmatamount.numberbox('setValue', '0.00');
                            $payableamount.numberbox('setValue', '0.00');
                            $totalamount.numberbox('setValue', '0.00');
                        }
                    });
                },
                onClear: function() {

                    $collectionbank.val('');
                    $collectionbankno.val('');

                    $advanceamount.numberbox('setValue', '0.00');
                    $stockamount.numberbox('setValue', '0.00');
                    $receivableamount.numberbox('setValue', '0.00');
                    $actingmatamount.numberbox('setValue', '0.00');
                    $payableamount.numberbox('setValue', '0.00');
                    $totalamount.numberbox('setValue', '0.00');
                }
            });
            $supplierid.supplierWidget('setValue', $supplierid.val());
            $supplierid.supplierWidget('setText', $suppliername.val());
            <c:if test="${param.type ne 'handle' or param.step eq '99' }">
            $supplierid.supplierWidget('readonly', true);
            </c:if>

            // 付款银行
            $paybank.widget({
                referwid: 'RL_T_BASE_FINANCE_BANK',
                onlyLeafCheck: true,
                singleSelect: true,
                <c:choose>
                <c:when test="${param.type eq 'handle' and empty param.step }">
                readonly: false,
                required: false,
                </c:when>
                <c:when test="${param.type eq 'handle' and param.step eq '02' }">
                readonly: false,
                required: true,
                </c:when>
                <c:when test="${param.type eq 'handle' and param.step eq '99' }">
                readonly: true,
                required: false,
                </c:when>
                </c:choose>
                width: 140
            });

            // panel onLoad close
        }
    });
});

function computePayMargin() {

    // 大写金额
    <c:if test="${param.type eq 'handle' and param.step != '99'}">

    setTimeout(function() {

        var val = $payamount.numberbox('getValue');
        $upperpayamount.val(AmountUnitCnChange(val));
    }, 100);

    </c:if>

    var arrivalamount = formatterMoney($arrivalamount.numberbox('getValue'));
    var payamount = formatterMoney($payamount.numberbox('getValue'));
    var expenseamount = formatterMoney($expenseamount.numberbox('getValue'));

    var paymargin = parseFloat(arrivalamount) - parseFloat(payamount) - parseFloat(expenseamount);

    $paymargin.numberbox('setValue', paymargin);
}

// 提交表单
function oasupplierpay_handle_save_form_submit(callBack, args) {

    $form.form({
        onSubmit: function(param) {

            var flag = $form.form('validate');
            if(!flag) {

                return false;
            }

            $('select').removeAttr('disabled');

            loading("提交中...");
            $advanceamount.numberbox('enable');
            $stockamount.numberbox('enable');
            $receivableamount.numberbox('enable');
            $actingmatamount.numberbox('enable');
            $payableamount.numberbox('enable');
            $totalamount.numberbox('enable');
        },
        success: function(data) {

            loaded();
            var json;
            try{
                json = $.parseJSON(data);
            }catch(e){
                $.messager.alert("提醒","保存失败！");
                $advanceamount.numberbox('disable');
                $stockamount.numberbox('disable');
                $receivableamount.numberbox('disable');
                $actingmatamount.numberbox('disable');
                $payableamount.numberbox('disable');
                $totalamount.numberbox('disable');
                return false;
            }

            // 保存失败
            if(data.flag) {
                $.messager.alert("提醒","保存失败！");
                $advanceamount.numberbox('disable');
                $stockamount.numberbox('disable');
                $receivableamount.numberbox('disable');
                $actingmatamount.numberbox('disable');
                $payableamount.numberbox('disable');
                $totalamount.numberbox('disable');
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

    //$.messager.confirm("提醒", "确定保存该货款支付申请单吗？", function(r){
    //	if(r){

    oasupplierpay_handle_save_form_submit(call, args);
    //	}
    //});

}

-->
</script>
</body>
</html>