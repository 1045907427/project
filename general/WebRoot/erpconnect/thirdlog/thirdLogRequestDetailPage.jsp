<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>凭证日志请求详细内容</title>

</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <table style="width: 100%;height: 100%">
            <tr style="height: 50%">
                <td style="width: 100px;">请求数据：</td>
                <td>
                    <textarea id="thirdlog-requestdata-textarea" style="width: 100%;height: 100%" readonly="readonly"></textarea>
                </td>
            </tr>
            <tr style="height: 25%">
                <td style="width: 100px;">返回内容：</td>
                <td>
                    <textarea id="thirdlog-responsedata-textarea" style="width: 100%;height: 100%" readonly="readonly"><c:out value="${responseData}"/></textarea>
                </td>
            </tr>
            <tr style="height: 25%">
                <td style="width: 100px;">验证内容：</td>
                <td>
                    <textarea id="thirdlog-checkdata-textarea" style="width: 100%;height: 100%" readonly="readonly"><c:out value="${checkData}"/></textarea>
                </td>
            </tr>
        </table>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            <input type="button" value="确 定" name="savenogo" id="thirdlog-button-addSave" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#thirdlog-button-addSave").click(function(){
            $("#erpconnect-dialog-thirdLogPage").dialog("close");
        });
    });
    loadData();
    function loadData(){
        var requestJson = '${requestData}';
        <%--var responseJson = '${responseData}';--%>
        <%--var checkJson = '${checkData}';--%>
        if(requestJson!=null && requestJson!=""){
            var requestFormatJson = format(requestJson,false);
            $("#thirdlog-requestdata-textarea").val(requestFormatJson);
        }
//        if(responseJson!=null && responseJson!=""){
//            var responseFormatJson = format(responseJson,false);
//            $("#thirdlog-responsedata-textarea").val(responseFormatJson);
//        }
//       if(checkJson!=null && checkJson!=""){
//            var checkFormatJson = format(checkJson,false);
//            $("#thirdlog-checkdata-textarea").val(checkFormatJson);
//        }
    }
    function format(txt,compress/*是否为压缩模式*/){/* 格式化JSON源码(对象转换为JSON文本) */
        var indentChar = '    ';
        if(/^\s*$/.test(txt)){
            alert('数据为空,无法格式化! ');
            return;
        }
        try{var data=eval('('+txt+')');}
        catch(e){
            alert('数据源语法错误,格式化失败! 错误信息: '+e.description,'err');
            return txt;
        };
        var draw=[],last=false,This=this,line=compress?'':'\n',nodeCount=0,maxDepth=0;

        var notify=function(name,value,isLast,indent/*缩进*/,formObj){
            nodeCount++;/*节点计数*/
            for (var i=0,tab='';i<indent;i++ )tab+=indentChar;/* 缩进HTML */
            tab=compress?'':tab;/*压缩模式忽略缩进*/
            maxDepth=++indent;/*缩进递增并记录*/
            if(value&&value.constructor==Array){/*处理数组*/
                draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line);/*缩进'[' 然后换行*/
                for (var i=0;i<value.length;i++)
                    notify(i,value[i],i==value.length-1,indent,false);
                draw.push(tab+']'+(isLast?line:(','+line)));/*缩进']'换行,若非尾元素则添加逗号*/
            }else   if(value&&typeof value=='object'){/*处理对象*/
                    draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);/*缩进'{' 然后换行*/
                    var len=0,i=0;
                    for(var key in value)len++;
                    for(var key in value)notify(key,value[key],++i==len,indent,true);
                    draw.push(tab+'}'+(isLast?line:(','+line)));/*缩进'}'换行,若非尾元素则添加逗号*/
                }else{
                        if(typeof value=='string')value='"'+value+'"';
                        draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);
                };
        };
        var isLast=true,indent=0;
        notify('',data,isLast,indent,false);
        return draw.join('');
    }
</script>
</body>
</html>
