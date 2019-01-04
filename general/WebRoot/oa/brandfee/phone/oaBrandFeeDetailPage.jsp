<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>品牌费用申请单（支付）(手机)</title>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaBrandFeeDetailPage" data-dom-cache="true">
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

        $(document).on('pageshow', '#oa-pricedetail-oaBrandFeeDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var feeList = $.parseJSON(localStorage['details']);

                if(index != -1) {

                    var detail = feeList[index];

                    detail.factoryamount = formatterMoney(detail.factoryamount);
                    detail.customer =  detail.customerid ? (detail.customerid + ':' + detail.customername) : '';

                    $('#oa-form-oaBrandFeeDetailPage').form('load', detail);

                } else {

                    $('#oa-form-oaBrandFeeDetailPage').form('load', {});
                    $('#oa-form-oaBrandFeeDetailPage')[0].reset();
                }
            }

            // 客户
            $('#oa-customer-oaBrandFeeDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RL_T_BASE_SALES_CUSTOMER_PARENT_2",
                    onSelect: 'selectCustomer'
                });

            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-customerid-oaBrandFeeDetailPage').attr('value', (v || ':').split(':')[0]);
            });

            $("#oa-form-oaBrandFeeDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true,
                        required: true
                    },
                    reason: {
                        required: true,
                        byteMaxLength: [100]
                    },
                    customer: {
                        required: true
                    },
                    factoryamount: {
                        required: true
                    },
                    remark: {
                        byteMaxLength: [100]
                    }
                }
            });

        });

        //选择供应商
        function selectCustomer(data){
            $('#oa-customerid-oaBrandFeeDetailPage').val(data.id);
            $('#oa-customername-oaBrandFeeDetailPage').val(data.name);
            $('#oa-customer-oaBrandFeeDetailPage').val(data.id + ':' + data.name);
        }

    </script>
    <form id="oa-form-oaBrandFeeDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>费用明细</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">

                <div class="ui-field-contain">
                    <label>申请事由<span class="warnning">*</span>
                        <input type="text" name="reason" id="oa-reason-oaBrandFeeDetailPage"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>工厂投入<span class="warnning">*</span>
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="factoryamount" id="oa-factoryamount-oaBrandFeeDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>客户<span class="warnning">*</span>
                        <input type="text" name="customer" id="oa-customer-oaBrandFeeDetailPage" readonly="readonly"/>
                        <input type="hidden" name="customerid" id="oa-customerid-oaBrandFeeDetailPage" />
                        <input type="hidden" name="customername" id="oa-customername-oaBrandFeeDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>备注
                        <input type="text" name="remark" id="oa-remark-oaBrandFeeDetailPage"/>
                    </label>
                </div>

            </div>
         </div>
        <div id="oa-footer-oaBrandFeeDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removeDetail();" id="oa-remove-oaBrandFeeDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return saveDetail();" id="oa-ok-oaBrandFeeDetailPage" data-role="button" data-icon="check">确定</a>
        </div>
    </form>
</div>
</body>
</html>
