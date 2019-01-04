<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>费用冲差支付申请单(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<script type="text/javascript">

    var pushList =  $.parseJSON('${pushJSON }');
    localStorage.setItem("pushList", '${pushJSON }');

    // 显示主页面时，reload明细数据
    $(document).on('pageshow', '#main', function(e) {

        refreshPushList(e);

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

        // 小数位截取
        //numberPrecision();
    });

    /**
     * 选择日期
     */
    function selectbusinessdate(data) {

        $('#oa-businessdate-oaExpensePushHandlePage').val(data);
        $('#oa-businessdate-oaExpensePushHandlePage').blur();
    }

    function selectCustomer(data){

        $('#oa-customerid-oaExpensePushHandlePage').val(data.id);
        $('#oa-customer-oaExpensePushHandlePage').val(data.id + ':' + data.name);

        $('#oa-salesdeptid-oaExpensePushHandlePage').val(data.salesdeptid);
        $('#oa-salesdeptname-oaExpensePushHandlePage').val(data.salesdeptname);

        // confirmid
        $('#oa-customer-oaExpensePushHandlePage').blur();
        $('#oa-customer-oaExpensePushHandlePage').change();

        refreshPrice();
    }

    function refreshPushList(e){

        var pushList = $.parseJSON(localStorage.getItem("pushList"));

        var html = new Array();

        for(var i in pushList) {

            var pushdetail = pushList[i];

            html.push('<li>');
            html.push('<a href="oa/expensepush/phone/oaExpensePushDetailPage.jsp?oarequired=${param.oarequired }&noaccess=${param.noaccess }&dept=${param.dept }&buyprice=${param.buyprice }&oacheck=${param.oacheck }" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
            html.push('<table>');

            html.push('<tr>');
            html.push('<th>折差品牌:</th>');
            html.push('<td>' + pushdetail.brandid + ':' + pushdetail.brandname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>冲差类型:</th>');
            html.push('<td>' + pushdetail.pushtype + ':' + pushdetail.pushtypename + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>折让金额:</th>');
            html.push('<td><span precision="2">' + pushdetail.amount + '</span></td>');
            html.push('</tr>');

            <c:choose>
                <c:when test="${param.noaccess eq 1}">

                    html.push('<tr>');
                    html.push('<th>费用科目:</th>');
                    html.push('<td><span>'  +  pushdetail.expensesortname + '</span></td>');
                    html.push('</tr>');

                    html.push('<tr>');
                    html.push('<th>开始日期:</th>');
                    html.push('<td><span>'  + pushdetail.startdate + '</span></td>');
                    html.push('</tr>');

                    html.push('<tr>');
                    html.push('<th>结束日期:</th>');
                    html.push('<td><span>'  + pushdetail.enddate + '</span></td>');
                    html.push('</tr>');

                </c:when>
                <c:otherwise>

                    html.push('<tr>');
                    html.push('<th>费用部门:</th>');
                    html.push('<td><span>'  + pushdetail.deptid + ':' + pushdetail.deptname + '</span></td>');
                    html.push('</tr>');

                    html.push('<tr>');
                    html.push('<th>已批OA号:</th>');
                    html.push('<td><span>' + pushdetail.oaid + '</span></td>');
                    html.push('</tr>');

                </c:otherwise>
            </c:choose>

            if(pushdetail.goodsid) {

                html.push('<tr>');
                html.push('<th>商品:</th>');
                html.push('<td>' + pushdetail.goodsid + ':' + pushdetail.goodsname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>数量:</th>');
                html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + pushdetail.unitnum + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>原价:</th>');
                html.push('<td><span precision="2">' + pushdetail.oldprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>现价:</th>');
                html.push('<td><span precision="2">' + pushdetail.newprice + '</span></td>');
                html.push('</tr>');

                <c:if test="${param.buyprice eq '1'}">
                    html.push('<tr>');
                    html.push('<th>采购价:</th>');
                    html.push('<td><span precision="2">' + pushdetail.buyprice + '</span></td>');
                    html.push('</tr>');
                </c:if>
            }

            html.push('<tr>');
            html.push('<th>备注:</th>');
            html.push('<td><span>' + pushdetail.remark + '</span></td>');
            html.push('</tr>');

            html.push('</table>');
            html.push('</a>');
            html.push('</li>');
        }

        $('#oa-pushdetail-oaExpensePushHandlePage').find(':not(:eq(0))').remove();
        $('#oa-pushdetail-oaExpensePushHandlePage').append(html.join(''));

        try {

            $('#oa-pushdetail-oaExpensePushHandlePage').listview('refresh');
        } catch(e) {}

    }

    //选择部门
    function selectDept(data){
        $('#oa-deptid-oaExpensePushDetailPage').val(data.id);
        $('#oa-deptname-oaExpensePushDetailPage').val(data.name);
        $('#oa-dept-oaExpensePushDetailPage').val(data.id + ':' + data.name);
    }

    function removePush(){

        var index = localStorage.getItem('index');

        if(index == -1) {

            return true;
        }

        pushList.splice(index, 1);
        localStorage.setItem("pushList", JSON.stringify(pushList));
        return true;
    }

    function savePush(){

        $('#oa-form-oaExpensePushDetailPage').validate({
            focusInvalid: true,
            debug: true,
            rules: {
            }
        });

        var flag = $('#oa-form-oaExpensePushDetailPage').validate().form();

        if(!flag) {

            return flag;
        }

        var data = $('#oa-form-oaExpensePushDetailPage').serializeJSON();

        // 验证
        $("#oa-form-oaExpensePushDetailPage").submit();

        var oaid = $('#oa-oaid-oaExpensePushDetailPage').val();
        if(oaid != undefined && oaid != null && oaid != ''){
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: 'act/selectProcess.do',
                data: {id: oaid},
                success: function(json) {

                    if(json.process == null) {
                        flag = false ;
                        alertMsg("已批OA编号对应的通路单不存在，重置为空！");
                        $('#oa-oaid-oaExpensePushDetailPage').val();
                        return false ;
                    }else{
                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            url: 'oa/selectAccessInfo.do',
                            data: {id: json.process.businessid},
                            success: function(json) {
                                if(json.access == null) {
                                    flag = false ;
                                    alertMsg("已批OA编号对应的通路单不存在，重置为空！");
                                    return false ;
                                    $('#oa-oaid-oaExpensePushDetailPage').val();
                                }else{
                                    count = 1 ;
                                    $('#oa-form-oaExpensePushDetailPage').form('clear');
                                    $('#oa-form-oaExpensePushDetailPage')[0].reset();
                                    var index = parseInt(localStorage.getItem('index'));

                                    data.amount  =  formatterMoney(data.amount);
                                    if(index == -1) {
                                        pushList.push(data);
                                    } else {
                                        pushList[index] = data;
                                    }
                                    localStorage.setItem('pushList', JSON.stringify(pushList));
                                    localStorage.setItem('index', -1);
                                    refreshPushList();
                                    return true;
                                }
                            },
                            error: function() {}

                        });
                    }
                }
            });

        }else{
            $('#oa-form-oaExpensePushDetailPage').form('clear');
            $('#oa-form-oaExpensePushDetailPage')[0].reset();
            var index = parseInt(localStorage.getItem('index'));

            data.amount  =  formatterMoney(data.amount);
            if(index == -1) {
                pushList.push(data);
            } else {
                pushList[index] = data;
            }
            localStorage.setItem('pushList', JSON.stringify(pushList));
            localStorage.setItem('index', -1);
            refreshPushList();
            return true;
        }
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
     * 刷新原价
     */
    function refreshPrice(e) {

        var pushList2 = $.parseJSON(localStorage['pushList']);
        var customerid = $('#oa-customerid-oaExpensePushHandlePage').val();
        loading();
        for(var i in pushList2) {

            var row = pushList2[i];
            var goodsid = pushList2[i].goodsid || '';

            if(goodsid == '') {
                continue;
            }

            $.ajax({
                type: 'post',
                url: 'oa/common/getGoodsPrice.do',
                data: {customerid: customerid, goodsid: goodsid, index: i},
                dataType: 'json',
                async: false,
                success: function(json) {

                    var idx = parseInt(json.index);
                    row.oldprice = json.goods.taxprice;
                    pushList2[idx] = row;
                    localStorage.setItem("pushList", JSON.stringify(pushList2));
                }
            });
        }
        loaded();
        refreshPushList(e);
        numberPrecision();
    }

    /**
     * 提交表单
     * @param call
     * @param args
     * @returns {boolean}
     */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaExpensePushHandlePage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaExpensePushHandlePage").submit();
        var flag = $('#oa-form-oaExpensePushHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaExpensePushHandlePage').validate({
            debug: false
        });

        $('#oa-pushList-oaExpensePushHandlePage').val(JSON.stringify(pushList));

        $('#oa-form-oaExpensePushHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                <c:choose>
                    <c:when test="${empty push or empty push.id}">
                        url:'oa/expensepush/addOaExpensePush.do',
                    </c:when>
                    <c:otherwise>
                        url:'oa/expensepush/editOaExpensePush.do',
                    </c:otherwise>
                </c:choose>
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

    $(function(){
        //客户档案控件
        $('#oa-customer-oaExpensePushHandlePage').on('click', function() {

            androidWidget({
                type: 'customerWidget', //客户档案
                onSelect: 'selectCustomer',
                isdatasql: '1', //1表示进行权限控制 2表示不进行权限控制,
                isopen: true
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-customerid-oaExpensePushHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        var date = $('#oa-businessdate-oaExpensePushHandlePage').val();
        if(date == ""){
            $('#oa-businessdate-oaExpensePushHandlePage').val(new Date().Format('yyyy-MM-dd'));
        }

        // 日期控件
        $(document).on('click', '#oa-businessdate-oaExpensePushHandlePage', function() {

            androidDateWidget($('#oa-businessdate-oaExpensePushHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectbusinessdate');
        });

        {
            var ptype = '${param.ptype }';  // 冲差类型
            if(ptype) {
                var $ptype = $('#oa-ptype-oaExpensePushHandlePage');
                var currentPtypeVal = $('#oa-ptype-oaExpensePushHandlePage option[selected]').attr('value');
                var ptypeArr = ptype.split('');

                var newPtypeArray = new Array();
                for(var i in ptypeArr) {
                    newPtypeArray[ptypeArr[i]] = ptypeArr[i];
                }

                $ptype.html('');
                if(newPtypeArray[1]) {
                    $ptype.append('<option value="1">冲差</option>');
                }
                if(newPtypeArray[2]) {
                    $ptype.append('<option value="2">货补</option>');
                }
                if(currentPtypeVal) {
                    $('#oa-ptype-oaExpensePushHandlePage').val(currentPtypeVal);
                }
                $ptype.selectmenu('refresh', true);
            }
        }
    });
</script>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>处理工作[${param.processid }]</h1>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <form action="" id="oa-form-oaExpensePushHandlePage" method="post">

        <input type="hidden" name="detaillist" id="oa-pushList-oaExpensePushHandlePage" value=""/>
        <input type="hidden" name="push.id" id="oa-id-oaExpensePushHandlePage" value="${param.id }"/>
        <input type="hidden" name="push.oaid" id="oaExpensePushHandlePage" value="${param.processid }"/>
        <input type="hidden" name="push.salesdeptid" id="oa-salesdeptid-oaExpensePushHandlePage" value="${push.salesdeptid }"/>
        <input type="hidden" name="push.salesdeptname" id="oa-salesdeptname-oaExpensePushHandlePage" value="${push.salesdeptname }"/>

        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户&nbsp;<font color="#F00">*</font>
                        <c:choose>
                            <c:when test="${not empty push and not empty push.customerid and not empty customer}">
                                <input type="text" class="required" name="push.customer" id="oa-customer-oaExpensePushHandlePage" value="${customer.id }:${customer.name }" readonly="readonly" data-clear-btn="true"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="required" name="push.customer" id="oa-customer-oaExpensePushHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" class="required" name="push.customerid" id="oa-customerid-oaExpensePushHandlePage" value="${customer.id }" readonly="readonly" data-clear-btn="true"/>
                    </label>
                    </div>

                <div class="ui-field-contain">
                    <label class="select">支付类型&nbsp;<font color="#F00">*</font>
                        <select sdata="${push.ptype}" name="push.ptype" id="oa-ptype-oaExpensePushHandlePage">
                            <option value="1" <c:if test="${push.ptype eq '1'}">selected="selected"</c:if> >冲差</option>
                            <option value="2" <c:if test="${push.ptype eq '2'}">selected="selected"</c:if> >货补</option>
                        </select>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>日期&nbsp;<font color="#F00">*</font>
                     <input type="text" name="push.businessdate" value="${push.businessdate}" id="oa-businessdate-oaExpensePushHandlePage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <a href="oa/expensepush/phone/oaExpensePushDetailPage.jsp?oarequired=${param.oarequired }&noaccess=${param.noaccess }&dept=${param.dept }&buyprice=${param.buyprice }&oacheck=${param.oacheck }" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-pushdetail-oaExpensePushHandlePage" data-inset="false">
                        <li data-role="list-divider">品牌明细</li>
                    </ul>
                </div>
             </div>
        </div>
    </form>
    <div id="oa-footer-oaExpensePushHandlePage" data-role="footer" data-position="fixed">
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
