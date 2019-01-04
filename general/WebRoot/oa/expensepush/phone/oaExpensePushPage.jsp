<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>费用冲差支付申请单(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">

        <%--
        var type = '${param.type }';
        var step = '${param.step }';
        var url = '';
        if(type == 'handle') {

            if(step == '01') {
                url = 'oa/expensepush/oaExpensePushHandlePage.do?type=${param.type }&to=phone&processid=${param.processid }&taskid=${param.taskid }&id=${param.id }';
            }
        } else {

            url = 'oa/expensepush/oaExpensePushViewPage.do?type=${param.type }&to=phone&processid=${param.id }&taskid=${process.taskid }&id=${process.businessid }'
        }

        location.href = url;
        --%>

    </script>
</head>
<body>

</body>
</html>
