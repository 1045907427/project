<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>批量特价审批单(手机)</title>
<%@include file="/phone/common/include.jsp"%>
<script type="text/javascript">

    var priceList = $.parseJSON('${list }');
    localStorage.setItem('priceList', '${list }');

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

    $(document).on('pageshow', '#main', function(e) {

        refreshPrices(e);

        numberPrecision();
    });

    /**
     *
     */
    $(document).on('change', '#oa-buyprice-oaOffPriceDetailPage,#oa-offprice-oaOffPriceDetailPage', computeProfitrate);

    $(function() {

        // 客户
        $('#oa-customerid2-oaOffPriceHandlePage').on('click', function() {

            androidWidget({
                type: 'customerWidget',
                ishead: '3',
                onSelect: 'selectCustomer'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-customerid-oaOffPriceHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 销售内勤
        $('#oa-indoorstaff2-oaOffPriceHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_PERSONNEL_INDOORSTAFF',
                onSelect: 'selectIndoorstaff'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-indoorstaff-oaOffPriceHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 业务员
        $('#oa-salesuserid2-oaOffPriceHandlePage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_PERSONNEL_BRAND_CUSTOMER_SELLER',
                onSelect: 'selectSalesuser'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-salesuserid-oaOffPriceHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 一次性开单时间（开始）
        $(document).on('click', '#oa-pricebegindate-oaOffPriceHandlePage', function() {

            androidDateWidget($('#oa-pricebegindate-oaOffPriceHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPricebegindate');
        });

        // 一次性开单时间（结束）
        $(document).on('click', '#oa-priceenddate-oaOffPriceHandlePage', function() {

            androidDateWidget($('#oa-priceenddate-oaOffPriceHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPriceenddate');
        });

        // 计算付款差额
        //$(document).on('change', '#oa-arrivalamount-oaOffPriceHandlePage,#oa-priceamount-oaOffPriceHandlePage,#oa-expenseamount-oaOffPriceHandlePage', computePayMargin);

        // 金额转换为大写
        $('#oa-priceamount-oaOffPriceHandlePage').on('change', function(e) {

            var v = $(this).val();
            var big = moneyToUpper(v);

            $('#oa-upperpriceamount-oaOffPriceHandlePage').val(big);
        });

    });

    /**
     * 选择商品
     */
    function selectGoods(data) {

        var goodsid = data.id;
        var exist = false;

        for(var i in priceList) {

            var price = priceList[i];

            if(goodsid == price.goodsid) {

                exist = true;
                break;
            }
        }

        if(exist) {

            alertMsg('明细中已存在该商品！');
            return true;
        }

        $('#oa-goods-oaOffPriceDetailPage').val(data.id + ':' + data.name);
        $('#oa-goodsid-oaOffPriceDetailPage').val(data.id);
        $('#oa-goodsname-oaOffPriceDetailPage').val(data.name);
        $('#oa-brand-oaOffPriceDetailPage').val(data.brand);
        $('#oa-barcode-oaOffPriceDetailPage').val(data.barcode);

        var buyprice = 0;
        if(data.costaccountprice && data.costaccountprice > 0) {
            buyprice = data.costaccountprice;
        } else {
            buyprice = data.highestbuyprice || 0.00;
            <c:choose>
                <c:when test="${param.pricetype eq '1'}">
                    buyprice = data.newbuyprice || 0.00;
                </c:when>
                <c:otherwise>
                    buyprice = data.highestbuyprice || 0.00;
                </c:otherwise>
            </c:choose>
        }

        $('#oa-buyprice-oaOffPriceDetailPage').val(buyprice);

        loading();
        $.ajax({
            type: 'post',
            url: 'report/sales/showSalesGoodsQuotationReportData.do',
            data: {customerid: $('#oa-customerid-oaOffPriceHandlePage').val(), brandid: data.brand, page: 1, rows: 9999},
            dataType: 'json',
            success: function(json) {

                for(var j in json.rows) {
                    if(json.rows[j].goodsid == data.id) {

                        $('#oa-oldprice-oaOffPriceDetailPage').val(json.rows[j].price);
                        loaded();
                        break;
                    }
                }
                loaded();
            },
            error: function(){}
        });

        computeProfitrate();

    }

    /**
     * 计算毛利率
     */
    function computeProfitrate() {

        var buyprice = parseFloat($('#oa-buyprice-oaOffPriceDetailPage').val() || 0);
        var offprice = parseFloat($('#oa-offprice-oaOffPriceDetailPage').val() || 0);

        if(buyprice != 0) {

            var rate = formatterMoney((offprice - buyprice) / buyprice * 100);
            $('#oa-profitrate-oaOffPriceDetailPage').val(rate);
        } else {
            $('#oa-profitrate-oaOffPriceDetailPage').val(0.00);
        }
    }

    /**
     * 刷新明细里的原价
     */
    function refreshOldprice(e) {

        var customerid = $('#oa-customerid-oaOffPriceHandlePage').val();

        loading();
        for(var i in priceList) {

            var price = priceList[i];
            $.ajax({
                type: 'post',
                url: 'report/sales/showSalesGoodsQuotationReportData.do',
                data: {customerid: customerid, brandid: price.brand, page: 1, rows: 9999},
                dataType: 'json',
                async: false,
                success: function(json) {

                    priceList[i].oldprice = 0.00;
                    for(var j in json.rows) {
                        if(json.rows[j].goodsid == price.goodsid) {

                            priceList[i].oldprice = json.rows[j].price;
                            break;
                        }
                    }
//                    androidLoaded();
                },
                error: function(){}
            });
        }

        refreshPrices();
        loaded();
        return true;
    }

    /**
     * 选择客户
     */
    function selectCustomer(data) {

        var oldcustomerid = $('#oa-customerid-oaOffPriceHandlePage').val();
        var newcustomerid = data.id;

        $('#oa-customerid2-oaOffPriceHandlePage').val(data.id + ':' + data.name);
        $('#oa-customerid-oaOffPriceHandlePage').val(data.id);

        $('#oa-customerid2-oaOffPriceHandlePage').blur();
        $('#oa-customerid2-oaOffPriceHandlePage').change();

        var indoorstaff = data.indoorstaff;
        loading();

        $.ajax({
            type: 'post',
            url: 'oa/common/selectPersonnelInfo.do',
            data: {id: indoorstaff},
            dataType: 'json',
            success: function(json) {

                loaded();
                if(json.personnel != null) {

                    selectIndoorstaff(json.personnel);
                }
            },
            error: function(error) {}
        });

        if(oldcustomerid != newcustomerid) {

            refreshOldprice();
        }
    }

    /**
     * 选择销售内勤
     */
    function selectIndoorstaff(data) {

        $('#oa-indoorstaff2-oaOffPriceHandlePage').val(data.id + ':' + data.name);
        $('#oa-indoorstaff-oaOffPriceHandlePage').val(data.id);

        $('#oa-indoorstaff2-oaOffPriceHandlePage').blur();
        $('#oa-indoorstaff2-oaOffPriceHandlePage').change();
    }

    /**
     * 选择业务员
     */
    function selectSalesuser(data) {

        $('#oa-salesuserid2-oaOffPriceHandlePage').val(data.id + ':' + data.name);
        $('#oa-salesuserid-oaOffPriceHandlePage').val(data.id);

        $('#oa-salesuserid2-oaOffPriceHandlePage').blur();
        $('#oa-salesuserid2-oaOffPriceHandlePage').change();
    }

    /**
     * 选择一次性开单时间（开始）
     */
    function selectPricebegindate(data) {

        $('#oa-pricebegindate-oaOffPriceHandlePage').val(data);
        $('#oa-pricebegindate-oaOffPriceHandlePage').blur();
    }
    
    /**
     * 选择一次性开单时间（结束）
     */
    function selectPriceenddate(data) {

        $('#oa-priceenddate-oaOffPriceHandlePage').val(data);
        $('#oa-priceenddate-oaOffPriceHandlePage').blur();
    }

    /*
     * 刷新价格明细
     */
    function refreshPrices(e) {

        var priceList = $.parseJSON(localStorage.getItem("priceList"));

        var html = new Array();

        for (var i in priceList) {

            var price = priceList[i];

            html.push('<li>');
            html.push('<a href="oa/offprice/phone/oaOffPriceDetailPage.jsp?buyprice=${param.buyprice }&pricetype=${param.pricetype }" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
            html.push('<table>');

            html.push('<tr>');
            html.push('<th>商品:</th>');
            html.push('<td><span>' + price.goodsid + ':' + price.goodsname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>条形码:</th>');
            html.push('<td><span>' + price.barcode + '</span></td>');
            html.push('</tr>');

            // 5185 6.7&通用版：批量特价申请单可以配置哪个节点能否查看采购进价
            <c:if test="${param.buyprice eq '1' or param.buyprice eq 'true'}">
                html.push('<tr>');
                html.push('<th>进价:</th>');
                html.push('<td><span precision="2">' + price.buyprice + '</span></td>');
                html.push('</tr>');
            </c:if>

            html.push('<tr>');
            html.push('<th>原价:</th>');
            html.push('<td><span precision="2">' + price.oldprice + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>特价:</th>');
            html.push('<td><span precision="2">' + price.offprice + '</span></td>');
            html.push('</tr>');

            <c:if test="${param.buyprice eq '1' or param.buyprice eq 'true'}">
                html.push('<tr>');
                html.push('<th>毛利率%:</th>');
                html.push('<td><span precision="2">' + price.profitrate + '</span></td>');
                html.push('</tr>');
            </c:if>

            html.push('<tr>');
            html.push('<th>本次订单数量:</th>');
            html.push('<td><span>' + price.ordernum + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>说明:</th>');
            html.push('<td><span>' + price.remark + '</span></td>');
            html.push('</tr>');

            html.push('</table>');
            html.push('</a>');
            html.push('</li>');
        }

        $('#oa-pricedetail-oaOffPriceHandlePage').find(':not(:eq(0))').remove();
        $('#oa-pricedetail-oaOffPriceHandlePage').append(html.join(''));
        numberPrecision();

        try {

            $('#oa-pricedetail-oaOffPriceHandlePage').listview('refresh');
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

    /**
     * 保存特价明细
     */
    function savePrice(e) {

        $('#oa-form-oaOffPriceDetailPage').validate({
            focusInvalid: true,
            debug: true
        });

        var flag = $('#oa-form-oaOffPriceDetailPage').validate().form();

        if(!flag) {

            return flag;
        }

        computeProfitrate();
        var data = $('#oa-form-oaOffPriceDetailPage').serializeJSON();

        $('#oa-form-oaOffPriceDetailPage').form('clear');
        $('#oa-form-oaOffPriceDetailPage')[0].reset();
        var index = parseInt(localStorage.getItem('index'));

        data.buyprice  =  formatterMoney(data.buyprice);
        data.oldprice  =  formatterMoney(data.oldprice);
        data.offprice  =  formatterMoney(data.offprice);
        data.profitrate  =  formatterMoney(data.profitrate);
        if(index == -1) {
            priceList.push(data);
        } else {
            priceList[index] = data;
        }
        localStorage.setItem('priceList', JSON.stringify(priceList));
        localStorage.setItem('index', -1);

        refreshPrices();
        return true;
    }

    /**
     * 移除明细
     */
    function removePrice(e) {

        var index = localStorage.getItem('index');

        if(index == -1) {

            return true;
        }

        priceList.splice(index, 1);
        localStorage.setItem("priceList", JSON.stringify(priceList));
        return true;
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaOffPriceHandlePage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaOffPriceHandlePage").submit();
        var flag = $('#oa-form-oaOffPriceHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaOffPriceHandlePage').validate({
            debug: false
        });

        $('#oa-paydetail-oaOffPriceHandlePage').val(JSON.stringify(priceList));

        $('#oa-form-oaOffPriceHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                //beforeSubmit: showRequest,
                <c:choose>
                    <c:when test="${empty price or empty price.id}">
                        url: 'oa/offprice/addOaOffPrice.do',
                    </c:when>
                    <c:otherwise>
                        url: 'oa/offprice/editOaOffPrice.do',
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
    <form action="" id="oa-form-oaOffPriceHandlePage" method="post">
        <input type="hidden" name="price.id" id="oa-id-oaOffPriceHandlePage" value="${param.id }"/>
        <input type="hidden" name="price.oaid" id="oa-oaid-oaOffPriceHandlePage" value="${param.processid }"/>
        <input type="hidden" name="paydetail" id="oa-paydetail-oaOffPriceHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户<font color="#F00">*</font>
                        <input type="text" class="required" name="" id="oa-customerid2-oaOffPriceHandlePage" value="<c:if test='${not empty customer }'><c:out value='${price.customerid }'/>:<c:out value='${customer.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="price.customerid" id="oa-customerid-oaOffPriceHandlePage" value="${price.customerid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>销售内勤
                        <input type="text" class="" name="" id="oa-indoorstaff2-oaOffPriceHandlePage" value="<c:if test='${not empty indoorstaff }'><c:out value='${price.indoorstaff }'/>:<c:out value='${indoorstaff.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="price.indoorstaff" id="oa-indoorstaff-oaOffPriceHandlePage" value="${price.indoorstaff }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>业务员
                        <input type="text" class="" name="" id="oa-salesuserid2-oaOffPriceHandlePage" value="<c:if test='${not empty salesuser }'><c:out value='${price.salesuserid }'/>:<c:out value='${salesuser.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="price.salesuserid" id="oa-salesuserid-oaOffPriceHandlePage" value="${price.salesuserid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>一次性开单时间<font color="#F00">*</font>
                        <input type="text" class="required dateISO" name="price.pricebegindate" id="oa-pricebegindate-oaOffPriceHandlePage" value="${price.pricebegindate}" data-clear-btn="true"/>
                        <input type="text" class="required dateISO" name="price.priceenddate" id="oa-priceenddate-oaOffPriceHandlePage" value="${price.priceenddate}" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>档期
                        <input type="text" name="price.schedule" id="oa-schedule-oaOffPriceHandlePage" value="<c:out value="${price.schedule }"/>" maxlength="25" data-clear-btn="true"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="price.remark" id="oa-remark-oaOffPriceHandlePage"><c:out value="${price.remark }"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <a href="oa/offprice/phone/oaOffPriceDetailPage.jsp?buyprice=${param.buyprice }&pricetype=${param.pricetype }" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-pricedetail-oaOffPriceHandlePage" data-inset="false">
                        <li data-role="list-divider">特价明细</li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oaOffPriceHandlePage" data-role="footer" data-position="fixed">
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
