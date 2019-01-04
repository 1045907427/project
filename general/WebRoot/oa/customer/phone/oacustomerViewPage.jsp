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

    });

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oacustomerViewPage').validate({
            focusInvalid: true,
            debug: true,
            rules: {
                'customer.settleday': {
                    required: settledayrequired
                }
            }
        });

        if(!settledayrequired) {

            $('[name="customer.settleday"]').rules('remove', 'required');
        }

        $("#oa-form-oacustomerViewPage").submit();
        var flag = $('#oa-form-oacustomerViewPage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oacustomerViewPage').validate({
            debug: false
        });

        $('#oa-form-oacustomerViewPage').submit(function(){

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
    <form action="" id="oa-form-oacustomerViewPage" method="post">
        <input type="hidden" name="customer.id" id="oa-id-oacustomerViewPage" value="${param.id }"/>
        <input type="hidden" name="customer.oaid" id="oa-oaid-oacustomerViewPage" value="${param.processid }"/>
        <textarea name="customerBrandJSON" style="display: none;"><c:out value="${brands }"/></textarea>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>客户信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户编号
                        <input type="text" customeridExisted="${param.id }" name="customer.customerid" id="oa-customerid-oacustomerViewPage" value="${customer.customerid }" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户名称
                        <textarea name="customer.customername" id="oa-customername-oacustomerViewPage" maxlength="33" readonly="readonly"><c:out value="${customer.customername}"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>上级客户
                        <input type="text" class="" name="customer.pcustomerid2" id="oa-pcustomerid2-oacustomerViewPage" maxlength="33" data-clear-btn="false" readonly="readonly" value="<c:if test="${not empty pcustomer}"><c:out value="${pcustomer.id }" />:<c:out value="${pcustomer.name }" /></c:if>"/>
                        <input type="hidden" name="customer.pcustomerid" id="oa-pcustomerid-oacustomerViewPage" value="${customer.pcustomerid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>销售部门
                        <input type="text" class="" name="customer.salesdeptid2" id="oa-salesdeptid2-oacustomerViewPage" value="<c:if test="${not empty salesdept}"><c:out value="${salesdept.id }" />:<c:out value="${salesdept.name }" /></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customer.salesdeptid" id="oa-salesdeptid-oacustomerViewPage" value="${customer.salesdeptid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>助记符
                        <input type="text" name="customer.shortcode" id="oa-shortcode-oacustomerViewPage" value="${customer.shortcode }" data-clear-btn="false" maxlength="20" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>营业执照号
                        <input type="text" class="" name="customer.licenseno" id="oa-licenseno-oacustomerViewPage" value="${customer.licenseno }" data-clear-btn="false" maxlength="15" readonly="readonly"/>
                    </label>
                </div>
                <%--
                <div class="ui-field-contain">
                    <label>客户简称
                        <input type="text" name="customer.shortname" id="oa-shortname-oacustomerViewPage" value="${customer.shortname }" data-clear-btn="false"maxlength="50" readonly="readonly"/>
                    </label>
                </div>
                --%>
                <div class="ui-field-contain">
                    <label>联系人
                        <input type="text" name="customer.contact" id="oa-contact-oacustomerViewPage" value="${customer.contact }" data-clear-btn="false" maxlength="20" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>联系电话
                        <input type="text" name="customer.mobile" id="oa-mobile-oacustomerViewPage" value="${customer.mobile }" data-clear-btn="false" maxlength="50" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>详细地址
                        <textarea name="customer.address" id="customer.address" readonly="readonly"><c:out value="${customer.address}"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属区域
                        <input type="text" name="customer.salesarea2" id="oa-salesarea2-oacustomerViewPage" value="<c:if test="${not empty salesarea}"><c:out value="${salesarea.id }" />:<c:out value="${salesarea.name }" /></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customer.salesarea" id="oa-salesarea-oacustomerViewPage" value="${customer.salesarea }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属分类
                        <input type="text" name="customer.customersort2" id="oa-customersort2-oacustomerViewPage" value="<c:if test="${not empty customersort}"><c:out value="${customersort.id }" />:<c:out value="${customersort.name }" /></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customer.customersort" id="oa-customersort-oacustomerViewPage" value="${customer.customersort }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>默认价格套
                        <select sdata="${customer.pricesort }" disabled="disabled">
                            <option></option>
                            <c:forEach items="${priceList}" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customer.pricesort" id="oa-pricesort-oacustomerViewPage" value="${customer.pricesort }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户业务员
                        <input type="text" name="customer.salesuserid2" id="oa-salesuserid2-oacustomerViewPage" value="<c:if test="${not empty salesuser}"><c:out value="${salesuser.id }" />:<c:out value="${salesuser.name }" /></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customer.salesuserid" id="oa-salesuserid-oacustomerViewPage" value="${customer.salesuserid }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>默认内勤
                        <input type="text" name="customer.indoorstaff2" id="oa-indoorstaff2-oacustomerViewPage" value="<c:if test="${not empty indoorstaff}"><c:out value="${indoorstaff.id }" />:<c:out value="${indoorstaff.name }" /></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customer.indoorstaff" id="oa-indoorstaff-oacustomerViewPage" value="${customer.indoorstaff }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>促销分类
                        <select sdata="${customer.promotionsort }" disabled="disabled">
                            <option></option>
                            <c:forEach items="${promotionsortList }" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customer.promotionsort" id="oa-promotionsort-oacustomerViewPage" value="${customer.promotionsort }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>信用等级
                        <select sdata="${customer.creditrating }" disabled="disabled">
                            <option value=""></option>
                            <c:forEach items="${creditratingList }" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customer.creditrating" id="oa-creditrating-oacustomerViewPage" value="${customer.creditrating }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>信用额度
                        <input type="number" name="customer.credit" id="oa-credit-oacustomerViewPage" value="${customer.credit }" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>结算方式
                        <input type="text" name="customer.settletype2" id="oa-settletype2-oacustomerViewPage" value="<c:if test="${not empty settletype}"><c:out value="${settletype.id }" />:<c:out value="${settletype.name }" /></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customer.settletype" id="oa-settletype-oacustomerViewPage" value="${customer.settletype }" />
                        <input type="hidden" id="oa-settletype3-oacustomerViewPage" value="${customer.settletype }" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>结算日
                        <select sdata="${customer.settleday }" disabled="disabled">
                            <option value=""></option>
                            <c:forEach items="${dayList}" var="day">
                                <option value="<c:out value='${day }'/>">${day }</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customer.settleday" id="oa-settleday-oacustomerViewPage" value="${customer.settleday }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>核销方式
                        <select sdata="${customer.canceltype }" disabled="disabled">
                            <option></option>
                            <c:forEach items="${canceltypeList }" var="list">
                                <option value="<c:out value='${list.code }'/>">${list.codename }</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customer.canceltype" id="oa-canceltype-oacustomerViewPage" value="${customer.canceltype }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>是否现款
                        <select sdata="${customer.iscash }" disabled="disabled">
                            <option></option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                        <input type="hidden" name="customer.iscash" id="oa-iscash-oacustomerViewPage" value="${customer.iscash }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>是否账期
                        <select sdata="${customer.islongterm }" disabled="disabled">
                            <option></option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                        <input type="hidden" name="customer.islongterm" id="oa-islongterm-oacustomerViewPage" value="${customer.islongterm }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <a href="oa/oacustomerAllotPSNPage.do?id=${param.processid }" class="ui-btn ui-corner-all">分配品牌业务员</a>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="customer.remark" id="oa-remark-oacustomerViewPage" maxlength="166" readonly="readonly"><c:out value="${customer.remark}"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
