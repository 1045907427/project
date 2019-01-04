<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>品牌费用申请单（支付）(手机)</title>
    <style type="text/css">
        .warnning {
            color: #F00;
        }
    </style>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">

        var feeList = $.parseJSON('${list }');
        localStorage.setItem('details', '${list }');

        // 显示主页面时，reload明细数据
        $(document).on('pageshow', '#main', function(e) {

            refreshFeeList(e);

            numberPrecision();
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

        function refreshFeeList(e){

            var feeList = $.parseJSON(localStorage.getItem("details"));

            var html = new Array();

            for(var i in feeList) {

                var feedetail = feeList[i];

                html.push('<li>');
                html.push('<a href="oa/brandfee/phone/oaBrandFeeDetailPage.jsp?" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>申请事由:</th>');
                html.push('<td><span>' + feedetail.reason + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>工厂投入:</th>');
                html.push('<td><span precision="2">' + feedetail.factoryamount + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>客户:</th>');
                html.push('<td>' + feedetail.customerid + ':' + feedetail.customername + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>备注:</th>');
                html.push('<td><span>' + feedetail.remark + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-detail-oaBrandFeeHandlePage').find(':not(:eq(0))').remove();
            $('#oa-detail-oaBrandFeeHandlePage').append(html.join(''));

            try {

                $('#oa-detail-oaBrandFeeHandlePage').listview('refresh');
            } catch(e) {}

        }

        /**
         * 小数位截取
         */
        function numberPrecision() {

            $('span[precision]').each(function(index) {

                var text = $(this).text();

                var precision = $(this).attr('precision');

                if(text != undefined && text != '' && text != null) {

                    text = parseFloat(text).toFixed(parseInt(precision));
                }

                $(this).text(text);
            });
        }

        $(function() {

            // 供应商
            $('#oa-supplierid2-oaBrandFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_BUY_SUPPLIER',
                    onSelect: 'selectSupplier'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-supplierid-oaBrandFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 部门
            $('#oa-deptid2-oaBrandFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
                    onSelect: 'selectDept'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-deptid-oaBrandFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 品牌
            $('#oa-brandid2-oaBrandFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_GOODS_BRAND',
                    onSelect: 'selectBrand'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-brandid-oaBrandFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 申请日期
            $(document).on('click', '#oa-businessdate-oaBrandFeeHandlePage', function() {

                androidDateWidget($('#oa-businessdate-oaBrandFeeHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectBusinessdate');
            });

            // 归还日期
            $(document).on('click', '#oa-returndate-oaBrandFeeHandlePage', function() {

                androidDateWidget($('#oa-returndate-oaBrandFeeHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectReturndate');
            });

            // 费用科目
            $('#oa-expensesort2-oaBrandFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
                    onSelect: 'selectExpensesort'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-expensesort-oaBrandFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 付款银行
            $('#oa-paybank2-oaBrandFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_FINANCE_BANK',
                    onSelect: 'selectBank'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-paybank-oaBrandFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

        });

        /**
         * 选择供应商
         */
        function selectSupplier(data) {

            $('#oa-supplierid2-oaBrandFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-supplierid-oaBrandFeeHandlePage').val(data.id);

            $('#oa-supplierid2-oaBrandFeeHandlePage').blur();
            $('#oa-supplierid2-oaBrandFeeHandlePage').change();


            if(data.buydeptid) {

                $('#oa-deptid2-oaBrandFeeHandlePage').val(data.buydeptid + ':' + data.buydeptname);
                $('#oa-deptid-oaBrandFeeHandlePage').val(data.buydeptid);
            }

//            $('#oa-deptid2-oaBrandFeeHandlePage').blur();
//            $('#oa-deptid2-oaBrandFeeHandlePage').change();
        }

        /**
         * 部门
         */
        function selectDept(data) {

            $('#oa-deptid2-oaBrandFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-deptid-oaBrandFeeHandlePage').val(data.id);

            $('#oa-deptid2-oaBrandFeeHandlePage').blur();
            $('#oa-deptid2-oaBrandFeeHandlePage').change();
        }

        /**
         * 品牌
         */
        function selectBrand(data) {

            $('#oa-brandid2-oaBrandFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-brandid-oaBrandFeeHandlePage').val(data.id);

            $('#oa-brandid2-oaBrandFeeHandlePage').blur();
            $('#oa-brandid2-oaBrandFeeHandlePage').change();
        }

        /**
         * 选择申请日期
         */
        function selectBusinessdate(data) {

            $('#oa-businessdate-oaBrandFeeHandlePage').val(data);
            $('#oa-businessdate-oaBrandFeeHandlePage').blur();
        }

        /**
         * 选择归还日期
         */
        function selectReturndate(data) {

            $('#oa-returndate-oaBrandFeeHandlePage').val(data);
            $('#oa-returndate-oaBrandFeeHandlePage').blur();
        }

        /**
         * 选择付款银行
         */
        function selectBank(data) {

            $('#oa-paybank2-oaBrandFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-paybank-oaBrandFeeHandlePage').val(data.id);

            $('#oa-paybank2-oaBrandFeeHandlePage').blur();
            $('#oa-paybank2-oaBrandFeeHandlePage').change();
        }

        /**
         * 选择费用科目
         */
        function selectExpensesort(data) {

            $('#oa-expensesort2-oaBrandFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-expensesort-oaBrandFeeHandlePage').val(data.id);

            $('#oa-expensesort2-oaBrandFeeHandlePage').blur();
            $('#oa-expensesort2-oaBrandFeeHandlePage').change();
        }

        function removeDetail(){

            var index = localStorage.getItem('index');

            if(index == -1) {

                return true;
            }

            feeList.splice(index, 1);
            localStorage.setItem("details", JSON.stringify(feeList));

            var payamount = 0;
            for(var i in feeList) {

                var fee = feeList[i];
                payamount = parseFloat(payamount) + parseFloat(fee.factoryamount);
            }

            $('#oa-payamount-oaBrandFeeHandlePage').val(formatterMoney(payamount));
            return true;
        }

        function saveDetail(){

            $('#oa-form-oaBrandFeeDetailPage').validate({
                focusInvalid: true,
                debug: true,
                rules: {
                }
            });

            var flag = $('#oa-form-oaBrandFeeDetailPage').validate().form();

            if(!flag) {

                return flag;
            }

            var data = $('#oa-form-oaBrandFeeDetailPage').serializeJSON();

            // 验证
            $("#oa-form-oaBrandFeeDetailPage").submit();

            $('#oa-form-oaBrandFeeDetailPage').form('clear');
            $('#oa-form-oaBrandFeeDetailPage')[0].reset();
            var index = parseInt(localStorage.getItem('index'));

            data.factoryamount = formatterMoney(data.factoryamount);
            if(index == -1) {
                feeList.push(data);
            } else {
                feeList[index] = data;
            }

            var payamount = 0;
            for(var i in feeList) {

                var fee = feeList[i];
                payamount = parseFloat(payamount) + parseFloat(fee.factoryamount);
            }

            $('#oa-payamount-oaBrandFeeHandlePage').val(formatterMoney(payamount));
            localStorage.setItem('details', JSON.stringify(feeList));
            localStorage.setItem('index', -1);

            return true;
        }

        /**
         * 提交表单
         * @param call
         * @param args
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaBrandFeeHandlePage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#oa-form-oaBrandFeeHandlePage").submit();
            var flag = $('#oa-form-oaBrandFeeHandlePage').validate().form();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaBrandFeeHandlePage').validate({
                debug: false
            });

            $('#oa-detaillist-oaBrandFeeHandlePage').val(localStorage.getItem('details'));

            $('#oa-form-oaBrandFeeHandlePage').submit(function(){

                $(this).ajaxSubmit({
                    type: 'post',
                    //beforeSubmit: showRequest,
                    <c:choose>
                        <c:when test="${empty brandfee or empty brandfee.id}">
                            url: 'oa/brandfee/addBrandFee.do',
                        </c:when>
                        <c:otherwise>
                            url: 'oa/brandfee/editBrandFee.do',
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
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1>处理工作[${param.processid }]</h1>
        <%--<a href="javascript:location.href = location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a>--%>
    </div>
    <form action="" id="oa-form-oaBrandFeeHandlePage" method="post">
        <input type="hidden" name="brandfee.id" id="oa-id-oaBrandFeeHandlePage" value="${param.id }"/>
        <input type="hidden" name="brandfee.oaid" id="oa-oaid-oaBrandFeeHandlePage" value="${param.processid }"/>
        <input type="hidden" name="detaillist" id="oa-detaillist-oaBrandFeeHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>供应商<span class="warnning">*</span>
                        <input type="text" class="required" name="brandfee.supplierid2" id="oa-supplierid2-oaBrandFeeHandlePage" value="<c:if test='${not empty supplier }'><c:out value='${brandfee.supplierid }'/>:<c:out value='${supplier.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="brandfee.supplierid" id="oa-supplierid-oaBrandFeeHandlePage" value="${brandfee.supplierid }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>供应商确认
                        <input type="text" name="brandfee.supplierconfirm" id="oa-supplierconfirm-oaCustomerFeeDetailPage" value="${brandfee.supplierconfirm }" maxlength="25"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>部门<span class="warnning">*</span>
                        <input type="text" class="required" name="brandfee.deptid2" id="oa-deptid2-oaBrandFeeHandlePage" value="<c:if test='${not empty dept }'><c:out value='${brandfee.deptid }'/>:<c:out value='${dept.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="brandfee.deptid" id="oa-deptid-oaBrandFeeHandlePage" value="${brandfee.deptid }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>品牌<span class="warnning">*</span>
                        <input type="text" class="required" name="brandfee.brandid2" id="oa-brandid2-oaBrandFeeHandlePage" value="<c:if test='${not empty brand }'><c:out value='${brandfee.brandid }'/>:<c:out value='${brand.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="brandfee.brandid" id="oa-brandid-oaBrandFeeHandlePage" value="${brandfee.brandid }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>申请日期<span class="warnning">*</span>
                        <c:choose>
                            <c:when test="${empty brandfee or empty brandfee.businessdate}">
                                <input type="text" class="required" name="brandfee.businessdate" id="oa-businessdate-oaBrandFeeHandlePage" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" data-clear-btn="true"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="required" name="brandfee.businessdate" id="oa-businessdate-oaBrandFeeHandlePage" value="${brandfee.businessdate }" data-clear-btn="true"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <a href="oa/brandfee/phone/oaBrandFeeDetailPage.jsp" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-detail-oaBrandFeeHandlePage" data-inset="false">
                        <li data-role="list-divider">费用明细</li>
                    </ul>
                </div>

                <div class="ui-field-contain">
                    <label>费用科目<span class="warnning">*</span>
                        <input type="text" class="required" name="brandfee.expensesort2" id="oa-expensesort2-oaBrandFeeHandlePage" value="<c:if test='${not empty expensesort }'><c:out value='${brandfee.expensesort }'/>:<c:out value='${expensesort.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="brandfee.expensesort" id="oa-expensesort-oaBrandFeeHandlePage" value="${brandfee.expensesort }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>费用金额
                        <input type="number" name="brandfee.payamount" id="oa-payamount-oaBrandFeeHandlePage" value="${brandfee.payamount }" readonly="readonly" data-clear-btn="false"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>付款银行
                        <input type="text" name="brandfee.paybank2" id="oa-paybank2-oaBrandFeeHandlePage" value="<c:if test='${not empty bank }'><c:out value='${brandfee.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="brandfee.paybank" id="oa-paybank-oaBrandFeeHandlePage" value="${brandfee.paybank }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>归还日期
                        <input type="text" name="brandfee.returndate" id="oa-returndate-oaBrandFeeHandlePage" value="${brandfee.returndate }" data-clear-btn="true" readonly="readonly"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>归还方式
                        <input type="text" name="brandfee.returnway" id="oa-returnway-oaCustomerFeeDetailPage" value="${brandfee.returnway }" maxlength="25"/>
                    </label>
                </div>

                <%--
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="brandfee.remark" id="oa-remark-oaBrandFeeHandlePage"><c:out value="${brandfee.remark }"/></textarea>
                    </label>
                </div>
                --%>

            </div>
        </div>
    </form>
    <div id="oa-footer-oaBrandFeeHandlePage" data-role="footer" data-position="fixed">
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
