<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>商品档案详情页面(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">
        $(document).on('pageshow', '#main', function(e) {
            var formaterNumEach = $(".formaterNum");
            $.each(formaterNumEach,function (index,domEle){
                domEle.value = Number(domEle.value);
            });
        });
    </script>
</head>
<body>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>商品详情</h1>
    </div>
    <div id="goodsinfo-phone-div" class="ui-corner-all custom-corners">
        <div class="ui-body ui-body-a">
        <div class="ui-field-contain">
            编号:<input type="text" readonly="readonly"  precision="2" value="${goodsInfo.id}" id="goodsInfo-id-baseInfo"  />
        </div>
        <div class="ui-field-contain">
            助记符:<input type="text" readonly="readonly" value="${goodsInfo.spell}" id="goodsInfo-spell-baseInfo" />
        </div>
        <div class="ui-field-contain">
            名称:<input type="text" readonly="readonly"  value="${goodsInfo.name}" id="goodsInfo-name-baseInfo" />
        </div>
        <div class="ui-field-contain">
            主单位:<input type="text" readonly="readonly"  value="${goodsInfo.mainunitName}" id="goodsInfo-mainunitName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            箱装量:<input type="text" readonly="readonly" class="formaterNum" value="${goodsInfo.boxnum}" id="goodsInfo-boxnum-baseInfo" />
        </div>
        <div class="ui-field-contain">
            辅单位:<input type="text" readonly="readonly"  value="${goodsInfo.auxunitname}" id="goodsInfo-auxunitname-baseInfo" />
        </div>
        <div class="ui-field-contain">
            规格型号:<input type="text" readonly="readonly"  value="${goodsInfo.model}" id="goodsInfo-model-baseInfo" />
        </div>
        <div class="ui-field-contain">
            条形码:<input type="text" readonly="readonly"  value="${goodsInfo.barcode}" id="goodsInfo-barcode-baseInfo" />
        </div>
        <div class="ui-field-contain">
            箱装条形码:<input type="text" readonly="readonly"  value="${goodsInfo.boxbarcode}" id="goodsInfo-boxbarcode-baseInfo" />
        </div>
        <%--<div class="ui-field-contain">--%>
            <%--商品分类:<input type="text" readonly="readonly"  value="${goodsInfo.defaultsortName}" id="goodsInfo-defaultsortName-baseInfo" />--%>
        <%--</div>--%>
        <div class="ui-field-contain">
            商品品牌:<input type="text" readonly="readonly"  value="${goodsInfo.brandName}" id="goodsInfo-brandName-baseInfo" />
        </div>
        <div class="ui-field-contain">
           商品类型:<input type="text" readonly="readonly"  value="${goodsInfo.goodstypeName}" id="goodsInfo-goodstypeName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            默认供应商:<input type="text" readonly="readonly"  value="${goodsInfo.defaultsupplierName}" id="goodsInfo-defaultsupplierName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            第二供应商:<input type="text" readonly="readonly"  value="${goodsInfo.secondsuppliername}" id="goodsInfo-secondsuppliername-baseInfo" />
        </div>
        <div class="ui-field-contain">
            长度:<input type="text" readonly="readonly"  value="${goodsInfo.glength}" id="goodsInfo-glength-baseInfo" />
        </div>
        <div class="ui-field-contain">
            高度:<input type="text" readonly="readonly"  value="${goodsInfo.gwidth}" id="goodsInfo-gwidth-baseInfo" />
        </div>
        <div class="ui-field-contain">
            宽度:<input type="text" readonly="readonly"  value="${goodsInfo.ghight}" id="goodsInfo-ghight-baseInfo" />
        </div>
        <div class="ui-field-contain">
            箱重:<input type="text" readonly="readonly"  value="${goodsInfo.totalweight}" id="goodsInfo-totalweight-baseInfo" />
        </div>
        <div class="ui-field-contain">
            箱体积:<input type="text" readonly="readonly"  value="${goodsInfo.totalvolume}" id="goodsInfo-totalvolume-baseInfo" />
        </div>
        <div class="ui-field-contain">
            默认仓库:<input type="text" readonly="readonly"  value="${goodsInfo.storageName}" id="goodsInfo-storageName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            库位管理:<input type="text" readonly="readonly"  value="${goodsInfo.isstoragelocationname}" id="goodsInfo-isstoragelocationname-baseInfo" />
        </div>
        <div class="ui-field-contain">
            购销类型:<input type="text" readonly="readonly"  value="${goodsInfo.bstypeName}" id="goodsInfo-bstypeName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            批次管理:<input type="text" readonly="readonly"  value="${goodsInfo.isbatchname}" id="goodsInfo-isbatchname-baseInfo" />
        </div>
        <div class="ui-field-contain">
            默认采购员:<input type="text" readonly="readonly"  value="${goodsInfo.defaultbuyerName}" id="goodsInfo-defaultbuyerName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            保质期管理:<input type="text" readonly="readonly"  value="${goodsInfo.isshelflifename}" id="goodsInfo-isshelflifename-baseInfo" />
        </div>
        <div class="ui-field-contain">
            保质期:<input type="text" readonly="readonly"  value="${goodsInfo.shelflifedetail}" id="goodsInfo-shelflifedetail-baseInfo" />
        </div>
        <div class="ui-field-contain">
            默认税种:<input type="text" readonly="readonly"  value="${goodsInfo.defaulttaxtypeName}" id="goodsInfo-defaulttaxtypeName-baseInfo" />
        </div>
        <div class="ui-field-contain">
            备注:<input type="text" readonly="readonly"  value="${goodsInfo.remark}" id="goodsInfo-remark-baseInfo" />
        </div>

       </div>
    </div>
</div>
</body>
</html>
