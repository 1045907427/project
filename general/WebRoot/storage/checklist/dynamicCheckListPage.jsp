<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>申请动态盘点页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'center',border:true">
    <div>
      <form action="storage/addDynamicCheckList.do" method="post" id="storage-form-DynamicCheckList" style="padding: 10px;">
        <table cellpadding="2" cellspacing="2" border="0">
          <tr>
              <td>盘点</td>
            <td colspan="3">
                <input class="easyui-numberspinner" name="days" style="width:100px;" data-options="min: 1,max: 100" value="1"/>
                天内库存变动过商品
            </td>
          </tr>
          <tr>
            <td>仓库：</td>
            <td>
              <input type="text" id="storage-dynamiccheckList-storageid" name="storageid"/>
            </td>
            <td class="len80 left">盘点人：</td>
            <td style="text-align: left">
                <input type="text" id="storage-dynamiccheckList-checkuserid" name="checkuserid"/>
            </td>
          </tr>
            <tr>
                <td>拆分：</td>
                <td>
                   <select name="splitbill" style="width:140px;">
                       <option value="1">不拆分</option>
                       <option value="2">按品牌拆分</option>
                       <option value="3">按供应商拆分</option>
                       <option value="4">按商品分类拆分</option>
                   </select>
                </td>

            </tr>
          <tr>
            <td>备注：</td>
            <td colspan="3">
              <input type="text" name="remark" style="width:376px;" />
            </td>
          </tr>
        </table>
      </form>
    </div>
  </div>
  <div data-options="region:'south'" style="height: 30px;border: 0px;">
    <div align="right">
      <a href="javaScript:void(0);" id="storage-save-DynamicCheckList" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">生成盘点单</a>
    </div>
  </div>
</div>
<script type="text/javascript">
  $(function(){
    $("#storage-dynamiccheckList-storageid").widget({
      width:140,
      required:true,
      referwid:'RL_T_BASE_STORAGE_INFO',
      singleSelect:false,
      onlyLeafCheck:false
    });
    $("#storage-dynamiccheckList-checkuserid").widget({
      referwid:'RL_T_BASE_PERSONNEL_STORAGER',
      width:140,
      singleSelect:true
    });
    $("#storage-save-DynamicCheckList").click(function(){
      $.messager.confirm("提醒","确认生成盘点单?",function(r){
        if(r){
          $("#storage-form-DynamicCheckList").submit();
        }
      });
    });
    $("#storage-form-DynamicCheckList").form({
        onSubmit: function(){
            var flag = $(this).form('validate');
            if(flag==false){
                $.messager.alert("提醒","请选择仓库!");
                return false;
            }
            loading("申请中..");
        },
        success:function(data){
            loaded();
            var json = $.parseJSON(data);
            if(json.flag){
                $.messager.alert("提醒","动态盘点申请成功。"+json.msg);
                $("#storage-datagrid-checkListPage").datagrid("reload");
                $('#storage-dialog-dynamicCheckList').dialog('close');
            }else{
                $.messager.alert("提醒",json.msg);
            }
        }
    });
  });
</script>
</body>
</html>
