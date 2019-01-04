<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>商品与会计科目关系</title>

</head>

<body>
<form action="thirdDb/updateBaseSubjectGoods.do" method="post" id="baseSubjectRelation-form-add">
    <input type="hidden" name="thirdBaseSubjectGoods.id" value="${thirdBaseSubjectGoods.id}"/>
    <input type="hidden" name="thirdBaseSubjectGoods.addtype" value="${thirdBaseSubjectGoods.addtype}"/>
    <input type="hidden" name="thirdBaseSubjectGoods.dbid" value="${thirdBaseSubjectGoods.dbid}"/>
    <input type="hidden" name="thirdBaseSubjectGoods.firstpid" value="${param.pid}"/>
    <div class="pageContent">
        <p>
            <label>
                <c:if test="${thirdBaseSubjectGoods.addtype=='1'}">
                    商品编码:
                </c:if>
                <c:if test="${thirdBaseSubjectGoods.addtype=='2'}">
                    品牌编码:
                </c:if>
            </label>
            <c:if test="${thirdBaseSubjectGoods.addtype=='1'}">
                <input type="text" readonly="readonly" name="thirdBaseSubjectGoods.goodsid" value="${thirdBaseSubjectGoods.goodsid}" class="easyui-validatebox"/>
            </c:if>
            <c:if test="${param.addtype=='2'}">
                <input type="text" readonly="readonly" name="thirdBaseSubjectGoods.brandid" value="${thirdBaseSubjectGoods.brandid}" class="easyui-validatebox"/>
            </c:if>
        </p>
        <p>
            <label>
                <c:if test="${thirdBaseSubjectGoods.addtype=='1'}">
                    商品名称:
                </c:if>
                <c:if test="${thirdBaseSubjectGoods.addtype=='2'}">
                    品牌名称:
                </c:if>
            </label>
            <c:if test="${param.addtype=='1'}">
                <input type="text" readonly="readonly"  value="${thirdBaseSubjectGoods.goodsname}" class="easyui-validatebox"/>
            </c:if>
            <c:if test="${param.addtype=='2'}">
                <input type="text" readonly="readonly"  value="${thirdBaseSubjectGoods.brandname}" class="easyui-validatebox"/>
            </c:if>
        </p>
        <p>
            <label>
                科目:
            </label>
            <input type="text" id="thirdGoodsSubjectEditPage-widget-subjectid"  name="thirdBaseSubjectGoods.subjectid" value="${param.subjectid}" class="easyui-validatebox"/>
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
                {field:'dbid',op:'equal',value:'${thirdBaseSubjectGoods.dbid}'}]
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
                    $("#thirdDb-table-goodsList").datagrid('reload');
                    $("#thidDbPage-editSubjectGoods-Dialog").dialog('close',true);

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
