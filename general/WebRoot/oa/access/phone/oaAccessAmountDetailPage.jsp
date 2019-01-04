<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>明细</title>
</head>
<body>
<div data-role="page" id="oa-amountdetail-oaAccessAmountDetailPage" data-dom-cache="true">
    <script type="text/javascript">

        $(document).on('pageshow', '#oa-amountdetail-oaAccessAmountDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                // var index = $(this).attr('index');
                var index = localStorage.getItem('index');
                var amounts = $.parseJSON(localStorage['amounts']);

                if(index != -1) {

                    var amount = amounts[index];

                    amount.difprice = formatterMoney(amount.difprice || '0.00');
                    amount.unitnum = formatterMoney(amount.unitnum || '0', <%=BillGoodsNumDecimalLenUtils.decimalLen %>);
                    amount.auxnum = formatterMoney(amount.auxnum || '0');
                    amount.auxremainder = formatterMoney(amount.auxremainder || '0', <%=BillGoodsNumDecimalLenUtils.decimalLen %>);
                    amount.auxdetail = formatterMoney(amount.auxdetail || '0');
                    amount.amount = formatterMoney(amount.amount || '0.00');
                    amount.erpnum = formatterMoney(amount.erpnum || '0', <%=BillGoodsNumDecimalLenUtils.decimalLen %>);
                    amount.downamount = formatterMoney(amount.downamount || '0.00');

                    amount.goodsid2 = amount.goodsid + ':' + amount.goodsname;

                    $('#oa-form-oaAccessAmountDetailPage').form('load', amount);

                    converseAmount();
                    computeAmount();
                    computeDownamount();

                    $('#oa-unitnum-oaAccessAmountDetailPage').off().on('change', function() {

                        converseAmount();
                        computeAmount();
                        computeDownamount();
                    });
                }
            }

        });

    </script>
    <form action="" id="oa-form-oaAccessAmountDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>数量明细</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>商品
                        <input type="text" name="goodsid2" id="oa-goodsid2-oaAccessAmountDetailPage" readonly="readonly"/>
                        <input type="hidden" name="goodsid" id="oa-goodsid-oaAccessAmountDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>单位差价
                        <input type="number" class="number" data-clear-btn="false" name="difprice" id="oa-difprice-oaAccessAmountDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>数量
                        <input type="number" class="number" precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>" data-clear-btn="true" name="unitnum" id="oa-unitnum-oaAccessAmountDetailPage"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>辅数量(整)
                        <input type="number" class="number" data-clear-btn="false" name="auxnum" id="oa-auxnum-oaAccessAmountDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>辅数量(余)
                        <input type="number" class="number" precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>" data-clear-btn="false" name="auxremainder" id="oa-auxremainder-oaAccessAmountDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>差价金额
                        <input type="number" class="number" data-clear-btn="false" name="amount" id="oa-amount-oaAccessAmountDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>ERP数量
                        <input type="number" class="number" precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>" data-clear-btn="false" name="erpnum" id="oa-erpnum-oaAccessAmountDetailPage" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>降价金额
                        <input type="number" class="number" data-clear-btn="false" name="downamount" id="oa-downamount-oaAccessAmountDetailPage" readonly="readonly"/>
                    </label>
                </div>
            </div>
        </div>
        <div id="oa-footer-oaAccessAmountDetailPage" data-role="footer" data-position="fixed" data-fullscreen="false">
            <a href="#main" onclick="javascript:return saveAmount();" id="oa-ok-oaAccessAmountDetailPage" data-role="button" data-icon="check">确定</a>
        </div>
    </form>
</div>
</body>
</html>



