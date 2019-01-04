<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>客户费用支付申请单(手机)</title>
<%@include file="/phone/common/include.jsp"%>
<script type="text/javascript">


    localStorage.setItem('details', '${list }');

    // 验证客户编号是否被占用
    $.validator.addMethod('accessNoValid', function(value, element, param) {

        value = value || '';
        if(value == '') {

            return this.optional(element) || true;
        }

        var data = {};

        $.ajax({
            type: 'post',
            url: 'act/selectProcess.do',
            data: {id: value},
            dataType: 'json',
            async: false,
            success: function(json) {

                data = json;
            }
        });

        if(data.flag) {

            return this.optional(element) || false;
        }

        return this.optional(element) || true;

    }, $.validator.format('该客户编号已经存在'));

    $(document).on('pagebeforecreate', '#main', function() {

        $('#oa-relateoaid-oaCustomerPayHandlePage').on('change', function(e) {

            var v = $(this).val() || '';
            var d = $(this);
            if(v == '') {

                return true;
            }

            $.ajax({
                type: 'post',
                url: 'act/selectProcess.do',
                data: {id: v},
                dataType: 'json',
                success: function(json) {

                    if(json.process == null) {

                        alertMsg('请输入正确的流程编号！');
                        d.val('');
                        d.focus();
                        return true;
                    }

                    if(json.process.status != '9') {

                        alertMsg('请输入已经结束的通路单编号！');
                        d.val('');
                        d.focus();
                        return true;
                    }

                    $.ajax({
                        type: 'post',
                        url: 'oa/selectAccessInfo.do',
                        data: {id: json.process.businessid },
                        dataType: 'json',
                        success: function(json2) {

                            if(json2.access == null || json2.access.oaid != json.process.id ) {

                                alertMsg('请输入已经结束的通路单编号！');
                                d.val('');
                                d.focus();
                                return true;
                            }

                            var access = json2.access;
                            var supplierid = access.supplierid;
                            var customerid = access.customerid;

                            // 客户
                            $.ajax({
                                type: 'post',
                                url: 'oa/common/selectCustomerInfo.do',
                                data: {id: customerid},
                                dataType: 'json',
                                success: function(json3) {

                                    var customer = json3.customer;

                                    setCustomer(customer);
                                }
                            });

                            // 供应商
                            $.ajax({
                                type: 'post',
                                url: 'oa/common/selectSupplierInfo.do',
                                data: {id: supplierid},
                                dataType: 'json',
                                success: function(json4) {

                                    var supplier = json4.supplier;

                                    setSupplier(supplier)
                                }
                            })

                        }
                    });
                },
                error: function(e) {

                    alertMsg('请求数据失败！');
                    d.val('');
                    d.focus();
                    return true;
                }
            });

            return true;
        });

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
        $('#oa-supplierid2-oaCustomerPayHandlePage').on('click', function() {

            androidWidget({
                type: 'supplierWidget',
                onSelect: 'selectSupplier'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-supplierid-oaCustomerPayHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 所属部门
        $('#oa-deptid2-oaCustomerPayHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_DEPATMENT',
                onSelect: 'selectDept'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-deptid-oaCustomerPayHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 客户
        $('#oa-customerid2-oaCustomerPayHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_SALES_CUSTOMER_OPEN',
                onSelect: 'selectCustomer'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-customerid-oaCustomerPayHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 付款银行
        $('#oa-paybank2-oaCustomerPayHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_FINANCE_BANK',
                onSelect: 'selectBank'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-paybank-oaCustomerPayHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 费用科目
        $('#oa-expensesort2-oaCustomerPayHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
                onSelect: 'selectExpensesort'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-expensesort-oaCustomerPayHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 到票日期
        $(document).on('click', '#oa-billdate-oaCustomerPayHandlePage', function() {

            androidDateWidget($('#oa-billdate-oaCustomerPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectBilldate');
        });

        // 付款日期
        $(document).on('click', '#oa-paydate-oaCustomerPayHandlePage', function() {

            androidDateWidget($('#oa-paydate-oaCustomerPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPaydate');
        });

        // 摊销时间开始
        $(document).on('click', '#oa-sharebegindate-oaCustomerPayHandlePage', function() {

            androidDateWidget($('#oa-sharebegindate-oaCustomerPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectSharebegindate');
        });

        // 摊销时间开始
        $(document).on('click', '#oa-shareenddate-oaCustomerPayHandlePage', function() {

            androidDateWidget($('#oa-shareenddate-oaCustomerPayHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectShareenddate');
        });

        // 金额转换为大写
        $('#oa-payamount-oaCustomerPayHandlePage').on('change', function(e) {

            var v = $(this).val();
            var big = moneyToUpper(v);

            $('#oa-upperpayamount-oaCustomerPayHandlePage').val(big);
        });
    });

    /**
     *
     */
    function setCustomer(customer) {

        if(customer != null) {

            $('#oa-customerid2-oaCustomerPayHandlePage').val(customer.id + ':' + customer.name);
            $('#oa-customerid-oaCustomerPayHandlePage').val(customer.id);

            $('#oa-customerid2-oaCustomerPayHandlePage + .error').remove();
        } else {

            $('#oa-customerid2-oaCustomerPayHandlePage').val('');
            $('#oa-customerid-oaCustomerPayHandlePage').val('');
        }
    }

    /**
     *
     */
    function setSupplier(supplier) {

        if(supplier != null) {

            $('#oa-supplierid2-oaCustomerPayHandlePage').val(supplier.id + ':' + supplier.name);
            $('#oa-supplierid-oaCustomerPayHandlePage').val(supplier.id);

            $('#oa-supplierid2-oaCustomerPayHandlePage + .error').remove();

            var deptid = supplier.buydeptid;

            // 部门
            $.ajax({
                type: 'post',
                url: 'oa/common/selectDeptInfo.do',
                data: {id: deptid},
                dataType: 'json',
                success: function(json5) {

                    var dept = json5.dept;

                    if(dept != null) {

                        $('#oa-deptid2-oaCustomerPayHandlePage').val(dept.id + ':' + dept.name);
                        $('#oa-deptid-oaCustomerPayHandlePage').val(dept.id);

                        $('#oa-deptid2-oaCustomerPayHandlePage + .error').remove();
                    } else {

                        $('#oa-deptid2-oaCustomerPayHandlePage').val('');
                        $('#oa-deptid-oaCustomerPayHandlePage').val('');
                    }
                }
            });
        } else {

            $('#oa-supplierid2-oaCustomerPayHandlePage').val('');
            $('#oa-supplierid-oaCustomerPayHandlePage').val('');

            $('#oa-deptid2-oaCustomerPayHandlePage').val('');
            $('#oa-deptid-oaCustomerPayHandlePage').val('');
        }
    }

    /**
     * 选择供应商
     */
    function selectSupplier(data) {

        setSupplier(data);

        $('#oa-supplierid2-oaCustomerPayHandlePage').blur();
        $('#oa-supplierid2-oaCustomerPayHandlePage').change();
    }

    /**
     * 选择部门
     */
    function selectDept(data) {

        $('#oa-deptid2-oaCustomerPayHandlePage').val(data.id + ':' + data.name);
        $('#oa-deptid-oaCustomerPayHandlePage').val(data.id);

        $('#oa-deptid2-oaCustomerPayHandlePage').blur();
        $('#oa-deptid2-oaCustomerPayHandlePage').change();
    }

    /**
     * 选择客户
     */
    function selectCustomer(data) {

        $('#oa-customerid2-oaCustomerPayHandlePage').val(data.id + ':' + data.name);
        $('#oa-customerid-oaCustomerPayHandlePage').val(data.id);

        $('#oa-customerid2-oaCustomerPayHandlePage').blur();
        $('#oa-customerid2-oaCustomerPayHandlePage').change();
    }

    /**
     * 选择付款银行
     */
    function selectBank(data) {

        $('#oa-paybank2-oaCustomerPayHandlePage').val(data.id + ':' + data.name);
        $('#oa-paybank-oaCustomerPayHandlePage').val(data.id);

        $('#oa-paybank2-oaCustomerPayHandlePage').blur();
        $('#oa-paybank2-oaCustomerPayHandlePage').change();
    }

    /**
     * 选择费用科目
     */
    function selectExpensesort(data) {

        $('#oa-expensesort2-oaCustomerPayHandlePage').val(data.id + ':' + data.name);
        $('#oa-expensesort-oaCustomerPayHandlePage').val(data.id);

        $('#oa-expensesort2-oaCustomerPayHandlePage').blur();
        $('#oa-expensesort2-oaCustomerPayHandlePage').change();
    }

    /**
     * 选择到票时间
     */
    function selectBilldate(data) {

        $('#oa-billdate-oaCustomerPayHandlePage').val(data);
        $('#oa-billdate-oaCustomerPayHandlePage').blur();
    }

    /**
     * 选择付款时间
     */
    function selectPaydate(data) {

        $('#oa-paydate-oaCustomerPayHandlePage').val(data);
        $('#oa-paydate-oaCustomerPayHandlePage').blur();
    }

    /**
     * 选择摊销时间（开始）
     */
    function selectSharebegindate(data) {

        $('#oa-sharebegindate-oaCustomerPayHandlePage').val(data);
        $('#oa-sharebegindate-oaCustomerPayHandlePage').blur();

        // TODO
    }

    /**
     * 选择摊销时间（结束）
     */
    function selectShareenddate(data) {

        $('#oa-shareenddate-oaCustomerPayHandlePage').val(data);
        $('#oa-shareenddate-oaCustomerPayHandlePage').blur();

        // TODO
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaCustomerPayHandlePage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaCustomerPayHandlePage").submit();
        var flag = $('#oa-form-oaCustomerPayHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaCustomerPayHandlePage').validate({
            debug: false
        });

        $('#oa-customerpaydetail-oaCustomerPayHandlePage').val(localStorage.getItem('details'));
        
        $('#oa-form-oaCustomerPayHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                //beforeSubmit: showRequest,
                <c:choose>
                    <c:when test="${empty pay or empty pay.id}">
                        url: 'oa/addOaCustomerPay.do',
                    </c:when>
                    <c:otherwise>
                        url: 'oa/editOaCustomerPay.do',
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
    <form action="" id="oa-form-oaCustomerPayHandlePage" method="post">
        <input type="hidden" name="customerpay.id" id="oa-id-oaCustomerPayHandlePage" value="${param.id }"/>
        <input type="hidden" name="customerpay.oaid" id="oa-oaid-oaCustomerPayHandlePage" value="${param.processid }"/>
        <input type="hidden" name="customerpaydetail" id="oa-customerpaydetail-oaCustomerPayHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>已批OA号
                        <input type="number" precision="0" name="customerpay.relateoaid" id="oa-relateoaid-oaCustomerPayHandlePage" value="${customerpay.relateoaid }" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属供应商<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.supplierid2" id="oa-supplierid2-oaCustomerPayHandlePage" value="<c:if test='${not empty supplier }'><c:out value='${customerpay.supplierid }'/>:<c:out value='${supplier.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerpay.supplierid" id="oa-supplierid-oaCustomerPayHandlePage" value="${customerpay.supplierid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属部门<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.deptid2" id="oa-deptid2-oaCustomerPayHandlePage" value="<c:if test='${not empty dept }'><c:out value='${customerpay.deptid }'/>:<c:out value='${dept.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerpay.deptid" id="oa-deptid-oaCustomerPayHandlePage" value="${customerpay.deptid }" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.customerid2" id="oa-customerid2-oaCustomerPayHandlePage" value="<c:if test='${not empty customer }'><c:out value='${customerpay.customerid }'/>:<c:out value='${customer.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerpay.customerid" id="oa-customerid-oaCustomerPayHandlePage" value="${customerpay.customerid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户银行
                        <input type="text" name="customerpay.collectionbank" id="oa-collectionbank-oaCustomerPayHandlePage" value="${customerpay.collectionbank }" data-clear-btn="true" maxlength="66"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户账号
                        <input type="number" precision="0" name="customerpay.collectionbankno" id="oa-collectionbankno-oaCustomerPayHandlePage" value="${customerpay.collectionbankno }" data-clear-btn="true" maxlength="25"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款金额<font color="#F00">*</font>
                        <input type="number" class="required" name="customerpay.payamount" id="oa-payamount-oaCustomerPayHandlePage" value="${customerpay.payamount }" data-clear-btn="true" maxlength="15"/>
                        <input type="hidden" name="customerpay.upperpayamount" id="oa-upperpayamount-oaCustomerPayHandlePage" value="${customerpay.upperpayamount }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款银行
                        <input type="text" name="customerpay.paybank2" id="oa-paybank2-oaCustomerPayHandlePage" value="<c:if test='${not empty bank }'><c:out value='${customerpay.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerpay.paybank" id="oa-paybank-oaCustomerPayHandlePage" value="${customerpay.paybank }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款用途
                        <select name="customerpay.payuse" id="oa-payuse-oaCustomerPayHandlePage" sdata="${customerpay.payuse }">
                            <option></option>
                            <c:forEach items="${paytype }" var="pay">
                                <option value="${pay.code }"><c:out value="${pay.codename }"></c:out></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>费用科目<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.expensesort2" id="oa-expensesort2-oaCustomerPayHandlePage" value="<c:if test='${not empty expensesort }'><c:out value='${customerpay.expensesort }'/>:<c:out value='${expensesort.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerpay.expensesort" id="oa-expensesort-oaCustomerPayHandlePage" value="${customerpay.expensesort }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票种类
                        <select name="customerpay.billtype" id="oa-billtype-oaCustomerPayHandlePage" sdata="${customerpay.billtype }">
                            <option></option>
                            <c:forEach items="${invoicetype }" var="invoice">
                                <option value="${invoice.code }"><c:out value="${invoice.codename }"></c:out></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>到票时间
                        <input type="text" name="customerpay.billdate" id="oa-billdate-oaCustomerPayHandlePage" value="${customerpay.billdate }" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票金额
                        <input type="number" name="customerpay.billamount" id="oa-billamount-oaCustomerPayHandlePage" value="${customerpay.billamount }" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款日期
                        <input type="text" name="customerpay.paydate" id="oa-paydate-oaCustomerPayHandlePage" value="${customerpay.paydate }" data-clear-btn="true"/>
                    </label>
                </div>
                <%--
                <div class="ui-field-contain">
                    <label>摊销时间
                        <input type="text" name="customerpay.sharebegindate" id="oa-sharebegindate-oaCustomerPayHandlePage" value="${customerpay.sharebegindate }" data-clear-btn="true"/>
                        <input type="text" name="customerpay.shareenddate" id="oa-shareenddate-oaCustomerPayHandlePage" value="${customerpay.shareenddate }" data-clear-btn="true"/>
                    </label>
                </div>
                --%>
                <input type="hidden" name="customerpay.sharebegindate" id="oa-sharebegindate-oaCustomerPayHandlePage" value="${customerpay.sharebegindate }" data-clear-btn="true"/>
                <input type="hidden" name="customerpay.shareenddate" id="oa-shareenddate-oaCustomerPayHandlePage" value="${customerpay.shareenddate }" data-clear-btn="true"/>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="customerpay.remark" id="oa-remark-oaCustomerPayHandlePage"><c:out value="${customerpay.remark }"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oaCustomerPayHandlePage" data-role="footer" data-position="fixed">
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
