<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>批量特价审批单明细页面(手机)</title>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaOffPriceDetailPage" data-dom-cache="true">
    <script type="text/javascript">
        $(document).on('pageshow', '#oa-pricedetail-oaOffPriceDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var priceList = $.parseJSON(localStorage['priceList']);

                if(index != -1) {

                    var pricedetail = priceList[index];
                    pricedetail.goods = pricedetail.goodsid + ':' + pricedetail.goodsname;

                    $('#oa-form-oaOffPriceDetailPage').form('load', pricedetail);

                } else {

                    $('#oa-form-oaOffPriceDetailPage').form('load', {});
                    $('#oa-form-oaOffPriceDetailPage')[0].reset();
                }

            }

            // 商品
            $('#oa-goods-oaOffPriceDetailPage').off().on('click', function() {
                    androidWidget({
                        type: 'goodsWidget',
                        //referwid : "RL_T_BASE_GOODS_BRAND",
                        onSelect: 'selectGoods'
                    });

            })/*.on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-brand-oaOffPriceDetailPage').attr('value', v);
            })*/;

            $("#oa-form-oaOffPriceDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true
                    }
                }
            });

        });
    </script>
    <form action="" id="oa-form-oaOffPriceDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>特价信息</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>商品
                        <input type="text" class="required" name="goods" id="oa-goods-oaOffPriceDetailPage" readonly="readonly"/>
                        <input type="hidden" name="goodsid" id="oa-goodsid-oaOffPriceDetailPage" />
                        <input type="hidden" name="goodsname" id="oa-goodsname-oaOffPriceDetailPage" />
                        <input type="hidden" name="barcode" id="oa-barcode-oaOffPriceDetailPage" />
                        <input type="hidden" name="brand" id="oa-brand-oaOffPriceDetailPage" />
                    </label>
                </div>
                <%--5185 6.7&通用版：批量特价申请单可以配置哪个节点能否查看采购进价--%>
                <c:choose>
                    <c:when test="${param.buyprice eq '1' or param.buyprice eq 'true'}">
                        <div class="ui-field-contain">
                            <label>进价
                                <input type="number" precision="2" class="number" data-clear-btn="false" name="buyprice" id="oa-buyprice-oaOffPriceDetailPage" money="true" readonly="readonly"/>
                            </label>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="ui-field-contain" style="display: none;">
                            <label>进价
                                <input type="number" precision="2" class="number" data-clear-btn="false" name="buyprice" id="oa-buyprice-oaOffPriceDetailPage" money="true" readonly="readonly"/>
                            </label>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="ui-field-contain">
                    <label>原价
                        <input type="number" precision="2" class="number" data-clear-btn="false" name="oldprice" id="oa-oldprice-oaOffPriceDetailPage" money="true" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>特价
                        <input type="number" precision="2" class="required number" data-clear-btn="true" name="offprice" id="oa-offprice-oaOffPriceDetailPage" money="true"/>
                    </label>
                </div>
                <c:choose>
                    <c:when test="${param.buyprice eq '1' or param.buyprice eq 'true'}">
                        <div class="ui-field-contain">
                            <label>毛利率%
                                <input type="number" precision="2" class="number" data-clear-btn="false" name="profitrate" id="oa-profitrate-oaOffPriceDetailPage" money="true" readonly="readonly"/>
                            </label>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="ui-field-contain" style="display: none;">
                            <label>毛利率%
                                <input type="number" precision="2" class="number" data-clear-btn="false" name="profitrate" id="oa-profitrate-oaOffPriceDetailPage" money="true" readonly="readonly"/>
                            </label>
                        </div>
                    </c:otherwise>
                </c:choose>
                <div class="ui-field-contain">
                    <label>本次订单数量
                        <input type="text" class="" data-clear-btn="true" name="ordernum" id="oa-ordernum-oaOffPriceDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>说明
                        <input type="text" class="" data-clear-btn="true" name="remark" id="oa-remark-oaOffPriceDetailPage"/>
                    </label>
                </div>
            </div>
         </div>
        <div id="oa-footer-oaOffPriceDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removePrice();" id="oa-remove-oaOffPriceDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return savePrice();" id="oa-ok-oaOffPriceDetailPage" data-role="button" data-icon="check">确定</a>
        </div>

        </form>

    </div>

</body>
</html>
