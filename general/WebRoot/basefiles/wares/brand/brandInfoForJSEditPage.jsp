<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>品牌档案金税相关信息修改</title>
    <%@include file="/include.jsp" %>
</head>

<body>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    <form action="" id="baseInfo-form-brandInfoForJS" method="post" style="padding-top: 2px;">
        <table>
            <tr>
                <td style="text-align: right">
                    ERP品牌：
                </td>
                <td>
                    <div style="line-height:25px;">
                        编码：${brandInfo.id}
                    </div>
                    <div style="line-height:25px;">
                        名称：${brandInfo.name}
                    </div>
                    <input type="hidden" id="baseInfo-form-brandInfoForJS-brandid" name="id" value="${brandInfo.id}"  />
                </td>
            </tr>
            <tr>
                <td style="text-align: right">
                    金税簇编码：
                </td>
                <td>
                    <input type="text" id="baseInfo-form-brandInfoForJS-jsclusterid" name="jsclusterid" value="${brandInfo.jsclusterid}"  style="width: 200px;" />
                </td>
            </tr>
        </table>
    </form>
    </div>
    <div data-options="region:'south',border:false">
        <div style="text-align: right;line-height:35px;">
        <a href="javaScript:void(0);" id="baseInfo-form-brandInfoForJS-buttons-add" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-edit'">更新</a>&nbsp;
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){

        $("#baseInfo-form-brandInfoForJS-buttons-add").click(function(){
            var fromflag = $("#baseInfo-form-brandInfoForJS").form('validate');
            if (!fromflag) {
                $.messager.alert("提醒", "抱歉，请认真填写表单中数据");
                return false;
            }
            var jsclusterid = $("#baseInfo-form-brandInfoForJS-jsclusterid").val() || "";
            if(jsclusterid==""){
                $.messager.alert("提醒", "抱歉，未能找到相关金税簇编码");
                return false;
            }
            var formdata = $("#baseInfo-form-brandInfoForJS").serializeJSON();
            formdata.oldFromData = "";
            delete formdata.oldFromData;
            $.messager.confirm("提醒","确认修改品牌档案金税相关信息？",function(r){
                if(r){
                    loading("提交中..");
                    $.ajax({
                        url:'basefiles/updateBrandInfoForJS.do',
                        dataType:'json',
                        type:'post',
                        data:formdata,
                        success:function(json){
                            loaded();
                            if(json.flag==true){
                                $.messager.alert("提醒","修改成功!<br>");
                                try{
                                    var queryJSON = $("#wares-form-brand-form-brandListQuery").serializeJSON();
                                    $("#brand-table-list").datagrid("load",queryJSON);

                                    $("#brand-div-brandInfo").panel("refresh");
                                }catch(e){

                                }
                                $('#brand-dialog-showUpdateBrandInfoForJS-content').dialog("close");
                            }else{
                                $.messager.alert("提醒","修改失败!");
                            }
                        },
                        error:function(){
                            loaded();
                            $.messager.alert("错误","更新出错!");
                        }
                    });
                }
            });
        });
    });
</script>
</body>
</html>
