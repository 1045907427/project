<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>税种与会计科目关系</title>

</head>

<body>
<form action="thirdDb/updateBaseSubjectTax.do" method="post" id="baseSubjectRelation-form-add">
    <input type="hidden" name="thirdBaseSubjectTax.id" value="${thirdBaseSubjectTax.id}"/>
    <input type="hidden" name="thirdBaseSubjectTax.dbid" value="${thirdBaseSubjectTax.dbid}"/>
    <input type="hidden" name="thirdBaseSubjectTax.firstpid" value="${param.pid}"/>
    <div class="pageContent">
        <p>
            <label>
                税种编码:
            </label>
            <input type="text" readonly="readonly" name="thirdBaseSubjectTax.taxtype" value="${thirdBaseSubjectTax.taxtype}" class="easyui-validatebox"/>
        </p>
        <p>
            <label>
                税种名称:
            </label>
            <input type="text" readonly="readonly"  value="${thirdBaseSubjectTax.taxtypeName}" class="easyui-validatebox"/>
        </p>
        <p>
            <label>
                科目:
            </label>
            <input type="text" id="thirdGoodsSubjectEditPage-widget-subjectid"  name="thirdBaseSubjectTax.subjectid" value="${param.subjectid}" class="easyui-validatebox"/>
        </p>
    </div>
</form>
<script type="text/javascript">
    $(function(){
        $("#thirdGoodsSubjectEditPage-widget-subjectid").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            onlyLeafCheck:true,
            singleSelect:true,
            width:200,
            param : [{field: 'id', op: 'startwith', value: '${param.pid}'},{field:'isleaf',op:'equal',value:'1'},
                {field:'dbid',op:'equal',value:'${thirdBaseSubjectTax.dbid}'}]
        })
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
                    $("#thirdDb-table-taxList").datagrid('reload');
                    $("#thidDbPage-editSubjectTax-Dialog").dialog('close',true);

                }else{
                    $.messager.alert("提醒",'保存失败');
                }
            }
        });

        $("#baseSubjectRelation-widget-subjectid").widget({
            referwid:'RT_T_ERP_ACCOUNTCODE',
            onlyLeafCheck:true,
            singleSelect:true
        });


    });
</script>
</body>
</html>
