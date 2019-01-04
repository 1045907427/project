<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>选择账套</title>
</head>
<body>
    <div style="height: 80px">
        <br/>
        &nbsp;&nbsp;&nbsp;账套：<select id="sales-choose-databaseid"  style="width: 230px;" name="databaseid">
        <c:forEach items="${dbList }" var="list">
            <option value="${list.id }">${list.dbasename }</option>
        </c:forEach>
    </select>
    </div>
    <div class="buttonDetailBG" style="height:30px;text-align:right;">
        <input type="button" name="savegoon" id="sales-savegoon-orderDetailAddPage" value="确定"/>
    </div>
    <script type="text/javascript">
        var type = '${type}';
        $("#sales-savegoon-orderDetailAddPage").click(function () {
            var databaseid = $("#sales-choose-databaseid").val();
            if(type == 1){
                $.messager.confirm("提醒","是否根据数据库连接同步ERP中的会计科目到系统中？",function(r){
                    if(r){
                        loading("同步中..");
                        $.ajax({
                            url: 'erpconnect/syncCodeToSystem.do',
                            type: 'post',
                            dataType: 'json',
                            data:{dbid:databaseid},
                            success: function (json) {
                                loaded();
                                if (json.flag) {
                                    $.messager.alert("提醒", "同步成功。");
                                } else {
                                    $.messager.alert("提醒", "同步失败。");
                                }
                            },
                            error: function () {
                                loaded();
                                $.messager.alert("错误", "同步失败");
                            }
                        });
                    }
                });
            }else if(type == 2){
                $.messager.confirm("提醒","是否根据数据库连接同步ERP中的会计凭证类别到系统中？",function(r){
                    if(r){
                        loading("同步中..");
                        $.ajax({
                            url: 'erpconnect/syncCodeSignToSystem.do',
                            type: 'post',
                            dataType: 'json',
                            data:{dbid:databaseid},
                            success: function (json) {
                                loaded();
                                if (json.flag) {
                                    $.messager.alert("提醒", "同步成功。");
                                } else {
                                    $.messager.alert("提醒", "同步失败。");
                                }
                            },
                            error: function () {
                                loaded();
                                $.messager.alert("错误", "同步失败");
                            }
                        });
                    }
                });

            }else if(type == 3){

            }else if(type == 4){


            }
            $("#erp-dialog-chooseAccount-content").dialog('close',true);
        });

    </script>
</body>
</html>
