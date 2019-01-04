<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>金税导入导出人员系统参数设置</title>
    <%@include file="/include.jsp" %>
</head>

<body>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    <form action="" id="account-form-jskpSysParamConfig" method="post" style="padding-top: 2px;">
        <table>
            <tr>
                <td>
                    收款人：
                </td>
                <td>
                    <input type="text" id="account-form-jskpSysParamConfig-receipterid" name="receipterid" value="${jskpDefaultReceipter}"  />
                    <input type="hidden" id="account-form-jskpSysParamConfig-receiptername" name="receiptername"   />
                </td>
            </tr>
            <tr>
                <td>
                    复核人：
                </td>
                <td>
                    <input type="text" id="account-form-jskpSysParamConfig-checkerid" name="checkerid" value="${jskpDefaultChecker}"  />
                    <input type="hidden" id="account-form-jskpSysParamConfig-checkername" name="checkername"   />
                </td>
            </tr>
        </table>
    </form>
    </div>
    <div data-options="region:'south',border:false">
        <div style="text-align: right;line-height:35px;">
        <a href="javaScript:void(0);" id="account-form-jskpSysParamConfig-buttons-add" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-edit'">更新</a>&nbsp;
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){
        //收款人
        $("#account-form-jskpSysParamConfig-receipterid").widget({
            referwid:'RT_T_BASE_PERSONNEL',
            singleSelect:false,
            width:350,
            initValue:'${jskpDefaultReceipter}',
            onlyLeafCheck:false,
            onSelect:function(){
                var name = $("#account-form-jskpSysParamConfig-receipterid").widget("getText") || "";
                $("#account-form-jskpSysParamConfig-receiptername").val(name);
            },
            onClear:function () {
                $("#account-form-jskpSysParamConfig-receiptername").val("");
            },
            onLoadSuccess:function(){
                var name = $("#account-form-jskpSysParamConfig-receipterid").widget("getText") || "";
                $("#account-form-jskpSysParamConfig-receiptername").val(name);
            }
        });


        //复核人
        $("#account-form-jskpSysParamConfig-checkerid").widget({
            referwid:'RT_T_BASE_PERSONNEL',
            singleSelect:false,
            width:350,
            initValue:'${jskpDefaultChcker}',
            onlyLeafCheck:false,
            onSelect:function(){
                var name = $("#account-form-jskpSysParamConfig-checkerid").widget("getText") || "";
                $("#account-form-jskpSysParamConfig-checkername").val(name);
            },
            onClear:function () {
                $("#account-form-jskpSysParamConfig-checkername").val("");
            },
            onLoadSuccess:function(){
                var name = $("#account-form-jskpSysParamConfig-checkerid").widget("getText") || "";
                $("#account-form-jskpSysParamConfig-checkername").val(name);
            }
        });

        $("#account-form-jskpSysParamConfig-buttons-add").click(function(){
            var fromflag = $("#account-form-jskpSysParamConfig").form('validate');
            if (!fromflag) {
                $.messager.alert("提醒", "抱歉，请认真填写表单中数据");
                return false;
            }
            var formdata = $("#account-form-jskpSysParamConfig").serializeJSON();
            formdata.oldFromData = "";
            delete formdata.oldFromData;
            $.messager.confirm("提醒","确认修改更新金税开票人员相关系统参数配置吗？",function(r){
                if(r){
                    loading("提交中..");
                    $.ajax({
                        url:'account/receivable/updateJSKPSysParamConfig.do',
                        dataType:'json',
                        type:'post',
                        data:formdata,
                        success:function(json){
                            loaded();
                            if(json.flag==true){
                                $.messager.alert("提醒","修改成功!<br>");
                                try{
                                    $("#account-panel-salesInvoiceBillPage").panel("refresh");
                                }catch(e){

                                }
                                $('#account-dialog-showJSKPSysParamConfigOper-content').dialog("close");
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
