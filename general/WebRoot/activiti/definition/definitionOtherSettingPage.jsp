<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>工作关键字设置页面</title>
      <%@include file="/include.jsp" %>
  </head>
  <body>
  	<style type="text/css">
        .accordion .accordion-header {
            background: #547084;
            filter: none;
            padding: 0px;
        }
        .accordion .accordion-header-selected {
            background: #374e60;
        }
        .accordion .accordion-header-selected .panel-title {
            color: #ffffff;
        }
        .other-tip {
			border: solid 1px #7babcf;
			margin: 2px 0px 2px 0px;
			background: #efefef;
		}
		.content {
			border-collapse: collapse;
			border: 1px solid #7babcf;
			width: 100%;
			table-layout: fixed;
			word-break: break-all;
		}
		.content td {
			border: solid 1px #7babcf;
			padding: 5px;
		}
		.loadingtip {
			margin: 5px;
			padding: 1px;
		}
  	</style>
    <div id="activiti-accordion-definitionOtherSettingPage" class="easyui-accordion" style="">
        <div title="关键字设定" data-options="" style="overflow:auto; padding: 2px;">
            <c:if test="${not ((empty definition.businessurl) and ((empty definition.formkey) and (empty form))) }">
                <div style="border-bottom:1px solid #7babcf;">
                    <a class="easyui-linkbutton" id="activiti-save-definitionOtherSettingPage" data-options="plain:true,iconCls:'button-save'" onclick="javascript:void(0);">保存</a>
                </div>
            </c:if>
            <div class="other-tip">
                <ol>
                    <li>关键字设定依赖于在线表单或者URL表单。如果流程的在线表单或者URL表单设置发生改变时，关键字的设置将失效。</li>
                    <li>URL表单类型的流程请联系开发人员提供关键字。</li>
                </ol>
            </div>
            <c:choose>
                <c:when test="${(empty definition.businessurl) and ((empty definition.formkey) and (empty form)) }">
                    <div class="tip" style="color: #f00">该流程未配置表单</div>
                </c:when>
                <c:otherwise>
                    <form id="activiti-keywordform-definitionOtherSettingPage" action="act/saveKeywordRule.do" method="post">
                        <input type="hidden" name="keywordrule.definitionkey" value="${param.definitionkey }"/>
                        <c:choose>
                            <c:when test="${not empty definition.businessurl }">
                                <input type="hidden" name="keywordrule.definitionset" value="${definition.businessurl }"/>
                                <div id="activiti-div1-definitionOtherSettingPage">
                                    <table style="width: 100%;" class="content" cellpadding="0" cellspacing="0">
                                        <%--<tr style="background:linear-gradient(#ffffff, #dfdfdf);line-height:28px;">--%>
                                        <tr style="background: #dfdfdf; line-height:28px;">
                                            <td style="width:15%">序号</td>
                                            <td style="width:85%">关键字</td>
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td><input class="len180" name="keywordrule.keyword1" id="acitivti-keyword1-definitionOtherSettingPage" value="<c:out value='${keywordrule.keyword1 }'/>" maxlength="50" autocomplete="off"/></td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td><input class="len180" name="keywordrule.keyword2" id="acitivti-keyword2-definitionOtherSettingPage" value="<c:out value='${keywordrule.keyword2 }'/>" maxlength="50" autocomplete="off"/></td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td><input class="len180" name="keywordrule.keyword3" id="acitivti-keyword3-definitionOtherSettingPage" value="<c:out value='${keywordrule.keyword3 }'/>" maxlength="50" autocomplete="off"/></td>
                                        </tr>
                                        <tr>
                                            <td>4</td>
                                            <td><input class="len180" name="keywordrule.keyword4" id="acitivti-keyword4-definitionOtherSettingPage" value="<c:out value='${keywordrule.keyword4 }'/>" maxlength="50" autocomplete="off"/></td>
                                        </tr>
                                        <tr>
                                            <td>5</td>
                                            <td><input class="len180" name="keywordrule.keyword5" id="acitivti-keyword5-definitionOtherSettingPage" value="<c:out value='${keywordrule.keyword5 }'/>" maxlength="50" autocomplete="off"/></td>
                                        </tr>
                                    </table>
                                    <div id="activiti-check-definitionOtherSettingPage" class="nodisplay">
                                        <div class="easyui-panel" data-options="href:'${definition.businessurl }?type=handle'">
                                        </div>
                                    </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" name="keywordrule.definitionset" value="${definition.formkey }"/>
                                <div id="activiti-div1-definitionOtherSettingPage">
                                    <table style="width: 100%;" class="content" cellpadding="0" cellspacing="0">
                                        <%--<tr style="background:linear-gradient(#ffffff, #dfdfdf);line-height:28px;">--%>
                                        <tr style="background: #dfdfdf; line-height:28px;">
                                            <td style="width:15%">序号</td>
                                            <td style="width:85%">关键字</td>
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td>
                                                <select class="len180" name="keywordrule.keyword1">
                                                    <option></option>
                                                    <c:forEach items="${items }" var="item" varStatus="idx">
                                                        <c:choose>
                                                            <c:when test="${item.itemid eq keywordrule.keyword1}">
                                                                <option value="${item.itemid }" selected="selected" ><c:out value="${item.itemname }"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.itemid }" ><c:out value="${item.itemname }"/></option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>
                                                <select class="len180" name="keywordrule.keyword2">
                                                    <option></option>
                                                    <c:forEach items="${items }" var="item" varStatus="idx">
                                                        <c:choose>
                                                            <c:when test="${item.itemid eq keywordrule.keyword2}">
                                                                <option value="${item.itemid }" selected="selected" ><c:out value="${item.itemname }"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.itemid }" ><c:out value="${item.itemname }"/></option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>
                                                <select class="len180" name="keywordrule.keyword3">
                                                    <option></option>
                                                    <c:forEach items="${items }" var="item" varStatus="idx">
                                                        <c:choose>
                                                            <c:when test="${item.itemid eq keywordrule.keyword3}">
                                                                <option value="${item.itemid }" selected="selected" ><c:out value="${item.itemname }"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.itemid }" ><c:out value="${item.itemname }"/></option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>4</td>
                                            <td>
                                                <select class="len180" name="keywordrule.keyword4">
                                                    <option></option>
                                                    <c:forEach items="${items }" var="item" varStatus="idx">
                                                        <c:choose>
                                                            <c:when test="${item.itemid eq keywordrule.keyword4}">
                                                                <option value="${item.itemid }" selected="selected" ><c:out value="${item.itemname }"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.itemid }" ><c:out value="${item.itemname }"/></option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>5</td>
                                            <td>
                                                <select class="len180" name="keywordrule.keyword5">
                                                    <option></option>
                                                    <c:forEach items="${items }" var="item" varStatus="idx">
                                                        <c:choose>
                                                            <c:when test="${item.itemid eq keywordrule.keyword5}">
                                                                <option value="${item.itemid }" selected="selected" ><c:out value="${item.itemname }"/></option>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <option value="${item.itemid }" ><c:out value="${item.itemname }"/></option>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </form>
                </c:otherwise>
            </c:choose>
        </div>
        <!--
        <div title="审批节点显示信息设置" data-options="" style="overflow:auto; padding: 2px;">

        </div>
        -->
    </div>
	<script type="text/javascript">

        <!--

         $(function(){

             $('#activiti-save-definitionOtherSettingPage').click(function() {

                 $('#activiti-keywordform-definitionOtherSettingPage').form({
                     onSubmit: function(){

                         <c:choose>
                             <c:when test="${not empty definition.businessurl }">
                                 var words = new Array();
                                 $('input[name^="keywordrule.keyword"]').each(function() {

                                     if($(this).val() != '') {
                                         words.push($(this).val());
                                     }
                                 });
                                 $('input[name^="keywordrule.keyword"]').val('');
                                 $.each(words, function(index, value){
                                     $('input[name="keywordrule.keyword' + (index + 1) + '"]').val(value);
                                 });

                             </c:when>
                             <c:otherwise>
                                 var words = new Array();
                                 $('select[name^="keywordrule.keyword"]').each(function() {

                                     if($(this).val() != '') {
                                         words.push($(this).val());
                                     }
                                 });
                                 $('select[name^="keywordrule.keyword"]').val('');
                                 $.each(words, function(index, value){
                                     $('select[name="keywordrule.keyword' + (index + 1) + '"]').val(value);
                                 });

                         </c:otherwise>
                         </c:choose>

                         loading('保存中...');
                     },
                     success:function(data){

                         loaded();
                         try {

                             var json = $.parseJSON(data);
                             if(json.flag) {

                                 $.messager.alert('提示', '设置成功。');
                                 return false;
                             }
                             $.messager.alert('提示', '设置失败！');

                         } catch (e) {
                             $.messager.alert('提示', '设置失败！');
                         }
                     }
                 });
                 $('#activiti-keywordform-definitionOtherSettingPage').submit();
             });


             <c:if test="${not empty definition.businessurl }">

                $('input[name*="keywordrule.keyword"]').change(function() {

                    var key = $(this).val();

                    if($('#activiti-check-definitionOtherSettingPage').find('[name="' + key + '"]').length == 0) {

                        $.messager.alert('提醒', '表单中不存在该项目！');
                        $(this).val('');
                        $(this).focus();
                        return false;
                    }
                });

             </c:if>

         });

        -->

    </script>
  </body>
</html>
