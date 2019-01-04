<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>新客户申请单(手机)</title>
<%@include file="/phone/common/include.jsp"%>
<script type="text/javascript">

    var settledayrequired = false;
    <c:if test="${settletype.type eq '1'}">
        settledayrequired = true;
    </c:if>

    var iscashrequired = false <c:if test="${empty customer }" > || true</c:if><c:if test="${empty customer.iscash and empty customer.islongterm }" > || true</c:if>;

    // 验证客户编号是否被占用
    $.validator.addMethod('customeridExisted', function(value, element, param) {

        value = value || '';
        if(value == '') {

            return this.optional(element) || true;
        }

        var data = {};

        $.ajax({
            type: 'post',
            url: 'oa/checkCustomerUsed.do',
            data: {customerid: value, id: param},
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

    }, $.validator.format('客户编码重复，请重新输入！'));

    // 验证客户名称是否重复
    $.validator.addMethod('customerNameExisted', function(value, element, param) {

        value = value || '';
        if(value == '') {

            return this.optional(element) || true;
        }

        var data = {};

        $.ajax({
            type: 'post',
            url: 'oa/checkCustomerNameUsed.do',
            data: {customername: value, id: param},
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

    }, $.validator.format('客户名称重复，请重新输入！'));

    // 结算方式为月结时，结算日必须选择
    jQuery.validator.addMethod('settledayrequired', function(value, element, param) {

        if(param == null || param == undefined || param.length == 0) {

            return true;
        }

        value = value || '';

        var settletype = $(param[0]).val() || '';
        if(settletype == '1' && value == '') {

            return false;
        }

        return true;

    }, $.validator.format('结算方式为月结时，该项目必填'));

    $(document).on('pageinit', function(e) {

        // 去除客户名称中的回车符
        $('#oa-customername-oacustomerHandlePage').on('change', function() {

            var v = $(this).val();
            v = v.replace(/\r\n/g, ' ').replace(/\r/g, ' ').replace(/\n/g, ' ');
            $(this).val(v);
        });

        // 去除客户名称中的回车符
        $('#oa-address-oacustomerHandlePage').on('change', function() {

            var v = $(this).val();
            v = v.replace(/\r\n/g, ' ').replace(/\r/g, ' ').replace(/\n/g, ' ');
            $(this).val(v);
        });
    });

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

        // 上级客户
        $('#oa-pcustomerid2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                checkType: '1',
                onSelect: 'selectPcustomer'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-pcustomerid-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 销售部门
        $('#oa-salesdeptid2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_DEPARTMENT_SELLER',
                checkType: '1',
                onSelect: 'selectSalesdept'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-salesdeptid-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 所属区域参照窗口
        $('#oa-salesarea2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                name: 't_oa_customer',
                col: 'salesarea',
                checkType: '1',
                onSelect: 'selectSalesarea'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-salesarea-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 所属分类
        $('#oa-customersort2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                name: 't_oa_customer',
                col: 'customersort',
                checkType: '1',
                onSelect: 'selectCustomersort'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-customersort-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 客户业务员
        $('#oa-salesuserid2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                name: 't_oa_customer',
                col: 'salesuserid',
                checkType: '1',
                onSelect: 'selectSalesuserid'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-salesuserid-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 默认内勤
        $('#oa-indoorstaff2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                name: 't_oa_customer',
                col: 'indoorstaff',
                checkType: '1',
                onSelect: 'selectIndoorstaff'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-indoorstaff-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 结算方式
        $('#oa-settletype2-oacustomerHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                name: 't_oa_customer',
                col: 'settletype',
                checkType: '1',
                onSelect: 'selectSettletype'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-settletype-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
            $('#oa-settletype3-oacustomerHandlePage').attr('value', (v || ':').split(':')[0]);
            $('#oa-settletype3-oacustomerHandlePage').change();
        });

        $('#oa-iscash-oacustomerHandlePage').off().on('change', function() {

            var thisvalue = $(this).val() || '0';
            if(thisvalue == '0') {

                $('#oa-islongterm-oacustomerHandlePage').val('1');
            } else {

                $('#oa-islongterm-oacustomerHandlePage').val('0');
            }
            iscashrequired = false;
            // 验证
            $('#oa-form-oacustomerHandlePage').validate({
                focusInvalid: true,
                debug: true,
                rules: {
                    'customer.settleday': {
                        required: settledayrequired
                    },
                    'customer.customerid': {
                        customeridExisted: '${param.id }'
                    },
                    'customer.customername': {
                        customerNameExisted: '${param.id }'
                    },
                    'customer.iscash': {
                        required: iscashrequired
                    },
                    'customer.islongterm': {
                        required: iscashrequired
                    }
                }
            });
            $('[name="customer.iscash"]').rules('remove', 'required');
            $('[name="customer.islongterm"]').rules('remove', 'required');
            $('[name="customer.iscash"]').next().remove();
            $('[name="customer.islongterm"]').next().remove();
            $('select').selectmenu('refresh', true);
        });

        $('#oa-islongterm-oacustomerHandlePage').off().on('change', function() {

            var thisvalue = $(this).val() || '0';
            if(thisvalue == '0') {

                $('#oa-iscash-oacustomerHandlePage').val('1');
            } else {

                $('#oa-iscash-oacustomerHandlePage').val('0');
            }
            iscashrequired = false;
            // 验证
            $('#oa-form-oacustomerHandlePage').validate({
                focusInvalid: true,
                debug: true,
                rules: {
                    'customer.settleday': {
                        required: settledayrequired
                    },
                    'customer.customerid': {
                        customeridExisted: '${param.id }'
                    },
                    'customer.iscash': {
                        required: iscashrequired
                    },
                    'customer.islongterm': {
                        required: iscashrequired
                    }
                }
            });
            $('[name="customer.iscash"]').rules('remove', 'required');
            $('[name="customer.islongterm"]').rules('remove', 'required');
            $('[name="customer.iscash"]').next().remove();
            $('[name="customer.islongterm"]').next().remove();
            $('select').selectmenu('refresh', true);
        });
    });

    /**
     * 选择上级客户
     * @param data
     * @returns {boolean}
     */
    function selectPcustomer(data) {

        $('#oa-pcustomerid-oacustomerHandlePage').val(data.id);
        $('#oa-pcustomerid2-oacustomerHandlePage').val(data.id + ':' + data.name);

        $('#oa-pcustomerid2-oacustomerHandlePage').blur();
        $('#oa-pcustomerid2-oacustomerHandlePage').change();
        return true;
    }

    /**
     * 选择销售部门
     * @param data
     * @returns {boolean}
     */
    function selectSalesdept(data) {

        $('#oa-salesdeptid-oacustomerHandlePage').val(data.id);
        $('#oa-salesdeptid2-oacustomerHandlePage').val(data.id + ':' + data.name);

        $('#oa-salesdeptid2-oacustomerHandlePage').blur();
        $('#oa-salesdeptid2-oacustomerHandlePage').change();
        return true;
    }

    /**
    * 选择所属区域
    * @param data
    * @returns {boolean}
    */
    function selectSalesarea(data) {

        $('#oa-salesarea-oacustomerHandlePage').val(data.id);
        $('#oa-salesarea2-oacustomerHandlePage').val(data.id + ':' + data.name);

        // salesarea
        $('#oa-salesarea2-oacustomerHandlePage').blur();
        $('#oa-salesarea2-oacustomerHandlePage').change();
        return true;
    }

    /**
     * 选择客户分类
     * @param data
     * @returns {boolean}
     */
    function selectCustomersort(data) {

        $('#oa-customersort-oacustomerHandlePage').val(data.id);
        $('#oa-customersort2-oacustomerHandlePage').val(data.id + ':' + data.name);

        // customersort
        $('#oa-customersort2-oacustomerHandlePage').blur();
        $('#oa-customersort2-oacustomerHandlePage').change();
        return true;
    }

    /**
     * 选择客户业务员
     * @param data
     * @returns {boolean}
     */
    function selectSalesuserid(data) {

        $('#oa-salesuserid-oacustomerHandlePage').val(data.id);
        $('#oa-salesuserid2-oacustomerHandlePage').val(data.id + ':' + data.name);

        // salesuserid
        $('#oa-salesuserid2-oacustomerHandlePage').blur();
        $('#oa-salesuserid2-oacustomerHandlePage').change();
        return true;
    }

    /**
     * 选择默认内勤
     * @param data
     * @returns {boolean}
     */
    function selectIndoorstaff(data) {

        $('#oa-indoorstaff-oacustomerHandlePage').val(data.id);
        $('#oa-indoorstaff2-oacustomerHandlePage').val(data.id + ':' + data.name);

        // indoorstaff
        $('#oa-indoorstaff2-oacustomerHandlePage').blur();
        $('#oa-indoorstaff2-oacustomerHandlePage').change();
        return true
    }

    /**
    * 选择结算方式
    * @param data
    * @returns {boolean}
    */
    function selectSettletype(data) {

        $('#oa-settletype-oacustomerHandlePage').val(data.id);
        $('#oa-settletype2-oacustomerHandlePage').val(data.id + ':' + data.name);
        $('#oa-settletype3-oacustomerHandlePage').val(data.type);

        // settletype
        $('#oa-settletype2-oacustomerHandlePage').blur();
        $('#oa-settletype2-oacustomerHandlePage').change();

        if(data.type == '1') {

            settledayrequired = true;

            $('#oa-iscash-oacustomerHandlePage').val('0');
            $('#oa-iscash-oacustomerHandlePage').change();
            $('select').selectmenu('refresh', true);

        } else {

            settledayrequired = false;

            $('#oa-iscash-oacustomerHandlePage').val('1');
            $('#oa-iscash-oacustomerHandlePage').change();
            $('select').selectmenu('refresh', true);

            $('[name="customer.settleday"]').rules('remove', 'required');
        }

        return true;
    }

    /**
     * 分配品牌业务员
     */
    function allotPSN() {

        var personids = new Array();
        var companies = new Array();
        $('#oa-form-oacustomerAllotPSNPage .personid').each(function(i, n) {

            var v = $(this).val() || '';
            if(v != '') {

                personids.push(v);
            }
        });
        $('#oa-form-oacustomerAllotPSNPage .company').each(function(i, n) {

            var v = $(this).val() || '';
            if(v != '') {

                companies.push(v);
            }
        });

        if(personids.length == 0) {

            alertMsg('请选择人员！');
            return false;
        }

        $('#oa-personids-oacustomerAllotPSNPage').val(personids.join(','));
        $('#oa-company-oacustomerAllotPSNPage').val(companies.join(','));

        $('#oa-form-oacustomerAllotPSNPage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                url: 'oa/allotCustomerToPsn.do',
                //beforeSubmit: showRequest,
                success: function(data) {

                    var json = $.parseJSON(data);
                    if(json.flag) {

                        alertMsg('分配成功');
                        $('#oa-return-oacustomerAllotPSNPage').trigger('click');
                    }
                }
            });

            return false; //此处必须返回false，阻止常规的form提交

        }).submit();

        return false;
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oacustomerHandlePage').validate({
            focusInvalid: true,
            debug: true,
            rules: {
                'customer.settleday': {
                    required: settledayrequired
                },
                'customer.customerid': {
                    customeridExisted: '${param.id }'
                },
                'customer.customername': {
                    customerNameExisted: '${param.id }'
                },
                'customer.iscash': {
                    required: iscashrequired
                },
                'customer.islongterm': {
                    required: iscashrequired
                }
            }
        });

        if(!settledayrequired) {

            $('[name="customer.settleday"]').rules('remove', 'required');
        }

        $("#oa-form-oacustomerHandlePage").submit();
        var flag = $('#oa-form-oacustomerHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oacustomerHandlePage').validate({
            debug: false
        });

        $('#oa-form-oacustomerHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                url: 'oa/editOaCustomer.do',
                //beforeSubmit: showRequest,
                success: function(data) {

                    var json = $.parseJSON(data);
                    if(json.flag) {

                        call(json.backid);
                    }
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
    <form action="" id="oa-form-oacustomerHandlePage" method="post">
        <input type="hidden" name="customer.id" id="oa-id-oacustomerHandlePage" value="${param.id }"/>
        <input type="hidden" name="customer.oaid" id="oa-oaid-oacustomerHandlePage" value="${param.processid }"/>
        <textarea name="customerBrandJSON" style="display: none;"><c:out value="${brands }"/></textarea>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>客户信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户编号
                        <input type="text" class="" name="customer.customerid" id="oa-customerid-oacustomerHandlePage" value="${customer.customerid }" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户名称<font color="#F00">*</font>
                        <textarea class="required" name="customer.customername" id="oa-customername-oacustomerHandlePage" maxlength="33"><c:out value="${customer.customername}"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>上级客户
                        <input type="text" class="" name="customer.pcustomerid2" id="oa-pcustomerid2-oacustomerHandlePage" maxlength="33" data-clear-btn="true" readonly="readonly" value="<c:if test="${not empty pcustomer}"><c:out value="${pcustomer.id }" />:<c:out value="${pcustomer.name }" /></c:if>"/>
                        <input type="hidden" name="customer.pcustomerid" id="oa-pcustomerid-oacustomerHandlePage" value="${customer.pcustomerid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>销售部门
                        <input type="text" class="" name="customer.salesdeptid2" id="oa-salesdeptid2-oacustomerHandlePage" value="<c:if test="${not empty salesdept}"><c:out value="${salesdept.id }" />:<c:out value="${salesdept.name }" /></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customer.salesdeptid" id="oa-salesdeptid-oacustomerHandlePage" value="${customer.salesdeptid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>助记符
                        <input type="text" class="" name="customer.shortcode" id="oa-shortcode-oacustomerHandlePage" value="${customer.shortcode }" data-clear-btn="true" maxlength="20"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>营业执照号
                        <input type="text" class="" name="customer.licenseno" id="oa-licenseno-oacustomerHandlePage" value="${customer.licenseno }" data-clear-btn="true" maxlength="50"/>
                    </label>
                </div>
                <%--
                <div class="ui-field-contain">
                    <label>客户简称<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.shortname" id="oa-shortname-oacustomerHandlePage" value="${customer.shortname }" data-clear-btn="true"maxlength="50"/>
                    </label>
                </div>
                --%>
                <div class="ui-field-contain">
                    <label>联系人<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.contact" id="oa-contact-oacustomerHandlePage" value="${customer.contact }" data-clear-btn="true" maxlength="20"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>联系电话<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.mobile" id="oa-mobile-oacustomerHandlePage" value="${customer.mobile }" data-clear-btn="true" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>详细地址<font color="#F00">*</font>
                        <textarea class="required" name="customer.address" id="customer.address"><c:out value="${customer.address}"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属区域<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.salesarea2" id="oa-salesarea2-oacustomerHandlePage" value="<c:if test="${not empty salesarea}"><c:out value="${salesarea.id }" />:<c:out value="${salesarea.name }" /></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customer.salesarea" id="oa-salesarea-oacustomerHandlePage" value="${customer.salesarea }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属分类<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.customersort2" id="oa-customersort2-oacustomerHandlePage" value="<c:if test="${not empty customersort}"><c:out value="${customersort.id }" />:<c:out value="${customersort.name }" /></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customer.customersort" id="oa-customersort-oacustomerHandlePage" value="${customer.customersort }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>默认价格套<font color="#F00">*</font>
                        <select class="required" name="customer.pricesort" id="oa-pricesort-oacustomerHandlePage" sdata="${customer.pricesort }">
                            <option></option>
                            <c:forEach items="${priceList}" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户业务员<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.salesuserid2" id="oa-salesuserid2-oacustomerHandlePage" value="<c:if test="${not empty salesuser}"><c:out value="${salesuser.id }" />:<c:out value="${salesuser.name }" /></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customer.salesuserid" id="oa-salesuserid-oacustomerHandlePage" value="${customer.salesuserid }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>默认内勤<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.indoorstaff2" id="oa-indoorstaff2-oacustomerHandlePage" value="<c:if test="${not empty indoorstaff}"><c:out value="${indoorstaff.id }" />:<c:out value="${indoorstaff.name }" /></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customer.indoorstaff" id="oa-indoorstaff-oacustomerHandlePage" value="${customer.indoorstaff }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>促销分类<font color="#F00">*</font>
                        <select class="required" name="customer.promotionsort" id="oa-promotionsort-oacustomerHandlePage" sdata="${customer.promotionsort }">
                            <option></option>
                            <c:forEach items="${promotionsortList }" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>信用等级<font color="#F00">*</font>
                        <select class="required" name="customer.creditrating" id="oa-creditrating-oacustomerHandlePage" sdata="${customer.creditrating }">
                            <option value=""></option>
                            <c:forEach items="${creditratingList }" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>信用额度<font color="#F00">*</font>
                        <input type="number" class="required" name="customer.credit" id="oa-credit-oacustomerHandlePage" value="${customer.credit }" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>结算方式<font color="#F00">*</font>
                        <input type="text" class="required" name="customer.settletype2" id="oa-settletype2-oacustomerHandlePage" value="<c:if test="${not empty settletype}"><c:out value="${settletype.id }" />:<c:out value="${settletype.name }" /></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customer.settletype" id="oa-settletype-oacustomerHandlePage" value="${customer.settletype }" />
                        <input type="hidden" id="oa-settletype3-oacustomerHandlePage" value="${customer.settletype }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>结算日
                        <select name="customer.settleday" id="oa-settleday-oacustomerHandlePage" sdata="${customer.settleday }">
                            <option value=""></option>
                            <c:forEach items="${dayList}" var="day">
                                <option value="<c:out value='${day }'/>">${day }</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>核销方式<font color="#F00">*</font>
                        <select class="required" name="customer.canceltype" id="oa-canceltype-oacustomerHandlePage" sdata="${customer.canceltype }">
                            <option></option>
                            <c:forEach items="${canceltypeList }" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>是否现款
                        <select name="customer.iscash" id="oa-iscash-oacustomerHandlePage" sdata="${customer.iscash }">
                            <option></option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>是否账期
                        <select name="customer.islongterm" id="oa-islongterm-oacustomerHandlePage" sdata="${customer.islongterm }">
                            <option></option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <a href="oa/oacustomerAllotPSNPage.do?id=${param.processid }&type=handle" class="ui-btn ui-corner-all">分配品牌业务员</a>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="customer.remark" id="oa-remark-oacustomerHandlePage" maxlength="166"><c:out value="${customer.remark}"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oacustomerHandlePage" data-role="footer" data-position="fixed">
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
