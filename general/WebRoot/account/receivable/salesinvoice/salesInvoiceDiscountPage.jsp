<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>销售发票</title>
  </head>
  
  <body>
    	<table  border="0" style="width: 400px;">
   			<tr>
   				<td width="120">折扣率(%):</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-salesInvoice-discount" width="200"/>
   				</td>
     		</tr>
   		</table>
   		<script type="text/javascript">
   			$("#account-salesInvoice-discount").numberbox({
   				precision:2,
   				max:100
   			});
   		</script>
  </body>
</html>
