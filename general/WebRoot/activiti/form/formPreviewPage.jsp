<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>表单预览</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <script type="text/javascript">

      function retHtml() {
          return '${detail }';
      }
      function retTemplate() {
          return '${template }';
      }
      function setFrameHeight(height) {

          $('iframe#viewframe').attr('height', (height + 50) +'px');
      }

      $(function() {

          $('#activiti-export-formPreviewPage').off().on('click', function() {

              $('#activiti-exportForm-formPreviewPage').submit();
          });
      });

  </script>
  <div class="buttonBG" id="activiti-buttons-formPreviewPage" style="height:26px; border-bottom: solid 1px #aaa;">
      <a href="javascript:void(0);" class="easyui-linkbutton" id="activiti-export-formPreviewPage" data-options="plain:true,iconCls:'button-export'">导出</a>
  </div>
  <div id="activiti-dummy-formPreviewPage" class="nodisplay">
      <form id="activiti-exportForm-formPreviewPage" action="act/exportForm.do" target="" method="post">
          <div class="nodisplay">
              <input name="key" value="${form.unkey }"/>
          </div>
      </form>
  </div>
  <iframe id="viewframe" name="viewframe" width="100%" height="200px;" frameborder="0" src="act/formPreviewPage3.do?flag=2&unkey=${form.unkey }&name=${form.name }&intro=${form.intro }">
  </iframe>
  </body>
</html>
