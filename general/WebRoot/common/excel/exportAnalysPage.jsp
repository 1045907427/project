<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>全局导出</title>
</head>
<body>
    <form action="" method="post" id="common-form-exportAnalysPage">
        <div style="width:350px;height:150px;overflow:hidden;margin: 5px;">
            <label style="width:80px">导出文件名：</label>
            <input type="text" class="easyui-validatebox" id="common-name-exportAnalysPage" required="required" name="exportname" value="${exportname}" style="width: 200px;"/>
            </br>
            <label style="width:80px">导出格式&nbsp;：</label>
            <input type="radio" name="fileType" value="xls" checked="checked" style="cursor: pointer"/>xls(97/2003)
            <input type="radio" name="fileType" value="xlsx" style="cursor: pointer"/> xlsx(2007)
            <input type="radio" name="fileType" value="csv" style="cursor: pointer"/>csv
            </br>
            <span style="color: red;">备注：xlsx(2007)与CSV格式导出速度较快，CSV格式文件比较适合导出数据条数多</span>
            <div align="center">
                <a href="javascript:;" id="common-export-exportAnalysPage" class="easyui-linkbutton" data-options="iconCls:'icon-sum'">导出</a>
            </div>
        </div>
        <input type="hidden" id="exportParam" name="param"/>
        <input type="hidden" id="common-formmater-exportAnalysPage" name="formmater" />
        <input type="hidden" id="common-commonCol-exportAnalysPage" name="commonCol" value="${commonCol}"/>
        <input type="hidden" name="colName" value="${colName}"/>
    </form>
    <script type="text/javascript">

        $(function(){

            $("#common-export-exportAnalysPage").click(function(){
                $("#common-form-exportAnalysPage").submit();
                $("#dialog-autoexport").dialog('close', true);
            });
//            $("#common-form-exportAnalysPage").form({
//                onSubmit: function(){
//                    loading("导出中，请等待..");
//                },
//                success:function(data){
//                    loaded();
//                    $("#dialog-autoexport").dialog('close', true);
//                }
//            });
        });

    </script>

</body>
</html>
