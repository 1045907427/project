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

    <%--
    $(function() {

        // 供应商
        $('#oa-supplierid2-oaSupplierPayViewPage').on('click', function() {

            androidWidget({
                type: 'supplierWidget',
                onSelect: 'selectSupplier'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);

            if(v == '') {

                $("#oa-advanceamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-stockamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-receivableamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-actingmatamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-payableamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-totalamount-oaSupplierPayViewPage").val('0.00');
                return true;
            }

            $('#oa-supplierid-oaSupplierPayViewPage').attr('value', (v || ':').split(':')[0]);
            return true;
        });

        // 付款银行
        $('#oa-paybank2-oaSupplierPayViewPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_FINANCE_BANK',
                onSelect: 'selectBank'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-paybank-oaSupplierPayViewPage').attr('value', (v || ':').split(':')[0]);
        });

        // 付款日期
        $(document).on('click', '#oa-paydate-oaSupplierPayViewPage', function() {

            androidDateWidget($('#oa-paydate-oaSupplierPayViewPage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPaydate');
        });

        // 到货日期
        $(document).on('click', '#oa-arrivaldate-oaSupplierPayViewPage', function() {

            androidDateWidget($('#oa-arrivaldate-oaSupplierPayViewPage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectArrivaldate');
        });

        // 抽单日期
        $(document).on('click', '#oa-writeoffdate-oaSupplierPayViewPage', function() {

            androidDateWidget($('#oa-writeoffdate-oaSupplierPayViewPage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectWriteoffdate');
        });

        // 计算付款差额
        $(document).on('change', '#oa-arrivalamount-oaSupplierPayViewPage,#oa-payamount-oaSupplierPayViewPage,#oa-expenseamount-oaSupplierPayViewPage', computePayMargin);

        // 金额转换为大写
        $('#oa-payamount-oaSupplierPayViewPage').on('change', function(e) {

            var v = $(this).val();
            var big = moneyToUpper(v);

            $('#oa-upperpayamount-oaSupplierPayViewPage').val(big);
        });

    });
    --%>

    /**
     * 选择供应商
     */
    function selectSupplier(data) {

        $('#oa-supplierid2-oaSupplierPayViewPage').val(data.id + ':' + data.name);
        $('#oa-supplierid-oaSupplierPayViewPage').val(data.id);

        $('#oa-supplierid2-oaSupplierPayViewPage').blur();
        $('#oa-supplierid2-oaSupplierPayViewPage').change();

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

                $("#oa-advanceamount-oaSupplierPayViewPage").val(prepayamount);
                $("#oa-stockamount-oaSupplierPayViewPage").val(storageamount);
                $("#oa-receivableamount-oaSupplierPayViewPage").val(receivableamount);
                $("#oa-actingmatamount-oaSupplierPayViewPage").val(advanceamount);
                $("#oa-payableamount-oaSupplierPayViewPage").val(payableamount);
                $("#oa-totalamount-oaSupplierPayViewPage").val(totalamount);

            },
            error: function() {

                $("#oa-advanceamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-stockamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-receivableamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-actingmatamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-payableamount-oaSupplierPayViewPage").val('0.00');
                $("#oa-totalamount-oaSupplierPayViewPage").val('0.00');
            }
        });
    }

    /**
     * 选择付款银行
     */
    function selectBank(data) {

        $('#oa-paybank2-oaSupplierPayViewPage').val(data.id + ':' + data.name);
        $('#oa-paybank-oaSupplierPayViewPage').val(data.id);

        $('#oa-paybank2-oaSupplierPayViewPage').blur();
        $('#oa-paybank2-oaSupplierPayViewPage').change();
    }

    /**
     * 选择到票时间
     */
    function selectBilldate(data) {

        $('#oa-billdate-oaSupplierPayViewPage').val(data);
        $('#oa-billdate-oaSupplierPayViewPage').blur();
    }
    
    /**
     * 选择到货日期
     */
    function selectArrivaldate(data) {

        $('#oa-arrivaldate-oaSupplierPayViewPage').val(data);
        $('#oa-arrivaldate-oaSupplierPayViewPage').blur();
    }

    /**
     * 选择到货日期
     */
    function selectWriteoffdate(data) {

        $('#oa-writeoffdate-oaSupplierPayViewPage').val(data);
        $('#oa-writeoffdate-oaSupplierPayViewPage').blur();
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

            var val = $('#oa-payamount-oaSupplierPayViewPage').val();
            $('#oa-upperpayamount-oaSupplierPayViewPage').val(moneyToUpper(val));
        }, 100);

        // 到货金额 - 付款金额 - 收回费用
        var arrivalamount = formatterMoney($('#oa-arrivalamount-oaSupplierPayViewPage').val());
        var payamount = formatterMoney($('#oa-payamount-oaSupplierPayViewPage').val());
        var expenseamount = formatterMoney($('#oa-expenseamount-oaSupplierPayViewPage').val());

        var paymargin = parseFloat(arrivalamount) - parseFloat(payamount) - parseFloat(expenseamount);

        $('#oa-paymargin-oaSupplierPayViewPage').val(paymargin);
        return true;
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaSupplierPayViewPage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaSupplierPayViewPage").submit();
        var flag = $('#oa-form-oaSupplierPayViewPage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaSupplierPayViewPage').validate({
            debug: false
        });
        
        $('#oa-form-oaSupplierPayViewPage').submit(function(){

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
        <c:choose>
            <c:when test="${param.type eq 'handle'}">
                <h1>处理工作[${param.processid }]</h1>
            </c:when>
            <c:otherwise>
                <h1>查看工作[${param.processid }]</h1>
            </c:otherwise>
        </c:choose>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <form action="" id="oa-form-oaSupplierPayViewPage" method="post">
        <input type="hidden" name="pay.id" id="oa-id-oaSupplierPayViewPage" value="${param.id }"/>
        <input type="hidden" name="pay.oaid" id="oa-oaid-oaSupplierPayViewPage" value="${param.processid }"/>
        <input type="hidden" name="paydetail" id="oa-paydetail-oaSupplierPayViewPage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>供应商
                        <input type="text" class="required" name="pay.supplierid2" id="oa-supplierid2-oaSupplierPayViewPage" value="<c:if test='${not empty supplier }'><c:out value='${pay.supplierid }'/>:<c:out value='${supplier.name }'/></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="pay.supplierid" id="oa-supplierid-oaSupplierPayViewPage" value="${pay.supplierid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>收款银行
                        <input type="text" class="" name="pay.collectionbank" id="oa-collectionbank-oaSupplierPayViewPage" value="${pay.collectionbank}" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>银行账号
                        <input type="text" class="" name="pay.collectionbankno" id="oa-collectionbankno-oaSupplierPayViewPage" value="${pay.collectionbankno}" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款金额
                        <input type="number" class="" name="pay.payamount" id="oa-payamount-oaSupplierPayViewPage" value="${pay.payamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                        <input type="hidden" name="pay.upperpayamount" id="oa-upperpayamount-oaSupplierPayViewPage" value="${pay.upperpayamount }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款日期
                        <input type="text" class="dateISO" name="pay.paydate" id="oa-paydate-oaSupplierPayViewPage" value="${pay.paydate}" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款银行
                        <input type="text" class="" name="pay.paybank2" id="oa-paybank2-oaSupplierPayViewPage" value="<c:if test='${not empty bank }'><c:out value='${pay.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="pay.paybank" id="oa-paybank-oaSupplierPayViewPage" value="${pay.paybank }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>到货金额
                        <input type="number" class="" name="pay.arrivalamount" id="oa-arrivalamount-oaSupplierPayViewPage" value="${pay.arrivalamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款差额
                        <input type="number" class="" name="pay.paymargin" id="oa-paymargin-oaSupplierPayViewPage" value="${pay.paymargin }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>到货日期
                        <input type="text" class="dateISO" name="pay.arrivaldate" id="oa-arrivaldate-oaSupplierPayViewPage" value="${pay.arrivaldate}" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票金额
                        <input type="number" class="" name="pay.billamount" id="oa-billamount-oaSupplierPayViewPage" value="${pay.expenseamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>收回费用
                        <input type="number" class="" name="pay.expenseamount" id="oa-expenseamount-oaSupplierPayViewPage" value="${pay.billamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>抽单金额
                        <input type="number" class="" name="pay.writeoffamount" id="oa-writeoffamount-oaSupplierPayViewPage" value="${pay.writeoffamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>抽单日期
                        <input type="text" class="dateISO" name="pay.writeoffdate" id="oa-writeoffdate-oaSupplierPayViewPage" value="${pay.writeoffdate}" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>订单金额
                        <input type="number" class="required" name="pay.orderamount" id="oa-orderamount-oaSupplierPayViewPage" value="${pay.orderamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>预付金额
                        <input type="number" class="" name="pay.advanceamount" id="oa-advanceamount-oaSupplierPayViewPage" value="${pay.advanceamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>库存金额
                        <input type="number" class="" name="pay.stockamount" id="oa-stockamount-oaSupplierPayViewPage" value="${pay.stockamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>应收金额
                        <input type="number" class="" name="pay.receivableamount" id="oa-receivableamount-oaSupplierPayViewPage" value="${pay.receivableamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>代垫金额
                        <input type="number" class="" name="pay.actingmatamount" id="oa-actingmatamount-oaSupplierPayViewPage" value="${pay.actingmatamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>应付金额
                        <input type="number" class="" name="pay.payableamount" id="oa-payableamount-oaSupplierPayViewPage" value="${pay.payableamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>合计占用
                        <input type="number" class="" name="pay.totalamount" id="oa-totalamount-oaSupplierPayViewPage" value="${pay.totalamount }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="pay.remark" id="oa-remark-oaSupplierPayViewPage" readonly="readonly"><c:out value="${pay.remark }"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${param.type eq 'handle'}">
        <div id="oa-footer-oaSupplierPayViewPage" data-role="footer" data-position="fixed">
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
    </c:if>
</div>
</body>
</html>
