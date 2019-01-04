<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>采购发票</title>
  </head>
  
  <body>
  	<script type="text/javascript">
  		$("#account-panel-relation-upper").dialog({
				href:"account/payable/showPurchaseInvoiceRelationUpperPage.do",
				title:"上游单据查询",
			    closed:false,
				modal:true,
				cache:false,
			    width:500,
			    height:300,
			    buttons:[{
							text:'查询',
							handler:function(){
								sourceQuery();
							}
						}]
			});
		$("#account-buttons-purchaseInvoicePage").buttonWidget("initButtonType", 'add');
  	</script>
  </body>
</html>
