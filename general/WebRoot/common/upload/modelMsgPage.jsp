<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript">

        //$(function() {

            var msg = '${msg }' || '';
            if(msg != '') {

                parent.showMsg('${msg }');
            }

        //});

    </script>
</head>
<body>
</body>
</html>
