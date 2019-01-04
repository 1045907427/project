<%--
  Created by IntelliJ IDEA.
  User: limin
  Date: 2018/2/22
  Time: 10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>客户费用申请单（账扣）(手机)</title>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaMatcostDetailPage" data-dom-cache="true">
    <script type="text/javascript">

        jQuery.validator.addMethod(
            "byteMaxLength",
            function(value, element, param) {
                var length = value.length;
                for(var i = 0; i < value.length; i++){
                    if(value.charCodeAt(i) > 127){
                        length++;
                    }
                }
                return this.optional(element) || (length <= param[0]);
            },
            $.validator.format("请确保输入的内容小于{0}个字节(一个中文字算2个字节)")
        );

        $(document).on('pageshow', '#oa-pricedetail-oaMatcostDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var feeList = $.parseJSON(localStorage['details']);

                if(index != -1) {

                    var detail = feeList[index];

                    detail.factoryamount = formatterMoney(detail.factoryamount);
                    detail.selfamount = formatterMoney(detail.selfamount);
                    detail.feeamount = formatterMoney(detail.feeamount);

                    detail.brand =  detail.brandname;
                    detail.dept =  detail.deptname;
                    detail.customer =  detail.customername;
                    detail.expensesort2 =  detail.expensesortname;

                    $('#oa-form-oaMatcostDetailPage').form('load', detail);

                } else {

                    $('#oa-form-oaMatcostDetailPage').form('load', {});
                    $('#oa-form-oaMatcostDetailPage')[0].reset();
                }
            }

            // 客户
            $('#oa-customer-oaMatcostDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RL_T_BASE_SALES_CUSTOMER",
                    onSelect: 'selectCustomer'
                });

            }).on('change', function(e) {

                var v = $(this).val();

                if(!v) {
                    $('#oa-customerid-oaMatcostHandlePage').attr('value', '');
                    $('#oa-customerid-oaMatcostHandlePage').val('');
                    $('#oa-customername-oaMatcostHandlePage').attr('value', '');
                    $('#oa-customername-oaMatcostHandlePage').val('');
                }
            });

            // 品牌控件
            $('#oa-brand-oaMatcostDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RL_T_BASE_GOODS_BRAND",
                    onSelect: 'selectBrand'
                });

            }).on('change', function(e) {

                var v = $(this).val();
                if(!v) {
                    $('#oa-brandid-oaMatcostHandlePage').attr('value', '');
                    $('#oa-brandid-oaMatcostHandlePage').val('');
                    $('#oa-brandname-oaMatcostHandlePage').attr('value', '');
                    $('#oa-brandname-oaMatcostHandlePage').val('');
                }
            });

            // 费用科目
            $('#oa-expensesort2-oaMatcostDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RT_T_BASE_FINANCE_EXPENSES_SORT",
                    onSelect: 'selectExpensesort'
                });

            }).on('change', function(e) {

                var v = $(this).val();
                if(!v) {
                    $('#oa-expensesort-oaMatcostHandlePage').attr('value', '');
                    $('#oa-expensesort-oaMatcostHandlePage').val('');
                    $('#oa-expensesortname-oaMatcostHandlePage').attr('value', '');
                    $('#oa-expensesortname-oaMatcostHandlePage').val('');
                }
            });

            $("#oa-form-oaMatcostDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true
                    },
                    customer: {
                        required: true
                    },
                    expensesort2: {
                        required: true
                    },
                    brand: {
                        required: true
                    },
                    factoryamount: {
                        required: false
                    },
                    selfamount: {
                        required: false
                    },
                    feeamount: {
                        required: false
                    },
                    remark: {
                        byteMaxLength: [50]
                    }
                }
            });

        });

        /**
         * 选择客户
         */
        function selectCustomer(data){
            $('#oa-customerid-oaMatcostDetailPage').val(data.id);
            $('#oa-customername-oaMatcostDetailPage').val(data.name);
            $('#oa-customer-oaMatcostDetailPage').val(data.name);

            $('#oa-customer-oaMatcostHandlePage').blur();
            $('#oa-customer-oaMatcostHandlePage').change();
        }

        /**
         * 选择费用科目
         * @param data
         */
        function selectExpensesort(data){
            $('#oa-expensesort-oaMatcostDetailPage').val(data.id);
            $('#oa-expensesortname-oaMatcostDetailPage').val(data.name);
            $('#oa-expensesort2-oaMatcostDetailPage').val(data.name);

            $('#oa-expensesort2-oaMatcostHandlePage').blur();
            $('#oa-expensesort2-oaMatcostHandlePage').change();
        }

        /**
         * 选择品牌
         * @param data
         */
        function selectBrand(data){
            $('#oa-brandid-oaMatcostDetailPage').val(data.id);
            $('#oa-brandname-oaMatcostDetailPage').val(data.name);
            $('#oa-brand-oaMatcostDetailPage').val(data.name);

            $('#oa-brand-oaMatcostHandlePage').blur();
            $('#oa-brand-oaMatcostHandlePage').change();
        }

    </script>
    <form id="oa-form-oaMatcostDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>费用明细</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">

                <div class="ui-field-contain">
                    <label>客户<span class="warnning">*</span>
                        <input type="text" name="customer" id="oa-customer-oaMatcostDetailPage" readonly="readonly"/>
                        <input type="hidden" name="customerid" id="oa-customerid-oaMatcostDetailPage" />
                        <input type="hidden" name="customername" id="oa-customername-oaMatcostDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>品牌<span class="warnning">*</span>
                        <input type="text" name="brand" id="oa-brand-oaMatcostDetailPage" readonly="readonly"/>
                        <input type="hidden" name="brandid" id="oa-brandid-oaMatcostDetailPage" />
                        <input type="hidden" name="brandname" id="oa-brandname-oaMatcostDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>费用科目<span class="warnning">*</span>
                        <input type="text" name="expensesort2" id="oa-expensesort2-oaMatcostDetailPage" readonly="readonly"/>
                        <input type="hidden" name="expensesort" id="oa-expensesort-oaMatcostDetailPage"/>
                        <input type="hidden" name="expensesortname" id="oa-expensesortname-oaMatcostDetailPage"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>工厂投入
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="factoryamount" id="oa-factoryamount-oaMatcostDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>自理
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="selfamount" id="oa-selfamount-oaMatcostDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>费用金额
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="feeamount" id="oa-feeamount-oaMatcostDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>备注
                        <input type="text" name="remark" id="oa-remark-oaMatcostDetailPage"/>
                    </label>
                </div>

            </div>
        </div>
        <div id="oa-footer-oaMatcostDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removeDetail();" id="oa-remove-oaMatcostDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return saveDetail();" id="oa-ok-oaMatcostDetailPage" data-role="button" data-icon="check">确定</a>
        </div>
    </form>
</div>
</body>
</html>
