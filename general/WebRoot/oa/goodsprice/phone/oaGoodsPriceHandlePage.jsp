<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>商品调价单(手机)</title>
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

    $(document).on('pageshow', '#oa-pricedetail-oaGoodsPriceDetailPage,#main', function(e) {

        $('[readonly=readonly]').css({background: '#f1f1f1'});
    });

    $(document).on('pageshow', '#main', function(e) {

        refreshPrices(e);

        numberPrecision();
    });

    $(function() {

        // 供应商
        $('#oa-supplierid2-oaGoodsPriceHandlePage').on('click', function() {

            androidWidget({
                type: 'supplierWidget',
                onSelect: 'selectSupplier'
            });
        }).on('change', function() {

            var v = $(this).val();
            $(this).attr('value', v);
            $('#oa-supplierid-oaGoodsPriceHandlePage').attr('value', (v || ':').split(':')[0]);
        });

        // 新价格变动时，计算毛利率
        $(document).on('change', '#oa-newbuytaxprice-oaGoodsPriceDetailPage', function() {

            computeProfitrate(1, 1);
            computeProfitrate(1, 2);
            computeProfitrate(1, 3);
            computeProfitrate(1, 4);
        });
        <c:forEach begin="1" end="${price_count }" step="1" varStatus="idx">
        $(document).on('change', '#oa-newprice${idx.index }-oaGoodsPriceDetailPage', function() {

            computeProfitrate(1, ${idx.index });
        });
        </c:forEach>

    });

    /**
     * 选择供应商
     */
    function selectSupplier(data) {

        $('#oa-supplierid2-oaGoodsPriceHandlePage').val(data.id + ':' + data.name);
        $('#oa-supplierid-oaGoodsPriceHandlePage').val(data.id);

        $('#oa-supplierid2-oaGoodsPriceHandlePage').blur();
        $('#oa-supplierid2-oaGoodsPriceHandlePage').change();
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

        $('#oa-goods-oaGoodsPriceDetailPage').val(data.id + ':' + data.name);
        $('#oa-goodsid-oaGoodsPriceDetailPage').val(data.id);
        $('#oa-goodsname-oaGoodsPriceDetailPage').val(data.name);

        $("#oa-barcode-oaGoodsPriceDetailPage").val(data.barcode);
        $("#oa-unitid-oaGoodsPriceDetailPage").val(data.mainunit);
        $("#oa-unitname-oaGoodsPriceDetailPage").val(data.mainunitName);
        $("#oa-oldbuytaxprice-oaGoodsPriceDetailPage").val(data.highestbuyprice);//最高采购价
        $("#oa-oldbasesaleprice-oaGoodsPriceDetailPage").val(data.basesaleprice);
        $("#oa-newbuytaxprice-oaGoodsPriceDetailPage").val(data.highestbuyprice);//最高采购价
        $("#oa-newbasesaleprice-oaGoodsPriceDetailPage").val(data.basesaleprice);

        loading();
        $.ajax({
            type: 'post',
            url: 'oa/common/selectPriceListByGoodsid.do',
            data: {id: data.id},
            dataType: 'json',
            success: function(json) {

                loaded();
                if(json.priceList != null) {

                    for(var i in json.priceList) {

                        var price = json.priceList[i].taxprice || '0.00';
                        var index = parseInt(i) + 1;

                        $('#oa-oldprice' + index + '-oaGoodsPriceDetailPage').val(formatterMoney(price, 4));
                        $('#oa-newprice' + index + '-oaGoodsPriceDetailPage').val(formatterMoney(price, 4));

                        computeProfitrate(0, index);
                        computeProfitrate(1, index);
                    }
                }
            }
        });

        return true;
    }

    /**
     * 计算毛利率
     * @param flag 0:old; 1:new
     * @param idx index
     */
    function computeProfitrate(flag, idx) {

        var priceid = 'oa-oldprice' + idx + '-oaGoodsPriceDetailPage';
        var costid = 'oa-oldbuytaxprice-oaGoodsPriceDetailPage';
        var rateid = 'oa-oldpricerate' + idx + '-oaGoodsPriceDetailPage';
        if(flag == 1) {

            priceid = 'oa-newprice' + idx + '-oaGoodsPriceDetailPage';
            costid = 'oa-newbuytaxprice-oaGoodsPriceDetailPage';
            var rateid = 'oa-newpricerate' + idx + '-oaGoodsPriceDetailPage';
        }

        var price = parseFloat($('#' + priceid).val() || '0.0000');
        var cost = parseFloat($('#' + costid).val() || '0.000000');

        if(cost != 0) {

            var rate = formatterMoney((price - cost) * 100 / price);

            $('#' + rateid).val(rate);
        }

    }

    /**
     * 保存调价明细
     */
    function savePrice(e) {

        <c:forEach begin="1" end="${price_count }" step="1" varStatus="idx">
            computeProfitrate(0, ${idx.index });
            computeProfitrate(1, ${idx.index });
        </c:forEach>

        $('#oa-form-oaGoodsPriceDetailPage').validate({
            focusInvalid: true,
            debug: true
        });

        var flag = $('#oa-form-oaGoodsPriceDetailPage').validate().form();

        if(!flag) {

            return flag;
        }

        //computeProfitrate();
        var data = $('#oa-form-oaGoodsPriceDetailPage').serializeJSON();

        $('#oa-form-oaGoodsPriceDetailPage').form('clear');
        $('#oa-form-oaGoodsPriceDetailPage')[0].reset();
        var index = parseInt(localStorage.getItem('index'));

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

    /*
     * 刷新价格明细
     */
    function refreshPrices(e) {

        var priceList = $.parseJSON(localStorage.getItem("priceList"));

        var html = new Array();

        for (var i in priceList) {

            var price = priceList[i];

            html.push('<li>');
            html.push('<a href="oa/goodsprice/phone/oaGoodsPriceDetailPage.do" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100; padding: 3px;">');
            html.push('<table>');

            html.push('<tr>');
            html.push('<th>商品:</th>');
            html.push('<td><span>' + price.goodsid + ':' + price.goodsname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>条形码:</th>');
            html.push('<td><span>' + price.barcode + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>单位:</th>');
            html.push('<td><span>' + price.unitname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>采购价(原):</th>');
            html.push('<td><span precision="6">' + price.oldbuytaxprice + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>采购价(现):</th>');
            if(formatterMoney(price.newbuytaxprice, 6) == formatterMoney(price.oldbuytaxprice, 6)) {

                html.push('<td><span precision="6">' + price.newbuytaxprice + '</span></td>');
            } else {

                html.push('<td><font color="#f00"><span precision="6">' + price.newbuytaxprice + '</span></font></td>');
            }
            //html.push('<td><span precision="6">' + price.newbuytaxprice + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>基准销售价(原):</th>');
            html.push('<td><span precision="6">' + price.oldbasesaleprice + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>基准销售价(现):</th>');
            if(formatterMoney(price.newbasesaleprice, 6) == formatterMoney(price.oldbasesaleprice, 6)) {

                html.push('<td><span precision="2">' + price.newbasesaleprice + '</span></td>');
            } else {

                html.push('<td><font color="#f00"><span precision="2">' + price.newbasesaleprice + '</span></font></td>');
            }
            //html.push('<td><span precision="2">' + price.newbasesaleprice + '</span></td>');
            html.push('</tr>');

            <c:forEach items="${priceList }" var="item" varStatus="idx">

            html.push('<tr>');
            html.push('<th><c:out value="${item.codename }" />(原):</th>');
            html.push('<td><span precision="6">' + price.oldprice${idx.index + 1 } + '</span></td>');
            html.push('</tr>');

            (function(){

                var oldprice = price.oldprice${idx.index + 1 } || 0.0000;
                var oldcost = price.oldbuytaxprice || 0.000000;

                if(oldcost != 0) {

                    var rate = formatterMoney((oldprice - oldcost) * 100 / oldprice);
                    price.oldpricerate${idx.index + 1 } = rate;

                    html.push('<tr>');
                    html.push('<th><c:out value="${item.codename }" />毛利率(原)%:</th>');
                    html.push('<td><span precision="2">' + rate + '</span></td>');
                    html.push('</tr>');
                }

            })();


            html.push('<tr>');
            html.push('<th><c:out value="${item.codename }" />(现):</th>');
            if(formatterMoney(price.newprice${idx.index + 1 }, 6) == formatterMoney(price.oldprice${idx.index + 1 }, 6)) {

                html.push('<td><span precision="6">' + price.newprice${idx.index + 1 } + '</span></td>');
            } else {

                html.push('<td><font color="#f00"><span precision="4">' + price.newprice${idx.index + 1 } + '</span></font></td>');
            }
            //html.push('<td><span precision="4">' + price.newprice${idx.index + 1 } + '</span></td>');
            html.push('</tr>');

            (function(){

                var newprice = price.newprice${idx.index + 1 } || 0.0000;
                var newcost = price.newbuytaxprice || 0.000000;

                if(newcost != 0) {

                    var rate = formatterMoney((newprice - newcost) * 100 / newprice);
                    price.newpricerate${idx.index + 1 } = rate;

                    html.push('<tr>');
                    html.push('<th><c:out value="${item.codename }" />毛利率(现)%:</th>');
                    html.push('<td><span precision="2">' + rate + '</span></td>');
                    html.push('</tr>');
                }

            })();

            </c:forEach>

            html.push('<tr>');
            html.push('<th>说明:</th>');
            html.push('<td><span>' + price.remark + '</span></td>');
            html.push('</tr>');

            html.push('</table>');
            html.push('</a>');
            html.push('</li>');
        }

        localStorage.setItem('priceList', JSON.stringify(priceList));

        $('#oa-pricedetail-oaGoodsPriceHandlePage').find(':not(:eq(0))').remove();
        $('#oa-pricedetail-oaGoodsPriceHandlePage').append(html.join(''));
        numberPrecision();

        try {

            $('#oa-pricedetail-oaGoodsPriceHandlePage').listview('refresh');
        } catch(e) {}
    }

    /**
    * 提交表单
    * @param call
    * @param args
    */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaGoodsPriceHandlePage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaGoodsPriceHandlePage").submit();
        var flag = $('#oa-form-oaGoodsPriceHandlePage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaGoodsPriceHandlePage').validate({
            debug: false
        });

        $('#oa-goodpricedetail-oaGoodsPriceHandlePage').val(JSON.stringify(priceList));

        $('#oa-form-oaGoodsPriceHandlePage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                <c:choose>
                    <c:when test="${empty price or empty price.id}">
                        url: 'oa/goodsprice/addOaGoodsPrice.do',
                    </c:when>
                    <c:otherwise>
                        url: 'oa/goodsprice/editOaGoodsPrice.do',
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
    <form action="" id="oa-form-oaGoodsPriceHandlePage" method="post">
        <input type="hidden" name="price.id" id="oa-id-oaGoodsPriceHandlePage" value="${param.id }"/>
        <input type="hidden" name="price.oaid" id="oa-oaid-oaGoodsPriceHandlePage" value="${param.processid }"/>
        <input type="hidden" name="goodpricedetail" id="oa-goodpricedetail-oaGoodsPriceHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>供应商<font color="#F00">*</font>
                        <input type="text" class="required" name="price.supplierid2" id="oa-supplierid2-oaGoodsPriceHandlePage" value="<c:if test='${not empty supplier }'><c:out value='${price.supplierid }'/>:<c:out value='${supplier.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="price.supplierid" id="oa-supplierid-oaGoodsPriceHandlePage" value="${price.supplierid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="price.remark" id="oa-remark-oaGoodsPriceHandlePage"><c:out value="${price.remark }"/></textarea>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <a href="oa/goodsprice/phone/oaGoodsPriceDetailPage.do" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-pricedetail-oaGoodsPriceHandlePage" data-inset="false">
                        <li data-role="list-divider">调价明细</li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
    <div id="oa-footer-oaGoodsPriceHandlePage" data-role="footer" data-position="fixed">
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
