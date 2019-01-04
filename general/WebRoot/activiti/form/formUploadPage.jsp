<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <script type="text/javascript">
          var html = '${html }';
          var check = '${check }';
          if(html.length > 0) {
              parent.formEditor.execCommand('cleardoc');
              parent.formEditor.execCommand('insertHtml', html);
          } else if(check == 'false') {
              alert('导入的文件无效！');
          }
          parent.uploadform.reset();
      </script>
  </head>
  <body>
  </body>
</html>
