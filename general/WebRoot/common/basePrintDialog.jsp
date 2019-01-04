<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>全部打印对话框</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'center',border:false">
        <form id="deliveryAddDetailForm">
            <table class="content_table">
                <tr>
                    <td class="len70">标题</td>
                    <td class="len200">
                        <input type="text" id="basePrintDialog-title" name="title" value="${param.title}"/>
                    </td>
                </tr>
                <tr>
                    <td class="len70">分页条数</td>
                    <td class="len200">
                        <input type="text" id="basePrintDialog-rownum" name="rownum" value="30"/>
                    </td>
                </tr>
                <tr>
                    <td>说明</td>
                    <td>${param.summary}</td>
                </tr>
                <tr>
                    <td class="len70">纸张大小</td>
                    <td>
                    <select id="basePrintDialog-strPageName" onchange="changePage()" name="strPageName">
                        <option value="A4" selected>A4</option>
                        <option value="A3">A3</option>
                    </select>
                    打印方向
                    <select id="basePrintDialog-intOrient" name="intOrient" onchange="changeInorient()">
                        <option value="1">纵向打印，固定纸张</option>
                        <option value="2">横向打印，固定纸张</option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="hidden" id="basePrintDialog-url" value="${param.url}"/>
                        <input type="hidden" id="basePrintDialog-datagrid-id" value="${param.id}"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div style="text-align: center;padding:10px;">
            <button type="button" id="basePrintDialog-btn-preview" class="easyui-linkbutton"
                    style="margin-right:100px;">打印预览
            </button>
            <button type="button" id="basePrintDialog-btn-print" class="easyui-linkbutton">直接打印</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        changePage();
    })
    $("body").on("click", "#basePrintDialog-btn-preview", function () {
        basePrint(1);
    }).on("click", "#basePrintDialog-btn-print", function () {
        basePrint();
    });
    function basePrint(ispreview) {
        var id = $("#basePrintDialog-datagrid-id").val();
        var url = $("#basePrintDialog-url").val();
        var name = $("#basePrintDialog-title").val();
        var rownum = $("#basePrintDialog-rownum").val();
        var strPageName = $("#basePrintDialog-strPageName").val();
        var intOrient = $("#basePrintDialog-intOrient").val();
        url = url + (url.indexOf("?") > 0 ? "&" : "?") + "rownum=" + rownum;
        if (ispreview)
            url = url + "&preview=1";
        //封装查询条件
        var objecr = $("#" + id).datagrid("options");
        var queryParam = objecr.queryParams;
        if (null != objecr.sortName && null != objecr.sortOrder) {
            queryParam["sort"] = objecr.sortName;
            queryParam["order"] = objecr.sortOrder;
        }
        var strParam = JSON.stringify(queryParam);
        var commonCol = getCommonlCol(strParam, name, id, url);
        post(url, {title: name, commonCol: commonCol, param: strParam, formmater: objecr.authority.formmater,
            strPageName:strPageName,intOrient:intOrient});
    }

    function changePage(){
        var tablewidth='${param.tablewidth}';
        var strPageName = $("#basePrintDialog-strPageName").val();
        var pagewidth=getPageWidth(strPageName);
        //纸张像素小的时候使用横向打印
        if(pagewidth<=tablewidth){
            $("#basePrintDialog-intOrient").val("2");
        }else {
            $("#basePrintDialog-intOrient").val("1");
        }
        var pageRowNum=getPageRowNum(strPageName,$("#basePrintDialog-intOrient").val());
        console.log(pageRowNum);
        $("#basePrintDialog-rownum").val(pageRowNum);
    }

    //获取纸张的像素 毫米*3.78=像素
    function getPageWidth(strPageName){
        if("A4"==strPageName){
            return 210*3.78;
        }else if("A3"==strPageName){
            return 297*3.78;
        }
    }

    //判断打印纸每页可以打印的数据条数strPageName纸张类型,intOrient纸张方向
    function getPageRowNum(strPageName,intOrient){
        if("A4"==strPageName){
            if("1"==intOrient){
                //90是标题和报表名称的高度,18是每行数据的高度
                return 46;
            }else if("2"==intOrient){
                //90是标题和报表名称的高度,18是每行数据的高度
                return 30;
            }
        }else if("A3"==strPageName){
            if("1"==intOrient){
                //90是标题和报表名称的高度,18是每行数据的高度
                return 67;
            }else if("2"==intOrient){
                //90是标题和报表名称的高度,18是每行数据的高度
                return 46;
            }
        }
    }

    function changeInorient(){
        var strPageName = $("#basePrintDialog-strPageName").val();
        var pageRowNum=getPageRowNum(strPageName,$("#basePrintDialog-intOrient").val());
        console.log(pageRowNum);
        $("#basePrintDialog-rownum").val(pageRowNum);
    }


</script>
</body>
</html>