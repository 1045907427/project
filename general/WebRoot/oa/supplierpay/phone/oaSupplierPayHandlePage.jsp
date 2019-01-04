<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>货款支付申请单(手机)</title>
<%@include file="/phone/common/include.jsp"%>
<script type="text/javascript">

    <%--
    localStorage.setItem('details', '${list }');
    --%>

    $(document).on('pagebeforecreate', '#main', function() {

        // 初始化select数据
        $('select').each(function() {

            var sdata = $(this).attr('sdata');

            $(this).removeAttr('sdata');
            $(this).children().removeAttr('selected');

            $(this).children().each(function() {

                if($(this).attr('value') == sdata) {
                    $(this).attr('selected', 'selected');
                }
            });
        });
    });

    $(function() {

        // 供应商
        $('#oa-supplierid2-oaSupplierPayHandlePage').on('click', function() {

            androidWidget({
                type: 'supplierWidget',
                onSelect: 'selectSupplier'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);

            if(v == '') {

                $("#oa-advanceamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-stockamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-receivableamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-actingmatamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-payableamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-totalamount-oaSupplierPayHandlePage").val('0.00');

                $('#oa-collectionbank-oaSupplierPayHandlePage').val('');
                $('#oa-collectionbankno-oaSupplierPayHandlePage').val('');
                return true;
            }

            $('#oa-supplierid-oaSupplierPayHandlePage').attr('value', (v || ':').split(':')[0]);
            return true;
        });

        // 付款银行
        $('#oa-paybank2-oaSupplierPayHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_FINANCE_BANK',
                onSelect: 'selectBank'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-paybank-oaSupplierPayHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 付款日期
        $(document).on('click', '#oa-paydate-oaSupplierPayHandlePage', function() {

            androidDateWidget($('#oa-paydate-oaSupplierPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPaydate');
        });

        // 到货日期
        $(document).on('click', '#oa-arrivaldate-oaSupplierPayHandlePage', function() {

            androidDateWidget($('#oa-arrivaldate-oaSupplierPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectArrivaldate');
        });

        // 抽单日期
        $(document).on('click', '#oa-writeoffdate-oaSupplierPayHandlePage', function() {

            androidDateWidget($('#oa-writeoffdate-oaSupplierPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectWriteoffdate');
        });

        // 计算付款差额
        $(document).on('change', '#oa-arrivalamount-oaSupplierPayHandlePage,#oa-payamount-oaSupplierPayHandlePage,#oa-expenseamount-oaSupplierPayHandlePage', computePayMargin);

        // 金额转换为大写
        $('#oa-payamount-oaSupplierPayHandlePage').on('change', function(e) {

            var v = $(this).val();
            var big = moneyToUpper(v);

            $('#oa-upperpayamount-oaSupplierPayHandlePage').val(big);
        });

    });

    /**
     * 选择供应商
     */
    function selectSupplier(data) {

        $('#oa-supplierid2-oaSupplierPayHandlePage').val(data.id + ':' + data.name);
        $('#oa-supplierid-oaSupplierPayHandlePage').val(data.id);

        $('#oa-supplierid2-oaSupplierPayHandlePage').blur();
        $('#oa-supplierid2-oaSupplierPayHandlePage').change();

        $('#oa-collectionbank-oaSupplierPayHandlePage').val(data.bank);
        $('#oa-collectionbankno-oaSupplierPayHandlePage').val(data.cardno);

        var supplierid = data.id;
        loading();

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

                $("#oa-advanceamount-oaSupplierPayHandlePage").val(prepayamount);
                $("#oa-stockamount-oaSupplierPayHandlePage").val(storageamount);
                $("#oa-receivableamount-oaSupplierPayHandlePage").val(receivableamount);
                $("#oa-actingmatamount-oaSupplierPayHandlePage").val(advanceamount);
                $("#oa-payableamount-oaSupplierPayHandlePage").val(payableamount);
                $("#oa-totalamount-oaSupplierPayHandlePage").val(totalamount);

            },
            error: function() {

                $("#oa-advanceamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-stockamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-receivableamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-actingmatamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-payableamount-oaSupplierPayHandlePage").val('0.00');
                $("#oa-totalamount-oaSupplierPayHandlePage").val('0.00');
            }
        });
    }

    /**
     * 选择付款银行
     */
    function selectBank(data) {

        $('#oa-paybank2-oaSupplierPayHandlePage').val(data.id + ':' + data.name);
        $('#oa-paybank-oaSupplierPayHandlePage').val(data.id);

        $('#oa-paybank2-oaSupplierPayHandlePage').blur();
        $('#oa-paybank2-oaSupplierPayHandlePage').change();
    }

    /**
     * 选择到票时间
     */
    function selectBilldate(data) {

        $('#oa-billdate-oaSupplierPayHandlePage').val(data);
        $('#oa-billdate-oaSupplierPayHandlePage').blur();
    }

    /**
     * 选择付款日期
     */
    function selectPaydate(data) {

        $('#oa-paydate-oaSupplierPayHandlePage').val(data);
        $('#oa-paydate-oaSupplierPayHandlePage').blur();
    }

    /**
     * 选择到货日期
     */
    function selectArrivaldate(data) {

        $('#oa-arrivaldate-oaSupplierPayHandlePage').val(data);
        $('#oa-arrivaldate-oaSupplierPayHandlePage').blur();
    }

    /**
     * 选择到货日期
     */
    function selectWriteoffdate(data) {

        $('#oa-writeoffdate-oaSupplierPayHandlePage').val(data);
        $('#oa-writeoffdate-oaSupplierPayHandlePage').blur();
    }

    /**
     * 计算付款差额
     */
    function computePayMargin() {

        <c:if test="${param.type eq 'view' or param.step eq '99'}">
            return true;
        </c:if>

        // 大写金额
        setTimeout(function() {

            var val = $('#oa-payamount-oaSupplierPayHandlePage').val();
            $('#oa-upperpayamount-oaSupplierPayHandlePage').val(moneyToUpper(val));
        }, 100);

        // 到货金额 - 付款金额 - 收回费用
        var arrivalamount = formatterMoney($('#oa-arrivalamount-oaSupplierPayHandlePage').val());
        var payamount = formatterMoney($('#oa-payamount-oaSupplierPayHandlePage').val());
        var expenseamount = formatterMoney($('#oa-expenseamount-oaSupplierPayHandlePage').val());

        var paymargin = parseFloat(arrivalamount) - parseFloat(payamount) - parseFloat(expenseamount);

        $('#oa-paymargin-oaSupplierPayHandlePage').val(paymargin);
        return true;
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaSupplierPayHandlePage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaSupplierPayHandlePage").submit();
        var flag = $('#oa-form-oaSupplierPayHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaSupplierPayHandlePage').validate({
            debug: false
        });
        
        $('#oa-form-oaSupplierPayHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                //beforeSubmit: showRequest,
                <c:choose>
                    <c:when test="${empty pay or empty pay.id}">
                        url: 'oa/addOaSupplierPay.do',
                    </c:when>
                    <c:otherwise>
                        url: 'oa/editOaSupplierPay.do',
                    </c:otherwise>
                </c:choose>
                success: function(data) {

                    var json = $.parseJSON(data);
                    if(json.flag) {

                        call(json.backid);
                    }
                },
                error: function(data) {

                    alertMsg(data);
                }
            });

            return false; //此处必须返回false，阻止常规的form提交

        }).submit();
    }
</script>
</head>
<body>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>处理工作[${param.processid }]</h1>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <form action="" id="oa-form-oaSupplierPayHandlePage" method="post">
        <input type="hidden" name="pay.id" id="oa-id-oaSupplierPayHandlePage" value="${param.id }"/>
        <input type="hidden" name="pay.oaid" id="oa-oaid-oaSupplierPayHandlePage" value="${param.processid }"/>
        <input type="hidden" name="paydetail" id="oa-paydetail-oaSupplierPayHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>供应商<font color="#F00">*</font>
                        <input type="text" class="required" name="pay.supplierid2" id="oa-supplierid2-oaSupplierPayHandlePage" value="<c:if test='${not empty supplier }'><c:out value='${pay.supplierid }'/>:<c:out value='${supplier.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="pay.supplierid" id="oa-supplierid-oaSupplierPayHandlePage" value="${pay.supplierid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>收款银行
                        <input type="text" class="" name="pay.collectionbank" id="oa-collectionbank-oaSupplierPayHandlePage" value="${pay.collectionbank}" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>银行账号
                        <input type="text" class="" name="pay.collectionbankno" id="oa-collectionbankno-oaSupplierPayHandlePage" value="${pay.collectionbankno}" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款金额
                        <input type="number" class="" name="pay.payamount" id="oa-payamount-oaSupplierPayHandlePage" value="${pay.payamount }" data-clear-btn="true" maxlength="15"/>
                        <input type="hidden" name="pay.upperpayamount" id="oa-upperpayamount-oaSupplierPayHandlePage" value="${pay.upperpayamount }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款日期
                        <input type="text" class="dateISO" name="pay.paydate" id="oa-paydate-oaSupplierPayHandlePage" value="${pay.paydate}" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款银行
                        <input type="text" class="" name="pay.paybank2" id="oa-paybank2-oaSupplierPayHandlePage" value="<c:if test='${not empty bank }'><c:out value='${pay.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="pay.paybank" id="oa-paybank-oaSupplierPayHandlePage" value="${pay.paybank }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>到货金额
                        <input type="number" class="" name="pay.arrivalamount" id="oa-arrivalamount-oaSupplierPayHandlePage" value="${pay.arrivalamount }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款差额
                        <input type="number" class="" name="pay.paymargin" id="oa-paymargin-oaSupplierPayHandlePage" value="${pay.paymargin }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>到货日期
                        <input type="text" class="dateISO" name="pay.arrivaldate" id="oa-arrivaldate-oaSupplierPayHandlePage" value="${pay.arrivaldate}" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票金额
                        <input type="number" class="" name="pay.billamount" id="oa-billamount-oaSupplierPayHandlePage" value="${pay.billamount }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>收回费用
                        <input type="number" class="" name="pay.expenseamount" id="oa-expenseamount-oaSupplierPayHandlePage" value="${pay.expenseamount }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>抽单金额
                        <input type="number" class="" name="pay.writeoffamount" id="oa-writeoffamount-oaSupplierPayHandlePage" value="${pay.writeoffamount }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>抽单日期
                        <input type="text" class="dateISO" name="pay.writeoffdate" id="oa-writeoffdate-oaSupplierPayHandlePage" value="${pay.writeoffdate}" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>订单金额<font color="#F00">*</font>
                        <input type="number" class="required" name="pay.orderamount" id="oa-orderamount-oaSupplierPayHandlePage" value="${pay.orderamount }" data-clear-btn="true" maxlength="15"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>预付金额
                        <input type="number" class="" name="pay.advanceamount" id="oa-advanceamount-oaSupplierPayHandlePage" value="${pay.advanceamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>库存金额
                        <input type="number" class="" name="pay.stockamount" id="oa-stockamount-oaSupplierPayHandlePage" value="${pay.stockamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>应收金额
                        <input type="number" class="" name="pay.receivableamount" id="oa-receivableamount-oaSupplierPayHandlePage" value="${pay.receivableamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>代垫金额
                        <input type="number" class="" name="pay.actingmatamount" id="oa-actingmatamount-oaSupplierPayHandlePage" value="${pay.actingmatamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>应付金额
                        <input type="number" class="" name="pay.payableamount" id="oa-payableamount-oaSupplierPayHandlePage" value="${pay.payableamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>合计占用
                        <input type="number" class="" name="pay.totalamount" id="oa-totalamount-oaSupplierPayHandlePage" value="${pay.totalamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="pay.remark" id="oa-remark-oaSupplierPayHandlePage"><c:out value="${pay.remark }"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oaSupplierPayHandlePage" data-role="footer" data-position="fixed">
        <c:choose>
            <c:when test="${empty param.taskid }">
                <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp?oarequired=${param.oarequired }">
                    <jsp:param name="taskid" value="${process.taskid }"/>
                    <jsp:param name="id" value="${param.processid }"/>
                    <jsp:param name="sign" value="${param.sign }"/>
                </jsp:include>
            </c:when>
            <c:otherwise>
                <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp?oarequired=${param.oarequired }">
                    <jsp:param name="taskid" value="${param.taskid }"/>
                    <jsp:param name="id" value="${param.processid }"/>
                    <jsp:param name="sign" value="${param.sign }"/>
                </jsp:include>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
