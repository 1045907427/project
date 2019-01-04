<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>客户费用申请单（账扣）(手机)</title>
</head>
<body>
<div data-role="page" id="oa-pricedetail-oaCustomerFeeDetailPage" data-dom-cache="true">
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

        $(document).on('pageshow', '#oa-pricedetail-oaCustomerFeeDetailPage', function(e) {

            if(!isWidgetPageBack()) {

                var index = localStorage.getItem('index');
                var feeList = $.parseJSON(localStorage['details']);

                if(index != -1) {

                    var detail = feeList[index];

                    detail.factoryamount = formatterMoney(detail.factoryamount);
                    detail.selfamount = formatterMoney(detail.selfamount);

                    detail.brand =  detail.brandid + ':' + detail.brandname;
                    detail.dept =  detail.deptid + ':' + detail.deptname;
                    detail.supplier =  detail.supplierid ? (detail.supplierid + ':' + detail.suppliername) : '';

                    $('#oa-form-oaCustomerFeeDetailPage').form('load', detail);

                } else {

                    $('#oa-form-oaCustomerFeeDetailPage').form('load', {});
                    $('#oa-form-oaCustomerFeeDetailPage')[0].reset();
                }
            }

            // 供应商
            $('#oa-supplier-oaCustomerFeeDetailPage').off().on('click', function() {
                androidWidget({
                    type: 'widget',
                    referwid : "RL_T_BASE_BUY_SUPPLIER",
                    onSelect: 'selectSupplier'
                });

            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-supplierid-oaCustomerFeeDetailPage').attr('value', (v || ':').split(':')[0]);
            });

            // 品牌控件
            $('#oa-brand-oaCustomerFeeDetailPage').off().on('click', function() {
                    androidWidget({
                        type: 'widget',
                        referwid : "RL_T_BASE_GOODS_BRAND",
                        onSelect: 'selectBrand'
                    });

            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-brandid-oaCustomerFeeDetailPage').attr('value', (v || ':').split(':')[0]);
            });

            // 部门控件
            $('#oa-dept-oaCustomerFeeDetailPage').off().on('click', function() {
                   androidWidget({
                       type: 'widget',
                       referwid : "RL_T_BASE_DEPARTMENT_SELLER",
                       onSelect: 'selectDept'
                   });

            }).on('change', function(e) {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-deptid-oaCustomerFeeDetailPage').attr('value', v);
            });

            $("#oa-form-oaCustomerFeeDetailPage").validate({
                debug: true,
                rules: {
                    money: {
                        money: true
                    },
                    supplier: {
                        required: true
                    },
                    dept: {
                        required: true
                    },
                    brand: {
                        required: true
                    },
                    factoryamount: {
                        required: true
                    },
                    selfamount: {
                        required: true
                    },
                    remark: {
                        byteMaxLength: [50]
                    },
                    branduser: {
                        byteMaxLength: [20]
                    }
                }
            });

        });

        //选择供应商
        function selectSupplier(data){
            $('#oa-supplierid-oaCustomerFeeDetailPage').val(data.id);
            $('#oa-suppliername-oaCustomerFeeDetailPage').val(data.name);
            $('#oa-supplier-oaCustomerFeeDetailPage').val(data.id + ':' + data.name);

            $('#oa-deptid-oaCustomerFeeDetailPage').val(data.buydeptid);
            $('#oa-deptname-oaCustomerFeeDetailPage').val(data.buydeptname);
            $('#oa-dept-oaCustomerFeeDetailPage').val(data.buydeptid+ ':' + data.buydeptname);
        }

        //选择部门
        function selectDept(data){
            $('#oa-deptid-oaCustomerFeeDetailPage').val(data.id);
            $('#oa-deptname-oaCustomerFeeDetailPage').val(data.name);
            $('#oa-dept-oaCustomerFeeDetailPage').val(data.id + ':' + data.name);
        }

        //选择品牌
        function selectBrand(data){
            $('#oa-brandid-oaCustomerFeeDetailPage').val(data.id);
            $('#oa-brandname-oaCustomerFeeDetailPage').val(data.name);
            $('#oa-brand-oaCustomerFeeDetailPage').val(data.id + ':' + data.name);
        }

    </script>
    <form id="oa-form-oaCustomerFeeDetailPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>费用明细</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>供应商<span class="warnning">*</span>
                        <input type="text" name="supplier" id="oa-supplier-oaCustomerFeeDetailPage" readonly="readonly"/>
                        <input type="hidden" name="supplierid" id="oa-supplierid-oaCustomerFeeDetailPage" />
                        <input type="hidden" name="suppliername" id="oa-suppliername-oaCustomerFeeDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>部门<span class="warnning">*</span>
                        <input type="text" name="dept" id="oa-dept-oaCustomerFeeDetailPage" readonly="readonly"/>
                        <input type="hidden" name="deptid" id="oa-deptid-oaCustomerFeeDetailPage"/>
                        <input type="hidden" name="deptname" id="oa-deptname-oaCustomerFeeDetailPage"/>
                    </label>
                </div>
                
                <div class="ui-field-contain">
                    <label>品牌<span class="warnning">*</span>
                        <input type="text" name="brand" id="oa-brand-oaCustomerFeeDetailPage" readonly="readonly"/>
                        <input type="hidden" name="brandid" id="oa-brandid-oaCustomerFeeDetailPage" />
                        <input type="hidden" name="brandname" id="oa-brandname-oaCustomerFeeDetailPage" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>申请事由
                        <input type="text" name="reason" id="oa-reason-oaCustomerFeeDetailPage"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>工厂投入<span class="warnning">*</span>
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="factoryamount" id="oa-factoryamount-oaCustomerFeeDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>自理<span class="warnning">*</span>
                        <input type="number" precision="2" class="number" data-clear-btn="true" name="selfamount" id="oa-selfamount-oaCustomerFeeDetailPage" money="true"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>品牌责任人
                        <input type="text" name="branduser" id="oa-branduser-oaCustomerFeeDetailPage"/>
                    </label>
                </div>

            </div>
         </div>
        <div id="oa-footer-oaCustomerFeeDetailPage" data-role="footer" data-position="fixed" data-tap-toggle="false">
            <a href="#main" onclick="javascript:return removeDetail();" id="oa-remove-oaCustomerFeeDetailPage" data-role="button" data-icon="minus">删除</a>
            <a href="#main" onclick="javascript:return saveDetail();" id="oa-ok-oaCustomerFeeDetailPage" data-role="button" data-icon="check">确定</a>
        </div>
    </form>
</div>
</body>
</html>
