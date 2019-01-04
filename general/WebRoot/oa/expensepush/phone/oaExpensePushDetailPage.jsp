<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>费用冲差支付申请单(手机)</title>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaExpensePushDetailPage" data-dom-cache="true">
    <script type="text/javascript">
        $(document).on('pageshow', '#oa-pricedetail-oaExpensePushDetailPage', function(e) {

            initCheckRule();

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var pushList = $.parseJSON(localStorage['pushList']);

                if(index != -1) {

                    var pushdetail = pushList[index];

                    pushdetail.amount = formatterMoney(pushdetail.amount);

                    pushdetail.expense = pushdetail.expensesortname;
                    pushdetail.brand = pushdetail.brandid + ':' + pushdetail.brandname;
                    pushdetail.dept = pushdetail.deptid + ':' + pushdetail.deptname;
                    pushdetail.goods = pushdetail.goodsid ? (pushdetail.goodsid + ':' + pushdetail.goodsname) : '';
                    pushdetail.pushtypetext = pushdetail.pushtypename;

                    $('#oa-form-oaExpensePushDetailPage').form('load', pushdetail);

                } else {

                    $('#oa-form-oaExpensePushDetailPage').form('load', {});
                    $('#oa-form-oaExpensePushDetailPage')[0].reset();
                    $('#oa-goods-oaExpensePushDetailPage').val('');
                }
            }

            // 品牌控件
            $('#oa-brand-oaExpensePushDetailPage').off().on('click', function() {
                    androidWidget({
                        type: 'widget',
                        referwid : "RL_T_BASE_GOODS_BRAND",
                        onSelect: 'selectBrand'
                    });

            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
//                $('#oa-brandid-oaExpensePushDetailPage').attr('value', v);
                $('#oa-brandid-oaExpensePushDetailPage').attr('value', (v || ':').split(':')[0]);
            });

            // 商品控件
            $('#oa-goods-oaExpensePushDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'goodsWidget',
                    referwid : "RL_T_BASE_GOODS_INFO",
                    onSelect: 'selectGoods'
                });

            }).on('change', function(e) {

                var v = $(this).val() || '';
                $(this).attr('value', v);
//                $('#oa-goodsid-oaExpensePushDetailPage').attr('value', v);
                $('#oa-goodsid-oaExpensePushDetailPage').attr('value', (v || ':').split(':')[0]);

                if(v != '') {

                    $("#oa-unitnum-oaExpensePushDetailPage").rules('add', {required: true});
                    $("#oa-oldprice-oaExpensePushDetailPage").rules('add', {required: true});
                    $("#oa-newprice-oaExpensePushDetailPage").rules('add', {required: true});
                } else {

                    $("#oa-unitnum-oaExpensePushDetailPage").rules('remove');
                    $("#oa-oldprice-oaExpensePushDetailPage").rules('remove');
                    $("#oa-newprice-oaExpensePushDetailPage").rules('remove');
                }
            });

            // OA验证
            $('#oa-oaid-oaExpensePushDetailPage').off().on('click', function() {}).on('change', function(e) {

                    <c:if test="${not empty param.oacheck and param.oacheck eq 0}">
                        return true;
                    </c:if>
                    var oaid = $('#oa-oaid-oaExpensePushDetailPage').val();

                    if(oaid != ''){

                        $.ajax({
                            type: 'post',
                            dataType: 'json',
                            url: 'act/selectProcess.do',
                            data: {id: oaid},
                            success: function(json) {

                                if(json.process == null) {
                                    // $.messager.alert('警告', '该OA编号对应的通路单不存在！');
                                    alertMsg("已批OA编号对应的通路单不存在，重置为空！");
                                    $('#oa-oaid-oaExpensePushDetailPage').focus();
                                    $('#oa-oaid-oaExpensePushDetailPage').val('');
                                    return false;
                                }

                                $.ajax({
                                    type: 'post',
                                    dataType: 'json',
                                    url: 'oa/selectAccessInfo.do',
                                    data: {id: json.process.businessid},
                                    success: function(json) {

                                        if(json.access == null) {

                                            //$.messager.alert('警告', '该OA编号对应的通路单不存在！');
                                            alertMsg("已批OA编号对应的通路单不存在，重置为空！");
                                            $('#oa-oaid-oaExpensePushDetailPage').focus();
                                            $('#oa-oaid-oaExpensePushDetailPage').val('');
                                            return false;
                                        }
                                    },
                                    error: function() {}

                                });
                            }
                        });

                    }else{
                        return true ;
                    }

            });

            // 费用分类
            $('#oa-expense-oaExpensePushDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RT_T_BASE_FINANCE_EXPENSES_SORT",
                    onSelect: 'selectExpensesort'
                });

            }).on('change', function(e) {

//                var v = $(this).val();
//                $(this).attr('value', v);
//                $('#oa-expensesort-oaExpensePushDetailPage').attr('value', v);
            });

            // 部门控件
            $('#oa-dept-oaExpensePushDetailPage').off().on('click', function() {
                   androidWidget({
                       type: 'widget',
                       referwid : "RL_T_BASE_DEPARTMENT_SELLER",
                       onSelect: 'selectDept'
                   });

            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-deptid-oaExpensePushDetailPage').attr('value', v);
            });

            // 冲差类型
            $('#oa-pushtypetext-oaExpensePushDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RL_T_PUSHTYPE_LIST",
                    onSelect: 'selectPushtype'
                });

            });

            // 开始日期
            $('#oa-startdate-oaExpensePushDetailPage').off().on('click', function() {

                androidDateWidget($('#oa-startdate-oaExpensePushDetailPage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectStartdate');
            });

            // 结束日期
            $('#oa-enddate-oaExpensePushDetailPage').off().on('click', function() {

                androidDateWidget($('#oa-enddate-oaExpensePushDetailPage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectEnddate');
            });

            $("#oa-form-oaExpensePushDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true
                    },
                    unitnum: {
                        required: $('#oa-goods-oaExpensePushDetailPage').val().length > 0
                    },
                    oldprice: {
                        required: $('#oa-goods-oaExpensePushDetailPage').val().length > 0
                    },
                    newprice: {
                        required: $('#oa-goods-oaExpensePushDetailPage').val().length > 0
                    }
                }
            });

        });

        //选择品牌
        function selectBrand(data){
            $('#oa-brandid-oaExpensePushDetailPage').val(data.id);
            $('#oa-brandname-oaExpensePushDetailPage').val(data.name);
            $('#oa-brand-oaExpensePushDetailPage').val(data.id + ':' + data.name);
            <c:if test="${param.noaccess ne 1}">
                <c:choose>
                    <c:when test="${param.dept eq 'salesdept'}">
                        if(($('#oa-salesdeptid-oaExpensePushHandlePage').val() || '') != '') {
                            $('#oa-deptid-oaExpensePushDetailPage').val($('#oa-salesdeptid-oaExpensePushHandlePage').val());
                            $('#oa-deptname-oaExpensePushDetailPage').val($('#oa-salesdeptname-oaExpensePushHandlePage').val());
                            $('#oa-dept-oaExpensePushDetailPage').val($('#oa-salesdeptid-oaExpensePushHandlePage').val() + ':' + $('#oa-salesdeptname-oaExpensePushHandlePage').val());
                        }
                    </c:when>
                    <c:otherwise>
                        $('#oa-deptid-oaExpensePushDetailPage').val(data.deptid);
                        $('#oa-deptname-oaExpensePushDetailPage').val(data.deptname);
                        $('#oa-dept-oaExpensePushDetailPage').val(data.deptid+ ':' + data.deptname);
                    </c:otherwise>
                </c:choose>
            </c:if>
        }

        //选择商品
        function selectGoods(data){

            $('#oa-goodsid-oaExpensePushDetailPage').val(data.id);
            $('#oa-goodsname-oaExpensePushDetailPage').val(data.name);
            $('#oa-goods-oaExpensePushDetailPage').val(data.id + ':' + data.name);

            $('#oa-buyprice-oaExpensePushDetailPage').val(data.newbuyprice);

            $('#oa-goods-oaExpensePushDetailPage').change();
//        $("#oa-form-oaExpensePushDetailPage").rules("add",rules);

            var customerid = $('#oa-customerid-oaExpensePushHandlePage').val();
            var index = localStorage.getItem('index');
            $.ajax({
                type: 'post',
                url: 'oa/common/getGoodsPrice.do',
                data: {customerid: customerid, goodsid: data.id, index: index},
                dataType: 'json',
                async: false,
                success: function(json) {

                    var idx = parseInt(json.index);
                    $('#oa-oldprice-oaExpensePushDetailPage').val(json.goods.taxprice)
                }
            });
        }

        /**
         * 选择费用分类
         */
        function selectExpensesort(data) {

            $('#oa-expense-oaExpensePushDetailPage').val(data.name);
            $('#oa-expensesort-oaExpensePushDetailPage').val(data.id);
            $('#oa-expensesortname-oaExpensePushDetailPage').val(data.name);
            return true;
        }

        /**
         * 开始日期
         * @param data
         */
        function selectStartdate(data) {
            $('#oa-startdate-oaExpensePushDetailPage').val(data);
            $('#oa-startdate-oaExpensePushDetailPage').blur();
        }

        /**
         * 结束日期
         * @param data
         */
        function selectEnddate(data) {
            $('#oa-enddate-oaExpensePushDetailPage').val(data);
            $('#oa-enddate-oaExpensePushDetailPage').blur();
        }

        /**
         * 选择冲差类型
         */
        function selectPushtype(data) {

            $('#oa-pushtypetext-oaExpensePushDetailPage').val(data.name);
            $('#oa-pushtype-oaExpensePushDetailPage').val(data.id);
            $('#oa-pushtypename-oaExpensePushDetailPage').val(data.name);
            return true;
        }

        /**
         * 刷新折让金额
         * @returns {boolean}
         */
        function refreshAmount() {

            if($('#oa-newprice-oaExpensePushDetailPage').val() == '') {
                return false;
            }

            var unitnum = $('#oa-unitnum-oaExpensePushDetailPage').val() || 0;
            var oldprice = $('#oa-oldprice-oaExpensePushDetailPage').val() || 0;
            var newprice = $('#oa-newprice-oaExpensePushDetailPage').val() || 0;
            var oldAmount = $('#oa-amount-oaExpensePushDetailPage').val() || 0;

            var amount = parseFloat(unitnum) * (parseFloat(oldprice) - parseFloat(newprice));
            amount = formatterMoney(amount);
            $('#oa-amount-oaExpensePushDetailPage').val(amount);

            return true;
        }

        function initCheckRule() {
            setTimeout(function () {
                if($('#oa-goods-oaExpensePushDetailPage').val() != '') {

                    $("#oa-unitnum-oaExpensePushDetailPage").rules('add', {required: true});
                    $("#oa-oldprice-oaExpensePushDetailPage").rules('add', {required: true});
                    $("#oa-newprice-oaExpensePushDetailPage").rules('add', {required: true});
                } else {

                    $("#oa-unitnum-oaExpensePushDetailPage").rules('remove');
                    $("#oa-oldprice-oaExpensePushDetailPage").rules('remove');
                    $("#oa-newprice-oaExpensePushDetailPage").rules('remove');
                }

                // 验证
                $('#oa-form-oaExpensePushDetailPage').validate({
                    focusInvalid: true,
                    debug: true
                });

                $("#oa-form-oaExpensePushDetailPage").submit();

            }, 200);
        }

    </script>
    <form action="" id="oa-form-oaExpensePushDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>价格明细</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>折差品牌
                        <input type="text" name="brand" id="oa-brand-oaExpensePushDetailPage" class="required" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="brandid" id="oa-brandid-oaExpensePushDetailPage" />
                        <input type="hidden" name="brandname" id="oa-brandname-oaExpensePushDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>冲差类型
                        <input type="text" name="pushtypetext" id="oa-pushtypetext-oaExpensePushDetailPage" class="required" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="pushtype" id="oa-pushtype-oaExpensePushDetailPage" />
                        <input type="hidden" name="pushtypename" id="oa-pushtypename-oaExpensePushDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>折让金额
                        <input class="required" type="number" precision="2" class="number" data-clear-btn="true" name="amount" id="oa-amount-oaExpensePushDetailPage" money="true"/>
                    </label>
                </div>

                <c:choose>
                    <c:when test="${param.noaccess eq 1}">

                        <div class="ui-field-contain">
                            <label>费用科目
                                <input type="text" name="expense" id="oa-expense-oaExpensePushDetailPage" class="required" readonly="readonly" data-clear-btn="true"/>
                                <input type="hidden" name="expensesort" id="oa-expensesort-oaExpensePushDetailPage"/>
                                <input type="hidden" name="expensesortname" id="oa-expensesortname-oaExpensePushDetailPage"/>
                            </label>
                        </div>

                        <div class="ui-field-contain">
                            <label>开始日期
                                <input type="text" name="startdate" id="oa-startdate-oaExpensePushDetailPage" readonly="readonly" data-clear-btn="true"/>
                            </label>
                        </div>

                        <div class="ui-field-contain">
                            <label>结束日期
                                <input type="text" name="enddate" id="oa-enddate-oaExpensePushDetailPage" readonly="readonly" data-clear-btn="true"/>
                            </label>
                        </div>

                    </c:when>
                    <c:otherwise>

                        <div class="ui-field-contain">
                            <label>费用部门
                                <input type="text" name="dept" id="oa-dept-oaExpensePushDetailPage" class="required"/>
                                <input type="hidden" name="deptid" id="oa-deptid-oaExpensePushDetailPage"/>
                                <input type="hidden" name="deptname" id="oa-deptname-oaExpensePushDetailPage"/>
                            </label>
                        </div>

                        <div class="ui-field-contain">
                            <label>已批OA号
                                <c:choose>
                                    <c:when test="${param.oarequired eq '0'}">
                                        <input type="number" precision="0" class="number" name="oaid" id="oa-oaid-oaExpensePushDetailPage" />
                                    </c:when>
                                    <c:otherwise>
                                        <input type="number" precision="0" class="number required" name="oaid" id="oa-oaid-oaExpensePushDetailPage" />
                                    </c:otherwise>
                                </c:choose>
                            </label>
                        </div>

                    </c:otherwise>
                </c:choose>

                <div class="ui-field-contain">
                    <label>商品
                        <input type="text" name="goods" id="oa-goods-oaExpensePushDetailPage" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="goodsid" id="oa-goodsid-oaExpensePushDetailPage" />
                        <input type="hidden" name="goodsname" id="oa-goodsname-oaExpensePushDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>数量
                        <input type="number" precision="3" class="number" data-clear-btn="true" name="unitnum" id="oa-unitnum-oaExpensePushDetailPage" money="true" onchange="javascript:refreshAmount();"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>原价
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="oldprice" id="oa-oldprice-oaExpensePushDetailPage" money="true" onchange="javascript:refreshAmount();"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>现价
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="newprice" id="oa-newprice-oaExpensePushDetailPage" money="true" onchange="javascript:refreshAmount();"/>
                    </label>
                </div>

                <c:if test="${param.buyprice eq '1'}">
                    <div class="ui-field-contain">
                        <label>采购价
                            <input type="number" precision="2" class="number" data-clear-btn="false" name="buyprice" id="oa-buyprice-oaExpensePushDetailPage" money="true" readonly="readonly"/>
                        </label>
                    </div>
                </c:if>

                <div class="ui-field-contain">
                    <label>备注
                        <input type="text" name="remark"  id="oa-remark-oaExpensePushDetailPage"/>
                    </label>
                </div>
            </div>
         </div>
        <div id="oa-footer-oaExpensePushDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removePush();" id="oa-remove-oaExpensePushDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return savePush();" id="oa-ok-oaExpensePushDetailPage" data-role="button" data-icon="check">确定</a>
        </div>
    </form>
</div>
</body>
</html>
