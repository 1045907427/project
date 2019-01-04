<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品调价单明细页面(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaGoodsPriceDetailPage" data-dom-cache="true">
    <style type="text/css">
        [readonly] {
            background: #aaa;
        }
    </style>
    <script type="text/javascript">
        $(document).on('pageshow', '#oa-pricedetail-oaGoodsPriceDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var priceList = $.parseJSON(localStorage['priceList']);

                if(index != -1) {

                    var pricedetail = priceList[index];
                    pricedetail.goods = pricedetail.goodsid + ':' + pricedetail.goodsname;

                    $('#oa-form-oaGoodsPriceDetailPage').form('load', pricedetail);

                } else {

                    $('#oa-form-oaGoodsPriceDetailPage').form('load', {});
                    $('#oa-form-oaGoodsPriceDetailPage')[0].reset();
                }
            }

            // 商品
            $('#oa-goods-oaGoodsPriceDetailPage').off().on('click', function() {
                    androidWidget({
                        type: 'goodsWidget',
                        onSelect: 'selectGoods',
                        paramRule: [{field: 'defaultsupplier', op: 'equal', value: $('#oa-supplierid-oaGoodsPriceHandlePage').val()}]
                    });

            })/*.on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-brand-oaGoodsPriceDetailPage').attr('value', v);
            })*/;

            $("#oa-form-oaGoodsPriceDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true
                    }
                }
            });

        });
    </script>
    <form action="" id="oa-form-oaGoodsPriceDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>调价信息</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>商品<font color="#F00">*</font>
                        <textarea class="required" name="goods" id="oa-goods-oaGoodsPriceDetailPage" readonly="readonly"></textarea>
                        <input type="hidden" name="goodsid" id="oa-goodsid-oaGoodsPriceDetailPage" />
                        <input type="hidden" name="goodsname" id="oa-goodsname-oaGoodsPriceDetailPage" />
                        <input type="hidden" name="brand" id="oa-brand-oaGoodsPriceDetailPage" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>条形码
                        <input type="text" class="" data-clear-btn="false" name="barcode" id="oa-barcode-oaGoodsPriceDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>单位
                        <input type="hidden" class="" data-clear-btn="false" name="unitid" id="oa-unitid-oaGoodsPriceDetailPage" readonly="readonly"/>
                        <input type="text" class="" data-clear-btn="false" name="unitname" id="oa-unitname-oaGoodsPriceDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>采购价(原)
                        <input type="number" precision="6" class="number" data-clear-btn="false" name="oldbuytaxprice" id="oa-oldbuytaxprice-oaGoodsPriceDetailPage" money="true" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>采购价(现)<font color="#F00">*</font>
                        <input type="number" precision="6" class="required number" data-clear-btn="true" name="newbuytaxprice" id="oa-newbuytaxprice-oaGoodsPriceDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>基准销售价(原)
                        <input type="number" precision="6" class="number" data-clear-btn="false" name="oldbasesaleprice" id="oa-oldbasesaleprice-oaGoodsPriceDetailPage" money="true" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>基准销售价(现)<font color="#F00">*</font>
                        <input type="number" precision="6" class="required number" data-clear-btn="true" name="newbasesaleprice" id="oa-newbasesaleprice-oaGoodsPriceDetailPage" money="true"/>
                    </label>
                </div>

                <%-- 价格套 --%>
                <c:forEach items="${priceList }" var="item" varStatus="idx">
                    <div class="ui-field-contain">
                        <label>${item.codename }(原)
                            <input type="number" precision="6" class="required number" data-clear-btn="false" name="oldprice${idx.index + 1}" id="oa-oldprice${idx.index + 1}-oaGoodsPriceDetailPage" money="true" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>${item.codename }毛利率(原)%
                            <input type="number" precision="2" class="number" data-clear-btn="false" name="oldpricerate${idx.index + 1}" id="oa-oldpricerate${idx.index + 1}-oaGoodsPriceDetailPage" money="true" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>${item.codename }(现)<font color="#F00">*</font>
                            <input type="number" precision="6" class="required number" data-clear-btn="true" name="newprice${idx.index + 1}" id="oa-newprice${idx.index + 1}-oaGoodsPriceDetailPage" money="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>${item.codename }毛利率(现)%
                            <input type="number" precision="2" class="number" data-clear-btn="false" name="newpricerate${idx.index + 1}" id="oa-newpricerate${idx.index + 1}-oaGoodsPriceDetailPage" money="true" readonly="readonly"/>
                        </label>
                    </div>
                </c:forEach>
                <div class="ui-field-contain">
                    <label>说明
                        <input type="text" class="" data-clear-btn="true" name="remark" id="oa-remark-oaGoodsPriceDetailPage"/>
                    </label>
                </div>
            </div>
         </div>
        <div id="oa-footer-oaGoodsPriceDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removePrice();" id="oa-remove-oaGoodsPriceDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return savePrice();" id="oa-ok-oaGoodsPriceDetailPage" data-role="button" data-icon="check">确定</a>
        </div>

    </form>
</div>
</body>
</html>
