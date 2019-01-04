<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>菜单帮助文档</title>
	<%@include file="/include.jsp" %>
      <script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script>
      <script type="text/javascript" src="js/kindeditor/keconfig.js"></script>
      <script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script>
  </head>
  
  <body>
  <div style="text-align: center;font-size: 20px;font-weight: bold;">
      功能页面：${title}
  </div>
    <div>
      <form action="accesscontrol/saveOperateHelp.do" method="post" id="operate-form-help">
          <textarea id="messageNotice-form-addNotice-content" name="opreateHelp.content" rows="0" cols="0" style="width:98%;height:600px;">${content}</textarea>
          <input id="operate-help-id" type="hidden" name="opreateHelp.operateid" value="${id}"/>
          <input id="operate-help-title" type="hidden" name="opreateHelp.title" value="${title}"/>
          <input id="operate-help-md5url" type="hidden" name="opreateHelp.md5url" value="${md5url}"/>
      </form>
    </div>
    <div style="text-align: center;margin: 30px 0 30px 0;">
        <a href="javascript:void(0);" id="operate-help-save" class="button-qr">保存</a>
    </div>
  <script type="text/javascript">
            $(function(){
                var helpKEditor=KindEditor.create('#messageNotice-form-addNotice-content',{
                    allowPreviewEmoticons:false,
                    allowImageUpload:true,
                    allowFlashUpload:false,
                    allowMediaUpload:false,
                    allowFileUpload:false,
                    allowFileManager:false,
                    uploadJson : 'common/commonUploadAction/kindEditorUploadHelp.do',
                    resizeType: 1,
                    syncType : 'auto',
                    items: KEditor.kditem,
                    afterChange: function (e) {
                        this.sync();
                    }
                });
            });
            $("#operate-form-help").form({
                onSubmit: function(){
                    var flag = $(this).form('validate');
                    if(flag==false){
                        return false;
                    }
                    loading("提交中..");
                },
                success:function(data){
                    loaded();
                    var json = $.parseJSON(data);
                    if(json.flag){
                        $.messager.alert("提醒","保存成功。");
                    }else{
                        $.messager.alert("提醒","保存失败。");
                    }
                }
            });
            $("#operate-help-save").click(function(){
                $("#operate-form-help").submit();
            });
    </script>
  </body>
</html>
