<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>明细</title>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaAccessPriceDetailPage" data-dom-cache="true">
    <script type="text/javascript">

        $(document).on('pageshow', '#oa-pricedetail-oaAccessPriceDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var prices = $.parseJSON(localStorage['prices']);

                if(index != -1) {

                    var price = prices[index];

                    price.buyprice = formatterMoney(price.buyprice);
                    price.factoryprice = formatterMoney(price.factoryprice);
                    price.myprice = formatterMoney(price.myprice);
                    price.oldprice = formatterMoney(price.oldprice);
                    price.newprice = formatterMoney(price.newprice);
                    price.rate = formatterMoney(price.rate);

                    price.goodsid2 = price.goodsid + ':' + price.goodsname;

                    $('#oa-form-oaAccessPriceDetailPage').form('load', price);

                } else {

                    $('#oa-form-oaAccessPriceDetailPage').form('load', {});
                    $('#oa-form-oaAccessPriceDetailPage')[0].reset()
                }

            }

            // 商品控件
            $('#oa-goodsid2-oaAccessPriceDetailPage').off().on('click', function() {

                androidWidget({
                    type: 'goodsWidget',
                    onSelect: 'selectGoods',
                    paramRule: [{field: 'defaultsupplier', op: 'equal', value: $('#oa-supplierid-oaAccessHandlePage').val()}]
                });
            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-goodsid-oaAccessPriceDetailPage').attr('value', v);
            });
            //验证
            $("#oa-form-oaAccessPriceDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true
                    }
                }
            });

            // 计算自理金额
            $('#oa-oldprice-oaAccessPriceDetailPage').on('change', computeMyprice);
            $('#oa-newprice-oaAccessPriceDetailPage').on('change', computeMyprice);
            $('#oa-factoryprice-oaAccessPriceDetailPage').on('change', computeMyprice);

            // 计算毛利率
            $('#oa-newprice-oaAccessPriceDetailPage').on('change', computeProfit);
            $('#oa-buyprice-oaAccessPriceDetailPage').on('change', computeProfit);

            // 计算结算金额
            $('#oa-factoryamount-oaAccessHandlePage').on('change', computeBranchaccount);
            $('#oa-payamount-oaAccessHandlePage').on('change', computeBranchaccount);

        });

    </script>
    <form action="" id="oa-form-oaAccessPriceDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>价格明细</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>商品
                        <input type="text" name="goodsid2" id="oa-goodsid2-oaAccessPriceDetailPage"/>
                        <input type="hidden" name="goodsid" id="oa-goodsid-oaAccessPriceDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain" <c:if test="${param.buyprice eq '0'}">style="display: none"</c:if> >
                    <label>进货价
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="buyprice" id="oa-buyprice-oaAccessPriceDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>工厂让利
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="factoryprice" id="oa-factoryprice-oaAccessPriceDetailPage">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>自理
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="myprice" id="oa-myprice-oaAccessPriceDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>原价
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="oldprice" id="oa-oldprice-oaAccessPriceDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>现价
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="newprice" id="oa-newprice-oaAccessPriceDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>毛利率%
                        <input type="number" precision="2" class="number" data-clear-btn="false" name="rate" id="oa-rate-oaAccessPriceDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>门店出货
                        <input type="text" name="senddetail" id="oa-senddetail-oaAccessPriceDetailPage"/>
                    </label>
                </div>
            </div>
        </div>
        <div id="oa-footer-oaAccessPriceDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removePrice();" id="oa-remove-oaAccessPriceDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return savePrice();" id="oa-ok-oaAccessPriceDetailPage" data-role="button" data-icon="check">确定</a>
        </div>
    </form>
</div>
</body>
</html>



