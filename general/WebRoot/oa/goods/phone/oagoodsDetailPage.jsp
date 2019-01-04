<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品调价单明细页面(手机)</title>
</head>
<body>
<div data-role="page" id="oa-goodsdetail-oagoodsDetailPage" data-dom-cache="true">
    <script type="text/javascript">
        $(document).on('pageshow', '#oa-goodsdetail-oagoodsDetailPage', function(e) {

            var index = localStorage.getItem('index');
            var goodsList = $.parseJSON(localStorage['goodsList']);

            if(isWidgetPageBack()) {
                return false;
            }

            if(index >= 0) {

                var goodsdetail = goodsList[index];

                goodsdetail.brandid2 = goodsdetail.brandid2 || (goodsdetail.brandid + ':' + goodsdetail.brandname);
                goodsdetail.goodssort2 = goodsdetail.goodssort2 || (goodsdetail.goodssort + ':' + goodsdetail.goodssortname);
                goodsdetail.unitid2 = goodsdetail.unitid2 || (goodsdetail.unitid + ':' + goodsdetail.unitname);
                goodsdetail.auxunitid2 = goodsdetail.auxunitid2 || (goodsdetail.auxunitid + ':' + goodsdetail.auxunitname);
                goodsdetail.storageid2 = goodsdetail.storageid2 || (goodsdetail.storageid + ':' + goodsdetail.storagename);
                goodsdetail.taxtype2 = goodsdetail.taxtype2 || (goodsdetail.taxtype + ':' + goodsdetail.taxname);

                $('#oa-form-oagoodsDetailPage').form('load', goodsdetail);

                <c:forEach items="${priceList }" varStatus="idx">
                computeProfitrate(${idx.index + 1});
                </c:forEach>

            } else if(index == -1) {

                $('#oa-form-oagoodsDetailPage')[0].reset();
                $('#oa-form-oagoodsDetailPage').form('load', {
                    auxunitid2: '<c:out value="${defaultAuxunitid }" />:<c:out value="${defaultAuxunit.name }" />',
                    auxunitid: '<c:out value="${defaultAuxunitid }" />',
                    taxtype2: '<c:out value="${defaultTaxtype }" />:<c:out value="${defaultTex.name }" />',
                    taxtype: '<c:out value="${defaultTaxtype }" />'
                });
            } else {
                localStorage.removeItem('index');
            }

            $("#oa-form-oagoodsDetailPage").validate({
                focusInvalid: true,
                debug: true,
                rules: {
                    money: {
                        money: true
                    },
                    goodsid: {
                        required: true,
                        goodsidExisted: '${param.id }',
                        maxByteLength: 20
                    },
                    goodsname: {
                        maxByteLength: 100
                    },
                    barcode: {
                        maxByteLength: 50
                    },
                    boxbarcode: {
                        maxByteLength: 50
                    },
                    boxnum: {
                        max: 99999999,
                        min: 0
                    },
                    totalweight: {
                        max: 99999999,
                        min: 0
                    },
                    glength: {
                        max: 99999999,
                        min: 0
                    },
                    gwidth: {
                        max: 99999999,
                        min: 0
                    },
                    gheight: {
                        max: 99999999,
                        min: 0
                    },
                    totalvolume: {
                        max: 99999999,
                        min: 0
                    },
                    buytaxprice: {
                        max: 99999999,
                        min: 0
                    },
                    basesaleprice: {
                        max: 99999999,
                        min: 0
                    },
                    <c:forEach items="${priceList }" var="item" varStatus="idx">
                    price${idx.index + 1 }: {
                        max: 99999999,
                        min: 0
                    },
                    </c:forEach>
                    maxByteLength: 200
                },
                messages: {
                    goodsid: {
                        required: '必须填写'
                    }
                }
            });
        });

    </script>
    <form action="" id="oa-form-oagoodsDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>商品详情</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>商品编码<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="false" name="goodsid" id="oa-goodsid-oagoodsDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>商品名称<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="false" name="goodsname" id="oa-goodsname-oagoodsDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>品牌<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="true" name="brandid2" id="oa-brandid2-oagoodsDetailPage" onclick="javascript: androidWidget({name: 't_oa_goods_detail', col: 'brandid',onSelect: 'selectBrand'});" readonly="readonly"/>
                        <input type="hidden" class="" data-clear-btn="false" name="brandid" id="oa-brandid-oagoodsDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>条形码<font color="#F00">*</font>
                        <input type="text" class="" data-clear-btn="true" name="barcode" id="oa-barcode-oagoodsDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>箱装条形码
                        <input type="text" class="" data-clear-btn="true" name="boxbarcode" id="oa-boxbarcode-oagoodsDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>商品分类<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="true" name="goodssort2" id="oa-goodssort2-oagoodsDetailPage" onclick="javascript:androidWidget({name: 't_oa_goods_detail',col: 'goodssort',onSelect: 'selectGoodssort'});" readonly="readonly"/>
                        <input type="hidden" class="" data-clear-btn="false" name="goodssort" id="oa-goodssort-oagoodsDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>主单位<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="true" name="unitid2" id="oa-unitid2-oagoodsDetailPage" onclick="javascript:androidWidget({name: 't_oa_goods_detail',col: 'unitid',onSelect: 'selectUnit'});" readonly="readonly"/>
                        <input type="hidden" class="" data-clear-btn="false" name="unitid" id="oa-unitid-oagoodsDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>辅单位<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="true" name="auxunitid2" id="oa-auxunitid2-oagoodsDetailPage" onclick="javascript:androidWidget({name: 't_oa_goods_detail',col: 'unitid',onSelect: 'selectAuxunitid'});" readonly="readonly"/>
                        <input type="hidden" class="" data-clear-btn="false" name="auxunitid" id="oa-auxunitid-oagoodsDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>规格型号
                        <input type="text" class="" data-clear-btn="false" name="model" id="oa-model-oagoodsDetailPage" maxlength="66"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>箱装量<font color="#F00">*</font>
                        <input type="number" precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>" class="required number" data-clear-btn="true" name="boxnum" id="oa-boxnum-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>箱重<font color="#F00">*</font>
                        <input type="number" precision="2" class="required number" data-clear-btn="true" name="totalweight" id="oa-totalweight-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>长度(m)
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="glength" id="oa-glength-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>宽度(m)
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="gwidth" id="oa-gwidth-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>高度(m)
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="gheight" id="oa-gheight-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>箱体积(m<sup>3</sup>)<font color="#F00">*</font>
                        <input type="number" precision="6" class="required number" data-clear-btn="true" name="totalvolume" id="oa-totalvolume-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>仓库<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="true" name="storageid2" id="oa-storageid2-oagoodsDetailPage" onclick="javascript:androidWidget({name: 't_oa_goods_detail',col: 'storageid',onSelect: 'selectStorage'});" readonly="readonly"/>
                        <input type="hidden" class="" data-clear-btn="false" name="storageid" id="oa-storageid-oagoodsDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>最高采购价<font color="#F00">*</font>
                        <input type="number" precision="6" class="required number" data-clear-btn="true" name="buytaxprice" id="oa-buytaxprice-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>基准销售价<font color="#F00">*</font>
                        <input type="number" precision="6" class="required number" data-clear-btn="true" name="basesaleprice" id="oa-basesaleprice-oagoodsDetailPage" money="true" />
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>税种<font color="#F00">*</font>
                        <input type="text" class="required" data-clear-btn="true" name="taxtype2" id="oa-taxtype2-oagoodsDetailPage" onclick="javascript:androidWidget({name: 't_oa_goods_detail',col: 'taxtype',onSelect: 'selectTaxtype'});" readonly="readonly"/>
                        <input type="hidden" class="" data-clear-btn="false" name="taxtype" id="oa-taxtype-oagoodsDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <c:forEach items="${priceList }" var="item" varStatus="idx">
                    <div class="ui-field-contain">
                        <label><c:out value="${item.codename }"/><font color="#F00">*</font>
                            <input type="number" precision="6" class="required number" data-clear-btn="true" name="price${idx.index + 1}" id="oa-price${idx.index + 1}-oagoodsDetailPage" money="true" />
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label><c:out value="${item.codename }"/>毛利率%
                            <input type="number" precision="2" class="required number" data-clear-btn="false" name="pricerate${idx.index + 1}" id="oa-pricerate${idx.index + 1}-oagoodsDetailPage" money="true" readonly="readonly"/>
                        </label>
                    </div>
                </c:forEach>
                <div class="ui-field-contain">
                    <label>产地
                        <input type="text" class="" data-clear-btn="true" name="productfield" id="oa-productfield-oagoodsDetailPage" maxlength="20"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    保质期
                    <div class="ui-grid-a">
                        <div class="ui-block-a">
                            <input type="text" class="number" data-clear-btn="true" name="shelflife" id="oa-shelflife-oagoodsDetailPage" maxlength="20" style="width: 50%"/>
                        </div>
                        <div class="ui-block-b">
                            <select name="shelflifeunit" id="oa-shelflifeunit-oagoodsDetailPage" style="width: 100%;">
                                <option></option>
                                <option value="1">天</option>
                                <option value="2">周</option>
                                <option value="3">月</option>
                                <option value="4">年</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="ui-field-contain">
                    <label>说明
                        <input type="text" class="" data-clear-btn="true" name="remark" id="oa-remark-oagoodsDetailPage" maxlength="100"/>
                    </label>
                </div>
            </div>
        </div>
        <div id="oa-footer-oagoodsDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removeGoods();" id="oa-remove-oagoodsDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return saveGoods();" id="oa-ok-oagoodsDetailPage" data-role="button" data-icon="check">确定</a>
        </div>

    </form>
</div>
</body>
</html>
