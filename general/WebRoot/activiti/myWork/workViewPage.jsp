<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作查看</title>
	<%@include file="/include.jsp" %>
  </head>
  <body>
  <style type="text/css">
  .panel-header {
  	background: #dce5f4;
  }
  .panel-title {
  	color: #000;
  }
  </style>
  <div class="easyui-layout" data-options="fit:true,border:false">
      <div data-options="region:'north',border:false">
          <div style="padding: 5px; font-weight: bold; background: #dce5f4;">
              OA号：${process.id }
          </div>
          <c:if test="${(not empty process.keyword1) or (not empty process.keyword2) or (not empty process.keyword3) or (not empty process.keyword4) or (not empty process.keyword5)}">
              <div id="activiti-keywords-workViewPage" style="padding: 5px; font-weight: bold; background: #fff; border: 1px solid #dce5f4;">
                  关键字：
                  <c:if test="${not empty process.keyword1 }">
                      <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword1 }"></c:out></span>
                  </c:if>
                  <c:if test="${not empty process.keyword2 }">
                      <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword2 }"></c:out></span>
                  </c:if>
                  <c:if test="${not empty process.keyword3 }">
                      <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword3 }"></c:out></span>
                  </c:if>
                  <c:if test="${not empty process.keyword4 }">
                      <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword4 }"></c:out></span>
                  </c:if>
                  <c:if test="${not empty process.keyword5 }">
                      <span style="margin: 3px; padding: 3px; border: solid 1px #ddd;"><c:out value="${process.keyword5 }"></c:out></span>
                  </c:if>
              </div>
          </c:if>
      </div>
  	<div data-options="region:'center',border:false">
  		<div id="activiti_div_workViewPage">该工作不存在。</div>
	</div>
	<div data-options="region:'south',border:false">
	    <div class="buttonDivR" id="activiti-buttons-workViewPage">
            <c:if test="${not empty process}">
                <a href="javascript:void(0);" id="activiti-print-workViewPage" class="easyui-linkbutton" data-options="iconCls:'button-print'" onclick="javascript:var w = window.open('act/workPrintPage.do?instanceid=${process.instanceid }', '打印');w.focus();">打印</a>
				<security:authorize url="/activiti/trace/traceListPage.do">
					<a href="javascript:void(0);" id="activiti-trace-workViewPage" class="easyui-linkbutton" data-options="iconCls:'more-history'">数据追踪</a>
				</security:authorize>
                <a href="javascript:void(0);" id="activiti-viewflow-workViewPage" class="easyui-linkbutton" data-options="iconCls:'button-view'">查看流程</a>
                <%-- <a href="javascript:void(0);" id="activiti-viewflowpic-workViewPage" class="easyui-linkbutton" data-options="iconCls:'button-view'">查看流程图</a> --%>
            </c:if>
            <a href="javascript:void(0);" id="activiti-close-workViewPage" class="easyui-linkbutton" data-options="iconCls:'button-close'">关闭</a>
	   	</div>
	</div>
  </div>
  <div id="activiti-dialog1-workViewPage"></div>
  <div id="activiti-dialog2-workViewPage"></div>
  <div id="activiti-dialog3-workViewPage"></div>
	<script type="text/javascript">

        <!--

		var handleWork_taskId = "${taskid }";
		$(function(){

            $('#activiti-close-workViewPage').click(function() {
                parent.closeNowTab();
            });

			var handleWork_formType = "${definition.formtype}";
			
			var url = "";
			if(handleWork_formType == "formkey"){
				url= "act/workFormKeyViewPage.do?taskid=${taskid }&instanceid=${instanceid }&processid=${process.id }";
			} else if(handleWork_formType == "business"){
			
				// 当前流程实例未结束，且流程发起人为当前用户
				<c:choose>
					<c:when test="${process.isend == '0' and process.applyuserid == user.userid and from == '1' }">
						<c:choose>
							<c:when test="${fn:contains(businessUrl, '?')}">
								url = "${businessUrl}&type=view&id=${businessId }&cancel=1&processid=${process.id }";
							</c:when>
							<c:otherwise>
								url = "${businessUrl}?type=view&id=${businessId }&cancel=1&processid=${process.id }";
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${fn:contains(businessUrl, '?')}">
								url = "${businessUrl}&type=view&id=${businessId }&cancel=0&processid=${process.id }";
							</c:when>
							<c:otherwise>
								url = "${businessUrl}?type=view&id=${businessId }&cancel=0&processid=${process.id }";
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			}
			
			$("#activiti_div_workViewPage").panel({
				href:url,
				fit:true,
				cache:false,
			    maximized:true,
			    border:false,
			    onLoad: function() {

					$('#activiti-viewflow-workViewPage').click(viewWorkFlow);
                    $('#activiti-trace-workViewPage').click(viewWorkDataTrace);
			    }
			});
			
		});
	
		function viewWorkFlowPic() {

			if('${process.id }' == ''){
				return false;
			}
			
			$('#activiti-dialog1-workViewPage').dialog({
				title:'查看流程图',
				width:600,
				height:450,
				closed:false,
				cache:false,
				modal: true,
				maximizable:true,
				maximized: true,
				resizable:true,
				href:'act/commentImgPage.do?type=3&id=${process.id }',
				onClose: function() {
	
					$('head').append('<base id="basePath" href="' + base_href + '"></base>');
				}
			});	
		}
		
		function viewWorkFlow() {

			if('${process.id }' == ''){
				return false;
			}

			var id = getRandomid();

			$('body').append('<div id="activiti-dialog' + id + '-workViewPage"></div>');
			$('#activiti-dialog' + id + '-workViewPage').dialog({
				title:'查看流程',
				//width:600,
				//height:450,
				closed:false,
				cache:false,
				modal: true,
				maximizable:true,
				maximized: true,
				resizable:true,
				//href:'act/commentListPage.do?type=3&id=${process.id }'
				href:'act/commentViewPage.do?id=${process.id }',
				onClose: function() {
					$('head').append('<base id="basePath" href="' + base_href + '"></base>');
					$('#activiti-dialog' + id + '-workViewPage').dialog('destroy');
				}
			});
		}

        function viewWorkDataTrace() {

            if('${process.id }' == ''){
                return false;
            }

            $('#activiti-dialog3-workViewPage').dialog({
                title: '数据记录',
                maximized: true,
                closed: false,
                cache: false,
                modal: true,
                maximizable: true,
                resizable: true,
                href: 'act/traceListPage.do',
				queryParams: {id: '${process.id }'}
            });

        }

        -->
	</script>
  </body>
</html>
