<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
      <%@include file="/include.jsp" %>
  </head>
  
  <body>
  <div id="notice-tree-noticePublishRangeDepartList" class="ztree"></div>
	<script type="text/javascript">
 		$(document).ready(function(){
            var noticePublishRangeDepartTreeSetting = {
                check: {
                    enable: true,
                    chkboxType:{ "Y" : "ps", "N" : "s" }
                },
                view: {
                    dblClickExpand: false,
                    showLine: true,
                    selectedMulti: true,
                    showIcon:true,
                    expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
                },
                async: {
                    enable: true,
                    type:'post',
                    url: "basefiles/showDepartMentCheckedTree.do",
                    autoParam: ["id","parentid", "text","state"],
                    otherParam:["checkidarrs","${receivedept}","view","view","checkall","${checkall}"]
                },
                data: {
                    key:{
                        title:"text",
                        name:"text"
                    },
                    simpleData: {
                        enable:true,
                        idKey: "id",
                        pIdKey: "parentid",
                        rootPId: null
                    }
                }
            };

            $.fn.zTree.init($("#notice-tree-noticePublishRangeDepartList"), noticePublishRangeDepartTreeSetting,null);

 		});
 	</script>
  </body>
</html>

