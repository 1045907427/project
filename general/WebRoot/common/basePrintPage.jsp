<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<!DOCTYPE html>
<head>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">--%>
    <title>${title}</title>
    <style id="style1" type="text/css">
        h1 { font-size: 24px; text-align: center }
        table{
            border-collapse: collapse; margin: auto;
            /*table-layout: fixed;*/
        }
        th, td {
            border: 1px solid #aaa;
        }
        th,td div{
            max-width:200px;
            overflow:hidden;
            white-space:nowrap;
        }
    </style>
    <base href="<%=basePath%>"/>
    <script type="text/javascript" src="js/LodopFuncs.js?v=20170307" charset="UTF-8" defer="defer"></script>
    <%--<script type="text/javascript" src="js/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="js/jquery.agreportprint.js"></script>--%>
</head>
<body id="body1">
<h1>${title}</h1>
${body}
<script type="text/javascript">
    //print();
    setTimeout(function () {
        var theLodopApp = MyLodop.getLodop();
        if (!theLodopApp && document.readyState !== "complete") {
            alert("C-Lodop没准备好，请稍后再试！");
            return;
        }
        var style = "<style>" + document.getElementById("style1").innerHTML + "</style>";
        var title = "<h1>${title}</h1>";
        var tables = document.getElementsByTagName("table");
        var html = "<style>" + document.getElementById("style1").innerHTML + "</style><body>" + document.getElementById("body1").innerHTML + "</body>";
        theLodopApp.PRINT_INIT("${title}-" + (Date.parse(new Date()) / 1000));
        theLodopApp.SET_PRINT_PAGESIZE('${intOrient}', 0, 0, '${strPageName}');
        theLodopApp.SET_PRINT_STYLEA(0, "HtmWaitMilSecs", 1000);
        for (var i = 0; i < tables.length; i++) {
            if (i != 0)
                theLodopApp.NewPage();
            theLodopApp.ADD_PRINT_HTM(0, 0, "100%", "100%", style + title + tables[i].outerHTML);
        }
        if ('${param.preview}' == '1') {
            theLodopApp.PREVIEW();
        } else {
            theLodopApp.PRINT();
        }
    }, 500);
</script>
</body>
</html>
