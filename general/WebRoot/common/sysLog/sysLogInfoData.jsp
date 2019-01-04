<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>系统用户日志详情</title>
    <%@include file="/include.jsp" %>
</head>
<body>

<script type="text/javascript">

    $(function(){

        var oldData = '${oldData}';
        var newData = '${newData}';
        var changeData = '${changeData}';


        while(newData.indexOf(",{}") > -1 ){
            newData = newData.replace(",{}","");
        }

        var parseData = eval(oldData.substring(oldData.indexOf("["),oldData.indexOf("]")+1));

        var beSortData =  newData.substring(newData.indexOf("["),newData.indexOf("]")+1);

        //尝试对[{},{]]格式数据进行解析
        try{
            beSortData = eval(beSortData);

            //判断解析后的数据是否为[{},{]]格式，对明细信息进行排序
            if(parseData instanceof Object && beSortData instanceof Object){

                var arr = sort(parseData,beSortData);
                if(arr != ""){
                    arr = arr.replace(",,",",");
                    arr = "[{"+arr+"}]}";

                    newData = newData.substring(0,newData.indexOf("["));
                    newData = newData+arr;
                }
            }
        }catch (e){

        }


        var changeData1 = changeData.split(",");
        var changeData2 = changeData.split(",");

        if(changeData.indexOf("数据过多")>-1){

            $("#remark").css('display','none');
            $("#oldpannel").css('display','none');
            $("#newpannel").css('display','none');

            document.getElementById("message").innerHTML =
                    "<p style='text-align: center'><font style='color: red;font-size: 30px;'>数据过多，无法显示</font></p>";

        }else if(changeData == ""){


            $("#remark").css('display','none');
            $("#oldpannel").css('display','none');
            $("#newpannel").css('display','none');

            document.getElementById("message").innerHTML =
                    "<p style='text-align: center'><font style='color: red;font-size: 30px;text-align: center'>无变更数据</font></p>";

        }else{

        //字符分割
        var oldstrs = oldData.split(",");
        var newstrs = newData.split(",");

        var oldarr = strCastToHtml("修改前数据",oldstrs,changeData1);
        var newarr = strCastToHtml("修改后数据",newstrs,changeData2);

        oldarr += "<br/>}<br/>";
        newarr +=  "<br/>}<br/>";

        document.getElementById("oldpannel").innerHTML = oldarr  ;
        document.getElementById("newpannel").innerHTML = newarr  ;

        }

    function sort(parseData,beSortData){
        var sortData = "";
        var arr = "";

        if(parseData.length < 1){
            sortData = parseData ;
        }else{
            sortData = parseData[0];
        }
        for(var i=0;i<beSortData.length;i++){
            if(arr != ""){
                arr = arr + "},{"
            }
            for(var key in sortData){
                var a = "";
                if(key in beSortData[i]){


                    var a = beSortData[i][key];
                }
                if(arr == ""){
                    arr = arr + "\"" +  key + "\":\"" + a + "\"" ;

                }else if(arr.charAt(arr.length - 1) === "{"){

                    arr = arr + "\"" +  key + "\":\"" + a + "\"," ;
                }else{
                    arr = arr + ",\"" +  key + "\":\"" + a + "\"" ;
                }
            }
        }

        return arr ;
    }


    //将后台数据转换为前台HTML
    function strCastToHtml(title,strs,changeData) {

        var htmlArr = "<br/><strong>" + title + "</strong><br/><br/>";
        var index = -1;
        var color = "";
        var count = -1;

        for (var i = 0; i < strs.length; i++, count,changeData) {
            //cursor 0 红字显示 1 黑字显示
            var cursor = "1";

            //去除json数据里的反斜杠
            while (strs[i].indexOf("\\") > -1) {
                strs[i] = strs[i].replace("\\", "");
            }

            var key = "";
            for (var j = 0; j < changeData.length; j++) {

                var keyvalue = changeData[j];
                var s = "";
                var compare = "";

                var code = strs[i].substring(strs[i].indexOf("\""),strs[i].indexOf(":"));
                code = code.replace("{","");//第一条变更数据 形如：{××× 故取代{

                //json字符串 第一个字段形如： ×××:[{"×××":"×××"  取第一个字段
                if(code.indexOf(":")>-1){
                    code = strs[i].substring(0,strs[i].indexOf(":"));
                }

                if(keyvalue.indexOf("#")>-1){

                    s = keyvalue.split("#");
                    compare = "\""+s[1]+"\"";

                    if (code == compare && s[0] == count){
                        key = j;
                        cursor = "0#"+s[0];
                        break;

                    }else if(code == compare && s[0] != count){
                        cursor = "0#"+s[0];
                        break;
                    }else{ //json字符串 第一个字段形如： ×××:[{"×××":"×××"  取第二个字段
                        code = strs[i].substring(strs[i].indexOf("\""),strs[i].lastIndexOf(":"));
                        if (code == compare && s[0] == count){
                            key = j;
                            cursor = "0#"+s[0];
                            break;

                        }else if(code == compare && s[0] != count){
                            cursor = "0#"+s[0];
                            break;
                        }
                    }
                }
                if(keyvalue == code){
                    cursor = "0";
                    break;
                }else{
                     continue ;
                }
            }

            if(cursor.indexOf("#")>-1 && key != ""){
                changeData.splice(key,1);
            }

            //变换Json字符里的花括号{}
            if (strs[i].indexOf("{}") > -1) {
                continue;

            }else if (strs[i].indexOf("{") == 0) {
                if(count != -1){
                    ++ count ;
                }
                strs[i] = strs[i].replace("{", "");

                htmlArr = htmlArr + "{" + "<br/>&nbsp;&nbsp;"

            }else if (strs[i].lastIndexOf("}") > 0) {//对json末尾的}进行处理

                if (strs[i].indexOf("]}") > -1) {

                    strs[i] = strs[i].replace("}]}", "}");

                } else {

                    strs[i] = strs[i].replace("}", "}");
                }
            }

            //对形如×××:[{  }]的json字符串进行格式组装
            if (index == 0 || strs[i].indexOf("[{") > -1) {
                var pos = "";
                if(cursor.indexOf("#")>-1 ){
                    var cur = cursor.split("#");
                    cursor = cur[0];
                    pos = cur[1];
                }

                if (index == -1) {
                    count = count +1;
                    index = 0;
                    var before = strs[i].split("[{")[0];
                    var after = strs[i].split("[{")[1];

                    if (cursor == 0 && pos == count) {
                        pos = "";
                        if (before.indexOf(keyvalue) == -1) {
                            color = "black";
                            htmlArr = htmlArr + "<font color='black'>" + before.replace(":", ": {") + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                            htmlArr = htmlArr + "<font color='red'>" + after + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                        } else {
                            color = "red";
                            htmlArr = htmlArr + "<font color='red'>" + before.replace(":", ": {") + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                            htmlArr = htmlArr + "<font color='red'>" + after + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                        }
                    } else {
                        color = "black";
                        htmlArr = htmlArr + "<font color='black'>" + before.replace(":", ": {") + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                        htmlArr = htmlArr + "<font color='black'>" + after + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                } else if (strs[i].lastIndexOf("}") == -1) {

                    if ( color == "red" || cursor == 0 && pos == count ) {
                        htmlArr = htmlArr + "<font color='red'>" + strs[i] + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                    } else {
                        htmlArr = htmlArr + "<font color='black'>" + strs[i] + "</font><br/>&nbsp;&nbsp;&nbsp;&nbsp;";
                    }
                } else {
                    //index = -1;
                    //color = "";
                    if (color == "red" || cursor == 0 && pos == count) {
                        htmlArr = htmlArr + "<font color='red'>" + strs[i].replace("}", "") + "</font><br/>&nbsp;&nbsp;<font color='red'>}</font><br/>&nbsp;&nbsp;";
                    } else {
                        htmlArr = htmlArr + "<font color='black'>" + strs[i].replace("}", "") + "</font><br/>&nbsp;&nbsp;<font color='black'>}</font><br/>&nbsp;&nbsp;";
                    }

                }
            } else if (cursor == 0) {
                htmlArr = htmlArr + "<font color='red'>" + strs[i] + "</font><br/>&nbsp;&nbsp;";

            } else {

                if(cursor.indexOf("#")>-1){
                    var cur = cursor.split("#");
                    cursor = cur[0];
                    pos = cur[1];
                }

                if(cursor == 0 && pos == count){

                    htmlArr = htmlArr + "<font color='red'>" + strs[i] + "</font><br/>&nbsp;&nbsp;";

                }else{
                    htmlArr = htmlArr + "<font color='black'>" + strs[i] + "</font><br/>&nbsp;&nbsp;";

                }
            }
        }
        return htmlArr;

    }

    });

</script>

<div id="remark" style="float: right">
    备注：<a style="color: #ff0000">标红色的数据为变动的数据&nbsp;&nbsp;</a><br/>
</div>

<div id = "oldpannel" style="height:auto; float:left; "></div>
<div id = "newpannel" style="height:auto; float:right; "></div>

<div id="message" />

</body>
</html>
