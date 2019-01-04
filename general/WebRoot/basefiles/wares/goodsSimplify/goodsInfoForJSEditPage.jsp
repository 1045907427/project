<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>商品档案金税相关信息修改</title>
    <%@include file="/include.jsp" %>
</head>

<body>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
    <form action="" id="baseInfo-form-goodsInfoForJS" method="post" style="padding-top: 2px;">
        <table>
            <tr>
                <td style="text-align: right">
                    ERP商品：
                </td>
                <td>
                    <div style="line-height:25px;">
                        编码：${goodsInfo.id}
                    </div>
                    <div style="line-height:25px;">
                        名称：${goodsInfo.name}
                    </div>
                    <input type="hidden" id="baseInfo-form-goodsInfoForJS-goodsid" name="id" value="${goodsInfo.id}"  />
                </td>
            </tr>
            <tr>
                <td style="text-align: right">
                    金税商品编码：
                </td>
                <td>
                    <input type="text" id="baseInfo-form-goodsInfoForJS-jsgoodsid" name="jsgoodsid" value="${goodsInfo.jsgoodsid}"  style="width: 200px;"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right">
                    金税税收分类编码：
                </td>
                <td>
                    <input type="text" id="baseInfo-form-goodsInfoForJS-jstaxsortid" name="jstaxsortid" value="${goodsInfo.jstaxsortid}"  style="width: 200px;" />
                </td>
            </tr>
        </table>
    </form>
    </div>
    <div data-options="region:'south',border:false">
        <div style="text-align: right;line-height:35px;">
        <a href="javaScript:void(0);" id="baseInfo-form-goodsInfoForJS-buttons-add" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-edit'">更新</a>&nbsp;
        </div>
    </div>

</div>
<script type="text/javascript">
    $(function(){

        $("#baseInfo-form-goodsInfoForJS-jstaxsortid").widget({
            referwid:'RL_T_BASE_JSTAXTYPECODE',
            singleSelect:true,
            width:'200',
            required:true
        });


        $("#baseInfo-form-goodsInfoForJS-buttons-add").click(function(){
            var fromflag = $("#baseInfo-form-goodsInfoForJS").form('validate');
            if (!fromflag) {
                $.messager.alert("提醒", "抱歉，请认真填写表单中数据");
                return false;
            }
            var goodsid = $("#baseInfo-form-goodsInfoForJS-goodsid").val() || "";
            if(goodsid==""){
                $.messager.alert("提醒", "抱歉，未能找到相关商品编码");
                return false;
            }
            var formdata = $("#baseInfo-form-goodsInfoForJS").serializeJSON();
            formdata.oldFromData = "";
            delete formdata.oldFromData;
            $.messager.confirm("提醒","确认修改商品档案金税相关信息？",function(r){
                if(r){
                    loading("提交中..");
                    $.ajax({
                        url:'basefiles/updateGoodsInfoForJS.do',
                        dataType:'json',
                        type:'post',
                        data:formdata,
                        success:function(json){
                            loaded();
                            if(json.flag==true){
                                $.messager.alert("提醒","修改成功!<br>");
                                try{
                                    var queryJSON = $("#wares-form-goodsInfoListQuery").serializeJSON();
                                    $("#wares-table-goodsInfoList").datagrid("load",queryJSON);
                                }catch(e){

                                }
                                $('#wares-dialog-showUpdateGoodsInfoForJS-content').dialog("close");
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
