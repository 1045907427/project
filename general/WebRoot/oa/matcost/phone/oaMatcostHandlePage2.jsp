<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2018/2/22
  Time: 11:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="today" class="java.util.Date"/>
<html>
<head>
    <title>代垫费用申请单处理页面</title>
    <%@include file="/phone/common/include.jsp"%>
    <style type="text/css">
        .warnning {
            color: #F00;
        }
    </style>
    <script type="text/javascript">

        var feeList = $.parseJSON('${list }');
        localStorage.setItem('details', '${list }');

        $(function() {

            // 申请日期
            $(document).on('click', '#oa-businessdate-oaMatcostHandlePage', function() {

                androidDateWidget($('#oa-businessdate-oaMatcostHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectBusinessdate');
            });

            // 供应商
            $('#oa-supplier-oaMatcostHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_BUY_SUPPLIER',
                    onSelect: 'selectSupplier'
                });
            }).on('change', function() {

                var v = $(this).val();

                if(!v) {
                    $('#oa-supplierid-oaMatcostHandlePage').attr('value', '');
                    $('#oa-supplierid-oaMatcostHandlePage').val('');
                }

            });

            // 部门
            $('#oa-dept-oaMatcostHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_DEPARTMENT_1',
                    onSelect: 'selectDept'
                });
            }).on('change', function() {

                var v = $(this).val();

                if(!v) {
                    $('#oa-deptid-oaMatcostHandlePage').attr('value', '');
                    $('#oa-deptid-oaMatcostHandlePage').val('');
                }

            });

            // 银行
            $('#oa-bank-oaMatcostHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_FINANCE_BANK',
                    onSelect: 'selectBank'
                });
            }).on('change', function() {

                var v = $(this).val();

                if(!v) {
                    $('#oa-paybank-oaMatcostHandlePage').attr('value', '');
                    $('#oa-paybank-oaMatcostHandlePage').val('');
                }

            });

            // 归还日期
            $(document).on('click', '#oa-returndate-oaMatcostHandlePage', function() {

                androidDateWidget($('#oa-returndate-oaMatcostHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectReturndate');
            });

            refreshFeeList();
        });

        /**
         * 选择申请日期
         */
        function selectBusinessdate(data) {

            $('#oa-businessdate-oaMatcostHandlePage').val(data);
            $('#oa-businessdate-oaMatcostHandlePage').blur();
        }

        /**
         * 选择归还日期
         */
        function selectReturndate(data) {

            $('#oa-returndate-oaMatcostHandlePage').val(data);
            $('#oa-returndate-oaMatcostHandlePage').blur();
        }

        /**
         * 选择供应商
         * @param data
         */
        function selectSupplier(data){

            $('#oa-supplierid-oaMatcostHandlePage').val(data.id);
            $('#oa-supplier-oaMatcostHandlePage').val(data.name);

            $('#oa-supplier-oaMatcostHandlePage').blur();
            $('#oa-supplier-oaMatcostHandlePage').change();
        }

        /**
         * 选择部门
         * @param data
         */
        function selectDept(data){

            $('#oa-deptid-oaMatcostHandlePage').val(data.id);
            $('#oa-dept-oaMatcostHandlePage').val(data.name);

            $('#oa-dept-oaMatcostHandlePage').blur();
            $('#oa-dept-oaMatcostHandlePage').change();
        }

        /**
         * 选择银行
         * @param data
         */
        function selectBank(data){

            $('#oa-paybank-oaMatcostHandlePage').val(data.id);
            $('#oa-bank-oaMatcostHandlePage').val(data.name);

            $('#oa-bank-oaMatcostHandlePage').blur();
            $('#oa-bank-oaMatcostHandlePage').change();
        }

        /**
         * 保存明细
         * @returns {*}
         */
        function saveDetail(){

            $('#oa-form-oaMatcostDetailPage').validate({
                focusInvalid: true,
                debug: true,
                rules: {
                }
            });

            var flag = $('#oa-form-oaMatcostDetailPage').validate().form();

            if(!flag) {

                return flag;
            }

            var data = $('#oa-form-oaMatcostDetailPage').serializeJSON();

            // 验证
            $("#oa-form-oaMatcostDetailPage").submit();

            $('#oa-form-oaMatcostDetailPage').form('clear');
            $('#oa-form-oaMatcostDetailPage')[0].reset();
            var index = parseInt(localStorage.getItem('index'));

            data.factoryamount = formatterMoney(data.factoryamount || 0);
            data.selfamount = formatterMoney(data.selfamount || 0);
            data.feeamount = formatterMoney(data.feeamount || 0);
            if(index == -1) {
                feeList.push(data);
            } else {
                feeList[index] = data;
            }

            localStorage.setItem('details', JSON.stringify(feeList));
            localStorage.setItem('index', -1);

            refreshFeeList();
            return true;
        }

        /**
         * 删除明细
         * @returns {boolean}
         */
        function removeDetail(){

            var index = localStorage.getItem('index');

            if(index == -1) {

                return true;
            }

            feeList.splice(index, 1);
            localStorage.setItem("details", JSON.stringify(feeList));
            return true;
        }

        /**
         * 刷新费用明细
         */
        function refreshFeeList(e){

            var feeList = $.parseJSON(localStorage.getItem("details"));

            var html = new Array();

            for(var i in feeList) {

                var feedetail = feeList[i];

                html.push('<li>');
                html.push('<a href="oa/matcost/phone/oaMatcostDetailPage.jsp?" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>客户:</th>');
                html.push('<td>' + feedetail.customername + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>品牌:</th>');
                html.push('<td><span>' + feedetail.brandname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>费用科目:</th>');
                html.push('<td><span>' +  feedetail.expensesortname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>工厂投入:</th>');
                html.push('<td><span precision="2">' + (feedetail.factoryamount || 0) + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>自理:</th>');
                html.push('<td><span precision="2">' + (feedetail.selfamount || 0) + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>费用金额:</th>');
                html.push('<td><span precision="2">' + (feedetail.feeamount || 0) + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>备注:</th>');
                html.push('<td><span>' + feedetail.remark + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-detail-oaMatcostHandlePage').find(':not(:eq(0))').remove();
            $('#oa-detail-oaMatcostHandlePage').append(html.join(''));

            try {

                $('#oa-detail-oaMatcostHandlePage').listview('refresh');
            } catch(e) {}

            numberPrecision();
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

        /**
         * 提交表单
         * @param call
         * @param args
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaMatcostHandlePage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#oa-form-oaMatcostHandlePage").submit();
            var flag = $('#oa-form-oaMatcostHandlePage').validate().form();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaMatcostHandlePage').validate({
                debug: false
            });

            $('#oa-detaillist-oaMatcostHandlePage').val(localStorage.getItem('details'));

            $('#oa-form-oaMatcostHandlePage').submit(function(){

                $(this).ajaxSubmit({
                    type: 'post',
                    <c:choose>
                        <c:when test="${empty matcost or empty matcost.id}">
                            url: 'oa/matcost/addOaMatcost.do',
                        </c:when>
                        <c:otherwise>
                            url: 'oa/matcost/editOaMatcost.do',
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
    <form action="" id="oa-form-oaMatcostHandlePage" method="post">

        <input type="hidden" name="detaillist" id="oa-detaillist-oaMatcostHandlePage" value=""/>
        <input type="hidden" name="matcost.id" id="oa-id-oaMatcostHandlePage" value="${param.id }"/>
        <input type="hidden" name="matcost.oaid" id="oa-oaid-oaMatcostHandlePage" value="${param.processid }"/>

        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">

                <div class="ui-field-contain">
                    <label>申请日期&nbsp;<font color="#F00">*</font>

                        <c:choose>
                            <c:when test="${not empty matcost and not empty matcost.businessdate }">
                                <input type="text" name="matcost.businessdate" value="${matcost.businessdate}" id="oa-businessdate-oaMatcostHandlePage" readonly="readonly"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" name="matcost.businessdate" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" id="oa-businessdate-oaMatcostHandlePage" readonly="readonly"/>
                            </c:otherwise>
                        </c:choose>

                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>供应商&nbsp;<font color="#F00">*</font>
                        <c:choose>
                            <c:when test="${not empty matcost and not empty matcost.supplierid and not empty supplier}">
                                <input type="text" class="required" name="matcost.supplier" id="oa-supplier-oaMatcostHandlePage" value="${supplier.name }" readonly="readonly" data-clear-btn="true"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="required" name="matcost.supplier" id="oa-supplier-oaMatcostHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" class="required" name="matcost.supplierid" id="oa-supplierid-oaMatcostHandlePage" value="${matcost.supplierid }" readonly="readonly" data-clear-btn="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>确认单号
                        <input type="text" name="matcost.supplierbillid" value="${matcost.supplierbillid}" id="oa-supplierbillid-oaMatcostHandlePage" data-clear-btn="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>部门
                        <c:choose>
                            <c:when test="${not empty matcost and not empty matcost.deptid and not empty dept}">
                                <input type="text" class="" name="matcost.dept" id="oa-dept-oaMatcostHandlePage" value="${dept.name }" readonly="readonly" data-clear-btn="true"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="" name="matcost.dept" id="oa-dept-oaMatcostHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" class="" name="matcost.deptid" id="oa-deptid-oaMatcostHandlePage" value="${matcost.deptid }" readonly="readonly" data-clear-btn="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>银行&nbsp;<font color="#F00">*</font>
                        <c:choose>
                            <c:when test="${not empty matcost and not empty matcost.paybank and not empty bank}">
                                <input type="text" class="required" name="matcost.bank" id="oa-bank-oaMatcostHandlePage" value="${bank.name }" readonly="readonly" data-clear-btn="true"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="required" name="matcost.bank" id="oa-bank-oaMatcostHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" class="required" name="matcost.paybank" id="oa-paybank-oaMatcostHandlePage" value="${matcost.paybank }" readonly="readonly" data-clear-btn="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>归还日期
                        <input type="text" name="matcost.returndate" value="${matcost.returndate}" id="oa-returndate-oaMatcostHandlePage" data-clear-btn="true" readonly="readonly"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label class="select">归还方式
                        <select sdata="${matcost.returnway}" name="matcost.returnway" id="oa-returnway-oaMatcostHandlePage">
                            <option></option>
                            <c:forEach items="${reimburseTypeList }" var="type">
                                <option value="${type.code }" <c:if test="${matcost.returnway eq type.code}">selected="selected"</c:if> ><c:out value="${type.codename }"></c:out></option>
                            </c:forEach>
                        </select>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <a href="oa/matcost/phone/oaMatcostDetailPage.jsp" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-detail-oaMatcostHandlePage" data-inset="false">
                        <li data-role="list-divider">费用明细</li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oaMatcostHandlePage" data-role="footer" data-position="fixed">
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

