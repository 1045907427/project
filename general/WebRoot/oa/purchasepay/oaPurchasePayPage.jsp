<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>行政采购付款申请单</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false">
        <div id="oa-panel-oaPurchasePayPage">
        </div>
    </div>
</div>
<script type="text/javascript">
    <!--

    var url = '';
    var type = '${param.type }';
    var id = '${param.id }';
    var step = '${param.step }';
    var from = '${param.from }';

    if(type == 'handle') {

        if(step == '99') {
            url = 'oa/purchasepay/oaPurchasePayViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';
        } else if(step == '02') {
            url = 'oa/purchasepay/oaPurchasePayHandlePage2.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
        } else {
            url = 'oa/purchasepay/oaPurchasePayHandlePage.do?id=${param.id }&step=${param.step }&type=handle&processid=${param.processid }';
        }

    } else if(type == 'view') {

        url = 'oa/purchasepay/oaPurchasePayViewPage.do?id=${param.id }&step=${param.step }&type=view&processid=${param.processid }';

    } else if(type == 'print') {

        url = 'oa/purchasepay/oaPurchasePayPrintPage.do?id=${param.id }&type=print&processid=${param.processid }';
        window.location.href = url;
    }

    $(function() {

        $('#oa-panel-oaPurchasePayPage').panel({
            href: url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function() {

                // 附件
                $attach.attach({
                    <c:choose>
                        <c:when test="${param.type == 'view' }">
                            attach: false,
                        </c:when>
                        <c:otherwise>
                            attach: true,
                        </c:otherwise>
                    </c:choose>
                    businessid: '${param.id }',
                    processid: '${param.processid }'
                });

                $comments2.comments({
                    businesskey: '${param.id }',
                    processid: '${param.processid }',
                    type: 'vertical',
                    width: '120',
                    agree: null
                });

                // 付款银行
                $paybank.widget({
                    referwid: 'RL_T_BASE_FINANCE_BANK',
                    onlyLeafCheck: true,
                    singleSelect: true,
                    <c:choose>
                        <c:when test="${param.type eq 'handle' and empty param.step }">
                            readonly: false,
                            required: false,
                        </c:when>
                        <c:when test="${param.type eq 'handle' and param.step eq '02' }">
                            readonly: false,
                            required: true,
                        </c:when>
                        <c:when test="${param.type eq 'handle' and param.step eq '99' }">
                            readonly: true,
                            required: false,
                        </c:when>
                    </c:choose>
                    width: 140
                });

                // panel onLoad close
            }
        });
    });

    /**
     * 显示/隐藏OA查看链接
     */
    function switchOaLink(oaid) {

        if((oaid || '') == '') {

            $oalink.removeAttr('oaid');
            $oalink.hide();
            return true;
        }

        $oalink.attr('oaid', oaid);
        $oalink.show();
        return true;
    }

    /**
     * 查看OA
     */
    function viewOaProcess(oaid) {

        console.log(oaid);

        $.ajax({
            type: 'post',
            dataType: 'json',
            url: 'act/selectProcess.do',
            data: {id: oaid},
            success: function(json) {

                top.addOrUpdateTab("act/workViewPage.do?from=1&taskid="+ json.process.taskid+ "&taskkey="+ json.process.taskkey +"&instanceid=" + json.process.instanceid, "工作查看");
            }
        });

    }

    /**
     * 将付款金额转换成大写
     */
    function changeAmount2Upper(amount) {

        var upper = AmountUnitCnChange(amount);
        $upperpayamount.val(upper);
        return true;
    }

    /**
     * 提交表单
     *
     * @param callBack
     * @param args
     */
    function oapurchasepay_handle_save_form_submit(callBack, args) {

        $form.form({
            onSubmit: function(param) {

                var flag = $form.form('validate');
                if(!flag) {

                    return false;
                }

                loading("提交中...");
            },
            success: function(data) {

                loaded();
                var json;
                try{
                    json = $.parseJSON(data);
                }catch(e){
                    $.messager.alert('错误', '保存失败！');
                    return false;
                }

                // 保存失败
                if(data.flag) {
                    $.messager.alert('提醒', '保存失败！');
                    return false;
                }

                // 保存成功
                $.messager.alert('提醒', '保存成功。');
                if(callBack.data != undefined && callBack.data != null) {

                    callBack.data(json.backid);
                    return false;
                }

            }
        });

        $form.submit();
    }

    // 提交表单(工作页面提交表单接口方法)
    function workFormSubmit(call, args) {

        oapurchasepay_handle_save_form_submit(call, args);
    }

    -->
</script>
</body>
</html>