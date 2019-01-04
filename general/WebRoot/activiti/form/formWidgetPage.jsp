<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>form表单参照窗口页面</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true,border:false">
      <div data-options="region:'center',border:false">
          <table style="border: 1px solid #aaaaaa; border-collapse: collapse; width:100%; margin-bottom: 10px; padding: 5px;">
              <tr>
                  <td></td>
              </tr>
              <tr>
                  <td style="padding: 5px; width: 100%;">
                      <input id="activiti-widget-formWidgetPage" />
                  </td>
              </tr>
              <tr>
                  <td id="activiti-select-formWidgetPage" style="padding: 5px; width: 100%; border-top: 1px solid #aaaaaa;"></td>
              </tr>
          </table>
      </div>
      <div data-options="region:'south',border:false">
      </div>
  </div>

  <script type="text/javascript">

      $(function() {

          formWidgetOption.referwid = formWidgetOption.refid;

          formWidgetOption.onSelect = formWidgetOption.onChecked = formWidgetOption.onClear = function() {

              var vals = $('#activiti-widget-formWidgetPage').widget('getValue');
              var texts = $('#activiti-widget-formWidgetPage').widget('getText');
              var obj = $('#activiti-widget-formWidgetPage').widget('getObject') || {};

              $('#activiti-select-formWidgetPage').text(texts);
              formWidgetOption.selectData = {
                  value: vals,
                  text: texts
              };
              formWidgetOption.selectObject = obj;
          };

          // 商品参照窗口goodsWidget
          if(formWidgetOption.widget == 'goods') {

              $('#activiti-widget-formWidgetPage').goodsWidget(formWidgetOption);

          // 客户参照窗口customerWidget
          } else if(formWidgetOption.widget == 'customer') {

              $('#activiti-widget-formWidgetPage').customerWidget(formWidgetOption);

          // 普通参照窗口
          } else {

              $('#activiti-widget-formWidgetPage').widget(formWidgetOption);
          }
      })
  </script>
  </body>
</html>
