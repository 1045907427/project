<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>特价通路申请单(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">

        var prices = $.parseJSON('${pricesJSON }');
        var amounts = $.parseJSON('${amountsJSON }');
        localStorage.setItem("prices", '${pricesJSON }');
        localStorage.setItem("amounts", '${amountsJSON }');

        //
        $(document).on('pagebeforehide', '#oa-pricedetail-oaAccessPriceDetailPage', function(e) {

            var index = $(this).attr('index');

            var data;
            try {

                data = $('#oa-form-oaAccessPriceDetailPage').serializeJSON();
            } catch(e) {
                console.log(e);
            }

            var prices = $.parseJSON(localStorage['prices']);

            prices[parseInt(index)] = data;

            localStorage.setItem("prices", JSON.stringify(prices));

            refreshPrices();
        });

        // 切换到价格明细页面时，加载表单数据 pagecreate -> pageshow
        $(document).on('pageshow', '#oa-pricedetail-oaAccessPriceDetailPage', function(e) {

//            // var index = $(this).attr('index');
//            var index = localStorage.getItem('index');
//            var prices = $.parseJSON(localStorage['prices']);
//
//            if(index != -1) {
//
//                var price = prices[index];
//
//                price.buyprice = formatterMoney(price.buyprice);
//                price.factoryprice = formatterMoney(price.factoryprice);
//                price.myprice = formatterMoney(price.myprice);
//                price.oldprice = formatterMoney(price.oldprice);
//                price.newprice = formatterMoney(price.newprice);
//                price.rate = formatterMoney(price.rate);
//
//                price.goodsid2 = price.goodsid + ':' + price.goodsname;
//
//                $('#oa-form-oaAccessPriceDetailPage').form('load', price);
//
//            } else {
//
//                $('#oa-form-oaAccessPriceDetailPage').form('load', {});
//                $('#oa-form-oaAccessPriceDetailPage')[0].reset()
//            }
//
//            // 商品控件
//            $('#oa-goodsid2-oaAccessPriceDetailPage').off().on('click', function() {
//
//                androidWidget({
//                    type: 'goodsWidget',
//                    onSelect: 'selectGoods',
////                    param: [{field: 'defaultsupplier' ,op: 'equal', value: $('#oa-supplierid-oaAccessHandlePage').val()}]
//                    param: [{field: 'defaultsupplier', op: 'equal', value: '1201'}]
//                });
//            }).on('change', function(e) {
//
//                var v = $(this).val();
//                $(this).attr('value', v);
//                $('#oa-goodsid-oaAccessPriceDetailPage').attr('value', v);
//            });
//
//            $("#oa-form-oaAccessPriceDetailPage").validate({
//                debug: true,
//                rules: {
//                    money: {
//                        money: true
//                    }
//                }
//            });

        });

        // 切换到数量明细页面时，加载表单数据
        $(document).on('pageshow', '#oa-amountdetail-oaAccessAmountDetailPage', function(e) {

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

                $('#oa-unitnum-oaAccessAmountDetailPage').off().on('change', converseAmount);
            }
        });

        // 显示主页面时，reload明细数据
        $(document).on('pageshow', '#main', function(e) {

            refreshPrices(e);
            refreshAmounts(e);

            numberPrecision();
        });
        //$(document).on('pageshow', '#main', refreshPrices);

        /*
        * 刷新价格明细
        */
        function refreshPrices(e) {

            var prices = $.parseJSON(localStorage.getItem("prices"));

            var html = new Array();

            for(var i in prices) {

                var price = prices[i];

                html.push('<li>');
                html.push('<a href="oa/access/phone/oaAccessPriceDetailPage.jsp?buyprice=${param.buyprice }" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>商品:</th>');
                html.push('<td>' + price.goodsid + ':' + price.goodsname + '</span></td>');
                html.push('</tr>');

                <c:if test="${param.buyprice eq '0'}">
                    html.push('<tr>');
                    html.push('<th>进货价:</th>');
                    html.push('<td><span precision="2">' + price.buyprice + '</span></td>');
                    html.push('</tr>');
                </c:if>

                html.push('<tr>');
                html.push('<th>工厂让利:</th>');
                html.push('<td><span precision="2">' + price.factoryprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>自理:</th>');
                html.push('<td><span precision="2">' + price.myprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>原价:</th>');
                html.push('<td><span precision="2">' + price.oldprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>现价:</th>');
                html.push('<td><span precision="2">' + price.newprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>毛利率:</th>');
                html.push('<td><span precision="2">' + price.rate + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>门店出货:</th>');
                html.push('<td><span>' + price.senddetail + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-pricedetail-oaAccessHandlePage').find(':not(:eq(0))').remove();
            $('#oa-pricedetail-oaAccessHandlePage').append(html.join(''));

            try {

                $('#oa-pricedetail-oaAccessHandlePage').listview('refresh');
            } catch(e) {}

        }

        /**
         * 刷新数量明细
         */
        function refreshAmounts(e) {

            var amounts = $.parseJSON(localStorage.getItem("amounts"));

            var html = new Array();

            for(var i in amounts) {

                var amount = amounts[i];

                html.push('<li>');
                //html.push('<a href="#oa-amountdetail-oaAccessAmountDetailPage" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>商品:</th>');
                html.push('<td>' + amount.goodsid + ':' + amount.goodsname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>单位差价:</th>');
                html.push('<td><span precision="2">' + amount.difprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>数量:</th>');
                html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + amount.unitnum + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>差价金额:</th>');
                html.push('<td><span precision="2">' + amount.amount + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>ERP数量:</th>');
                html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + amount.erpnum + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>降价金额:</th>');
                html.push('<td><span precision="2">' + amount.downamount + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
                //html.push('</a>');
                html.push('</li>');
            }

            $('#oa-amountdetail-oaAccessHandlePage').find(':not(:eq(0))').remove();
            $('#oa-amountdetail-oaAccessHandlePage').append(html.join(''));

            try {

                $('#oa-amountdetail-oaAccessHandlePage').listview('refresh');
            } catch(e) {}
            numberPrecision();
        }

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

        $(function() {

            $('#oa-supplierid2-oaAccessHandlePage').on('click', function() {

                androidWidget({
                    type: 'supplierWidget',
                    onSelect: 'selectSupplier',
                    isdatasql: '1'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-supplierid-oaAccessHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // oa-expensesort2-oaAccessHandlePage
            $('#oa-expensesort2-oaAccessHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    name: 't_oa_access',
                    col: 'expensesort',
                    onSelect: 'selectExpensesort',
                    checkType: 1
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-expensesort-oaAccessHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // oa-customerid2-oaAccessHandlePage
            $('#oa-customerid2-oaAccessHandlePage').on('click', function() {

                androidWidget({
                    type: 'customerWidget',
                    ishead: '3',
                    onSelect: 'selectCustomer'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-customerid-oaAccessHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 开始日期
            $(document).on('click', '#oa-planbegindate-oaAccessHandlePage', function() {

                androidDateWidget($('#oa-planbegindate-oaAccessHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPlanbegindate');
            });

            // 结束日期
            $(document).on('click', '#oa-planenddate-oaAccessHandlePage', function() {

                androidDateWidget($('#oa-planenddate-oaAccessHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectPlanenddate');
            });

            // 收回日期
            $(document).on('click', '#oa-reimbursedate-oaAccessHandlePage', function(e) {

                androidDateWidget($('#oa-reimbursedate-oaAccessHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM', 'selectReimbursedate');
            });
            //
            $(document).on('change', '#oa-pricetype-oaAccessHandlePage', copyDate);

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

            $('#oa-conbegindate-oaAccessHandlePage').on('change', refreshErpNum);
            $('#oa-conenddate-oaAccessHandlePage').on('change', refreshErpNum);
//            oa-conbegindate-oaAccessHandlePage
//            oa-conenddate-oaAccessHandlePage
        });

        /**
         * 选择供应商后执行操作
         */
        function selectSupplier(data) {

            $('#oa-supplierid-oaAccessHandlePage').val(data.id);
            $('#oa-supplierid2-oaAccessHandlePage').val(data.id + ':' + data.name);

            // confirmid
            $('#oa-supplierid2-oaAccessHandlePage').blur();
            $('#oa-supplierid2-oaAccessHandlePage').change();
        }

        /**
         * 选择科目后执行操作
         */
        function selectExpensesort(data) {

            $('#oa-expensesort-oaAccessHandlePage').val(data.id);
            $('#oa-expensesort2-oaAccessHandlePage').val(data.id + ':' + data.name);

            $('#oa-expensesort2-oaAccessHandlePage').blur();
            $('#oa-expensesort2-oaAccessHandlePage').change();
        }

        /**
         * 选择客户后执行操作
         */
        function selectCustomer(data) {

            $('#oa-customerid-oaAccessHandlePage').val(data.id);
            $('#oa-customerid2-oaAccessHandlePage').val(data.id + ':' + data.name);

            // 执行地点
            $('#oa-executeaddr-oaAccessHandlePage').val(data.address);

            $('#oa-customerid2-oaAccessHandlePage').blur();

            refreshErpNum();
        }

        /**
         * 选择商品后执行操作
         */
        function selectGoods(data) {

            $('#oa-goodsid-oaAccessPriceDetailPage').val(data.id);
            $('#oa-goodsid2-oaAccessPriceDetailPage').val(data.id + ':' + data.name);

            $('#oa-buyprice-oaAccessPriceDetailPage').val(data.newbuyprice);
            $('#oa-buyprice-oaAccessPriceDetailPage').change();

            $.ajax({
                type: 'post',
                data: {customerid: $('#oa-customerid-oaAccessHandlePage').val(), goodsid: data.id},
                dataType: 'json',
                url: 'oa/hd/getGoodsPrice.do',
                success: function (json) {

                    document.getElementById('oa-oldprice-oaAccessPriceDetailPage').value = json.goods.taxprice;
                    $('#oa-oldprice-oaAccessPriceDetailPage').change();

                    $('#oa-goodsid2-oaAccessHandlePage').blur();
                }
            });
        }

        /**
         * 选择开始日期
         */
        function selectPlanbegindate(data) {

            $('#oa-planbegindate-oaAccessHandlePage').val(data);
            $('#oa-planbegindate-oaAccessHandlePage').blur();

            copyDate();
        }

        /**
         * 选择开始日期
         */
        function selectPlanenddate(data) {

            $('#oa-planenddate-oaAccessHandlePage').val(data);
            $('#oa-planenddate-oaAccessHandlePage').blur();

            copyDate();
        }

        /**
         * 选择确认日期（开始）
         */
        function selectConbegindate(data) {

            $('#oa-conbegindate-oaAccessHandlePage').val(data);
            $('#oa-conbegindate-oaAccessHandlePage').blur();
        }

        /**
         * 选择确认日期（结束）
         */
        function selectConenddate(data) {

            $('#oa-conenddate-oaAccessHandlePage').val(data);
            $('#oa-conenddate-oaAccessHandlePage').blur();
        }

        /**
         * 选择降价设定时间（开始）
         */
        function selectCombegindate(data) {

            $('#oa-combegindate-oaAccessHandlePage').val(data);
            $('#oa-combegindate-oaAccessHandlePage').blur();
        }

        /**
         * 选择降价设定时间（结束）
         */
        function selectComenddate(data) {

            $('#oa-comenddate-oaAccessHandlePage').val(data);
            $('#oa-comenddate-oaAccessHandlePage').blur();
        }

        /**
         * 选择收回日期
         */
        function selectReimbursedate(data) {

            $('#oa-reimbursedate-oaAccessHandlePage').val(data);
            $('#oa-reimbursedate-oaAccessHandlePage').blur();
        }

        /**
         * 计算自理金额
         */
        function computeMyprice() {

//            setTimeout(function() {

                var oldprice = $('#oa-oldprice-oaAccessPriceDetailPage').val() || '0.00';
                var newprice = $('#oa-newprice-oaAccessPriceDetailPage').val() || '0.00';
                var factoryprice = $('#oa-factoryprice-oaAccessPriceDetailPage').val() || '0.00';

                var myprice = parseFloat(oldprice) - parseFloat(newprice) - parseFloat(factoryprice);
                myprice = formatterMoney(myprice);

                $('#oa-myprice-oaAccessPriceDetailPage').val(myprice);
                $('#oa-myprice-oaAccessPriceDetailPage').change();
//            }, 100);

            return true;
        }

        /**
         * 计算毛利率
         */
        function computeProfit() {

            var newprice = $('#oa-newprice-oaAccessPriceDetailPage').val() || '0.00';
            var buyprice = $('#oa-buyprice-oaAccessPriceDetailPage').val() || '0.00';

            var profit = formatterMoney((parseFloat(newprice) - parseFloat(buyprice)) * 100 / parseFloat(newprice));

            $('#oa-rate-oaAccessPriceDetailPage').val(profit);
            $('#oa-rate-oaAccessPriceDetailPage').change();
            return true;
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
         * 保存价格明细
         */
        function savePrice() {

            // 验证
            var flag = $('#oa-form-oaAccessPriceDetailPage').validate().form();
            if(!flag) {

                return false;
            }

//            // 计算自理金额
//            $('#oa-oldprice-oaAccessPriceDetailPage').trigger('change');
//            $('#oa-newprice-oaAccessPriceDetailPage').trigger('change');
//            $('#oa-factoryprice-oaAccessPriceDetailPage').trigger('change');
//
//            // 计算毛利率
//            $('#oa-newprice-oaAccessPriceDetailPage').trigger('change');
//            $('#oa-buyprice-oaAccessPriceDetailPage').trigger('change');
//
//            // 计算结算金额
//            $('#oa-factoryamount-oaAccessHandlePage').trigger('change');
//            $('#oa-payamount-oaAccessHandlePage').trigger('change');

//            converseAmount();
//            computeAmount();
//            computeDownamount();
            computeMyprice();
            computeProfit();
            computeBranchaccount();

            var data = $('#oa-form-oaAccessPriceDetailPage').serializeJSON();
            $('#oa-form-oaAccessPriceDetailPage').form('clear');
            $('#oa-form-oaAccessPriceDetailPage')[0].reset();
            var index = parseInt(localStorage.getItem('index'));

            data.goodsname = data.goodsid2.substring(data.goodsid.length + 1, data.goodsid2.length);
            data.buydata  =  formatterMoney(data.buydata);
            data.factorydata  =  formatterMoney(data.factorydata);
            data.mydata  =  formatterMoney(data.mydata);
            data.oldprice  =  formatterMoney(data.oldprice);
            data.newprice  =  formatterMoney(data.newprice);
            data.rate  =  formatterMoney(data.rate);

            var amount = {};
            amount.goodsid = data.goodsid;
            amount.goodsname = data.goodsname;
            amount.difprice = formatterMoney(parseFloat(data.oldprice || '0.00') - parseFloat(data.newprice || '0.00'));
            amount.unitnum = '0';
            amount.amount = '0.00';
            amount.erpnum = '0.00';
            amount.downamount = '0.00';

            if(index == -1) {

                prices.push(data);
                amounts.push(amount);
            } else {

                prices[index] = data;
                amounts[index] = amount;
            }
            localStorage.setItem('prices', JSON.stringify(prices));
            localStorage.setItem('amounts', JSON.stringify(amounts));
            localStorage.setItem('index', -1);

            refreshErpNum();

            return true;
        }

        /**
         * 保存数量明细
         */
        function saveAmount() {

            $('#oa-form-oaAccessAmountDetailPage').validate({
                rules: {
                    'oa-difprice-oaAccessPriceDetailPage': {
                        money: null
                    },
                    'oa-amount-oaAccessPriceDetailPage': {
                        money: null
                    },
                    'oa-downamount-oaAccessPriceDetailPage': {
                        money: null
                    }
                }
            });

            // 验证
            var flag = $('#oa-form-oaAccessAmountDetailPage').valid();
            if(!flag) {

                return false;
            }

            var data = $('#oa-form-oaAccessAmountDetailPage').serializeJSON();
//            $('#oa-form-oaAccessPriceDetailPage').form('clear');
//            $('#oa-form-oaAccessPriceDetailPage')[0].reset();
            var index = parseInt(localStorage.getItem('index'));

            if(index == -1) {

                amounts.push(data);
            } else {

                amounts[index] = data;
            }

            localStorage.setItem('amounts', JSON.stringify(amounts));
            localStorage.setItem('index', -1);

            return true;
        }

        /**
         * 删除价格明细
         */
        function removePrice() {

            var index = localStorage.getItem('index');

            if(index == -1) {

                return true;
            }

            prices.splice(index, 1);
            amounts.splice(index, 1);
            localStorage.setItem("prices", JSON.stringify(prices));
            localStorage.setItem("amounts", JSON.stringify(amounts));
            return true;
        }

        /**
         *
         */
        function copyDate() {

            $('#oa-conbegindate-oaAccessHandlePage').val('');
            $('#oa-conenddate-oaAccessHandlePage').val('');
            $('#oa-combegindate-oaAccessHandlePage').val('');
            $('#oa-comenddate-oaAccessHandlePage').val('');

            $('#oa-conbegindate-oaAccessHandlePage').change();
            $('#oa-conenddate-oaAccessHandlePage').change();

            var begindate = $('#oa-planbegindate-oaAccessHandlePage').val();
            var enddate = $('#oa-planenddate-oaAccessHandlePage').val();

            var v = $('#oa-pricetype-oaAccessHandlePage').val();
            // 2: 降价特价
            if(v == '2') {

                $('#oa-combegindate-oaAccessHandlePage').val(begindate);
                $('#oa-comenddate-oaAccessHandlePage').val(enddate);

            } else {

                $('#oa-conbegindate-oaAccessHandlePage').val(begindate);
                $('#oa-conenddate-oaAccessHandlePage').val(enddate);

                $('#oa-conbegindate-oaAccessHandlePage').change();
                $('#oa-conenddate-oaAccessHandlePage').change();
            }

        }

        /**
         * 计算结算金额
         */
        function computeBranchaccount() {

            var factoryamount = $('#oa-factoryamount-oaAccessHandlePage').val() || '0.00';
            var payamount = $('#oa-payamount-oaAccessHandlePage').val() || '0.00';

            var branchaccount = (parseFloat(formatterMoney(factoryamount)) * 100 - parseFloat(formatterMoney(payamount)) * 100) / 100;
            $('#oa-branchaccount-oaAccessHandlePage').val(branchaccount);
        }

        /**
         * 总数量 → 辅数量
         */
        function converseAmount() {

            var unitnum = $('#oa-unitnum-oaAccessAmountDetailPage').val() || '0';
            var goodsid = $('#oa-goodsid-oaAccessAmountDetailPage').val() || '';

            if(goodsid == '') {

                $('#oa-auxnum-oaAccessAmountDetailPage').val('');
                $('#oa-auxremainder-oaAccessAmountDetailPage').val('');
                return true;
            }

            $.ajax({
                type: 'post',
                url: 'oa/hd/getGoodsInfo.do',
                data: {goodsid: goodsid},
                dataType: 'json',
                success: function(json) {

                    var goods = json.goods;
                    var auxunitid = goods.auxunitid;

                    $.ajax({
                        type: 'post',
                        data: {goodsid: goodsid, auxunitid: auxunitid, unitnum: unitnum},
                        url: 'oa/hd/computeGoodsByUnitnum.do',
                        dataType: 'json',
                        success: function(json2) {

                            var auxnum = json2.auxInteger;
                            var auxremainder = json2.auxremainder;

                            $('#oa-auxnum-oaAccessAmountDetailPage').val(auxnum);
                            $('#oa-auxremainder-oaAccessAmountDetailPage').val(auxremainder);
                        }
                    });
                }
            });
        }

        /**
         * 刷新商品明细ERP数量以及降价金额
         */
        function refreshErpNum() {

            var customerid = $('#oa-customerid-oaAccessHandlePage').val() || '';
            if(customerid == '') {

                return true;
            }

            loading();

            for(var i in amounts) {

                var amount = amounts[i];

                $.ajax({
                    type: 'post',
                    url: 'oa/hd/getErpNum.do',
                    data: {
                        goodsid: amount.goodsid,
                        customerid: customerid,
                        startdate: $('#oa-conbegindate-oaAccessHandlePage').val(),
                        enddate: $('#oa-conenddate-oaAccessHandlePage').val(),
                        index: i
                    },
                    async: false,
                    success: function(json) {

                        var index = json.index || '';
                        if(index == '') {

                            return true;
                        }

                        amount.erpnum = json.sales;

                        var difprice = amount.difprice || '0.00';
                        var downamount = parseFloat(difprice) * parseFloat(amount.erpnum);
                        amount.downamount = downamount;
                        amounts[i] = amount;

                        if(i == parseInt(index)) {

                            refreshAmounts();
                        }
                    }
                })
            }

            loaded();
            return true;
        }

        /**
        * 提交表单
        * @param call
        * @param args
        * @returns {boolean}
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaAccessHandlePage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#oa-form-oaAccessHandlePage").submit();
            var flag = $('#oa-form-oaAccessHandlePage').validate().form();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaAccessHandlePage').validate({
                debug: false
            });

            $('#oa-goodsprice-oaAccessHandlePage').val(JSON.stringify(prices));
            $('#oa-goodsamount-oaAccessHandlePage').val(JSON.stringify(amounts));

            $('#oa-form-oaAccessHandlePage').submit(function(){

                $(this).ajaxSubmit({
                    type: 'post',
                    url: 'oa/hd/editOaAccess.do',
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
    </script>
</head>
<body>
    <div data-role="page" id="main">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>处理工作[${param.processid }]</h1>
            <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <form action="" id="oa-form-oaAccessHandlePage" method="post">
            <input type="hidden" name="goodsprice" id="oa-goodsprice-oaAccessHandlePage" value=""/>
            <input type="hidden" name="goodsamount" id="oa-goodsamount-oaAccessHandlePage" value=""/>
            <input type="hidden" name="access.id" id="oa-id-oaAccessHandlePage" value="${param.id }"/>
            <input type="hidden" name="access.oaid" id="oa-oaid-oaAccessHandlePage" value="${param.processid }"/>
            <div class="ui-corner-all custom-corners">
                <div class="ui-bar ui-bar-b">
                    <h1>第一确认</h1>
                </div>
                <div class="ui-body ui-body-a">
                    <div class="ui-field-contain">
                        <label>供应商<font color="#F00">*</font>
                            <c:choose>
                                <c:when test="${not empty access and not empty access.supplierid and not empty supplier}">
                                    <input type="text" class="required" name="access.supplierid2" id="oa-supplierid2-oaAccessHandlePage" value="${supplier.id }:${supplier.name }" readonly="readonly" data-clear-btn="true"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="required" name="access.supplierid2" id="oa-supplierid2-oaAccessHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" class="required" name="access.supplierid" id="oa-supplierid-oaAccessHandlePage" value="${supplier.id }" readonly="readonly" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>时间段<font color="#F00">*</font>
                            <input type="text" class="required dateISO" name="access.planbegindate" id="oa-planbegindate-oaAccessHandlePage" value="${access.planbegindate }" readonly="readonly" data-clear-btn="true"/>
                            <input type="text" class="required dateISO" name="access.planenddate" id="oa-planenddate-oaAccessHandlePage" value="${access.planenddate }" readonly="readonly" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>确认单号
                            <input type="text" class="number" name="access.confirmid" id="oa-confirmid-oaAccessHandlePage" value="${access.confirmid }" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>申请通路费<font color="#F00">*</font>
                            <c:choose>
                                <c:when test="${not empty access and not empty access.expensesort and not empty expensesort }">
                                    <input type="text" class="required" name="access.expensesort2" id="oa-expensesort2-oaAccessHandlePage" value="${expensesort.id }:${expensesort.name }" readonly="readonly" data-clear-btn="true"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="required" name="access.expensesort2" id="oa-expensesort2-oaAccessHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" name="access.expensesort" id="oa-expensesort-oaAccessHandlePage" value="${expensesort.id }" readonly="readonly" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label class="select">申请特价<font color="#F00">*</font>
                            <select name="access.pricetype" id="oa-pricetype-oaAccessHandlePage" sdata="${access.pricetype }">
                                <option value=""></option>
                                <option value="1">补差特价</option>
                                <option value="2">降价特价</option>
                            </select>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label class="select">是否补库存
                            <select name="access.isaddstorage" id="oa-isaddstorage-oaAccessHandlePage" sdata="${access.isaddstorage }">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>客户<font color="#F00">*</font>
                            <c:choose>
                                <c:when test="${not empty access and not empty access.customerid and not empty customer }">
                                    <input type="text" class="required" name="access.customerid2" id="oa-customerid2-oaAccessHandlePage" value="${customer.id }:${customer.name }" readonly="readonly" data-clear-btn="true"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="required" name="access.customerid2" id="oa-customerid2-oaAccessHandlePage" value="" readonly="readonly" data-clear-btn="true"/>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" class="required" name="access.customerid" id="oa-customerid-oaAccessHandlePage" value="${customer.id }" readonly="readonly" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>执行地点
                            <input type="text" name="access.executeaddr" id="oa-executeaddr-oaAccessHandlePage" value="${access.executeaddr }" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <!-- <a href="#oa-pricedetail-oaAccessPriceDetailPage" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a> -->
                        <a href="<%=request.getScheme()+"://"+request.getServerName()+(request.getServerPort() == 80 ? "" : ":" + request.getServerPort())+request.getContextPath()+"/"%>oa/access/phone/oaAccessPriceDetailPage.jsp?buyprice=${param.buyprice }" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                        <ul data-role="listview" id="oa-pricedetail-oaAccessHandlePage" data-inset="false">
                            <li data-role="list-divider">价格明细</li>
                        </ul>
                    </div>
                    <%--
                    <div class="ui-field-contain">
                        <label>工厂金额
                            <input type="number" class="money" name="access.factoryamount" id="oa-factoryamount-oaAccessHandlePage" value="${access.factoryamount }" data-clear-btn="true"/>
                        </label>
                    </div>
                    --%>
                    <div class="ui-field-contain">
                        <label>自理金额
                            <input type="number" class="money" name="access.myamount" id="oa-myamount-oaAccessHandlePage" value="${access.myamount }" data-clear-btn="true"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>收回方式
                            <select name="access.reimbursetype" id="oa-reimbursetype-oaAccessHandlePage" sdata="${access.reimbursetype }">
                                <option></option>
                                <c:forEach items="${typelist }" var="type">
                                    <option value="${type.code }"><c:out value="${type.codename }"></c:out></option>
                                </c:forEach>
                            </select>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>收回日期
                            <input type="text" name="access.reimbursedate" id="oa-reimbursedate-oaAccessHandlePage" value="${access.reimbursedate }" data-clear-btn="false"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>说明
                            <textarea name="access.remark1" id="oa-remark1-oaAccessHandlePage" maxlength="166"><c:out value="${access.remark1}"/></textarea>
                        </label>
                    </div>
                </div>
            </div>
            <div class="ui-corner-all custom-corners">
                <div class="ui-bar ui-bar-b">
                    <h1>第二确认</h1>
                </div>
                <div class="ui-body ui-body-a">
                    <div class="ui-field-contain">
                        <label>确认时间
                            <input type="text" class="" name="access.conbegindate" id="oa-conbegindate-oaAccessHandlePage" value="${access.conbegindate }" data-clear-btn="false" readonly="readonly"/>
                            <input type="text" class="" name="access.conenddate" id="oa-conenddate-oaAccessHandlePage" value="${access.conenddate }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>降价设定时间
                            <input type="text" class="" name="access.combegindate" id="oa-combegindate-oaAccessHandlePage" value="${access.combegindate }" data-clear-btn="false" readonly="readonly"/>
                            <input type="text" class="" name="access.comenddate" id="oa-comenddate-oaAccessHandlePage" value="${access.comenddate }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <label class="select">支付方式
                        <select sdata="${access.paytype }" disabled="disabled">
                            <option></option>
                            <option value="1">折扣</option>
                            <option value="2">支票</option>
                        </select>
                        <input type="hidden" name="access.paytype" id="oa-paytype-oaAccessHandlePage" value="${access.paytype }"/>
                    </label>
                    <div class="ui-field-contain">
                        <label>费用金额
                            <input type="number" name="access.totalamount" id="oa-totalamount-oaAccessHandlePage" value="${access.totalamount }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <%--
                    <div class="ui-field-contain">
                        <label>数量
                            <input type="number" name="access.totalnum" id="oa-totalnum-oaAccessHandlePage" value="${access.totalnum }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    --%>
                    <div class="ui-field-contain">
                        <label>支付日期
                            <input type="text" name="access.paydate" id="oa-paydate-oaAccessHandlePage" value="${access.paydate }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>

                    <div class="ui-field-contain">
                        <ul data-role="listview" id="oa-amountdetail-oaAccessHandlePage" data-inset="false">
                            <li data-role="list-divider">数量明细</li>
                        </ul>
                    </div>
                    <div class="ui-field-contain">
                        <label>说明
                            <textarea name="access.remark2" id="oa-remark2-oaAccessHandlePage" readonly="readonly" maxlength="166"><c:out value="${access.remark2}"/></textarea>
                        </label>
                    </div>
                </div>
            </div>
            <div class="ui-corner-all custom-corners">
                <div class="ui-bar ui-bar-b">
                    <h1>第三确认</h1>
                </div>
                <div class="ui-body ui-body-a">
                    <div class="ui-field-contain">
                        <label>电脑冲差金额
                            <input type="number" name="access.compdiscount" id="oa-compdiscount-oaAccessHandlePage" value="${access.compdiscount }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>电脑降价金额
                            <input type="number" name="access.comdownamount" id="oa-comdownamount-oaAccessHandlePage" value="${access.comdownamount }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>支票金额
                            <input type="number" name="access.payamount" id="oa-payamount-oaAccessHandlePage" value="${access.payamount }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>结算金额
                            <input type="number" name="access.branchaccount" id="oa-branchaccount-oaAccessHandlePage" value="${access.branchaccount }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                </div>
            </div>
        </form>
        <div id="oa-footer-oaAccessHandlePage" data-role="footer" data-position="fixed">
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
