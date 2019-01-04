<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>相关档案与会计科目关系</title>

</head>

<body>
<form action="thirdDb/saveBaseSubjectRelation.do" method="post" id="baseSubjectRelation-form-add">
    <input type="hidden" name="baseSubjectRelation.id" value="${param.id}"/>
    <input type="hidden" name="baseSubjectRelation.dbid" value="${param.dbid}"/>
    <input type="hidden" name="baseSubjectRelation.basetype" value="${param.basetype}"/>
    <input type="hidden" name="baseSubjectRelation.codetype" value="${param.codetype}"/>
    <div class="pageContent">
        <p>
            <label>
                <c:if test="${param.basetype==1}">
                    银行档案编码:
                </c:if>
                <c:if test="${param.basetype==2}">
                    其它出入库类型编码:
                </c:if>
                <c:if test="${param.basetype==3 || param.basetype==4}">
                    费用编码:
                </c:if>
            </label>
            <input type="text" readonly="readonly" name="baseSubjectRelation.baseid" value="${param.baseid}" class="easyui-validatebox"/>
        </p>
        <p>
            <label>
                <c:if test="${param.basetype==1}">
                    银行档案名称:
                </c:if>
                <c:if test="${param.basetype==2}">
                    其它出入库类型名称:
                </c:if>
                <c:if test="${param.basetype==3 || param.basetype==4}">
                    费用名称:
                </c:if>
            </label>
            <input type="text" readonly="readonly" name="baseSubjectRelation.basename" value="${param.basename}" class="easyui-validatebox"/>
        </p>
        <p>
            <label>科目档案:</label>
            <input type="text" id="baseSubjectRelation-widget-subjectid" value="${baseSubjectRelation.subjectid}" name="baseSubjectRelation.subjectid" class="easyui-validatebox"/>
        </p>
    </div>
</form>
<script type="text/javascript">
    $(function(){
        $("#baseSubjectRelation-form-add").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                if(json.flag==true){
                    $.messager.alert("提醒",'保存成功');
                    showBaseTable('${param.basetype}');
                    $("#thidDbPage-dataRelation-Dialog").dialog('close',true);
                }else{
                    $.messager.alert("提醒",'保存失败');
                }
            }
        });

        $("#baseSubjectRelation-widget-subjectid").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            onlyLeafCheck:true,
            singleSelect:true,
            param:[{field:'dbid',op:'equal',value:'${param.dbid}'}]
        });


    });
</script>
</body>
</html>
