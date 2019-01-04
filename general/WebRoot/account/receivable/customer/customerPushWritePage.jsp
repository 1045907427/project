<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应收款冲差核销页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div id="account-dialog-writeoff"></div>
    <script type="text/javascript">
    	$("#account-dialog-writeoff").dialog({
			href:"account/receivable/showWriteoffCollectionOrderPage.do?id=${customerid}&invoiceid=${id}",
			title:"核销",
		    fit:true,
			modal:true,
			cache:false,
			maximizable:true,
			resizable:true,
		    cache: false,  
		    modal: true,
		    buttons:[{
					text:'确定',
					handler:function(){
						collectionOrderWriteOff();
					}
				}]
		});
    </script>
  </body>
</html>
